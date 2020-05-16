package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveBottomNavBehaviorClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.ui.gallery.GalleryFragment;

public class RhemaHiveUserPortalAreaActivity extends AppCompatActivity implements RhemaHiveMessageFragment.OnFragmentInteractionListener,RhemaHiveStarredMessageFragment.OnFragmentInteractionListener,RhemaHiveGroupMessageFragment.OnFragmentInteractionListener,RhemaHiveAddUserFragment.OnFragmentInteractionListener {
    Context c  = RhemaHiveUserPortalAreaActivity.this;
    private BottomNavigationView navigation;
    private ActionBar actionBar;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rhema_hive_user_portal_area);
        //getAuto().getToast(c, " Church " +  retrChurch("user_church",MODE_PRIVATE,"church_name"), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        //getAuto().getToast(c, " Church " +  retrChurch("user_uid",MODE_PRIVATE,"uid"), RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        actionBar = getSupportActionBar();

        actionBar.setTitle(getString(R.string.message));
loadFragment(new RhemaHiveMessageFragment());
       navigation =  findViewById(R.id.bottom_navigation_id);
     CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
 layoutParams.setBehavior(new RhemaHiveBottomNavBehaviorClass());

        try {
           navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
             @Override



             public boolean onNavigationItemSelected(@NonNull MenuItem item) {                  int id  = item.getItemId();
                  switch(id){
                     case R.id.item_message:
                            actionBar.setTitle(getString(R.string.message));
                          fragment = new RhemaHiveMessageFragment();
                         loadFragment(fragment);
                          return true;
                        case R.id.item_group_chats:
                           actionBar.setTitle(getString(R.string.group_chats));
                           fragment = new RhemaHiveGroupMessageFragment();
                           loadFragment(fragment);
                           return true;
                       case R.id.item_starred_chats:
                           actionBar.setTitle(getString(R.string.starred_chats));
                     fragment = new RhemaHiveStarredMessageFragment();
                         loadFragment(fragment);
                         return true;

                      case R.id.item_add_chats:
actionBar.setTitle(getString(R.string.add_friends));
                          fragment = new RhemaHiveAddUserFragment();
                          loadFragment(fragment);
                          return true;


                    }
                return false;
             }
           });
      } catch (Exception e) {
         getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
       }

    }





public String retrChurch(String key,  int mode,String prefKey){
    SharedPreferences sharedPreferences = getSharedPreferences(prefKey,mode);
    //if(sharedPreferences.contains("user_church")){}
    return sharedPreferences.getString(key,null);
}

    public String retrUserId(String key,  int mode,String prefKey){
        SharedPreferences sharedPreferences = getSharedPreferences(prefKey,mode);
        //if(sharedPreferences.contains("user_church")){}
        return sharedPreferences.getString(key,null);
    }

    public RhemaHiveAutoUtilsClass getAuto(){
        return RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
