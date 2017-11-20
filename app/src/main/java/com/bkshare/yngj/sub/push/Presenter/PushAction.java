package com.bkshare.yngj.sub.push.Presenter;

import android.widget.Toast;

import com.bkshare.yngj.global.cfg.GlobalEventT;
import com.bkshare.yngj.global.model.instance.CacheProcess;
import com.bkshare.yngj.global.util.statics.LsLog;
import com.bkshare.yngj.global.util.statics.MsgHelper;
import com.bkshare.yngj.http.present.HttpRequest;
import com.bkshare.yngj.http.present.inf.HttpRequestInf;
import com.bkshare.yngj.sub.PushOrder;
import com.bkshare.yngj.sub.push.Presenter.inf.PushActionInf;
import com.bkshare.yngj.sub.push.model.PushState;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zmy_11 on 2017/11/17.
 */

public class PushAction implements PushActionInf {
    PushState pushState = null;
    CacheProcess cacheProcess = CacheProcess.getInstance();
    PushOrder pushOrder;
    HttpRequestInf http = new HttpRequest();
    List<String> tels = new ArrayList<>();

    public PushAction() {
        pushState = cacheProcess.getPushState();
        if (pushState == null) {
            pushState = new PushState();
            cacheProcess.setPushState(pushState);
        }
        //初始化后台所有平台用户的手机号码
        new Thread(new Runnable() {
            @Override
            public void run() {
                tels.addAll(http.getUserTel());
            }
        }).start();
    }

    @Override
    public void recoveryPushState() {
        sendMsgDrawSelectCompany();
        sendMsgDrawSelectStyle();
        sendMsgDrawSelectNotify();
    }

    @Override
    public void clickFloatBtn() {
        sendMsgPopSettingDialog();
    }


    @Override
    public void scanTagSuccess(String barNum) {
        sendMsgStopScan();
        sendMsgPlayVoice();
        CreateCurrentOrder(barNum);
        sendMsgPopOrder();
    }


    @Override
    public void inputTelNumChange(final String tel) {
        Observable.from(tels)
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.contains(tel);
                    }
                })
                .take(10)//仅选则前10位做关联感应
                .toList()
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        sendMsgShowTelTags(strings);
                    }
                });


    }


    @Override
    public void scanTelNumSuccess() {

    }

    @Override
    public void selectPackageStyle(int index) {
        pushState.bagStyle = index;
        cacheProcess.setPushState(pushState);
        sendMsgDrawSelectStyle();
    }

    @Override
    public void selectFactoryType(int index) {
        pushState.bagOperator = index;
        cacheProcess.setPushState(pushState);
        sendMsgDrawSelectCompany();
    }

    @Override
    public void selectNotification(int index) {
        pushState.notificationType = index;
        cacheProcess.setPushState(pushState);
        sendMsgDrawSelectNotify();
    }

    @Override
    public void postOrder() {

        //读取缓存，然后发送请求到服务端保存
        sendMsgHideOrderDialog();
        sendMsgShowLoadingDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendMsgHideLoadingDialog();
                //if true//假设保存成功
                sendMsgPushOrderResult(true);
                sendMsgRecoveryScan();
            }
        }).start();
    }


    @Override
    public void clickCancalPostOrder() {
        sendMsgRecoveryScan();
        sendMsgHideOrderDialog();
    }

    @Override
    public void selectTelNum(String s) {
        pushOrder.customTel = s;
        sendMsgSetDefault();
    }


    @Override
    public void clickCancalSettingBtn() {
        sendMsgHideSettingDialog();
    }

    private void CreateCurrentOrder(String barNum) {
        pushOrder = new PushOrder();
        pushOrder.barNum = barNum;
        pushOrder.pushState = pushState;
    }

    private void sendMsgPopSettingDialog() {
        MsgHelper.sendEvent(GlobalEventT.push_pop_setting_dialog, "", null);
    }

    private void sendMsgHideSettingDialog() {
        MsgHelper.sendEvent(GlobalEventT.push_hide_setting_dialog, "", null);
    }

    private void sendMsgPopOrder() {
        MsgHelper.sendEvent(GlobalEventT.push_draw_order_dialog_info, "", pushOrder);
    }

    private void sendMsgHideOrderDialog() {
        MsgHelper.sendEvent(GlobalEventT.push_hide_order_dialog, "", null);
    }

    private void sendMsgShowLoadingDialog() {
        MsgHelper.sendEvent(GlobalEventT.push_pop_order_loading_dialog, "", null);
    }

    private void sendMsgHideLoadingDialog() {
        MsgHelper.sendEvent(GlobalEventT.push_hide_order_loading_dialog, "", null);
    }

    private void sendMsgPushOrderResult(boolean b) {
        MsgHelper.sendEvent(GlobalEventT.push_pop_bottom_tip, "", b);
    }

    private void sendMsgStopScan() {
        MsgHelper.sendEvent(GlobalEventT.push_stop_scan, "", null);
    }

    private void sendMsgRecoveryScan() {
        MsgHelper.sendEvent(GlobalEventT.push_resume_scan, "", null);
    }

    private void sendMsgPlayVoice() {
        MsgHelper.sendEvent(GlobalEventT.push_play_bee_voice, "", null);
    }

    private void sendMsgDrawSelectCompany() {
        MsgHelper.sendEvent(GlobalEventT.push_draw_setting_dialog_company, "", pushState.bagOperator);
    }

    private void sendMsgDrawSelectStyle() {
        MsgHelper.sendEvent(GlobalEventT.push_draw_setting_dialog_style, "", pushState.bagStyle);
    }

    private void sendMsgDrawSelectNotify() {
        MsgHelper.sendEvent(GlobalEventT.push_draw_setting_dialog_notify, "", pushState.notificationType);
    }

    private void sendMsgShowTelTags(List<String> telNumStrings) {
        MsgHelper.sendEvent(GlobalEventT.push_draw_order_tels_tag, "", telNumStrings);
    }

    private void sendMsgSetDefault() {
        MsgHelper.sendEvent(GlobalEventT.push_set_order_input_telnum, pushOrder.customTel,null);
    }


}
