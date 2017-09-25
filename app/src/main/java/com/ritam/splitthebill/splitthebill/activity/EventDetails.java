package com.ritam.splitthebill.splitthebill.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EventDetails extends AppCompatActivity {

    LinearLayout Button_Back, Button_BookNow, Button_GuestList, Button_BottleMenu, Button_TableMenu, Button_Chat, Button_Map;

    GoogleMap googleMap;

    TextView EventName, EventUrl, EventDate, EventMonthAndYear, EventStartTime, EventEndTime, EventStartTimeAmOrPm, EventDescription, VenueName, VenueAddress;
    ImageView EventImage;

    String tableMenu, bottleMenu;
    int BookingStatus;
    int RequestStatus;


    MapView Map;

    TextView Button_Text;
    boolean BookNowButton = false;

    int expire_status;
    String venue_id;

    ProgressBar ReviewDialog_ProgressBar;

    String VenueLat,VenueLong;

    int eventratedstatus;
    String event_guest_available,event_age_limit,event_music,event_note,ownerName;

    int NumberOfBooking;

    String eventId,event_name,event_date,event_day,event_month,event_year,eventDescription,eventLink,eventStartTime,eventEndTime,event_image,venue_name,venue_rating,venue_address,eventStatus,eventOwner;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Button_Back = (LinearLayout) findViewById(R.id.ll_back);

        Button_BookNow = (LinearLayout) findViewById(R.id.ll_booknow);

        Button_GuestList = (LinearLayout) findViewById(R.id.ll_guestlist);

        EventName = (TextView) findViewById(R.id.eventname);
        EventUrl = (TextView) findViewById(R.id.eventurl);
        EventDate = (TextView) findViewById(R.id.tv_event_date);
        EventMonthAndYear = (TextView) findViewById(R.id.tv_event_monthyear);
        EventStartTime = (TextView) findViewById(R.id.event_starttime);
        EventEndTime = (TextView) findViewById(R.id.event_endtime);
        EventStartTimeAmOrPm = (TextView) findViewById(R.id.event_startdate_am);
        EventDescription = (TextView) findViewById(R.id.tv_eventdesc);
        VenueName = (TextView) findViewById(R.id.tv_venuename);
        VenueAddress = (TextView) findViewById(R.id.tv_venueaddress);
        EventImage = (ImageView) findViewById(R.id.eventimage);
        Button_BottleMenu = (LinearLayout) findViewById(R.id.ll_bottlemenu);
        Button_TableMenu = (LinearLayout) findViewById(R.id.ll_tablemenu);
        Button_Chat = (LinearLayout) findViewById(R.id.ll_chat);
        Button_Map = (LinearLayout) findViewById(R.id.ll_map);
        Button_Text = (TextView) findViewById(R.id.tv_buttontext);
        //FullView_Chat = (LinearLayout) findViewById(R.id.ll_full_chat);

        FetchingEventDetails();


        //Button_Chat.setVisibility(View.VISIBLE);


        if (getIntent().getExtras().getBoolean("FromPrevious")) {

            Button_BookNow.setVisibility(View.GONE);

        } else {

            Button_BookNow.setVisibility(View.VISIBLE);

        }


        Map = (MapView) findViewById(R.id.map);
        Map.onCreate(savedInstanceState);
        Map.onResume();


        if (Map != null) {
            Map.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {

/* googleMap.addMarker(new MarkerOptions()
.icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
.anchor(0.0f, 1.0f)
.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(Long))));*/

                    googleMap = mMap;

// For showing a move to my location button
//googleMap.setMyLocationEnabled(true);

// For dropping a marker at a point on the Map
                    LatLng latLng = new LatLng(22.5848, 88.4903);
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(Html.fromHtml("Ecospace"))));

// For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(10).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }
            });

        }


        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (AppData.FromPage.equals("Booking") || AppData.FromPage.equals("Notification")) {

                    finish();

                } else {

                    Intent intent = new Intent(EventDetails.this, HomeActivity.class);
                    startActivity(intent);

                }


            }
        });


        Button_BookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (BookNowButton){



                            Intent intent = new Intent(EventDetails.this, SelectTable.class);
                            intent.putExtra("FromPrevious", getIntent().getExtras().getBoolean("FromPrevious"));
                            startActivity(intent);





                }else {

                    LinearLayout Button_Cross , Button_Submit;
                    final EditText ReviewText;
                    final RatingBar ReviewRating;

                    View view1 = LayoutInflater.from(EventDetails.this).inflate(R.layout.dialog_eventrating, null);
                    final Dialog dialog = new Dialog(EventDetails.this, R.style.MaterialDialogSheet);
                    dialog.setContentView(view1);
                    dialog.setCancelable(true);
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    dialog.show();


                    Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_back_review);

                    ReviewText = (EditText) view1.findViewById(R.id.et_rating);

                    ReviewRating = (RatingBar) view1.findViewById(R.id.rating_bar_review);

                    Button_Submit = (LinearLayout) view1.findViewById(R.id.ll_submit_review);

                    ReviewDialog_ProgressBar = (ProgressBar) view1.findViewById(R.id.progressBar5);


                    Button_Cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                        }
                    });


                    Button_Submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Vol_ReviewPost(ReviewRating.getRating(),ReviewText.getText().toString(),dialog);


                        }
                    });

                }


                //finish();

            }
        });

        Button_GuestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!(getIntent().getExtras().getBoolean("FromPrevious"))) {

                   // if (BookingStatus == 0) {


                        LinearLayout Button_Cross, Button_ReserveGuestList;

                    TextView Dialog_EventName,Dialog_EventGuestAvailable,Dialog_EventStartTime,Dialog_EventStartTime_AmOrPm,Dialog_EventEndTime,Dialog_EventEndTime_AmOrPm;
                    TextView Dialog_EventAgeLimit,Dialog_EventMusicType,Dialog_EventNotes;

                        View view1 = LayoutInflater.from(EventDetails.this).inflate(R.layout.dialog_guestlist, null);
                        final Dialog dialog = new Dialog(EventDetails.this, R.style.MaterialDialogSheet);
                        dialog.setContentView(view1);
                        dialog.setCancelable(true);
                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        dialog.getWindow().setGravity(Gravity.CENTER);
                        dialog.show();


                        Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_back);

                        Button_ReserveGuestList = (LinearLayout) view1.findViewById(R.id.ll_reserveguestlist);


                    Dialog_EventName = (TextView) view1.findViewById(R.id.dialog_tv_eventname);
                    Dialog_EventGuestAvailable = (TextView) view1.findViewById(R.id.dialog_eventguestavailable);
                    Dialog_EventStartTime = (TextView) view1.findViewById(R.id.dialog_tv_starttime);
                    Dialog_EventStartTime_AmOrPm = (TextView) view1.findViewById(R.id.dialog_tv_starttime_amorpm);
                    Dialog_EventEndTime = (TextView) view1.findViewById(R.id.dialog_eventendtime);
                    Dialog_EventEndTime_AmOrPm = (TextView) view1.findViewById(R.id.dialog_endtime_amorpm);
                    Dialog_EventAgeLimit = (TextView) view1.findViewById(R.id.dialog_eventagelimit);
                    Dialog_EventMusicType = (TextView) view1.findViewById(R.id.dialog_tv_eventmusic);
                    Dialog_EventNotes = (TextView) view1.findViewById(R.id.dialog_tv_eventnotes);

                    Dialog_EventName.setText(event_name);
                    Dialog_EventGuestAvailable.setText(event_guest_available);

                    String[] StartTimeDevider = eventStartTime.split("\\s+");

                    Dialog_EventStartTime.setText(StartTimeDevider[0]);
                    Dialog_EventStartTime_AmOrPm.setText(StartTimeDevider[1]);


                    String[] EndTimeDevider = eventEndTime.split("\\s+");

                    Dialog_EventEndTime.setText(EndTimeDevider[0]);
                    Dialog_EventEndTime_AmOrPm.setText(EndTimeDevider[1]);


                    Dialog_EventAgeLimit.setText(event_age_limit);
                    Dialog_EventMusicType.setText(event_music);
                    Dialog_EventNotes.setText(event_note);

                        Button_Cross.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();

                            }
                        });

                        Button_ReserveGuestList.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(EventDetails.this, ReverseGuest.class);
                                intent.putExtra("VenueName",venue_name);
                                intent.putExtra("EventName",event_name);
                                intent.putExtra("EventDate",event_date);

                                startActivity(intent);
                                dialog.dismiss();

                            }
                        });

                   // }


                }


            }
        });

//        Button_Chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (BookingStatus == 1) {
//
//                    Toast.makeText(EventDetails.this, "Chat button is activated", Toast.LENGTH_SHORT).show();
//
//
//
//
//                } else {
//
//                    Toast.makeText(EventDetails.this, "Chat button is deactivated", Toast.LENGTH_SHORT).show();
//
//
//                }
//
//            }
//        });


        Button_BottleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(tableMenu.equals("")) && !(tableMenu.isEmpty()) && !(tableMenu.equals(null)) && !(tableMenu.equals("null"))) {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tableMenu));
                    startActivity(browserIntent);

                }

            }
        });

        Button_TableMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(bottleMenu.equals("")) && !(bottleMenu.isEmpty()) && !(bottleMenu.equals(null)) && !(bottleMenu.equals("null"))) {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bottleMenu));
                    startActivity(browserIntent);

                }

            }
        });

        Button_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout Button_Cross;

                MapView DialogMap;


                View view1 = LayoutInflater.from(EventDetails.this).inflate(R.layout.dialog_mapview, null);
                final Dialog dialog = new Dialog(EventDetails.this, R.style.MaterialDialogSheet);
                dialog.setContentView(view1);
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();


                Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_back);

                DialogMap = (MapView) view1.findViewById(R.id.fullmap);


                DialogMap.onCreate(savedInstanceState);
                DialogMap.onResume();


                if (DialogMap != null) {
                    DialogMap.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap mMap) {

/* googleMap.addMarker(new MarkerOptions()
.icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
.anchor(0.0f, 1.0f)
.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(Long))));*/

                            googleMap = mMap;

// For showing a
// move to my location button
//googleMap.setMyLocationEnabled(true);

// For dropping a marker at a point on the Map
                            LatLng latLng = new LatLng(22.5848, 88.4903);

                            LatLng SecondLatLng = new LatLng(22.6429886, 88.4297074);
                            googleMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(Html.fromHtml("Ecospace"))));
                            googleMap.addMarker(new MarkerOptions().position(SecondLatLng).title(String.valueOf(Html.fromHtml("Airport"))));


                            PolylineOptions rectOptions = new PolylineOptions().color(Color.BLUE).width(7);

                            rectOptions .add(latLng);

                            rectOptions.add(SecondLatLng);

                            googleMap.addPolyline(rectOptions);

// For zooming automatically to the location of the marker
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(10).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        }
                    });

                }


                Button_Cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });

            }
        });


        EventUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(EventUrl.getText().toString().trim()));
                    startActivity(browserIntent);

                }catch (Exception e){

                }


            }
        });

    }


    public void FetchingEventDetails() {

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "eventlisting_control/eventDetails?eventid=" + AppData.EventId + "&userid=" + sharedPreferences.getString("UserId", "");

        Log.v("EventDetails Url::", url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Log.v("EventDetails Response::", response.toString());


                try {

                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("success")) {

                        if (message.equals("Event found")) {

                            JSONObject Details = response.getJSONObject("Details");

                            eventratedstatus = Details.getInt("eventratedstatus");
                            event_guest_available = Details.getString("event_guest_available");
                            event_age_limit = Details.getString("event_age_limit");
                            event_music = Details.getString("event_music");
                            event_note = Details.getString("event_note");
                            eventId = Details.getString("eventId");
                            event_name = Details.getString("event_name");
                            event_date = Details.getString("event_date");
                            event_day = Details.getString("event_day");
                            event_month = Details.getString("event_month");
                            event_year = Details.getString("event_year");
                            eventDescription = Details.getString("eventDescription");
                            eventLink = Details.getString("eventLink");
                            eventStartTime = Details.getString("eventStartTime");
                            eventEndTime = Details.getString("eventEndTime");
                            event_image = Details.getString("event_image");
                            venue_name = Details.getString("venue_name");
                            venue_address = Details.getString("venue_address");
                            venue_rating = Details.getString("venue_rating");
                            VenueLat = Details.getString("venue_lat");
                            VenueLong = Details.getString("venue_long");
                            eventStatus = Details.getString("eventStatus");
                            bottleMenu = Details.getString("bottleMenu");
                            tableMenu = Details.getString("tableMenu");
                            eventOwner = Details.getString("eventOwner");
                            ownerName = Details.getString("ownerName");
                            BookingStatus = Details.getInt("status");
                            RequestStatus = Details.getInt("request_status");
                            expire_status = Details.getInt("expire_status");
                            venue_id = Details.getString("venue_id");
                            NumberOfBooking = Details.getInt("numberbooking");


                            EventName.setText(event_name);
                            EventUrl.setText(eventLink);
                            EventDate.setText(event_day);
                            EventMonthAndYear.setText(event_month + " " + event_year);

                            String[] StartTimeDevider = eventStartTime.split("\\s+");

                            EventStartTime.setText(StartTimeDevider[0]);
                            EventStartTimeAmOrPm.setText(StartTimeDevider[1]);
                            EventEndTime.setText(eventEndTime);
                            EventDescription.setText(eventDescription);
                            VenueName.setText(venue_name);
                            VenueAddress.setText(venue_address);

                            Picasso.with(EventDetails.this).load(event_image).fit().into(EventImage);

                            if (Map != null) {
                                Map.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(GoogleMap mMap) {

/* googleMap.addMarker(new MarkerOptions()
.icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
.anchor(0.0f, 1.0f)
.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(Long))));*/

                                        googleMap = mMap;

// For showing a move to my location button
//googleMap.setMyLocationEnabled(true);

// For dropping a marker at a point on the Map
                                        LatLng latLng = new LatLng(Double.parseDouble(VenueLat), Double.parseDouble(VenueLong));
                                        googleMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(Html.fromHtml("Ecospace"))));

// For zooming automatically to the location of the marker
                                        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(10).build();
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                    }
                                });

                            }


                            if (expire_status == 1){

                                if (AppData.OwnProfile){

                                    Button_Text.setText("RATE NOW");
                                    BookNowButton = false;
                                    Button_Chat.setVisibility(View.GONE);

                                }else {

                                    Button_Text.setText("RATE NOW");
                                    BookNowButton = false;
                                    Button_BookNow.setVisibility(View.GONE);
                                    Button_Chat.setVisibility(View.GONE);

                                }



                            }else {


                                if (NumberOfBooking >= 2){

                                    Button_BookNow.setBackground(getResources().getDrawable(R.drawable.grey_mold_rectangle));
                                    Button_BookNow.setClickable(false);
                                    Button_BookNow.setEnabled(false);

                                }else {

                                    Button_Text.setText("BOOK NOW");
                                    BookNowButton = true;
                                    Button_BookNow.setClickable(true);
                                    Button_BookNow.setEnabled(true);

                                }


                                //Button_Chat.setVisibility(View.VISIBLE);

                            }


                            if (BookingStatus == 1 || RequestStatus == 1){

                                Button_BottleMenu.setVisibility(View.GONE);
                                Button_TableMenu.setVisibility(View.GONE);

                            }else {

                                Button_BottleMenu.setVisibility(View.VISIBLE);
                                Button_TableMenu.setVisibility(View.VISIBLE);

                            }


                            if (BookingStatus == 1) {


//                                if (RequestStatus == 0){
//
//                                    Button_GuestList.setVisibility(View.GONE);
//
//                                }else {
//
//                                    Button_GuestList.setVisibility(View.VISIBLE);
//
//                                }

                                //Button_Chat.setVisibility(View.VISIBLE);
                                //FullView_Chat.setVisibility(View.VISIBLE);
                                //Button_Chat.setEnabled(true);
                                //Button_Chat.setClickable(true);
                                Button_GuestList.setVisibility(View.GONE);

                            } else {


                                Button_Chat.setVisibility(View.GONE);
                                Button_GuestList.setVisibility(View.VISIBLE);
                                //FullView_Chat.setVisibility(View.GONE);
                                Button_Chat.setEnabled(false);
                                Button_Chat.setClickable(false);

                            }

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



    public void Vol_ReviewPost(float rating, String Review, final Dialog dialog){


        ReviewDialog_ProgressBar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences =  getSharedPreferences("AutoLogin",MODE_PRIVATE);

        String review = null;

        try {
            review = URLEncoder.encode(Review,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = AppData.DomainUrlForProfile+"rating_control?userid="+sharedPreferences.getString("UserId","")+"&rating="+rating+"&restaurent="+venue_id+"&comment="+review+"&event_id="+AppData.EventId;

        Log.v("Review Url::",url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("Review Response::",response.toString());

                String message = null;

                try {

                    String status = response.getString("status");
                    message = response.getString("message");

                    if (status.equals("success")){

                        dialog.dismiss();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(EventDetails.this,message,Toast.LENGTH_SHORT).show();

                ReviewDialog_ProgressBar.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ReviewDialog_ProgressBar.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (AppData.FromGuestList){

            FetchingEventDetails();

            AppData.FromGuestList = false;

        }

    }
}


