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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.EventRating_Splitters_Adapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import setergeter.EventRating_Splitters_SetterGetter;

public class EventRating extends AppCompatActivity {

    TextView EventName;

    LinearLayout Button_Back,Button_Submit;

    RatingBar RateEvent;

    RecyclerView SplitterList;

    public ArrayList<EventRating_Splitters_SetterGetter> EventRatingSplittersDetails;

    String VenueRating,VenueId,ProfileRating,SplitterId,EventId;

    ProgressBar RatingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_rating);

        EventName = (TextView) findViewById(R.id.evntname);

        Button_Back = (LinearLayout) findViewById(R.id.ll_back_eventrating);

        RateEvent = (RatingBar) findViewById(R.id.rating_bar);

        Button_Submit = (LinearLayout) findViewById(R.id.ll_submit);

        RatingProgress = (ProgressBar) findViewById(R.id.progressBar);


        SplitterList = (RecyclerView) findViewById(R.id.spliterlist);

        SplitterList.setHasFixedSize(true);
        SplitterList.setLayoutManager(new LinearLayoutManager(EventRating.this));

        EventId = getIntent().getExtras().getString("EventId");
        VenueId = getIntent().getExtras().getString("VenueId");


        AllDetails();

        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        Button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(EventRating.this,"EventRating : "+EventRatingSplittersDetails.get(0).getUserRating(),Toast.LENGTH_SHORT).show();

                VenueRating = String.valueOf(RateEvent.getRating());

                if (EventRatingSplittersDetails != null && EventRatingSplittersDetails.size() > 0){


                    for (int i=0;i<EventRatingSplittersDetails.size();i++){

                        if (i == 0){

                            ProfileRating = String.valueOf(EventRatingSplittersDetails.get(i).getUserRating());
                            SplitterId = EventRatingSplittersDetails.get(i).getUserId();

                        }else {

                            ProfileRating = ProfileRating+","+String.valueOf(EventRatingSplittersDetails.get(i).getUserRating());
                            SplitterId = SplitterId+","+EventRatingSplittersDetails.get(i).getUserId();

                        }

                    }


                }else {

                    ProfileRating = "";
                    SplitterId = "";

                }


                Vol_Submit();

            }
        });


    }

    public void AllDetails(){

        RatingProgress.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);


        String url = AppData.DomainUrlForProfile+"event_people_list?event_id="+getIntent().getExtras().getString("EventId")+"&table_id="+getIntent().getExtras().getString("TableId")+"&venue_id="+VenueId+"&logged_in="+sharedPreferences.getString("UserId","");

        Log.v("EventRatingDetails_Url::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    String status = response.getString("status");
                    String message = response.getString("message");


                    if (status.equals("success") && message.equals("data found")){

                        String eventname = response.getString("eventname");
                        String eventimage = response.getString("eventimage");
                        String event_rating = response.getString("event_rating");
                        int eventratedstatus = response.getInt("eventratedstatus");

                        if (eventratedstatus == 1){

                            Button_Submit.setEnabled(false);
                            Button_Submit.setClickable(false);
                            Button_Submit.setBackground(getResources().getDrawable(R.drawable.grey_square_background));

                            RateEvent.setClickable(false);
                            RateEvent.setFocusable(false);
                            RateEvent.setFocusableInTouchMode(false);
                            RateEvent.setIsIndicator(true);

                        }

                        EventName.setText(eventname);
                        RateEvent.setRating(Float.parseFloat(event_rating));

                        VenueRating = event_rating;

                        JSONArray Details = response.getJSONArray("Details");

                        if (Details.length() > 0){

                            EventRatingSplittersDetails = new ArrayList<EventRating_Splitters_SetterGetter>();

                            for (int i=0;i<Details.length();i++){

                                JSONObject splitterlistDetails = Details.getJSONObject(i);

                                String user_id = splitterlistDetails.getString("user_id");
                                String name = splitterlistDetails.getString("name");
                                String user_image = splitterlistDetails.getString("user_image");
                                int user_rating = splitterlistDetails.getInt("user_rating");

                                SharedPreferences sharedPreferences =  getSharedPreferences("AutoLogin",MODE_PRIVATE);

                                if (!(user_id.equals(sharedPreferences.getString("UserId","")))){

                                    EventRating_Splitters_SetterGetter eventRating_splitters_setterGetter =  new EventRating_Splitters_SetterGetter(user_id,name,user_image,user_rating);
                                    EventRatingSplittersDetails.add(eventRating_splitters_setterGetter);

                                }



                            }


                            if (EventRatingSplittersDetails.size() > 0){

                                EventRating_Splitters_Adapter eventRating_splitters_adapter = new EventRating_Splitters_Adapter(EventRatingSplittersDetails,EventRating.this,EventRating.this,eventratedstatus);
                                SplitterList.setAdapter(eventRating_splitters_adapter);

                            }

                        }

                    }

                    RatingProgress.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    RatingProgress.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                RatingProgress.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public void Vol_Submit(){

        RatingProgress.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences =  getSharedPreferences("AutoLogin",MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"profilevenuerating_control?userid="+sharedPreferences.getString("UserId","")+"&venue_id="+VenueId+"&venue_rating="+VenueRating+"&profile_rating="+ProfileRating+"&spliterid="+SplitterId+"&event_id="+EventId;

        Log.v("ChangeRating_Url::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");

                    if (status.equals("success")){

                        finish();

                        RatingProgress.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    RatingProgress.setVisibility(View.GONE);
                }

                try {
                    Toast.makeText(EventRating.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();

                    RatingProgress.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                RatingProgress.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
