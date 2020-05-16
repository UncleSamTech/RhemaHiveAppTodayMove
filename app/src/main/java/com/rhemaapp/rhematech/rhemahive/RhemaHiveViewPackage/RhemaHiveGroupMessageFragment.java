package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveGroupMessageFragmentAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveGroupChatModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserMessageModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveRecyclerItemDecoration;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RhemaHiveGroupMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RhemaHiveGroupMessageFragment extends Fragment {
    private ArrayList<RhemaHiveGroupChatModelClass> rModelList;
    private RecyclerView recyclerView;
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private CollectionReference rhemaCollRef;
    private RhemaHiveGroupChatModelClass rModel;
    private ProgressDialog pBar;
    private FirebaseAuth fAuth;
    private String userId;
    private FirebaseFirestore fStore;
    private RhemaHiveGroupMessageFragmentAdapter messageFragmentAdapter;
    private FloatingActionButton fab;


    private View v;


    private RhemaHiveGroupMessageFragment.OnFragmentInteractionListener mListener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RhemaHiveGroupMessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RhemaHiveGroupMessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RhemaHiveGroupMessageFragment newInstance(String param1, String param2) {
        RhemaHiveGroupMessageFragment fragment = new RhemaHiveGroupMessageFragment();
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
        try {
            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();

            connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            //the NetworkInfo class gets the current state of the device network connection
            networkInfo = connMgr.getActiveNetworkInfo();
            // Inflate the layout for this fragment
            pBar = new ProgressDialog(getContext());

            v = inflater.inflate(R.layout.fragment_rhema_hive_group_message, container, false);
            fab = v.findViewById(R.id.fab_chat_send_mess);

            rModelList = new ArrayList<>();
            recyclerView = v.findViewById(R.id.rv_grp_chat);
            messageFragmentAdapter = new RhemaHiveGroupMessageFragmentAdapter(rModelList);
            RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new RhemaHiveRecyclerItemDecoration(getContext(),LinearLayoutManager.VERTICAL, 16));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(messageFragmentAdapter);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendGroupChat(getEdt(R.id.grp_edt_mess).getText().toString().trim(),retTime(),"Samuel ");
                    getEdt(R.id.grp_edt_mess).setText("");
                }
            });

            popGpChats();
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
        if (context instanceof RhemaHiveGroupMessageFragment.OnFragmentInteractionListener) {
            mListener = (RhemaHiveGroupMessageFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    public void popGpChats() throws NullPointerException{
        rModelList.add(new RhemaHiveGroupChatModelClass("Chidi","Im good","2:34pm"));
        rModelList.add(new RhemaHiveGroupChatModelClass("Rex","Thanks","2:35pm"));
        rModelList.add(new RhemaHiveGroupChatModelClass("Mark","Welcome Man","3:34pm"));
        rModelList.add(new RhemaHiveGroupChatModelClass("Fred","how is life","8:34pm"));
        rModelList.add(new RhemaHiveGroupChatModelClass("Daniel","keep moving","5:34pm"));
        messageFragmentAdapter.notifyDataSetChanged();
    }

    public void sendGroupChat(String mess, String time,String name) throws NullPointerException{
        if(mess != null && time != null && name !=null && !mess.equals("") && !time.equals("") && !name.equals("") && mess.length() > 0 && name.length() > 0 && time.length() > 0){
            rModelList.add(new RhemaHiveGroupChatModelClass(name,mess,time));
            messageFragmentAdapter.notifyDataSetChanged();
        }

        else{
            getAuto().getToast(getContext(), RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " some null data",RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }

    }


    public RhemaHiveAutoUtilsClass getAuto(){
        return RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
    }

    public String retTime(){
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        return currentTime;
    }

    private TextView getTv(int id){
        TextView tv = v.findViewById(id);
        return tv;
    }

    private EditText getEdt(int id){
        EditText edt = v.findViewById(id);
        return edt;
    }
}
