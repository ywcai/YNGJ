package ywcai.ls.mobileutil.service;

/**
 * Created by zmy_11 on 2017/10/6.
 */

public interface ServiceControlInf {
    void setTaskProgress(int pos,int max);
    void setTaskCompleteTip(int completeType);
    void regBroadCastForStop();
}
