package com.ritam.splitthebill.splitthebill.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.MessageDetails;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Set;

import setergeter.ChatList_SetterGetter;

/**
 * Created by ritam on 18/04/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Viewholder> {

    Context context;
    ArrayList<ChatList_SetterGetter> ChatListDetails;

    public MessageAdapter(Context context, ArrayList<ChatList_SetterGetter> chatlistarray) {
        this.context = context;
        ChatListDetails = chatlistarray;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(final Viewholder holder, final int position) {


//        if (!(ChatListDetails.get(position).getEventImage().equals("")) && !(ChatListDetails.get(position).getEventImage().isEmpty()) && ChatListDetails.get(position).getEventImage() != null){
//
//            Picasso.with(context)
//                    .load(ChatListDetails.get(position).getEventImage())
//                    .placeholder(R.drawable.appicon_round)
//                    .error(R.drawable.appicon_round)
//                    .transform(new RoundedTransformation())
//                    .into(holder.Eventimage);
//
//        }

        holder.EventName.setText(ChatListDetails.get(position).getEventName());
        holder.VenueName.setText(ChatListDetails.get(position).getAddress() + " members");

        try {

            if (ChatListDetails.get(position).getLastMessage().equals("null") || ChatListDetails.get(position).getLastMessage().equals(null) || ChatListDetails.get(position).getLastMessage().equals("")){

                holder.Address.setVisibility(View.GONE);

            }else {

                holder.Address.setVisibility(View.VISIBLE);
                holder.Address.setText(ChatListDetails.get(position).getLastMessage());

            }

        }catch (Exception e){

            holder.Address.setVisibility(View.GONE);

        }

        try {

            if (ChatListDetails.get(position).getLastMessageSentTime().equals("null") || ChatListDetails.get(position).getLastMessageSentTime().equals(null) || ChatListDetails.get(position).getLastMessageSentTime().equals("")){

                holder.LastMessageSentTime.setVisibility(View.GONE);

            }else {

                holder.LastMessageSentTime.setVisibility(View.VISIBLE);
                holder.LastMessageSentTime.setText(ChatListDetails.get(position).getLastMessageSentTime());

            }

        }catch (Exception e){

            holder.LastMessageSentTime.setVisibility(View.GONE);

        }



        if (ChatListDetails.get(position).getUnreadMessageCount() > 0){

            holder.UnreadMessageCount.setVisibility(View.VISIBLE);
            holder.UnreadMessageCount.setText(String.valueOf(ChatListDetails.get(position).getUnreadMessageCount()));

        }else {

            holder.UnreadMessageCount.setVisibility(View.GONE);

        }


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, MessageDetails.class);
                intent.putExtra("GroupId",ChatListDetails.get(position).getGroupId());
                intent.putExtra("GroupName",ChatListDetails.get(position).getEventName());
                intent.putExtra("MemberCount",ChatListDetails.get(position).getAddress());
                AppData.OccupantIds = ChatListDetails.get(position).getOccupantIds();
                context.startActivity(intent);


            }
        });


        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                QBSettings.getInstance().init(context, AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
                QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setMessage("Are you sure you want to delete this group?");

                // Setting Icon to Dialog
                //alertDialog.setIcon(R.drawable.delete);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        holder.GroupProgress.setVisibility(View.VISIBLE);

                        QBRestChatService.deleteDialog(ChatListDetails.get(position).getGroupId(), true).performAsync(new QBEntityCallback<Void>() {
                            @Override
                            public void onSuccess(Void aVoid, Bundle bundle) {

                                ChatListDetails.remove(position);
                                notifyDataSetChanged();

                                holder.GroupProgress.setVisibility(View.GONE);

                            }

                            @Override
                            public void onError(QBResponseException e) {

                                holder.GroupProgress.setVisibility(View.GONE);

                            }
                        });

                        dialog.dismiss();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                // Showing Alert Message
                alertDialog.show();




                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return ChatListDetails.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        View view;
        ImageView Eventimage;
        TextView EventName,VenueName,Address,UnreadMessageCount,LastMessageSentTime;
        ProgressBar GroupProgress;

        public Viewholder(View itemView) {
            super(itemView);

            Eventimage = (ImageView) itemView.findViewById(R.id.iv_image);
            EventName =  (TextView) itemView.findViewById(R.id.tv_eventname);
            VenueName =  (TextView) itemView.findViewById(R.id.tv_venuename);
            Address =  (TextView) itemView.findViewById(R.id.tv_address);
            GroupProgress = (ProgressBar) itemView.findViewById(R.id.progressBar);
            UnreadMessageCount = (TextView) itemView.findViewById(R.id.text_image);
            LastMessageSentTime = (TextView) itemView.findViewById(R.id.tv_lastmessagesenttime);

            view = itemView;

        }
    }
}