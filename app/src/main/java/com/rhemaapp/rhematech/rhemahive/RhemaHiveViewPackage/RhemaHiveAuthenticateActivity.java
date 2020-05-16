package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaFirebasePackage.RhemaHiveFirebaseConnection;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveForgotPasswordAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RhemaHiveAuthenticateActivity extends AppCompatActivity implements RhemaHiveForgotPasswordAdapter.RhemaHiveForgotPasswordListener {
    private List<AuthUI.IdpConfig> providers;
    private AuthUI.IdpConfig fbUi;
    private AuthUI.IdpConfig twitUi;
    private AuthUI.IdpConfig emailUi;
    private AuthUI.IdpConfig gogUi;
    private AuthUI.IdpConfig phoneUi;
    RhemaHiveFirebaseConnection user;
    Context c = RhemaHiveAuthenticateActivity.this;
    ImageButton twitButton;
    ImageButton fbImg;
    private Bundle ui_bund;
    ImageButton googBut;
    private static ActionCodeSettings actSettings;
    private FirebaseUser f_user;
    private FirebaseAuth fAuth;
    Uri dynamicUri;
    private int responseCode;
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private ProgressDialog progressDialog;
    private AppCompatEditText emailEdt;
    private AppCompatEditText passEdt;
    private TextView tView;
    private TextView forPassTv;
    private String uuid;
    private CollectionReference rhemaCollRef;
    private FirebaseFirestore rChurchFireStore;
    private int acctExCode;
    private Intent userIntent;
    private Intent churchIntent;
    private Intent userOnboardIntent;
    private Intent churchOnboardIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhema_hive_authenticate);
        rChurchFireStore = FirebaseFirestore.getInstance();
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //the NetworkInfo class gets the current state of the device network connection
        networkInfo = connMgr.getActiveNetworkInfo();
        fAuth = FirebaseAuth.getInstance();
        tView = getTv(R.id.sign_up_lab);
        forPassTv = getTv(R.id.forget_pass_label);
        forPassTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        tView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getAuto().newActivityStarter(c,RhemaHiveSignUpActivity.class));
            }
        });
        emailEdt = getAppEdt(R.id.email_app_edt_log);
        passEdt = getAppEdt(R.id.pass_app_edt_log);
        getBut(R.id.sign_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    signInUser(emailEdt.getText().toString().trim(),passEdt.getText().toString().trim());
                }
                catch(NullPointerException np){
                    getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            }
        });

        progressDialog = new ProgressDialog(c);
        twitButton = getImgBut(R.id.twit_ui);
        googBut = getImgBut(R.id.goog_ui);
        fbImg = getImgBut(R.id.fb_ui);
        ui_bund = new Bundle();
        userIntent = new Intent(c, RhemaHiveUserPortal.class);
        churchIntent = new Intent(c,RhemaHiveChurchPortal.class);
        userOnboardIntent = new Intent(c, RhemaHiveOnboardingPage.class);
        churchOnboardIntent = new Intent(c,RhemaChurchOnboardingActivity.class);

        twitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTwitAuth();


            }
        });


        fbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFbAuth();


            }
        });

        googBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGoogAuth();
            }
        });

    }

    /**
     * This method is used to return facebook UI
     *
     * @return
     */
    public List<AuthUI.IdpConfig> getFbProvider() {
        providers = Collections.singletonList(getFbUi());
        return providers;
    }

    /**
     * This method is used to return facebook UI
     *
     * @return
     */
    public List<AuthUI.IdpConfig> getEmailProvider() {
        providers = Collections.singletonList(getEmailUi());
        return providers;
    }

    /**
     * This method is used to get facebook Ui
     *
     * @return
     */
    public List<AuthUI.IdpConfig> getTwitterProvider() {
        providers = Collections.singletonList(getTwitUi());
        return providers;
    }

    /**
     * This method is used to get Google provider
     *
     * @return
     */
    public List<AuthUI.IdpConfig> getGoogProvider() {
        providers = Collections.singletonList(getGoogUi());
        return providers;
    }

    /**
     * This method is used to run and return AuthUI.IdpConfig providers which is all providers
     *
     * @return
     */
    public List<AuthUI.IdpConfig> getProviders() {

        providers = Arrays.asList(
                getEmailUi(),
                getPhoneUi(),
                getGoogUi(),
                getFbUi(),
                getTwitUi());


        return providers;
    }


    /**
     * This method is called when the various login is succesful
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {

            if (requestCode == RhemaHiveClassReferenceConstants.RC_SIGN_IN_ID) {
                IdpResponse response = IdpResponse.fromResultIntent(data);

                if (resultCode == RESULT_OK) {
                    // Successfully signed in
                    try {
                        f_user = getUser().getUser();

                        getAuto().getToast(c, " Authentication Sucessful Welcome Hiver ! ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        //startActivity(RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass().newActivityStarter(c, RhemaHiveDecisionActivity.class));
                        authenticateUser(returnUid());

                    } catch (NullPointerException np) {
                        getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                    // ...
                } else {
                    if (response == null) {
                        try {
                            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + response.getError().getErrorCode(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        } catch (NullPointerException np) {
                            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        }
                    }


                    // Sign in failed. If response is null the user canceled the
                    // sign-in flow using the back button. Otherwise check
                    // response.getError().getErrorCode() and handle the error.
                    // ...
                }
            }
        }

        else{
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getReason(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
    }

    public ImageButton getImgBut(int id) {
        ImageButton btn = findViewById(id);
        return btn;
    }


    public void loadAuthUI() {

        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(getProviders()).setLogo(R.drawable.rhema_logg).setTheme(R.style.AppTheme_NoActionBar2).build(), RhemaHiveClassReferenceConstants.RC_SIGN_IN_ID);


    }


public String returnUid(){
        if(fAuth != null){
            uuid = fAuth.getCurrentUser().getUid();
        }
        else{
            getAuto().getToast(c," id doesnt exist", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

        return uuid;

}


    public void loadFbAuth() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(getFbProvider()).setLogo(R.drawable.rhema_logg).setTheme(R.style.AppTheme_NoActionBar2).build(), RhemaHiveClassReferenceConstants.RC_SIGN_IN_ID);
    }


    /**
     * this method loads twitter authentication
     */
    public void loadTwitAuth() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(getTwitterProvider()).setLogo(R.drawable.rhema_logg).setTheme(R.style.AppTheme_NoActionBar2).build(), RhemaHiveClassReferenceConstants.RC_SIGN_IN_ID);
        //authenticateTwitter();
       decideTwittLoginToUse();

    }




    public void loadGoogAuth() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(getGoogProvider()).setLogo(R.drawable.rhema_logg).setTheme(R.style.AppTheme_NoActionBar2).build(), RhemaHiveClassReferenceConstants.RC_SIGN_IN_ID);
    }

    private void signOutUser() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            getAuto().getToast(c, "We are about to sign you out", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            try {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(c, "You have being sucessfully signed out", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            } catch (NullPointerException fb) {
                Toast.makeText(c, "Unable to sign out as a result of " + fb.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(c, "You are already signed out", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
    }

    /**
     * This method is used to get the user id
     * @return
     * @throws NullPointerException
     */
    public String getUserId() throws NullPointerException{


        if(fAuth != null){

            uuid = fAuth.getCurrentUser().getUid();

        }

        else{
            getAuto().getToast(c," Oops there is no id associated with this user " , RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

        return uuid;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater authSignInflate = getMenuInflater();
        authSignInflate.inflate(R.menu.menu_auth, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_user:
                signOutUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public AuthUI.IdpConfig getFbUi() {
        fbUi = new AuthUI.IdpConfig.FacebookBuilder().build();
        return fbUi;
    }


    public AuthUI.IdpConfig getTwitUi() {
        twitUi = new AuthUI.IdpConfig.TwitterBuilder().build();
        return twitUi;
    }

    public AuthUI.IdpConfig getEmailUi() {
        emailUi = new AuthUI.IdpConfig.EmailBuilder().build();
        return emailUi;
    }

    public AuthUI.IdpConfig getGoogUi() {
        gogUi = new AuthUI.IdpConfig.GoogleBuilder().build();
        return gogUi;
    }

    public AuthUI.IdpConfig getPhoneUi() {
        phoneUi = new AuthUI.IdpConfig.PhoneBuilder().build();
        return phoneUi;
    }

    public RhemaHiveFirebaseConnection getUser() {
        user = RhemaHiveInstanceManagerClass.getHiveFirebaseConnection();
        return user;
    }

    public RhemaHiveAutoUtilsClass getAuto() {
        RhemaHiveAutoUtilsClass autoUtilsClass = RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
        return autoUtilsClass;
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

    private void signInUser(String email, String pass) throws  NullPointerException {
        if (checkFilledData(email, pass) == 1) {
            if (networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {


                    progressDialog.show();
                    progressDialog.setMessage("Please wait while we verify your details");
                    fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (fAuth.getCurrentUser() != null && fAuth.getCurrentUser().isEmailVerified()) {
                                    getAuto().getToast(c, "Welcome Hiver ! ! Logged in with email " + fAuth.getCurrentUser().getEmail(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                                    authenticateUser(returnUid());

                                        //startActivity(getAuto().newActivityStarter(c,RhemaHiveDecisionActivity.class));
                                        progressDialog.dismiss();




                                }
                                else {
                                    getAuto().getToast(c, "Oops ! !  Sorry your email havent been verified", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                                    progressDialog.dismiss();
                                }

                            } else {
                                getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
             else {
                getAuto().getToast(c, "Oops ! ! No Internet seen on Phone", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }
        }
    }

    private TextView getTv(int id){
        TextView tv = findViewById(id);
        return tv;
    }


    private AppCompatEditText getAppEdt(int id){
        AppCompatEditText apt = findViewById(id);
        return apt;
    }

    private Button getBut(int id){
        Button btn =  findViewById(id);
        return btn;
    }


    /**
     * This method is used to send a forgot password email
     * @param email
     */
    public void forgotPassword(final String email){




            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                getAuto().getToast(c,"Password reset link has been sent to : " + email,RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            }
                        }
                    });



    }


    @Override
    public void onDialogPositiveClick(String email) {
        if(!TextUtils.isEmpty(email)){
            forgotPassword(email);
        }
        else{
            getAuto().getToast(c,"Oops ! ! Seems we are missing out your email here",RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();

        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {
        dialogFragment.dismiss();

    }

    public void showDialog(){
        DialogFragment dialogFragment = new RhemaHiveForgotPasswordAdapter();
        dialogFragment.show(getSupportFragmentManager(),"Forgot Password Dialog");

    }

    public void authenticateTwitter(){
        if (networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            final OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
            // Target specific email with login hint.
            //provider.addCustomParameter("lang", "fr");

            Task<AuthResult> pendingResultTask = fAuth.getPendingAuthResult();
            if (pendingResultTask != null) {
                pendingResultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        try {
                            // String twit_uname = authResult.getAdditionalUserInfo().getUsername();
                            //startActivity(getAuto().newActivityStarter(c,RhemaHiveDecisionActivity.class));
                            //getAuto().getToast(c,"Welcome Hiver ! ",RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            authenticateUser(returnUid());


                        } catch (NullPointerException np) {
                            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                });
            } else {

                fAuth
                        .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                        .addOnSuccessListener(
                                new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {

                                        try {


                                            //startActivity(getAuto().newActivityStarter(c,RhemaHiveDecisionActivity.class));
                                            authenticateUser(returnUid());
                                            getAuto().getToast(c, "Start Registration ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();

                                        } catch (NullPointerException np) {

                                            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();


                                        }
                                        // User is signed in.
                                        // IdP data available in
                                        // authResult.getAdditionalUserInfo().getProfile().
                                        // The OAuth access token can also be retrieved:
                                        // authResult.getCredential().getAccessToken().
                                        // The OAuth secret can be retrieved by calling:
                                        // authResult.getCredential().getSecret().
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle failure.
                                        getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage().toString(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                                    }
                                });
            }

        }

        else{
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getReason(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
    }

    public void decideTwittLoginToUse(){
       // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(fAuth.getCurrentUser() != null){
            authTwitterWithExistingProvider();
        }
        else{
            authenticateTwitter();
        }
    }


    public void authTwitterWithExistingProvider(){
        if (networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            final OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
            // Target specific email with login hint.
            //provider.addCustomParameter("lang", "fr");
            fAuth = FirebaseAuth.getInstance();
            Task<AuthResult> pendingResultTask = fAuth.getPendingAuthResult();
            if (pendingResultTask != null) {
                pendingResultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        try {
                            String twit_uname = authResult.getAdditionalUserInfo().getUsername();
                            // String uid = getUserId();
                            // ui_bund = new Bundle();
                            //ui_bund.putString("uuid",uid);
                            //Intent intent  = new Intent(c, RhemaHiveDecisionActivity.class);
                            //intent.putExtras(intent);
                            //startActivity(getAuto().newActivityStarter());
                            getAuto().getToast(c, "User is already signed in as : " + twit_uname, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            authenticateUser(returnUid());
                            //startActivity(getAuto().newActivityStarter(c , RhemaHiveDecisionActivity.class));
                        } catch (NullPointerException np) {
                            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                });
            } else {
                final FirebaseUser userFb = FirebaseAuth.getInstance().getCurrentUser();
                try {
                    //userFb.s
                    userFb.startActivityForLinkWithProvider(/* activity= */ this, provider.build())
                            .addOnSuccessListener(
                                    new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            //getAuto().getToast(c, "User : " + userFb.getDisplayName() + " is signed in on twitter as : " + authResult.getAdditionalUserInfo().getUsername().toString(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                                            //startActivity(getAuto().newActivityStarter(c, RhemaHiveDecisionActivity.class));
                                            authenticateUser(returnUid());

                                            // Twitter credential is linked to the current user.
                                            // IdP data available in
                                            // authResult.getAdditionalUserInfo().getProfile().
                                            // The OAuth access token can also be retrieved:
                                            // authResult.getCredential().getAccessToken().
                                            // The OAuth secret can be retrieved by calling:
                                            // authResult.getCredential().getSecret().
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle failure.
                                            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                                        }
                                    });

                } catch (NullPointerException np) {
                    getAuto().getToast(c, "error as a result of :  " + np.getLocalizedMessage().toString(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            }


        }
        else{
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getReason(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

    }


    public void authenticateUser(String user_id){
        if (networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {

            rhemaCollRef = rChurchFireStore.collection("rhema_churches");
            rhemaCollRef.whereEqualTo("user_uid",user_id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(DocumentSnapshot d : queryDocumentSnapshots.getDocuments()){
                        if(d.exists()){

                            String user_ids = d.getString("user_uid");
                            String user_type = d.getString("user_type");

                            if(user_type.equals("RhemaHiveUser")){

                                ui_bund.putString("user_uid",user_ids);
                                userIntent.putExtras(ui_bund);
                                startActivity(userIntent);
                            }

                            else{
                                ui_bund.putString("user_uid",user_ids);
                                churchIntent.putExtras(ui_bund);
                                startActivity(churchIntent);
                            }

                        }

                        else{
                            //ui_bund.putString("user_uid",user_id);
                            startActivity(new Intent(c,RhemaHiveDecisionActivity.class));

                            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no document exists here",RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        }
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC  + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    startActivity(new Intent(c,RhemaHiveDecisionActivity.class));
                }
            });
        }
        else{
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getReason(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
        }






}
