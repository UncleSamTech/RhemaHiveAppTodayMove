package com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class RhemaHiveAutoUtilsClass {

    /**
     * This method is used for starting a new activity
     * @param c
     * @param newClass
     * @return
     */
    public Intent newActivityStarter(Context c, Class newClass){
        Intent intent = new Intent(c, newClass);
        return intent;
    }

    /**
     * This method is used for getting Toast
     * @param c
     * @param message
     * @param len
     * @return
     */
    public Toast getToast(Context c, String message, int len){
        Toast t = Toast.makeText(c,message,len);
        return t;
    }

}
