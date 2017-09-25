package com.ritam.splitthebill.splitthebill.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.GroupUserInfoAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import setergeter.GroupMemberInfo_SetterGetter;

public class GroupDetails extends AppCompatActivity {


    ImageView GroupImage;

    TextView HeaderText,EventName,EventTiming,VenueName,VenueAddress,TableName,Cost;

    LinearLayout Button_ExitGroup,Button_Back;

    RecyclerView GroupMembersList;

    ArrayList<GroupMemberInfo_SetterGetter> GroupINfoArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        GroupImage = (ImageView) findViewById(R.id.iv_groupimage);

        HeaderText = (TextView) findViewById(R.id.tv_headertext);
        EventName = (TextView) findViewById(R.id.tv_eventname);
        EventTiming = (TextView) findViewById(R.id.tv_time);
        VenueName = (TextView) findViewById(R.id.tv_venuename);
        VenueAddress = (TextView) findViewById(R.id.tv_venueaddress);
        TableName = (TextView) findViewById(R.id.tv_tablename);
        Cost = (TextView) findViewById(R.id.tv_cost);

        GroupMembersList = (RecyclerView) findViewById(R.id.rv_groupmember);
        GroupMembersList.setHasFixedSize(true);
        GroupMembersList.setLayoutManager(new LinearLayoutManager(GroupDetails.this));

        Button_ExitGroup = (LinearLayout) findViewById(R.id.ll_exitgroup);

        Button_Back = (LinearLayout) findViewById(R.id.ll_groupdetails_back);


        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        FetchingValue();

    }

    public void FetchingValue(){

        String url = AppData.DomainUrlForProfile+"groupdetails_control?groupid="+getIntent().getExtras().getString("GroupId");

        Log.v("Fetching_group_EventDetails_url::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("success")){

                        if (message.equals("data found")){

                            JSONObject TableObject = response.getJSONObject("Details");

                            String table_name = TableObject.getString("tablename");
                            String table_cost = TableObject.getString("tablecost");


                            String eventId = TableObject.getString("eventId");
                            String event_guest_available = TableObject.getString("event_guest_available");
                            String event_age_limit = TableObject.getString("event_age_limit");
                            String event_music = TableObject.getString("event_music");
                            String event_note = TableObject.getString("event_note");
                            String event_name = TableObject.getString("event_name");
                            String event_date = TableObject.getString("event_date");
                            String event_day = TableObject.getString("event_day");
                            String event_month = TableObject.getString("event_month");
                            String event_year = TableObject.getString("event_year");
                            String eventDescription = TableObject.getString("eventDescription");
                            String eventLink = TableObject.getString("eventLink");
                            String eventStartTime = TableObject.getString("eventStartTime");
                            String eventEndTime = TableObject.getString("eventEndTime");
                            String event_image = TableObject.getString("event_image");

                            String venue_id = TableObject.getString("venue_id");
                            String venue_name = TableObject.getString("venue_name");
                            String venue_rating = TableObject.getString("venue_rating");
                            String venue_address = TableObject.getString("venue_address");
                            String venue_lat = TableObject.getString("venue_lat");
                            String venue_long = TableObject.getString("venue_long");


                            String eventStatus = TableObject.getString("eventStatus");
                            String bottleMenu = TableObject.getString("bottleMenu");
                            String tableMenu = TableObject.getString("tableMenu");
                            String eventOwner = TableObject.getString("eventOwner");
                            String ownerName = TableObject.getString("ownerName");



                            HeaderText.setText(event_name.toUpperCase());
                            EventName.setText(event_name.toUpperCase());
                            EventTiming.setText(event_month+" "+event_day+" "+event_year+" "+eventStartTime+" to "+eventEndTime);
                            VenueName.setText(venue_name.toUpperCase());
                            VenueAddress.setText(venue_address);
                            TableName.setText(table_name);
                            Cost.setText("$"+table_cost);
                            Picasso.with(GroupDetails.this).load(event_image).fit().into(GroupImage);

                            JSONObject host_details = TableObject.getJSONObject("host_details");

                            String user_id = host_details.getString("user_id");
                            String user_name = host_details.getString("user_name");
                            String image = host_details.getString("image");
                            String user_phone = host_details.getString("user_phone");

                            GroupMemberInfo_SetterGetter groupMemberInfo_setterGetter = new GroupMemberInfo_SetterGetter(user_id,user_name,image,user_phone,"1");


                            GroupINfoArray = new ArrayList<GroupMemberInfo_SetterGetter>();

                            GroupINfoArray.add(groupMemberInfo_setterGetter);


                            JSONArray jsonArray = TableObject.getJSONArray("group_details");

                            if (jsonArray.length() > 0){


                                for (int i = 0; i < jsonArray.length(); i++){

                                    JSONObject GroupMemberObject = jsonArray.getJSONObject(i);

                                    String splitterid = GroupMemberObject.getString("user_id");
                                    String splittername = GroupMemberObject.getString("user_name");
                                    String splitterimage = GroupMemberObject.getString("image");
                                    String spliitterphone = GroupMemberObject.getString("user_phone");
                                    String host_status = "0";

                                    GroupMemberInfo_SetterGetter groupMemberInfo_setterGetter1 = new GroupMemberInfo_SetterGetter(splitterid,splittername,splitterimage,spliitterphone,host_status);
                                    GroupINfoArray.add(groupMemberInfo_setterGetter1);

                                }



                            }


                            GroupUserInfoAdapter groupUserInfoAdapter = new GroupUserInfoAdapter(GroupDetails.this,GroupINfoArray);
                            GroupMembersList.setAdapter(groupUserInfoAdapter);

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
