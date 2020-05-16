package com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rhemaapp.rhematech.rhemahive.GlideApp;
import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserMessageModelClass;

import java.util.ArrayList;

public class RhemaHiveAddUserAdapter extends RecyclerView.Adapter<RhemaHiveAddUserAdapter.RhemaHiveAddUserViewHolder>  {
    private RhemaHiveUserMessageModelClass rhemaHiveUserMessageModelClass;
    private ArrayList<RhemaHiveUserMessageModelClass> rhemList;
    private View v;

    public RhemaHiveAddUserAdapter(ArrayList<RhemaHiveUserMessageModelClass> rhemList) {
        this.rhemList = rhemList;
    }

    @NonNull
    @Override
    public RhemaHiveAddUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_lay_add_user, parent, false);
        return new RhemaHiveAddUserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RhemaHiveAddUserViewHolder holder, int position) {
rhemaHiveUserMessageModelClass = rhemList.get(position);
holder.add_user_name.setText(rhemaHiveUserMessageModelClass.getReceiverName());
holder.add_user_stat.setText(rhemaHiveUserMessageModelClass.getReceiverStat());
holder.add_user_id.setText(rhemaHiveUserMessageModelClass.getReceiverId());
if(rhemaHiveUserMessageModelClass.getReceiverPix() != null && rhemaHiveUserMessageModelClass.getReceiverPix().length() > 0 && !rhemaHiveUserMessageModelClass.getReceiverPix().equals("")){
    GlideApp.with(v).load(rhemaHiveUserMessageModelClass.getReceiverPix()).into(holder.add_user_ico);
}
else{
    holder.add_user_ico.setImageResource(R.drawable.user_male);

}
holder.add_user_pix.setImageResource(R.drawable.ic_person_add_friend_color);
holder.add_user_pix.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(v.getContext(), "Freind request sent", Toast.LENGTH_SHORT).show();
        holder.add_user_pix.setImageResource(R.drawable.ic_request_sent_col);
    }
});
    }

    @Override
    public int getItemCount() {
        return rhemList.size();
    }

    public class RhemaHiveAddUserViewHolder extends RecyclerView.ViewHolder {
        TextView add_user_name, add_user_stat,add_user_id;
        ImageView add_user_ico, add_user_pix;

        public RhemaHiveAddUserViewHolder(@NonNull View itemView) {
            super(itemView);
            add_user_name = itemView.findViewById(R.id.add_user_receiver_name);
            add_user_stat = itemView.findViewById(R.id.add_user_receiver_stat);
            add_user_ico = itemView.findViewById(R.id.img_add_user_receiver);
            add_user_pix = itemView.findViewById(R.id.add_frd_ico);
            add_user_id = itemView.findViewById(R.id.add_user_receiver_id);
        }


    }
}
