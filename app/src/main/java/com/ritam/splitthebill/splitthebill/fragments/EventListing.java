package com.ritam.splitthebill.splitthebill.fragments;

import android.content.Context;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.EventsAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import setergeter.EventList_SetterGetter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventListing.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventListing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListing extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EventsAdapter adapter;
    //AppData.recyclerView AppData.recyclerView;



    ArrayList<EventList_SetterGetter> EventListArray;

    private OnFragmentInteractionListener mListener;

    public EventListing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment EventListing.
     */
    // TODO: Rename and change types and number of parameters
    public static EventListing newInstance() {
        EventListing fragment = new EventListing();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_event_listing, container, false);


        AppData.EventListActivity = getActivity();

        AppData.recyclerView= (RecyclerView) view.findViewById(R.id.rv);
        AppData.NoEventText = (TextView) view.findViewById(R.id.tv_noevent);
        AppData.recyclerView.setHasFixedSize(true);
        AppData.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FetchingEventList();

        return view;
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


    public void FetchingEventList(){

        SharedPreferences sharedPreferences = AppData.EventListActivity.getSharedPreferences("AutoLogin", AppData.EventListActivity.MODE_PRIVATE);


        String url = AppData.DomainUrlForProfile+"eventlisting_control?userid="+sharedPreferences.getString("UserId","")+"&latitude=22.5830327&longitude=88.49365";

        Log.v("FetchingEventList_URL:::",url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Log.v("FetchingEventList_Response:::",response.toString());

                try {


                    String status = response.getString("status");
                    String message = response.getString("message");


                    if (status.equals("success")){

                        if (message.equals("data found")){

                            EventListArray = new ArrayList<EventList_SetterGetter>();

                            int count = response.getInt("count");

                            JSONArray Details = response.getJSONArray("Details");

                            if (Details.length() > 0){

                                AppData.recyclerView.setVisibility(View.VISIBLE);
                                AppData.NoEventText.setVisibility(View.GONE);

                                for (int i = 0; i < Details.length(); i++){

                                    JSONObject EventObject = Details.getJSONObject(i);

                                    String event_id = EventObject.getString("event_id");
                                    String event_name = EventObject.getString("event_name");
                                    String event_date = EventObject.getString("event_date");
                                    String event_image = EventObject.getString("event_image");
                                    String venue_name = EventObject.getString("venue_name");
                                    String venue_rating = EventObject.getString("venue_rating");
                                    String distance = EventObject.getString("distance");
                                    String venue_address = EventObject.getString("venue_address");


                                    EventList_SetterGetter eventList_setterGetter = new EventList_SetterGetter(event_id,event_name,event_date,event_image,venue_name,venue_rating,distance,venue_address);
                                    EventListArray.add(eventList_setterGetter);

                                }


                                adapter=new EventsAdapter(AppData.EventListActivity,EventListArray);
                                AppData.recyclerView.setAdapter(adapter);

                            }else {

                                AppData.recyclerView.setVisibility(View.GONE);
                                AppData.NoEventText.setVisibility(View.VISIBLE);

                            }


                        }else {

                            AppData.recyclerView.setVisibility(View.GONE);
                            AppData.NoEventText.setVisibility(View.VISIBLE);

                        }

                    }else {

                        AppData.recyclerView.setVisibility(View.GONE);
                        AppData.NoEventText.setVisibility(View.VISIBLE);

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

    public void SearchingEventList(String charecter){



        String url = AppData.DomainUrlForProfile+"search?search="+charecter;

        Log.v("SearchingEventList_URL:::",url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Log.v("FetchingEventList_Response:::",response.toString());

                try {


                    String status = response.getString("status");
                    String message = response.getString("message");


                    if (status.equals("success")){

                        if (message.equals("data found")){

                            EventListArray = new ArrayList<EventList_SetterGetter>();

                            int count = response.getInt("count");

                            JSONArray Details = response.getJSONArray("Details");

                            if (Details.length() > 0){

                                AppData.recyclerView.setVisibility(View.VISIBLE);
                                AppData.NoEventText.setVisibility(View.GONE);

                                for (int i = 0; i < Details.length(); i++){

                                    JSONObject EventObject = Details.getJSONObject(i);

                                    String event_id = EventObject.getString("event_id");
                                    String event_name = EventObject.getString("event_name");
                                    String event_date = EventObject.getString("event_date");
                                    String event_image = EventObject.getString("event_image");
                                    String venue_name = EventObject.getString("venue_name");
                                    String venue_rating = EventObject.getString("venue_rating");
                                    String distance = EventObject.getString("distance");
                                    String venue_address = EventObject.getString("venue_address");


                                    EventList_SetterGetter eventList_setterGetter = new EventList_SetterGetter(event_id,event_name,event_date,event_image,venue_name,venue_rating,distance,venue_address);
                                    EventListArray.add(eventList_setterGetter);

                                }


                                adapter=new EventsAdapter(AppData.EventListActivity,EventListArray);
                                AppData.recyclerView.setAdapter(adapter);

                            }else {

                                AppData.recyclerView.setVisibility(View.GONE);
                                AppData.NoEventText.setVisibility(View.VISIBLE);

                            }


                        }else {

                            AppData.recyclerView.setVisibility(View.GONE);
                            AppData.NoEventText.setVisibility(View.VISIBLE);

                        }

                    }else {

                        AppData.recyclerView.setVisibility(View.GONE);
                        AppData.NoEventText.setVisibility(View.VISIBLE);

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


    public void Vol_FilterEvents(String Lat,String Long ,String distance){

        SharedPreferences sharedPreferences = AppData.EventListActivity.getSharedPreferences("AutoLogin", AppData.EventListActivity.MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"search_control?userid="+sharedPreferences.getString("UserId","")+"&latitude="+Lat+"&longitude="+Long+"&distance="+distance;



        Log.v("FetchingEventList_Filter_URL:::",url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Log.v("FetchingEventList_Response:::",response.toString());

                try {


                    String status = response.getString("status");
                    String message = response.getString("message");


                    if (status.equals("success")){

                        if (message.equals("data found")){

                            EventListArray = new ArrayList<EventList_SetterGetter>();

                            int count = response.getInt("count");

                            JSONArray Details = response.getJSONArray("Details");

                            if (Details.length() > 0){

                                AppData.recyclerView.setVisibility(View.VISIBLE);
                                AppData.NoEventText.setVisibility(View.GONE);

                                for (int i = 0; i < Details.length(); i++){

                                    JSONObject EventObject = Details.getJSONObject(i);

                                    String event_id = EventObject.getString("event_id");
                                    String event_name = EventObject.getString("event_name");
                                    String event_date = EventObject.getString("event_date");
                                    String event_image = EventObject.getString("event_image");
                                    String venue_name = EventObject.getString("venue_name");
                                    String venue_rating = EventObject.getString("venue_rating");
                                    String distance = EventObject.getString("distance");
                                    String venue_address = EventObject.getString("venue_address");


                                    EventList_SetterGetter eventList_setterGetter = new EventList_SetterGetter(event_id,event_name,event_date,event_image,venue_name,venue_rating,distance,venue_address);
                                    EventListArray.add(eventList_setterGetter);

                                }


                                adapter=new EventsAdapter(AppData.EventListActivity,EventListArray);
                                AppData.recyclerView.setAdapter(adapter);

                            }else {

                                AppData.recyclerView.setVisibility(View.GONE);
                                AppData.NoEventText.setVisibility(View.VISIBLE);

                            }


                        }else {

                            AppData.recyclerView.setVisibility(View.GONE);
                            AppData.NoEventText.setVisibility(View.VISIBLE);

                        }

                    }else {

                        AppData.recyclerView.setVisibility(View.GONE);
                        AppData.NoEventText.setVisibility(View.VISIBLE);

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
