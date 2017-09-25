package com.ritam.splitthebill.splitthebill.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.RecyclerSliters;
import com.ritam.splitthebill.splitthebill.adapter.ReverseContact;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import setergeter.Phone_Selected_SetterGetter;
import setergeter.Splitter_Selected_SetterGetter;

public class ReverseGuest extends AppCompatActivity {

    RecyclerView recyclerView;
    ReverseContact adapter;
    LinearLayout add_button,ll_back;
    RelativeLayout Button_Done;

    TextView Name,Surname;

    ProgressBar GueslistProgress;

    String SendSms_PhoneNumber = "";

    final int MY_PERMISSIONS_SEND_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse_guest);

        recyclerView= (RecyclerView) findViewById(R.id.rv);
        add_button= (LinearLayout) findViewById(R.id.add_button);
        ll_back= (LinearLayout) findViewById(R.id.ll_back);

        Button_Done = (RelativeLayout) findViewById(R.id.rl_done);

        GueslistProgress = (ProgressBar) findViewById(R.id.progressBar);

        Name = (TextView) findViewById(R.id.tv_name);
        Surname = (TextView) findViewById(R.id.tv_surname);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        Name.setText(sharedPreferences.getString("first_name",""));
        Surname.setText(sharedPreferences.getString("last_name",""));

        Button_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppData.Contact_Block) {

                    GueslistProgress.setVisibility(View.VISIBLE);

                    String phone = null;
                    String name = null;

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

                                    name = AppData.selectedContact.get(i).getName();

                                } else {

                                    Log.v("SelectedContact_Lenth", AppData.selectedContact.get(i).getNumber());

                                    String phone1 = (AppData.selectedContact.get(i).getNumber().replaceAll("[)\\-\\+\\.\\^:,(\\s+]", ""));

                                    if (phone1.length() > 10) {

                                        phone = phone + "," + phone1.substring(phone1.length() - 10);

                                    } else {

                                        phone = phone + "," + phone1;

                                    }

                                    name = name + "," + AppData.selectedContact.get(i).getName();

                                }


                            }

                        } else {

                            phone = "";
                            name = "";

                        }

                    } else {

                        phone = "";
                        name = "";

                    }

                    if (phone != "") {

                        try {
                            phone = URLEncoder.encode(phone, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                    if (name != "") {

                        try {
                            name = URLEncoder.encode(name, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                    Vol_InviteGuest(phone,name);

                    AppData.Contact_Block = false;

                }else {

                    Toast.makeText(ReverseGuest.this,"Select Someone to invite",Toast.LENGTH_SHORT).show();

                }

            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppData.contactVOList != null){

                    if (AppData.contactVOList.size() > 0){

                        if (AppData.ContactSynced){

                            Intent intent = new Intent(ReverseGuest.this, ContactPage.class);
                            startActivity(intent);

                        }else {

                            Toast.makeText(ReverseGuest.this,"Contact syncing is going on!",Toast.LENGTH_SHORT).show();

                        }


                    }else {

                        Toast.makeText(ReverseGuest.this,"Contact syncing is going on!",Toast.LENGTH_SHORT).show();

                    }

                }else {

                    Toast.makeText(ReverseGuest.this,"Contact syncing is going on!",Toast.LENGTH_SHORT).show();

                }
            }
        });

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppData.selectedContact!=null) {
                    AppData.selectedContact.clear();
                }
                finish();
            }
        });
    }


    public void HideInviteList() {

        AppData.Contact_Block = false;

        //BookingAsHostLayout.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        AppData.PhoneSelectectedContacts = new ArrayList<Phone_Selected_SetterGetter>();
        AppData.SplitterSelectedContacts = new ArrayList<Splitter_Selected_SetterGetter>();

        AppData.PhoneSelectectedContacts.clear();
        AppData.SplitterSelectedContacts.clear();

        if(AppData.Contact_Block==true) {
            recyclerView.setVisibility(View.VISIBLE);

            if (AppData.selectedContact != null) {
                if (AppData.selectedContact.size() > 0) {

                    Log.d("Rv","visiable");

                    recyclerView = (RecyclerView) findViewById(R.id.rv);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ReverseGuest.this, LinearLayoutManager.HORIZONTAL, false));

                    adapter = new ReverseContact(ReverseGuest.this);
                    recyclerView.setAdapter(adapter);
                }
            }

        }else {
            recyclerView.setVisibility(View.INVISIBLE);

        }
    }


    public void Vol_InviteGuest(String phone,String name){


        GueslistProgress.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"guestlist_control?userid="+sharedPreferences.getString("UserId","")+"&event_id="+AppData.EventId+"&phone="+phone+"&name="+name;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("GuestList_Responce::",response.toString());

                String message = null;

                try {
                    String status = response.getString("status");

                    message = response.getString("message");

                    if (status.equals("success")){


                        JSONArray non_appuser =   response.getJSONArray("non_appuser");

                        if (non_appuser.length() > 0){


                            for (int i = 0;i<non_appuser.length();i++){

                                if (non_appuser.length() > 1){

                                    if (i == 0){

                                        SendSms_PhoneNumber = non_appuser.getString(i) + ";";
                                    }

                                    else if (i == non_appuser.length() - 1){

                                        SendSms_PhoneNumber = SendSms_PhoneNumber + non_appuser.getString(i);

                                    }else {

                                        SendSms_PhoneNumber = SendSms_PhoneNumber + non_appuser.getString(i) + ";";

                                    }

                                }else {

                                    SendSms_PhoneNumber = non_appuser.getString(i);

                                }


// Here, thisActivity is the current activity
                                if (ContextCompat.checkSelfPermission(ReverseGuest.this,
                                        Manifest.permission.SEND_SMS)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    // Should we show an explanation?
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(ReverseGuest.this,
                                            Manifest.permission.SEND_SMS)) {

                                        // Show an explanation to the user *asynchronously* -- don't block
                                        // this thread waiting for the user's response! After the user
                                        // sees the explanation, try again to request the permission.

                                        Log.v("SMS_Permission", "1");

                                        ActivityCompat.requestPermissions(ReverseGuest.this,
                                                new String[]{Manifest.permission.SEND_SMS},
                                                MY_PERMISSIONS_SEND_SMS);


                                    } else {

                                        // No explanation needed, we can request the permission.

                                        ActivityCompat.requestPermissions(ReverseGuest.this,
                                                new String[]{Manifest.permission.SEND_SMS},
                                                MY_PERMISSIONS_SEND_SMS);

                                        Log.v("SMS_Permission", "2");

                                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    }
                                } else {

                                    Log.v("SMS_Permission", "3");

                                    SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);


                                    Uri sendSmsTo = Uri.parse("smsto:" + SendSms_PhoneNumber);
                                    Intent intent = new Intent(
                                            android.content.Intent.ACTION_SENDTO, sendSmsTo);
                                    intent.putExtra("sms_body", sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", "") + " has added you on to the Guest-list at " + getIntent().getExtras().getString("VenueName") +" for " + getIntent().getExtras().getString("EventName") + " on " + getIntent().getExtras().getString("EventDate")
                                            + " please click here to download the STB app for more information.");
                                    startActivity(intent);


                                }


                            }




                        }

                        AppData.FromGuestList = true;

                        finish();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();

                    GueslistProgress.setVisibility(View.GONE);
                }

                GueslistProgress.setVisibility(View.GONE);

                Toast.makeText(ReverseGuest.this,message,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                GueslistProgress.setVisibility(View.GONE);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
