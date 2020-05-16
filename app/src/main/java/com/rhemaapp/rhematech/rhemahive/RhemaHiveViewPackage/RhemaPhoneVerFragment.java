package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseUser;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaFirebasePackage.RhemaHiveFirebaseConnection;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RhemaPhoneVerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RhemaPhoneVerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RhemaPhoneVerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AuthUI.IdpConfig phoneUi;
    private static ActionCodeSettings actSettings;
    private List<AuthUI.IdpConfig> providers;
    private RhemaHiveFirebaseConnection user;;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseUser f_user;

    private OnFragmentInteractionListener mListener;

    public RhemaPhoneVerFragment() {
        // Required empty public constructor
    }

    private List<AuthUI.IdpConfig> getProviders(){
        providers = Arrays.asList(
                getPhoneUi()
        );

        return providers;
    }

    public void loadAuthUI(){

        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(getProviders()).setLogo(R.drawable.rhema_logg).setTheme(R.style.AppTheme_NoActionBar2).build(), RhemaHiveClassReferenceConstants.RC_SIGN_IN_ID);



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RhemaHiveClassReferenceConstants.RC_SIGN_IN_ID) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode==RESULT_OK){
                // Successfully signed in
                try{
                    f_user  = getUser().getUser();
                    String dispName = f_user.getDisplayName();
                    getAuto().getToast(getContext(),"Welcome : " +  dispName, RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                    startActivity(RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass().newActivityStarter(getContext(), RhemaHiveDecisionActivity.class));
                }
                catch(NullPointerException np){
                    getAuto().getToast(getContext(), RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + np.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
                }
            }

        }

        }

    public RhemaHiveAutoUtilsClass getAuto(){
        RhemaHiveAutoUtilsClass autoUtilsClass = RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
        return autoUtilsClass;
    }


    public AuthUI.IdpConfig getPhoneUi(){
        phoneUi = new AuthUI.IdpConfig.PhoneBuilder().build();
        return phoneUi;
    }


    public RhemaHiveFirebaseConnection getUser(){
        user = RhemaHiveInstanceManagerClass.getHiveFirebaseConnection();
        return user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RhemaPhoneVerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RhemaPhoneVerFragment newInstance(String param1, String param2) {
        RhemaPhoneVerFragment fragment = new RhemaPhoneVerFragment();
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

        getBtn(R.id.btn_phone_ver_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAuthUI();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rhema_phone_ver, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private Button getBtn(int id){
        View v = new View(getContext());
        Button btn  = v.findViewById(id);
        return btn;
    }
}
