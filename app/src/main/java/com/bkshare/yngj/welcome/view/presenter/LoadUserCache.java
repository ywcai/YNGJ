package com.bkshare.yngj.welcome.view.presenter;

import com.bkshare.yngj.global.cfg.GlobalEventT;
import com.bkshare.yngj.global.model.instance.CacheProcess;
import com.bkshare.yngj.global.util.statics.LsLog;
import com.bkshare.yngj.global.util.statics.MsgHelper;
import com.bkshare.yngj.identity.Presenter.LoginAction;
import com.bkshare.yngj.identity.Presenter.inf.LoginActionInf;
import com.bkshare.yngj.identity.model.User;

/**
 * Created by zmy_11 on 2017/11/17.
 */

public class LoadUserCache {
    CacheProcess cacheProcess = CacheProcess.getInstance();
    User cacheUser;

    //读取缓存自动登录
    public void login() {
        cacheUser = cacheProcess.getCacheUser();
        if (cacheUser == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendMsgLoadCacheNull();
                }
            }).start();
        } else {
            validateCacheUser(cacheUser);
        }
    }

    private void validateCacheUser(User cacheUser) {
        LoginActionInf loginActionInf = new LoginAction();
        loginActionInf.loginCache(cacheUser);
    }

    private void sendMsgLoadCacheNull() {
        MsgHelper.sendEvent(GlobalEventT.login_cache_null, "", null);
    }
}
