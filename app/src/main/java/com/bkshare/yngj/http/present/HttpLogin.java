package com.bkshare.yngj.http.present;

import com.bkshare.yngj.global.model.instance.CacheProcess;
import com.bkshare.yngj.identity.model.LoginSession;
import com.bkshare.yngj.identity.model.User;

/**
 * Created by zmy_11 on 2017/7/15.
 */

public class HttpLogin {
    CacheProcess cacheProcess = CacheProcess.getInstance();
    public LoginSession requestAuth(User user) {
        //伪代码，始终通过验证
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LoginSession loginResult = reqLoginSession();
        writeCache(user, loginResult);
        return loginResult;
    }
    private void writeCache(User user, LoginSession loginResult) {
        if (!loginResult.isLogin) {
            cacheProcess.setCacheUser(null);
            cacheProcess.setLoginSession(null);
        } else {
            cacheProcess.setCacheUser(user);
            cacheProcess.setLoginSession(loginResult);
        }
    }
    private LoginSession reqLoginSession()
    {
        LoginSession loginSession = new LoginSession();
        loginSession.isLogin = true;
        loginSession.accessToken = "testToken";
        return loginSession;
    }

}
