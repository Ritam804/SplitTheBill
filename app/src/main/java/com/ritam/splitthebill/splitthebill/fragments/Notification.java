package com.ritam.splitthebill.splitthebill.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.NotificationAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.push.MyFirebaseMessagingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import setergeter.Notifications_SetterGetter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Notification.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Notification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notification extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView NotificationList;
    NotificationAdapter adapter;

    private OnFragmentInteractionListener mListener;

    ArrayList<Notifications_SetterGetter> AllNotificationValues;

    TextView NoNotificationsText;

    MyReceiver myReceiver;

    public Notification() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment Notification.
     */
    // TODO: Rename and change types and number of parameters
    public static Notification newInstance() {
        Notification fragment = new Notification();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            AppData.NotificationFirstPAge = 1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);


    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppData.FromNotificationPage = true;

        NotificationList = (RecyclerView) view.findViewById(R.id.rv_notification);
        NotificationList.setHasFixedSize(true);
        NotificationList.setLayoutManager(new LinearLayoutManager(getActivity()));

        NoNotificationsText = (TextView) view.findViewById(R.id.tv_nonotification);

        adapter = null;

        AppData.NotificationFirstPAge = 1;

        Vol_AllNotifications(1);

        //adapter=new EventsAdapter();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void Vol_AllNotifications(int page) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AutoLogin", getActivity().MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "notification_control?id=" + sharedPreferences.getString("UserId", "") + "&page=" + page + "&limit=10";

        Log.v("Notifications Url:::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("success")) {

                        if (message.equals("data found")) {

                            NoNotificationsText.setVisibility(View.GONE);
                            NotificationList.setVisibility(View.VISIBLE);


                            AllNotificationValues = new ArrayList<Notifications_SetterGetter>();

                            AllNotificationValues.clear();



                            int count = response.getInt("count");
                            JSONArray Details = response.getJSONArray("Details");

                            if (Details.length() > 0) {

                                for (int i = 0; i < Details.length(); i++) {

                                    JSONObject NotificationDetails = Details.getJSONObject(i);

                                    String date_added = NotificationDetails.getString("date_added");
                                    String title = NotificationDetails.getString("title");
                                    String type = NotificationDetails.getString("type");
                                    String description = NotificationDetails.getString("description");
                                    String event_id = NotificationDetails.getString("event_id");
                                    int expire_status = NotificationDetails.getInt("expire_status");
                                    String qb_groupid = NotificationDetails.getString("qb_groupid");
                                    String qb_senderid = NotificationDetails.getString("qb_senderid");
                                    String venuename = NotificationDetails.getString("venuename");

                                    String image = NotificationDetails.getString("image");
                                    String offer_amount = NotificationDetails.getString("offer_amount");
                                    String offer_male = NotificationDetails.getString("offer_male");
                                    String offer_female = NotificationDetails.getString("offer_female");
                                    String event_name = NotificationDetails.getString("event_name");
                                    String eventStartTime = NotificationDetails.getString("eventStartTime");
                                    String eventEndTime = NotificationDetails.getString("eventEndTime");
                                    String event_day = NotificationDetails.getString("event_day");
                                    String event_month = NotificationDetails.getString("event_month");
                                    String event_year = NotificationDetails.getString("event_year");
                                    String requester_id = NotificationDetails.getString("requester_id");
                                    String requested_amount = NotificationDetails.getString("requested_amount");
                                    String follower_id = NotificationDetails.getString("follower_id");
                                    String venueid = NotificationDetails.getString("venueid");

                                    JSONObject tabledet = NotificationDetails.getJSONObject("tabledet");

                                    Notifications_SetterGetter notifications_setterGetter = new Notifications_SetterGetter(date_added, title, type, description, event_id, expire_status, tabledet, image, offer_amount, offer_male, offer_female, event_name, eventStartTime, eventEndTime, event_day, event_month, event_year, requester_id, requested_amount, follower_id,qb_groupid,qb_senderid,venueid,venuename);
                                    AllNotificationValues.add(notifications_setterGetter);


                                }


                                if (adapter == null) {

                                    AppData.NotificationFirstPAge = 2;
                                    adapter = new NotificationAdapter(getActivity(), AllNotificationValues, Notification.this);
                                    NotificationList.setAdapter(adapter);

                                } else {

                                    adapter.Update(AllNotificationValues);

                                }


                            }

                        } else {


                            if (AppData.NotificationFirstPAge == 1) {

                                NoNotificationsText.setVisibility(View.VISIBLE);
                                NotificationList.setVisibility(View.GONE);

                            }


                        }

                    } else {

                        if (AppData.NotificationFirstPAge == 1) {

                            NoNotificationsText.setVisibility(View.VISIBLE);
                            NotificationList.setVisibility(View.GONE);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    if (AppData.NotificationFirstPAge == 1) {

                        NoNotificationsText.setVisibility(View.VISIBLE);
                        NotificationList.setVisibility(View.GONE);

                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (AppData.NotificationFirstPAge == 1) {

                    NoNotificationsText.setVisibility(View.VISIBLE);
                    NotificationList.setVisibility(View.GONE);

                }


            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }



    @Override
    public void onResume() {
        super.onResume();
        Log.v("onResume","Booking");


        AppData.setIsNotificationPage("YES");

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyFirebaseMessagingService.Notification_Action);
        getActivity().registerReceiver(myReceiver, intentFilter);

        if (AppData.NotificationFirstPAge == 2){


            adapter = null;

            AppData.NotificationFirstPAge = 1;

            Vol_AllNotifications(1);

        }



    }

    @Override
    public void onPause() {
        super.onPause();

        AppData.setIsNotificationPage("NO");
        Log.v("onPause","Booking");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("onStart","Booking");

        AppData.setIsNotificationPage("YES");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("onStop","Booking");

        AppData.setIsNotificationPage("NO");

        try {
            getActivity().unregisterReceiver(myReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        AppData.setIsNotificationPage("NO");
    }

    private class MyReceiver extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {

            adapter = null;

            AppData.NotificationFirstPAge = 1;

            Vol_AllNotifications(1);

        }
    }


    public void Vol_AcceptFollowRequest(String reply,String toId){

        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences("AutoLogin",getActivity().MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"follow_control/followReqRply?reply="+reply+"&to_id="+toId+"&from_id="+sharedPreferences.getString("UserId","");

        Log.v("FollowUrl:::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");


                    if (status.equals("success")) {

                        adapter = null;

                        AppData.NotificationFirstPAge = 1;

                        Vol_AllNotifications(1);

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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public void Vol_AcceptOrDeclineInGroupList(String EventId,String Type){

        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences("AutoLogin",getActivity().MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"guestaccept_control?userid="+sharedPreferences.getString("UserId","")+"&event_id="+EventId+"&type="+Type;

        Log.v("Vol_AcceptFollowRequest_Url::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");

                    if (status.equals("success")) {

                        adapter = null;

                        AppData.NotificationFirstPAge = 1;

                        Vol_AllNotifications(1);

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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);



    }

}
