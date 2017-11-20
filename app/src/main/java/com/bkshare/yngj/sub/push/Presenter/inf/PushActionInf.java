package com.bkshare.yngj.sub.push.Presenter.inf;

public interface PushActionInf {
    void recoveryPushState();//重新进入APP后自动回复上一次选择的状态

    void clickFloatBtn();



    void scanTagSuccess(String text);



    void scanTelNumSuccess();

    void selectPackageStyle(int index);

    void selectFactoryType(int index);

    void selectNotification(int index);



    void clickCancalSettingBtn();


    void inputTelNumChange(String tel);

    void postOrder();

    void clickCancalPostOrder();

    void selectTelNum(String s);
}
