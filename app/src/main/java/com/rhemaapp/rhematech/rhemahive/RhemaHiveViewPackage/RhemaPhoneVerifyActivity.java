package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaFirebasePackage.RhemaHiveFirebaseConnection;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUsersPackage.RhemaHiveUserSuperClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class RhemaPhoneVerifyActivity extends AppCompatActivity {
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks;
    Toast toast;
    Context c = RhemaPhoneVerifyActivity.this;
    String verifyId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    RhemaHiveUserSuperClass userSuperClass;
    CountryCodePicker ccp;
    Country countryLab;
    AppCompatEditText appCompatEditText;
    EditText edt;
    Button but_ver_sms;
    Button butt_ver_code;
    FirebaseAuth mAuth;
    FirebaseUser user;
    RhemaHiveFirebaseConnection rhemaHiveFirebaseConnection;
    String phoneNum;
    String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhema_phone_verify);
        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();
      appCompatEditText =  getEdt(R.id.phone_number_edt);
        getCcp(R.id.ccp).registerPhoneNumberTextView(appCompatEditText);
        getEdit(R.id.code_edt).setVisibility(View.GONE);
        getButt(R.id.btn_ver_code).setVisibility(View.GONE);
        getCcp(R.id.ccp).setVisibility(View.VISIBLE);
        getButt(R.id.button_verify_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyNumber(getFullNumber(getSelectedCountryCode(),appCompatEditText.getText().toString().trim()),RhemaHiveClassReferenceConstants.TIME_DUR);
            }
        });




    }




    /**
     * This method is used to get the OnVerificationStateChangedCallbacks
     *
     * @return
     */
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks getOnVerificationStateChangedCallbacks() {
        onVerificationStateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull final PhoneAuthCredential phoneAuthCredential) {

                getButt(R.id.btn_ver_code).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            signInWithPhoneAuthCredential(phoneAuthCredential);
                    }
                });


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

                if (e instanceof FirebaseTooManyRequestsException) {
                    getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_EXCLAMATION_LIMIT_REACH, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mResendToken = forceResendingToken;
                verifyId = s;

               appCompatEditText = getEdt(R.id.phone_number_edt);
               edt = getEdit(R.id.code_edt);
                but_ver_sms = getButt(R.id.button_verify_sms);
               butt_ver_code = getButt(R.id.btn_ver_code);
               appCompatEditText.setVisibility(View.GONE);
               but_ver_sms.setVisibility(View.GONE);
                edt.setVisibility(View.VISIBLE);
                getButt(R.id.btn_ver_code).setVisibility(View.VISIBLE);
                getCcp(R.id.ccp).setVisibility(View.GONE);



            }


        };
        return onVerificationStateChangedCallbacks;
    }

    /**
     * This method is used to verify phone number
     *
     * @param phoneNumber
     * @param timeOutDur
     */
    public void verifyNumber(String phoneNumber, long timeOutDur) {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, timeOutDur, TimeUnit.SECONDS, this, getOnVerificationStateChangedCallbacks());
    }

    public CountryCodePicker getCcp(int id){
        ccp = findViewById(id);
        return ccp;
    }

    public AppCompatEditText getEdt(int id){
        appCompatEditText = findViewById(id);
        return appCompatEditText;
    }

    /**
     * This method is used to move user in to onboarding screen
     */
    public void moveToOnboard() {

        RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass().newActivityStarter(c, RhemaHiveOnboardingPage.class);
    }



    public RhemaHiveUserSuperClass gUserSuperClass(){
        userSuperClass = RhemaHiveInstanceManagerClass.getRhemaHiveUserSuperClass();
        return userSuperClass;
    }

    public String getSelectedCountryCode(){
        String selCode = getCcp(R.id.ccp).getDefaultCountryCodeWithPlus();
        getToast(c,"Code for selected country is : " + selCode,Toast.LENGTH_SHORT).show();
        return selCode;
    }

    public String getFullNumber(String cCode, String phone){
        String fullNumber = cCode + phone;
        getToast(c,"Full Number is : " + fullNumber, Toast.LENGTH_SHORT).show();
        return fullNumber;
    }

    public Button getButt(int id){
        Button but = findViewById(id);
        return but;
    }

    public EditText getEdit(int id){
        EditText edt = findViewById(id);
        return edt;
    }

    public void resendVerCode(){}

    public Toast getToast(Context c, String message, int dur){
        Toast t = Toast.makeText(c, message,dur);
        return t;
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        getConn().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            getToast(c, RhemaHiveClassReferenceConstants.PHONE_VERIFICATION_MESSAGE_SUCESS, Toast.LENGTH_SHORT).show();
                            try{
                             user = task.getResult().getUser();


                            getToast(c,"Verified Number is " + user.getPhoneNumber(),Toast.LENGTH_SHORT).show();
                            moveToOnboard();
                            }
                            catch(NullPointerException n){
                                getToast(c,"error as a result of :  " + n.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }

                            }
                     else {
                            // Sign in failed, display a message and update the UI
                            getToast(c,"Code Invalid",Toast.LENGTH_SHORT);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                getToast(c,"Invalid code entered",Toast.LENGTH_SHORT).show();
                                // The verification code entered was invalid
                            }
                        }
                    }
                });

    }

    public FirebaseAuth getConn(){
        mAuth = RhemaHiveInstanceManagerClass.getHiveFirebaseConnection().getRhemaFirebaseAuth();
        return mAuth;

    }

    public String getVerNumber(){
        try{user = FirebaseAuth.getInstance().getCurrentUser();
        phoneNum = user.getPhoneNumber();
        }
        catch (NullPointerException np){
            getToast(c," Error as a result of " + np.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        return phoneNum;
    }

    public String getCountry(){
        try{
            country = getCcp(R.id.ccp).getSelectedCountryName();
            //getCcp(R.id.ccp).cou

        }
        catch (NullPointerException np){
            getToast(c," Error as a result of " + np.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        }
        return country;

    }




}
