package com.rhemaapp.rhematech.rhemahive.RhemaHiveUsersPackage;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

public class RhemaHiveUserSubClass  {
    View v;
    AppCompatEditText appCompatEditText;

    public RhemaHiveUserSubClass()  {

    }


    /**
     * This method is used to generate regStat based on regCode received regCode -1 = Suspended
     * regCode 0 = Registered
     * regCode 1 = Approved
     *
     * @param regCode
     * @return
     */
    public String genRegStat(int regCode) {
        String regStat;
        switch (regCode) {
            case -1:
                regStat = "Suspended";
                break;
            case 0:
                regStat = "Registered";
                break;
            case 1:
                regStat = "Approved";
                break;
            default:
                regStat = "Invalid";
                break;


        }

        return regStat;
    }

    /**
     * This method is used for checking empty params
     *
     * @param fName
     * @param lName
     * @param brName
     * @param chName
     * @param addr
     * @param city
     * @param dob
     * @param gender
     * @param postalCode
     * @param stat
     * @return
     */
    public int checkEmptyParam(String fName, String lName, String brName, String chName, String addr, String country, String city, String dob, String gender, String postalCode, String stat, String phone,String userType, String pix, String email,String about,String uid) {
        int responseCode = 0;
        try {
            if (TextUtils.isEmpty(fName) && TextUtils.isEmpty(lName) && TextUtils.isEmpty(brName) && TextUtils.isEmpty(chName) && TextUtils.isEmpty(addr) && TextUtils.isEmpty(city) && TextUtils.isEmpty(dob) && TextUtils.isEmpty(gender) && TextUtils.isEmpty(postalCode) && TextUtils.isEmpty(stat) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(country) && TextUtils.isEmpty(userType)  && TextUtils.isEmpty(pix) &&  TextUtils.isEmpty(email)  && TextUtils.isEmpty(about) && TextUtils.isEmpty(uid)) {
                responseCode = -1;
            } else if (TextUtils.isEmpty(fName) || TextUtils.isEmpty(lName) || TextUtils.isEmpty(brName) || TextUtils.isEmpty(chName) || TextUtils.isEmpty(addr) || TextUtils.isEmpty(city) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(gender) || TextUtils.isEmpty(postalCode) && TextUtils.isEmpty(stat) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(country) || TextUtils.isEmpty(userType) || TextUtils.isEmpty(pix) ||  TextUtils.isEmpty(email) ||  TextUtils.isEmpty(about) || TextUtils.isEmpty(uid)) {
                responseCode = -2;
            } else {
                responseCode = 1;
            }
        } catch (NullPointerException nu) {

        }
        return responseCode;
    }


    public int checkEmptyChatsParam(String recevName, String recievePix, String recevStat, String recevId){
        int responseCode = 0;
        if(TextUtils.isEmpty(recevName) && TextUtils.isEmpty(recievePix) && TextUtils.isEmpty(recevStat) && TextUtils.isEmpty(recevId)){
            responseCode = -1;

        }

        if(TextUtils.isEmpty(recevName)  || TextUtils.isEmpty(recievePix) || TextUtils.isEmpty(recevStat) || TextUtils.isEmpty(recevId)){
            responseCode = -2;

        }

        else{
            responseCode =1;
        }

        return responseCode;
    }


    public int checkMessageParams(String userMessage, String messageTimeStamp, String messageStatus, String messageType){
        int messageCode = 0;
        if(TextUtils.isEmpty(userMessage) && TextUtils.isEmpty(messageTimeStamp) && TextUtils.isEmpty(messageStatus) && TextUtils.isEmpty(messageType) ){
            messageCode = -1;
        }
        else if(TextUtils.isEmpty(userMessage) || TextUtils.isEmpty(messageTimeStamp) || TextUtils.isEmpty(messageStatus) || TextUtils.isEmpty(messageType) ){
            messageCode = -2;
        }

        else{
            messageCode = 1;
        }

        return messageCode;

    }

    /**
     * this method is used for retrieving  message status
     * @param messCode
     * @return
     */
    public  String getmessageStatus(int messCode){
        switch (messCode){
            case 0:
                return "Delivered";

            case 1:
                return "Read";
            case -1:
                return "Failed";
            default:
                throw new IllegalStateException("Message error not defined");

        }
    }

    /**
     * this method returns message type
     * @param messTypeCode
     * @return
     */
    public String getMessageType(int messTypeCode){
        switch (messTypeCode){
            case 0:
                return "Personal";
            case 1:
                return "Group";
            case 3:
                return "Sponsored";
            default:
                throw new IllegalStateException("Undefined message type");
        }
    }
}
