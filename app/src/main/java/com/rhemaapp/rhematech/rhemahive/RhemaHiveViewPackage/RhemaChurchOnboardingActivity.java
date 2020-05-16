package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveChurchModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUsersPackage.RhemaHiveChurchSubClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;

import java.io.File;

public class RhemaChurchOnboardingActivity extends AppCompatActivity {
    Context c = RhemaChurchOnboardingActivity.this;
    String imgPath;
    Uri imgUri;
    Button but;
    private Bundle bundle;
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private FirebaseFirestore rChurchFireStore;
    private RhemaChurchRes rhemaChurchRes;
    private RhemaHiveChurchModelClass rhemaHiveChurchModelClass;
    private RhemaHiveChurchSubClass rhemaHiveChurchSubClass;
    private CollectionReference rhemaCollRef;
    private Task<Uri> urlTask;

    private UploadTask uploadTask;
    private String downloadUrl;
    private Uri files;
    private ProgressDialog progressDialog;
    private FirebaseAuth fAuth;
    private String uid;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhema_church_onboarding);
        rChurchFireStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        rhemaChurchRes = new RhemaChurchRes();
        try {

            bundle = new Bundle();

        } catch (NullPointerException e) {
            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }


        progressDialog = new ProgressDialog(c);
        but = getBut(R.id.onboard_church_button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    uploadImg(get_fire_storage(),rhemaChurchRes.getChurchName());


                } catch (NullPointerException np) {
                    getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                } catch (StorageException e) {
                    getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            }
        });
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //the NetworkInfo class gets the current state of the device network connection
        networkInfo = connMgr.getActiveNetworkInfo();

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException np) {
            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

        getImg(R.id.img_upload_ico).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocalImgPath();
            }
        });



      getImg(R.id.back_img_church).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              moveBack();
          }
      });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RhemaHiveClassReferenceConstants.GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                imgUri = data.getData();
                imgPath = data.getData().getSchemeSpecificPart();

                getImg(R.id.img_upload_ico).setImageURI(imgUri);
                getAuto().getToast(c, "Path is : " + imgPath,Toast.LENGTH_LONG).show();
            } catch (NullPointerException np) {
                getAuto().getToast(c, "Error as a result of : " + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }
        }
    }


    public Uri getImgUri(){
        Uri upUri = imgUri;

        return upUri;
    }

    public String getChurchImgPath() {
       // getAuto().getToast(c, "Image Path for this is : " + imgPath, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        return imgPath;
    }


    public RhemaHiveAutoUtilsClass getAuto() {
        RhemaHiveAutoUtilsClass rAuto = RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
        return rAuto;
    }


    public void getLocalImgPath() {
        try {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, RhemaHiveClassReferenceConstants.GALLERY_REQUEST_CODE);

            //imgPath = galleryIntent.getData().getSchemeSpecificPart();
        } catch (NullPointerException np) {
            getAuto().getToast(c, "Error as a result of : " + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
       // getAuto().getToast(c, "Selected Image path is : " + imgPath, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();

    }

    public ImageView getImg(int id) {
        ImageView img = findViewById(id);
        return img;
    }



    public Button getBut(int id) {
        Button but = findViewById(id);
        return but;
    }

    public AppCompatEditText getAppEdt(int id) {
        AppCompatEditText appEdt = findViewById(id);
        return appEdt;
    }

    private class RhemaChurchRes {

        public String getChurchName() throws NullPointerException {
            return getAppEdt(R.id.appEdtChurchName).getText().toString().trim();
        }

        public String getChurchAddr() throws NullPointerException {
            return getAppEdt(R.id.appEdtChurchAddr).getText().toString().trim();
        }

        public String getChurchEmail() throws NullPointerException {
            return getAppEdt(R.id.appEdtChurchEmail).getText().toString().trim();
        }

        public String getChurchLeadName() throws NullPointerException {
            return getAppEdt(R.id.appEdtleadName).getText().toString().trim();
        }

        public String getChurchIcon() throws NullPointerException {
            return getChurchImgPath();
        }

        public String getChurchDescr() throws NullPointerException {
            return getAppEdt(R.id.appEdtChurchDescr).getText().toString().trim();
        }

        public String getChurchPhone() throws NullPointerException {
            return getAppEdt(R.id.appEdtChurchPhone).getText().toString().trim();
        }

        public String getChurchRegStat() throws NullPointerException {
            return getRhemaHiveChurchSubClass().genChurchRegStat(0);
        }

        public String getUid() throws NullPointerException{
            if(fAuth != null){
                uid = fAuth.getCurrentUser().getUid();

            }

            else{
                getAuto().getToast(c, "Oops ! !..seems we cant find any user", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }
            return uid;
        }

        public String returnUserType(){
            return "RhemaHiveAdmin";
        }

    }


    public void clearChurchDetails() {
        getAppEdt(R.id.appEdtChurchAddr).setText("");
        getAppEdt(R.id.appEdtChurchEmail).setText("");
        getAppEdt(R.id.appEdtChurchName).setText("");
        getAppEdt(R.id.appEdtleadName).setText("");
        getAppEdt(R.id.appEdtChurchPhone).setText("");
        getImg(R.id.img_upload_ico).setImageResource(R.drawable.ic_camera);
        getAppEdt(R.id.appEdtChurchDescr).setText("");

    }

    public RhemaHiveChurchModelClass getChurchModelClass(String church_name, String church_addr, String church_email, String church_lead_name, String churchPhone, String church_icon, String church_descr, String reg_stat, String uid,String user_type) {

        return new RhemaHiveChurchModelClass(church_name, church_addr, church_email, church_lead_name, churchPhone, church_icon, church_descr, reg_stat, uid,user_type);

    }


    public RhemaHiveChurchSubClass getRhemaHiveChurchSubClass() {
        return RhemaHiveInstanceManagerClass.getRhemaHiveChurchSubClass();
    }



    public void onBoardChurchDetails(String user_church, String church_addr, String church_email, String church_leadName, String churchPhone, String church_icon, String churchDescr, String church_regStat, final String uid,String user_type) {
        if (networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            rhemaCollRef = rChurchFireStore.collection("rhema_churches");
            rhemaHiveChurchSubClass = getRhemaHiveChurchSubClass();

            if (rhemaHiveChurchSubClass.checkChurchEmptyParams(church_icon, user_church, church_addr, churchPhone, church_email, church_leadName, churchDescr, church_regStat,uid,user_type) == 1) {

                rhemaHiveChurchModelClass = getChurchModelClass(user_church, church_addr, church_email, church_leadName, churchPhone, church_icon, churchDescr, church_regStat,uid,user_type);

                rhemaCollRef.document(uid).set(rhemaHiveChurchModelClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            try {
                                getAuto().getToast(c, " Congratulations you Just Registered a new Church", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                                intent  = new Intent(c, RhemaHiveChurchPortal.class);
                                bundle.putString("user_uid", uid);

                                intent.putExtras(bundle);
                                startActivity(intent);
                                clearChurchDetails();
                            }
                            catch(NullPointerException np){
                                getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            }
                        } else {
                            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            startActivity(getAuto().newActivityStarter(c, RhemaHiveChurchPortal.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                });


            } else {

                getAuto().getToast(c, "Oops Seems we are missing out some data here", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();

            }

        } else {
            getAuto().getToast(c, "Oops Seems No Internet Service is connected", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
    }

    /**
     * This is used to get and instance of Firebase Storage
     * @return
     */
    public FirebaseStorage get_fire_storage(){
        FirebaseStorage fStorage = FirebaseStorage.getInstance();
        return fStorage;}





    /**
     * this is used to upload image
     *
     * @return
     */
    public String uploadImg( FirebaseStorage storage, String church_name) throws NullPointerException, StorageException {

        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {

            progressDialog.show();
progressDialog.setMessage("Please wait while we process your request");


            final StorageReference storeRef = storage.getReference(church_name + "/").child(System.currentTimeMillis() + "." + getFileExtension(getImgUri()));


            uploadTask = storeRef.putFile(getImgUri());
                urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        } else {
                            //downloadUrl = storeRef.getDownloadUrl().toString();
                            storeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = uri.toString();
                                }
                            });

                        }

                        return storeRef.getDownloadUrl();
                    }
                });
                uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                   try{
                       double progress = (100.0 * taskSnapshot.getBytesTransferred());
                       getAuto().getToast(c, "Upload is " + progress + "% done ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                   }
                   catch(NullPointerException np){
                       getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC +  np.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        }
                    }
                }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        try{
                            getAuto().getToast(c, "Uplpoad is paused as a result of : " + taskSnapshot.getError().getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();}
                        catch(NullPointerException np){
                            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC,RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            getAuto().getToast(c, "Image Uploaded Successfully", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            try{
                            onBoardChurchDetails(rhemaChurchRes.getChurchName(), rhemaChurchRes.getChurchAddr(), rhemaChurchRes.getChurchPhone(), rhemaChurchRes.getChurchLeadName(), rhemaChurchRes.getChurchEmail(), rhemaChurchRes.getChurchRegStat(), downloadUrl, rhemaChurchRes.getChurchDescr(),rhemaChurchRes.getUid(),rhemaChurchRes.returnUserType());

                            }

                            catch(NullPointerException np){
                                getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            }
                            progressDialog.dismiss();


                        } else {
                            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                });


        }
        else{
            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
return downloadUrl;
    }

public String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
    MimeTypeMap mime = MimeTypeMap.getSingleton();
    return mime.getExtensionFromMimeType(cr.getType(uri));
}



    public void moveBack(){
        super.onBackPressed();


}


public ImageButton getImgBut(int id){
        ImageButton img  = findViewById(id);
        return img;
}

    public String getUid2() throws NullPointerException{
        if(fAuth != null){
            uid = fAuth.getCurrentUser().getUid() + rhemaChurchRes.getChurchPhone();
            getAuto().getToast(c, "Current UUID : " + uid, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

        else{
            getAuto().getToast(c, "Oops ! !..seems we cant find any user", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
        return uid;
    }





}
