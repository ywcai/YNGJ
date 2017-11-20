package com.bkshare.yngj.identity.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bkshare.yngj.R;
import com.bkshare.yngj.global.cfg.AppConfig;
import com.bkshare.yngj.global.cfg.GlobalEventT;
import com.bkshare.yngj.global.model.GlobalEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import com.bkshare.yngj.global.util.statics.SetTitle;
import com.bkshare.yngj.identity.Presenter.LoginAction;
import com.bkshare.yngj.identity.Presenter.inf.LoginActionInf;

import ywcai.ls.control.LoadingDialog;


@Route(path = "/identity/view/LoginActivity")
public class LoginActivity extends AppCompatActivity {
    private LoginActionInf loginActionInf = new LoginAction();
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTitle.setTitleTransparent(getWindow());
        setContentView(R.layout.activity_login);
        InitToolBar();
        InitActionEvent();
        CreateLoadingProgress();
    }

    private void CreateLoadingProgress() {
        loadingDialog= new LoadingDialog(this);
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


    private void InitToolBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.menu1:
//                        break;
//                    case R.id.menu2:
//                        break;
////                    case R.id.menu3:
////                        break;
////                    case R.id.menu4:
////                        break;
//                }
//                return false;
//            }
//        });
    }

    private void InitActionEvent() {
        AppCompatButton loginButton = (AppCompatButton) findViewById(R.id.login_login_action);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout username = (TextInputLayout) findViewById(R.id.login_input_username);
                TextInputLayout password = (TextInputLayout) findViewById(R.id.login_input_password);
                String userId = username.getEditText().getText().toString();
                String psw = password.getEditText().getText().toString();
                loginActionInf.loginForSelf(userId, psw);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        return false;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        PackageManager pm = getPackageManager();
        ResolveInfo homeInfo = pm.resolveActivity(
                new Intent(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_HOME), 0);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityInfo ai = homeInfo.activityInfo;
            Intent startIntent = new Intent(Intent.ACTION_MAIN);
            startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
            startIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(startIntent);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLoginView(GlobalEvent event) {
        switch (event.type) {
            case GlobalEventT.login_format_err:
                loginInputFormatErr();
                break;
            case GlobalEventT.login_success:
                loginSuccess();
                break;
            case GlobalEventT.login_net_err:
                loginNetErr();
                break;
            case GlobalEventT.login_fail:
                loginFail();
                break;
            case GlobalEventT.login_loading:
                loginLoading();
                break;
        }
    }


    private void loginInputFormatErr() {
        loginLoadingBarVisible(false);
        showInfo("输入账号密码格式错误，登录失败！");
    }

    private void loginSuccess() {
        loginLoadingBarVisible(false);
        ARouter.getInstance().build(AppConfig.MAIN_ACTIVITY_PATH).
                navigation();
    }

    private void loginNetErr() {
        loginLoadingBarVisible(false);
        showInfo("网络连接故障，登录失败！");
    }

    private void loginFail() {
        loginLoadingBarVisible(false);
        showInfo("账号密码错误，登录失败！");

    }

    private void loginLoading() {
        loginLoadingBarVisible(true);
    }


    //基础方法，直接渲染UI
    private void showInfo(String tip) {
        Toast.makeText(this, tip, Toast.LENGTH_LONG).show();
    }

    private void loginLoadingBarVisible(boolean isShow) {
        if (isShow) {
            loadingDialog.show();
        } else {
            loadingDialog.dismiss();
        }
    }

}


