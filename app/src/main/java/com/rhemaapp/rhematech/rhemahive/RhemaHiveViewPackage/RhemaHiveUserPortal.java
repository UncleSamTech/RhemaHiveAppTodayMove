package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rhemaapp.rhematech.rhemahive.BuildConfig;
import com.rhemaapp.rhematech.rhemahive.GlideApp;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class RhemaHiveUserPortal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private String user_name;
    private String user_phone;
    private String user_email;
    private String user_pix_link;
    private String user_uid;
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private CollectionReference rhemaCollRef;
    private Context c = RhemaHiveUserPortal.this;
    private Bundle bund;
    private String user_church;
    private String churchName;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhema_hive_user_portal);
        try {
            Toolbar toolbar = findViewById(R.id.toolbar_user);
            setSupportActionBar(toolbar);

            actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
            }





            DrawerLayout drawer = findViewById(R.id.drawer_layout_user);/*
            NavigationView navigationView = findViewById(R.id.nav_view_user);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                    R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                    .setDrawerLayout(drawer)
                    .build();
            //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_user);
            NavController navController = Navigation.findNavController
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);*/
            //NavigationUI.setupWithNavController(navigationView,navController,toolbar);
            FloatingActionButton fab = findViewById(R.id.fab_user);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/

                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                        String shareMessage= "\n Bringing the churches to your doorsteps..Download using this link..\n\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                    } catch (Exception e) {
                        getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                }
            });
            connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            //the NetworkInfo class gets the current state of the device network connection
            networkInfo = connMgr.getActiveNetworkInfo();
            try {
                if (getIntent() != null) {
                    if (getIntent().getExtras() != null) {
                        bund = getIntent().getExtras();
                        if (bund.containsKey("user_uid")) {
                            if (!TextUtils.isEmpty(bund.getString("user_uid"))) {
                                user_uid = bund.getString("user_uid");
                            }
                        }
                    }
                }
            } catch (NullPointerException np) {
                getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }

            try {
                retrUserDet(retUid());
            } catch (NullPointerException e) {
                getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
            }

        } catch (NullPointerException np) {
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

        getCardView(R.id.use_port_card_mess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushChurch(retrChurchName(),"user_church",MODE_PRIVATE,"church_name");
                pushUserId(retUid(),"user_uid",MODE_PRIVATE, "uid");
                startActivity(new Intent(c, RhemaHiveUserPortalAreaActivity.class));

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rhema_hive_user_portal, menu);
        return true;
    }

   /* @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    public FirebaseFirestore getFStore(){
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        return fStore;

    }

    public void retrUserDet(final String uid) throws NullPointerException{
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            FirebaseFirestore fS = getFStore();
            rhemaCollRef = fS.collection("rhema_churches");
            rhemaCollRef.whereEqualTo("user_uid",uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.getDocuments().isEmpty()){
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            user_email = doc.getString("user_email");
                            user_name = doc.getString("user_fName") +  "  " + doc.getString("user_lName");
                            user_phone = doc.getString("user_phone");
                            user_pix_link = doc.getString("user_pix");
                            user_church = doc.getString("user_church");
                            getTex(R.id.user_retr_email).setText(user_email);
                            getTex(R.id.user_retr_name).setText(user_name);
                            getTex(R.id.user_retr_phone).setText(user_phone);
                            getTex(R.id.user_church_name).setText(user_church);
                            ld_wt_gl(c,user_pix_link,getImg(R.id.user_retr_logg));

                        }
                    }

                    else{
                        getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no matching data found " , RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            });


        }
        else{
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }


    }

    public RhemaHiveAutoUtilsClass getAuto(){
        RhemaHiveAutoUtilsClass rAuto = RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
        return rAuto;
    }

    public String retUid(){
        return user_uid;
    }

    public TextView getTex(int id){
        TextView tv = findViewById(id);
        return tv;
    }


    public void ld_wt_gl(Context c, String imgPath, ImageView imageView) {

        //RequestOptions requestOptions = n
        /**GlideApp.with(c)
         .load(imgPath)
         .transforms(new CenterCrop(), new RoundedCorners(20))
         .into(imageView);*/
       // GlideApp.with(c).load(imgPath).centerCrop().fitCenter().transform(new RoundedCornersTransformation(rad, marg)).into(imageView);
        GlideApp.with(c).load(imgPath).into(imageView);
    }

    public ImageView getImg(int id){
        ImageView img = findViewById(id);
        return img;
    }

    private CardView getCardView(int id){
        CardView cardView = findViewById(id);
        return cardView;
    }

    public void pushChurch( String value,String key , int mode,String prefKey){
        SharedPreferences shPref = getSharedPreferences(prefKey,mode);
        SharedPreferences.Editor edt = shPref.edit();
        edt.putString(key,value);
        edt.apply();
    }

    public void pushUserId( String value,String key , int mode, String prefkey){
        SharedPreferences shPref = getSharedPreferences(prefkey,mode);
        SharedPreferences.Editor edt = shPref.edit();
        edt.putString(key,value);
        edt.apply();
    }

    public String retrChurchName(){
        if(getTex(R.id.user_church_name).getText().toString().trim().length() > 0 && !getTex(R.id.user_church_name).getText().toString().trim().equals("") ){
          churchName = getTex(R.id.user_church_name).getText().toString().trim();
        }

        else{
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no church retrived", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

        return churchName;
    }



}


