package com.rhemaapp.rhematech.rhemahive.RhemaFirebasePackage;

import com.google.firebase.firestore.FirebaseFirestore;

public class RhemaFireStoreClass {
    FirebaseFirestore db;

    public FirebaseFirestore getFireStore(){
        db = FirebaseFirestore.getInstance();
        return db;
    }
}
