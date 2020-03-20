//package com.fis.integratebeaconmodule.business;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
//import com.fis.integratebeaconmodule.services.MyBeaconService;
//
//public class MyBeaconBroadcastReceiver extends BroadcastReceiver {
//
//    static final String MY_BEACON_ACTION = "android.intent.action.MY_BEACON_ACTION";
//
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(MY_BEACON_ACTION)) {
//
//            /**
//             * Start Sensoro SDK in Activity with boot.
//             */
////            Intent bootCompletedActivityIntent = new Intent(context, MainActivity.class);
////            bootCompletedActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            context.startActivity(bootCompletedActivityIntent);
//
//            /**
//             * Startan Sensoro SDK in Service with boot.
//             */
//            Intent bootCompletedSerivceIntent = new Intent(context, MyBeaconService.class);
//            context.startService(bootCompletedSerivceIntent);
//        }
//    }
//
//
//}
