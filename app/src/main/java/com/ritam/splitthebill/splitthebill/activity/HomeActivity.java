package com.ritam.splitthebill.splitthebill.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.PlacesAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.fragments.Booking;
import com.ritam.splitthebill.splitthebill.fragments.EventListing;
import com.ritam.splitthebill.splitthebill.fragments.Message;
import com.ritam.splitthebill.splitthebill.fragments.Notification;
import com.ritam.splitthebill.splitthebill.fragments.Profile;
import com.ritam.splitthebill.splitthebill.push.MyFirebaseMessagingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import setergeter.SetterGetter_Places;

public class HomeActivity extends AppCompatActivity {


    RelativeLayout Tab_EventList, Tab_Booking, Tab_Chat, Tab_Notification, Tab_Profile;
    RelativeLayout Button_Search, Button_Settings;
    RelativeLayout Button_Filter, Button_EditProfile;
    TextView HeaderText;
    RelativeLayout SearchBar, Button_Profile_Search;
    LinearLayout Close_SearchBar;
    EditText SearchText;
    ImageView Icon_Filter;
    String Button_PerPose;

    boolean SearchPlace = false;
    ArrayList<SetterGetter_Places> PlacesInfo;
    RecyclerView Dialog_SearchPlaceList;
    EditText Dialog_SearchPlace;

    boolean CitySelectOrNot = false;

    int SelectedPlaceLocation;

    final int MY_PERMISSIONS_SEND_SMS = 1;

    ProgressBar LogoutProgress;

    ImageView Icon_Search, Icon_Settings;

    String Left_Button_Purpose;

    public TextView BadgeCount, MessageBadgeCount;

    int UnreadMessageCount = 0;

    MyReceiver myReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Tab_EventList = (RelativeLayout) findViewById(R.id.rl_tab_eventlist);
        Tab_Booking = (RelativeLayout) findViewById(R.id.rl_tab_booking);
        Tab_Chat = (RelativeLayout) findViewById(R.id.rl_tab_chat);
        Tab_Notification = (RelativeLayout) findViewById(R.id.rl_tab_notification);
        Tab_Profile = (RelativeLayout) findViewById(R.id.rl_tab_profile);

        LogoutProgress = (ProgressBar) findViewById(R.id.progressBar6);

        Button_Filter = (RelativeLayout) findViewById(R.id.ll_filter);
        Button_Profile_Search = (RelativeLayout) findViewById(R.id.ll_profile_search);

        //Button_EditProfile = (RelativeLayout) findViewById(R.id.rl_editpro);

        Icon_Filter = (ImageView) findViewById(R.id.iv_filter);

        Button_Search = (RelativeLayout) findViewById(R.id.ll_search);
        Button_Settings = (RelativeLayout) findViewById(R.id.ll_settings);
        Icon_Search = (ImageView) findViewById(R.id.iv_search);
        Icon_Settings = (ImageView) findViewById(R.id.iv_settings);
        SearchText = (EditText) findViewById(R.id.et_search);

        SearchBar = (RelativeLayout) findViewById(R.id.rl_search);
        Close_SearchBar = (LinearLayout) findViewById(R.id.ll_close);


        HeaderText = (TextView) findViewById(R.id.tv_header);

        BadgeCount = (TextView) findViewById(R.id.text_counter);
        MessageBadgeCount = (TextView) findViewById(R.id.text_counter_message);


        HeaderText.setText("Events");
        //Tab_EventList.setBackgroundColor(Color.parseColor("#c91010"));
        //Tab_Booking.setBackgroundColor(Color.parseColor("#292929"));
        Tab_EventList.setSelected(true);
        Tab_Booking.setSelected(false);
        Tab_Chat.setSelected(false);
        Tab_Notification.setSelected(false);
        Tab_Profile.setSelected(false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_homepage, EventListing.newInstance());
        fragmentTransaction.commit();

        Icon_Filter.setVisibility(View.VISIBLE);

        Button_PerPose = "Filter";

        Left_Button_Purpose = "Search";


        Tab_EventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AppData.setIsMessageDetails("NO");
                AppData.setIsMessage("NO");
                //Button_EditProfile.setVisibility(View.GONE);

                Button_Filter.setVisibility(View.VISIBLE);

                Icon_Filter.setVisibility(View.VISIBLE);

                Button_PerPose = "Filter";

                Left_Button_Purpose = "Search";

                Button_Search.setVisibility(View.VISIBLE);

                Icon_Search.setVisibility(View.VISIBLE);
                Icon_Settings.setVisibility(View.GONE);
                Button_Settings.setVisibility(View.GONE);
                Button_Profile_Search.setVisibility(View.GONE);

                HeaderText.setText("Events");
//                Tab_EventList.setBackgroundColor(Color.parseColor("#c91010"));
//                Tab_Booking.setBackgroundColor(Color.parseColor("#292929"));
                Tab_EventList.setSelected(true);
                Tab_Booking.setSelected(false);
                Tab_Chat.setSelected(false);
                Tab_Notification.setSelected(false);
                Tab_Profile.setSelected(false);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_homepage, EventListing.newInstance());
                fragmentTransaction.commit();

                if (AppData.FromNotificationPage) {

                    AppData.FromNotificationPage = false;

                    Vol_BadgeCount();

                }
                if (AppData.FromMessagePage) {

                    AppData.FromMessagePage = false;

                    UnreadMessageCount = 0;

                    QBSettings.getInstance().init(HomeActivity.this, AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
                    QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

                    QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
                    requestBuilder.setLimit(100);

                    QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                            new QBEntityCallback<ArrayList<QBChatDialog>>() {
                                @Override
                                public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {


                                    int totalEntries = params.getInt("total_entries");


                                    Log.v("QBChatDialog_Bundle::", params.toString());

                                    if (result.size() > 0) {


                                        for (int i = 0; i < result.size(); i++) {


                                            int unreadmessagecount = result.get(i).getUnreadMessageCount();

                                            UnreadMessageCount = UnreadMessageCount + unreadmessagecount;


                                        }


//                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                            editor.putInt("UnreadMessageCount",UnreadMessageCount);
//
//                            editor.commit();

                                        if (UnreadMessageCount > 0) {

                                            MessageBadgeCount.setVisibility(View.VISIBLE);

                                            MessageBadgeCount.setText(String.valueOf(UnreadMessageCount));

                                        } else {

                                            MessageBadgeCount.setVisibility(View.GONE);

                                        }


                                    } else {

                                        MessageBadgeCount.setVisibility(View.GONE);

                                    }


                                }

                                @Override
                                public void onError(QBResponseException responseException) {

                                    MessageBadgeCount.setVisibility(View.GONE);

                                }
                            });

                }


            }
        });


        Tab_Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppData.setIsMessageDetails("NO");
                AppData.setIsMessage("NO");
                //Button_EditProfile.setVisibility(View.GONE);

                Button_Filter.setVisibility(View.GONE);
                Button_Search.setVisibility(View.GONE);
                Button_Settings.setVisibility(View.GONE);
                Button_Profile_Search.setVisibility(View.GONE);


                HeaderText.setText("Reservations");
//                Tab_EventList.setBackgroundColor(Color.parseColor("#292929"));
//                Tab_Booking.setBackgroundColor(Color.parseColor("#c91010"));
                Tab_EventList.setSelected(false);
                Tab_Booking.setSelected(true);
                Tab_Chat.setSelected(false);
                Tab_Notification.setSelected(false);
                Tab_Profile.setSelected(false);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_homepage, Booking.newInstance());
                fragmentTransaction.commit();

                if (AppData.FromNotificationPage) {

                    AppData.FromNotificationPage = false;

                    Vol_BadgeCount();

                }


                if (AppData.FromMessagePage) {

                    AppData.FromMessagePage = false;

                    UnreadMessageCount = 0;

                    QBSettings.getInstance().init(HomeActivity.this, AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
                    QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

                    QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
                    requestBuilder.setLimit(100);

                    QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                            new QBEntityCallback<ArrayList<QBChatDialog>>() {
                                @Override
                                public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {


                                    int totalEntries = params.getInt("total_entries");


                                    Log.v("QBChatDialog_Bundle::", params.toString());

                                    if (result.size() > 0) {


                                        for (int i = 0; i < result.size(); i++) {


                                            int unreadmessagecount = result.get(i).getUnreadMessageCount();

                                            UnreadMessageCount = UnreadMessageCount + unreadmessagecount;


                                        }


//                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                            editor.putInt("UnreadMessageCount",UnreadMessageCount);
//
//                            editor.commit();

                                        if (UnreadMessageCount > 0) {

                                            MessageBadgeCount.setVisibility(View.VISIBLE);

                                            MessageBadgeCount.setText(String.valueOf(UnreadMessageCount));

                                        } else {

                                            MessageBadgeCount.setVisibility(View.GONE);

                                        }


                                    } else {

                                        MessageBadgeCount.setVisibility(View.GONE);

                                    }


                                }

                                @Override
                                public void onError(QBResponseException responseException) {

                                    MessageBadgeCount.setVisibility(View.GONE);

                                }
                            });

                }


            }
        });


        Tab_Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AppData.setIsMessageDetails("NO");
                //Button_EditProfile.setVisibility(View.GONE);

                Button_Filter.setVisibility(View.GONE);
                Button_Search.setVisibility(View.GONE);
                Button_Settings.setVisibility(View.GONE);
                Button_Profile_Search.setVisibility(View.GONE);

                HeaderText.setText("Chats");
//                Tab_EventList.setBackgroundColor(Color.parseColor("#292929"));
//                Tab_Booking.setBackgroundColor(Color.parseColor("#292929"));
                Tab_EventList.setSelected(false);
                Tab_Booking.setSelected(false);
                Tab_Chat.setSelected(true);
                Tab_Notification.setSelected(false);
                Tab_Profile.setSelected(false);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_homepage, Message.newInstance());
                fragmentTransaction.commit();

                if (AppData.FromNotificationPage) {

                    AppData.FromNotificationPage = false;

                    Vol_BadgeCount();

                }

                AppData.FromMessagePage = true;
                MessageBadgeCount.setVisibility(View.GONE);


//                UnreadMessageCount = 0;
//
//                QBSettings.getInstance().init(HomeActivity.this, AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
//                QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);
//
//                QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
//                requestBuilder.setLimit(100);
//
//                QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
//                        new QBEntityCallback<ArrayList<QBChatDialog>>() {
//                            @Override
//                            public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {
//
//
//                                int totalEntries = params.getInt("total_entries");
//
//
//                                Log.v("QBChatDialog_Bundle::", params.toString());
//
//                                if (result.size() > 0) {
//
//
//                                    for (int i = 0; i < result.size(); i++) {
//
//
//                                        int unreadmessagecount = result.get(i).getUnreadMessageCount();
//
//                                        UnreadMessageCount = UnreadMessageCount + unreadmessagecount;
//
//
//                                    }
//
//
////                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
////                            SharedPreferences.Editor editor = sharedPreferences.edit();
////
////                            editor.putInt("UnreadMessageCount",UnreadMessageCount);
////
////                            editor.commit();
//
//                                    if (UnreadMessageCount > 0){
//
//                                        MessageBadgeCount.setVisibility(View.VISIBLE);
//
//                                        MessageBadgeCount.setText(String.valueOf(UnreadMessageCount));
//
//                                    }else {
//
//                                        MessageBadgeCount.setVisibility(View.GONE);
//
//                                    }
//
//
//                                } else {
//
//                                    MessageBadgeCount.setVisibility(View.GONE);
//
//                                }
//
//
//                            }
//
//                            @Override
//                            public void onError(QBResponseException responseException) {
//
//                                MessageBadgeCount.setVisibility(View.GONE);
//
//                            }
//                        });
            }
        });

        Tab_Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AppData.setIsMessageDetails("NO");
                AppData.setIsMessage("NO");
                //Button_EditProfile.setVisibility(View.GONE);

                Button_Filter.setVisibility(View.GONE);
                Button_Search.setVisibility(View.GONE);
                Button_Settings.setVisibility(View.GONE);
                Button_Profile_Search.setVisibility(View.GONE);

                HeaderText.setText("Notifications");
//                Tab_EventList.setBackgroundColor(Color.parseColor("#292929"));
//                Tab_Booking.setBackgroundColor(Color.parseColor("#292929"));
                Tab_EventList.setSelected(false);
                Tab_Booking.setSelected(false);
                Tab_Chat.setSelected(false);
                Tab_Notification.setSelected(true);
                Tab_Profile.setSelected(false);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_homepage, Notification.newInstance());
                fragmentTransaction.commit();

                AppData.FromNotificationPage = true;
                BadgeCount.setVisibility(View.GONE);

                if (AppData.FromMessagePage) {

                    AppData.FromMessagePage = false;

                    UnreadMessageCount = 0;

                    QBSettings.getInstance().init(HomeActivity.this, AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
                    QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

                    QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
                    requestBuilder.setLimit(100);

                    QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                            new QBEntityCallback<ArrayList<QBChatDialog>>() {
                                @Override
                                public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {


                                    int totalEntries = params.getInt("total_entries");


                                    Log.v("QBChatDialog_Bundle::", params.toString());

                                    if (result.size() > 0) {


                                        for (int i = 0; i < result.size(); i++) {


                                            int unreadmessagecount = result.get(i).getUnreadMessageCount();

                                            UnreadMessageCount = UnreadMessageCount + unreadmessagecount;


                                        }


//                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                            editor.putInt("UnreadMessageCount",UnreadMessageCount);
//
//                            editor.commit();

                                        if (UnreadMessageCount > 0) {

                                            MessageBadgeCount.setVisibility(View.VISIBLE);

                                            MessageBadgeCount.setText(String.valueOf(UnreadMessageCount));

                                        } else {

                                            MessageBadgeCount.setVisibility(View.GONE);

                                        }


                                    } else {

                                        MessageBadgeCount.setVisibility(View.GONE);

                                    }


                                }

                                @Override
                                public void onError(QBResponseException responseException) {

                                    MessageBadgeCount.setVisibility(View.GONE);

                                }
                            });

                }

            }
        });


        Tab_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppData.setIsMessageDetails("NO");
                AppData.setIsMessage("NO");
                //Button_EditProfile.setVisibility(View.VISIBLE);

                Button_Filter.setVisibility(View.VISIBLE);
                Button_Profile_Search.setVisibility(View.VISIBLE);

                Icon_Filter.setVisibility(View.GONE);

                Button_PerPose = "Logout";

                Left_Button_Purpose = "Settings";

                Button_Search.setVisibility(View.GONE);

                Icon_Search.setVisibility(View.GONE);
                Icon_Settings.setVisibility(View.VISIBLE);
                Button_Settings.setVisibility(View.VISIBLE);

                HeaderText.setText("Edit Profile");
//                Tab_EventList.setBackgroundColor(Color.parseColor("#292929"));
//                Tab_Booking.setBackgroundColor(Color.parseColor("#292929"));
                Tab_EventList.setSelected(false);
                Tab_Booking.setSelected(false);
                Tab_Chat.setSelected(false);
                Tab_Notification.setSelected(false);
                Tab_Profile.setSelected(true);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_homepage, Profile.newInstance());
                fragmentTransaction.commit();

                if (AppData.FromNotificationPage) {

                    AppData.FromNotificationPage = false;
                    Vol_BadgeCount();
                }

                if (AppData.FromMessagePage) {

                    AppData.FromMessagePage = false;

                    UnreadMessageCount = 0;

                    QBSettings.getInstance().init(HomeActivity.this, AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
                    QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

                    QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
                    requestBuilder.setLimit(100);

                    QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                            new QBEntityCallback<ArrayList<QBChatDialog>>() {
                                @Override
                                public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {


                                    int totalEntries = params.getInt("total_entries");


                                    Log.v("QBChatDialog_Bundle::", params.toString());

                                    if (result.size() > 0) {


                                        for (int i = 0; i < result.size(); i++) {


                                            int unreadmessagecount = result.get(i).getUnreadMessageCount();

                                            UnreadMessageCount = UnreadMessageCount + unreadmessagecount;


                                        }


//                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                            editor.putInt("UnreadMessageCount",UnreadMessageCount);
//
//                            editor.commit();

                                        if (UnreadMessageCount > 0) {

                                            MessageBadgeCount.setVisibility(View.VISIBLE);

                                            MessageBadgeCount.setText(String.valueOf(UnreadMessageCount));

                                        } else {

                                            MessageBadgeCount.setVisibility(View.GONE);

                                        }


                                    } else {

                                        MessageBadgeCount.setVisibility(View.GONE);

                                    }


                                }

                                @Override
                                public void onError(QBResponseException responseException) {

                                    MessageBadgeCount.setVisibility(View.GONE);

                                }
                            });

                }

            }
        });

//        Button_EditProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                startActivity(new Intent(HomeActivity.this,EditProfile.class));
//
//            }
//        });

        Button_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeActivity.this, Settings.class));

            }
        });

        Button_Profile_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeActivity.this, UserSearch.class));

            }
        });


        Button_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Button_PerPose.equals("Filter")) {

                    LinearLayout Button_Cross, Button_Accept;

                    ImageView Button_Calender;

                    final SeekBar Seek_NearBy;

                    final TextView Seek_Progress_Text, Text_Calender;


                    View view1 = LayoutInflater.from(HomeActivity.this).inflate(R.layout.dialog_filter, null);
                    final Dialog dialog = new Dialog(HomeActivity.this, R.style.MaterialDialogSheet);
                    dialog.setContentView(view1);
                    dialog.setCancelable(true);
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    dialog.show();


                    Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_dialog_cross);

                    Button_Accept = (LinearLayout) view1.findViewById(R.id.ll_accept);

                    Seek_NearBy = (SeekBar) view1.findViewById(R.id.sb_nearby);

                    Seek_Progress_Text = (TextView) view1.findViewById(R.id.tv_nearby);

                    Seek_NearBy.setMax(60);


                    Button_Calender = (ImageView) view1.findViewById(R.id.iv_date);

                    Text_Calender = (TextView) view1.findViewById(R.id.tv_datetext);

                    Dialog_SearchPlace = (EditText) view1.findViewById(R.id.et_place);
                    Dialog_SearchPlaceList = (RecyclerView) view1.findViewById(R.id.rv_serchplace);


                    Dialog_SearchPlace.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            SearchPlace = true;

                        }
                    });

                    Dialog_SearchPlace.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {


                            SearchPlace = true;
                            return false;
                        }
                    });


                    Dialog_SearchPlace.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                            try {

                                if (SearchPlace) {

                                    if (charSequence.length() > 0) {

                                        Dialog_SearchPlaceList.setVisibility(View.VISIBLE);
                                        Vol_FetchingPlace(URLEncoder.encode(charSequence.toString(), "utf-8"));

                                    } else {

                                        Dialog_SearchPlaceList.setVisibility(View.GONE);

                                    }


                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void afterTextChanged(Editable editable) {


                        }
                    });


                    Button_Calender.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Calendar calendar = Calendar.getInstance();

                            DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {


                                    String inputPattern = "dd/mm/yyyy";
                                    String outputPattern = "dd MMMM yyyy";
                                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                                    Date date = null;
                                    String str = null;

                                    try {
                                        date = inputFormat.parse(String.valueOf(i2) + "/" + String.valueOf(i1) + "/" + String.valueOf(i));
                                        str = outputFormat.format(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    Text_Calender.setText(str);


                                }
                            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


                            datePickerDialog.show();

                        }
                    });

                    Seek_NearBy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                            Seek_Progress_Text.setText(String.valueOf(i));

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                    Button_Cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                        }
                    });


                    Button_Accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            EventListing eventListing = EventListing.newInstance();
                            eventListing.Vol_FilterEvents(AppData.FilterLat, AppData.FilterLong, String.valueOf(Seek_NearBy.getProgress()));

                            dialog.dismiss();

                        }
                    });

                }


            }
        });


        Button_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Left_Button_Purpose == "Search") {

                    SearchBar.setVisibility(View.VISIBLE);
                    SearchText.setText("");
                    Animation RightSwipe = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.right_to_left);
                    SearchBar.startAnimation(RightSwipe);
                }

//                }else if (Left_Button_Purpose == "Settings"){
//
//
//                    startActivity(new Intent(HomeActivity.this,Settings.class));
//
//                }


            }
        });


        SearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0) {

                    EventListing eventListing = EventListing.newInstance();
                    eventListing.SearchingEventList(charSequence.toString().trim());

                } else {

                    EventListing eventListing = EventListing.newInstance();
                    eventListing.FetchingEventList();

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        Close_SearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                EventListing eventListing = EventListing.newInstance();
//                eventListing.FetchingEventList();
                SearchText.setText("");

                SearchBar.setVisibility(View.GONE);
                Animation LeftSwipe = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.left_to_right);
                SearchBar.startAnimation(LeftSwipe);

                View view1 = getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });


        SearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Toast.makeText(HomeActivity.this, "Searching....", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });


        try {

            if (getIntent().getExtras().getString("type").equals("notification")) {

                Tab_Notification.performClick();

            } else if (getIntent().getExtras().getString("type").equals("message")) {

                Tab_Chat.performClick();

            }

        } catch (Exception e) {


        }


        try {

            if (getIntent().getExtras().getString("FromPage").equals("Payment")) {

                Tab_Booking.performClick();

                if (getIntent().getExtras().getString("NonAppUser").equals("YES")) {


                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(HomeActivity.this,
                            Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                                Manifest.permission.SEND_SMS)) {

                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                            Log.v("SMS_Permission", "1");

                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_SEND_SMS);


                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_SEND_SMS);

                            Log.v("SMS_Permission", "2");

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {

                        Log.v("SMS_Permission", "3");

                        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);


                        Uri sendSmsTo = Uri.parse("smsto:" + getIntent().getExtras().getString("PhoneNumber"));
                        Intent intent = new Intent(
                                android.content.Intent.ACTION_SENDTO, sendSmsTo);
                        intent.putExtra("sms_body", sharedPreferences.getString("first_name", "") + " " + sharedPreferences.getString("last_name", "") + " has invited you to Split The Bill at " + getIntent().getExtras().getString("EventName") + " on " + getIntent().getExtras().getString("EventDate")
                                + " please click here to download the STB app for more information.");
                        startActivity(intent);

                    }

                }


            } else if (getIntent().getExtras().getString("FromPage").equals("CancelBooking")) {

                Tab_Booking.performClick();

            }

        } catch (Exception e) {

        }


    }


    public void Vol_FetchingPlace(String place) {


        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + place + "&key=AIzaSyBYWD7Jcqv6JwOhVtk_4uPZjAv7lzcdMww";

        Log.v("AutoSuggestPlacesUrl:", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray results = response.getJSONArray("results");

                    Log.v("AutoSuggestPlacesUrlLenght", "" + results.length());

                    if (results.length() > 0) {

                        Dialog_SearchPlaceList.setVisibility(View.VISIBLE);

                        PlacesInfo = new ArrayList<SetterGetter_Places>();


                        for (int i = 0; i < results.length(); i++) {

                            JSONObject placeObject = results.getJSONObject(i);

                            JSONArray address_components = placeObject.getJSONArray("address_components");

                            String CityName = (address_components.getJSONObject(0)).getString("long_name");
                            String CountryName = (address_components.getJSONObject(address_components.length() - 1)).getString("long_name");

                            String FullAddress = placeObject.getString("formatted_address");

                            JSONObject geometry = placeObject.getJSONObject("geometry");

                            long Lat = (geometry.getJSONObject("location")).getLong("lat");
                            long Long = (geometry.getJSONObject("location")).getLong("lng");


                            SetterGetter_Places setterGetter_places = new SetterGetter_Places(CityName, CountryName, FullAddress, Lat, Long);
                            PlacesInfo.add(setterGetter_places);

                        }

                        PlacesAdapter placesAdapter = new PlacesAdapter(HomeActivity.this, HomeActivity.this, PlacesInfo);
                        Dialog_SearchPlaceList.setHasFixedSize(true);
                        Dialog_SearchPlaceList.setLayoutManager(new GridLayoutManager(HomeActivity.this, 1));
                        Dialog_SearchPlaceList.setAdapter(placesAdapter);

                    } else {

                        Dialog_SearchPlaceList.setVisibility(View.GONE);

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


    public void ChoosePlace(int pos) {

        SearchPlace = false;

        CitySelectOrNot = true;

        SelectedPlaceLocation = pos;

        Dialog_SearchPlace.setText(PlacesInfo.get(pos).getFullAddress());

        Dialog_SearchPlaceList.setVisibility(View.GONE);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_SEND_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Uri sendSmsTo = Uri.parse("smsto:" + getIntent().getExtras().getString("PhoneNumber"));
                    Intent intent = new Intent(
                            android.content.Intent.ACTION_SENDTO, sendSmsTo);
                    intent.putExtra("sms_body", "Testing");
                    startActivity(intent);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(HomeActivity.this, "You have to give permission for sending sms", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppData.setIsMessageDetails("NO");

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyFirebaseMessagingService.Update_BadgeCount_Action);
        registerReceiver(myReceiver, intentFilter);

        Vol_BadgeCount();

        UnreadMessageCount = 0;

        QBSettings.getInstance().init(HomeActivity.this, AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
        QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(100);

        QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                new QBEntityCallback<ArrayList<QBChatDialog>>() {
                    @Override
                    public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {


                        int totalEntries = params.getInt("total_entries");


                        Log.v("QBChatDialog_Bundle::", params.toString());

                        if (result.size() > 0) {


                            for (int i = 0; i < result.size(); i++) {


                                int unreadmessagecount = result.get(i).getUnreadMessageCount();

                                UnreadMessageCount = UnreadMessageCount + unreadmessagecount;


                            }


//                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                            editor.putInt("UnreadMessageCount",UnreadMessageCount);
//
//                            editor.commit();

                            if (UnreadMessageCount > 0) {

                                MessageBadgeCount.setVisibility(View.VISIBLE);

                                MessageBadgeCount.setText(String.valueOf(UnreadMessageCount));

                            } else {

                                MessageBadgeCount.setVisibility(View.GONE);

                            }


                        } else {

                            MessageBadgeCount.setVisibility(View.GONE);

                        }


                    }

                    @Override
                    public void onError(QBResponseException responseException) {

                        MessageBadgeCount.setVisibility(View.GONE);

                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        AppData.setIsMessageDetails("NO");
    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            unregisterReceiver(myReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Vol_BadgeCount() {

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "badgecount_control?userid=" + sharedPreferences.getString("UserId", "");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");

                    if (status.equals("success")) {

                        String badgecount = response.getString("badgecount");


                        if (Integer.parseInt(badgecount) > 0) {

                            BadgeCount.setVisibility(View.VISIBLE);
                            BadgeCount.setText(badgecount);

                        } else {

                            BadgeCount.setVisibility(View.GONE);

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


    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.v("MyReceiver", "Calling");

            if (intent.getExtras().getString("Purpose").equals("Notification")) {

                Vol_BadgeCount();

            } else if (intent.getExtras().getString("Purpose").equals("Message")) {

                UnreadMessageCount = 0;

                QBSettings.getInstance().init(HomeActivity.this, AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
                QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

                QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
                requestBuilder.setLimit(100);

                QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                        new QBEntityCallback<ArrayList<QBChatDialog>>() {
                            @Override
                            public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {


                                int totalEntries = params.getInt("total_entries");


                                Log.v("QBChatDialog_Bundle::", params.toString());

                                if (result.size() > 0) {


                                    for (int i = 0; i < result.size(); i++) {


                                        int unreadmessagecount = result.get(i).getUnreadMessageCount();

                                        UnreadMessageCount = UnreadMessageCount + unreadmessagecount;


                                    }


//                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                            editor.putInt("UnreadMessageCount",UnreadMessageCount);
//
//                            editor.commit();

                                    if (UnreadMessageCount > 0) {

                                        MessageBadgeCount.setVisibility(View.VISIBLE);

                                        MessageBadgeCount.setText(String.valueOf(UnreadMessageCount));

                                    } else {

                                        MessageBadgeCount.setVisibility(View.GONE);

                                    }


                                } else {

                                    MessageBadgeCount.setVisibility(View.GONE);

                                }


                            }

                            @Override
                            public void onError(QBResponseException responseException) {

                                MessageBadgeCount.setVisibility(View.GONE);

                            }
                        });

            }


        }
    }

}
