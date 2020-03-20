package com.fis.integratebeaconmodule.entity;

public class BeaconEntity {
    private String serialNumber;
    private Integer major;
    private Integer minor;
    private String eddystoneUID;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public String getEddystoneUID() {
        return eddystoneUID;
    }

    public void setEddystoneUID(String eddystoneUID) {
        this.eddystoneUID = eddystoneUID;
    }
}
