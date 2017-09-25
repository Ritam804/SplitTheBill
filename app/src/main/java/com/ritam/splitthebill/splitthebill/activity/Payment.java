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
import com.google.gson.JsonArray;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.CardAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import setergeter.CardDetails_SetterGetter;

public class Payment extends AppCompatActivity {

    LinearLayout back;
    RelativeLayout add_card;
    RecyclerView recyclerView;
    CardAdapter adapter;
    LinearLayout Button_Pay;


    ArrayList<CardDetails_SetterGetter> CardDetailsArray;

    TextView PayPrice;


    String SendSms_PhoneNumber = "";

    String NonAppUser = "NO";

    ProgressBar Loader;

    TextView NoCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        back = (LinearLayout) findViewById(R.id.back);
        add_card = (RelativeLayout) findViewById(R.id.add_card);

        PayPrice = (TextView) findViewById(R.id.tv_payprice);

        PayPrice.setText("$" + AppData.Amount);

        Button_Pay = (LinearLayout) findViewById(R.id.ll_pay);

        Loader = (ProgressBar) findViewById(R.id.progressBar9);

        NoCard = (TextView) findViewById(R.id.tv_nocard);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));


//        CardDetailsArray = new ArrayList<CardDetails_SetterGetter>();
//
//        for (int i = 0; i < 2; i++) {
//
//            CardDetails_SetterGetter cardDetails_setterGetter = new CardDetails_SetterGetter(false, "", "", "");
//            CardDetailsArray.add(cardDetails_setterGetter);
//
//        }
//
//        adapter = new CardAdapter(Payment.this, CardDetailsArray);
//        recyclerView.setAdapter(adapter);


        Vol_CardDetails();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button_Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (AppData.FromButton.equals("HostTheTable")) {

                    Vol_Pay();

                } else if (AppData.FromButton.equals("ContactTheHost")) {

                    Intent intent = new Intent(Payment.this, HomeActivity.class);
                    intent.putExtra("FromPage", "Payment");
                    intent.putExtra("PhoneNumber", "");
                    intent.putExtra("NonAppUser", false);
                    startActivity(intent);
                    finish();

                }


            }
        });

        add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Payment.this, AddCard.class));

            }
        });
    }

    public void Vol_Pay() {

        String url = null;

        Loader.setVisibility(View.VISIBLE);
        Button_Pay.setClickable(false);
        Button_Pay.setEnabled(false);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);


        if (AppData.FromButton.equals("HostTheTable")) {


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

                            Log.v("SelectedContact_Lenth", phone1);

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

            if (name != null){

                try {
                    name = URLEncoder.encode(name, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }


            //url = AppData.DomainUrlForProfile + "booking_control?userid=" + sharedPreferences.getString("UserId", "") + "&event_id=" + AppData.EventId + "&table_id=" + AppData.TableId + "&male=" + AppData.MaleCount + "&female=" + AppData.FemaleCount + "&price=" + AppData.Amount + "&phone=" + phone;

            url = AppData.DomainUrlForProfile + "booking_control?userid=" + sharedPreferences.getString("UserId", "") + "&event_id=" + AppData.EventId + "&table_id=" + AppData.TableId + "&male=" + AppData.MaleCount + "&female=" + AppData.FemaleCount + "&price=" + AppData.Amount + "&phone=" + phone + "&name=" + name + "&cardId=" + AppData.CardId + "&qb_groupid=" + "&qb_userid="+sharedPreferences.getString("QB_UserId","")+"&promoter_id="+AppData.SelectedPromoterId;


        } else if (AppData.FromButton.equals("ContactTheHost")) {

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


            //url = AppData.DomainUrlForProfile+"requesthost_control?requester_id="+sharedPreferences.getString("UserId", "")+"&host_id="+AppData.HostId+"&event_id="+AppData.EventId+"&table_id="+AppData.TableId + "&price=" + AppData.Amount + "&phone=" + phone + "&male=" + AppData.MaleCount + "&female=" + AppData.FemaleCount;

            url = AppData.DomainUrlForProfile + "booking_control?userid=" + sharedPreferences.getString("UserId", "") + "&event_id=" + AppData.EventId + "&table_id=" + AppData.TableId + "&male=" + AppData.MaleCount + "&female=" + AppData.FemaleCount + "&price=" + AppData.Amount + "&phone=" + phone + "&name=" + name +"&cardId=" + AppData.CardId + "&qb_groupid=" + "&qb_userid="+sharedPreferences.getString("QB_UserId","");


        }


        Log.v("PayUrl::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("PayUrlResponse::", response.toString());

                String message = null,event_date = null,event_name = null;

                try {


                    String status = response.getString("status");

                    event_date = response.getString("event_date");
                    event_name = response.getString("event_name");

                    message = response.getString("message");

                    if (status.equals("success")) {

                        JSONArray non_appuser = response.getJSONArray("non_appuser");

                        if (non_appuser.length() > 0) {

                            NonAppUser = "YES";

                            for (int i = 0; i < non_appuser.length(); i++) {

                                if (non_appuser.length() > 1) {

                                    if (i == 0) {

                                        SendSms_PhoneNumber = non_appuser.getString(i) + ";";
                                    } else if (i == non_appuser.length() - 1) {

                                        SendSms_PhoneNumber = SendSms_PhoneNumber + non_appuser.getString(i);

                                    } else {

                                        SendSms_PhoneNumber = SendSms_PhoneNumber + non_appuser.getString(i) + ";";

                                    }

                                } else {

                                    SendSms_PhoneNumber = non_appuser.getString(i);

                                }


                            }


                        } else {

                            NonAppUser = "NO";

                        }


                        Log.v("PayUrlResponse", SendSms_PhoneNumber);

                        Intent intent = new Intent(Payment.this, HomeActivity.class);
                        intent.putExtra("FromPage", "Payment");
                        intent.putExtra("PhoneNumber", SendSms_PhoneNumber);
                        intent.putExtra("NonAppUser", NonAppUser);
                        intent.putExtra("EventDate", event_date);
                        intent.putExtra("EventName", event_name);
                        intent.putExtra("Purpose","NormalMessage");
                        startActivity(intent);
                        finish();


                        for (int i = 0; i < AppData.contactVOList.size(); i++) {

                            AppData.contactVOList.get(i).setCheckStatus(false);

                        }

                    }

                    Loader.setVisibility(View.GONE);

                    Button_Pay.setClickable(true);
                    Button_Pay.setEnabled(true);

                } catch (JSONException e) {
                    e.printStackTrace();

                    Loader.setVisibility(View.GONE);
                    Button_Pay.setClickable(true);
                    Button_Pay.setEnabled(true);
                }

                Toast.makeText(Payment.this, message, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Loader.setVisibility(View.GONE);
                Button_Pay.setClickable(true);
                Button_Pay.setEnabled(true);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);


    }


    public void Vol_CardDetails() {

        final SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "cardlist_control?userid=" + sharedPreferences.getString("UserId", "");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String message;

                try {

                    String status = response.getString("status");
                    message = response.getString("message");

                    if (status.equals("success") && message.equals("Data found")) {

                        JSONArray Details = response.getJSONArray("Details");

                        if (Details.length() > 0){

                            CardDetailsArray = new ArrayList<CardDetails_SetterGetter>();

                            for (int i=0;i<Details.length();i++){

                                NoCard.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                                JSONObject CardDetails = Details.getJSONObject(i);

                                String card_id = CardDetails.getString("card_id");
                                String last_digit = CardDetails.getString("last_digit");
                                String brand = CardDetails.getString("brand");
                                String default_status = CardDetails.getString("default_status");

                                if (default_status.equals("1")){

                                    CardDetails_SetterGetter cardDetails_setterGetter = new CardDetails_SetterGetter(true,brand,sharedPreferences.getString("first_name","")+" "+sharedPreferences.getString("last_name",""),last_digit,card_id);
                                    CardDetailsArray.add(cardDetails_setterGetter);

                                }else if (default_status.equals("0")){

                                    CardDetails_SetterGetter cardDetails_setterGetter = new CardDetails_SetterGetter(false,brand,sharedPreferences.getString("first_name","")+" "+sharedPreferences.getString("last_name",""),last_digit,card_id);
                                    CardDetailsArray.add(cardDetails_setterGetter);

                                }

                            }

                            adapter = new CardAdapter(Payment.this, CardDetailsArray);
                            recyclerView.setAdapter(adapter);

                        }else {

                            NoCard.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);

                        }

                    }else {

                        NoCard.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    NoCard.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NoCard.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }


}
