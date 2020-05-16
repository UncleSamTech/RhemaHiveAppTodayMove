package com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage;


import android.os.Bundle;

import androidx.fragment.app.ListFragment;

public class RhemaHiveMessageTabListFragment extends ListFragment {

    static RhemaHiveMessageTabListFragment newInstance(int num){
        RhemaHiveMessageTabListFragment rhemaHiveMessageTabListFragment = new RhemaHiveMessageTabListFragment();
        Bundle args = new Bundle();
        args.putInt("num",num);
        rhemaHiveMessageTabListFragment.setArguments(args);
        return rhemaHiveMessageTabListFragment;
    }


}
