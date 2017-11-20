package com.bkshare.yngj.global.cfg;

import com.bkshare.yngj.R;

/**
 * Created by zmy_11 on 2017/10/6.
 */

public class AppConfig {
    public static final String MAIN_ACTIVITY_PATH = "/main/view/MainActivity";
    public static final String LOGIN_ACTIVITY_PATH = "/identity/view/LoginActivity";
    public static final int MAIN_MENU_SIZE = 5;
    public static final String PUSH_ACTIVITY_PATH = "/sub/push/view/PushActivity";
    public static final String PULL_ACTIVITY_PATH = "/sub/pull/view/PullActivity";
    public static final String FIND_ACTIVITY_PATH = "/sub/find/view/FindActivity";
    public static final String SEND_ACTIVITY_PATH = "/sub/send/view/SendActivity";
    public static final String USER_ACTIVITY_PATH = "/sub/user/view/UserActivity";

    public static final String TITLE_PUSH = "包裹入库";
    public static final String TITLE_PULL = "包裹出库";
    public static final String TITLE_FIND = "包裹查询";
    public static final String TITLE_SEND = "寄件入库";
    public static final String TITLE_USER = "用户管理";

    public static final int INDEX_PUSH = 0;
    public static final int INDEX_PULL = 1;
    public static final int INDEX_FIND = 2;
    public static final int INDEX_SEND = 3;
    public static final int INDEX_USER = 4;


    public static final String[] strCompanyName = new String[]{
            "EMS",
            "顺丰",
            "圆通",
            "韵达",
            "中通",
            "其他"
    };
    public static final String[] strOrderStyle = new String[]{
            "长方形",
            "正方形",
            "大箱子",
            "小袋子",
            "大袋子",
            "其它"
    };
    public static final String[] strNotifyContent = new String[]{
            "生鲜，速拿",
            "贵重物品",
            "你有一个宝贝",
            "其它"
    };
    public static final int[] INTS_CHANNEL_5G = new int[]{149, 153, 157, 161, 165};

    public static final int[] colors = new int[]{
            R.color.chartLineColor0,
            R.color.chartLineColor1,
            R.color.chartLineColor2,
            R.color.chartLineColor3,
            R.color.chartLineColor4,
            R.color.chartLineColor5,
            R.color.chartLineColor6,
            R.color.chartLineColor7,
            R.color.chartLineColor8,
            R.color.chartLineColor9,
            R.color.chartLineColor10,
            R.color.chartLineColor11,
            R.color.chartLineColor12
    };

    public static String[] getMenuTextStr() {
        String[] temp = new String[MAIN_MENU_SIZE];
        for (int i = 0; i < temp.length; i++) {
            switch (i) {
                case INDEX_PUSH:
                    temp[i] = TITLE_PUSH;
                    break;
                case INDEX_PULL:
                    temp[i] = TITLE_PULL;
                    break;
                case INDEX_FIND:
                    temp[i] = TITLE_FIND;
                    break;
                case INDEX_SEND:
                    temp[i] = TITLE_SEND;
                    break;
                case INDEX_USER:
                    temp[i] = TITLE_USER;
                    break;
//                case INDEX_BLE:
//                    temp[i] = TITLE_BLE;
//                    break;
//                case INDEX_LAN:
//                    temp[i] = TITLE_LAN;
//                    break;
//                case INDEX_PORT:
//                    temp[i] = TITLE_PORT;
//                    break;
//                case INDEX_GPS:
//                    temp[i] = TITLE_GPS;
//                    break;
//                case INDEX_SENSOR:
//                    temp[i] = TITLE_SENSOR;
//                    break;
//                case INDEX_SPEED:
//                    temp[i] = TITLE_SPEED;
//                    break;
//                case INDEX_ORIENTATION:
//                    temp[i] = TITLE_ORIENTATION;
//                    break;
            }
        }
        return temp;
    }


    public static String[] getMenuUrlStr() {
        String[] temp = new String[MAIN_MENU_SIZE];
        for (int i = 0; i < temp.length; i++) {
            switch (i) {
                case INDEX_PUSH:
                    temp[i] = PUSH_ACTIVITY_PATH;
                    break;
                case INDEX_PULL:
                    temp[i] = PULL_ACTIVITY_PATH;
                    break;
                case INDEX_FIND:
                    temp[i] = FIND_ACTIVITY_PATH;
                    break;
                case INDEX_SEND:
                    temp[i] = SEND_ACTIVITY_PATH;
                    break;
                case INDEX_USER:
                    temp[i] = USER_ACTIVITY_PATH;
                    break;
//                case INDEX_BLE:
//                    temp[i] = BLE_ACTIVITY_PATH;
//                    break;
//                case INDEX_LAN:
//                    temp[i] = LAN_ACTIVITY_PATH;
//                    break;
//                case INDEX_PORT:
//                    temp[i] = PORT_ACTIVITY_PATH;
//                    break;
//                case INDEX_GPS:
//                    temp[i] = GPS_ACTIVITY_PATH;
//                    break;
//                case INDEX_SENSOR:
//                    temp[i] = SENSOR_ACTIVITY_PATH;
//                    break;
//                case INDEX_SPEED:
//                    temp[i] = SPEED_ACTIVITY_PATH;
//                    break;
//                case INDEX_ORIENTATION:
//                    temp[i] = ORIENTATION_ACTIVITY_PATH;
//                    break;
            }
        }
        return temp;
    }

    public static int[] getMenuIconRes() {
        int[] temp = new int[MAIN_MENU_SIZE];
        for (int i = 0; i < temp.length; i++) {
            switch (i) {
                case INDEX_PUSH:
                    temp[i] = R.drawable.homepage_menu_ping;
                    break;
                case INDEX_PULL:
                    temp[i] = R.drawable.homepage_menu_wifi;
                    break;
                case INDEX_FIND:
                    temp[i] = R.drawable.homepage_menu_station;
                    break;
                case INDEX_SEND:
                    temp[i] = R.drawable.homepage_menu_station;
                    break;
                case INDEX_USER:
                    temp[i] = R.drawable.homepage_menu_station;
                    break;
//                case INDEX_BLE:
//                    temp[i] = R.drawable.homepage_menu_ble;
//                    break;
//                case INDEX_LAN:
//                    temp[i] = R.drawable.homepage_menu_lan;
//                    break;
//                case INDEX_PORT:
//                    temp[i] = R.drawable.homepage_menu_more;
//                    break;
//                case INDEX_GPS:
//                    temp[i] = R.drawable.homepage_menu_gps;
//                    break;
//                case INDEX_SENSOR:
//                    temp[i] = R.drawable.homepage_menu_sensor;
//                    break;
//                case INDEX_SPEED:
//                    temp[i] = R.drawable.homepage_menu_orientation;
//                    break;
//                case INDEX_ORIENTATION:
//                    temp[i] = R.drawable.homepage_menu_orientation;
//                    break;
            }
        }
        return temp;
    }

}

