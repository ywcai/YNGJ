package com.bkshare.yngj.sub;

import com.bkshare.yngj.global.util.statics.MyTime;
import com.bkshare.yngj.sub.push.model.PushState;


public class PushOrder {
    public String customTel, barNum, actionTime;
    public int actionType = 1;//默认是1存入,2表示取件;
    public int storageNum;//服务端自动下发分配货架号，存入了后生成反馈。
    public PushState pushState;//其他包裹属性，包括形状，快递厂商，通知短信类型.
    public PushOrder()
    {
        actionTime= MyTime.getDetailTime();

    }
}
