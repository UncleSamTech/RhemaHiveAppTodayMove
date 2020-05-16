package com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage;


public class RhemaHiveUserMessageModelClass {

    public String receiverName;
    public String receiverMessage;
    public String receiverPix;
    public String senderName;

    public String senderPix;
    public String userMessage;
    public String messageTimeStamp;
    public String receiveMessTime;
    public String receiverStat;
    public boolean isMessageStarred;
    public String messageStatus;
    public String messageType;
    public String senderId;
    public String receiverId;
    public int VIEW_TYPE;
    public static  int SEND_MESS_ID = 1;
    public static int REC_MESS_ID = 2;




    public RhemaHiveUserMessageModelClass(String receiverName, String receiverPix, String receiverStat, String receiverId) {
        this.receiverName = receiverName;
        this.receiverPix = receiverPix;
        this.receiverStat = receiverStat;
        this.receiverId = receiverId;
    }

    public RhemaHiveUserMessageModelClass(String receiverMessage, String receiveMessTime, String receiverPix) {
        this.receiverMessage = receiverMessage;
        this.receiveMessTime = receiveMessTime;
        this.receiverPix = receiverPix;
    }

    public RhemaHiveUserMessageModelClass() {
    }

    public RhemaHiveUserMessageModelClass(String userMessage, String messageTimeStamp, int VIEW_TYPE) {
        this.userMessage = userMessage;
        this.messageTimeStamp = messageTimeStamp;
        this.VIEW_TYPE = VIEW_TYPE;
    }

    public RhemaHiveUserMessageModelClass(String userMessage, String messageTimeStamp, boolean isMessageStarred, String messageStatus, String messageType) {
        this.userMessage = userMessage;
        this.messageTimeStamp = messageTimeStamp;
        this.isMessageStarred = isMessageStarred;
        this.messageStatus = messageStatus;
        this.messageType = messageType;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMessage() {
        return receiverMessage;
    }

    public void setReceiverMessage(String receiverMessage) {
        this.receiverMessage = receiverMessage;
    }

    public int getVIEW_TYPE() {
        return VIEW_TYPE;
    }

    public String getReceiverPix() {
        return receiverPix;
    }

    public void setReceiverPix(String receiverPix) {
        this.receiverPix = receiverPix;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPix() {
        return senderPix;
    }

    public void setSenderPix(String senderPix) {
        this.senderPix = senderPix;
    }

    public String getSenderMessage() {
        return userMessage;
    }

    public void setSenderMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getSendMessTime() {
        return messageTimeStamp;
    }

    public void setSendMessTime(String messageTimeStamp) {
        this.messageTimeStamp = messageTimeStamp;
    }

    public String getReceiveMessTime() {
        return receiveMessTime;
    }

    public void setReceiveMessTime(String receiveMessTime) {
        this.receiveMessTime = receiveMessTime;
    }

    public String getReceiverStat() {
        return receiverStat;
    }

    public void setReceiverStat(String receiverStat) {
        this.receiverStat = receiverStat;
    }

    public boolean isMessStarred() {
        return isMessageStarred;
    }

    public void setMessStarred(boolean isMessageStarred) {
        isMessageStarred = isMessageStarred;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
