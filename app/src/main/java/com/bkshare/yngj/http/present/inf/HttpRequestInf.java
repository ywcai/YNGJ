package com.bkshare.yngj.http.present.inf;

import com.bkshare.yngj.identity.model.LoginSession;
import com.bkshare.yngj.identity.model.User;

import java.util.List;

/**
 * Created by zmy_11 on 2017/10/3.
 */

public interface HttpRequestInf {
    LoginSession validateUserPsw(User user);

    List<String> getUserTel();
}
