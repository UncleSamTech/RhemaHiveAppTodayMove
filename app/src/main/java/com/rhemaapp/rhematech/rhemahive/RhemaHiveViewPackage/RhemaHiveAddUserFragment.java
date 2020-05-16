package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.FilterDialogFragment;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveAddUserAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveUserMessageAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserMessageModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveRecyclerItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RhemaHiveAddUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RhemaHiveAddUserFragment extends Fragment implements View.OnClickListener, FilterDialogFragment.FilterListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private NetworkInfo networkInfo;
    private ProgressDialog pBar;
    private FirebaseAuth fAuth;
    private String userId;
    private View v;
    private FirebaseFirestore fStore;
    private ConnectivityManager connMgr;
    private CollectionReference rhemaCollRef;
    private FilterDialogFragment mFilterDialog;
    private RecyclerView recyclerView;
    private RhemaHiveAddUserFragment.OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RhemaHiveAddUserAdapter messagedAdapter;

    private ArrayList<RhemaHiveUserMessageModelClass> rModelList;

    public RhemaHiveAddUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RhemaHiveAddUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RhemaHiveAddUserFragment newInstance(String param1, String param2) {
        RhemaHiveAddUserFragment fragment = new RhemaHiveAddUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //the NetworkInfo class gets the current state of the device network connection
        networkInfo = connMgr.getActiveNetworkInfo();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

v = inflater.inflate(R.layout.fragment_rhema_hive_add_user, container, false);
        setHasOptionsMenu(true);
        pBar = new ProgressDialog(getContext());
        mFilterDialog = new FilterDialogFragment();

        rModelList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.rv_add_user);
        messagedAdapter = new RhemaHiveAddUserAdapter( rModelList);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RhemaHiveRecyclerItemDecoration(getContext(),LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messagedAdapter);
        popData();
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()){
                case R.id.filter_bar:

                    onFilterClicked();

                    break;
                case R.id.button_clear_filter:

                    onClearFilterClicked();
                    break;
            }
        } catch (NullPointerException e) {
            getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }


    }


    public void onFilterClicked() throws NullPointerException {
        // Show the dialog containing filter options
        getAuto().getToast(getContext(),"Filter button clicked",RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        mFilterDialog.show(getActivity().getSupportFragmentManager(),FilterDialogFragment.TAG);
    }

    public void onClearFilterClicked() {
        getAuto().getToast(getContext(),"clear button clicked",RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        mFilterDialog.resetFilters();

        //onFilter(.getDefault());
    }

    @Override
    public void onFilter(RhemaHiveUserModelClass filters) {
searchFriendsByChurch(filters.getUser_church());

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void popData(){
        rModelList.add(new RhemaHiveUserMessageModelClass("Samuel Iwuchukwu","","I am cool","hu8uy7"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Emeka Fred","","nice","jjhkw8h"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Bekee Chidi","","business driven","hyije8"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Francis ugo","","Investor","hyjdb8"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Timothy Obialor","","Hustler",""));
        rModelList.add(new RhemaHiveUserMessageModelClass("Okoro Eze","","Engr","kdjmd9"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Samuel Iwuchukwu","","I am cool","hu8uy7"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Emeka Fred","","nice","jjhkw8h"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Bekee Chidi","","business driven","hyije8"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Francis ugo","","Investor","hyjdb8"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Timothy Obialor","","Hustler",""));
        rModelList.add(new RhemaHiveUserMessageModelClass("Okoro Eze","","Engr","kdjmd9"));
        messagedAdapter.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RhemaHiveAddUserFragment.OnFragmentInteractionListener) {
            mListener = (RhemaHiveAddUserFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * this searches by country
     * @param country
     */
    public void searchFriendsByCountry(String country){
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            rhemaCollRef = fStore.collection("rhema_churches");
            rhemaCollRef.whereEqualTo("user_country",country).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot d : task.getResult()){
                            if(d.exists()){
                                //retrieve all here
                            }

                            else{
                                getAuto().getToast(getContext(), RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no data exists ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            }
                        }
                    }
                    else{
                        getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            });
        }

        else{
            getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

    }


    /**
     * this searches by gender
     * @param gender
     */
    public void searchFriendsByGender(String gender){
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            rhemaCollRef = fStore.collection("rhema_churches");
            rhemaCollRef.whereEqualTo("user_gender",gender).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot d : task.getResult()){
                            if(d.exists()){
                                //retrieve all here
                            }

                            else{
                                getAuto().getToast(getContext(), RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no data exists ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            }
                        }
                    }
                    else{
                        getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            });
        }

        else{
            getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

    }

    /**
     * this searches by church
     * @param church
     */
    public void searchFriendsByChurch(String church){
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            rhemaCollRef = fStore.collection("rhema_churches");
            rhemaCollRef.whereEqualTo("user_church",church).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                   if(task.isSuccessful()){
                       for(DocumentSnapshot d : task.getResult()){
                           if(d.exists()) {
                               //retrieve all here
                               rModelList.add(new RhemaHiveUserMessageModelClass(d.getString("fName") + " " + d.getString("lName"), d.getString("user_pix"), d.getString("user_about"), d.getString("user_uid")));
                               messagedAdapter.notifyDataSetChanged();
                           }
                           else{
                               getAuto().getToast(getContext(), RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no data exists ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                           }
                       }
                   }
                   else{
                       getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                   }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            });
        }

        else{
            getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

    }


    public RhemaHiveAutoUtilsClass getAuto(){
        return RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
    }

    /**
     * this searches by church, gender and country
     * @param church
     * @param gend
     * @param country
     */
    public void searchFriendsByChurchGendCountry(String church,String gend,String country){
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            rhemaCollRef = fStore.collection("rhema_churches");
            rhemaCollRef.whereEqualTo("user_church",church).whereEqualTo("user_country",country).whereEqualTo("user_gender",gend).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot d : task.getResult()){
                            if(d.exists()){
                                //retrieve all here
                            }

                            else{
                                getAuto().getToast(getContext(), RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no data exists ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            }
                        }
                    }
                    else{
                        getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            });
        }

        else{
            getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

    }

    /**
     * this searches for friends by church and gender
     * @param church
     * @param gend
     */
    public void searchFriendsByChurchGend(String church,String gend){
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            rhemaCollRef = fStore.collection("rhema_churches");
            rhemaCollRef.whereEqualTo("user_church",church).whereEqualTo("user_gender",gend).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot d : task.getResult()){
                            if(d.exists()){
                                //retrieve all here
                            }

                            else{
                                getAuto().getToast(getContext(), RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no data exists ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            }
                        }
                    }
                    else{
                        getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            });
        }

        else{
            getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

    }


    /**
     * this searches by gender and country
     * @param gend
     * @param country
     */
    public void searchFriendsByGenderCountry(String gend,String country){
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            rhemaCollRef = fStore.collection("rhema_churches");
            rhemaCollRef.whereEqualTo("user_gender",gend).whereEqualTo("user_country",country).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot d : task.getResult()){
                            if(d.exists()){
                                //retrieve all here
                            }

                            else{
                                getAuto().getToast(getContext(), RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no data exists ", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                            }
                        }
                    }
                    else{
                        getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            });
        }

        else{
            getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

    }





}
