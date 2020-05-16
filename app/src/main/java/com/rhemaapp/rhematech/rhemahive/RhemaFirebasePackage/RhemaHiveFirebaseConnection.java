package com.rhemaapp.rhematech.rhemahive.RhemaFirebasePackage;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RhemaHiveFirebaseConnection {
    private FirebaseAuth rhemaFirebaseAuth;
    private FirebaseUser fbUser;



    public FirebaseAuth getRhemaFirebaseAuth(){
        rhemaFirebaseAuth = FirebaseAuth.getInstance();

        return rhemaFirebaseAuth;
    }

    public FirebaseUser getUser(){
        fbUser = getRhemaFirebaseAuth().getCurrentUser();
        return fbUser;
    }
}
