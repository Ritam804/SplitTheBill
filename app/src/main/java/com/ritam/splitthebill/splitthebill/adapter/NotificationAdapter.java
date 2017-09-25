package com.ritam.splitthebill.splitthebill.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.BookingAsHost;
import com.ritam.splitthebill.splitthebill.activity.CounterBit;
import com.ritam.splitthebill.splitthebill.activity.EventDetails;
import com.ritam.splitthebill.splitthebill.activity.EventFullDetails;
import com.ritam.splitthebill.splitthebill.activity.EventRating;
import com.ritam.splitthebill.splitthebill.activity.OthersProfile;
import com.ritam.splitthebill.splitthebill.activity.SplitPercentage;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.fragments.Notification;

import org.json.JSONException;

import java.util.ArrayList;

import setergeter.Notifications_SetterGetter;

/**
 * Created by su on 18/4/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    ArrayList<Notifications_SetterGetter> AllNotificationsValue;
    Notification notification;
    int Page = 1;
    Context context;

    public NotificationAdapter(Context context, ArrayList<Notifications_SetterGetter> allNotificationsValue, Notification notification) {
        AllNotificationsValue = allNotificationsValue;
        this.notification = notification;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.Title.setText(AllNotificationsValue.get(position).getNotificationMessage());
        //holder.Message.setText(AllNotificationsValue.get(position).getDescription());
        holder.Date.setText(AllNotificationsValue.get(position).getDate_Added());

//        if(position%2==1){
//            holder.full_view.setBackgroundColor(Color.parseColor("#ffffff"));
//            holder.txt_button.setBackgroundResource(R.drawable.red_mold_rectangle);
//            holder.txt_button.setText("RESPOND");
//            holder.txt_button.setTextColor(Color.WHITE);
//        }

        if (AllNotificationsValue.size() > 9) {

            if (position == AllNotificationsValue.size() - 2) {

                Page = Page + 1;

                notification.Vol_AllNotifications(Page);

                AppData.NotificationFirstPAge = 2;

            }

        }

        holder.full_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences sharedPreferences = context.getSharedPreferences("AutoLogin", context.MODE_PRIVATE);

                if (AllNotificationsValue.get(position).getType().equals("6") || AllNotificationsValue.get(position).getType().equals("8") || AllNotificationsValue.get(position).getType().equals("9") || AllNotificationsValue.get(position).getType().equals("10")) {

                    try {
                        if (sharedPreferences.getString("UserId", "").equals(AllNotificationsValue.get(position).getTableObject().getString("host_id"))) {

                            Intent intent = new Intent(notification.getActivity(), EventFullDetails.class);
                            try {
                                AppData.TableId = AllNotificationsValue.get(position).getTableObject().getString("table_id");
                                AppData.EventId = AllNotificationsValue.get(position).getEventId();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            notification.getActivity().startActivity(intent);

                        } else {

                            Intent intent = new Intent(notification.getActivity(), BookingAsHost.class);
                            intent.putExtra("Purpose", "Notification");
                            AppData.FromButton = "ContactTheHost";
                            AppData.EventId = AllNotificationsValue.get(position).getEventId();
                            AppData.TableId = AllNotificationsValue.get(position).getTableObject().getString("table_id");
                            AppData.HostName = AllNotificationsValue.get(position).getTableObject().getString("host_name");
                            AppData.HostId = AllNotificationsValue.get(position).getTableObject().getString("host_id");
                            AppData.MaximumTotalMember = Integer.parseInt(AllNotificationsValue.get(position).getTableObject().getString("total"));
                            AppData.NumberOfAvailableSeat = Integer.parseInt(AllNotificationsValue.get(position).getTableObject().getString("number_of_available"));
                            AppData.MinimumAmount = Integer.parseInt(AllNotificationsValue.get(position).getTableObject().getString("min_amount"));
                            AppData.MaximumAmount = Integer.parseInt(AllNotificationsValue.get(position).getTableObject().getString("cost"));
                            AppData.GroupId = AllNotificationsValue.get(position).getTableObject().getString("group_id");
                            context.startActivity(intent);
                            AppData.Contact_Block = false;
                            notification.getActivity().startActivity(intent);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else if (AllNotificationsValue.get(position).getType().equals("2")) {


                    Intent intent = new Intent(context, BookingAsHost.class);
                    intent.putExtra("Purpose", "Notification");
                    AppData.FromButton = "ContactTheHost";
                    try {
                        AppData.TableId = AllNotificationsValue.get(position).getTableObject().getString("table_id");
                        AppData.HostName = AllNotificationsValue.get(position).getTableObject().getString("host_name");
                        AllNotificationsValue.get(position).getTableObject().getString("host_id");
                        AppData.MaximumAmount = Integer.parseInt(AllNotificationsValue.get(position).getTableObject().getString("cost"));
                        AppData.GroupId = AllNotificationsValue.get(position).getTableObject().getString("group_id");
                        AppData.MaximumTotalMember = Integer.parseInt(AllNotificationsValue.get(position).getTableObject().getString("total"));
                        AppData.NumberOfAvailableSeat = Integer.parseInt(AllNotificationsValue.get(position).getTableObject().getString("number_of_available"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    AppData.MinimumAmount = 50;

                    context.startActivity(intent);
                    AppData.Contact_Block = false;

                } else if (AllNotificationsValue.get(position).getType().equals("7")) {

                    try {
                        if (sharedPreferences.getString("UserId", "").equals(AllNotificationsValue.get(position).getTableObject().getString("host_id"))) {


                            Intent intent = new Intent(notification.getActivity(), EventFullDetails.class);
                            AppData.TableId = AllNotificationsValue.get(position).getTableObject().getString("table_id");
                            AppData.EventId = AllNotificationsValue.get(position).getEventId();
                            notification.getActivity().startActivity(intent);


                        } else {


                            final AlertDialog alertDialog = new AlertDialog.Builder(
                                    context).create();

                            // Setting Dialog Title
                            alertDialog.setTitle("Offer Declined");

                            // Setting Dialog Message
                            alertDialog.setMessage("Your offer has been declined by the host.You won't be abble to be a part of this event.You can check out other events.");

                            // Setting Icon to Dialog
                            //alertDialog.setIcon(R.drawable.tick);

                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog closed
                                    alertDialog.dismiss();
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else if (AllNotificationsValue.get(position).getType().equals("4")) {


                    Intent intent = new Intent(notification.getActivity(), EventRating.class);
                    intent.putExtra("EventId", AllNotificationsValue.get(position).getEventId());
                    intent.putExtra("VenueId", AllNotificationsValue.get(position).getVenueId());
                    try {
                        intent.putExtra("TableId", AllNotificationsValue.get(position).getTableObject().getString("table_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    notification.getActivity().startActivity(intent);

                } else if (AllNotificationsValue.get(position).getType().equals("12")) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    // Setting Dialog Title
                    //alertDialog.setTitle("Confirm Delete...");

                    // Setting Dialog Message
                    alertDialog.setMessage(AllNotificationsValue.get(position).getNotificationMessage());

                    // Setting Icon to Dialog
                    //alertDialog.setIcon(R.drawable.delete);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // Write your code here to invoke YES event
                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            notification.Vol_AcceptFollowRequest("1", AllNotificationsValue.get(position).getFollowerId());
                            dialog.dismiss();
                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("DECLINE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            notification.Vol_AcceptFollowRequest("2", AllNotificationsValue.get(position).getFollowerId());
                            dialog.dismiss();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();


                } else if (AllNotificationsValue.get(position).getType().equals("13")) {


                    Intent intent = new Intent(notification.getActivity(), OthersProfile.class);
                    intent.putExtra("UserId", AllNotificationsValue.get(position).getFollowerId());
                    notification.getActivity().startActivity(intent);

                } else if (AllNotificationsValue.get(position).getType().equals("14")) {


                    Intent intent = new Intent(notification.getActivity(), EventFullDetails.class);
                    intent.putExtra("From", "Remaining");
                    try {
                        AppData.TableId = AllNotificationsValue.get(position).getTableObject().getString("table_id");
                        AppData.EventId = AllNotificationsValue.get(position).getEventId();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    notification.getActivity().startActivity(intent);

                } else if (AllNotificationsValue.get(position).getType().equals("15")) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    // Setting Dialog Title
                    //alertDialog.setTitle("Confirm Delete...");

                    // Setting Dialog Message
                    alertDialog.setMessage(AllNotificationsValue.get(position).getNotificationMessage());

                    // Setting Icon to Dialog
                    //alertDialog.setIcon(R.drawable.delete);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // Write your code here to invoke YES event
                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            notification.Vol_AcceptOrDeclineInGroupList(AllNotificationsValue.get(position).getEventId(), "1");
                            dialog.dismiss();
                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("DISAGREE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            notification.Vol_AcceptOrDeclineInGroupList(AllNotificationsValue.get(position).getEventId(), "2");
                            dialog.dismiss();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                } else if (AllNotificationsValue.get(position).getType().equals("5")) {

                    try {

                        Intent intent = new Intent(notification.getActivity(), CounterBit.class);
                        intent.putExtra("UserImage", AllNotificationsValue.get(position).getImage());
                        intent.putExtra("EventTitle", AllNotificationsValue.get(position).getNotificationMessage());
                        intent.putExtra("EventName", AllNotificationsValue.get(position).getEventName());
                        intent.putExtra("EventStartTime", AllNotificationsValue.get(position).getEventStartTime());
                        intent.putExtra("EventEndTime", AllNotificationsValue.get(position).getEventEndTime());
                        intent.putExtra("Amount", AllNotificationsValue.get(position).getOfferAmount());
                        intent.putExtra("MaleCount", AllNotificationsValue.get(position).getOfferMale());
                        intent.putExtra("FemaleCount", AllNotificationsValue.get(position).getOfferFemale());
                        intent.putExtra("EventDay", AllNotificationsValue.get(position).getEventDay());
                        intent.putExtra("EventMonth", AllNotificationsValue.get(position).getEventMonth());
                        intent.putExtra("EventYear", AllNotificationsValue.get(position).getEventYear());
                        intent.putExtra("MinimumAmount", AllNotificationsValue.get(position).getOfferAmount());
                        intent.putExtra("MaximumAmount", AllNotificationsValue.get(position).getTableObject().getString("remaining_amount"));

                        intent.putExtra("PushId", AllNotificationsValue.get(position).getRequesterId());
                        intent.putExtra("RequisterId", AllNotificationsValue.get(position).getRequesterId());
                        intent.putExtra("HostId", AllNotificationsValue.get(position).getTableObject().getString("host_id"));
                        intent.putExtra("AccepterId", sharedPreferences.getString("UserId", ""));
                        intent.putExtra("EventId", AllNotificationsValue.get(position).getEventId());
                        intent.putExtra("TableId", AllNotificationsValue.get(position).getTableObject().getString("table_id"));
                        intent.putExtra("QB_GroupId", AllNotificationsValue.get(position).getQB_GroupId());
                        intent.putExtra("QB_SenderId", AllNotificationsValue.get(position).getQB_SenderId());
                        intent.putExtra("VenueName", AllNotificationsValue.get(position).getVenueName());

                        if (Integer.parseInt(AllNotificationsValue.get(position).getOfferAmount()) < Integer.parseInt(AllNotificationsValue.get(position).getRequestedAmount())) {

                            intent.putExtra("LastRequestedAmount", AllNotificationsValue.get(position).getOfferAmount());

                        } else {

                            intent.putExtra("LastRequestedAmount", AllNotificationsValue.get(position).getRequestedAmount());

                        }


                        intent.putExtra("Type","FirstTime");
                        notification.getActivity().startActivity(intent);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {

                    try {
                        if (sharedPreferences.getString("UserId", "").equals(AllNotificationsValue.get(position).getTableObject().getString("host_id"))) {


                            Intent intent = new Intent(notification.getActivity(), CounterBit.class);
                            intent.putExtra("UserImage", AllNotificationsValue.get(position).getImage());
                            intent.putExtra("EventTitle", AllNotificationsValue.get(position).getNotificationMessage());
                            intent.putExtra("EventName", AllNotificationsValue.get(position).getEventName());
                            intent.putExtra("EventStartTime", AllNotificationsValue.get(position).getEventStartTime());
                            intent.putExtra("EventEndTime", AllNotificationsValue.get(position).getEventEndTime());
                            intent.putExtra("Amount", AllNotificationsValue.get(position).getOfferAmount());
                            intent.putExtra("MaleCount", AllNotificationsValue.get(position).getOfferMale());
                            intent.putExtra("FemaleCount", AllNotificationsValue.get(position).getOfferFemale());
                            intent.putExtra("EventDay", AllNotificationsValue.get(position).getEventDay());
                            intent.putExtra("EventMonth", AllNotificationsValue.get(position).getEventMonth());
                            intent.putExtra("EventYear", AllNotificationsValue.get(position).getEventYear());
                            intent.putExtra("MinimumAmount", AllNotificationsValue.get(position).getOfferAmount());
                            intent.putExtra("MaximumAmount", AllNotificationsValue.get(position).getTableObject().getString("remaining_amount"));
                            intent.putExtra("PushId", AllNotificationsValue.get(position).getRequesterId());
                            intent.putExtra("RequisterId", AllNotificationsValue.get(position).getRequesterId());
                            intent.putExtra("HostId", AllNotificationsValue.get(position).getTableObject().getString("host_id"));
                            intent.putExtra("AccepterId", sharedPreferences.getString("UserId", ""));
                            intent.putExtra("EventId", AllNotificationsValue.get(position).getEventId());
                            intent.putExtra("TableId", AllNotificationsValue.get(position).getTableObject().getString("table_id"));
                            intent.putExtra("QB_GroupId", AllNotificationsValue.get(position).getQB_GroupId());
                            intent.putExtra("QB_SenderId", AllNotificationsValue.get(position).getQB_SenderId());
                            intent.putExtra("VenueName", AllNotificationsValue.get(position).getVenueName());

                            if (Integer.parseInt(AllNotificationsValue.get(position).getOfferAmount()) < Integer.parseInt(AllNotificationsValue.get(position).getRequestedAmount())) {

                                intent.putExtra("LastRequestedAmount", AllNotificationsValue.get(position).getOfferAmount());

                            } else {

                                intent.putExtra("LastRequestedAmount", AllNotificationsValue.get(position).getRequestedAmount());

                            }

                            intent.putExtra("Type","OtherTime");
                            notification.getActivity().startActivity(intent);


                        } else {

                            if (AllNotificationsValue.get(position).getTableObject().getInt("requested_status") == 1) {

                                Intent intent = new Intent(notification.getActivity(), CounterBit.class);
                                intent.putExtra("UserImage", AllNotificationsValue.get(position).getImage());
                                intent.putExtra("EventTitle", AllNotificationsValue.get(position).getNotificationMessage());
                                intent.putExtra("EventName", AllNotificationsValue.get(position).getEventName());
                                intent.putExtra("EventStartTime", AllNotificationsValue.get(position).getEventStartTime());
                                intent.putExtra("EventEndTime", AllNotificationsValue.get(position).getEventEndTime());
                                intent.putExtra("Amount", AllNotificationsValue.get(position).getOfferAmount());
                                intent.putExtra("MaleCount", AllNotificationsValue.get(position).getOfferMale());
                                intent.putExtra("FemaleCount", AllNotificationsValue.get(position).getOfferFemale());
                                intent.putExtra("EventDay", AllNotificationsValue.get(position).getEventDay());
                                intent.putExtra("EventMonth", AllNotificationsValue.get(position).getEventMonth());
                                intent.putExtra("EventYear", AllNotificationsValue.get(position).getEventYear());
                                intent.putExtra("MinimumAmount", AllNotificationsValue.get(position).getOfferAmount());
                                intent.putExtra("MaximumAmount", AllNotificationsValue.get(position).getTableObject().getString("remaining_amount"));
                                intent.putExtra("PushId", AllNotificationsValue.get(position).getTableObject().getString("host_id"));
                                intent.putExtra("RequisterId", AllNotificationsValue.get(position).getRequesterId());
                                intent.putExtra("HostId", AllNotificationsValue.get(position).getTableObject().getString("host_id"));
                                intent.putExtra("AccepterId", sharedPreferences.getString("UserId", ""));
                                intent.putExtra("EventId", AllNotificationsValue.get(position).getEventId());
                                intent.putExtra("TableId", AllNotificationsValue.get(position).getTableObject().getString("table_id"));
                                intent.putExtra("QB_GroupId", AllNotificationsValue.get(position).getQB_GroupId());
                                intent.putExtra("QB_SenderId", AllNotificationsValue.get(position).getQB_SenderId());
                                intent.putExtra("VenueName", AllNotificationsValue.get(position).getVenueName());
                                if (Integer.parseInt(AllNotificationsValue.get(position).getOfferAmount()) < Integer.parseInt(AllNotificationsValue.get(position).getRequestedAmount())) {

                                    intent.putExtra("LastRequestedAmount", AllNotificationsValue.get(position).getOfferAmount());

                                } else {

                                    intent.putExtra("LastRequestedAmount", AllNotificationsValue.get(position).getRequestedAmount());

                                }

                                intent.putExtra("Type","OtherTime");
                                notification.getActivity().startActivity(intent);


                            } else {

                                Intent intent = new Intent(context, BookingAsHost.class);
                                AppData.FromButton = "ContactTheHost";
                                AppData.TableId = AllNotificationsValue.get(position).getTableObject().getString("table_id");
                                AppData.EventId = AllNotificationsValue.get(position).getEventId();
                                AppData.HostName = AllNotificationsValue.get(position).getTableObject().getString("host_name");
                                AppData.HostId = AllNotificationsValue.get(position).getTableObject().getString("host_id");
                                AppData.MaximumTotalMember = Integer.parseInt(AllNotificationsValue.get(position).getTableObject().getString("total"));
                                AppData.NumberOfAvailableSeat = Integer.parseInt(AllNotificationsValue.get(position).getTableObject().getString("number_of_available"));
                                AppData.MinimumAmount = Integer.parseInt(AllNotificationsValue.get(position).getTableObject().getString("min_amount"));
                                AppData.MaximumAmount = Integer.parseInt(AllNotificationsValue.get(position).getTableObject().getString("remaining_amount"));
                                context.startActivity(intent);
                                AppData.Contact_Block = false;


                            }

                        }


//                    if (position == 0){
//
//                        notification.getActivity().startActivity(new Intent(notification.getActivity(), CounterBit.class));
//
//                    }else {
//
//                        AppData.EventId = AllNotificationsValue.get(position).getEventId();
//                        AppData.FromPage = "Notification";
//
//                        Intent intent = new Intent(notification.getActivity(), EventDetails.class);
//                        intent.putExtra("FromPrevious", false);
//                        notification.getActivity().startActivity(intent);
//
//                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return AllNotificationsValue.size();
    }

    public void Update(ArrayList<Notifications_SetterGetter> newArray) {

        AllNotificationsValue.addAll(newArray);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //TextView txt_button;
        LinearLayout full_view;

        TextView Title, Date;

        public ViewHolder(View itemView) {
            super(itemView);

            //txt_button= (TextView) itemView.findViewById(R.id.txt_button);

            full_view = (LinearLayout) itemView.findViewById(R.id.full_view);

            Title = (TextView) itemView.findViewById(R.id.tv_title);
            //Message = (TextView) itemView.findViewById(R.id.tv_message);
            Date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
