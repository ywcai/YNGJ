package com.bkshare.yngj.identity.Presenter;

import com.bkshare.yngj.global.cfg.GlobalEventT;
import com.bkshare.yngj.global.util.statics.MD5;
import com.bkshare.yngj.global.util.statics.MsgHelper;
import com.bkshare.yngj.http.present.HttpRequest;
import com.bkshare.yngj.http.present.inf.HttpRequestInf;
import com.bkshare.yngj.identity.Presenter.inf.LoginActionInf;
import com.bkshare.yngj.identity.cfg.LoginInfoT;
import com.bkshare.yngj.identity.model.LoginSession;
import com.bkshare.yngj.identity.model.User;

public class LoginAction implements LoginActionInf {
    User tempUser = new User();
    HttpRequestInf httpRequestinf = new HttpRequest();

    @Override
    public void loginForSelf(String userId, String psw) {
        sendMsgLoading();
        ValidateFormat validate = new ValidateFormat();
        if ((!validate.checkUserName(userId)) || (!validate.checkPsw(psw))) {
            sendMsgLoginFormatErr();
            return;
        }
        String md5Psw = MD5.md5(psw);
        tempUser.userId = userId;
        tempUser.md5psw = md5Psw;
        tempUser.loginChannel = LoginInfoT.login_channel_self;
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginSession result = httpRequestinf.validateUserPsw(tempUser);
                processResult(result);
            }
        }).start();
    }

    @Override
    public void loginForQQ() {

    }

    @Override
    public void loginForMM() {

    }

    //在调用时使用开启新的线程？？？
    @Override
    public void loginCache(final User cacheUser) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginSession result = httpRequestinf.validateUserPsw(cacheUser);
                processResult(result);

            }
        }).start();
    }

    @Override
    public void reg(String userId, String psw) {

    }

    @Override
    public void findPassword(String userId) {

    }


    private void processResult(LoginSession result) {
        if (result == null) {
            sendMsgLoginNetErr();
            return;
        }
        if (result.isLogin) {
            sendMsgLoginSuccess();
        } else {
            sendMsgLoginFail();
        }
    }

    private void sendMsgLoginSuccess() {
        MsgHelper.sendEvent(GlobalEventT.login_success, "", tempUser);
    }

    private void sendMsgLoginFail() {
        MsgHelper.sendEvent(GlobalEventT.login_fail, "", null);
    }

    private void sendMsgLoginNetErr() {
        MsgHelper.sendEvent(GlobalEventT.login_net_err, "", null);
    }

    private void sendMsgLoginFormatErr() {
        MsgHelper.sendEvent(GlobalEventT.login_format_err, "", null);
    }
    private void sendMsgLoading() {
        MsgHelper.sendEvent(GlobalEventT.login_loading, "", null);
    }

}
