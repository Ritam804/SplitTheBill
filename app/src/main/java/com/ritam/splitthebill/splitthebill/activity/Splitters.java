package com.ritam.splitthebill.splitthebill.activity;

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
import com.ritam.splitthebill.splitthebill.adapter.SplitterlistAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import setergeter.SpliterList_SetterGetter;

public class Splitters extends AppCompatActivity {

    RecyclerView SplitterList;
    ProgressBar Loader;
    LinearLayout Button_Back;

    ArrayList<SpliterList_SetterGetter> AllSplitterDetails;

    LinearLayout NoSplitters;

    SplitterlistAdapter splitterlistAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splitters);


        SplitterList = (RecyclerView) findViewById(R.id.rv_profile_splitters);
        SplitterList.setHasFixedSize(true);
        SplitterList.setLayoutManager(new LinearLayoutManager(Splitters.this));

        Loader = (ProgressBar) findViewById(R.id.progressBar);
        Button_Back = (LinearLayout) findViewById(R.id.ll_back);

        NoSplitters = (LinearLayout) findViewById(R.id.ll_nospltter);

        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        Vol_Splitters(1);


    }


    public void Vol_Splitters(int page){

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"followfollowing_control?user_id="+getIntent().getExtras().getString("UserId")+"&logged_in="+sharedPreferences.getString("UserId","")+"&page="+page+"&limit=10";

        Log.v("Splitter_Url::::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("success") && message.equals("data found")){

                        JSONArray Details = response.getJSONArray("Details");

                        if (Details.length() > 0){

                            AllSplitterDetails = new ArrayList<SpliterList_SetterGetter>();

                            for (int i=0;i<Details.length();i++){

                                JSONObject DetailsObject = Details.getJSONObject(i);

                                String userid = DetailsObject.getString("userid");
                                String rating = DetailsObject.getString("rating");
                                String image = DetailsObject.getString("image");
                                String name = DetailsObject.getString("name");
                                String splits = DetailsObject.getString("splits");
                                String splitter = DetailsObject.getString("splitter");
                                int status_check = DetailsObject.getInt("status_check");

                                SpliterList_SetterGetter spliterList_setterGetter = new SpliterList_SetterGetter(userid,rating,image,name,splits,splitter,status_check);
                                AllSplitterDetails.add(spliterList_setterGetter);

                            }

                            NoSplitters.setVisibility(View.GONE);
                            SplitterList.setVisibility(View.VISIBLE);

                            if (splitterlistAdapter == null){

                                splitterlistAdapter = new SplitterlistAdapter(AllSplitterDetails,Splitters.this);
                                SplitterList.setAdapter(splitterlistAdapter);

                            }else {

                                splitterlistAdapter.Update(AllSplitterDetails);
                            }



                        }else {

                            NoSplitters.setVisibility(View.VISIBLE);
                            SplitterList.setVisibility(View.GONE);

                        }

                    }else {

                        NoSplitters.setVisibility(View.VISIBLE);
                        SplitterList.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    NoSplitters.setVisibility(View.VISIBLE);
                    SplitterList.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NoSplitters.setVisibility(View.VISIBLE);
                SplitterList.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
