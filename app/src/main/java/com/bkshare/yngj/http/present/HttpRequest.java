package com.bkshare.yngj.http.present;

import com.bkshare.yngj.http.present.inf.HttpRequestInf;
import com.bkshare.yngj.identity.model.LoginSession;
import com.bkshare.yngj.identity.model.User;

import java.util.List;

public class HttpRequest implements HttpRequestInf {


    @Override
    public LoginSession validateUserPsw(User user) {
        HttpLogin httpLogin = new HttpLogin();
        LoginSession loginResult = httpLogin.requestAuth(user);
        return loginResult;
    }

    @Override
    public List<String> getUserTel() {
        HttpCore httpCore = new HttpCore();
        return httpCore.getUserTels();
    }
}
