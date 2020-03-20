package com.fis.integratebeaconmodule.configs;


public class Constant {
    public static final String BLE_STATE_CHANGED_ACTION = "android.bluetooth.adapter.action.STATE_CHANGED";
    //public static final String API_URL = BuildConfig.API_URL;
    // public static final long EXPIRED_CALL_API = BuildConfig.EXPIRED_CALL_API;
    public static final long EXPIRED_CALL_API = 5000;
    public static final String API_URL = "https://e541d45d.ngrok.io/";
    public static final String TOKEN_KEY = "xxxix adasd";
    public static final String ADD_BROADCAST_KEY = "7b4b5ff594fdaf8f9fc7f2b494e400016f461205";

    public static interface LOG {
        String MAIN = "BEACON_MODULE";
        String RESPONSE_API = "RESPONSE_API";
        String NOTIFICATION = "NOTIFICATION";
        String HANDLER_NEW_BEACON = "HANDLER_NEW_BEACON";
        String URL_TO_BITMAP = "URL_TO_BITMAP";
        String ON_NEW_BEACON = "ON_NEW_BEACON";
        String ON_GONE_BEACON = "ON_GONE_BEACON";
        String ON_UPDATE_BEACON = "ON_UPDATE_BEACON";
    }

    public static interface NOTIFICATION {
        String GROUP_KEY = "com.android.example.integrate-beacon-fis";
        String CHANNEL_ID = "notification-integrate-beacon-fis";
        String CHANNEL_NAME = "beacon notification";
        String CHANNEL_DESCRIPTION = "beacon notification";
    }
}
