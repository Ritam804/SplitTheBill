package com.ritam.splitthebill.splitthebill.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import setergeter.Splits_SetterGetter;

import com.ritam.splitthebill.splitthebill.adapter.SplitsAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Splits extends AppCompatActivity {

    LinearLayout Button_Back, NoSplits;
    RecyclerView SplitsList;

    ArrayList<Splits_SetterGetter> AllSplitsListDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splits);


        Button_Back = (LinearLayout) findViewById(R.id.ll_back);
        NoSplits = (LinearLayout) findViewById(R.id.ll_nosplits);

        SplitsList = (RecyclerView) findViewById(R.id.rv_splits);
        SplitsList.setHasFixedSize(true);
        SplitsList.setLayoutManager(new LinearLayoutManager(Splits.this));

        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        AllSplits();


    }


    public void AllSplits() {

        String url = AppData.DomainUrlForProfile + "splits_control?userid="+getIntent().getExtras().getString("UserId");

        Log.v("AllSplits_Url::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {


                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("success") && message.equals("data found")) {


                        JSONArray Details = response.getJSONArray("Details");

                        if (Details.length() > 0) {

                            AllSplitsListDetails = new ArrayList<Splits_SetterGetter>();

                            for (int i = 0; i < Details.length(); i++) {

                                JSONObject SplitsDetails = Details.getJSONObject(i);

                                String event_id = SplitsDetails.getString("event_id");
                                String event_rating = SplitsDetails.getString("event_rating");
                                String event_month = SplitsDetails.getString("event_month");
                                String event_date = SplitsDetails.getString("event_date");
                                String event_time = SplitsDetails.getString("event_time");
                                String event_end_time = SplitsDetails.getString("event_end_time");
                                String eventdate = SplitsDetails.getString("eventdate");
                                String eventname = SplitsDetails.getString("eventname");
                                String venue_name = SplitsDetails.getString("venue_name");
                                String venue_address = SplitsDetails.getString("venue_address");
                                String cost = SplitsDetails.getString("cost");
                                String table_name = SplitsDetails.getString("table_name");
                                String table_id = SplitsDetails.getString("table_id");


                                JSONObject host = SplitsDetails.getJSONObject("host");
                                String host_id = host.getString("host_id");
                                String host_payment = host.getString("host_payment");
                                String host_name = host.getString("host_name");
                                String host_male = host.getString("host_male");
                                String host_female = host.getString("host_female");
                                String host_rating = host.getString("host_rating");
                                String host_image = host.getString("host_image");


                                JSONArray splitters = SplitsDetails.getJSONArray("splitters");

                                Splits_SetterGetter splits_setterGetter = new Splits_SetterGetter(event_id, event_rating, event_month, event_date, event_time
                                        , event_end_time, eventdate, eventname, venue_name, venue_address, cost, table_name, table_id, host_id, host_payment, host_name,
                                        host_male, host_female, host_rating, host_image, splitters);

                                AllSplitsListDetails.add(splits_setterGetter);

                            }

                            SplitsAdapter splitsAdapter = new SplitsAdapter(AllSplitsListDetails,Splits.this);
                            SplitsList.setAdapter(splitsAdapter);

                        }

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


}
