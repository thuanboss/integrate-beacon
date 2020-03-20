package com.fis.integratebeaconmodule;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

//import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.fis.integratebeaconmodule.business.BeaconListener;
import com.fis.integratebeaconmodule.business.MyBeacon;
import com.fis.integratebeaconmodule.configs.Constant;

public class DetectBeacon {
    private BroadcastReceiver bluetoothBroadcastReceiver;
    private MyBeacon myBeacon = null;

    public void init(Context context, BeaconListener beaconListener) {

        myBeacon = new MyBeacon(context, beaconListener);
        myBeacon.create();
//                bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();

        try {
            final MyBeacon beacon = myBeacon;

            bluetoothBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    if (intent.getAction().equals(Constant.BLE_STATE_CHANGED_ACTION)) {

                        if (beacon.isBluetoothEnabled()) {
                            beacon.startSensoroSDK();
                        }
                    }
                }
            };
        } catch (Exception e) {
            Log.e(Constant.LOG.MAIN, e.getMessage());
        }
//        return myBeacon;
    }

    public void getNotification(String beaconCode, String phoneNumber) {

    }

    public BroadcastReceiver getBluetoothBroadcastReceiver() {
        return this.bluetoothBroadcastReceiver;
    }

    public boolean isBluetoothEnabled() {
        return myBeacon.isBluetoothEnabled();
    }

    public boolean startSensoroSDK() {
        return this.myBeacon.startSensoroSDK();
    }
}
