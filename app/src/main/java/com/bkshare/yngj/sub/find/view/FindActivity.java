package com.bkshare.yngj.sub.find.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.bkshare.yngj.global.util.statics.SetTitle;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;
import mehdi.sakout.fancybuttons.FancyButton;

@Route(path = "/sub/find/view/FindActivity")
public class FindActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTitle.setTitleTransparent(getWindow());//沉静式状态栏
        setContentView(R.layout.activity_push);
    }


    @Override
    protected void onResume() {
//        action.activityResume();
        super.onResume();

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
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(GlobalEvent event) {
        switch (event.type) {

        }
    }


}
