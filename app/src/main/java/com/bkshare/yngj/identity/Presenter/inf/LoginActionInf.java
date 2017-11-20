package com.bkshare.yngj.identity.Presenter.inf;

import com.bkshare.yngj.identity.model.User;

/**
 * Created by zmy_11 on 2017/7/16.
 */

public interface LoginActionInf {
    void loginForSelf(String userId,String psw);
    void loginForQQ();
    void loginForMM();
    void loginCache(User cacheUser);
    void reg(String userId,String psw);
    void findPassword(String userId);
}
