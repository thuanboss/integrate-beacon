package com.fis.integratebeaconmodule.business;

import com.sensoro.beacon.kit.Beacon;

import java.util.ArrayList;

public interface BeaconListener {
    void onNewBeacon(Object data);

    void onGoneBeacon(Object data);

    void onUpdateBeacon(Object lstBeacon);
}
