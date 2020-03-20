//package com.fis.integratebeaconmodule.services;
//
//import android.app.Service;
//import android.bluetooth.BluetoothAdapter;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.IBinder;
//
//import com.fis.integratebeaconmodule.business.BeaconListener;
//import com.fis.integratebeaconmodule.business.MyBeacon;
//import com.fis.integratebeaconmodule.configs.Constant;
//
//public class MyBeaconService extends Service {
//
//    private MyBeacon myBeacon;
//    private BluetoothBroadcastReceiver bluetoothBroadcastReceiver;
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        myBeacon = new MyBeacon(this, new BeaconListener() {
//            @Override
//            public void onNewBeacon(Object data) {
//
//            }
//
//            @Override
//            public void onGoneBeacon(Object data) {
//
//            }
//
//            @Override
//            public void onUpdateBeacon(Object lstBeacon) {
//
//            }
//        });
//        myBeacon.create();
//        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();
//        registerReceiver(bluetoothBroadcastReceiver, new IntentFilter(Constant.BLE_STATE_CHANGED_ACTION));
//
//        if (myBeacon.isBluetoothEnabled()){
//            myBeacon.startSensoroSDK();
//        } else {
//            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            bluetoothAdapter.enable();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(bluetoothBroadcastReceiver);
//    }
//
//    class BluetoothBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(Constant.BLE_STATE_CHANGED_ACTION)){
//                if (myBeacon.isBluetoothEnabled()){
//                    myBeacon.startSensoroSDK();
//                }
//            }
//        }
//    }
//}
