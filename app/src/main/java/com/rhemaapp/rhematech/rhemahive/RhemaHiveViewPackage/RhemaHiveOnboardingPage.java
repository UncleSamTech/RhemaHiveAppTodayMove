package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.alexzh.circleimageview.ItemSelectedListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rhemaapp.rhematech.rhemahive.GlideApp;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveChurchModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUsersPackage.RhemaHiveUserSubClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;
import com.rilixtech.CountryCodePicker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.net.Uri;

import org.w3c.dom.Text;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class RhemaHiveOnboardingPage extends AppCompatActivity {
    FirebaseFirestore fstore;
    Context context = RhemaHiveOnboardingPage.this;
    String church_select;
    String br_chu_select;
    FirebaseAuth fAuth;
    int value;
    String churchExtract;
    public HashMap<String, Integer> churchMap = new HashMap<>();

    String phone;
    String country;
    String gend;
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    String[] church_val;
    String imgPath;
    Uri imgUri;
    String genderSelect;
    Spinner spin;
    private DatePickerDialog datePickerDialog;
    AppCompatEditText edtDob;
    String full_date;
    int selecDay;
    int selecMonth;
    int selecYear;
    int years;
    int month;
    int day;
    int year_diff;
    private RhemaHiveUserModelClass rhemaHiveUserModelClass;
    private RhemaHiveUserSubClass rhemaHiveUserSubClass;
    private RhemaHiveUserRes rhemaHiveUserRes;
    private CollectionReference fiReference;
    private CountryCodePicker ccp;
    private UploadTask uploadTask;
    private String downloadUrl;
    private ProgressDialog progressDialog;
    private Task<Uri> urlTask;
    private String uuid;
    private ArrayList<String> churches;

    private FirebaseFirestore rheFstore;
    private String doc_id;
    private Intent intent;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhema_hive_onboarding_page);
        churches = new ArrayList<>();
        try {
            intent = new Intent(context, RhemaHiveUserPortal.class);
            bundle = new Bundle();

        } catch (NullPointerException e) {
            getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
        }


        fAuth  = FirebaseAuth.getInstance();
        rhemaHiveUserRes = new RhemaHiveUserRes();
        progressDialog = new ProgressDialog(context);
       /* try {


            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException np) {
            getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }*/
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //the NetworkInfo class gets the current state of the device network connection
        networkInfo = connMgr.getActiveNetworkInfo();

        edtDob = getAppEdt(R.id.appEdtDob);
        try {


            edtDob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Calendar c = Calendar.getInstance();
                    years = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);

                    datePickerDialog = new DatePickerDialog(RhemaHiveOnboardingPage.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            selecDay = dayOfMonth;
                            selecMonth = month;
                            selecYear = year;
                            year_diff = years - selecYear;
                            if(year_diff >= 18){
                                edtDob.setText(selecDay + " - " + (selecMonth + 1) + " - " + selecYear);
                                full_date = monthIntToYear(selecDay, selecMonth + 1, selecYear);
                            }

                            else{
                                getAuto().getToast(context,"Oops! ! You have to be 18 and above ",RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            }
                            //edtDob.sett


                        }
                    }, years, month, day);
                    datePickerDialog.show();
                }
            });
        } catch (NullPointerException np) {
            getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }


        getSelecChurchBr(2);
        // getGend();
        getBut(R.id.button_onboard).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

try{
uploadImg(get_fire_storage(),rhemaHiveUserRes.getUserChurchName());
}
catch(NullPointerException np){
    getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
} catch (StorageException e) {
    getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
}
            }
        });


        getImg(R.id.img_upload_user_prof).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocalImgPath();
            }
        });

       /* try {
           retreiveAllChurches();
        } catch (NullPointerException e) {
            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
        }*/

        getImg(R.id.back_img).setOnClickListener(new View.OnClickListener() {
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
                imgPath = data.getDataString();
                getImg(R.id.img_upload_user_prof).setImageURI(imgUri);
            } catch (NullPointerException np) {
                gettToast(context, "Error as a result of : " + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }
        }
    }



    public Uri getImgUri(){
        Uri uri = imgUri;
        return uri;}

    /**
     * This method is used for returning the image path
     *
     * @return
     */
    public String getImgPath() {
        //Toast.makeText(c, "Image Path : " + imgPath, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        return imgPath;
    }



    public String getFullName(String fn, String ln){
        return fn + " " +  ln;
    }

    /**
     * This method is used for onboarding users
     * @param fName
     * @param lName
     * @param gender
     * @param userType
     * @param dob
     * @param regStat
     * @param imgPath
     * @param userEmail
     * @param phone
     * @param about
     * @param churchName
     * @param branchName
     * @param addr
     * @param city
     * @param country
     * @param postalCode
     */
    public void onBoardUserWithoutSocial(String fName, String lName, String gender, String userType, String dob, String regStat,  String imgPath, String userEmail,  String phone , String about,  String churchName, String branchName, String addr, String city, String country,String postalCode,final String uid) {
        if (networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            fstore = getFstore();
        fiReference = fstore.collection("rhema_churches");
        rhemaHiveUserSubClass = getRhemaHiveUserSubClass();

            if (rhemaHiveUserSubClass.checkEmptyParam(fName, lName, branchName, churchName, addr, country, city, dob, gender, postalCode, regStat, phone,  userType,imgPath,userEmail,about,uid) == 1) {
                rhemaHiveUserModelClass = getUserModelClass(fName,lName,gender,userType,dob,regStat, imgPath,userEmail,phone,about,churchName,branchName,addr,city,country,postalCode,uid);
                fiReference.document(uid).set(rhemaHiveUserModelClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            try{
                            getAuto().getToast(context, "Congratulation You've onboarded sucessfully..",RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                                bundle.putString("user_id", uid);

                                intent.putExtras(bundle);
                            startActivity(intent);
                            clearUserDetails();
                            }
                            catch(NullPointerException np){
                                getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            }

                        }
                        else{
                            getAuto().getToast(context,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                });

            } else {
                getAuto().getToast(context, "Oops ! !..Seems we are missing out some of your details", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }

        }




        else{
            getAuto().getToast(context, "Oops ! !..Seems you need to put on your data", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public void moveBack(){

       super.onBackPressed();
    }


    /**
     * This method is used for retrieving an instance of FirebaseFirestore
     *
     * @return
     */
    public FirebaseFirestore getFstore() {
        fstore = RhemaHiveInstanceManagerClass.getFireStoreClass().getFireStore();
        return fstore;
    }


    /**
     * This method is used to return the toast
     *
     * @param c
     * @param message
     * @param dur
     * @return
     */
    public Toast gettToast(Context c, String message, int dur) {
        Toast t = Toast.makeText(c, message, dur);
        return t;
    }


    public void clearValues() {


    }

    /**
     * This is used for holding an instance of Spinner
     *
     * @param id
     * @return
     */
    public Spinner getRhemaSpin(int id) {
        Spinner rhemaSpin = findViewById(id);
        return rhemaSpin;
    }

    /**
     * This is used for holding an instance of EditText
     *
     * @param id
     * @return
     */
    public EditText getEdt(int id) {
        EditText rhemEdit = findViewById(id);
        return rhemEdit;
    }

    /**
     * This is used for holding an instance of Button
     *
     * @param id
     * @return
     */
    public Button getBut(int id) {
        Button but = findViewById(id);
        return but;
    }

    /**
     * This method is used for returning arrayAdapter
     *
     * @param arr_cont_id
     * @param lay_id
     * @param dropId
     * @return
     */
    public ArrayAdapter getArrayAdapter(Context c, int arr_cont_id, int lay_id, int dropId) {
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(c, arr_cont_id, lay_id);
        arrayAdapter.setDropDownViewResource(dropId);
        return arrayAdapter;
    }


    public String[] getSelecChurchBr(int len) {
        church_val = new String[len];
        ArrayAdapter arrAd = getArrayAdapter(this, R.array.churches, android.R.layout.simple_spinner_item, android.R.layout.simple_dropdown_item_1line);
        Spinner churchspin = getRhemaSpin(R.id.church_spinner);
        churchspin.setAdapter(arrAd);
        churchspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                church_select = (String) parent.getItemAtPosition(position);
                if (church_select.equalsIgnoreCase("---Select Church---")) {
                    gettToast(context, "Please select a valid church", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                } else {
                    br_chu_select = setBranch(getArrayAdapter(context, getChurchDetails(church_select, putChurch()), android.R.layout.simple_spinner_item, android.R.layout.simple_dropdown_item_1line), getRhemaSpin(R.id.church_branch_spinner));
                }

                church_val[0] = church_select;
                church_val[1] = br_chu_select;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return church_val;
    }


    public String setBranch(ArrayAdapter br_ch, Spinner br_spin) {

        br_spin.setAdapter(br_ch);
        br_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                br_chu_select = (String) parent.getItemAtPosition(position);
                if (br_chu_select.equalsIgnoreCase("---Select Branch Name---")) {
                    gettToast(context, "Youv'e not made a valid selection yet", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                } else {
                    gettToast(context, "You selected : " + br_chu_select, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return br_chu_select;
    }


    /**
     * This method is used for retrieving the array associated with each church
     *
     * @param church
     * @param hmp
     * @return
     */
    public int getChurchDetails(String church, HashMap<String, Integer> hmp) {

        if (hmp.containsKey(church)) {
            try {
                value = hmp.get(church);
            } catch (NullPointerException np) {
                gettToast(context, " Unable to retreive value", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }
        }

        return value;
    }

    /**
     * This method is used to push values to the hashmap
     *
     * @return
     */
    public HashMap<String, Integer> putChurch() {
        churchMap.put("Church of Christ", R.array.coc_branch);
        churchMap.put("Redeemed Christian Church of God", R.array.redeem_branch);
        churchMap.put("Jehovah Witness", R.array.jw_branch);
        churchMap.put("Eckankar", R.array.eckankar_branch);
        churchMap.put("Lords Chosen", R.array.lord_cho_branch);
        churchMap.put("Catholic", R.array.catholic_branch);
        churchMap.put("Mountain of Fire and Miracles Center Church", R.array.mfm_branch);
        churchMap.put("Seventh Day Adventist", R.array.sda_branch);
        churchMap.put("Anglican Church", R.array.angl_branch);
        churchMap.put("Christ Embassy", R.array.chrem_branch);
        churchMap.put("House on the Rock", R.array.honr_branch);
        churchMap.put("Winners Chapel", R.array.winn_branch);

        return churchMap;
    }


    public String getChurch() {


        churchExtract = church_select;

        return churchExtract;
    }

    public String getBranch() {

        churchExtract = br_chu_select;

        return churchExtract;
    }

    public RhemaHiveAutoUtilsClass getAuto() {
        RhemaHiveAutoUtilsClass rAuto = RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
        return rAuto;
    }
    RadioButton radSexBut;



   public String getSelecGend(int id) {
RadioGroup genGrp = getRadGp(id);

int selectedRadBtn = genGrp.getCheckedRadioButtonId();

    radSexBut  = findViewById(selectedRadBtn);
    getAuto().getToast(context, "You selected  " + radSexBut.getText().toString(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN ).show();



return radSexBut.getText().toString().trim();
   }




   public RadioGroup getRadGp(int id){
        RadioGroup rGp = findViewById(id);
        return rGp;
   }
    public String getGend(View v) {

        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.radMale:
                if (checked) {
                    gend = "Male";
                }
                break;
            case R.id.radFemale:
                if (checked) {
                    gend = "Female";
                }
                break;
            default:
                gend = "Not Specified";
        }

        return gend;
    }



    public void getLocalImgPath() {
        try {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, RhemaHiveClassReferenceConstants.GALLERY_REQUEST_CODE);


        } catch (NullPointerException np) {
            gettToast(context, "Error as a result of : " + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }


    }


    public ImageView getImg(int id) {
        ImageView img = findViewById(id);
        return img;
    }



    public String monthIntToYear(int day, int month, int year) {

        String dayString = Integer.toString(day);
        String monthString = Integer.toString(month);
        String yearString = Integer.toString(year);

        String fullDate = dayString + " - " + monthString + " - " + yearString;

        return fullDate;
    }


    public AppCompatEditText getAppEdt(int id) {
        AppCompatEditText appCompatEditText = findViewById(id);
        return appCompatEditText;
    }


    public void ld_wt_gl(Context c, String imgPath, int rad, int marg, ImageView imageView) {
        /**GlideApp.with(c)
         .load(c)
         .transforms(new CenterCrop(), new RoundedCorners(20))
         .into(imageView);*/
        GlideApp.with(c).load(imgPath).centerCrop().fitCenter().transform(new RoundedCornersTransformation(rad, marg)).into(imageView);
        //GlideApp.with(c).load(imgPath).transform(new RoundedCornersTransformation(rad, marg)).into(imageView);
    }


    public void clearUserDetails() {
        getAppEdt(R.id.appEdtFName).setText("");
        getAppEdt(R.id.appEdtLName).setText("");
        getAppEdt(R.id.appEdtDob).setText("");
        getAppEdt(R.id.appEdtEmail).setText("");
        getAppEdt(R.id.appEdtPhone).setText("");
        getAppEdt(R.id.appEdtAbout).setText("");
        getRhemaSpin(R.id.church_spinner).setSelection(0);
        getRhemaSpin(R.id.church_branch_spinner).setSelection(0);
        getAppEdt(R.id.appEdtAddress).setText("");
        getAppEdt(R.id.appEdtCity).setText("");
        getAppEdt(R.id.appEdtPostal).setText("");
        getImg(R.id.img_upload_user_prof).setImageResource(R.drawable.ic_camera);

    }


    private class RhemaHiveUserRes {


        /**
         * this returns users first name captured from edittext view
         *
         * @param id
         * @return
         * @throws NullPointerException
         */
        public String getUserFName(int id) throws NullPointerException {
            return getAppEdt(id).getText().toString().trim();
        }

        /**
         * this method returns users last name captured from edittext view
         *
         * @param id
         * @return
         * @throws NullPointerException
         */

        public String getUserLName(int id) throws NullPointerException {
            return getAppEdt(id).getText().toString().trim();
        }

        /**
         * this method returns users date of birth captured from edittext view
         *
         * @param id
         * @return
         * @throws NullPointerException
         */
        public String getUserDob(int id) throws NullPointerException {
            return getAppEdt(id).getText().toString().trim();
        }

        /**
         * this method returns users email address captured from edittext view
         *
         * @param id
         * @return
         * @throws NullPointerException
         */
        public String getUserEmail(int id) throws NullPointerException {
            return getAppEdt(id).getText().toString().trim();
        }

        /**
         * this method returns users phone number captured from edittext view
         *
         *
         * @return
         * @throws NullPointerException
         */
        public String getUserPhone() throws NullPointerException {
            return getAppEdt(R.id.appEdtPhone).getText().toString().trim();
        }

        /**
         * this method returns users about captured from edittext view
         *
         * @param id
         * @return
         * @throws NullPointerException
         */
        public String getUserAbout(int id) throws NullPointerException {
            return getAppEdt(id).getText().toString().trim();
        }

        /**
         * this method returns users last name captured from spinner
         *
         * @return
         * @throws NullPointerException
         */
        public String getUserChurchName() throws NullPointerException {
            return getChurch();
        }

        /**
         * this method returns users last name captured from spinner
         *
         * @return
         * @throws NullPointerException
         */
        public String getUserChurchBranchName() throws NullPointerException {
            return getBranch();
        }


        /**
         * this method returns users address captured from edittext view
         *
         * @param id
         * @return
         * @throws NullPointerException
         */
        public String getUserAddr(int id) throws NullPointerException {
            return getAppEdt(id).getText().toString().trim();
        }

        /**
         * this method returns users city captured from edittext view
         *
         * @param id
         * @return
         * @throws NullPointerException
         */
        public String getUserCity(int id) throws NullPointerException {
            return getAppEdt(id).getText().toString().trim();
        }

        /**
         * this method returns users last name captured from country code picker
         *
         * @param id
         * @return
         * @throws NullPointerException
         */
        public String getUserCountry(int id) throws NullPointerException {
            return getCCp(id).getSelectedCountryName();
        }


        /**
         * This method returns the user id
         * @return
         */
        public String getUid() throws NullPointerException{
            if(fAuth != null){
                uuid = fAuth.getCurrentUser().getUid() ;


            }
            else{
                getAuto().getToast(context, "Oops there is no user associated with this account ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }
            return uuid;
        }

        /**
         * this method returns users postal captured from edittext view
         *
         * @param id
         * @return
         * @throws NullPointerException
         */

        public String getUserPostalAddr(int id) throws NullPointerException {
            return getAppEdt(id).getText().toString().trim();
        }

        /**
         * this method returns users reg stat generated from user registration status method
         *
         * @param id
         * @return
         * @throws NullPointerException
         */

        public String getUserRegStat(int id) throws NullPointerException {
            return getRhemaHiveUserSubClass().genRegStat(id);
        }

        /**
         * This method gets users image link from getImgPath method
         *
         * @return
         * @throws NullPointerException
         */
        public String getUserImgLink() throws NullPointerException {

            return getImgPath();

        }

        /**
         * This method returns gender of user
         *
         * @param rad
         * @return
         */
        public String getUserGend(int rad) {
            return getSelecGend(rad);
        }

        public String getUserType(String user_type){
            return user_type;
        }


    }

    public CountryCodePicker getCCp(int id){
        CountryCodePicker ccp = findViewById(id);
        return ccp;
    }

    public RhemaHiveUserSubClass getRhemaHiveUserSubClass() {
        return RhemaHiveInstanceManagerClass.getRhemaHiveUserSubClass();
    }

    public RhemaHiveUserModelClass getUserModelClass(String fName, String lName, String gender, String userType, String userDob, String regStat, String pix, String email, String phone, String about, String church, String branch, String addr, String city,String country, String postCode,String uid) {
        return new RhemaHiveUserModelClass(fName,lName,gender,userType,userDob,regStat, pix, email, phone, about,church,branch,addr, city, country, postCode,uid);
    }

    /**
     * This is used to get and instance of Firebase Storage
     * @return
     */
    public FirebaseStorage get_fire_storage(){
        FirebaseStorage fStorage = FirebaseStorage.getInstance();
        return fStorage;}



    public String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


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
                        getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    } else {
                            storeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = uri.toString();
                                }
                            });

                        //downloadUrl = storeRef.getDownloadUrl().toString();

                    }

                    return storeRef.getDownloadUrl();
                }
            });
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    try{
                        double progress = (100.0 * taskSnapshot.getBytesTransferred());
                        getAuto().getToast(context, "Upload is " + progress + "% done ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                    catch(NullPointerException np){
                        getAuto().getToast(context,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC +  np.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    try{
                        getAuto().getToast(context, "Uplpoad is paused as a result of : " + taskSnapshot.getError().getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();}
                    catch(NullPointerException np){
                        getAuto().getToast(context,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC,RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        getAuto().getToast(context, "Image Uploaded Successfully", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        try {
                            onBoardUserWithoutSocial(rhemaHiveUserRes.getUserFName(R.id.appEdtFName),rhemaHiveUserRes.getUserLName(R.id.appEdtLName),rhemaHiveUserRes.getUserGend(R.id.rad_gend_group),rhemaHiveUserRes.getUserType("RhemaHiveUser"),rhemaHiveUserRes.getUserDob(R.id.appEdtDob),rhemaHiveUserRes.getUserRegStat(0),downloadUrl,rhemaHiveUserRes.getUserEmail(R.id.appEdtEmail),rhemaHiveUserRes.getUserPhone(),rhemaHiveUserRes.getUserAbout(R.id.appEdtAbout),rhemaHiveUserRes.getUserChurchName(),rhemaHiveUserRes.getUserChurchBranchName(),rhemaHiveUserRes.getUserAddr(R.id.appEdtAddress), rhemaHiveUserRes.getUserCity(R.id.appEdtCity), rhemaHiveUserRes.getUserCountry(R.id.country_ccp_reg),rhemaHiveUserRes.getUserPostalAddr(R.id.appEdtPostal),rhemaHiveUserRes.getUid());
                        }
                            catch(NullPointerException np){
                                getAuto().getToast(context,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            }
                            progressDialog.dismiss();


                        } else {
                            getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                });


            }
        else{
                getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }
            return downloadUrl;
        }

/*
public String getChurches() throws NullPointerException{
    if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
        fstore = getFstore();
        fiReference = fstore.collection("rhema_churches");
        fiReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot doc : queryDocumentSnapshots.toObjects()) {
                    churches.add(doc.getString("church_name"));
                }
            }
        });


    }
    else{
        getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC,RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
    }
    StringBuilder sb = new StringBuilder();
    for(String s : churches){
        sb.append(s);
        sb.append("\t");
    }

    getAuto().getToast(c, "These are the churches : " + churches, RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
    return sb.toString();
}*/

public void retreiveAllChurches() throws NullPointerException{
    fstore = getFstore();
    fiReference = fstore.collection("rhema_churches");
    fiReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot q : task.getResult()){
                   doc_id = q.getString("church_name");

                }
            }

            else{
                getAuto().getToast(context, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage() , RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            getAuto().getToast(context,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }
    });

    getAuto().getToast(context, " Details : " + doc_id ,RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
}


public Bundle getBundUser(String userId){
    Bundle bund  = new Bundle();
    bund.putString("user_id",userId);
    return bund;
}

    }
