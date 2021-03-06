package com.bkshare.yngj.global.util.statics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.bkshare.yngj.global.model.instance.MainApplication;

/**
 * Created by zmy_11 on 2017/10/16.
 */

public class InStallService {

    public static  void bindService(Context context,Class service, ServiceConnection sc) {
        Intent i = new Intent(context,service);
        context
                .bindService(i, sc, Context.BIND_AUTO_CREATE);
    }
    public static void unbindService(Context context,Class service,ServiceConnection sc) {
        Intent i = new Intent(context,service);
        context
                .unbindService(sc);
    }
    public static  void bindService(Class service, ServiceConnection sc) {
        Intent i = new Intent(MainApplication.getInstance().getApplicationContext(),service);
        MainApplication.getInstance().getApplicationContext()
                .bindService(i, sc, Context.BIND_AUTO_CREATE);
    }
    public static void unbindService(Class service,ServiceConnection sc) {
        Intent i = new Intent(MainApplication.getInstance().getApplicationContext(),service);
        MainApplication.getInstance().getApplicationContext()
                .unbindService(sc);
    }


    public static void waitService(Service service) {
        int i = 0;
        while (service == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            if (i >= 500) {
                //避免service为空，如果一直没获取到就不启动任务。默认刷新10秒钟
                return;
            }
        }
    }
}
