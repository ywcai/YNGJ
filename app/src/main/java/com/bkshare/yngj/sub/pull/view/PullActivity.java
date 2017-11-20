package com.bkshare.yngj.sub.pull.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appyvet.rangebar.RangeBar;
import com.bkshare.yngj.R;
import com.bkshare.yngj.global.model.GlobalEvent;
import com.bkshare.yngj.global.util.statics.InputValidate;
import com.bkshare.yngj.global.util.statics.LsLog;
import com.bkshare.yngj.global.util.statics.SetTitle;
import com.bkshare.yngj.sub.push.view.PushActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;
import mehdi.sakout.fancybuttons.FancyButton;
import ywcai.ls.control.LoadingDialog;
import ywcai.ls.control.flex.FlexButtonLayout;
import ywcai.ls.control.flex.OnFlexButtonClickListener;

@Route(path = "/sub/pull/view/PullActivity")
public class PullActivity extends AppCompatActivity {

    private FlexButtonLayout flex_button_test, flex_button_test2, flex_button_test3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTitle.setTitleTransparent(getWindow());//沉静式状态栏
        setContentView(R.layout.activity_pull);
        flex_button_test = (FlexButtonLayout) findViewById(R.id.flex_button_test);
        List<String> list = new ArrayList<>();
        list.add("圆通");
        list.add("申通");
        list.add("快递");
        list.add("MES");
        int[] select = new int[list.size()];
        flex_button_test.setDataAdapter(list);
        flex_button_test.setSelectIndex(select);
        flex_button_test.setOnFlexButtonClickListener(new OnFlexButtonClickListener() {
            @Override
            public void clickItem(int i, boolean b) {

            }

            @Override
            public void clickAllBtn(int[] ints, boolean b) {

            }
        });
        flex_button_test.setBtnSecondaryBgColor(2, ContextCompat.getColor(this,R.color.LDarkLight));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
