package com.ritam.splitthebill.splitthebill.fragments;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonArray;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.BookingAdapter_Previous;
import com.ritam.splitthebill.splitthebill.adapter.BookingAdapter_Upcoming;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import setergeter.Booking_SetterGetter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Booking.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Booking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Booking extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    BookingAdapter_Upcoming adapter;
    BookingAdapter_Previous adapter_previous;

    RelativeLayout Button_Upcoming,Button_Previous,Button_Pending;
    //TextView Line_Upcoming,Line_Previous,Line_Pending;

    ArrayList<Booking_SetterGetter> BookingHistoryValues;

    TextView NoBookingText;

    private OnFragmentInteractionListener mListener;

    boolean FromPrevious = false;

    public Booking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment Booking.
     */
    // TODO: Rename and change types and number of parameters
    public static Booking newInstance() {
        Booking fragment = new Booking();
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

        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView= (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        Button_Upcoming = (RelativeLayout) view.findViewById(R.id.rl_upcoming);
        Button_Previous = (RelativeLayout) view.findViewById(R.id.rl_previous);
        Button_Pending = (RelativeLayout) view.findViewById(R.id.rl_pending);


//        Line_Upcoming = (TextView) view.findViewById(R.id.tv_upcoming);
//        Line_Previous = (TextView) view.findViewById(R.id.tv_previous);
//        Line_Pending = (TextView) view.findViewById(R.id.tv_pending);

        NoBookingText = (TextView) view.findViewById(R.id.tv_nobooking);

        //adapter=new EventsAdapter();

        Button_Upcoming.setSelected(false);
        Button_Previous.setSelected(false);
        Button_Pending.setSelected(true);

        BookingHistory("3","1");


        Button_Upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button_Upcoming.setSelected(true);
                Button_Previous.setSelected(false);
                Button_Pending.setSelected(false);

                FromPrevious = false;

//                Line_Upcoming.setVisibility(View.VISIBLE);
//                Line_Previous.setVisibility(View.GONE);
//                Line_Pending.setVisibility(View.GONE);

                adapter = null;

                BookingHistory("1","1");

            }
        });


        Button_Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button_Upcoming.setSelected(false);
                Button_Previous.setSelected(true);
                Button_Pending.setSelected(false);

                FromPrevious = true;

//                Line_Upcoming.setVisibility(View.GONE);
//                Line_Pending.setVisibility(View.GONE);
//                Line_Previous.setVisibility(View.VISIBLE);

                adapter = null;

                BookingHistory("2","1");

            }
        });

        Button_Pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button_Upcoming.setSelected(false);
                Button_Previous.setSelected(false);
                Button_Pending.setSelected(true);

                FromPrevious = false;

//                Line_Upcoming.setVisibility(View.GONE);
//                Line_Previous.setVisibility(View.GONE);
//                Line_Pending.setVisibility(View.VISIBLE);

                adapter = null;

                BookingHistory("3","1");

            }
        });

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


    public void BookingHistory(final String status, String Page){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AutoLogin", getActivity().MODE_PRIVATE);


        String url = AppData.DomainUrlForProfile+"bookinghistory_control?userid="+sharedPreferences.getString("UserId","")+"&status="+status+"&page="+Page+"&limit=10";

        Log.v("BookingHistory::",url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Log.v("BookingHistory Response::",response.toString());

                try {


                    String status1 = response.getString("status");
                    String message = response.getString("message");

                    if (status1.equals("success")){

                        if (message.equals("data found")){

                            BookingHistoryValues = new ArrayList<Booking_SetterGetter>();
                            BookingHistoryValues.clear();


                            JSONArray Details = response.getJSONArray("Details");

                            if (Details.length() > 0){

                                recyclerView.setVisibility(View.VISIBLE);
                                NoBookingText.setVisibility(View.GONE);

                                for (int i = 0;i<Details.length();i++){

                                    JSONObject BookingObject = Details.getJSONObject(i);

                                    String event_id = BookingObject.getString("event_id");
                                    String eventdate = BookingObject.getString("eventdate");
                                    String eventname = BookingObject.getString("eventname");
                                    String venue_name = BookingObject.getString("venue_name");
                                    String venue_address = BookingObject.getString("venue_address");
                                    String reference_id = BookingObject.getString("reference_id");
                                    String table_name = BookingObject.getString("table_name");
                                    String event_month = BookingObject.getString("event_month");
                                    String event_date = BookingObject.getString("event_date");
                                    String event_time = BookingObject.getString("event_time");
                                    String table_id = BookingObject.getString("table_id");
                                    String host_id = BookingObject.getString("host_id");

                                    String host_name = BookingObject.getString("host_name");
                                    String total_seat = BookingObject.getString("total_seat");
                                    String remaining_seat = BookingObject.getString("remaining_seat");
                                    String table_cost = BookingObject.getString("remaining_amount");
                                    String group_id = BookingObject.getString("group_id");

                                    Booking_SetterGetter booking_setterGetter = new Booking_SetterGetter(event_id,eventname,venue_name,venue_address,reference_id,table_name,event_month,event_date,event_time,table_id,host_id,host_name,total_seat,remaining_seat,table_cost,group_id);
                                    BookingHistoryValues.add(booking_setterGetter);



                                }


                                if (adapter == null){

                                    adapter=new BookingAdapter_Upcoming(BookingHistoryValues,getActivity(),Booking.this,status,FromPrevious,"Booking");
                                    recyclerView.setAdapter(adapter);

                                }else {

                                    adapter.LoadMore(BookingHistoryValues);

                                }

                            }else {

                                recyclerView.setVisibility(View.GONE);
                                NoBookingText.setVisibility(View.VISIBLE);

                            }



                        }else {

                            recyclerView.setVisibility(View.GONE);
                            NoBookingText.setVisibility(View.VISIBLE);

                        }


                    }else {

                        recyclerView.setVisibility(View.GONE);
                        NoBookingText.setVisibility(View.VISIBLE);


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

    @Override
    public void onResume() {
        super.onResume();
        Log.v("onResume","Booking");

    }

    @Override
    public void onPause() {
        super.onPause();

        Log.v("onPause","Booking");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("onStart","Booking");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("onStop","Booking");
    }
}
