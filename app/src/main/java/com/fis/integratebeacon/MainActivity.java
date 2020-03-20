package com.fis.integratebeacon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.fis.integratebeacon.business.NotifyRequest;
import com.fis.integratebeacon.configs.Constant;
import com.fis.integratebeacon.entity.NotificationParamEntity;
import com.fis.integratebeaconmodule.DetectBeacon;
import com.fis.integratebeaconmodule.business.BeaconListener;
import com.fis.integratebeaconmodule.entity.BeaconEntity;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_NOTIFICATION_CODE = 123;
    private static final int PERMISSION_REQUEST_LOCATION_CODE = 124;
    private static final int PERMISSION_REQUEST_BLUETOOTH_CODE = 125;

    private Context context = this;
    private DetectBeacon detectBeacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestNotificationPermission();
        requestBluetoothPermission();
        requestLocationPermission();
        startApp();
    }

    void startApp() {
        try {
            if (isGranted() && detectBeacon == null) {
                detectBeacon = new DetectBeacon();
                detectBeacon.init(this, new BeaconListener() {

                    @Override
                    public void onNewBeacon(Object data) {
                        System.out.println("onNewBeacon" + data.toString());

                        BeaconEntity beacon = (BeaconEntity) data;
                        NotificationParamEntity param = new NotificationParamEntity();
                        param.setBeaconCode(beacon.getSerialNumber());
                        param.setPhoneNumber("0965383013");
                        NotifyRequest notifyRequest = new NotifyRequest(context);
                        notifyRequest.execute(param);
                    }

                    @Override
                    public void onGoneBeacon(Object data) {
                        System.out.println("onGoneBeacon" + data.toString());
                    }

                    @Override
                    public void onUpdateBeacon(Object lstBeacon) {

                    }
                });
                //LocalBroadcastManager.getInstance(mContext).registerReceiver(beaconUtil.bluetoothBroadcastReceiver, new IntentFilter(Constant.BLE_STATE_CHANGED_ACTION));
                registerReceiver(detectBeacon.getBluetoothBroadcastReceiver(), new IntentFilter(Constant.BLE_STATE_CHANGED_ACTION));
                detectBeacon.startSensoroSDK();
                /* init */
//                if (detectBeacon.isBluetoothEnabled()) {
//                } else {
                    /**
                     * Enable bluetooth by user permission.
                     */
//                    Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(bluetoothIntent, PERMISSION_REQUEST_BLUETOOTH_CODE);

//                    /**
//                     * Enable bluetooth in background.
//                     */
//                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//                    bluetoothAdapter.enable();
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, PERMISSION_NOTIFICATION_CODE);
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context)
                        .setTitle("Bật vị trí ?")
                        .setMessage("Ứng dụng yêu cầu vị trí!")
                        .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION_CODE);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION_CODE);
            }
        }
    }

    private void requestBluetoothPermission() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter != null) {
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothIntent, PERMISSION_REQUEST_BLUETOOTH_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PERMISSION_REQUEST_BLUETOOTH_CODE) {

            if (resultCode == RESULT_OK) {
                startApp();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_LOCATION_CODE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // location-related task you need to do.
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    startApp();
                }

            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        } else if (requestCode == PERMISSION_NOTIFICATION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startApp();
            } else {
            }
        }
    }

    private boolean isGranted() {
        boolean bluetooth = false;
        boolean notification = false;
        boolean location = false;

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            bluetooth = true;
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = true;
        }

        if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            notification = true;
        }

        return bluetooth && notification && location;
    }
}
