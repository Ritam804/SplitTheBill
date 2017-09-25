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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonArray;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.MessageDetails;
import com.ritam.splitthebill.splitthebill.adapter.MessageAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.push.MyFirebaseMessagingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import setergeter.ChatList_SetterGetter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Message.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Message#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Message extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView MessageList;
    MessageAdapter adapter;
    TextView NoChatText;

    ProgressBar MessageProgress;

    ArrayList<ChatList_SetterGetter> CHatListArray;

    private OnFragmentInteractionListener mListener;

    MyReceiver myReceiver;

    String difference = null;

    String lastmessage = null;

    public Message() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment Message.
     */
    // TODO: Rename and change types and number of parameters
    public static Message newInstance() {
        Message fragment = new Message();
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
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppData.FromMessagePage = true;

        NoChatText = (TextView) view.findViewById(R.id.tv_nochat);

        MessageProgress = (ProgressBar) view.findViewById(R.id.progressBar);

        MessageList = (RecyclerView) view.findViewById(R.id.rv_messages);
        MessageList.setHasFixedSize(true);
        MessageList.setLayoutManager(new LinearLayoutManager(getActivity()));

        MessageProgress.setVisibility(View.VISIBLE);

        QBSettings.getInstance().init(getActivity(), AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
        QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(1000);

        QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                new QBEntityCallback<ArrayList<QBChatDialog>>() {
                    @Override
                    public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {

                        MessageProgress.setVisibility(View.VISIBLE);


                        int totalEntries = params.getInt("total_entries");


                        Log.v("QBChatDialog::", result.toString());

                        Log.v("QBChatDialog_Bundle::", params.toString());

                        if (result.size() > 0) {

                            CHatListArray = new ArrayList<ChatList_SetterGetter>();

                            for (int i = 0; i < result.size(); i++) {


                                String event_id = "";
                                String group_id = result.get(i).getDialogId();
                                String event_name = result.get(i).getName();
                                String event_image = result.get(i).getPhoto();
                                String venue_name = result.get(i).getDialogId();
                                String address = String.valueOf(result.get(i).getOccupants().size());
                                List<Integer> occupants = result.get(i).getOccupants();
                                int unreadmessagecount = result.get(i).getUnreadMessageCount();


                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeZone(TimeZone.getDefault());
                                calendar.getTimeInMillis();

                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm a");
                                sdf.setTimeZone(TimeZone.getDefault());
                                Date resultdate = new Date(result.get(i).getLastMessageDateSent() * 1000);
                                Date resultdate1 = new Date(calendar.getTimeInMillis());

                                Log.v("Message_LastMessage_SentTime::::",""+result.get(i).getLastMessage());

                                if (result.get(i).getLastMessageDateSent() == 0) {

                                    difference = "null";
                                    lastmessage = "null";

                                } else {


                                    long diff = resultdate1.getTime() - resultdate.getTime();

                                    lastmessage = result.get(i).getLastMessage();

                                    if (diff > 0) {


                                        long sec = ((calendar.getTimeInMillis() - result.get(i).getLastMessageDateSent() * 1000)) / 1000;

                                        if (sec / 60 > 0) {

                                            long min = sec / 60;

                                            if (min / 60 > 0) {

                                                long hour = min / 60;

                                                if (hour / 24 > 0) {

                                                    if (hour / 24 > 1) {


                                                        long day = hour / 24;

                                                        if (day / 365 > 0) {

                                                            if (day / 365 > 1) {

                                                                difference = String.valueOf(day / 365) + "Years ago";

                                                            } else {

                                                                difference = String.valueOf(day / 365) + "Year ago";

                                                            }

                                                        } else if (day / 30 > 0) {

                                                            if (day / 30 > 1) {

                                                                difference = String.valueOf(day / 30) + "Months ago";

                                                            } else {

                                                                difference = String.valueOf(day / 30) + "Month ago";

                                                            }

                                                        } else if (day / 7 > 0) {

                                                            if (day / 7 > 1) {

                                                                difference = String.valueOf(day / 7) + "Weeks ago";

                                                            } else {

                                                                difference = String.valueOf(day / 7) + "Week ago";

                                                            }

                                                        } else {

                                                            difference = String.valueOf(hour / 24) + "Days ago";

                                                        }


                                                    } else {

                                                        difference = String.valueOf(hour / 24) + "Day ago";

                                                    }


                                                } else {

                                                    difference = String.valueOf(hour) + "Hour ago";
                                                }


                                            } else {

                                                if (min > 1) {

                                                    difference = String.valueOf(min) + "Minutes ago";

                                                } else {

                                                    difference = String.valueOf(min) + "Minute ago";

                                                }


                                            }


                                        } else {

                                            if (sec > 1) {

                                                difference = String.valueOf(sec) + "Secends ago";

                                            } else {

                                                difference = String.valueOf(sec) + "Secend ago";

                                            }


                                        }

                                    }


                                }





                                ChatList_SetterGetter chatList_setterGetter = new ChatList_SetterGetter(event_id, group_id, event_name, event_image, venue_name, address, occupants, lastmessage, unreadmessagecount, difference);
                                CHatListArray.add(chatList_setterGetter);


                            }

                            NoChatText.setVisibility(View.GONE);
                            MessageList.setVisibility(View.VISIBLE);
                            adapter = new MessageAdapter(getActivity(), CHatListArray);
                            MessageList.setAdapter(adapter);

                            MessageProgress.setVisibility(View.GONE);

                        } else {

                            MessageProgress.setVisibility(View.GONE);
                            NoChatText.setVisibility(View.VISIBLE);
                            MessageList.setVisibility(View.GONE);

                        }


                    }

                    @Override
                    public void onError(QBResponseException responseException) {

                        MessageProgress.setVisibility(View.GONE);
                        NoChatText.setVisibility(View.VISIBLE);
                        MessageList.setVisibility(View.GONE);

                    }
                });


        //adapter=new EventsAdapter();

        //Vol_ChatList();


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


//    public void Vol_ChatList(){
//
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AutoLogin", getActivity().MODE_PRIVATE);
//
//        String url = AppData.DomainUrlForProfile+"chatlist_control?logged_id="+sharedPreferences.getString("UserId", "");
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//
//                    String status = response.getString("status");
//
//                    String message = response.getString("message");
//
//                    if (status.equals("success")){
//
//                        int count = response.getInt("count");
//
//                        JSONArray Details = response.getJSONArray("Details");
//
//                        if (Details.length() > 0){
//
//                            CHatListArray = new ArrayList<ChatList_SetterGetter>();
//
//                            for (int i = 0;i < Details.length();i++){
//
//                                JSONObject ChatDetails = Details.getJSONObject(i);
//
//                                String event_id = ChatDetails.getString("event_id");
//                                String group_id = ChatDetails.getString("group_id");
//                                String event_name = ChatDetails.getString("event_name");
//                                String event_image = ChatDetails.getString("event_image");
//                                String venue_name = ChatDetails.getString("venue_name");
//                                String address = ChatDetails.getString("address");
//
//                                ChatList_SetterGetter chatList_setterGetter = new ChatList_SetterGetter(event_id,group_id,event_name,event_image,venue_name,address);
//                                CHatListArray.add(chatList_setterGetter);
//
//                            }
//
//                            NoChatText.setVisibility(View.GONE);
//                            MessageList.setVisibility(View.VISIBLE);
//                            adapter=new MessageAdapter(getActivity(),CHatListArray);
//                            MessageList.setAdapter(adapter);
//
//                        }else {
//
//                            NoChatText.setVisibility(View.VISIBLE);
//                            MessageList.setVisibility(View.GONE);
//
//                        }
//
//                    }else {
//
//
//                        NoChatText.setVisibility(View.VISIBLE);
//                        MessageList.setVisibility(View.GONE);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                    NoChatText.setVisibility(View.VISIBLE);
//                    MessageList.setVisibility(View.GONE);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                NoChatText.setVisibility(View.VISIBLE);
//                MessageList.setVisibility(View.GONE);
//
//            }
//        });
//        AppData.getInstance().addToRequestQueue(jsonObjectRequest);
//
//    }


    @Override
    public void onStart() {
        super.onStart();

        Log.v("Message_Page", "OnStart");

        AppData.setIsMessageDetails("NO");
        AppData.setIsMessage("YES");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.v("Message_Page", "OnResume");

        AppData.setIsMessageDetails("NO");
        AppData.setIsMessage("YES");

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyFirebaseMessagingService.Message_Action);
        getActivity().registerReceiver(myReceiver, intentFilter);

        if (AppData.GroupMessage) {

//            TextView Count = (TextView) getActivity().findViewById(R.id.text_counter_message);
//            Count.setVisibility(View.GONE);


            MessageProgress.setVisibility(View.VISIBLE);

            QBSettings.getInstance().init(getActivity(), AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
            QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

            QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
            requestBuilder.setLimit(1000);

            QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                    new QBEntityCallback<ArrayList<QBChatDialog>>() {
                        @Override
                        public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {

                            MessageProgress.setVisibility(View.VISIBLE);


                            int totalEntries = params.getInt("total_entries");


                            Log.v("QBChatDialog::", result.toString());

                            Log.v("QBChatDialog_Bundle::", params.toString());

                            if (result.size() > 0) {

                                CHatListArray = new ArrayList<ChatList_SetterGetter>();

                                for (int i = 0; i < result.size(); i++) {


                                    String event_id = "";
                                    String group_id = result.get(i).getDialogId();
                                    String event_name = result.get(i).getName();
                                    String event_image = result.get(i).getPhoto();
                                    String venue_name = result.get(i).getDialogId();
                                    String address = String.valueOf(result.get(i).getOccupants().size());
                                    List<Integer> occupants = result.get(i).getOccupants();
                                    int unreadmessagecount = result.get(i).getUnreadMessageCount();

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTimeZone(TimeZone.getDefault());
                                    calendar.getTimeInMillis();

                                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm a");
                                    sdf.setTimeZone(TimeZone.getDefault());
                                    Date resultdate = new Date(result.get(i).getLastMessageDateSent() * 1000);
                                    Date resultdate1 = new Date(calendar.getTimeInMillis());

                                    Log.v("Message_LastMessage_SentTime::::",""+result.get(i).getLastMessageDateSent());

                                    if (result.get(i).getLastMessageDateSent() == 0) {

                                        difference = "null";
                                        lastmessage = "null";

                                    } else {

                                        long diff = resultdate1.getTime() - resultdate.getTime();
                                        lastmessage = result.get(i).getLastMessage();

                                        if (diff > 0) {


                                            long sec = ((calendar.getTimeInMillis() - result.get(i).getLastMessageDateSent() * 1000)) / 1000;

                                            if (sec / 60 > 0) {

                                                long min = sec / 60;

                                                if (min / 60 > 0) {

                                                    long hour = min / 60;

                                                    if (hour / 24 > 0) {

                                                        if (hour / 24 > 1) {


                                                            long day = hour / 24;

                                                            if (day / 365 > 0) {

                                                                if (day / 365 > 1) {

                                                                    difference = String.valueOf(day / 365) + "Years ago";

                                                                } else {

                                                                    difference = String.valueOf(day / 365) + "Year ago";

                                                                }

                                                            } else if (day / 30 > 0) {

                                                                if (day / 30 > 1) {

                                                                    difference = String.valueOf(day / 30) + "Months ago";

                                                                } else {

                                                                    difference = String.valueOf(day / 30) + "Month ago";

                                                                }

                                                            } else if (day / 7 > 0) {

                                                                if (day / 7 > 1) {

                                                                    difference = String.valueOf(day / 7) + "Weeks ago";

                                                                } else {

                                                                    difference = String.valueOf(day / 7) + "Week ago";

                                                                }

                                                            } else {

                                                                difference = String.valueOf(hour / 24) + "Days ago";

                                                            }


                                                        } else {

                                                            difference = String.valueOf(hour / 24) + "Day ago";

                                                        }


                                                    } else {

                                                        difference = String.valueOf(hour) + "Hour ago";
                                                    }


                                                } else {

                                                    if (min > 1) {

                                                        difference = String.valueOf(min) + "Minutes ago";

                                                    } else {

                                                        difference = String.valueOf(min) + "Minute ago";

                                                    }


                                                }


                                            } else {

                                                if (sec > 1) {

                                                    difference = String.valueOf(sec) + "Secends ago";

                                                } else {

                                                    difference = String.valueOf(sec) + "Secend ago";

                                                }


                                            }

                                        }

                                    }




                                    ChatList_SetterGetter chatList_setterGetter = new ChatList_SetterGetter(event_id, group_id, event_name, event_image, venue_name, address, occupants, lastmessage, unreadmessagecount, difference);
                                    CHatListArray.add(chatList_setterGetter);


                                }

                                NoChatText.setVisibility(View.GONE);
                                MessageList.setVisibility(View.VISIBLE);
                                adapter = new MessageAdapter(getActivity(), CHatListArray);
                                MessageList.setAdapter(adapter);

                                MessageProgress.setVisibility(View.GONE);

                            } else {

                                MessageProgress.setVisibility(View.GONE);
                                NoChatText.setVisibility(View.VISIBLE);
                                MessageList.setVisibility(View.GONE);

                            }


                        }

                        @Override
                        public void onError(QBResponseException responseException) {

                            MessageProgress.setVisibility(View.GONE);
                            NoChatText.setVisibility(View.VISIBLE);
                            MessageList.setVisibility(View.GONE);

                        }
                    });

            AppData.GroupMessage = false;

        }

    }

    @Override
    public void onStop() {
        super.onStop();

        AppData.setIsMessage("NO");

        try {
            getActivity().unregisterReceiver(myReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        AppData.setIsMessage("NO");
    }

    @Override
    public void onPause() {
        super.onPause();


        AppData.setIsMessage("NO");
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.v("MyReceiver", "Calling");

            MessageProgress.setVisibility(View.VISIBLE);

            QBSettings.getInstance().init(getActivity(), AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
            QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

            QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
            requestBuilder.setLimit(1000);

            QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                    new QBEntityCallback<ArrayList<QBChatDialog>>() {
                        @Override
                        public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {

                            MessageProgress.setVisibility(View.VISIBLE);


                            int totalEntries = params.getInt("total_entries");


                            Log.v("QBChatDialog::", result.toString());

                            Log.v("QBChatDialog_Bundle::", params.toString());

                            if (result.size() > 0) {

                                CHatListArray = new ArrayList<ChatList_SetterGetter>();

                                for (int i = 0; i < result.size(); i++) {


                                    String event_id = "";
                                    String group_id = result.get(i).getDialogId();
                                    String event_name = result.get(i).getName();
                                    String event_image = result.get(i).getPhoto();
                                    String venue_name = result.get(i).getDialogId();
                                    String address = String.valueOf(result.get(i).getOccupants().size());
                                    List<Integer> occupants = result.get(i).getOccupants();
                                    int unreadmessagecount = result.get(i).getUnreadMessageCount();

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTimeZone(TimeZone.getDefault());
                                    calendar.getTimeInMillis();

                                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm a");
                                    sdf.setTimeZone(TimeZone.getDefault());
                                    Date resultdate = new Date(result.get(i).getLastMessageDateSent() * 1000);
                                    Date resultdate1 = new Date(calendar.getTimeInMillis());


                                    Log.v("Message_LastMessage_SentTime::::",""+result.get(i).getLastMessage());

                                    if (result.get(i).getLastMessageDateSent() == 0) {

                                        difference = "null";
                                        lastmessage = "null";

                                    } else {

                                        long diff = resultdate1.getTime() - resultdate.getTime();

                                        if (diff > 0) {


                                            lastmessage = result.get(i).getLastMessage();
                                            long sec = ((calendar.getTimeInMillis() - result.get(i).getLastMessageDateSent() * 1000)) / 1000;

                                            if (sec / 60 > 0) {

                                                long min = sec / 60;

                                                if (min / 60 > 0) {

                                                    long hour = min / 60;

                                                    if (hour / 24 > 0) {

                                                        if (hour / 24 > 1) {


                                                            long day = hour / 24;

                                                            if (day / 365 > 0) {

                                                                if (day / 365 > 1) {

                                                                    difference = String.valueOf(day / 365) + "Years ago";

                                                                } else {

                                                                    difference = String.valueOf(day / 365) + "Year ago";

                                                                }

                                                            } else if (day / 30 > 0) {

                                                                if (day / 30 > 1) {

                                                                    difference = String.valueOf(day / 30) + "Months ago";

                                                                } else {

                                                                    difference = String.valueOf(day / 30) + "Month ago";

                                                                }

                                                            } else if (day / 7 > 0) {

                                                                if (day / 7 > 1) {

                                                                    difference = String.valueOf(day / 7) + "Weeks ago";

                                                                } else {

                                                                    difference = String.valueOf(day / 7) + "Week ago";

                                                                }

                                                            } else {

                                                                difference = String.valueOf(hour / 24) + "Days ago";

                                                            }


                                                        } else {

                                                            difference = String.valueOf(hour / 24) + "Day ago";

                                                        }


                                                    } else {

                                                        difference = String.valueOf(hour) + "Hour ago";
                                                    }


                                                } else {

                                                    if (min > 1) {

                                                        difference = String.valueOf(min) + "Minutes ago";

                                                    } else {

                                                        difference = String.valueOf(min) + "Minute ago";

                                                    }


                                                }


                                            } else {

                                                if (sec > 1) {

                                                    difference = String.valueOf(sec) + "Secends ago";

                                                } else {

                                                    difference = String.valueOf(sec) + "Secend ago";

                                                }


                                            }

                                        }

                                    }


                                    ChatList_SetterGetter chatList_setterGetter = new ChatList_SetterGetter(event_id, group_id, event_name, event_image, venue_name, address, occupants, lastmessage, unreadmessagecount, difference);
                                    CHatListArray.add(chatList_setterGetter);


                                }

                                NoChatText.setVisibility(View.GONE);
                                MessageList.setVisibility(View.VISIBLE);
                                adapter = new MessageAdapter(getActivity(), CHatListArray);
                                MessageList.setAdapter(adapter);

                                MessageProgress.setVisibility(View.GONE);

                            } else {

                                MessageProgress.setVisibility(View.GONE);
                                NoChatText.setVisibility(View.VISIBLE);
                                MessageList.setVisibility(View.GONE);

                            }


                        }

                        @Override
                        public void onError(QBResponseException responseException) {

                            MessageProgress.setVisibility(View.GONE);
                            NoChatText.setVisibility(View.VISIBLE);
                            MessageList.setVisibility(View.GONE);

                        }
                    });

        }
    }

}
