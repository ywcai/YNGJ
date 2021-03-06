package com.bkshare.yngj.global.model.instance;

import com.bkshare.yngj.identity.model.User;

public class CoreInstance {
    private static Object lock = new Object();
    private static CoreInstance socketObject = null;
    public User loginUser;
    private CoreInstance() {

    }
    public static CoreInstance getInstance() {
        synchronized (lock) {
            if (socketObject == null) {
                socketObject = new CoreInstance();
            }
        }
        return socketObject;
    }
}
