package com.bkshare.yngj.sub.push.Presenter;

import com.bkshare.yngj.sub.push.Presenter.inf.PushActionInf;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by zmy_11 on 2017/11/18.
 */

public class PushResultHandler implements  ZXingScannerView.ResultHandler {
    private PushActionInf pushActionInf;
    public void setAction(PushActionInf pushActionInf)
    {
        this.pushActionInf=pushActionInf;
    }

    @Override
    public void handleResult(Result result) {
        pushActionInf.scanTagSuccess(result.getText());
    }


}
