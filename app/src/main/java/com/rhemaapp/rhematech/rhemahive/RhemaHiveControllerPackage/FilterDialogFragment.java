package com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.Query;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserModelClass;

public class FilterDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "FilterDialog";

   public interface FilterListener {

        void onFilter(RhemaHiveUserModelClass filters);

    }

    private View mRootView;

    private Spinner mChurchSpinner;
    private Spinner mCountrySpinner;
    private Spinner mGenderSpinner;


    public FilterListener mFilterListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.friend_list_filter, container, false);

        mChurchSpinner = mRootView.findViewById(R.id.church_spinner_category);
        mCountrySpinner = mRootView.findViewById(R.id.country_spinner_city);
        //mSortSpinner = mRootView.findViewById(R.id.spinner_sort);
        mGenderSpinner = mRootView.findViewById(R.id.gender_spinner_category);

        mRootView.findViewById(R.id.button_search).setOnClickListener(this);
        mRootView.findViewById(R.id.button_cancel).setOnClickListener(this);

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FilterListener) {
            mFilterListener = (FilterListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

   @Override
    public void onClick(View v) {
switch (v.getId()) {
        case R.id.button_search:
             onSearchClicked();
                break;
         case R.id.button_cancel:
              onCancelClicked();
               break;
        }
  }

  public void onSearchClicked() {
        if (mFilterListener != null) {
            mFilterListener.onFilter(getFilters());
       }

      dismiss();
    }


    public void onCancelClicked() {
        dismiss();
    }

    @Nullable
    private String getSelectedChurch() {
        String selected = (String) mChurchSpinner.getSelectedItem();
        if (getString(R.string.select_church).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedCountry() {
        String selected = (String) mCountrySpinner.getSelectedItem();
        if (getString(R.string.select_country).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    private String getSelectedGender() {
        String selected = (String) mGenderSpinner.getSelectedItem();
        if (selected.equals(getString(R.string.gend_def))) {
            return null;
        }
        else {
            return selected;
        }
    }

    /*@Nullable
    private String getSelectedSortBy() {
        String selected = (String) mSortSpinner.getSelectedItem();
        if (getString(R.string.sort_by_rating).equals(selected)) {
            return Restaurant.FIELD_AVG_RATING;
        } if (getString(R.string.sort_by_price).equals(selected)) {
            return Restaurant.FIELD_PRICE;
        } if (getString(R.string.sort_by_popularity).equals(selected)) {
            return Restaurant.FIELD_POPULARITY;
        }

        return null;
    }*/

    /*@Nullable
    private Query.Direction getSortDirection() {
        String selected = (String) mSortSpinner.getSelectedItem();

        if (getString(R.string.sort_by_rating).equals(selected)) {
            return Query.Direction.DESCENDING;
        } if (getString(R.string.sort_by_price).equals(selected)) {
            return Query.Direction.ASCENDING;
        } if (getString(R.string.sort_by_popularity).equals(selected)) {
            return Query.Direction.DESCENDING;
        }

        return null;
    }*/

    public void resetFilters() {
        if (mRootView != null) {
            mChurchSpinner.setSelection(0);
            mCountrySpinner.setSelection(0);
            mGenderSpinner.setSelection(0);

        }
    }

    public RhemaHiveUserModelClass getFilters(){
        RhemaHiveUserModelClass rhemModel = new RhemaHiveUserModelClass();
        if(mRootView != null){

            rhemModel.setUser_gender(getSelectedGender());
            rhemModel.setUser_country(getSelectedCountry());
            rhemModel.setUser_church(getSelectedChurch());

        }

        return rhemModel;
    }


/*
    public Filters getFilters() {
        Filters filters = new Filters();

        if (mRootView != null) {
            filters.setCategory(getSelectedCategory());
            filters.setCity(getSelectedCity());
            filters.setPrice(getSelectedPrice());
            filters.setSortBy(getSelectedSortBy());
            filters.setSortDirection(getSortDirection());
        }

        return filters;
    }*/
}

