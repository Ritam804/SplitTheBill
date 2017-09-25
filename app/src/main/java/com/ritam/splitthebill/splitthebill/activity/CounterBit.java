package com.ritam.splitthebill.splitthebill.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBGroupChatManager;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.request.QBDialogRequestBuilder;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.Dialog_AdvanceList_Bookingasahost_Adapter;
import com.ritam.splitthebill.splitthebill.adapter.Dialog_AdvanceList_Counter_Adapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CounterBit extends AppCompatActivity {

    LinearLayout Button_Back,Button_Agree,Button_Disagree,Button_Counter;

    TextView EventTitle,EventName,EventDate,EventMonthYear,EventStartTime,EventStartAmOrPm,EventEndTime,Amount,MaleCount,FemaleCount,Button_Counter_Text;

    ImageView UserImage;

    ArrayList<String> AllAmount;

    int LastValue;

    ProgressBar Loader;

    LinearLayout ButtonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_bit);

        Button_Back = (LinearLayout) findViewById(R.id.ll_back_counterbit);

        Button_Agree = (LinearLayout) findViewById(R.id.ll_agree);

        Button_Disagree = (LinearLayout) findViewById(R.id.ll_disagree);

        Button_Counter = (LinearLayout) findViewById(R.id.ll_counter);

        UserImage = (ImageView) findViewById(R.id.iv_userimg);

        EventTitle = (TextView) findViewById(R.id.tv_title);

        EventName = (TextView) findViewById(R.id.tv_eventname);

        EventDate = (TextView) findViewById(R.id.tv_event_date);

        EventMonthYear = (TextView) findViewById(R.id.tv_event_monthyear);

        EventStartTime = (TextView) findViewById(R.id.event_starttime);

        EventStartAmOrPm = (TextView) findViewById(R.id.event_startdate_am);

        EventEndTime = (TextView) findViewById(R.id.event_endtime);

        Amount = (TextView) findViewById(R.id.tv_amount);

        MaleCount = (TextView) findViewById(R.id.tv_malecount);

        FemaleCount = (TextView) findViewById(R.id.tv_femalecount);

        Loader = (ProgressBar) findViewById(R.id.progressBar8);

        ButtonImage = (LinearLayout) findViewById(R.id.ll_image);

        Button_Counter_Text = (TextView) findViewById(R.id.tv_counter);

        AllAmount = new ArrayList<String>();
        AllAmount.clear();

        SharedPreferences sharedPreferences =  getSharedPreferences("AutoLogin",MODE_PRIVATE);

        Log.v("QB_UserID","::::"+sharedPreferences.getString("QB_UserId",""));
        Log.v("QB_SenderId","::::"+getIntent().getExtras().getString("QB_SenderId"));
        Log.v("QB_GroupId","::::"+getIntent().getExtras().getString("QB_GroupId"));


        String str = getIntent().getExtras().getString("EventStartTime");
        String[] SplitEventStartDate = str.split("\\s+");



        if (getIntent().getExtras().getString("Type").equals("FirstTime")){

            Button_Agree.setVisibility(View.GONE);

            Button_Counter_Text.setText("ACCEPT");

        }else {

            Button_Agree.setVisibility(View.VISIBLE);

            Button_Counter_Text.setText("COUNTER");

        }

        if (!(getIntent().getExtras().getString("UserImage").equals("")) && !(getIntent().getExtras().getString("UserImage").isEmpty()) && getIntent().getExtras().getString("UserImage") != null){

            Picasso.with(CounterBit.this)
                    .load(getIntent().getExtras().getString("UserImage"))
                    .placeholder(R.drawable.appicon_round)
                    .error(R.drawable.appicon_round)
                    .transform(new RoundedTransformation())
                    .into(UserImage);

        }

        EventTitle.setText(getIntent().getExtras().getString("EventTitle"));
        EventName.setText(getIntent().getExtras().getString("EventName"));
        EventDate.setText(getIntent().getExtras().getString("EventDay"));
        EventMonthYear.setText(getIntent().getExtras().getString("EventMonth") + " " + getIntent().getExtras().getString("EventYear"));
        EventStartTime.setText(SplitEventStartDate[0]);
        EventStartAmOrPm.setText(SplitEventStartDate[1]);
        EventEndTime.setText(getIntent().getExtras().getString("EventEndTime"));
        Amount.setText(getIntent().getExtras().getString("Amount"));
        MaleCount.setText(getIntent().getExtras().getString("MaleCount"));
        FemaleCount.setText(getIntent().getExtras().getString("FemaleCount"));




        if (Integer.parseInt(getIntent().getExtras().getString("LastRequestedAmount")) == 0){

            LastValue = 50;

            if (50 >= Integer.parseInt(getIntent().getExtras().getString("MaximumAmount"))){

                AllAmount.add(String.valueOf(LastValue));

            }else {

                for (int i = 0; i <= ((Integer.parseInt(getIntent().getExtras().getString("MaximumAmount")) - 50) / 50) + 1; i++) {

                    if ((i == ((Integer.parseInt(getIntent().getExtras().getString("MaximumAmount")) - 50) / 50) + 1)) {

                        if (((Integer.parseInt(getIntent().getExtras().getString("MaximumAmount")) - 50) % 50) != 0){

                            AllAmount.add(String.valueOf(LastValue + ((Integer.parseInt(getIntent().getExtras().getString("MaximumAmount")) - 50) % 50)));

                        }else {

                            AllAmount.add(String.valueOf(LastValue + 50));

                        }

                    } else {

                        if (i == 0){

                            LastValue = 50;

                        }else {

                            LastValue = LastValue + 50;

                        }


                        AllAmount.add(String.valueOf(LastValue));

                    }

                }

            }

        }else {

            LastValue = Integer.parseInt(getIntent().getExtras().getString("LastRequestedAmount"));

            if (Integer.parseInt(getIntent().getExtras().getString("LastRequestedAmount")) >= Integer.parseInt(getIntent().getExtras().getString("MaximumAmount"))){

                AllAmount.add(String.valueOf(LastValue));

            }else {

                for (int i = 0; i <= ((Integer.parseInt(getIntent().getExtras().getString("MaximumAmount")) - Integer.parseInt(getIntent().getExtras().getString("LastRequestedAmount"))) / 50) + 1; i++) {

                    if ((i == ((Integer.parseInt(getIntent().getExtras().getString("MaximumAmount")) - Integer.parseInt(getIntent().getExtras().getString("LastRequestedAmount"))) / 50) + 1)) {

                        if (((Integer.parseInt(getIntent().getExtras().getString("MaximumAmount")) - Integer.parseInt(getIntent().getExtras().getString("LastRequestedAmount"))) % 50) != 0){

                            AllAmount.add(String.valueOf(LastValue + ((Integer.parseInt(getIntent().getExtras().getString("MaximumAmount")) - Integer.parseInt(getIntent().getExtras().getString("LastRequestedAmount"))) % 50)));

                        }else {

                            AllAmount.add(String.valueOf(LastValue + 50));

                        }

                    } else {

                        if (i == 0){

                            LastValue = Integer.parseInt(getIntent().getExtras().getString("LastRequestedAmount"));

                        }else {

                            LastValue = LastValue + 50;

                        }


                        AllAmount.add(String.valueOf(LastValue));

                    }

                }

            }


        }






        ButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CounterBit.this, OthersProfile.class);
                intent.putExtra("UserId",getIntent().getExtras().getString("RequisterId"));
                startActivity(intent);

            }
        });


        Button_Counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout Button_Cross;
                RecyclerView AmountList;
                TextView Dialog_HeaderText;

                View view1 = LayoutInflater.from(CounterBit.this).inflate(R.layout.dialog_bookasahost_advancelist, null);
                final Dialog dialog = new Dialog(CounterBit.this, R.style.MaterialDialogSheet);
                dialog.setContentView(view1);
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();


                Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_back_dialog_bookingasahost);

                Dialog_HeaderText = (TextView) view1.findViewById(R.id.tv_dialog_header);

                Dialog_HeaderText.setText("Select Amount");

                AmountList = (RecyclerView) view1.findViewById(R.id.rv_bookingasahost_advanceList);
                AmountList.setHasFixedSize(true);
                AmountList.setLayoutManager(new LinearLayoutManager(CounterBit.this));

                Dialog_AdvanceList_Counter_Adapter dialog_advanceList_bookingasahost_adapter = new Dialog_AdvanceList_Counter_Adapter(AllAmount,CounterBit.this,dialog,Amount,CounterBit.this,"Counter");
                AmountList.setAdapter(dialog_advanceList_bookingasahost_adapter);


                Button_Cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });

            }
        });


        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        Button_Agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                QBSettings.getInstance().init(getApplicationContext(), AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
                QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

                SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

                if (getIntent().getExtras().getString("QB_GroupId").equals("")){


                    ArrayList<Integer> occupantIdsList = new ArrayList<Integer>();
                    occupantIdsList.add(Integer.parseInt(sharedPreferences.getString("QB_UserId","")));
                    occupantIdsList.add(Integer.parseInt(getIntent().getExtras().getString("QB_SenderId")));

//                    QBChatDialog dialog = new QBChatDialog();
//                    dialog.setName("Chat with Garry and John");
//                    dialog.setPhoto("1786");
//                    dialog.setType(QBDialogType.GROUP);
//                    dialog.setOccupantsIds(occupantIdsList);

//or just use DialogUtils
//for creating PRIVATE dialog
//QBChatDialog dialog = DialogUtils.buildPrivateDialog(recipientId);

//for creating GROUP dialog
                    final QBChatDialog dialog = DialogUtils.buildDialog(getIntent().getExtras().getString("EventName"), QBDialogType.GROUP, occupantIdsList);

                    QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
                        @Override
                        public void onSuccess(QBChatDialog result, Bundle params) {

                            Log.v("QBChatDialog::",result.toString());
                            Log.v("QBChatDialog_Bundle::",params.toString());


                            Log.v("QBChatDialog_GroupId::",result.getDialogId());


                            Vol_Agree(result.getDialogId());

                        }

                        @Override
                        public void onError(QBResponseException responseException) {

                        }
                    });


                }else {

                    QBDialogRequestBuilder requestBuilder = new QBDialogRequestBuilder();
                    requestBuilder.addUsers(Integer.parseInt(getIntent().getExtras().getString("QB_SenderId"))); // add another users
// requestBuilder.removeUsers(22); // Remove yourself (user with ID 22)

                    QBChatDialog qbChatDialog = new QBChatDialog();
                    qbChatDialog.setDialogId(getIntent().getExtras().getString("QB_GroupId"));
                    qbChatDialog.setName(getIntent().getExtras().getString("EventName"));


                    QBRestChatService.updateGroupChatDialog(qbChatDialog,requestBuilder).performAsync(new QBEntityCallback<QBChatDialog>() {
                        @Override
                        public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {

                            Vol_Agree(getIntent().getExtras().getString("QB_GroupId"));

                        }

                        @Override
                        public void onError(QBResponseException e) {

                        }
                    });

//                    QBDialog dialog = ...; // dialog should contain dialogId
//                    dialog.setName("Team room");
//                    QBGroupChatManager groupChatManager = QBChatService.getInstance().getGroupChatManager();
//                    groupChatManager.updateDialog(dialog, requestBuilder, new QBEntityCallback<QBDialog>() {
//                        @Override
//                        public void onSuccess(QBDialog dialog, Bundle args) {
//
//                        }
//
//                        @Override
//                        public void onError(QBResponseException errors) {
//
//                        }
//                    });

                }







            }
        });

        Button_Disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Vol_DisAgree();


            }
        });


    }


    public void Vol_Agree(String groupId){

        Loader.setVisibility(View.VISIBLE);

        Button_Agree.setEnabled(false);

        Button_Agree.setClickable(false);


        String url = AppData.DomainUrlForProfile+"acceptdeal_control?push_id="+getIntent().getExtras().getString("PushId")+"&accepter_id="+getIntent().getExtras().getString("AccepterId")+"&host_id="+getIntent().getExtras().getString("HostId")+"&requester_id="+getIntent().getExtras().getString("RequisterId")+"&event_id="+getIntent().getExtras().getString("EventId")+"&table_id="+getIntent().getExtras().getString("TableId")+"&male="+getIntent().getExtras().getString("MaleCount")+"&female="+getIntent().getExtras().getString("FemaleCount")+"&payment="+getIntent().getExtras().getString("Amount") + "&qb_groupid=" + groupId;

        Log.v("Agree_Url::",url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("Agree_Responce::",response.toString());

                String message = null;

                try {
                    String status = response.getString("status");
                    message = response.getString("message");
                    String additional = response.getString("additional");

                    if (status.equals("success")){

                        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);


                            if (sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("HostId"))){


                                if (!(additional.equals(""))){

                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(CounterBit.this);

                                    // Setting Dialog Title
                                    //alertDialog.setTitle("Confirm Delete...");

                                    // Setting Dialog Message
                                    alertDialog.setMessage("You have to pay additional " + "$" + additional);

                                    // Setting Icon to Dialog
                                    //alertDialog.setIcon(R.drawable.delete);

                                    // Setting Positive "Yes" Button
                                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            // Write your code here to invoke YES event
                                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(CounterBit.this,EventFullDetails.class);
                                            intent.putExtra("Table_Id",getIntent().getExtras().getString("TableId"));
                                            intent.putExtra("Host_Id",getIntent().getExtras().getString("HostId"));
                                            intent.putExtra("Event_Id",getIntent().getExtras().getString("EventId"));
                                            AppData.EventId = getIntent().getExtras().getString("EventId");
                                            AppData.TableId = getIntent().getExtras().getString("TableId");
                                            startActivity(intent);
                                            finish();
                                            dialog.dismiss();
                                        }
                                    });

                                    // Setting Negative "NO" Button
//                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            // Write your code here to invoke NO event
//                                            //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
//                                            dialog.dismiss();
//                                        }
//                                    });

                                    // Showing Alert Message
                                    alertDialog.show();


                                }else {

                                    Intent intent = new Intent(CounterBit.this,EventFullDetails.class);
                                    intent.putExtra("Table_Id",getIntent().getExtras().getString("TableId"));
                                    intent.putExtra("Host_Id",getIntent().getExtras().getString("HostId"));
                                    intent.putExtra("Event_Id",getIntent().getExtras().getString("EventId"));
                                    AppData.EventId = getIntent().getExtras().getString("EventId");
                                    AppData.TableId = getIntent().getExtras().getString("TableId");
                                    startActivity(intent);
                                    finish();

                                }



                            }else {

                                Intent intent = new Intent(CounterBit.this, SplitPercentage.class);
                                intent.putExtra("PersonCount",String.valueOf(Integer.parseInt(getIntent().getExtras().getString("MaleCount"))+Integer.parseInt(getIntent().getExtras().getString("FemaleCount"))));
                                intent.putExtra("Amount",getIntent().getExtras().getString("Amount"));
                                intent.putExtra("VenueName",getIntent().getExtras().getString("VenueName"));
                                AppData.FromButton = "ContactTheHost";
                                startActivity(intent);
                                finish();

                            }

                    }

                    Loader.setVisibility(View.GONE);

                    Button_Agree.setEnabled(true);

                    Button_Agree.setClickable(true);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Loader.setVisibility(View.GONE);

                    Button_Agree.setEnabled(true);

                    Button_Agree.setClickable(true);
                }

                Toast.makeText(CounterBit.this,message,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Loader.setVisibility(View.GONE);

                Button_Agree.setEnabled(true);

                Button_Agree.setClickable(true);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public void Vol_DisAgree(){

        Loader.setVisibility(View.VISIBLE);

        Button_Disagree.setEnabled(false);

        Button_Disagree.setClickable(false);


        String url = AppData.DomainUrlForProfile+"declinedeal_control?push_id="+getIntent().getExtras().getString("PushId")+"&accepter_id="+getIntent().getExtras().getString("AccepterId")+"&host_id="+getIntent().getExtras().getString("HostId")+"&requester_id="+getIntent().getExtras().getString("RequisterId")+"&event_id="+getIntent().getExtras().getString("EventId")+"&table_id="+getIntent().getExtras().getString("TableId");


        Log.v("DisAgree_url",url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("DisAgree_response",response.toString());

                String message = null;

                try {
                    String status = response.getString("status");
                    message = response.getString("message");

                    if (status.equals("success")){

                        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);


                        if (sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("HostId"))){


                            Intent intent = new Intent(CounterBit.this,EventFullDetails.class);
                            intent.putExtra("Table_Id",getIntent().getExtras().getString("TableId"));
                            intent.putExtra("Host_Id",getIntent().getExtras().getString("HostId"));
                            intent.putExtra("Event_Id",getIntent().getExtras().getString("EventId"));
                            AppData.EventId = getIntent().getExtras().getString("EventId");
                            AppData.TableId = getIntent().getExtras().getString("TableId");
                            startActivity(intent);
                            finish();

                        }else {

                            startActivity(new Intent(CounterBit.this, HomeActivity.class));
                            finish();

                        }

                    }

                    Loader.setVisibility(View.GONE);

                    Button_Disagree.setEnabled(true);

                    Button_Disagree.setClickable(true);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Loader.setVisibility(View.GONE);

                    Button_Disagree.setEnabled(true);

                    Button_Disagree.setClickable(true);
                }

                Toast.makeText(CounterBit.this,message,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Loader.setVisibility(View.GONE);

                Button_Disagree.setEnabled(true);

                Button_Disagree.setClickable(true);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    public void Vol_Counter(String price){

        Loader.setVisibility(View.VISIBLE);

        Button_Counter.setEnabled(false);

        Button_Counter.setClickable(false);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = null;

        if (getIntent().getExtras().getString("Type").equals("FirstTime")){

            url = AppData.DomainUrlForProfile+"counteroffer_control?event_id="+getIntent().getExtras().getString("EventId")+"&table_id="+getIntent().getExtras().getString("TableId")+"&to_id="+getIntent().getExtras().getString("PushId")+"&from_id="+sharedPreferences.getString("UserId","")+"&price="+price+"&male="+getIntent().getExtras().getString("MaleCount")+"&female="+getIntent().getExtras().getString("FemaleCount")+"&firsttime=1";


        }else {

            url = AppData.DomainUrlForProfile+"counteroffer_control?event_id="+getIntent().getExtras().getString("EventId")+"&table_id="+getIntent().getExtras().getString("TableId")+"&to_id="+getIntent().getExtras().getString("PushId")+"&from_id="+sharedPreferences.getString("UserId","")+"&price="+price+"&male="+getIntent().getExtras().getString("MaleCount")+"&female="+getIntent().getExtras().getString("FemaleCount")+"&firsttime=0";


        }


        Log.v("CounterUrl::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String message = null;

                try {
                    String status = response.getString("status");

                    message = response.getString("message");


                    if (status.equals("success")){

                        Loader.setVisibility(View.GONE);

                        Button_Counter.setEnabled(true);

                        Button_Counter.setClickable(true);

                        finish();

                    }

                    Loader.setVisibility(View.GONE);

                    Button_Counter.setEnabled(true);

                    Button_Counter.setClickable(true);

                } catch (JSONException e) {
                    e.printStackTrace();

                    Loader.setVisibility(View.GONE);

                    Button_Counter.setEnabled(true);

                    Button_Counter.setClickable(true);
                }


                Toast.makeText(CounterBit.this,message,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Loader.setVisibility(View.GONE);

                Button_Counter.setEnabled(true);

                Button_Counter.setClickable(true);

            }
        });


        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }


}
