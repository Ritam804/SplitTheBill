package com.ritam.splitthebill.splitthebill.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.SelectTableAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import setergeter.Promoter_SetterGetter;
import setergeter.SelectTableSetterGetter;

public class SelectTable extends AppCompatActivity {

    RecyclerView TableList;
    SelectTableAdapter adapter;

    LinearLayout Button_Back;

    ArrayList<SelectTableSetterGetter>  SelectTabelArray;

    ProgressBar Loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_table);

        TableList = (RecyclerView) findViewById(R.id.rv_tables);
        Button_Back = (LinearLayout) findViewById(R.id.ll_selecttable_back);

        Loader = (ProgressBar) findViewById(R.id.progressBar4);


        TableList.setHasFixedSize(true);
        TableList.setLayoutManager(new LinearLayoutManager(SelectTable.this));

        //adapter=new EventsAdapter();


        FetchingTableValues();

        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SelectTable.this, EventDetails.class);
                intent.putExtra("FromPrevious",getIntent().getExtras().getBoolean("FromPrevious"));
                startActivity(intent);

            }
        });

    }


    public void FetchingTableValues() {

        Loader.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "gettabledetails_control?eventid=" + AppData.EventId + "&logged_in="+sharedPreferences.getString("UserId", "");

        Log.v("TableUrl::::::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");
                    String message = response.getString("message");
                    String Address = response.getString("Address");

                    JSONArray promoters = response.getJSONArray("promoters");

                    if (promoters.length() > 0){

                        AppData.PromoterDetails = new ArrayList<Promoter_SetterGetter>();

                        Promoter_SetterGetter promoter_setterGetter1 = new Promoter_SetterGetter("0","STB");
                        AppData.PromoterDetails.add(promoter_setterGetter1);



                        for (int i=0;i<promoters.length();i++){

                            JSONObject jsonObject = promoters.getJSONObject(i);

                            Promoter_SetterGetter promoter_setterGetter = new Promoter_SetterGetter(jsonObject.getString("promoter_id"),jsonObject.getString("promoter_name"));
                            AppData.PromoterDetails.add(promoter_setterGetter);

                        }



                    }else {

                        AppData.PromoterDetails = new ArrayList<Promoter_SetterGetter>();

                        Promoter_SetterGetter promoter_setterGetter1 = new Promoter_SetterGetter("0","STB");
                        AppData.PromoterDetails.add(promoter_setterGetter1);

                    }

                    JSONArray Details = response.getJSONArray("Details");

                    if (Details.length() > 0) {

                        SelectTabelArray = new ArrayList<SelectTableSetterGetter>();

                        for (int i = 0; i < Details.length(); i++) {

                            JSONObject Result = Details.getJSONObject(i);

                            String table_id = Result.getString("table_id");
                            String group_id = Result.getString("group_id");
                            String table_name = Result.getString("table_name");
                            String cost = Result.getString("cost");
                            String min_amount = Result.getString("min_amount");
                            String table_status = Result.getString("table_status");
                            String number_of_available = Result.getString("number_of_available");
                            String total = Result.getString("total");
                            String what_to_do = Result.getString("what_to_do");
                            String host_name = Result.getString("host_name");
                            String host_id = Result.getString("host_id");
                            int booking_status = Result.getInt("booking_status");
                            int decline_status = Result.getInt("decline_status");
                            int request_status = Result.getInt("request_status");
                            String remaining_amount = Result.getString("remaining_amount");

                            SelectTableSetterGetter selectTableSetterGetter = new SelectTableSetterGetter(table_name, cost, min_amount, table_status, number_of_available, total, what_to_do,table_id,host_name,host_id,booking_status,decline_status,request_status,group_id,remaining_amount,Address);
                            SelectTabelArray.add(selectTableSetterGetter);

                        }


                        adapter = new SelectTableAdapter(SelectTable.this,SelectTabelArray);
                        TableList.setAdapter(adapter);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Loader.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Loader.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
