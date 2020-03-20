package com.fis.integratebeaconmodule.services;

        import android.content.Context;
        import android.content.SharedPreferences;

        import java.util.Date;

public class BeaconSharedPreferences {
    private SharedPreferences beaconSharedPreferences;

    public BeaconSharedPreferences(Context context) {
        beaconSharedPreferences = context.getSharedPreferences("beacon_storage", context.MODE_PRIVATE);
    }

    public Long getExpiredCallApi() {
        return beaconSharedPreferences.getLong("expiredCallApi", new Date().getTime());
    }

    public void setExpiredCallApi(Long time) {
        SharedPreferences.Editor editor = beaconSharedPreferences.edit();
        editor.putLong("expiredCallApi", new Date().getTime() + time);
        editor.apply();
    }
}
