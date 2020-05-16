package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.ui.tools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToolsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ToolsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Dashboard Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}