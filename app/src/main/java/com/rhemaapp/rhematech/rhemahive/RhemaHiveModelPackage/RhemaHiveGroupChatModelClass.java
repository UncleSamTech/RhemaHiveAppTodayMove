package com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage;

public class RhemaHiveGroupChatModelClass {

    public String senderName;
    public String senderMessage;
    public String senderTime;
    public String senderChurch;
    public String senderPics;

    public RhemaHiveGroupChatModelClass(String senderName, String senderMessage, String senderTime, String senderChurch, String senderPics) {
        this.senderName = senderName;
        this.senderMessage = senderMessage;
        this.senderTime = senderTime;
        this.senderChurch = senderChurch;
        this.senderPics = senderPics;
    }

    public RhemaHiveGroupChatModelClass(String senderName, String senderMessage, String senderTime) {
        this.senderName = senderName;
        this.senderMessage = senderMessage;
        this.senderTime = senderTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public String getSenderTime() {
        return senderTime;
    }

    public String getSenderChurch() {
        return senderChurch;
    }

    public String getSenderPics() {
        return senderPics;
    }
}
