package com.fis.integratebeaconmodule.business;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fis.integratebeaconmodule.configs.Constant;
import com.fis.integratebeaconmodule.entity.BeaconEntity;
import com.sensoro.beacon.kit.Beacon;
import com.sensoro.beacon.kit.BeaconManagerListener;
import com.sensoro.cloud.SensoroManager;

import java.util.ArrayList;

public class MyBeacon implements BeaconManagerListener {

    private static final String TAG = MyBeacon.class.getSimpleName();
    private SensoroManager sensoroManager;
    private BeaconListener beaconListener;
    private Context context;

    public MyBeacon(Context context, BeaconListener beaconListener) {
        this.context = context;
        this.beaconListener = beaconListener;
    }

    public void create() {
        initSensoroSDK();
        /**
         * Start SDK in Service.
         */
        Intent intent = new Intent();
        intent.setClass(context, MyBeacon.class);
        context.startService(intent);
    }

    /**
     * Check whether bluetooth enabled.
     *
     * @return
     */
    public boolean isBluetoothEnabled() {
        return sensoroManager.isBluetoothEnabled();
    }

    /**
     * Start Sensoro SDK.
     */
    public boolean startSensoroSDK() {
        try {
            sensoroManager.startService();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Initial Sensoro SDK.
     */
    private void initSensoroSDK() {
        try {
            sensoroManager = SensoroManager.getInstance(context);
            sensoroManager.setCloudServiceEnable(true);
            sensoroManager.addBroadcastKey(Constant.ADD_BROADCAST_KEY);
            sensoroManager.setBeaconManagerListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Sự kiện phát hiện ra 1 beacon mới */
    @Override
    public void onNewBeacon(Beacon beacon) {
        Log.i(Constant.LOG.ON_NEW_BEACON, beacon.toString());
//        BeaconNotificationService service = new BeaconNotificationService(context);
//        service.execute(beacon);
        if (beaconListener != null) {
            BeaconEntity beaconEntity = new BeaconEntity();
            beaconEntity.setSerialNumber(beacon.getSerialNumber());
            beaconEntity.setMajor(beacon.getMajor());
            beaconEntity.setMinor(beacon.getMinor());
            beaconEntity.setEddystoneUID(beacon.getEddystoneUID());
            beaconListener.onNewBeacon(beaconEntity);
        }
    }

    /* sự kiện rời khỏi phạm vi 1 beacon */
    @Override
    public void onGoneBeacon(Beacon beacon) {
        Log.i(Constant.LOG.ON_GONE_BEACON, beacon.toString());

        if (beaconListener != null) {
            BeaconEntity beaconEntity = new BeaconEntity();
            beaconEntity.setSerialNumber(beacon.getSerialNumber());
            beaconEntity.setMajor(beacon.getMajor());
            beaconEntity.setMinor(beacon.getMinor());
            beaconEntity.setEddystoneUID(beacon.getEddystoneUID());
            beaconListener.onGoneBeacon(beaconEntity);
        }
    }

    /* có bất kì thay đổi nào sẽ chạy hàm này */
    @Override
    public void onUpdateBeacon(ArrayList<Beacon> lstBeacon) {
        beaconListener.onUpdateBeacon(lstBeacon);
    }
}
