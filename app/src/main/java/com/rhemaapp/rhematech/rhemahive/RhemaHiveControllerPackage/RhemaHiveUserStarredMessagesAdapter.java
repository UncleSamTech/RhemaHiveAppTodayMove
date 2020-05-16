package com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage;

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

import java.util.ArrayList;
import java.util.List;

public class RhemaHiveUserStarredMessagesAdapter extends RecyclerView.Adapter<RhemaHiveUserStarredMessagesAdapter.RhemaHiveStarredMessViewHolder> implements Filterable {
    private View v;
    private RhemaHiveUserMessageModelClass messageModelClass;
    private List<RhemaHiveUserMessageModelClass> rhemaHiveUserMessageModelClasses;
    private List<RhemaHiveUserMessageModelClass> filteredList;

    public RhemaHiveUserStarredMessagesAdapter(ArrayList<RhemaHiveUserMessageModelClass> rhemaHiveUserMessageModelClasses) {
        this.rhemaHiveUserMessageModelClasses = rhemaHiveUserMessageModelClasses;
        this.filteredList = new ArrayList<>(rhemaHiveUserMessageModelClasses);
    }

    @NonNull
    @Override
    public RhemaHiveStarredMessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_starred_lay, parent, false);
        return new RhemaHiveStarredMessViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RhemaHiveStarredMessViewHolder holder, int position) {
        messageModelClass = rhemaHiveUserMessageModelClasses.get(position);
        holder.tvTimeStarred.setText(messageModelClass.getReceiveMessTime());
        holder.tvMessageStarred.setText(messageModelClass.getReceiverMessage());
        if(messageModelClass.getReceiverPix() != null && messageModelClass.getReceiverPix().length() > 0 && !messageModelClass.getReceiverPix().equals("")){

            GlideApp.with(v).load(messageModelClass.getReceiverPix()).into(holder.imgStarred);
        } else {
            holder.imgStarred.setImageResource(R.drawable.user_male);
        }
    }

    @Override
    public int getItemCount() {
        return rhemaHiveUserMessageModelClasses.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charText = charSequence.toString().toLowerCase().trim();
            if(charText.isEmpty()){
                filteredList = rhemaHiveUserMessageModelClasses;

            }
            else{
                List<RhemaHiveUserMessageModelClass> filteredChatsList = new ArrayList<>();
                for(RhemaHiveUserMessageModelClass userMessageModelClass : rhemaHiveUserMessageModelClasses){
                    if(userMessageModelClass.getReceiverMessage().toLowerCase().contains(charText)){
                        filteredChatsList.add(userMessageModelClass);

                    }
                }
                filteredList = filteredChatsList;

            }


            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            rhemaHiveUserMessageModelClasses = (ArrayList<RhemaHiveUserMessageModelClass>)filterResults.values;
            notifyDataSetChanged();

        }
    };

    public class RhemaHiveStarredMessViewHolder extends RecyclerView.ViewHolder{
    TextView tvMessageStarred;
    TextView tvTimeStarred;
    ImageView imgStarred;

        public RhemaHiveStarredMessViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageStarred = itemView.findViewById(R.id.user_receiver_name_starred);
            tvTimeStarred =itemView.findViewById(R.id.user_receiver_time_starred);
            imgStarred = itemView.findViewById(R.id.img_user_receiver_stared);
        }
    }


}
