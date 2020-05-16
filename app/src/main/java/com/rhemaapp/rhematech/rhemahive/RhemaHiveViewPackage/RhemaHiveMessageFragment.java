package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveRecyclerTouchListener;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveUserMessageAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserMessageModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveRecyclerItemDecoration;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RhemaHiveMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class RhemaHiveMessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RhemaHiveMessageFragment.OnFragmentInteractionListener mListener;
    private RhemaHiveUserMessageAdapter messagedAdapter;

    private ArrayList<RhemaHiveUserMessageModelClass> rModelList;
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private CollectionReference rhemaCollRef;
    private RecyclerView recyclerView;

    private RhemaHiveUserMessageModelClass rModel;
    private ProgressDialog pBar;
    private FirebaseAuth fAuth;
    private String userId;
    private FirebaseFirestore fStore;


    private View v;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RhemaHiveMessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RhemaHiveMessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RhemaHiveMessageFragment newInstance(String param1, String param2) {
        RhemaHiveMessageFragment fragment = new RhemaHiveMessageFragment();

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
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_rhema_hive_message, container, false);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //the NetworkInfo class gets the current state of the device network connection
        networkInfo = connMgr.getActiveNetworkInfo();
        setHasOptionsMenu(true);
        pBar = new ProgressDialog(getContext());

        rModelList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.rv_user_recv_mess_list);
        messagedAdapter = new RhemaHiveUserMessageAdapter( rModelList);
       RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RhemaHiveRecyclerItemDecoration(getContext(),LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(messagedAdapter);
        recyclerView.addOnItemTouchListener(new RhemaHiveRecyclerTouchListener(getContext(), recyclerView, new RhemaHiveRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
              RhemaHiveUserMessageModelClass  rModel = rModelList.get(position);
                getAuto().getToast(getContext(),"position : " + position,RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
               pushId(rModel.getReceiverId(),"receiver_id",MODE_PRIVATE,"rid");
                pushName(rModel.getReceiverName(),"receiver_key",MODE_PRIVATE,"rkey");
               pushPix(rModel.getReceiverPix(),"receiver_pix",MODE_PRIVATE,"rkey");
                pushStat(rModel.getReceiverStat(),"receiver_stat",MODE_PRIVATE,"rStat");
                startActivity(new Intent(getContext(),RhemaHiveMessagingActivity.class));


                // Write your code here

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
//popChats();
String church = retrChurch("user_church",MODE_PRIVATE,"church_name");



   try {
            retrieveRecChats(church,"RhemaHiveUser");
       //retrieveRecAllChats("RhemaHiveUser");
        } catch (NullPointerException e) {
            getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();

        }




        return v;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RhemaHiveMessageFragment.OnFragmentInteractionListener) {
            mListener = (RhemaHiveMessageFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filter_chats, menu);
        MenuItem searchItem = menu.findItem(R.id.filter_rece_chats);
        SearchView searchview = (SearchView) searchItem.getActionView();
        searchview.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                messagedAdapter.getFilter().filter(newText);
                return false;
            }
        });
        //super.onCreateOptionsMenu(menu, inflater);
    }



    public String retrChurch(String key,  int mode,String prefKey) throws NullPointerException{
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(prefKey,mode);
        //if(sharedPreferences.contains("user_church")){}
        return sharedPreferences.getString(key,"church not found");
    }

    private void popChats(){
        rModelList.add(new RhemaHiveUserMessageModelClass("Samuel Iwuchukwu","","I am cool","hu8uy7"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Emeka Fred","","nice","jjhkw8h"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Bekee Chidi","","business driven","hyije8"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Francis ugo","","Investor","hyjdb8"));
        rModelList.add(new RhemaHiveUserMessageModelClass("Timothy Obialor","","Hustler","lk89jkk"));
        //rModelList.add(new RhemaHiveUserMessageModelClass("Okoro Eze","","Engr","kdjmd9"));
        //rModelList.add(new RhemaHiveUserMessageModelClass("Samuel Iwuchukwu","","I am cool","hu8uy7"));
        //rModelList.add(new RhemaHiveUserMessageModelClass("Emeka Fred","","nice","jjhkw8h"));
        //rModelList.add(new RhemaHiveUserMessageModelClass("Bekee Chidi","","business driven","hyije8"));
        //rModelList.add(new RhemaHiveUserMessageModelClass("Francis ugo","","Investor","hyjdb8"));
        //rModelList.add(new RhemaHiveUserMessageModelClass("Timothy Obialor","","Hustler",""));
       //rModelList.add(new RhemaHiveUserMessageModelClass("Okoro Eze","","Engr","kdjmd9"));
        messagedAdapter.notifyDataSetChanged();
    }




    public String retrUserId(String key, int mode, String prefKey){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(prefKey,mode);
        //if(sharedPreferences.contains("user_church")){}
        return sharedPreferences.getString(key,"no id found");
    }

    public String getUserId() throws NullPointerException{

        if(fAuth != null){
            userId = fAuth.getCurrentUser().getUid();

        }

        else{
            getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no id found ",RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

        return userId;
    }

    public RhemaHiveAutoUtilsClass getAuto(){
        return RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
    }



    public void retrieveRecAllChats(String user_type) throws NullPointerException {
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            pBar.show();
            pBar.setMessage("Please wait while we retrieve all chats");

            rhemaCollRef = fStore.collection("rhema_churches");
            rhemaCollRef.whereEqualTo("user_type",user_type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        pBar.dismiss();
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            if(doc.exists()){

                                String user_uid = doc.getString("user_uid");
                                if(!user_uid.equals(getUserId())){

                                    rModelList.add(new RhemaHiveUserMessageModelClass(doc.getString("user_fName") + " " + doc.getString("user_lName"), doc.getString("user_pix"),doc.getString("user_about"), user_uid));
                                    messagedAdapter.notifyDataSetChanged();

                                }


                            }

                            else{
                                getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " No document found ",RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                            }
                        }
                    }
                    else{
                        getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pBar.dismiss();
                    getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            });
        }
        else{
            pBar.dismiss();
            getAuto().getToast(getContext(), RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

    }



    public void retrieveRecChats(String church,String user_type) throws NullPointerException {
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            pBar.show();
            pBar.setMessage("Please wait while we retrieve all chats");

            rhemaCollRef = fStore.collection("rhema_churches");
            rhemaCollRef.whereEqualTo("user_type",user_type).whereEqualTo("user_church",church).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
pBar.dismiss();
                    if(!queryDocumentSnapshots.getDocuments().isEmpty()){
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            if(doc.exists()){
                                String user_uid = doc.getString("user_uid");
                                if(!user_uid.equals(getUserId())){
                                    rModelList.add(new RhemaHiveUserMessageModelClass(doc.getString("user_fName") + "  " + doc.getString("user_lName"),doc.getString("user_pix"), doc.getString("user_about"),user_uid));
                                    messagedAdapter.notifyDataSetChanged();

                                }
                            }
                            else{
                                pBar.dismiss();
                                getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no documents found",RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                            }
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pBar.dismiss();
                    getAuto().getToast(getContext(),RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            });
        }
        else{
            pBar.dismiss();
            getAuto().getToast(getContext(), RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

    }


    //rModelList.add(new RhemaHiveUserMessageModelClass(d.getString("user_fName") + " " + d.getString("user_lName"), d.getString("user_pix"), d.getString("user_about"),d.getString("user_uid")));


    public void pushName( String value,String key , int mode,String prefKey){
        SharedPreferences shPref = getContext().getSharedPreferences(prefKey,mode);
        SharedPreferences.Editor edt = shPref.edit();
        edt.putString(key,value);
        edt.apply();
    }

    public void pushPix( String value,String key , int mode,String prefKey){
        SharedPreferences shPref = getContext().getSharedPreferences(prefKey,mode);
        SharedPreferences.Editor edt = shPref.edit();
        edt.putString(key,value);
        edt.apply();
    }

    public void pushId( String value,String key , int mode,String prefKey){
        SharedPreferences shPref = getContext().getSharedPreferences(prefKey,mode);
        SharedPreferences.Editor edt = shPref.edit();
        edt.putString(key,value);
        edt.apply();
    }

    public void pushStat( String value,String key , int mode,String prefKey){
        SharedPreferences shPref = getContext().getSharedPreferences(prefKey,mode);
        SharedPreferences.Editor edt = shPref.edit();
        edt.putString(key,value);
        edt.apply();
    }





}
