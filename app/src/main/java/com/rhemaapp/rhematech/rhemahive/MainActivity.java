package com.rhemaapp.rhematech.rhemahive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveThreadUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.RhemaHiveAuthenticateActivity;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.RhemaHiveDecisionActivity;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.RhemaHiveOnboardingPage;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.RhemaHiveSwipeViewActivity;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.RhemaHiveTermsCondition;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.RhemaPhoneVerifyActivity;

public class MainActivity extends AppCompatActivity {
    RhemaHiveThreadUtilsClass rhThread;
    Context c = MainActivity.this;
    int status = 0 ;
    Intent intent;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            getSupportActionBar().hide();
        }catch(NullPointerException np){
            getRhemaHiveAutoUtilsClass().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC,RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
        executeRhemaDelayThread(c, RhemaHiveTermsCondition.class);
        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();



    }


    public RhemaHiveAutoUtilsClass getRhemaHiveAutoUtilsClass(){
        RhemaHiveAutoUtilsClass rhemaHiveAutoUtilsClass = RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
        return rhemaHiveAutoUtilsClass;
    }

    /**
     * This method holds a singleton instance of RhemaThreadUtilsClass
     * @return
     */
    public RhemaHiveThreadUtilsClass getThread(){
        rhThread = RhemaHiveInstanceManagerClass.getRhemaHiveThreadUtilsClass();
    return rhThread;
    }

    /**
     * This method is used to run a delayed thread on any activity it is called on
     * @param context
     * @param classs
     * @return
     */
    public void executeRhemaDelayThread(final Context context, final Class classs){

        new Thread(new Runnable() {
            public void run() {
                while (status < 100) {
                    status += 5;

                    try {

                        Thread.sleep(200);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                checkUserVerified();
                //startActivity(new Intent(context, classs));

            }
        }).start();


    }

    public void checkUserVerified(){
        if(mAuth.getCurrentUser()!=null){
           startActivity(getRhemaHiveAutoUtilsClass().newActivityStarter(c, RhemaHiveAuthenticateActivity.class));
        }
        else{
            startActivity(getRhemaHiveAutoUtilsClass().newActivityStarter(c,RhemaHiveTermsCondition.class));
        }
    }



}
