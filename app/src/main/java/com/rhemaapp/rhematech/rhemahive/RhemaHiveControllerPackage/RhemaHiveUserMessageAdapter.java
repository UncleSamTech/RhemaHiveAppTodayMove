package com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rhemaapp.rhematech.rhemahive.GlideApp;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserMessageModelClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserModelClass;

import java.util.ArrayList;
import java.util.List;

public class RhemaHiveUserMessageAdapter extends RecyclerView.Adapter<RhemaHiveUserMessageAdapter.RhemaHiveUserMessageViewHolder> implements Filterable {

    private List<RhemaHiveUserMessageModelClass> messageList;
    private List<RhemaHiveUserMessageModelClass> filteredMessage;
    private RhemaHiveUserMessageModelClass messageModelClass;
    private View v;

    public RhemaHiveUserMessageAdapter(ArrayList<RhemaHiveUserMessageModelClass> messageList) {
        this.messageList = messageList;
        filteredMessage = new ArrayList<>(messageList);
    }


    @NonNull
    @Override
    public RhemaHiveUserMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_lay, parent, false);
        return new RhemaHiveUserMessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RhemaHiveUserMessageViewHolder holder, int position) {
        messageModelClass = messageList.get(position);
        holder.receiverStat.setText(messageModelClass.getReceiverStat());
        holder.receiverName.setText(messageModelClass.getReceiverName());
        holder.receiverId.setText(messageModelClass.getReceiverId());
        if (messageModelClass.getReceiverPix().length() > 0 && !messageModelClass.getReceiverPix().equalsIgnoreCase("") && messageModelClass.getReceiverPix() != null) {
            GlideApp.with(v).load(messageModelClass.getReceiverPix()).into(holder.receiverPix);
        } else {
            holder.receiverPix.setImageResource(R.drawable.user_male);
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public Filter getFilter() {
        return rhemaFilter;
    }

    public Filter rhemaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
           /* ArrayList<RhemaHiveUserMessageModelClass> rhemaModelList = new ArrayList<>();
            if(charSequence.length() == 0 || charSequence == null ){
               rhemaModelList.addAll(filteredMessage);

            }
            else{

                String filteredEntry  = charSequence.toString().toLowerCase().trim();
                for(RhemaHiveUserMessageModelClass rModel : filteredMessage){
                    if(rModel.getReceiverName().toLowerCase().contains(filteredEntry)){
                        rhemaModelList.add(rModel);
                    }
                }

                filteredMessage = rhemaModelList;
            }
*/

            String value = charSequence.toString();
            if (value.isEmpty()) {
                filteredMessage = messageList;
            } else {
                List<RhemaHiveUserMessageModelClass> filterChatList = new ArrayList<>();
                for (RhemaHiveUserMessageModelClass messageModelClass : messageList) {
                    if (messageModelClass.getReceiverName().toLowerCase().contains(value.toLowerCase())) {
                        filterChatList.add(messageModelClass);
                    }
                }
                filteredMessage = filterChatList;
            }

            FilterResults results = new FilterResults();
            results.values = filteredMessage;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            messageList = (ArrayList<RhemaHiveUserMessageModelClass>) filterResults.values;
            notifyDataSetChanged();

        }


    };

    public class RhemaHiveUserMessageViewHolder extends RecyclerView.ViewHolder {
        public TextView receiverName;
        public TextView receiverStat;
        public ImageView receiverPix;
        public TextView receiverId;

        public RhemaHiveUserMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverName = itemView.findViewById(R.id.user_receiver_name);
            receiverId = itemView.findViewById(R.id.user_receiver_id);
            receiverStat = itemView.findViewById(R.id.user_receiver_stat);
            receiverPix = itemView.findViewById(R.id.img_user_receiver);


        }
    }
}
