package com.ritam.splitthebill.splitthebill.adapter;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import setergeter.FetchingChat_SetterGetter;

/**
 * Created by su on 7/14/16.
 */
public class MessageChat_Adapter extends RecyclerView.Adapter<MessageChat_Adapter.Messagechat_Viewholder> {

    Context context;
    LayoutInflater inflater;
    int type;
    public ArrayList<FetchingChat_SetterGetter> FetchingChatDetailsArray;


    public MessageChat_Adapter(Context context, ArrayList<FetchingChat_SetterGetter> fetchingChatDetails) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        FetchingChatDetailsArray = fetchingChatDetails;
    }

    @Override
    public Messagechat_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new Messagechat_Viewholder(inflater.inflate(R.layout.incoming_textmessage, null), 1);
        } else {
            return new Messagechat_Viewholder(inflater.inflate(R.layout.outgoing_textmessage, null), 2);
        }
    }

    @Override
    public void onBindViewHolder(final Messagechat_Viewholder holder, final int position) {


        holder.Message.setText(FetchingChatDetailsArray.get(position).getMessage());

        holder.MessageDate.setText(FetchingChatDetailsArray.get(position).getDateTime());

        SharedPreferences sharedPreferences = context.getSharedPreferences("AutoLogin", context.MODE_PRIVATE);

        if (sharedPreferences.getString("QB_UserId", "").equals(FetchingChatDetailsArray.get(position).getSenderId())) {
            holder.SenderName.setText("You");
        } else {

            try {

                holder.SenderName.setText(FetchingChatDetailsArray.get(position).getSenderName());

            }catch (Exception e){

                holder.SenderName.setText("No Name");
            }

        }

        holder.total_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                QBSettings.getInstance().init(context, AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
                QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setMessage("Are you sure you want to delete chat?");

                // Setting Icon to Dialog
                //alertDialog.setIcon(R.drawable.delete);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Write your code here to invoke YES event
                        //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();

                        holder.MessageProgress.setVisibility(View.VISIBLE);

                        Set<String> messagesIds = new HashSet<String>() {{
                            add(FetchingChatDetailsArray.get(position).getToDo());
                        }};

                        QBRestChatService.deleteMessages(messagesIds, false).performAsync(new QBEntityCallback<Void>() {
                            @Override
                            public void onSuccess(Void aVoid, Bundle bundle) {

                                FetchingChatDetailsArray.remove(position);
                                notifyDataSetChanged();

                                holder.MessageProgress.setVisibility(View.GONE);

                            }

                            @Override
                            public void onError(QBResponseException e) {

                                holder.MessageProgress.setVisibility(View.GONE);

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
        return FetchingChatDetailsArray.size();
    }

    @Override
    public int getItemViewType(int position) {

//        if(Integer.parseInt(AppController.userid)== Integer.parseInt(chatlist.get(position).getSenderId()))
//            type=2;
//        else
//            type=1;

        SharedPreferences sharedPreferences = context.getSharedPreferences("AutoLogin", context.MODE_PRIVATE);

        Log.v("QB_UserId::", sharedPreferences.getString("QB_UserId", ""));
        Log.v("QB_SenderId::", FetchingChatDetailsArray.get(position).getSenderId());

        if (sharedPreferences.getString("QB_UserId", "").equals(FetchingChatDetailsArray.get(position).getSenderId()))

            type = 2;

        else

            type = 1;


        return type;
    }

    public class Messagechat_Viewholder extends RecyclerView.ViewHolder {

        TextView Message, SenderName,MessageDate;

        View total_view;

        ProgressBar MessageProgress;

        public Messagechat_Viewholder(View itemView, int viewtype) {
            super(itemView);
            total_view = itemView;

            Message = (TextView) itemView.findViewById(R.id.chat_messagtext);

            SenderName = (TextView) itemView.findViewById(R.id.chat_personname);

            MessageDate = (TextView) itemView.findViewById(R.id.chat_date);

            MessageProgress = (ProgressBar) itemView.findViewById(R.id.progressBar);


        }
    }

    public void MessageUpdate(FetchingChat_SetterGetter fetchingChat_setterGetter) {


        FetchingChatDetailsArray.add(FetchingChatDetailsArray.size(), fetchingChat_setterGetter);

        notifyDataSetChanged();

    }
//    public void NewMessage(Messages_SetterGetter newmessage){
//
//        //chatlist.add(newmessage);
//        chatlist.add(0,newmessage);
//
//        //notifyItemInserted(chatlist.size());
//
//        notifyDataSetChanged();
//
//    }


}