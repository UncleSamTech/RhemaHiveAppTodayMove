package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rhemaapp.rhematech.rhemahive.BuildConfig;
import com.rhemaapp.rhematech.rhemahive.GlideApp;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveBottomNavBehaviorClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.ui.gallery.GalleryFragment;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.ui.home.HomeFragment;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.ui.send.SendFragment;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.ui.share.ShareFragment;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.ui.tools.ToolsFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class RhemaHiveChurchPortal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private CollectionReference rhemaCollRef;
    private DocumentReference rhemaDocRef;
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private Context c = RhemaHiveChurchPortal.this;
    private String church_name;
    private String church_phone;
    private String church_email;
    private String church_pix_link;
    private String church_uid;
    private Bundle bundle;
    private BottomNavigationView navigation;
    private ActionBar actionBar;
    private Fragment fragment;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhema_hive_church_portal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {

            actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
            }
        } catch (Exception e) {
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }


        getCardView(R.id.church_port_card_mess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(c,RhemaHiveUserPortalAreaActivity.class));
            }
        });
  /*      try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(NullPointerException nu){
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + nu.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();

        }
//        actionBar.setTitle(getString(R.string.menu_home));
//        loadFragment(new HomeFragment());
//        navigation =  findViewById(R.id.bottom_navigation_id);
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new RhemaHiveBottomNavBehaviorClass());
//
//        try {
//            navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    int id  = item.getItemId();
//                    switch(id){
//                        case R.id.item_home:
//                            actionBar.setTitle(getString(R.string.menu_home));
//                            fragment = new HomeFragment();
//                            loadFragment(fragment);
//                            return true;
//                        case R.id.item_messaging:
//                            actionBar.setTitle(getString(R.string.message));
//                            fragment = new ShareFragment();
//                            loadFragment(fragment);
//                            return true;
//                        case R.id.item_publish_news:
//                            actionBar.setTitle(getString(R.string.news_lab));
//                            fragment = new SendFragment();
//                            loadFragment(fragment);
//                            return true;
//                        case R.id.item_media:
//                            actionBar.setTitle(getString(R.string.media_lab));
//                            fragment = new GalleryFragment();
//                            loadFragment(fragment);
//                            return true;
//                        case R.id.item_dashboard:
//                            actionBar.setTitle(getString(R.string.dashboard_lab));
//                            fragment = new ToolsFragment();
//                            loadFragment(fragment);
//                            return true;
//                    }
//                    return false;
//                }
//            });
//        } catch (Exception e) {
//            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
//        }
//
//*/
/*
        try {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);

            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                    R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

        } catch (Exception e) {
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }*/

        FloatingActionButton fab = findViewById(R.id.fab);
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //the NetworkInfo class gets the current state of the device network connection
        networkInfo = connMgr.getActiveNetworkInfo();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        try{
            if (getIntent() != null) {
                if (getIntent().getExtras() != null) {
                    bundle = getIntent().getExtras();
                    if (bundle.containsKey("user_uid")) {
                        if (!TextUtils.isEmpty(bundle.getString("user_uid"))) {
                            church_uid = bundle.getString("user_uid");
                        }
                    }
                }
            }}
        catch(NullPointerException np){
            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }



        try {
            retreiveChurchDetails(church_uid);
        } catch (NullPointerException e) {
            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

    }


    @Override
    protected void onStart() {


        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rhema_hive_church_portal, menu);
        return true;
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    private TextView getTv(int id){
        TextView tv = findViewById(id);
        return tv;
    }

    public FirebaseFirestore getFStore(){
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        return fStore;

    }

    /**
     * this method retreives church details
     * @param id
     * @throws NullPointerException
     */
    public void retreiveChurchDetails(String id) throws NullPointerException{
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
        FirebaseFirestore fS = getFStore();
        rhemaCollRef = fS.collection("rhema_churches");
        rhemaCollRef.whereEqualTo("user_uid", id ).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(DocumentSnapshot documentSnapshot  : queryDocumentSnapshots.getDocuments()){
                        church_name = documentSnapshot.getString("user_church");
                        church_email = documentSnapshot.getString("church_email");
                        church_phone = documentSnapshot.getString("church_phone");
                        church_pix_link = documentSnapshot.getString("church_pix");
                        getTextView(R.id.church_retr_name).setText(church_name);
                        getTextView(R.id.church_retr_email).setText(church_email);
                        getTextView(R.id.church_retr_phone).setText(church_phone);
                        ld_wt_gl(c,church_pix_link,getImg(R.id.church_retr_logg));

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
            getAuto().getToast(c, "Oops ! !..Seems we need an internet connection for this", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }


    }


    public void retrUserDetails(String id){}

    private RhemaHiveAutoUtilsClass getAuto(){
        RhemaHiveAutoUtilsClass rhemaHiveAutoUtilsClass = RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
        return rhemaHiveAutoUtilsClass;
    }


    public TextView getTextView(int id){

        TextView tv = findViewById(id);
        return tv;
    }

    public void ld_wt_gl(Context c, String imgPath,  ImageView imageView) {
        /**GlideApp.with(c)
         .load(c)
         .transforms(new CenterCrop(), new RoundedCorners(20))
         .into(imageView);*/
        GlideApp.with(c).load(imgPath).into(imageView);
        //GlideApp.with(c).load(imgPath).transform(new RoundedCornersTransformation(rad, marg)).into(imageView);
    }


    public ImageView getImg(int id){
        ImageView img = findViewById(id);
        return img;
    }

    private String getUid(){


        return church_uid;

    }

   /* private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/


   private CardView getCardView(int id){
      CardView cardView = findViewById(id);
       return cardView;
   }

}
