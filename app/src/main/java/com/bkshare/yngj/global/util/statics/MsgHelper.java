package com.bkshare.yngj.global.util.statics;

import com.bkshare.yngj.global.model.GlobalEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zmy_11 on 2017/7/15.
 */

public class MsgHelper {

    public static void sendEvent(int type,String tip,Object object)
    {
        GlobalEvent event=new GlobalEvent();
        event.type=type;
        event.tip=tip;
        event.obj=object;
        EventBus.getDefault().post(event);
    }
}
