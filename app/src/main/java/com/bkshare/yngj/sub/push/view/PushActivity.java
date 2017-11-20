package com.bkshare.yngj.sub.push.view;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bkshare.yngj.R;
import com.bkshare.yngj.global.cfg.AppConfig;
import com.bkshare.yngj.global.cfg.GlobalEventT;
import com.bkshare.yngj.global.model.GlobalEvent;
import com.bkshare.yngj.global.util.statics.SetTitle;
import com.bkshare.yngj.sub.PushOrder;
import com.bkshare.yngj.sub.push.Presenter.PushAction;
import com.bkshare.yngj.sub.push.Presenter.PushResultHandler;
import com.bkshare.yngj.sub.push.Presenter.inf.PushActionInf;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import me.drakeet.materialdialog.MaterialDialog;
import mehdi.sakout.fancybuttons.FancyButton;
import ywcai.ls.control.LoadingDialog;
import ywcai.ls.control.flex.FlexButtonLayout;
import ywcai.ls.control.flex.OnFlexButtonClickListener;

@Route(path = "/sub/push/view/PushActivity")
public class PushActivity extends AppCompatActivity {
    private MaterialDialog settingDialog, orderDialog;
    private LoadingDialog loadingDialog;
    private PushActionInf pushActionInf;
    private ZXingScannerView mScannerView;
    private PushResultHandler mResultHandler;
    private MediaPlayer mediaPlayer;
    FlexButtonLayout flexCompany, flexStyle, flexNotify, flexTels;
    private TextView barNumText, companyText, packageStyleText, notificationTypeText;
    private MaterialEditText editTextInputTel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTitle.setTitleTransparent(getWindow());//沉静式状态栏
        setContentView(R.layout.activity_push);
        InitAction();
        InitPopView();
        InitToolBar();
        initScannerView();
    }

    @Override
    public void onResume() {
        pushActionInf.recoveryPushState();
        recoveryScan();
        super.onResume();

    }

    @Override
    public void onPause() {
        stopScan();
        super.onPause();
    }

    private void initScannerView() {
        mScannerView = (ZXingScannerView) findViewById(R.id.push_scanner_view);
        mResultHandler = new PushResultHandler();
        mResultHandler.setAction(pushActionInf);
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.beep);
    }

    private void InitPopView() {
        createOrderDialog();
        createSettingDialog();
        createLoadingDialog();
    }

    private void InitAction() {
        pushActionInf = new PushAction();
        FloatingActionButton setting = (FloatingActionButton) findViewById(R.id.push_float_btn_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushActionInf.clickFloatBtn();
            }
        });

    }

    private void InitToolBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.push_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createOrderDialog() {
        orderDialog = new MaterialDialog(this);
        orderDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(this).inflate(R.layout.pop_dialog_push_order, null);
        orderDialog.setContentView(view);
        editTextInputTel = (MaterialEditText) view.findViewById(R.id.push_order_telnum);
        editTextInputTel.setCursorVisible(true);
        editTextInputTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 2 || s.length() >= 11) {
                    flexTels.removeAllViews();
                    return;
                }
                pushActionInf.inputTelNumChange(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        barNumText = (TextView) view.findViewById(R.id.push_order_bar_number);
        companyText = (TextView) view.findViewById(R.id.push_order_company);
        packageStyleText = (TextView) view.findViewById(R.id.push_order_style);
        notificationTypeText = (TextView) view.findViewById(R.id.push_order_notification);


        flexTels = (FlexButtonLayout) view.findViewById(R.id.flex_custom_tels);
        flexTels.setOnFlexButtonClickListener(new OnFlexButtonClickListener() {
            @Override
            public void clickItem(int i, boolean b) {
                String text = ((FancyButton) flexTels.getChildAt(i)).getText().toString();
                pushActionInf.selectTelNum(text);
            }

            @Override
            public void clickAllBtn(int[] ints, boolean b) {

            }
        });

        FancyButton postOrder = (FancyButton) view.findViewById(R.id.btn_order_post);
        FancyButton cancal = (FancyButton) view.findViewById(R.id.btn_order_cancal);
        postOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //所有数据在缓存中读取保存
                pushActionInf.postOrder();
            }
        });
        cancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushActionInf.clickCancalPostOrder();
            }
        });


    }

    private void createSettingDialog() {
        settingDialog = new MaterialDialog(this);
        settingDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(this).inflate(R.layout.pop_dialog_push_setting, null);
        settingDialog.setContentView(view);
        FancyButton saveSetting = (FancyButton) view.findViewById(R.id.push_setting_save);
        saveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushActionInf.clickCancalSettingBtn();
            }
        });
        flexStyle = (FlexButtonLayout) view.findViewById(R.id.flex_package);
        flexNotify = (FlexButtonLayout) view.findViewById(R.id.flex_notification);
        flexCompany = (FlexButtonLayout) view.findViewById(R.id.flex_company);
        createCompanyTag();
        createStyleTag();
        createNotifyTag();
    }

    private void createCompanyTag() {
        List<String> tags = new ArrayList();
        for (int i = 0; i < AppConfig.strCompanyName.length; i++) {
            tags.add(AppConfig.strCompanyName[i]);
        }
        flexCompany.setDataAdapter(tags);
        flexCompany.setOnFlexButtonClickListener(new OnFlexButtonClickListener() {
            @Override
            public void clickItem(int i, boolean b) {
                pushActionInf.selectFactoryType(i);
            }

            @Override
            public void clickAllBtn(int[] ints, boolean b) {

            }
        });

    }

    private void createStyleTag() {
        List<String> tags = new ArrayList();
        for (int i = 0; i < AppConfig.strOrderStyle.length; i++) {
            tags.add(AppConfig.strOrderStyle[i]);
        }
        flexStyle.setDataAdapter(tags);
        flexStyle.setOnFlexButtonClickListener(new OnFlexButtonClickListener() {
            @Override
            public void clickItem(int i, boolean b) {
                pushActionInf.selectPackageStyle(i);
            }

            @Override
            public void clickAllBtn(int[] ints, boolean b) {

            }
        });
    }

    private void createNotifyTag() {
        List<String> tags = new ArrayList();
        for (int i = 0; i < AppConfig.strNotifyContent.length; i++) {
            tags.add(AppConfig.strNotifyContent[i]);
        }
        flexNotify.setDataAdapter(tags);
        flexNotify.setOnFlexButtonClickListener(new OnFlexButtonClickListener() {
            @Override
            public void clickItem(int i, boolean b) {
                pushActionInf.selectNotification(i);
            }

            @Override
            public void clickAllBtn(int[] ints, boolean b) {

            }
        });
    }


    private void createLoadingDialog() {
        loadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(GlobalEvent event) {
        switch (event.type) {
            case GlobalEventT.push_pop_bottom_tip:
                if ((boolean) event.obj) {
                    showSnackTip("存件成功！");
                } else {
                    showSnackTip("存件失败，请重新扫描包裹！");
                }
                break;
            case GlobalEventT.push_pop_setting_dialog:
                popSettingDialog();
                break;
            case GlobalEventT.push_hide_setting_dialog:
                hideSettingDialog();
                break;
            case GlobalEventT.push_hide_order_dialog:
                hideOrderDialog();
                break;
            case GlobalEventT.push_pop_order_loading_dialog:
                popLoadingDialog();
                break;
            case GlobalEventT.push_hide_order_loading_dialog:
                hideLoadingDialog();
                break;
            case GlobalEventT.push_stop_scan:
                stopScan();
                break;
            case GlobalEventT.push_resume_scan:
                recoveryScan();
                break;
            case GlobalEventT.push_play_bee_voice:
                playBeeVoice();
                break;
            case GlobalEventT.push_draw_setting_dialog_company:
                recoveryCompany((int) event.obj);
                break;
            case GlobalEventT.push_draw_setting_dialog_style:
                recoveryStyle((int) event.obj);
                break;
            case GlobalEventT.push_draw_setting_dialog_notify:
                recoveryNotify((int) event.obj);
                break;
            case GlobalEventT.push_draw_order_dialog_info:
                recoveryOrderDialog((PushOrder) event.obj);
                popOrderDialog();
                break;
            case GlobalEventT.push_draw_order_tels_tag:
                recoveryTelTags((List<String>) event.obj);
                break;
            case GlobalEventT.push_set_order_input_telnum:
                setTelInputDefault(event.tip);
                break;
        }
    }

    private void setTelInputDefault(String tip) {
        editTextInputTel.setText(tip);
    }

    private void recoveryTelTags(List<String> tels) {
        flexTels.setDataAdapter(tels);
    }


    private void recoveryOrderDialog(PushOrder pushOrder) {
        barNumText.setText(pushOrder.barNum);
        companyText.setText(AppConfig.strCompanyName[pushOrder.pushState.bagOperator]);
        packageStyleText.setText(AppConfig.strOrderStyle[pushOrder.pushState.bagStyle]);
        notificationTypeText.setText(AppConfig.strNotifyContent[pushOrder.pushState.notificationType]);
    }

    private void popOrderDialog() {
        orderDialog.show();
        stopScan();
    }

    private void playBeeVoice() {
        mediaPlayer.start();
    }


    private void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

    private void popLoadingDialog() {
        loadingDialog.show();
    }

    private void hideOrderDialog() {
        orderDialog.dismiss();
    }


    private void popSettingDialog() {
        settingDialog.show();
        stopScan();
    }

    private void hideSettingDialog() {
        settingDialog.dismiss();
        recoveryScan();
    }

    private void showSnackTip(String tip) {
        RelativeLayout snack_container = (RelativeLayout) findViewById(R.id.snack_container);
        Snackbar.make(snack_container, tip, Snackbar.LENGTH_SHORT).show();
    }

    private void stopScan() {
        mScannerView.setResultHandler(null);
        mScannerView.stopCamera();
    }

    private void recoveryScan() {
        mScannerView.setResultHandler(mResultHandler);
        mScannerView.startCamera();
    }

    private void recoveryCompany(int selectIndex) {
        flexCompany.setCurrentIndex(selectIndex);
    }

    private void recoveryStyle(int selectIndex) {
        flexStyle.setCurrentIndex(selectIndex);
    }

    private void recoveryNotify(int selectIndex) {
        flexNotify.setCurrentIndex(selectIndex);
    }


}
