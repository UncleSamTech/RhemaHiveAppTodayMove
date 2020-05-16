package com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;

import com.rhemaapp.rhematech.rhemahive.R;

public class RhemaHiveEmailAuthenticationAdapter extends DialogFragment implements AppCompatEditText.OnEditorActionListener  {

    AlertDialog.Builder builder;
    AppCompatEditText appEdt;
    RhemaHiveEmailAuthenticationAdapter.RhemaHiveEmailVerListener rhemaHiveEmailVerListener;

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v  = inflater.inflate(R.layout.email_verifier,null);
        appEdt = v.findViewById(R.id.email_app_edt_email_ver);

        appEdt.setOnEditorActionListener(this);
        builder.setView(v).setPositiveButton(R.string.ver_email, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rhemaHiveEmailVerListener.onDialogPositiveClick(appEdt.getText().toString().trim());
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rhemaHiveEmailVerListener.onDialogNegativeClick(RhemaHiveEmailAuthenticationAdapter.this);

            }
        });
        return builder.create();
    }



    @Override
    public void onAttach(@NonNull Context context) {
        try{
            rhemaHiveEmailVerListener = (RhemaHiveEmailAuthenticationAdapter.RhemaHiveEmailVerListener) context;
        }catch(ClassCastException e){

            throw new ClassCastException(context.toString()
                    + " must implement Forgot Password Listener");
        }
        super.onAttach(context);
    }





    public interface RhemaHiveEmailVerListener{
        void onDialogPositiveClick(String email);
        void onDialogNegativeClick(DialogFragment dialogFragment);
    }
}
