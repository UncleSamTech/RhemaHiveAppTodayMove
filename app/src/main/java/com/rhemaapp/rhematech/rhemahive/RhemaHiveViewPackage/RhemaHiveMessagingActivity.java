package com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveChatAreaAdapterClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage.RhemaHiveUserMessageAdapter;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserMessageModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUsersPackage.RhemaHiveUserSubClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveRecyclerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RhemaHiveMessagingActivity extends AppCompatActivity {
    private ArrayList<RhemaHiveUserMessageModelClass> rModelList;
    private RhemaHiveChatAreaAdapterClass chatAreaAdapterClass;
    private NetworkInfo networkInfo;
    private ConnectivityManager connMgr;
    private CollectionReference rhemaCollRef;
    private RecyclerView recyclerView;
    private ProgressDialog pBar;
    private FirebaseAuth fAuth;
    private FloatingActionButton fab;
    private String userId;
    private FirebaseFirestore fStore;
    RhemaHiveUserMessageModelClass rhemModel;
    private String chatEntry;
    private String rId;

    private View v;
    private Context c = RhemaHiveMessagingActivity.this;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhema_hive_messaging);
        try {
            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();

            connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            //the NetworkInfo class gets the current state of the device network connection
            networkInfo = connMgr.getActiveNetworkInfo();
            pBar = new ProgressDialog(c);
            rId = retrRecId("receiver_id",MODE_PRIVATE,"rid");

            rModelList = new ArrayList<>();
            recyclerView = findViewById(R.id.rv_chat_area);
        } catch (Exception e) {
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
        }


        getFab(R.id.fab_message_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setSenderChat(retrChatEntry(),getSenderId(),rId,retTime(),1,new RhemaHiveUserSubClass().getmessageStatus(0),new RhemaHiveUserSubClass().getMessageType(0));
            getEdt(R.id.edt_chat_area).setText("");
            }


        });
        chatAreaAdapterClass = new RhemaHiveChatAreaAdapterClass(rModelList);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new RhemaHiveRecyclerItemDecoration(c,LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(chatAreaAdapterClass);
        try {
            //ret
            //popRecChat();
            retrieveRecChatList(getSenderId(),rId);
            //retrieveSenderChatList(getSenderId(),rId);



        } catch (NullPointerException e) {
            getAuto().getToast(c, RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
        }


    }


    /**
     * this is a shared preference method for retreieving receiverId
     * @param key
     * @param mode
     * @param prefKey
     * @return
     */
    public String retrRecId(String key,  int mode,String prefKey){
        SharedPreferences sharedPreferences = getSharedPreferences(prefKey,mode);
        //if(sharedPreferences.contains("user_church")){}
        return sharedPreferences.getString(key,null);
    }


    /**
     * this is a sharedpreference method for retreiving receiver name
     * @param key
     * @param mode
     * @param prefKey
     * @return
     */
    public String retrRecName(String key,  int mode,String prefKey){
        SharedPreferences sharedPreferences = getSharedPreferences(prefKey,mode);
        //if(sharedPreferences.contains("user_church")){}
        return sharedPreferences.getString(key,null);
    }


    /**
     * this is a sharedpreferences method for retreiving receiver pix
     * @param key
     * @param mode
     * @param prefKey
     * @return
     */
    public String retrRecPix(String key,  int mode,String prefKey){
        SharedPreferences sharedPreferences = getSharedPreferences(prefKey,mode);
        //if(sharedPreferences.contains("user_church")){}
        return sharedPreferences.getString(key,null);
    }

    /**
     * this is a demo for retrieving receiver status
     * @param key
     * @param mode
     * @param prefKey
     * @return
     */
    public String retrRecStat(String key,  int mode,String prefKey){
        SharedPreferences sharedPreferences = getSharedPreferences(prefKey,mode);
        //if(sharedPreferences.contains("user_church")){}
        return sharedPreferences.getString(key,null);
    }


    /**
     * this is a demo for populating the senders chat
     * @throws NullPointerException
     */
    public void popSendChat() throws NullPointerException{
        rModelList.add(new RhemaHiveUserMessageModelClass("Hello","2:23",1));
        rModelList.add(new RhemaHiveUserMessageModelClass("Hi","3:23",1));
        rModelList.add(new RhemaHiveUserMessageModelClass("Thank u","4:23",1));
        rModelList.add(new RhemaHiveUserMessageModelClass("are u there","5:23",1));
        rModelList.add(new RhemaHiveUserMessageModelClass("move out","6:23",1));
        rModelList.add(new RhemaHiveUserMessageModelClass("come in ","9:23",1));
        chatAreaAdapterClass.notifyDataSetChanged();
    }

    /**
     * this is a demo receive chat
     * @throws NullPointerException
     */
    public void popRecChat() throws NullPointerException{
        rModelList.add(new RhemaHiveUserMessageModelClass("Hello","2:23",2));
        rModelList.add(new RhemaHiveUserMessageModelClass("Hi","3:23",2));
        rModelList.add(new RhemaHiveUserMessageModelClass("Thank u","4:23",2));
        rModelList.add(new RhemaHiveUserMessageModelClass("are u there","5:23",2));
        rModelList.add(new RhemaHiveUserMessageModelClass("move out","6:23",2));
       rModelList.add(new RhemaHiveUserMessageModelClass("come in ","9:23",2));
        chatAreaAdapterClass.notifyDataSetChanged();
    }

    public RhemaHiveAutoUtilsClass getAuto(){
        return RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
    }

    /**
     * this saves chat to database
     * @param senderId
     * @param receiverId
     * @param message
     * @param time
     * @param isMessStared
     * @param messageStatus
     * @param messageType
     */
    public void saveChatToDb(String senderId, String receiverId,String message, String time, boolean isMessStared,String messageStatus, String messageType){
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            rhemaCollRef = fStore.collection("rhema_churches");
            rhemaCollRef.document(senderId).collection(receiverId).document(time).set(getModel(message,time,isMessStared,messageStatus,messageType)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        getAuto().getToast(c,"Message saved successfully",RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                    }
                    else{
                        getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + task.getException().getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(),RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                }
            });
        }
        else {
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(),RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
        }
    }


    /**
     * this retrieves the model for syncing message to firebase
     * @param userMessage
     * @param messageTimeStamp
     * @param isMessageStarred
     * @param messageStatus
     * @param messageType
     * @return
     */
    public RhemaHiveUserMessageModelClass getModel(String userMessage, String messageTimeStamp, boolean isMessageStarred,  String messageStatus, String messageType){

return new RhemaHiveUserMessageModelClass(userMessage,messageTimeStamp,isMessageStarred,messageStatus,messageType);
    }



    public void setSenderChat(String message, String senderId, String receiverId, String time, int id,String messStat, String messType) throws NullPointerException{

        if(new RhemaHiveUserSubClass().checkMessageParams(message,time,messStat,messType)==1){
            saveChatToDb(senderId,receiverId,message,time,false,messStat,messType);
        }

        rModelList.add(new RhemaHiveUserMessageModelClass(message,time,id));
        chatAreaAdapterClass.notifyDataSetChanged();
    }


    /**
     * this is used for retrieving chat list
     * @param senderId
     * @param receiverId
     * @throws NullPointerException
     */
    public void retrieveRecChatList(String  senderId, String receiverId) throws NullPointerException{
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            rhemaCollRef = fStore.collection("rhema_churches");
            rhemaCollRef.document(receiverId).collection(senderId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.getDocuments().isEmpty()){
                        for(DocumentSnapshot  doc : queryDocumentSnapshots.getDocuments()){
                            if(doc.exists()){
                                rModelList.add(new RhemaHiveUserMessageModelClass(doc.getString("userMessage"),doc.getString("messageTimeStamp"),1));
                                chatAreaAdapterClass.notifyDataSetChanged();
                            }
                            else{
                                getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no such document exists ", RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                            }
                        }
                    }
                    else{
                        getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " empty docs ", RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                }
            });
        }

        else{
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(), RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
        }
    }

    /**
     * this method retrieves senders chat list
     * @param senderId
     * @param receiverId
     * @throws NullPointerException
     */
    public void retrieveSenderChatList(String  senderId, String receiverId) throws NullPointerException{
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isConnected()) {
            rhemaCollRef = fStore.collection("rhema_churches");
            rhemaCollRef.document(senderId).collection(receiverId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.getDocuments().isEmpty()){
                        for(DocumentSnapshot  doc : queryDocumentSnapshots.getDocuments()){
                            if(doc.exists()){
                                rModelList.add(new RhemaHiveUserMessageModelClass(doc.getString("userMessage"),doc.getString("messageTimeStamp"),1));
                                chatAreaAdapterClass.notifyDataSetChanged();
                            }
                            else{
                                getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " no such document exists ", RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                            }
                        }
                    }
                    else{
                        getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " empty docs ", RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + e.getLocalizedMessage(), RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
                }
            });
        }

        else{
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + networkInfo.getExtraInfo(), RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
        }
    }

    /**
     *
     * @return
     * @throws NullPointerException
     */
    private String retrChatEntry() throws NullPointerException{
        if(!TextUtils.isEmpty(getEdt(R.id.edt_chat_area).getText().toString().trim())) {
           chatEntry =  getEdt(R.id.edt_chat_area).getText().toString().trim();


        }
        else{
            getAuto().getToast(c,RhemaHiveClassReferenceConstants.ERROR_MESSAGE_GENERIC + " values are blank ", RhemaHiveClassReferenceConstants.TOAST_LONG_LEN).show();
        }

        return chatEntry;
    }

    /**
     * this method returns the current time
     * @return
     * @throws NullPointerException
     */
    public String retTime() throws  NullPointerException{
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        return currentTime;
    }

    /**
     * this method returns an object for FloatingActionButton
     * @param id
     * @return
     */
    private FloatingActionButton getFab(int id){
        FloatingActionButton fab = findViewById(id);
        return fab;
    }

    /**
     * this method returns an object for TextInputEdit
     * @param id
     * @return
     */
    private TextInputEditText getEdt(int id){
        TextInputEditText tEdt = findViewById(id);
        return tEdt;
    }

    /**
     *
     * @return
     * @throws NullPointerException
     */
    public String getSenderId() throws NullPointerException{
        if(fAuth != null){
            userId = fAuth.getCurrentUser().getUid();

        }

        return userId;
    }


}
