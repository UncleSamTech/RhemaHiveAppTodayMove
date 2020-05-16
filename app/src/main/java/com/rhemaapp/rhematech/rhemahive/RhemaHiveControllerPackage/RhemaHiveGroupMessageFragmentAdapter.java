package com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveGroupChatModelClass;

import java.util.ArrayList;

public class RhemaHiveGroupMessageFragmentAdapter extends RecyclerView.Adapter<RhemaHiveGroupMessageFragmentAdapter.RhemaHiveGroupChatViewHolder> {
    
private RhemaHiveGroupChatModelClass rhemGpModel;
private ArrayList<RhemaHiveGroupChatModelClass> rhemGrpModelList;
private View v;


    public RhemaHiveGroupMessageFragmentAdapter(ArrayList<RhemaHiveGroupChatModelClass> rhemGrpModelList) {
        this.rhemGrpModelList = rhemGrpModelList;
    }

    @NonNull
    @Override
    public RhemaHiveGroupChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_lay_group, parent, false);
        return new RhemaHiveGroupChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RhemaHiveGroupChatViewHolder holder, int position) {
        rhemGpModel =rhemGrpModelList.get(position);
        holder.senderMess.setText(rhemGpModel.getSenderMessage());
        holder.senderTime.setText(rhemGpModel.getSenderTime());
        holder.senderName.setText(rhemGpModel.getSenderName());

    }

    @Override
    public int getItemCount() {
        return rhemGrpModelList.size();
    }

    public class RhemaHiveGroupChatViewHolder extends RecyclerView.ViewHolder {
        TextView senderName;
        TextView senderMess;
        TextView senderTime;
        public RhemaHiveGroupChatViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.user_group_chat_name);
            senderTime  = itemView.findViewById(R.id.tv_chat_group_time);
            senderMess = itemView.findViewById(R.id.tv_chat_group_mess);
        }
    }
}
