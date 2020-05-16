package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;

import java.util.regex.Pattern;

public class RhemaHiveTermsCondition extends AppCompatActivity {
    RhemaHiveAutoUtilsClass rhemaHiveAutoUtilsClass;
    Button btn;
    CheckBox radioButton;
    Context c = RhemaHiveTermsCondition.this;
    TextView tv;
    NetworkInfo networkInfo;
    private ConnectivityManager connMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhema_hive_terms_condition);
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        getBtn(R.id.btn_click_cont).setVisibility(RhemaHiveClassReferenceConstants.VIEW_GONE);
        radioButton = getRad(R.id.rhema_tc_radio);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton.isChecked()) {

                    getBtn(R.id.btn_click_cont).setVisibility(RhemaHiveClassReferenceConstants.VIEW_VIS);

                } else {
                    getBtn(R.id.btn_click_cont).setVisibility(RhemaHiveClassReferenceConstants.VIEW_GONE);
                }

            }
        });
        getBtn(R.id.btn_click_cont).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRhemaHiveAutoUtilsClass().getToast(c, " Congrats Hiver !", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
               startActivity(getRhemaHiveAutoUtilsClass().newActivityStarter(c, RhemaHiveAuthenticateActivity.class));
            }
        });


        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isConnectedOrConnecting()) {
            loadTerms();
        } else {
            getRhemaHiveAutoUtilsClass().getToast(c, "Oops ! No internet on Phone", RhemaHiveClassReferenceConstants.TOAST_SHORT_LEN).show();
        }


    }

    public RhemaHiveAutoUtilsClass getRhemaHiveAutoUtilsClass() {
        rhemaHiveAutoUtilsClass = RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
        return rhemaHiveAutoUtilsClass;
    }

    public TextView getTv(int id) {
        tv = findViewById(id);
        return tv;
    }

    private void loadTerms() {
        getTv(R.id.tc_tv_read_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/18U8Nwcr86qdGQysty0hii7_A5OwI0VQvyNyZGRFpCX0/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });
    }


    public Button getBtn(int id) {
        btn = findViewById(id);
        return btn;
    }

    public CheckBox getRad(int id) {
        radioButton = findViewById(id);
        return radioButton;
    }


}
