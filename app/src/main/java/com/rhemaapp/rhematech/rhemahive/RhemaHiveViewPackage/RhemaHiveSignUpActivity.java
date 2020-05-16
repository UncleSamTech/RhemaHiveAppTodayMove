package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaFirebasePackage.RhemaHiveFirebaseConnection;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;

public class RhemaHiveSignUpActivity extends AppCompatActivity {
    private Context c = RhemaHiveSignUpActivity.this;
    private int responseCode;
    private FirebaseUser user;

    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private ProgressBar pBar;
    private FirebaseAuth fAuth;
    private Button btn;
    private AppCompatEditText emailEdt;
    private AppCompatEditText passEdt;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhema_hive_sign_up);
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //the NetworkInfo class gets the current state of the device network connection
        networkInfo = connMgr.getActiveNetworkInfo();
        textView = getTv(R.id.sign_inreg_lab);
        fAuth = FirebaseAuth.getInstance();
        pBar = getBar(R.id.pBar);
        pBar.setVisibility(RhemaHiveClassReferenceConstants.VIEW_GONE);
        btn = getButton(R.id.sign_upreg_btn);
        emailEdt = getAppEdit(R.id.email_sup_app_edt);
        passEdt = getAppEdit(R.id.pass_sup_app_edt);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(getAuto().newActivityStarter(c,RhemaHiveAuthenticateActivity.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    signUpUser(emailEdt.getText().toString().trim(), passEdt.getText().toString());
                } catch (NullPointerException np) {
                    getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            }
        });


    }

    private AppCompatEditText getAppEdit(int id) {
        AppCompatEditText appEdit = findViewById(id);
        return appEdit;
    }

    private Button getButton(int id) {
        Button btn = findViewById(id);
        return btn;
    }

    private RhemaHiveAutoUtilsClass getAuto() {
        RhemaHiveAutoUtilsClass rAuto = RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
        return rAuto;
    }

    private void signUpUser(String email, String password) throws NullPointerException {
        if (checkFilledData(email, password) == 1) {


            if (networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {

                pBar.setVisibility(RhemaHiveClassReferenceConstants.VIEW_VIS);
                getButton(R.id.sign_upreg_btn).setClickable(false);
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            getButton(R.id.sign_upreg_btn).setClickable(true);
                            pBar.setVisibility(RhemaHiveClassReferenceConstants.VIEW_GONE);

                            getAuto().getToast(c,"Account Created Successfully", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            startActivity(getAuto().newActivityStarter(c,RhemaHiveAuthenticateActivity.class));
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        getAuto().getToast(c, " Please verify email to continue.. Email have been sent to : " + fAuth.getCurrentUser().getEmail(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                                    } else {
                                        getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                                    }
                                }
                            });
                        } else {
                            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        }
                    }
                });


            } else {
                getAuto().getToast(c, "Oops ! ! No Internet Seen on Phone ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }

        } else {
            getAuto().getToast(c, "Oops ! !..Sorry you need to fill data properly", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
    }


    private int checkFilledData(String email, String password) {
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            responseCode = -1;
            getAuto().getToast(c, " Oops ! !..Email  and Password are empty", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        } else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            responseCode = 0;
            getAuto().getToast(c, " Oops ! !..Email  and Password are empty", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        } else {
            responseCode = 1;
        }

        return responseCode;
    }

    private ProgressBar getBar(int id) {
        ProgressBar pBar = findViewById(id);
        return pBar;
    }

    private FirebaseUser getFbConn() {
        user = RhemaHiveInstanceManagerClass.getHiveFirebaseConnection().getUser();
        return user;
    }

    private TextView getTv(int id){
        TextView tv = findViewById(id);
        return tv;
    }

}
