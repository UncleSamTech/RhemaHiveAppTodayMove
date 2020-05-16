package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveEmailAuthenticationAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveForgotPasswordAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveChurchModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;

import java.util.Collections;
import java.util.List;

public class RhemaHiveDecisionActivity extends AppCompatActivity  {
    Context c = RhemaHiveDecisionActivity.this;
    private AuthUI.IdpConfig emailUi;
    private List<AuthUI.IdpConfig> providers;
    private Bundle bundle;
    private CollectionReference rhemaCollRef;
    private FirebaseFirestore rChurchFireStore;
    private int acctExCode;
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private FirebaseAuth fAuth;
    private String uid;
    private DocumentReference documentReference;
    private String membName;
    private String churchName;
    private String membLName;
    private String mainUid;
    private String membFullName;
    private String userMainId;
    private String suppliedEmail;
    private Intent intent;
    private Intent intentUser;
    private Bundle userBundle;
    private Bundle churchBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhema_hive_decision);
        fAuth = FirebaseAuth.getInstance();
        rChurchFireStore = FirebaseFirestore.getInstance();
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //the NetworkInfo class gets the current state of the device network connection
        networkInfo = connMgr.getActiveNetworkInfo();
        intent = new Intent(c, RhemaHiveChurchPortal.class);
        intentUser = new Intent(c, RhemaHiveUserPortal.class);
        userBundle = new Bundle();
        churchBundle = new Bundle();





        getRadBut(R.id.user_rad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(getRadBut(R.id.user_rad));
            }
        });

        getRadBut(R.id.church_rad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(getRadBut(R.id.church_rad));
            }
        });

    }

    public RadioButton getRadBut(int id){
        RadioButton rad = findViewById(id);
        return rad;
    }












    public void onRadioButtonClicked(RadioButton rad){
        boolean checked = rad.isChecked();
        switch(rad.getId()){
            case R.id.user_rad:
                if(checked){
                getAuto().getToast(c,"User Selected", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    startActivity(getAuto().newActivityStarter(c,RhemaHiveOnboardingPage.class));
                }
                break;
            case R.id.church_rad:
                if(checked){
                getAuto().getToast(c,"Church Selected", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    startActivity(getAuto().newActivityStarter(c, RhemaChurchOnboardingActivity.class));
                }
                break;
            default:
                getAuto().getToast(c, " no selection made yet ",RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                break;


        }
    }

    public RhemaHiveAutoUtilsClass getAuto(){

        RhemaHiveAutoUtilsClass rhemaHiveAutoUtilsClass = RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
        return rhemaHiveAutoUtilsClass;
    }


    public List<AuthUI.IdpConfig> getEmailProvider() {
        providers = Collections.singletonList(getEmailUi());
        return providers;
    }


    public AuthUI.IdpConfig getEmailUi() {
        emailUi = new AuthUI.IdpConfig.EmailBuilder().build();
        return emailUi;
    }








    /**
     * this method returns supplied email
     * @return
     * @throws NullPointerException
     */
    private String returnsuppEmail() throws NullPointerException{
        return suppliedEmail;
    }
}
