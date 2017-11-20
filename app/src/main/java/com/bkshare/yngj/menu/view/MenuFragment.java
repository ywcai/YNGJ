package com.bkshare.yngj.menu.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bkshare.yngj.R;
import com.bkshare.yngj.menu.model.IndexMenu;
import com.bkshare.yngj.menu.model.MenuItemDecoration;
import com.bkshare.yngj.menu.model.TitleTipAdapter;
import com.bkshare.yngj.menu.presenter.inf.OnItemClickListener;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Observable;
import rx.functions.Action1;
import com.bkshare.yngj.global.cfg.AppConfig;
import com.bkshare.yngj.global.model.GlobalEvent;

import com.bkshare.yngj.menu.model.MenuAdapter;
import com.bkshare.yngj.menu.model.TitleTipMenu;


@Route(path = "/main/menuFragment")
public class MenuFragment extends Fragment {
    private List<IndexMenu> menuList = new ArrayList();
    private List<TitleTipMenu> titleTipMenuList = new ArrayList();
    private TitleTipAdapter titleTipAdapter;
    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private View view;
    private String[] menus = AppConfig.getMenuTextStr();
    private int[] menuImg = AppConfig.getMenuIconRes();
    private String[] linkUrl = AppConfig.getMenuUrlStr();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        //安装网格化选择菜单
        InitView();
        InitMenu();
        InitToolBar();
        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void InitToolBar() {
        CollapsingToolbarLayout cToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        cToolbar.setTitleEnabled(false);
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("快递管理");
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        break;
                    case R.id.menu2:
                        break;
                }
                return false;
            }
        });
    }

    private void InitView() {
        Glide.with(this).load(R.drawable
                .main_menu_top)
                .bitmapTransform(new BlurTransformation(this.getContext(), 10))
                .into((ImageView) view.findViewById(R.id.main_head_bg));
        InitTitleTip();
    }

    private void InitTitleTip() {
        titleTipMenuList = new ArrayList<>();
        TitleTipMenu titleTipMenu1 = new TitleTipMenu();
        titleTipMenu1.textColor = 0xFFF79646;
        Drawable d1 = ContextCompat.getDrawable(this.getContext(), R.drawable.main_title_run);

        titleTipMenu1.titleIcon = d1;
        titleTipMenu1.titleTopText = "运行中";
        titleTipMenu1.titleContent = "0";
        Drawable t1 = ContextCompat.getDrawable(this.getContext(), R.drawable.menu_title_border_run);
        titleTipMenu1.titleBorder = t1;

        TitleTipMenu titleTipMenu2 = new TitleTipMenu();
        titleTipMenu2.textColor = 0xFF91B743;
        Drawable d2 = ContextCompat.getDrawable(this.getContext(), R.drawable.main_title_end);
        titleTipMenu2.titleIcon = d2;
        titleTipMenu2.titleTopText = "待处理";
        titleTipMenu2.titleContent = "0";
        Drawable t2 = ContextCompat.getDrawable(this.getContext(), R.drawable.menu_title_border_end);
        titleTipMenu2.titleBorder = t2;

        TitleTipMenu titleTipMenu3 = new TitleTipMenu();
        titleTipMenu3.textColor = 0xFF32A5C3;
        Drawable d3 = ContextCompat.getDrawable(this.getContext(), R.drawable.main_title_all);
        titleTipMenu3.titleIcon = d3;
        titleTipMenu3.titleTopText = "已完成";
        titleTipMenu3.titleContent = "0";
        Drawable t3 = ContextCompat.getDrawable(this.getContext(), R.drawable.menu_title_border_all);
        titleTipMenu3.titleBorder = t3;

        titleTipMenuList.add(titleTipMenu1);
        titleTipMenuList.add(titleTipMenu2);
        titleTipMenuList.add(titleTipMenu3);
        titleTipAdapter = new TitleTipAdapter(this.getContext(), titleTipMenuList);
        RecyclerView titleTip = (RecyclerView) view.findViewById(R.id.main_title_grid);
        titleTip.setAdapter(titleTipAdapter);
        GridLayoutManager layoutManager = new
                GridLayoutManager(this.getContext(), 3);
        titleTip.setLayoutManager(layoutManager);
    }

    private void InitMenu() {

        for (int i = 0; i < menus.length; i++) {
            IndexMenu indexMenu = new IndexMenu();
            indexMenu.menuText = menus[i];
            indexMenu.imgUrl = ContextCompat.getDrawable(this.getContext(), menuImg[i]);
            indexMenu.linkUrl = linkUrl[i];
            menuList.add(indexMenu);
        }
        menuAdapter = new MenuAdapter(this.getContext(), menuList);
        menuAdapter.setOnclickListener(new OnItemClickListener() {
            @Override
            public void OnClickItem(View v, int pos) {
                Observable.just(pos).throttleFirst(3, TimeUnit.SECONDS)
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer pos) {
                                ARouter.getInstance().build(menuList.get(pos).linkUrl).navigation();
                            }
                        });
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.menus);
        recyclerView.setAdapter(menuAdapter);
        GridLayoutManager layoutManager = new
                GridLayoutManager(this.getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        MenuItemDecoration menuItemDecoration = new MenuItemDecoration();
        recyclerView.addItemDecoration(menuItemDecoration);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateDeviceList(GlobalEvent event) {
        switch (event.type) {

        }
    }





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
