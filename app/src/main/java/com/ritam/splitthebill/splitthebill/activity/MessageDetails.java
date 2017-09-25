package com.ritam.splitthebill.splitthebill.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.request.QBMessageGetBuilder;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.messages.QBPushNotifications;
import com.quickblox.messages.model.QBEnvironment;
import com.quickblox.messages.model.QBEvent;
import com.quickblox.messages.model.QBNotificationType;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.MessageAdapter;
import com.ritam.splitthebill.splitthebill.adapter.MessageChat_Adapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.push.MyFirebaseMessagingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import setergeter.ChatList_SetterGetter;
import setergeter.FetchingChat_SetterGetter;

public class MessageDetails extends AppCompatActivity {

    RecyclerView MessageChatList;
    LinearLayout messageback;

    RelativeLayout Button_GoiingPeople;

    ArrayList<FetchingChat_SetterGetter> FetchingChatDetails;

    TextView Button_SendMessage, GroupName;

    EditText Text_SendMessage;

    ProgressBar MessageProgress;
    MessageChat_Adapter messageChat_adapter;

    TextView MemberCount;

    MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        MessageChatList = (RecyclerView) findViewById(R.id.message_chatlist);

        Button_GoiingPeople = (RelativeLayout) findViewById(R.id.peoplegoing);

        MemberCount = (TextView) findViewById(R.id.peoplegoingcount);

        GroupName = (TextView) findViewById(R.id.tv_groupname);

        MessageChatList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        MessageChatList.setLayoutManager(linearLayoutManager);

        AppData.QBGroupId = getIntent().getExtras().getString("GroupId");

//        MessageChat_Adapter messageChat_adapter = new MessageChat_Adapter(MessageDetails.this);
//
//        MessageChatList.setAdapter(messageChat_adapter);

        messageback = (LinearLayout) findViewById(R.id.messageback);

        Button_SendMessage = (TextView) findViewById(R.id.sendmessage_id);

        Text_SendMessage = (EditText) findViewById(R.id.add_message);

        MessageProgress = (ProgressBar) findViewById(R.id.progressBar);

        messageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppData.GroupMessage = true;
                AppData.QBGroupId = "";
                finish();
            }
        });

        MemberCount.setText(getIntent().getExtras().getString("MemberCount"));

        GroupName.setText(getIntent().getExtras().getString("GroupName"));

        Button_GoiingPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MessageDetails.this, GroupDetails.class);
                intent.putExtra("GroupId",getIntent().getExtras().getString("GroupId"));
                startActivity(intent);

            }
        });


        ////////////////..............Send Message......................//////////////////////////

        Button_SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Text_SendMessage.getText().toString().trim().length() > 0) {

                    MessageProgress.setVisibility(View.VISIBLE);

                    View view1 = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }


                    SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);


                    QBChatMessage msg = new QBChatMessage();
                    msg.setBody(Text_SendMessage.getText().toString());
                    msg.setId(sharedPreferences.getString("QB_UserId", ""));
                    msg.setProperty("senderName", sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", ""));

                    //msg.setRecipientId(223);
                    msg.setDialogId(getIntent().getExtras().getString("GroupId")); //Set the dialog Id or recipient Id

                    //QBAttachment attachment = new QBAttachment("photo");
                    //attachment.setId("3451");
                    //msg.addAttachment(attachment);

                    //msg.setProperty("param1", "value1");
                    //msg.setProperty("param2", "value2");


                    boolean sendToDialog = false; //set true for send this message to the chat or false for just create it.

                    QBRestChatService.createMessage(msg, sendToDialog).performAsync(new QBEntityCallback<QBChatMessage>() {
                        @Override
                        public void onSuccess(QBChatMessage message, Bundle bundle) {

                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

                            String sender_id = sharedPreferences.getString("QB_UserId", "");
                            String sender_name = sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", "");
                            String to_id = message.getId();
                            String message1 = Text_SendMessage.getText().toString();
                            String datetime = "Just Now";

                            FetchingChat_SetterGetter fetchingChat_setterGetter = new FetchingChat_SetterGetter(sender_id, sender_name, to_id, message1, datetime);

                            if (FetchingChatDetails != null) {

                                //FetchingChatDetails.add(fetchingChat_setterGetter);

                            } else {

                                FetchingChatDetails = new ArrayList<FetchingChat_SetterGetter>();
                                FetchingChatDetails.add(fetchingChat_setterGetter);

                            }

                            if (messageChat_adapter == null) {

                                messageChat_adapter = new MessageChat_Adapter(MessageDetails.this, FetchingChatDetails);

                                MessageChatList.setAdapter(messageChat_adapter);


                            } else {

                                messageChat_adapter.MessageUpdate(fetchingChat_setterGetter);
                                MessageChatList.smoothScrollToPosition(messageChat_adapter.FetchingChatDetailsArray.size());

                            }


                            StringifyArrayList<Integer> userIds = new StringifyArrayList<Integer>();

                            for (int i = 0; i < AppData.OccupantIds.size(); i++) {

                                if (!(String.valueOf(AppData.OccupantIds.get(i)).equals(sharedPreferences.getString("QB_UserId", "")))) {

                                    userIds.add(AppData.OccupantIds.get(i));

                                }


                            }


                            QBEvent event = new QBEvent();
                            event.setUserIds(userIds);
                            event.setEnvironment(QBEnvironment.PRODUCTION);
                            event.setNotificationType(QBNotificationType.PUSH);
                            //event.setMessage(Text_SendMessage.getText().toString());


                            //HashMap<String, String> data = new HashMap<String, String>();

                            JSONObject data = new JSONObject();

                            try {
                                data.put("message", sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", "") + ":"+Text_SendMessage.getText().toString());
                                data.put("header", sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", "") + " " + "have sent you a message !");
                                data.put("groupID", getIntent().getExtras().getString("GroupId"));
                                data.put("senderID", sharedPreferences.getString("QB_UserId", ""));
                                data.put("senderName", sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", ""));
                                data.put("groupName", getIntent().getExtras().getString("GroupName"));
                                data.put("text", Text_SendMessage.getText().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            event.setMessage(data.toString());

                            event.setOccuredCount(1);

                            QBPushNotifications.createEvent(event).performAsync(new QBEntityCallback<QBEvent>() {
                                @Override
                                public void onSuccess(QBEvent qbEvent, Bundle args) {
                                    // sent

                                    Log.v("QBPushNotifications", "Success");

                                    Text_SendMessage.setText("");

                                    Toast.makeText(MessageDetails.this, "Successfully sent!", Toast.LENGTH_SHORT).show();

                                    MessageProgress.setVisibility(View.GONE);

                                }

                                @Override
                                public void onError(QBResponseException errors) {

                                    Log.v("QBPushNotifications", "Error");

                                    Log.v("Error", errors.getMessage().toString());

                                    MessageProgress.setVisibility(View.GONE);

                                }
                            });


                        }

                        @Override
                        public void onError(QBResponseException e) {

                            MessageProgress.setVisibility(View.GONE);

                        }
                    });


                } else {

                    Toast.makeText(MessageDetails.this, "Please write something to send!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        ///////////////..........Fetching message from groupid.............///////////////////////

        MessageProgress.setVisibility(View.VISIBLE);

        QBSettings.getInstance().init(getApplicationContext(), AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
        QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

        QBChatDialog chatDialog = new QBChatDialog(getIntent().getExtras().getString("GroupId"));

        QBMessageGetBuilder messageGetBuilder = new QBMessageGetBuilder();
        messageGetBuilder.setLimit(1000);
        //messageGetBuilder.setSkip(1);

        QBRestChatService.getDialogMessages(chatDialog, messageGetBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatMessage>>() {
            @Override
            public void onSuccess(ArrayList<QBChatMessage> qbChatMessages, Bundle bundle) {

                Log.v("QB_Messages:::", qbChatMessages.toString());

                //Log.v("QB_Messages:::", String.valueOf(qbChatMessages.size()));

                if (qbChatMessages.size() > 0) {

                    FetchingChatDetails = new ArrayList<FetchingChat_SetterGetter>();

                    String sender_name = null;

                    for (int i = 0; i < qbChatMessages.size(); i++) {

                        String difference = null;

                        String sender_id = String.valueOf(qbChatMessages.get(i).getSenderId());

                        try {

                            sender_name = qbChatMessages.get(i).getProperty("senderName").toString();

                        } catch (Exception e) {

                            sender_name = "No Name";

                        }


                        String to_id = qbChatMessages.get(i).getId();
                        String message = qbChatMessages.get(i).getBody();

                        Log.v("Current_TimeZone::::",""+TimeZone.getDefault());

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeZone(TimeZone.getDefault());
                        calendar.getTimeInMillis();

                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy hh:mm aa");
                        sdf.setTimeZone(TimeZone.getDefault());
                        Date resultdate = new Date(qbChatMessages.get(i).getDateSent() * 1000);
                        Date resultdate1 = new Date(calendar.getTimeInMillis());


                        long diff = resultdate1.getTime() - resultdate.getTime();

                        if (diff > 0){



                            long sec = ((calendar.getTimeInMillis() - qbChatMessages.get(i).getDateSent() * 1000)) / 1000;

                            if (sec/60 > 0){

                                long min = sec/60;

                                if (min/60 > 0){

                                    long hour = min/60;

                                    if (hour/24 > 0){

                                        if (hour/24 > 1){



                                            difference = String.valueOf(sdf.format(resultdate));

//                                            long day = hour/24;
//
//                                            if (day/365 > 0){
//
//                                                if (day/365 > 1){
//
//                                                    difference = String.valueOf(day/365)+"Years ago";
//
//                                                }else {
//
//                                                    difference = String.valueOf(day/365)+"Year ago";
//
//                                                }
//
//                                            }else if (day/30 > 0){
//
//                                                if (day/30 > 1){
//
//                                                    difference = String.valueOf(day/30)+"Months ago";
//
//                                                }else {
//
//                                                    difference = String.valueOf(day/30)+"Month ago";
//
//                                                }
//
//                                            }else if (day/7 > 0){
//
//                                                if (day/7 > 1){
//
//                                                    difference = String.valueOf(day/7)+"Weeks ago";
//
//                                                }else {
//
//                                                    difference = String.valueOf(day/7)+"Week ago";
//
//                                                }
//
//                                            }else {
//
//                                                difference = String.valueOf(hour/24)+"Days ago";
//
//                                            }


                                        }else {

                                            difference = String.valueOf(hour/24)+"Day ago";

                                        }


                                    }else {

                                        difference = String.valueOf(hour)+"Hour ago";
                                    }


                                }else {

                                    if (min > 1){

                                        difference = String.valueOf(min)+"Minutes ago";

                                    }else {

                                        difference = String.valueOf(min)+"Minute ago";

                                    }



                                }


                            }else {

                                if (sec > 1){

                                    difference = String.valueOf(sec)+"Secends ago";

                                }else {

                                    difference = String.valueOf(sec)+"Secend ago";

                                }



                            }

                        }

                        Log.v("CurrentDateAndTime:::",""+sdf.format(resultdate1));
                        Log.v("CrrentDateAndTime1:::",""+sdf.format(resultdate));
                        Log.v("CurrentDateAndTime_difference_count",""+diff);
                        Log.v("CurrentDateAndTime_difference",""+difference);
                        Log.v("CurrentDateAndTime2:::",""+qbChatMessages.get(i).getDateSent() * 1000);
                        Log.v("CrrentDateAndTime3:::",""+calendar.getTimeInMillis());

//                        DateFormat sdf = new SimpleDateFormat("dd/mm/yyyy' 'HH:mm:ss");
//                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//                        Calendar calendar = Calendar.getInstance();
//
//                        Log.v("DateSent::", String.valueOf(qbChatMessages.get(i).getDateSent()));
//
//                        calendar.setTimeInMillis(qbChatMessages.get(i).getDateSent());
//                        TimeZone tz = TimeZone.getDefault();
//                        sdf.setTimeZone(tz);


                        String datetime = String.valueOf(sdf.format(resultdate));

                        FetchingChat_SetterGetter fetchingChat_setterGetter = new FetchingChat_SetterGetter(sender_id, sender_name, to_id, message, difference);
                        FetchingChatDetails.add(fetchingChat_setterGetter);

                    }

                    messageChat_adapter = new MessageChat_Adapter(MessageDetails.this, FetchingChatDetails);

                    MessageChatList.setAdapter(messageChat_adapter);

                }

                MessageProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(QBResponseException e) {

                MessageProgress.setVisibility(View.GONE);

            }
        });


        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(1000);

        QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                new QBEntityCallback<ArrayList<QBChatDialog>>() {
                    @Override
                    public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {

                        MessageProgress.setVisibility(View.VISIBLE);


                        //int totalEntries = params.getInt("total_entries");


                        //Log.v("QBChatDialog::",result.toString());

                        //Log.v("QBChatDialog_Bundle::",params.toString());

                        if (result.size() > 0) {


                            for (int i = 0; i < result.size(); i++) {


                                if (result.get(i).getDialogId().equals(getIntent().getExtras().getString("GroupId"))) {

                                    AppData.OccupantIds = result.get(i).getOccupants();


                                }

                            }

                            MemberCount.setText(String.valueOf(AppData.OccupantIds.size()));
                            MessageProgress.setVisibility(View.GONE);

                        } else {

                            MessageProgress.setVisibility(View.GONE);
                        }


                    }

                    @Override
                    public void onError(QBResponseException responseException) {

                        MessageProgress.setVisibility(View.GONE);

                    }
                });

    }


//    public void Vol_FetchingChat(){
//
//        String url = AppData.DomainUrlForProfile+"chatfetch_control?groupid="+getIntent().getExtras().getString("GroupId");
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//
//                    String status = response.getString("status");
//                    String statusmessage = response.getString("message");
//
//                    if (status.equals("success")){
//
//                        JSONArray Details = response.getJSONArray("Details");
//
//                        if (Details.length() > 0){
//
//                            FetchingChatDetails = new ArrayList<FetchingChat_SetterGetter>();
//
//                            for (int i=0;i<Details.length();i++){
//
//                                JSONObject ChatDetails = Details.getJSONObject(i);
//
//                                String sender_id = ChatDetails.getString("sender_id");
//                                String sender_name = ChatDetails.getString("sender_name");
//                                String to_id = ChatDetails.getString("to_id");
//                                String message = ChatDetails.getString("message");
//                                String datetime = ChatDetails.getString("datetime");
//
//                                FetchingChat_SetterGetter fetchingChat_setterGetter = new FetchingChat_SetterGetter(sender_id,sender_name,to_id,message,datetime);
//                                FetchingChatDetails.add(fetchingChat_setterGetter);
//
//                            }
//
//                        }
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        AppData.getInstance().addToRequestQueue(jsonObjectRequest);
//
//    }


    @Override
    public void onStart() {
        super.onStart();

        Log.v("MessageDetails_Page","OnStart");

        AppData.setIsMessage("NO");
        AppData.setIsMessageDetails("YES");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.v("MessageDetails_Page","OnResume");

        AppData.setIsMessage("NO");
        AppData.setIsMessageDetails("YES");

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyFirebaseMessagingService.MessageDetails_Action);
        registerReceiver(myReceiver, intentFilter);

    }

    @Override
    public void onStop() {
        super.onStop();

        AppData.setIsMessageDetails("NO");

        try {
            unregisterReceiver(myReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        AppData.setIsMessageDetails("NO");
    }

    @Override
    public void onPause() {
        super.onPause();


        AppData.setIsMessageDetails("NO");
    }


    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent intent) {
            // TODO Auto-generated method stub

            Log.d("My Receiver", "-------------call");


            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

            String sender_id = intent.getExtras().getString("senderId");
            String sender_name = sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", "");
            String to_id = intent.getExtras().getString("senderId");

            String[] FormatedMessage = intent.getExtras().getString("message").split(":");

            String message1 = FormatedMessage[1];
            String datetime = "Just Now";

            FetchingChat_SetterGetter fetchingChat_setterGetter = new FetchingChat_SetterGetter(sender_id, sender_name, to_id, message1, datetime);

            if (FetchingChatDetails != null) {

                //FetchingChatDetails.add(fetchingChat_setterGetter);

            } else {

                FetchingChatDetails = new ArrayList<FetchingChat_SetterGetter>();
                FetchingChatDetails.add(fetchingChat_setterGetter);

            }

            if (messageChat_adapter == null) {

                messageChat_adapter = new MessageChat_Adapter(MessageDetails.this, FetchingChatDetails);

                MessageChatList.setAdapter(messageChat_adapter);


            } else {

                messageChat_adapter.MessageUpdate(fetchingChat_setterGetter);
                MessageChatList.smoothScrollToPosition(messageChat_adapter.FetchingChatDetailsArray.size());


            }

//            String to_id = intent.getExtras().getString("FromId");
//            String message = intent.getExtras().getString("Message");
//            String datetime = "Current Time";
//
//            FetchingChat_SetterGetter fetchingChat_setterGetter = new FetchingChat_SetterGetter(sender_id, sender_name, to_id, message, datetime);
//            FetchingChatDetails.add(fetchingChat_setterGetter);

//            try {
//
//                String username = intent.getExtras().getString("username");
//                String date = intent.getExtras().getString("date");
//                String restaurant_name = intent.getExtras().getString("restuarantname");
//                String restaurant_address = intent.getExtras().getString("restuarantaddress");
//                String price = intent.getExtras().getString("price");
//                String order_id = intent.getExtras().getString("orderid");
//                String orderStatus = intent.getExtras().getString("orderstatus");
//                String quantity = intent.getExtras().getString("quantity");
//                String order_number = intent.getExtras().getString("ordernumber");
//                String homedelivery = intent.getExtras().getString("homedelivery");
//                String deliveryaddress = intent.getExtras().getString("deliveryaddress");
//                String deliveryphone = intent.getExtras().getString("deliveryphone");
//                String confirmcode = intent.getExtras().getString("confirmcode");
//
//
//                SetterGetter_Oreders setterGetter_oreders = new SetterGetter_Oreders(username, date, restaurant_name, restaurant_address, price, order_id, orderStatus, quantity, order_number, homedelivery, deliveryaddress, deliveryphone,confirmcode);
//                //CurrentOrdersArray.add(0,setterGetter_oreders);
//                CurrentOrdersArray = new ArrayList<SetterGetter_Oreders>();
//                CurrentOrdersArray.add(setterGetter_oreders);
//
//
//                if (currentOrdersAdapter == null) {
//
//                    CurrentOrdersList.setHasFixedSize(true);
//                    CurrentOrdersList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
//
//                    CurrentOrdersList.setVisibility(View.VISIBLE);
//                    NoValue.setVisibility(View.GONE);
//
//                    currentOrdersAdapter = new CurrentOrdersAdapter(CurrentOrdersArray, getActivity(), CurrentOrders.this);
//
//                    CurrentOrdersList.setAdapter(currentOrdersAdapter);
//
//                } else {
//
//                    currentOrdersAdapter.AppendForPush(setterGetter_oreders);
//
//                }
//
//
//            } catch (Exception e) {
//                //Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
//            }


        }

    }


}
