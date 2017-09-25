package com.ritam.splitthebill.splitthebill.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.Dialog_AdvanceList_Counter_Adapter;
import com.ritam.splitthebill.splitthebill.adapter.RecyclerSliters;
import com.ritam.splitthebill.splitthebill.adapter.SplittersAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import setergeter.MemberList_Settergetter;

public class EventFullDetails extends AppCompatActivity {

    LinearLayout Button_Back, Button_InviteSplitter, Button_Cancel, Button_EventDetails, Button_EditBooking;
    RecyclerView SplittersList;
    SplittersAdapter adaptersp;
    TextView AccomodatesCount, BookedCount, AvailableCount, EventCost, EventHeader;
    ArrayList<MemberList_Settergetter> AllMember;
    ArrayList<MemberList_Settergetter> AcceptedMember;
    boolean AcceptedMeberIsAvailableOrNot = false;

    final int MY_PERMISSIONS_SEND_SMS = 1;
    String PhoneNumber;

    ProgressBar Loader;

    boolean UserBookOrNot = false;

    TextView Text_Button_ViewEvent;
    boolean Authorize = false;

    String MaleCount, FemaleCount, OfferAmount;
    LinearLayout Button_Chat;

    String QB_GroupId, GroupName;

    boolean PartOfAnyGroup = false, PartOfAnyGroupExceptHost = false;

    String remaining_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_full_details);

        AppData.ContactPageCount = 1;


        Button_Back = (LinearLayout) findViewById(R.id.ll_back_eventfulldetails);

        SplittersList = (RecyclerView) findViewById(R.id.rv_splitters);
        SplittersList.setHasFixedSize(true);
        SplittersList.setLayoutManager(new LinearLayoutManager(EventFullDetails.this));

        Button_InviteSplitter = (LinearLayout) findViewById(R.id.ll_invite);

        Button_Cancel = (LinearLayout) findViewById(R.id.ll_cancel);

        Button_EditBooking = (LinearLayout) findViewById(R.id.ll_editbooking);

        AccomodatesCount = (TextView) findViewById(R.id.tv_accomodates);

        AvailableCount = (TextView) findViewById(R.id.tv_available);

        BookedCount = (TextView) findViewById(R.id.tv_booked);

        EventCost = (TextView) findViewById(R.id.tv_eventcost);

        Button_EventDetails = (LinearLayout) findViewById(R.id.ll_eventdetails);

        Text_Button_ViewEvent = (TextView) findViewById(R.id.tv_viewevent);

        Loader = (ProgressBar) findViewById(R.id.progressBar11);

        Button_Chat = (LinearLayout) findViewById(R.id.ll_chat);

        EventHeader = (TextView) findViewById(R.id.header);

        AllMember = new ArrayList<MemberList_Settergetter>();


        try {

            if (getIntent().getExtras().getString("From").equals("Remaining")) {

                Button_InviteSplitter.setVisibility(View.GONE);
                Button_Cancel.setVisibility(View.GONE);
                Button_EventDetails.setVisibility(View.GONE);
                Button_EditBooking.setVisibility(View.GONE);

            }


        } catch (Exception e) {

            e.printStackTrace();

        }


        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        Button_Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PartOfAnyGroup) {

                    Intent intent = new Intent(EventFullDetails.this, MessageDetails.class);
                    intent.putExtra("GroupName", GroupName);
                    intent.putExtra("GroupId", QB_GroupId);
                    startActivity(intent);

                } else {

                    Toast.makeText(EventFullDetails.this, "You are not part of any group yet!", Toast.LENGTH_SHORT).show();

                }


            }
        });

        Button_EditBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(EventFullDetails.this, BookingAsHost.class);
                intent.putExtra("Purpose", "Edit");
                AppData.FromButton = "ContactTheHost";
                AppData.TableId = getIntent().getExtras().getString("Table_Id");
                AppData.HostName = getIntent().getExtras().getString("Host_Name");
                AppData.HostId = getIntent().getExtras().getString("Host_Id");
                AppData.MaximumTotalMember = Integer.parseInt(getIntent().getExtras().getString("TotalSeat"));
                AppData.NumberOfAvailableSeat = Integer.parseInt(getIntent().getExtras().getString("AvailableSeat"));
                AppData.MinimumAmount = 50;
                AppData.MaximumAmount = Integer.parseInt(getIntent().getExtras().getString("TableCost"));
                AppData.GroupId = getIntent().getExtras().getString("GroupId");
                startActivity(intent);
                AppData.Contact_Block = false;

            }
        });


        Button_InviteSplitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppData.contactVOList != null) {

                    if (AppData.contactVOList.size() > 0) {

                        if (AppData.ContactSynced) {

                            Intent intent = new Intent(EventFullDetails.this, ContactPage.class);
                            startActivity(intent);

                        } else {

                            Toast.makeText(EventFullDetails.this, "Contact syncing is going on!", Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(EventFullDetails.this, "Contact syncing is going on!", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(EventFullDetails.this, "Contact syncing is going on!", Toast.LENGTH_SHORT).show();

                }


            }
        });

        Button_EventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Authorize) {

                    Intent intent = new Intent(EventFullDetails.this, EventDetails.class);
                    AppData.EventId = getIntent().getExtras().getString("Event_Id");
                    intent.putExtra("FromPrevious", true);
                    AppData.FromPage = "Booking";
                    startActivity(intent);

                } else {


                    Intent intent = new Intent(EventFullDetails.this, SplitPercentage.class);
                    intent.putExtra("PersonCount", String.valueOf(Integer.parseInt(MaleCount) + Integer.parseInt(FemaleCount)));
                    intent.putExtra("Amount", OfferAmount);
                    AppData.FromButton = "ContactTheHost";
                    startActivity(intent);

                }


            }
        });

        Button_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

                if (getIntent().getExtras().getString("Host_Id").equals(sharedPreferences.getString("UserId", ""))) {

                    //User is host....

                    if (AcceptedMeberIsAvailableOrNot) {

                        if (AcceptedMember != null) {

                            if (AcceptedMember.size() > 0) {

                                if (AcceptedMember.size() > 1) {

                                    //More than 1 members are accepted.....
                                    //Open all accepted members list and fire the url with the choosen memberid as changeHostId.......


                                    LinearLayout Button_Cross;
                                    RecyclerView AmountList;
                                    TextView Dialog_HeaderText;

                                    View view1 = LayoutInflater.from(EventFullDetails.this).inflate(R.layout.dialog_bookasahost_advancelist, null);
                                    final Dialog dialog = new Dialog(EventFullDetails.this, R.style.MaterialDialogSheet);
                                    dialog.setContentView(view1);
                                    dialog.setCancelable(true);
                                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                    dialog.getWindow().setGravity(Gravity.CENTER);
                                    dialog.show();


                                    Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_back_dialog_bookingasahost);

                                    Dialog_HeaderText = (TextView) view1.findViewById(R.id.tv_dialog_header);

                                    Dialog_HeaderText.setText("Select Host");

                                    AmountList = (RecyclerView) view1.findViewById(R.id.rv_bookingasahost_advanceList);
                                    AmountList.setHasFixedSize(true);
                                    AmountList.setLayoutManager(new LinearLayoutManager(EventFullDetails.this));

                                    Dialog_AdvanceList_Counter_Adapter dialog_advanceList_counter_adapter = new Dialog_AdvanceList_Counter_Adapter("CancelBooking", EventFullDetails.this, AcceptedMember, dialog);
                                    AmountList.setAdapter(dialog_advanceList_counter_adapter);


                                    Button_Cross.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            dialog.dismiss();

                                        }
                                    });


                                } else {

                                    //Only 1 member is accepted.....
                                    //fire the url with accpted member is as changeHostId.....


                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(EventFullDetails.this);

                                    alertDialog.setMessage("Are you sure you want to cancel the booking?");

                                    // Setting Icon to Dialog
                                    //alertDialog.setIcon(R.drawable.delete);

                                    // Setting Positive "Yes" Button
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            // Write your code here to invoke YES event
                                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                            Vol_CancelBooking(AcceptedMember.get(0).getHostId());
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


                                }

                            }

                        }

                    } else {

                        //No member accepted yet.....
                        ////Fire the url with changeHostId as blank....

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EventFullDetails.this);

                        alertDialog.setMessage("Are you sure you want to cancel the booking?");

                        // Setting Icon to Dialog
                        //alertDialog.setIcon(R.drawable.delete);

                        // Setting Positive "Yes" Button
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // Write your code here to invoke YES event
                                //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                Vol_CancelBooking("");
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

                    }


                } else {

                    //User is not host....
                    //Fire the url with changeHostId as blank....

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(EventFullDetails.this);

                    alertDialog.setMessage("Are you sure you want to cancel the booking?");

                    // Setting Icon to Dialog
                    //alertDialog.setIcon(R.drawable.delete);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // Write your code here to invoke YES event
                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            Vol_CancelBooking("");
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

                }

            }
        });


        MembersDetails();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.v("EventFullDetails", "OnResume");

        if (AppData.Contact_Block) {

            String phone = null;

            if (AppData.selectedContact != null) {
                if (AppData.selectedContact.size() > 0) {

                    Log.v("SelectedContact_Lenth", String.valueOf(AppData.selectedContact.size()));
                    Log.v("SelectedContact_Lenth", AppData.selectedContact.toString());

                    for (int i = 0; i < AppData.selectedContact.size(); i++) {

                        if (i == 0) {

                            Log.v("SelectedContact_Lenth", AppData.selectedContact.get(i).getNumber());


                            String phone1 = (AppData.selectedContact.get(i).getNumber().replaceAll("[)\\-\\+\\.\\^:,(\\s+]", ""));

                            if (phone1.length() > 10) {

                                phone = phone1.substring(phone1.length() - 10);

                            } else {

                                phone = phone1;

                            }

                        } else {

                            Log.v("SelectedContact_Lenth", AppData.selectedContact.get(i).getNumber());

                            String phone1 = (AppData.selectedContact.get(i).getNumber().replaceAll("[)\\-\\+\\.\\^:,(\\s+]", ""));

                            if (phone1.length() > 10) {

                                phone = phone + "," + phone1.substring(phone1.length() - 10);

                            } else {

                                phone = phone + "," + phone1;

                            }

                        }


                    }

                } else {

                    phone = "";

                }

            } else {

                phone = "";

            }

            if (phone != "") {

                try {
                    phone = URLEncoder.encode(phone, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            Vol_InviteSplitters(phone);

            AppData.Contact_Block = false;

        }


    }

    public void Vol_InviteSplitters(String phone) {

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "invite_control?table_id=" + getIntent().getExtras().getString("Table_Id") + "&host_id=" + getIntent().getExtras().getString("Host_Id") + "&phone=" + phone + "&logged_id=" + sharedPreferences.getString("UserId", "") + "&event_id=" + getIntent().getExtras().getString("Event_Id");

        Log.v("InviteSplitters Url::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("InviteSplitters Response", response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public void MembersDetails() {

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "invitehistory_control?event_id=" + AppData.EventId + "&table_id=" + AppData.TableId + "&logged_in=" + sharedPreferences.getString("UserId", "");

        Log.v("MemberDetails_Url_EventFullDetails::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");
                    String message = response.getString("message");
                    String exists_status = response.getString("exists_status");
                    String authorize = response.getString("authorize");
                    QB_GroupId = response.getString("qb_groupid");
                    GroupName = response.getString("venue_name");

                    EventHeader.setText(GroupName);


                    if (exists_status.equals("0")) {

                        UserBookOrNot = false;

                    } else {

                        UserBookOrNot = true;

                    }

                    if (status.equals("success")) {

                        if (message.equals("data found")) {

                            String venue_name = response.getString("venue_name");
                            String total_seat = response.getString("total_seat");
                            String total_booked = response.getString("total_booked");
                            remaining_book = response.getString("remaining_book");
                            String booked_date = response.getString("booked_date");
                            String table_cost = response.getString("table_cost");


                            if (Integer.parseInt(remaining_book) > 0){

                                Button_InviteSplitter.setVisibility(View.GONE);

                            }

                            AccomodatesCount.setText(total_seat);
                            BookedCount.setText(total_booked);
                            AvailableCount.setText(remaining_book);
                            EventCost.setText("$ " + table_cost);


                            JSONObject host = response.getJSONObject("host");
                            String host_name = host.getString("host_name");
                            String image = host.getString("image");
                            String host_payment = host.getString("host_payment");
                            String host_male = host.getString("host_male");
                            String host_female = host.getString("host_female");
                            String host_id = host.getString("host_id");
                            String qb_userid = host.getString("qb_userid");

                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

                            if (sharedPreferences.getString("QB_UserId", "").equals(qb_userid)) {

                                PartOfAnyGroup = true;

                            }

                            if (sharedPreferences.getString("UserId", "").equals(host_id)) {

                                MaleCount = host_male;
                                FemaleCount = host_female;
                                OfferAmount = host_payment;

                            }

                            MemberList_Settergetter memberList_settergetter = new MemberList_Settergetter(host_name, host_payment, host_male, host_female, image, "4", host_id, "", "", "", "", "", "", "", "", "", "", "", "", ""
                                    , "", "", "", "", "", "", "", "", "", "");
                            AllMember.add(memberList_settergetter);


                            //Category:1 for Invited,2 for Offer,3 for Confirmed

                            JSONArray confirmed = response.getJSONArray("confirmed");

                            if (confirmed.length() > 0) {

                                AcceptedMeberIsAvailableOrNot = true;
                                AcceptedMember = new ArrayList<MemberList_Settergetter>();

                                for (int i = 0; i < confirmed.length(); i++) {

                                    JSONObject confirmedObject = confirmed.getJSONObject(i);

                                    String name = confirmedObject.getString("confirmed_name");
                                    String price = confirmedObject.getString("confirmed_price");
                                    String male = confirmedObject.getString("confirmed_male");
                                    String female = confirmedObject.getString("confirmed_female");
                                    String confirmedimage = confirmedObject.getString("image");
                                    String id = confirmedObject.getString("splitter_id");
                                    String qb_userid1 = host.getString("qb_userid");


                                    if (sharedPreferences.getString("QB_UserId", "").equals(qb_userid1)) {

                                        PartOfAnyGroup = true;
                                        PartOfAnyGroupExceptHost = true;

                                    }

                                    if (sharedPreferences.getString("UserId", "").equals(id)) {

                                        MaleCount = male;
                                        FemaleCount = female;
                                        OfferAmount = price;

                                    }


                                    MemberList_Settergetter memberList_settergetter1 = new MemberList_Settergetter(name, price, male, female, confirmedimage, "3", id, "", "", "", "", "", "", "", "", "", "", "", "", ""
                                            , "", "", "", "", "", "", "", "", "", "");
                                    AllMember.add(memberList_settergetter1);
                                    AcceptedMember.add(memberList_settergetter1);

                                }


                            } else {

                                AcceptedMeberIsAvailableOrNot = false;
                                PartOfAnyGroup = false;

                            }


                            JSONArray invited = response.getJSONArray("invited");

                            if (invited.length() > 0) {

                                for (int i = 0; i < invited.length(); i++) {

                                    JSONObject invitedObject = invited.getJSONObject(i);

                                    String name = invitedObject.getString("invited_name");
                                    String confirmedimage = invitedObject.getString("image");
                                    String invited_phone = invitedObject.getString("invited_phone");
                                    String id = invitedObject.getString("splitter_id");


                                    MemberList_Settergetter memberList_settergetter2 = new MemberList_Settergetter(name, invited_phone, "", "", confirmedimage, "1", id, "", "", "", "", "", "", "", "", "", "", "", "", ""
                                            , "", "", "", "", "", "", "", "", "", "");
                                    AllMember.add(memberList_settergetter2);

                                }


                            }


                            JSONArray request = response.getJSONArray("request");

                            if (request.length() > 0) {

                                for (int i = 0; i < request.length(); i++) {

                                    JSONObject requestObject = request.getJSONObject(i);

                                    String name = requestObject.getString("request_name");
                                    String price = requestObject.getString("request_payment");
                                    String male = requestObject.getString("request_male");
                                    String female = requestObject.getString("request_female");
                                    String confirmedimage = requestObject.getString("image");
                                    String id = requestObject.getString("splitter_id");


                                    String requester_id = requestObject.getString("requester_id");
                                    String event_id = requestObject.getString("event_id");
                                    String event_name = requestObject.getString("event_name");
                                    String event_day = requestObject.getString("event_day");
                                    String event_month = requestObject.getString("event_month");
                                    String event_year = requestObject.getString("event_year");
                                    String eventStartTime = requestObject.getString("eventStartTime");
                                    String eventEndTime = requestObject.getString("eventEndTime");
                                    String offer_male = requestObject.getString("offer_male");
                                    String offer_female = requestObject.getString("offer_female");
                                    String offer_amount = requestObject.getString("offer_amount");
                                    String title = requestObject.getString("title");

                                    JSONObject tabledet = requestObject.getJSONObject("tabledet");

                                    String table_id = tabledet.getString("table_id");
                                    String table_host_name = tabledet.getString("host_name");
                                    String table_host_id = tabledet.getString("host_id");
                                    String table_name = tabledet.getString("table_name");
                                    String cost = tabledet.getString("cost");
                                    String min_amount = tabledet.getString("min_amount");
                                    String remaining_amount = tabledet.getString("remaining_amount");
                                    String table_status = tabledet.getString("table_status");
                                    String number_of_available = tabledet.getString("number_of_available");
                                    String total = tabledet.getString("total");
                                    String what_to_do = tabledet.getString("what_to_do");

                                    MemberList_Settergetter memberList_settergetter3 = new MemberList_Settergetter(name, price, male, female, confirmedimage, "2", id,
                                            requester_id, event_id, event_name, event_day, event_month, event_year, eventStartTime, eventEndTime, offer_male, offer_female, offer_amount,
                                            table_id, table_host_name, table_host_id, table_name, cost, min_amount, remaining_amount, table_status, number_of_available, total, what_to_do, title);
                                    AllMember.add(memberList_settergetter3);

                                    //MemberList_Settergetter memberList_settergetter3 = new MemberList_Settergetter(name, price, male, female, confirmedimage, "2", id);
                                    //AllMember.add(memberList_settergetter3);

                                }


                            }


                            if (PartOfAnyGroup) {

                                Button_Chat.setVisibility(View.VISIBLE);

                            } else {

                                Button_Chat.setVisibility(View.GONE);

                            }


                            try {

                                if (getIntent().getExtras().getString("Purpose").equals("Pending")) {

                                    Text_Button_ViewEvent.setText("View Event Details");
                                    Authorize = true;

                                } else {

                                    if (authorize.equals("0")) {

                                        //if (PartOfAnyGroupExceptHost){

                                        Text_Button_ViewEvent.setText("Authorize");
                                        Authorize = false;

//                                        }else {
//
//
//                                            Text_Button_ViewEvent.setText("View Event Details");
//                                            Authorize = true;
//
//                                        }


                                    } else {

                                        Text_Button_ViewEvent.setText("View Event Details");
                                        Authorize = true;

                                    }

                                }

                            } catch (Exception e) {

                                if (authorize.equals("0")) {

                                    //if (PartOfAnyGroupExceptHost){

                                    Text_Button_ViewEvent.setText("Authorize");
                                    Authorize = false;

//                                    }else {
//
//                                        Text_Button_ViewEvent.setText("View Event Details");
//                                        Authorize = true;
//
//                                    }

                                } else {

                                    Text_Button_ViewEvent.setText("View Event Details");
                                    Authorize = true;

                                }

                            }

                            adaptersp = new SplittersAdapter(EventFullDetails.this, AllMember, EventFullDetails.this, "EventFullDetails", Loader);
                            SplittersList.setAdapter(adaptersp);


                        }

                    }

                    if (UserBookOrNot) {

                        if (Integer.parseInt(remaining_book) > 0){

                            Button_InviteSplitter.setVisibility(View.VISIBLE);
                            Button_Cancel.setVisibility(View.VISIBLE);

                        }else {

                            Button_InviteSplitter.setVisibility(View.GONE);
                            Button_Cancel.setVisibility(View.VISIBLE);

                        }



                    } else {

                        Button_InviteSplitter.setVisibility(View.GONE);
                        Button_Cancel.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    public void Vol_CancelBooking(String changeHostId) {

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "hostchange_control?event_id=" + AppData.EventId + "&host_id=" + getIntent().getExtras().getString("Host_Id") + "&change_host_id=" + changeHostId + "&table_id=" + AppData.TableId + "&loggedin_id=" + sharedPreferences.getString("UserId", "");

        Log.v("Delete_Url::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            String message = null;

            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");

                    message = response.getString("message");

                    if (status.equals("success")) {

                        Intent intent = new Intent(EventFullDetails.this, HomeActivity.class);
                        intent.putExtra("FromPage", "CancelBooking");
                        startActivity(intent);
                        finish();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(EventFullDetails.this, message, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public void SendSms(String Number) {

        PhoneNumber = Number;

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(EventFullDetails.this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(EventFullDetails.this,
                    Manifest.permission.SEND_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                Log.v("SMS_Permission", "1");

                ActivityCompat.requestPermissions(EventFullDetails.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_SEND_SMS);


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(EventFullDetails.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_SEND_SMS);

                Log.v("SMS_Permission", "2");

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

            Log.v("SMS_Permission", "3");


            Uri sendSmsTo = Uri.parse("smsto:" + PhoneNumber);
            Intent intent = new Intent(
                    android.content.Intent.ACTION_SENDTO, sendSmsTo);
            intent.putExtra("sms_body", "Testing");
            startActivity(intent);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_SEND_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Uri sendSmsTo = Uri.parse("smsto:" + PhoneNumber);
                    Intent intent = new Intent(
                            android.content.Intent.ACTION_SENDTO, sendSmsTo);
                    intent.putExtra("sms_body", "Testing");
                    startActivity(intent);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(EventFullDetails.this, "You have to give permission for sending sms", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
