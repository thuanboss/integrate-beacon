package com.fis.integratebeaconmodule.entity;

import java.io.Serializable;
import java.util.Date;

public class BeaconsNotificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date createdTime;
    private Date updatedTime;
    private Date updatedUser;
    private Date createdUser;
    private Long beaconNotificationId;
    private String name;
    private String content;
    private String link;
    private String imagePath;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(Date updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Date getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Date createdUser) {
        this.createdUser = createdUser;
    }

    public Long getBeaconNotificationId() {
        return beaconNotificationId;
    }

    public void setBeaconNotificationId(Long beaconNotificationId) {
        this.beaconNotificationId = beaconNotificationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
