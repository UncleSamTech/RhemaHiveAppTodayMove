package com.rhemaapp.rhematech.rhemahive.RhemaHiveUsersPackage;

import android.text.TextUtils;

import org.w3c.dom.Text;

public class RhemaHiveChurchSubClass {
    String regStat;
    int responseCode;

    public RhemaHiveChurchSubClass() {

    }

    /**
     * This method is used to generate regStat based on regCode received regCode -1 = Suspended
     * regCode 0 = Registered
     * regCode 1 = Approved
     *
     * @param regCode
     * @return
     */
    public String genChurchRegStat(int regCode) {

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


public int checkChurchEmptyParams(String church_logg, String church_name, String church_address, String church_phone, String church_email, String church_leadname, String churchDescr, String church_reg_stat , String uid,String user_type){

if(TextUtils.isEmpty(church_logg) && TextUtils.isEmpty(church_name) && TextUtils.isEmpty(church_address) && TextUtils.isEmpty(church_phone) && TextUtils.isEmpty(church_email) && TextUtils.isEmpty(church_leadname) && TextUtils.isEmpty(churchDescr) && TextUtils.isEmpty(church_reg_stat) && TextUtils.isEmpty(uid) && TextUtils.isEmpty(user_type)){

    responseCode = -1;
}

else if(TextUtils.isEmpty(church_logg) || TextUtils.isEmpty(church_name) || TextUtils.isEmpty(church_address)  || TextUtils.isEmpty(church_phone) || TextUtils.isEmpty(church_email) || TextUtils.isEmpty(church_leadname) || TextUtils.isEmpty(churchDescr) || TextUtils.isEmpty(church_reg_stat) || TextUtils.isEmpty(uid) || TextUtils.isEmpty(user_type)){

    responseCode = -2;
}
else{

    responseCode = 1;
}

return responseCode;
}

}
