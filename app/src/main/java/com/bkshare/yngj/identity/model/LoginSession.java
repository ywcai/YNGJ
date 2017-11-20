package com.bkshare.yngj.identity.model;

public class LoginSession {
    public boolean isLogin;

    //*****
    public String accessToken;
    //访问服务端数据接口的临时秘钥，每次请求均要将其混入基础信息做MD5加密，在服务端进行验证
    //该秘钥每次退出登录或重新登录后，服务端均会重新分配
    //登录成功后，将缓存这个秘钥，一旦秘钥丢失，任何数据操作都将失败。并且跳转页面时会被ali_router路由组件的拦截器拦截后返回到登录页面
    public String errCode;
    public String userId;//手机号

    @Override
    public String toString() {
        return "LoginSession{" +
                "isLogin=" + isLogin +
                ", accessToken='" + accessToken + '\'' +
                ", errCode='" + errCode + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
