package com.ritam.splitthebill.splitthebill.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.BookingAsHost;
import com.ritam.splitthebill.splitthebill.activity.CounterBit;
import com.ritam.splitthebill.splitthebill.activity.EventFullDetails;
import com.ritam.splitthebill.splitthebill.activity.OthersProfile;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.ritam.splitthebill.splitthebill.fragments.Message;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import setergeter.MemberList_Settergetter;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by su on 13/6/17.
 */

public class SplittersAdapter extends RecyclerView.Adapter<SplittersAdapter.ViewHolder> {

    Context context;
    ArrayList<MemberList_Settergetter> MemberList;
    String phone;
    EventFullDetails Event;
    BookingAsHost Book;
    String Page;
    ProgressBar Progress;

    public SplittersAdapter(Context context, ArrayList<MemberList_Settergetter> memberList, EventFullDetails eventFullDetails, String page, ProgressBar prog) {
        this.context = context;
        MemberList = memberList;
        Event = eventFullDetails;
        Page = page;
        Progress = prog;
    }

    public SplittersAdapter(Context context, ArrayList<MemberList_Settergetter> memberList, BookingAsHost bookingAsHost, String page, ProgressBar prog) {
        this.context = context;
        MemberList = memberList;
        Book = bookingAsHost;
        Page = page;
        Progress = prog;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.splitters_adapter_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        SharedPreferences sharedPreferences1 =  context.getSharedPreferences("AutoLogin",context.MODE_PRIVATE);

        if (MemberList.get(0).getHostId().equals(sharedPreferences1.getString("UserId",""))){

            viewHolder.Price.setVisibility(View.VISIBLE);

        }else {


            if (sharedPreferences1.getString("UserId","").equals(MemberList.get(position).getHostId())){

                viewHolder.Price.setVisibility(View.VISIBLE);

            }else {

                viewHolder.Price.setVisibility(View.GONE);

            }

        }

        if (MemberList.get(position).getHostId().equals("") || MemberList.get(position).getHostId().equals(null) || MemberList.get(position).getHostId().equals("null")){

            viewHolder.MessageIcon.setVisibility(View.GONE);

        }else {

            viewHolder.MessageIcon.setVisibility(View.VISIBLE);

        }


        if (MemberList.get(position).getCategory().equals("4")) {

            viewHolder.Field_MaleFemale.setVisibility(View.VISIBLE);
            viewHolder.Button_Resend.setVisibility(View.GONE);

        } else {

            if (MemberList.get(position).getCategory().equals("1")) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("AutoLogin", MODE_PRIVATE);
                if (sharedPreferences.getString("UserId", "").equals(MemberList.get(0).getHostId())) {

                    viewHolder.Field_MaleFemale.setVisibility(View.GONE);
                    viewHolder.Button_Resend.setVisibility(View.VISIBLE);

                } else {

                    viewHolder.Field_MaleFemale.setVisibility(View.GONE);
                    viewHolder.Button_Resend.setVisibility(View.GONE);

                }

            } else {


                viewHolder.Field_MaleFemale.setVisibility(View.VISIBLE);
                viewHolder.Button_Resend.setVisibility(View.GONE);

            }


        }

        if (MemberList.get(position).getCategory().equals("1")) {

            //viewHolder.right_block.setVisibility(View.GONE);


            viewHolder.Status.setText("Invited");

            if (MemberList.get(position).getName().equals("")) {

                boolean Found = false;

                viewHolder.TextUserImage.setVisibility(View.VISIBLE);
                viewHolder.UserImage.setVisibility(View.GONE);

                viewHolder.UserImage.setEnabled(false);
                viewHolder.UserImage.setClickable(false);

                for (int i = 0; i < AppData.contactVOList.size(); i++) {


                    try {

                        String phone1 = (AppData.contactVOList.get(i).getNumber().replaceAll("[)\\-\\+\\.\\^:,(\\s+]", ""));

                        Log.v("SelectedContact_Lenth", phone1);

                        if (phone1.length() > 10) {

                            phone = phone1.substring(phone1.length() - 10);

                        } else {

                            phone = phone1;

                        }


                        if (phone.equals(MemberList.get(position).getPayment())) {

                            Found = true;

                            viewHolder.UserName.setText(AppData.contactVOList.get(i).getName());

                            if (AppData.contactVOList.get(i).getPhoto_uri() != null) {

                                Picasso.with(context)
                                        .load(AppData.contactVOList.get(i).getPhoto_uri())
                                        .placeholder(R.drawable.appicon_round)
                                        .error(R.drawable.appicon_round)
                                        .transform(new RoundedTransformation())
                                        .into(viewHolder.UserImage);

                            } else {


                                String[] Fullname = AppData.contactVOList.get(i).getName().split("//s+");

                                if (Fullname.length <= 1) {

                                    viewHolder.TextUserImage.setText(AppData.contactVOList.get(i).getName().substring(0, 1).toUpperCase());


                                } else {

                                    int pos = AppData.contactVOList.get(i).getName().lastIndexOf(' ');
                                    viewHolder.TextUserImage.setText(AppData.contactVOList.get(i).getName().substring(0, 1).toUpperCase() + AppData.contactVOList.get(i).getName().substring(pos + 1, pos + 2).toUpperCase());


                                }


                            }

                        }


                    } catch (Exception e) {

                        Log.v("Exception", e.toString());

                    }


                }


                if (Found == false) {

                    viewHolder.UserName.setText("UNKNOWN");
                    viewHolder.UserImage.setVisibility(View.GONE);
                    viewHolder.TextUserImage.setVisibility(View.VISIBLE);
                    viewHolder.TextUserImage.setText("U");

                }


            } else {


                viewHolder.TextUserImage.setVisibility(View.GONE);
                viewHolder.UserImage.setVisibility(View.VISIBLE);

                viewHolder.UserImage.setEnabled(true);
                viewHolder.UserImage.setClickable(true);

                viewHolder.UserName.setText(MemberList.get(position).getName());

                if (MemberList.get(position).getImage() != null && !(MemberList.get(position).getImage().isEmpty())){

                    Picasso.with(context)
                            .load(MemberList.get(position).getImage())
                            .placeholder(R.drawable.appicon_round)
                            .error(R.drawable.appicon_round)
                            .transform(new RoundedTransformation())
                            .into(viewHolder.UserImage);

                }else {

                    Picasso.with(context)
                            .load(R.drawable.appicon_round)
                            .placeholder(R.drawable.appicon_round)
                            .error(R.drawable.appicon_round)
                            .transform(new RoundedTransformation())
                            .into(viewHolder.UserImage);

                }



            }


        } else {

            //viewHolder.right_block.setVisibility(View.VISIBLE);

            viewHolder.TextUserImage.setVisibility(View.GONE);
            viewHolder.UserImage.setVisibility(View.VISIBLE);

            if (MemberList.get(position).getCategory().equals("2")) {

                viewHolder.Status.setText("Offered");

            } else if (MemberList.get(position).getCategory().equals("3")) {

                viewHolder.Status.setText("Confirmed");

            } else if (MemberList.get(position).getCategory().equals("4")) {

                viewHolder.Status.setText("Host");

            }

            viewHolder.UserName.setText(MemberList.get(position).getName());

            Picasso.with(context)
                    .load(MemberList.get(position).getImage())
                    .placeholder(R.drawable.appicon_round)
                    .error(R.drawable.appicon_round)
                    .transform(new RoundedTransformation())
                    .into(viewHolder.UserImage);

            viewHolder.Price.setText("$" + MemberList.get(position).getPayment());

            viewHolder.MaleCount.setText(MemberList.get(position).getMale());
            viewHolder.FemaleCount.setText(MemberList.get(position).getFemale());

        }


        viewHolder.UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OthersProfile.class);
                intent.putExtra("UserId", MemberList.get(position).getHostId());
                context.startActivity(intent);

            }
        });

        viewHolder.UserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OthersProfile.class);
                intent.putExtra("UserId", MemberList.get(position).getHostId());
                context.startActivity(intent);

            }
        });


        viewHolder.Button_Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MemberList.get(position).getName().equals("")) {

                    if (Page.equals("EventFullDetails")) {

                        Event.SendSms(MemberList.get(position).getPayment());

                    } else if (Page.equals("BookingAsHost")) {

                        Book.SendSms(MemberList.get(position).getPayment());

                    }

                } else {

                    Vol_InviteSplitters(MemberList.get(position).getPayment(),MemberList.get(position).getName());

                }

            }
        });


        viewHolder.TotalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MemberList.get(position).getCategory().equals("2")) {

                    SharedPreferences sharedPreferences = context.getSharedPreferences("AutoLogin", context.MODE_PRIVATE);

                    if (sharedPreferences.getString("UserId", "").equals(MemberList.get(position).getTable_HostId())){

                        if (sharedPreferences.getString("UserId", "").equals(MemberList.get(position).getTable_HostId())) {

                            Intent intent = null;


                            if (Page.equals("EventFullDetails")) {

                                intent = new Intent(context, CounterBit.class);

                            } else if (Page.equals("BookingAsHost")) {

                                intent = new Intent(context, CounterBit.class);

                            }


                            intent.putExtra("UserImage", MemberList.get(position).getImage());
                            intent.putExtra("EventTitle", MemberList.get(position).getSplitter_Title());
                            intent.putExtra("EventName", MemberList.get(position).getSplitter_EventName());
                            intent.putExtra("EventStartTime", MemberList.get(position).getSplitter_EventStartTime());
                            intent.putExtra("EventEndTime", MemberList.get(position).getSplitter_EventEndTime());
                            intent.putExtra("Amount", MemberList.get(position).getSplitter_OfferAmount());
                            intent.putExtra("MaleCount", MemberList.get(position).getSplitter_OfferMale());
                            intent.putExtra("FemaleCount", MemberList.get(position).getSplitter_OfferFemale());
                            intent.putExtra("EventDay", MemberList.get(position).getSplitter_EventDay());
                            intent.putExtra("EventMonth", MemberList.get(position).getSplitter_EventMonth());
                            intent.putExtra("EventYear", MemberList.get(position).getSplitter_EventYear());
                            intent.putExtra("MinimumAmount", MemberList.get(position).getTable_MinAmount());
                            intent.putExtra("MaximumAmount", MemberList.get(position).getTable_Cost());
                            intent.putExtra("PushId", MemberList.get(position).getSplitter_RequesterId());
                            intent.putExtra("RequisterId", MemberList.get(position).getSplitter_RequesterId());
                            intent.putExtra("HostId", MemberList.get(position).getTable_HostId());
                            intent.putExtra("AccepterId", sharedPreferences.getString("UserId", ""));
                            intent.putExtra("EventId", MemberList.get(position).getSplitter_EventId());
                            intent.putExtra("TableId", MemberList.get(position).getTable_TableId());

                            if (Integer.parseInt(MemberList.get(position).getSplitter_OfferAmount()) < Integer.parseInt(MemberList.get(position).getPayment())) {

                                intent.putExtra("LastRequestedAmount", MemberList.get(position).getSplitter_OfferAmount());

                            } else {

                                intent.putExtra("LastRequestedAmount", MemberList.get(position).getPayment());

                            }


                            context.startActivity(intent);


                        } else {


                            Intent intent = new Intent(context, CounterBit.class);
                            intent.putExtra("UserImage", MemberList.get(position).getImage());
                            intent.putExtra("EventTitle", MemberList.get(position).getSplitter_Title());
                            intent.putExtra("EventName", MemberList.get(position).getSplitter_EventName());
                            intent.putExtra("EventStartTime", MemberList.get(position).getSplitter_EventStartTime());
                            intent.putExtra("EventEndTime", MemberList.get(position).getSplitter_EventEndTime());
                            intent.putExtra("Amount", MemberList.get(position).getSplitter_OfferAmount());
                            intent.putExtra("MaleCount", MemberList.get(position).getSplitter_OfferMale());
                            intent.putExtra("FemaleCount", MemberList.get(position).getSplitter_OfferFemale());
                            intent.putExtra("EventDay", MemberList.get(position).getSplitter_EventDay());
                            intent.putExtra("EventMonth", MemberList.get(position).getSplitter_EventMonth());
                            intent.putExtra("EventYear", MemberList.get(position).getSplitter_EventYear());
                            intent.putExtra("MinimumAmount", MemberList.get(position).getSplitter_OfferAmount());
                            intent.putExtra("MaximumAmount", MemberList.get(position).getTable_RamainingAmount());
                            intent.putExtra("PushId", MemberList.get(position).getTable_HostId());
                            intent.putExtra("RequisterId", MemberList.get(position).getSplitter_RequesterId());
                            intent.putExtra("HostId", MemberList.get(position).getTable_HostId());
                            intent.putExtra("AccepterId", sharedPreferences.getString("UserId", ""));
                            intent.putExtra("EventId", MemberList.get(position).getSplitter_EventId());
                            intent.putExtra("TableId", MemberList.get(position).getTable_TableId());
                            if (Integer.parseInt(MemberList.get(position).getSplitter_OfferAmount()) < Integer.parseInt(MemberList.get(position).getPayment())) {

                                intent.putExtra("LastRequestedAmount", MemberList.get(position).getSplitter_OfferAmount());

                            } else {

                                intent.putExtra("LastRequestedAmount", MemberList.get(position).getPayment());

                            }


                            context.startActivity(intent);




                        }


                   }
// else{
//
//                        if (sharedPreferences.getString("UserId", "").equals(MemberList.get(position).getHostId())){
//
//
//                            if (sharedPreferences.getString("UserId", "").equals(MemberList.get(position).getTable_HostId())) {
//
//                                Intent intent = null;
//
//
//                                if (Page.equals("EventFullDetails")) {
//
//                                    intent = new Intent(context, CounterBit.class);
//
//                                } else if (Page.equals("BookingAsHost")) {
//
//                                    intent = new Intent(context, CounterBit.class);
//
//                                }
//
//
//                                intent.putExtra("UserImage", MemberList.get(position).getImage());
//                                intent.putExtra("EventTitle", "Counter from Splitters");
//                                intent.putExtra("EventName", MemberList.get(position).getSplitter_EventName());
//                                intent.putExtra("EventStartTime", MemberList.get(position).getSplitter_EventStartTime());
//                                intent.putExtra("EventEndTime", MemberList.get(position).getSplitter_EventEndTime());
//                                intent.putExtra("Amount", MemberList.get(position).getSplitter_OfferAmount());
//                                intent.putExtra("MaleCount", MemberList.get(position).getSplitter_OfferMale());
//                                intent.putExtra("FemaleCount", MemberList.get(position).getSplitter_OfferFemale());
//                                intent.putExtra("EventDay", MemberList.get(position).getSplitter_EventDay());
//                                intent.putExtra("EventMonth", MemberList.get(position).getSplitter_EventMonth());
//                                intent.putExtra("EventYear", MemberList.get(position).getSplitter_EventYear());
//                                intent.putExtra("MinimumAmount", MemberList.get(position).getTable_MinAmount());
//                                intent.putExtra("MaximumAmount", MemberList.get(position).getTable_Cost());
//                                intent.putExtra("PushId", MemberList.get(position).getSplitter_RequesterId());
//                                intent.putExtra("RequisterId", MemberList.get(position).getSplitter_RequesterId());
//                                intent.putExtra("HostId", MemberList.get(position).getTable_HostId());
//                                intent.putExtra("AccepterId", sharedPreferences.getString("UserId", ""));
//                                intent.putExtra("EventId", MemberList.get(position).getSplitter_EventId());
//                                intent.putExtra("TableId", MemberList.get(position).getTable_TableId());
//
//                                if (Integer.parseInt(MemberList.get(position).getSplitter_OfferAmount()) < Integer.parseInt(MemberList.get(position).getPayment())) {
//
//                                    intent.putExtra("LastRequestedAmount", MemberList.get(position).getSplitter_OfferAmount());
//
//                                } else {
//
//                                    intent.putExtra("LastRequestedAmount", MemberList.get(position).getPayment());
//
//                                }
//
//
//                                context.startActivity(intent);
//
//
//                            } else {
//
//
//                                Intent intent = new Intent(context, CounterBit.class);
//                                intent.putExtra("UserImage", MemberList.get(position).getImage());
//                                intent.putExtra("EventTitle", "Counter from Splitters");
//                                intent.putExtra("EventName", MemberList.get(position).getSplitter_EventName());
//                                intent.putExtra("EventStartTime", MemberList.get(position).getSplitter_EventStartTime());
//                                intent.putExtra("EventEndTime", MemberList.get(position).getSplitter_EventEndTime());
//                                intent.putExtra("Amount", MemberList.get(position).getSplitter_OfferAmount());
//                                intent.putExtra("MaleCount", MemberList.get(position).getSplitter_OfferMale());
//                                intent.putExtra("FemaleCount", MemberList.get(position).getSplitter_OfferFemale());
//                                intent.putExtra("EventDay", MemberList.get(position).getSplitter_EventDay());
//                                intent.putExtra("EventMonth", MemberList.get(position).getSplitter_EventMonth());
//                                intent.putExtra("EventYear", MemberList.get(position).getSplitter_EventYear());
//                                intent.putExtra("MinimumAmount", MemberList.get(position).getSplitter_OfferAmount());
//                                intent.putExtra("MaximumAmount", MemberList.get(position).getTable_RamainingAmount());
//                                intent.putExtra("PushId", MemberList.get(position).getTable_HostId());
//                                intent.putExtra("RequisterId", MemberList.get(position).getSplitter_RequesterId());
//                                intent.putExtra("HostId", MemberList.get(position).getTable_HostId());
//                                intent.putExtra("AccepterId", sharedPreferences.getString("UserId", ""));
//                                intent.putExtra("EventId", MemberList.get(position).getSplitter_EventId());
//                                intent.putExtra("TableId", MemberList.get(position).getTable_TableId());
//                                if (Integer.parseInt(MemberList.get(position).getSplitter_OfferAmount()) < Integer.parseInt(MemberList.get(position).getPayment())) {
//
//                                    intent.putExtra("LastRequestedAmount", MemberList.get(position).getSplitter_OfferAmount());
//
//                                } else {
//
//                                    intent.putExtra("LastRequestedAmount", MemberList.get(position).getPayment());
//
//                                }
//
//
//                                context.startActivity(intent);
//
//
//
//
//                            }
//
//                        }
//
//                    }



                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return MemberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout right_block, Field_MaleFemale, Button_Resend;
        ImageView UserImage,MessageIcon;
        TextView UserName, Status, Price, MaleCount, FemaleCount, TextUserImage;

        View TotalView;

        public ViewHolder(View itemView) {
            super(itemView);
            right_block = (LinearLayout) itemView.findViewById(R.id.right_block);
            UserImage = (ImageView) itemView.findViewById(R.id.iv_userimage);
            UserName = (TextView) itemView.findViewById(R.id.tv_username);
            Status = (TextView) itemView.findViewById(R.id.status);
            Price = (TextView) itemView.findViewById(R.id.tv_price);
            MaleCount = (TextView) itemView.findViewById(R.id.tv_malecounter);
            FemaleCount = (TextView) itemView.findViewById(R.id.tv_femalecounter);
            TextUserImage = (TextView) itemView.findViewById(R.id.text_image);
            Field_MaleFemale = (LinearLayout) itemView.findViewById(R.id.ll_malefemale);
            Button_Resend = (LinearLayout) itemView.findViewById(R.id.ll_resend);
            MessageIcon = (ImageView) itemView.findViewById(R.id.imageView2);

            TotalView = itemView;

        }
    }

    public void Vol_InviteSplitters(String phone, final String name) {

        Progress.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = context.getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "invite_control?table_id=" + AppData.TableId + "&host_id=" + MemberList.get(0).getHostId() + "&phone=" + phone + "&logged_id=" + sharedPreferences.getString("UserId", "") + "&event_id=" + AppData.EventId;

        Log.v("InviteSplitters Url::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("InviteSplitters Response", response.toString());

                Toast.makeText(context,name+" is invited Successfully",Toast.LENGTH_SHORT).show();

                Progress.setVisibility(View.GONE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Progress.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}
