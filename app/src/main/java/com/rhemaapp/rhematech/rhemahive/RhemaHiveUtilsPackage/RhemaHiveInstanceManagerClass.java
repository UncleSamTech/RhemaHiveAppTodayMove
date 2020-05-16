package com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage;

import androidx.fragment.app.FragmentManager;

import com.rhemaapp.rhematech.rhemahive.MainActivity;
import com.rhemaapp.rhematech.rhemahive.RhemaFirebasePackage.RhemaFireStoreClass;
import com.rhemaapp.rhematech.rhemahive.RhemaFirebasePackage.RhemaHiveFirebaseConnection;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveSwipeViewAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveChurchModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUsersPackage.RhemaHiveChurchSubClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUsersPackage.RhemaHiveUserSubClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUsersPackage.RhemaHiveUserSuperClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.RhemaChurchOnboardingActivity;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.RhemaHiveOnboardingPage;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.RhemaPhoneVerifyActivity;

public class RhemaHiveInstanceManagerClass {
    private static  MainActivity mainActivity = null;
    private static  RhemaHiveThreadUtilsClass rhemaHiveThreadUtilsClass = null;
    private static  RhemaHiveInstanceManagerClass rhemaHiveInstanceManagerClass = null;
    private static RhemaPhoneVerifyActivity rhemaPhoneVerifyActivity = null;
    private static RhemaHiveAutoUtilsClass rhemaHiveAutoUtilsClass = null;
    private static RhemaHiveUserSuperClass rhemaHiveUserSuperClass = null;
    private static RhemaHiveFirebaseConnection rhemaHiveFirebaseConnection = null;
    private static RhemaFireStoreClass fireStoreClass = null ;
    private static RhemaHiveSwipeViewAdapter rhemaHiveSwipeViewAdapter = null;
    private static RhemaHiveUserModelClass userModelClass = null;
    private static RhemaHiveChurchModelClass churchModelClass = null;
    private static RhemaHiveUserSubClass rhemaHiveUserSubClass = null;
    private static RhemaHiveChurchSubClass rhemaHiveChurchSubClass = null;
    private static RhemaHiveOnboardingPage rhemOnboard = null;
    private static RhemaChurchOnboardingActivity rhemaChurchOnboardingActivity = null;







    /**
     * This method creates a single instance of MainActivity
     *
     * @return
     */
    public static  MainActivity getMainActivity() {
        if (mainActivity == null) {
            mainActivity = new MainActivity();
        }
        return mainActivity;
    }

    /**
     * This method returns a singleton instance of RhemaHiveThreadUtilsClass
     *
     * @return
     */
    public static RhemaHiveThreadUtilsClass getRhemaHiveThreadUtilsClass() {
        if (rhemaHiveThreadUtilsClass == null) {
            rhemaHiveThreadUtilsClass = new RhemaHiveThreadUtilsClass();
        }
        return rhemaHiveThreadUtilsClass;
    }

    /**
     * This method creates a singleton instance of RhemaHiveInstanceManagerClass
     *
     * @return
     */
    public static RhemaHiveInstanceManagerClass getRhemaHiveInstanceManagerClass() {
        if (rhemaHiveInstanceManagerClass == null) {
            rhemaHiveInstanceManagerClass = new RhemaHiveInstanceManagerClass();
        }
        return rhemaHiveInstanceManagerClass;
    }

    /**
     * this method is used to get a singleton instance of RhemaPhoneVerifyActivity
     * @return
     */
    public static  RhemaPhoneVerifyActivity getRhemaPhoneVerifyActivity(){
        if(rhemaPhoneVerifyActivity==null){
            rhemaPhoneVerifyActivity = new RhemaPhoneVerifyActivity();
        }
    return rhemaPhoneVerifyActivity;
    }

    /**
     * This method is used to return a singleton instance of RhemaHiveAutoUtilsClass
     * @return
     */
    public static  RhemaHiveAutoUtilsClass getRhemaHiveAutoUtilsClass(){
        if(rhemaHiveAutoUtilsClass==null){
            rhemaHiveAutoUtilsClass = new RhemaHiveAutoUtilsClass();
        }
        return rhemaHiveAutoUtilsClass;
    }

    /**
     * This method is used to return a singleton instance of RhemaHiveUserSuperClass
     * @return
     */
    public static RhemaHiveUserSuperClass getRhemaHiveUserSuperClass(){
        if(rhemaHiveUserSuperClass==null){
            rhemaHiveUserSuperClass = new RhemaHiveUserSuperClass();
        }
        return rhemaHiveUserSuperClass;
    }

    /**]
     *
     *This method returns a singleton instance of RhemaHiveFirebaseConnection class
     * @return
     */
    public static RhemaHiveFirebaseConnection getHiveFirebaseConnection(){
        if(rhemaHiveFirebaseConnection==null){
            rhemaHiveFirebaseConnection = new RhemaHiveFirebaseConnection();
        }

        return rhemaHiveFirebaseConnection;
    }


    /**
     * This creates a singleton method for accessing RhemaHiveFireStoreClass
     * @return
     */
    public static RhemaFireStoreClass getFireStoreClass(){
        if(fireStoreClass==null){
            fireStoreClass = new RhemaFireStoreClass();
        }

        return fireStoreClass;
}

    /**
     * this creates a singleton instance of RhemaHiveSwipeViewAdappter
     * @param fm
     * @return
     */
    public static RhemaHiveSwipeViewAdapter  getRhemaHiveSwipeViewAdapter(FragmentManager fm){
    if(rhemaHiveSwipeViewAdapter == null){
        rhemaHiveSwipeViewAdapter = new RhemaHiveSwipeViewAdapter(fm);
    }
    return rhemaHiveSwipeViewAdapter;
}

    /**
     * this creates a singleton instance of RhemaHiveChurchModelClass
     * @return
     */
    public static RhemaHiveChurchModelClass getChurchModelClass(){
        if(churchModelClass == null){
            churchModelClass = new RhemaHiveChurchModelClass();

        }

        return churchModelClass;
}

    /**
     * This creates a singleton instance of RhemaHiveUserModelClass
     * @return
     */
    public static RhemaHiveUserModelClass getUserModelClass(){
        if(userModelClass==null){
            userModelClass = new RhemaHiveUserModelClass();
        }

        return userModelClass;
}


    /**
     * This creates a singleton instance of RhemaHiveUserSubClass
     * @return
     */
    public static RhemaHiveUserSubClass getRhemaHiveUserSubClass(){
        if(rhemaHiveUserSubClass==null){
            rhemaHiveUserSubClass = new RhemaHiveUserSubClass();
        }

        return rhemaHiveUserSubClass;

}

    /**
     * this method is used to get a singleton instance of RhemaHiveSubClass
     * @return
     */
    public static RhemaHiveChurchSubClass getRhemaHiveChurchSubClass(){
        if(rhemaHiveChurchSubClass==null){
            rhemaHiveChurchSubClass = new RhemaHiveChurchSubClass();
        }

        return rhemaHiveChurchSubClass;
}

public static RhemaHiveOnboardingPage getRhemOnboard(){
        if(rhemOnboard == null){
            rhemOnboard = new RhemaHiveOnboardingPage();
        }

        return rhemOnboard;
}

public static RhemaChurchOnboardingActivity getRhemaChurchOnboardingActivity(){
        if(rhemaChurchOnboardingActivity==null){
            rhemaChurchOnboardingActivity = new RhemaChurchOnboardingActivity();
        }

        return rhemaChurchOnboardingActivity;
}


}
