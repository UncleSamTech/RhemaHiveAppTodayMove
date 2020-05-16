package com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserMessageModelClass;

import java.util.ArrayList;

import static com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage.RhemaHiveUserMessageModelClass.SEND_MESS_ID;

public class RhemaHiveChatAreaAdapterClass extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View v;
    private ArrayList<RhemaHiveUserMessageModelClass> rModelList;
    private RhemaHiveUserMessageModelClass rhemModel;


    public RhemaHiveChatAreaAdapterClass(ArrayList<RhemaHiveUserMessageModelClass> rModelList) {
        this.rModelList = rModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       if (viewType == SEND_MESS_ID) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_chat_area_send, parent, false);
            //viewHolder = new RhemaHiveSendChatViewHolder(v);
            return new RhemaHiveSendChatViewHolder(v);
        } else if (viewType == RhemaHiveUserMessageModelClass.REC_MESS_ID) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_chat_receive_area, parent, false);
            return new RhemaHiveReceiveChatViewHolder(v);
        } else {

            throw new IllegalStateException("Unexpected value  " + viewType);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        rhemModel = rModelList.get(position);
        if(rhemModel != null) {
            switch (rhemModel.VIEW_TYPE) {
                case 1:
                    ((RhemaHiveSendChatViewHolder) holder).tv_send_message.setText(rhemModel.getSenderMessage());
                    ((RhemaHiveSendChatViewHolder) holder).tv_send_message_time.setText(rhemModel.getSendMessTime());

                    break;
                case 2:
                    ((RhemaHiveReceiveChatViewHolder) holder).receiveChat.setText(rhemModel.getSenderMessage());
                    ((RhemaHiveReceiveChatViewHolder) holder).receiveChatTime.setText(rhemModel.getSendMessTime());
                    break;
                default:
                    throw new IllegalStateException("Unable to bind value to holder because of : ");


            }




    /*   if(holder instanceof RhemaHiveReceiveChatViewHolder){
           ((RhemaHiveReceiveChatViewHolder) holder).receiveChat.setText(rhemModel.getSenderMessage());
           ((RhemaHiveReceiveChatViewHolder) holder).receiveChatTime.setText(rhemModel.getSendMessTime());


        }
        else if (holder instanceof RhemaHiveSendChatViewHolder){
           ((RhemaHiveSendChatViewHolder) holder).tv_send_message.setText(rhemModel.getSenderMessage());
           ((RhemaHiveSendChatViewHolder) holder).tv_send_message_time.setText(rhemModel.getSendMessTime());

        }

        else{
            throw new IllegalStateException("Unable to bind value to holder because of : "  );
        }


*/
        /*if(getItemViewType(position)==SEND_CHAT_ID){
            ((RhemaHiveSendChatViewHolder) holder).setSendChat(rhemModel);
        }
        else if(getItemViewType(position)==RECEIVE_CHAT_ID){
            ((RhemaHiveReceiveChatViewHolder) holder).setRecChat(rhemModel);
        }
*/

        }

    }

    @Override
    public int getItemCount() {
        return rModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        rhemModel = rModelList.get(position);
switch (rhemModel.VIEW_TYPE){
    case 1:
        return SEND_MESS_ID;
    case 2:
        return RhemaHiveUserMessageModelClass.REC_MESS_ID;
    default:
        throw new IllegalStateException("Invalid vaalues entered");
}
        /*if (rhemModel.VIEW_TYPE == 1) {
            return SEND_CHAT_ID;

        } else if (rhemModel.VIEW_TYPE == 2) {
            return RECEIVE_CHAT_ID;

        } else {
            throw new IllegalStateException("invalid value");
        }*/



        /*switch (rhemModel.getVIEW_TYPE()) {
            case 1:
                return SEND_CHAT_ID;

            case 2:
                return RECEIVE_CHAT_ID;
            default:
                throw new IllegalStateException("invalid value");


        }
*/
        // return super.getItemViewType(position);

    }

    public class RhemaHiveSendChatViewHolder extends RecyclerView.ViewHolder {
        TextView tv_send_message, tv_send_message_time;

        RhemaHiveSendChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_send_message = itemView.findViewById(R.id.tv_send_message);
            tv_send_message_time = itemView.findViewById(R.id.tv_send_chat_time);
        }

        /*public void setSendChat(RhemaHiveUserMessageModelClass rhemModelRecChat){
tv_send_message.setText(rhemModelRecChat.getSenderMessage());
tv_send_message_time.setText(rhemModelRecChat.getSendMessTime());
        }*/


    }

    public class RhemaHiveReceiveChatViewHolder extends RecyclerView.ViewHolder {
        TextView receiveChat, receiveChatTime;

        RhemaHiveReceiveChatViewHolder(@NonNull View itemView) {
            super(itemView);
            receiveChat = itemView.findViewById(R.id.tv_receive_chat);
            receiveChatTime = itemView.findViewById(R.id.receive_chat_time);


        }

       /* public void setRecChat(RhemaHiveUserMessageModelClass rhemModelRecChat){
receiveChat.setText(rhemModelRecChat.getReceiverMessage());
receiveChatTime.setText(rhemModelRecChat.getReceiveMessTime());
        }*/


    }


}
