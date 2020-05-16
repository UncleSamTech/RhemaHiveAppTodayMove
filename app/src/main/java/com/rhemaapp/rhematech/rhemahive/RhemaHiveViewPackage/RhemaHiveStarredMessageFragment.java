package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveRecyclerTouchListener;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveUserMessageAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveUserStarredMessagesAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserMessageModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveRecyclerItemDecoration;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RhemaHiveStarredMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RhemaHiveStarredMessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RhemaHiveStarredMessageFragment.OnFragmentInteractionListener mListener;
    private RhemaHiveUserStarredMessagesAdapter messagedAdapter;

    private ArrayList<RhemaHiveUserMessageModelClass> rModelList;
    private RecyclerView recyclerView;
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private CollectionReference rhemaCollRef;
    private RhemaHiveUserMessageModelClass rModel;
    private ProgressDialog pBar;
    private FirebaseAuth fAuth;
    private String userId;
    private View v;
    private FirebaseFirestore fStore;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RhemaHiveStarredMessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RhemaHiveStarredMessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RhemaHiveStarredMessageFragment newInstance(String param1, String param2) {
        RhemaHiveStarredMessageFragment fragment = new RhemaHiveStarredMessageFragment();
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
        v = inflater.inflate(R.layout.fragment_rhema_hive_starred_message, container, false);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //the NetworkInfo class gets the current state of the device network connection
        networkInfo = connMgr.getActiveNetworkInfo();
        setHasOptionsMenu(true);
        pBar = new ProgressDialog(getContext());
        rModelList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.rv_starred_mess);
        messagedAdapter = new RhemaHiveUserStarredMessagesAdapter( rModelList);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RhemaHiveRecyclerItemDecoration(getContext(),LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messagedAdapter);
        recyclerView.addOnItemTouchListener(new RhemaHiveRecyclerTouchListener(getContext(), recyclerView, new RhemaHiveRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                RhemaHiveUserMessageModelClass  rModel = rModelList.get(position);
               getAuto().getToast(getContext(),"position : " + position, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                //pushId(rModel.getReceiverId(),"receiver_id",MODE_PRIVATE,"rid");
               // pushName(rModel.getReceiverName(),"receiver_key",MODE_PRIVATE,"rkey");
                //pushPix(rModel.getReceiverPix(),"receiver_pix",MODE_PRIVATE,"rkey");
                //pushStat(rModel.getReceiverStat(),"receiver_stat",MODE_PRIVATE,"rStat");
               // startActivity(new Intent(getContext(),RhemaHiveMessagingActivity.class));


                // Write your code here

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

popChats();
        return v;
    }


    public RhemaHiveAutoUtilsClass getAuto(){
        return RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RhemaHiveStarredMessageFragment.OnFragmentInteractionListener) {
            mListener = (RhemaHiveStarredMessageFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void popChats(){
        rModelList.add(new RhemaHiveUserMessageModelClass("Hello","2:15",""));
        rModelList.add(new RhemaHiveUserMessageModelClass("Hello","2:15",""));
        rModelList.add(new RhemaHiveUserMessageModelClass("Hello","2:15",""));
        rModelList.add(new RhemaHiveUserMessageModelClass("Hello","2:15",""));
        rModelList.add(new RhemaHiveUserMessageModelClass("Hello","2:15",""));
        messagedAdapter.notifyDataSetChanged();
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



}


