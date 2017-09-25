package com.ritam.splitthebill.splitthebill.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.Dialog_AdvanceList_Bookingasahost_Adapter;
import com.ritam.splitthebill.splitthebill.adapter.Dialog_AdvanceList_Counter_Adapter;
import com.ritam.splitthebill.splitthebill.adapter.PromoterList_Adapter;
import com.ritam.splitthebill.splitthebill.adapter.RecyclerSliters;
import com.ritam.splitthebill.splitthebill.adapter.SplittersAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import setergeter.MemberList_Settergetter;
import setergeter.Phone_Selected_SetterGetter;
import setergeter.Splitter_Selected_SetterGetter;

public class BookingAsHost extends AppCompatActivity {

    LinearLayout Button_Back, Button_InviteSplitter, done, Button_ViewDetails, Button_Authorize, Button_Cancel;

    RecyclerView recyclerView;
    RecyclerSliters adapter;

    TextView male_no, female_no;

    SeekBar seek_bar1, seek_bar2;

    LinearLayout contact_block, contact_the_host_view;

    RelativeLayout ContactTheHostLayout;
    TextView BookingAsHostLayout;

    int total;
    int temp;


    int Mprogress = 0;
    int Fprogress = 0;
    int MaleProgress = 0;
    int FemaleProgress = 0;

    TextView HeaderText, MaximumAvailableSeat, GuestCount, Text_MinimumAmount, TExt_TableAmount;

    ArrayList<String> AllAmount;

    int LastValue;

    RelativeLayout Button_Amount;

    TextView ContactTheHost_Amount;

    TextView Button_Done_Text, MinAmount_Text;

    RecyclerView rv_spliter;
    SplittersAdapter adaptersp;

    ImageView male_minus, male_plus, female_minus, female_plus;

    ArrayList<MemberList_Settergetter> AllMember;

    TextView VenueName, BookedDate, PromoterText;

    String SendSms_PhoneNumber = "";

    String NonAppUser = "NO";

    ProgressBar Loader;

    String Booked_MaleCount, Booked_FemaleCount, Booked_UserStatus;

    Dialog dialog;


    String PhoneNumber;
    final int MY_PERMISSIONS_SEND_SMS = 1;

    int AvailableSeatCount = AppData.NumberOfAvailableSeat;

    ArrayList<MemberList_Settergetter> AcceptedMember;
    boolean AcceptedMeberIsAvailableOrNot = false;

    boolean MalePlus = false, FemalePlus = false, MaleMinus = false, FemaleMinus = false, RateChanged = false;

    TextView Button_Promoter;

    TextView EnterAmountTExt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppData.ContactPageCount = 1;
        AppData.RateChanged = false;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        double wi = (double) width / (double) dm.xdpi;
        double hi = (double) height / (double) dm.ydpi;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);

        Log.v("ScreenSize", String.valueOf(screenInches));


        if (AppData.FromButton.equals("ContactTheHost")) {

            setContentView(R.layout.activity_booking_as_host_requister);

        } else if (AppData.FromButton.equals("HostTheTable")) {

            AppData.SelectedPromoterId = "0";

            if (screenInches > 5.5) {

                setContentView(R.layout.activity_booking_as_host);

            } else {

                setContentView(R.layout.activity_booking_as_host_small);

            }

        }


        EnterAmountTExt = (TextView) findViewById(R.id.enter_amount);

        Button_Amount = (RelativeLayout) findViewById(R.id.rl_amountbox);


        AllMember = new ArrayList<MemberList_Settergetter>();

        VenueName = (TextView) findViewById(R.id.tv_venuename);
        BookedDate = (TextView) findViewById(R.id.tv_date);


        if (AppData.FromButton.equals("HostTheTable")) {

            PromoterText = (TextView) findViewById(R.id.tv_promoter);

            Button_Promoter = (TextView) findViewById(R.id.tv_promoter_text);
        }


        contact_block = (LinearLayout) findViewById(R.id.contact_block);
        contact_the_host_view = (LinearLayout) findViewById(R.id.contact_the_host_view);

        ContactTheHostLayout = (RelativeLayout) findViewById(R.id.rl_contactthehost);
        BookingAsHostLayout = (TextView) findViewById(R.id.tv_hostthetable);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        HeaderText = (TextView) findViewById(R.id.header);

        MaximumAvailableSeat = (TextView) findViewById(R.id.tv_noofseat);

        //AccomodateText = (TextView) findViewById(R.id.tv_text_accomodate);
        rv_spliter = (RecyclerView) findViewById(R.id.rv_spliter);

        GuestCount = (TextView) findViewById(R.id.tv_guestcount);

        GuestCount.setText("0");

        Loader = (ProgressBar) findViewById(R.id.progressBar10);


        Text_MinimumAmount = (TextView) findViewById(R.id.tv_minimumamount);

        TExt_TableAmount = (TextView) findViewById(R.id.tv_tableamount);


        if (AppData.FromButton.equals("ContactTheHost")) {

            Text_MinimumAmount.setText(String.valueOf(String.valueOf(AppData.MaximumAmount)));

            TExt_TableAmount.setText("0");

        } else {

            Text_MinimumAmount.setText(String.valueOf(String.valueOf(AppData.MinimumAmount)));


            TExt_TableAmount.setText(String.valueOf(AppData.MinimumAmount));
        }


        if (AppData.FromButton.equals("ContactTheHost")){

            try {

                if (getIntent().getExtras().getString("Purpose").equals("Edit") || getIntent().getExtras().getString("Purpose").equals("Notification")) {


                    Button_Amount.setVisibility(View.VISIBLE);
                    Button_Amount.setEnabled(true);
                    Button_Amount.setClickable(true);
                    EnterAmountTExt.setVisibility(View.VISIBLE);
                    EnterAmountTExt.setEnabled(true);
                    EnterAmountTExt.setClickable(true);

                }else {

                    Button_Amount.setVisibility(View.GONE);
                    Button_Amount.setEnabled(false);
                    Button_Amount.setClickable(false);
                    EnterAmountTExt.setVisibility(View.GONE);
                    EnterAmountTExt.setEnabled(false);
                    EnterAmountTExt.setClickable(false);

                }

            }catch (Exception e){


                Button_Amount.setVisibility(View.GONE);
                Button_Amount.setEnabled(false);
                Button_Amount.setClickable(false);
                EnterAmountTExt.setVisibility(View.GONE);
                EnterAmountTExt.setEnabled(false);
                EnterAmountTExt.setClickable(false);

            }





        }

//        else {
//
//            Button_Amount.setVisibility(View.VISIBLE);
//            Button_Amount.setEnabled(true);
//            Button_Amount.setClickable(true);
//            EnterAmountTExt.setVisibility(View.VISIBLE);
//            EnterAmountTExt.setEnabled(true);
//            EnterAmountTExt.setClickable(true);
//
//        }

        if (AppData.FromButton.equals("HostTheTable")) {

            VenueName.setText(getIntent().getExtras().getString("VenueName"));

        }

        AllAmount = new ArrayList<String>();
        AllAmount.clear();

        LastValue = AppData.MinimumAmount;

        Button_Cancel = (LinearLayout) findViewById(R.id.ll_cancel);

        if (AppData.FromButton.equals("ContactTheHost")) {

            Button_ViewDetails = (LinearLayout) findViewById(R.id.ll_viewdetails);
            Button_Authorize = (LinearLayout) findViewById(R.id.ll_authorize);

            if (getIntent().getExtras().getString("Purpose").equals("Notification")) {

                Button_Authorize.setVisibility(View.VISIBLE);

            } else {

                Button_Authorize.setVisibility(View.GONE);

            }

        }


        ContactTheHost_Amount = (TextView) findViewById(R.id.et_price);

        if (AppData.FromButton.equals("ContactTheHost")) {

            ContactTheHost_Amount.setText("0");

        } else {

            ContactTheHost_Amount.setText(String.valueOf(AppData.MinimumAmount));
        }


        Button_Done_Text = (TextView) findViewById(R.id.tv_donetext);
        MinAmount_Text = (TextView) findViewById(R.id.tv_minamount);

        male_minus = (ImageView) findViewById(R.id.male_minus);
        male_plus = (ImageView) findViewById(R.id.male_plus);
        female_minus = (ImageView) findViewById(R.id.female_minus);
        female_plus = (ImageView) findViewById(R.id.female_plus);


        if (AppData.FromButton.equals("HostTheTable")) {

            Button_Done_Text.setText("DONE");
            MinAmount_Text.setText("Min Deposit");
            AppData.FromEdit = "No";

        } else if (AppData.FromButton.equals("ContactTheHost")) {

            try {

                if (getIntent().getExtras().getString("Purpose").equals("Edit") || getIntent().getExtras().getString("Purpose").equals("Notification")) {

                    Button_Done_Text.setText("SAVE");

                    if (getIntent().getExtras().getString("Purpose").equals("Edit")) {

                        AppData.FromEdit = "Yes";

                    } else {

                        AppData.FromEdit = "No";

                    }

                } else {

                    Button_Done_Text.setText("NOTIFY THE HOST");
                    AppData.FromEdit = "No";

                }

            } catch (Exception e) {

                Button_Done_Text.setText("NOTIFY THE HOST");
                AppData.FromEdit = "No";

            }


            MinAmount_Text.setText("Remaining Amount");
            contact_the_host_view.setVisibility(View.VISIBLE);

            rv_spliter.setHasFixedSize(true);
            rv_spliter.setLayoutManager(new LinearLayoutManager(BookingAsHost.this));


        }

        AppData.SelectedAmount = AppData.MinimumAmount;

        Log.v("BHagFal", String.valueOf(AppData.MaximumAmount / AppData.MinimumAmount));
        Log.v("BHagSes", String.valueOf(AppData.MaximumAmount % AppData.MinimumAmount));
        Log.v("MaximumAmount::", String.valueOf(AppData.MaximumAmount));


        for (int i = 0; i <= ((AppData.MaximumAmount - AppData.MinimumAmount) / 50); i++) {

            if ((i == ((AppData.MaximumAmount - AppData.MinimumAmount) / 50))) {

                if (((AppData.MaximumAmount - AppData.MinimumAmount) % 50) != 0) {

                    AllAmount.add(String.valueOf(LastValue + ((AppData.MaximumAmount - AppData.MinimumAmount) % 50)));

                } else {

                    AllAmount.add(String.valueOf(LastValue + 50));

                }

            } else {

                if (i == 0) {

                    LastValue = AppData.MinimumAmount;

                } else {

                    LastValue = LastValue + 50;

                }


                AllAmount.add(String.valueOf(LastValue));

            }

        }

        Log.v("AllValues", AllAmount.toString());

        Button_Amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout Button_Cross;
                RecyclerView AmountList;

                View view1 = LayoutInflater.from(BookingAsHost.this).inflate(R.layout.dialog_bookasahost_advancelist, null);
                final Dialog dialog = new Dialog(BookingAsHost.this, R.style.MaterialDialogSheet);
                dialog.setContentView(view1);
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();


                Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_back_dialog_bookingasahost);

                AmountList = (RecyclerView) view1.findViewById(R.id.rv_bookingasahost_advanceList);
                AmountList.setHasFixedSize(true);
                AmountList.setLayoutManager(new LinearLayoutManager(BookingAsHost.this));

                Dialog_AdvanceList_Bookingasahost_Adapter dialog_advanceList_bookingasahost_adapter = new Dialog_AdvanceList_Bookingasahost_Adapter(AllAmount, BookingAsHost.this, dialog, TExt_TableAmount, ContactTheHost_Amount);
                AmountList.setAdapter(dialog_advanceList_bookingasahost_adapter);


                Button_Cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });

            }
        });


        TExt_TableAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LinearLayout Button_Cross;
                RecyclerView AmountList;

                View view1 = LayoutInflater.from(BookingAsHost.this).inflate(R.layout.dialog_bookasahost_advancelist, null);
                final Dialog dialog = new Dialog(BookingAsHost.this, R.style.MaterialDialogSheet);
                dialog.setContentView(view1);
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();


                Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_back_dialog_bookingasahost);

                AmountList = (RecyclerView) view1.findViewById(R.id.rv_bookingasahost_advanceList);
                AmountList.setHasFixedSize(true);
                AmountList.setLayoutManager(new LinearLayoutManager(BookingAsHost.this));

                Dialog_AdvanceList_Bookingasahost_Adapter dialog_advanceList_bookingasahost_adapter = new Dialog_AdvanceList_Bookingasahost_Adapter(AllAmount, BookingAsHost.this, dialog, TExt_TableAmount, ContactTheHost_Amount);
                AmountList.setAdapter(dialog_advanceList_bookingasahost_adapter);


                Button_Cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });


            }
        });


        Button_InviteSplitter = (LinearLayout) findViewById(R.id.ll_invitesplitter);

        try {

            if (getIntent().getExtras().getString("Purpose").equals("Edit")) {

                Button_InviteSplitter.setVisibility(View.VISIBLE);

                if (AppData.FromButton.equals("ContactTheHost")) {

                    Button_Cancel.setVisibility(View.VISIBLE);

                }


            } else if (getIntent().getExtras().getString("Purpose").equals("Notification")) {

                Button_InviteSplitter.setVisibility(View.GONE);
                if (AppData.FromButton.equals("ContactTheHost")) {

                    Button_Cancel.setVisibility(View.GONE);

                }

            } else {

                Button_InviteSplitter.setVisibility(View.VISIBLE);
                if (AppData.FromButton.equals("ContactTheHost")) {

                    Button_Cancel.setVisibility(View.GONE);

                }

            }

        } catch (Exception e) {

            Button_InviteSplitter.setVisibility(View.GONE);
            if (AppData.FromButton.equals("ContactTheHost")) {

                Button_Cancel.setVisibility(View.GONE);

            }

        }


        done = (LinearLayout) findViewById(R.id.done);

        MaximumAvailableSeat.setText(String.valueOf(AppData.NumberOfAvailableSeat) + "/" + String.valueOf(AppData.MaximumTotalMember));
        //AccomodateText.setText(String.valueOf(AppData.MaximumTotalMember));


        if (AppData.NumberOfAvailableSeat > 1) {

            try {

                if (getIntent().getExtras().getString("Purpose").equals("Edit")) {

                    Button_InviteSplitter.setVisibility(View.VISIBLE);


                    if (AppData.FromButton.equals("ContactTheHost")) {

                        Button_Cancel.setVisibility(View.VISIBLE);

                    }

                } else if (getIntent().getExtras().getString("Purpose").equals("Notification")) {

                    Button_InviteSplitter.setVisibility(View.GONE);
                    if (AppData.FromButton.equals("ContactTheHost")) {

                        Button_Cancel.setVisibility(View.GONE);

                    }

                } else {

                    Button_InviteSplitter.setVisibility(View.VISIBLE);
                    if (AppData.FromButton.equals("ContactTheHost")) {

                        Button_Cancel.setVisibility(View.GONE);

                    }

                }

            } catch (Exception e) {

                Button_InviteSplitter.setVisibility(View.VISIBLE);
                if (AppData.FromButton.equals("ContactTheHost")) {

                    Button_Cancel.setVisibility(View.GONE);

                }

            }


        } else {

            Button_InviteSplitter.setVisibility(View.GONE);

        }

        total = AppData.NumberOfAvailableSeat;
//        try {
//
//            if (getIntent().getExtras().getString("FromButton").equals("ContactTheHost")){
//
//                ContactTheHostLayout.setVisibility(View.VISIBLE);
//                BookingAsHostLayout.setVisibility(View.GONE);
//                contact_block.setVisibility(View.VISIBLE);
//
//            }else if (getIntent().getExtras().getString("FromButton").equals("HostTheTable")){
//
//                ContactTheHostLayout.setVisibility(View.GONE);
//                BookingAsHostLayout.setVisibility(View.VISIBLE);
//                contact_block.setVisibility(View.VISIBLE);
//
//            }
//
//        }catch (Exception e){
//
//
//
//        }


        if (AppData.FromButton.equals("ContactTheHost")) {

            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

//            String Name = sharedPreferences.getString("first_name", "").substring(0, 1).toUpperCase() + sharedPreferences.getString("first_name", "").substring(1);
//            String SurName = sharedPreferences.getString("last_name", "").substring(0, 1).toUpperCase() + sharedPreferences.getString("last_name", "").substring(1);

            try {

                if (getIntent().getExtras().getString("Purpose").equals("Edit") || getIntent().getExtras().getString("Purpose").equals("Notification")) {

                    HeaderText.setText("SAVE");

                } else {

                    HeaderText.setText("Contact the Host" + " - " + AppData.HostName);

                }

            } catch (Exception e) {

                HeaderText.setText("Contact the Host" + " - " + AppData.HostName);

            }


        } else if (AppData.FromButton.equals("HostTheTable")) {

            HeaderText.setText("Booking as a Host");

        }

/*
        seek_bar1 = (SeekBar) findViewById(R.id.seek_bar1);
        seek_bar2 = (SeekBar) findViewById(R.id.seek_bar2);*/

        male_no = (TextView) findViewById(R.id.male_no);
        female_no = (TextView) findViewById(R.id.female_no);

        female_no.setText("0");
        male_no.setText("0");

        //seek_bar1.setMax(AppData.NumberOfAvailableSeat - 1);
        // seek_bar2.setMax(AppData.NumberOfAvailableSeat - 1);

/*        seek_bar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                temp = progress;
                MaleProgress = progress;

                if (MaleProgress + FemaleProgress == AppData.NumberOfAvailableSeat - 1) {

                    Button_InviteSplitter.setVisibility(View.GONE);
                    Button_InviteSplitter.setEnabled(false);
                    Button_InviteSplitter.setClickable(false);

                    if (AppData.FromButton.equals("HostTheTable")) {

                        TExt_TableAmount.setText(String.valueOf(AppData.MaximumAmount));

                    }



                } else {

                    Button_InviteSplitter.setVisibility(View.VISIBLE);
                    Button_InviteSplitter.setBackground(getResources().getDrawable(R.drawable.red_mold_rectangle));
                    Button_InviteSplitter.setEnabled(true);
                    Button_InviteSplitter.setClickable(true);

                    if (AppData.FromButton.equals("HostTheTable")){

                        TExt_TableAmount.setText(String.valueOf(AppData.SelectedAmount));

                    }



                }

                //Toast.makeText(getActivity(),""+temp,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                if ((MaleProgress + FemaleProgress) > (total)) {

                    seek_bar2.setProgress(total - temp);
                    male_no.setText("" + (total - temp));
                    female_no.setText("" + temp);

                } else {

                    male_no.setText("" + temp);

                }


                GuestCount.setText(String.valueOf(Integer.parseInt(male_no.getText().toString()) + Integer.parseInt(female_no.getText().toString())));


            }
        });*/

/*        seek_bar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                temp = progress;
                FemaleProgress = progress;
                // Toast.makeText(getActivity(),""+temp,Toast.LENGTH_SHORT).show();

                if (MaleProgress + FemaleProgress == AppData.NumberOfAvailableSeat - 1) {

                    Button_InviteSplitter.setVisibility(View.GONE);
                    Button_InviteSplitter.setEnabled(false);
                    Button_InviteSplitter.setClickable(false);
                    TExt_TableAmount.setText(String.valueOf(AppData.MaximumAmount));

                } else {

                    Button_InviteSplitter.setVisibility(View.VISIBLE);
                    Button_InviteSplitter.setBackground(getResources().getDrawable(R.drawable.red_mold_rectangle));
                    Button_InviteSplitter.setEnabled(true);
                    Button_InviteSplitter.setClickable(true);
                    TExt_TableAmount.setText(String.valueOf(AppData.SelectedAmount));

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                if ((MaleProgress + FemaleProgress) > (total)) {

                    seek_bar1.setProgress(total - temp);
                    female_no.setText("" + (total - temp));
                    male_no.setText("" + temp);

                } else {

                    female_no.setText("" + temp);

                }


                GuestCount.setText(String.valueOf(Integer.parseInt(male_no.getText().toString()) + Integer.parseInt(female_no.getText().toString())));


            }
        });*/

        male_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MalePlus = true;

                Log.d("Count-F-Progress", "=" + Fprogress);
                Log.d("Count-M-Progress", "=" + Mprogress);
                Log.d("Count-Total", "=" + total);
                Log.d("Count-NumberOfAvailableSeat", "=" + AppData.NumberOfAvailableSeat);
                Log.d("Count-MaximumTotalMember", "=" + AppData.MaximumTotalMember);
                Log.d("Count-MaximumTotalMember", "=" + AvailableSeatCount);
                Log.d("Count-End", "------------------------");


                if (Mprogress < total) {
                    Mprogress++;

                    if (Mprogress + Fprogress > total) {

                        if (Fprogress > 0) {
                            Fprogress--;
                        }

                    }

                }

                Log.d("Count-F-Progress", "=" + Fprogress);
                Log.d("Count-M-Progress", "=" + Mprogress);
                Log.d("Count-Total", "=" + total);
                Log.d("Count-NumberOfAvailableSeat", "=" + AppData.NumberOfAvailableSeat);
                Log.d("Count-MaximumTotalMember", "=" + AppData.MaximumTotalMember);
                Log.d("Count-MaximumTotalMember", "=" + AvailableSeatCount);
                Log.d("Count-End", "------------------------");

                temp = Mprogress;
                MaleProgress = Mprogress;
                FemaleProgress = Fprogress;

                if (AvailableSeatCount - 1 <= 0) {

                    AvailableSeatCount = 0;

                    MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));


                    Button_InviteSplitter.setVisibility(View.GONE);
                    Button_InviteSplitter.setEnabled(false);
                    Button_InviteSplitter.setClickable(false);

                    if (AppData.FromButton.equals("HostTheTable")) {

                        TExt_TableAmount.setText(String.valueOf(AppData.MaximumAmount));

                    }


                } else {

                    //  Log.d("male_plus","-else");

                    AvailableSeatCount = AvailableSeatCount - 1;

                    MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));

                    try {

                        if (getIntent().getExtras().getString("Purpose").equals("Edit")) {

                            Button_InviteSplitter.setVisibility(View.VISIBLE);
                            if (AppData.FromButton.equals("ContactTheHost")) {

                                Button_Cancel.setVisibility(View.VISIBLE);

                            }

                        } else if (getIntent().getExtras().getString("Purpose").equals("Notification")) {

                            Button_InviteSplitter.setVisibility(View.GONE);
                            if (AppData.FromButton.equals("ContactTheHost")) {

                                Button_Cancel.setVisibility(View.GONE);

                            }

                        } else {

                            Button_InviteSplitter.setVisibility(View.VISIBLE);
                            if (AppData.FromButton.equals("ContactTheHost")) {

                                Button_Cancel.setVisibility(View.GONE);

                            }

                        }

                    } catch (Exception e) {

                        Button_InviteSplitter.setVisibility(View.VISIBLE);
                        if (AppData.FromButton.equals("ContactTheHost")) {

                            Button_Cancel.setVisibility(View.GONE);

                        }

                    }

                    Button_InviteSplitter.setBackground(getResources().getDrawable(R.drawable.red_mold_rectangle));
                    Button_InviteSplitter.setEnabled(true);
                    Button_InviteSplitter.setClickable(true);

                    if (AppData.FromButton.equals("HostTheTable")) {

                        TExt_TableAmount.setText(String.valueOf(AppData.SelectedAmount));

                    }

                }


                Log.d("Count-MaleProgress", "=" + MaleProgress);
                Log.d("Count-FemaleProgress", "=" + FemaleProgress);
                Log.d("Count-total", "=" + total);
                Log.d("Count-temp", "=" + temp);


                male_no.setText("" + (MaleProgress));
                female_no.setText("" + FemaleProgress);

//                if ((MaleProgress + FemaleProgress) > (total)) {
//
//                  //  Log.d("M+F",">total");
//
//
//                    female_no.setText("" + (total - temp));
//                    male_no .setText("" + temp);
//
//                } else {
//                 //   Log.d("M+F","<total");
//
//                    male_no.setText("" + temp);
//
//                }

                GuestCount.setText(String.valueOf(Integer.parseInt(male_no.getText().toString()) + Integer.parseInt(female_no.getText().toString())));


            }
        });

        male_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Count-F-Progress", "=" + Fprogress);
                Log.d("Count-M-Progress", "=" + Mprogress);
                Log.d("Count-Total", "=" + total);
                Log.d("Count-NumberOfAvailableSeat", "=" + AppData.NumberOfAvailableSeat);
                Log.d("Count-MaximumTotalMember", "=" + AppData.MaximumTotalMember);
                Log.d("Count-MaximumTotalMember", "=" + AvailableSeatCount);
                Log.d("Count-End", "------------------------");


                MaleMinus = true;

                if (Mprogress > 0) {
                    Mprogress--;


//                    if(Fprogress==0){
//                        Fprogress=0;
//                    }else if(Fprogress<=total){
//                        Fprogress++;
//                    }
                }

                Log.d("Count-F-Progress", "=" + Fprogress);
                Log.d("Count-M-Progress", "=" + Mprogress);
                Log.d("Count-Total", "=" + total);
                Log.d("Count-NumberOfAvailableSeat", "=" + AppData.NumberOfAvailableSeat);
                Log.d("Count-MaximumTotalMember", "=" + AppData.MaximumTotalMember);
                Log.d("Count-MaximumTotalMember", "=" + AvailableSeatCount);
                Log.d("Count-End", "------------------------");

                temp = Mprogress;
                MaleProgress = Mprogress;
                FemaleProgress = Fprogress;

                if (Mprogress >= 0) {
                    if (AvailableSeatCount + 1 >= AppData.NumberOfAvailableSeat) {

                        AvailableSeatCount = AppData.NumberOfAvailableSeat;

                        MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));

                        Button_InviteSplitter.setVisibility(View.GONE);
                        Button_InviteSplitter.setEnabled(false);
                        Button_InviteSplitter.setClickable(false);

                        if (AppData.FromButton.equals("HostTheTable")) {

                            TExt_TableAmount.setText(String.valueOf(AppData.MaximumAmount));

                        }


                    } else {

                        if (Mprogress > 0) {

                            AvailableSeatCount = AvailableSeatCount + 1;

                            MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));

                        } else if (Mprogress == 0) {

                            if (Fprogress == 0) {

                                AvailableSeatCount = AppData.NumberOfAvailableSeat;

                                MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));

                            } else {

                                AvailableSeatCount = AvailableSeatCount + 1;

                                MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));

                            }


                        }

                        try {

                            if (getIntent().getExtras().getString("Purpose").equals("Edit")) {

                                Button_InviteSplitter.setVisibility(View.VISIBLE);
                                if (AppData.FromButton.equals("ContactTheHost")) {

                                    Button_Cancel.setVisibility(View.VISIBLE);

                                }

                            } else if (getIntent().getExtras().getString("Purpose").equals("Notification")) {

                                Button_InviteSplitter.setVisibility(View.GONE);
                                if (AppData.FromButton.equals("ContactTheHost")) {

                                    Button_Cancel.setVisibility(View.GONE);

                                }

                            } else {

                                Button_InviteSplitter.setVisibility(View.VISIBLE);
                                if (AppData.FromButton.equals("ContactTheHost")) {

                                    Button_Cancel.setVisibility(View.GONE);

                                }

                            }

                        } catch (Exception e) {

                            Button_InviteSplitter.setVisibility(View.VISIBLE);
                            if (AppData.FromButton.equals("ContactTheHost")) {

                                Button_Cancel.setVisibility(View.GONE);

                            }

                        }

                        Button_InviteSplitter.setBackground(getResources().getDrawable(R.drawable.red_mold_rectangle));
                        Button_InviteSplitter.setEnabled(true);
                        Button_InviteSplitter.setClickable(true);

                        if (AppData.FromButton.equals("HostTheTable")) {

                            TExt_TableAmount.setText(String.valueOf(AppData.SelectedAmount));

                        }


                    }


                    Log.d("Count-MaleProgress", "=" + MaleProgress);
                    Log.d("Count-FemaleProgress", "=" + FemaleProgress);
                    Log.d("Count-total", "=" + total);
                    Log.d("Count-temp", "=" + temp);

                    male_no.setText("" + (MaleProgress));
                    female_no.setText("" + FemaleProgress);

//                    if ((MaleProgress + FemaleProgress) > (total)) {
//
//                        female_no.setText("" + (total - temp));
//                        male_no.setText("" + temp);
//
//                    } else {
//
//                        male_no.setText("" + temp);
//
//                    }

                    GuestCount.setText(String.valueOf(Integer.parseInt(male_no.getText().toString()) + Integer.parseInt(female_no.getText().toString())));
                }

            }
        });

        female_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FemalePlus = true;

                Log.d("Count-F-Progress", "=" + Fprogress);
                Log.d("Count-M-Progress", "=" + Mprogress);
                Log.d("Count-Total", "=" + total);
                Log.d("Count-NumberOfAvailableSeat", "=" + AppData.NumberOfAvailableSeat);
                Log.d("Count-MaximumTotalMember", "=" + AppData.MaximumTotalMember);
                Log.d("Count-MaximumTotalMember", "=" + AvailableSeatCount);
                Log.d("Count-End", "------------------------");


                if (Fprogress < total) {
                    Fprogress++;

                    if (Fprogress + Mprogress > total) {
                        if (Mprogress > 0) {
                            Mprogress--;
                        }

                    }

                }

                Log.d("Count-F-Progress", "=" + Fprogress);
                Log.d("Count-M-Progress", "=" + Mprogress);
                Log.d("Count-Total", "=" + total);
                Log.d("Count-NumberOfAvailableSeat", "=" + AppData.NumberOfAvailableSeat);
                Log.d("Count-MaximumTotalMember", "=" + AppData.MaximumTotalMember);
                Log.d("Count-MaximumTotalMember", "=" + AvailableSeatCount);
                Log.d("Count-End", "------------------------");


                temp = Fprogress;
                FemaleProgress = Fprogress;
                MaleProgress = Mprogress;

                if (AvailableSeatCount - 1 <= 0) {

                    AvailableSeatCount = 0;

                    MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));

                    Button_InviteSplitter.setVisibility(View.GONE);
                    Button_InviteSplitter.setEnabled(false);
                    Button_InviteSplitter.setClickable(false);

                    if (AppData.FromButton.equals("HostTheTable")) {

                        TExt_TableAmount.setText(String.valueOf(AppData.MaximumAmount));

                    }


                } else {

                    AvailableSeatCount = AvailableSeatCount - 1;

                    MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));


                    try {

                        if (getIntent().getExtras().getString("Purpose").equals("Edit")) {

                            Button_InviteSplitter.setVisibility(View.VISIBLE);
                            if (AppData.FromButton.equals("ContactTheHost")) {

                                Button_Cancel.setVisibility(View.VISIBLE);

                            }

                        } else if (getIntent().getExtras().getString("Purpose").equals("Notification")) {

                            Button_InviteSplitter.setVisibility(View.GONE);
                            if (AppData.FromButton.equals("ContactTheHost")) {

                                Button_Cancel.setVisibility(View.GONE);

                            }

                        } else {

                            Button_InviteSplitter.setVisibility(View.VISIBLE);
                            if (AppData.FromButton.equals("ContactTheHost")) {

                                Button_Cancel.setVisibility(View.GONE);

                            }

                        }

                    } catch (Exception e) {

                        Button_InviteSplitter.setVisibility(View.VISIBLE);
                        if (AppData.FromButton.equals("ContactTheHost")) {

                            Button_Cancel.setVisibility(View.GONE);

                        }

                    }

                    Button_InviteSplitter.setBackground(getResources().getDrawable(R.drawable.red_mold_rectangle));
                    Button_InviteSplitter.setEnabled(true);
                    Button_InviteSplitter.setClickable(true);
                    if (AppData.FromButton.equals("HostTheTable")) {

                        TExt_TableAmount.setText(String.valueOf(AppData.SelectedAmount));

                    }


                }

                Log.d("Count-MaleProgress", "=" + MaleProgress);
                Log.d("Count-FemaleProgress", "=" + FemaleProgress);
                Log.d("Count-total", "=" + total);
                Log.d("Count-temp", "=" + temp);


                male_no.setText("" + (MaleProgress));
                female_no.setText("" + FemaleProgress);

//                if ((MaleProgress + FemaleProgress) > (total)) {
//
//                    male_no.setText("" + (total - temp));
//                    female_no.setText("" + temp);
//
//                } else {
//
//                    female_no.setText("" + temp);
//
//                }


                GuestCount.setText(String.valueOf(Integer.parseInt(male_no.getText().toString()) + Integer.parseInt(female_no.getText().toString())));

            }

        });

        female_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FemaleMinus = true;

                Log.d("Count-F-Progress", "=" + Fprogress);
                Log.d("Count-M-Progress", "=" + Mprogress);
                Log.d("Count-Total", "=" + total);
                Log.d("Count-NumberOfAvailableSeat", "=" + AppData.NumberOfAvailableSeat);
                Log.d("Count-MaximumTotalMember", "=" + AppData.MaximumTotalMember);
                Log.d("Count-MaximumTotalMember", "=" + AvailableSeatCount);
                Log.d("Count-End", "------------------------");

                if (Fprogress > 0) {
                    Fprogress--;

//                    if(Mprogress==0){
//                        Mprogress=0;
//                    }else if (Mprogress <= total) {
//                        Mprogress++;
//                    }
                }

                Log.d("Count-F-Progress", "=" + Fprogress);
                Log.d("Count-M-Progress", "=" + Mprogress);
                Log.d("Count-Total", "=" + total);
                Log.d("Count-NumberOfAvailableSeat", "=" + AppData.NumberOfAvailableSeat);
                Log.d("Count-MaximumTotalMember", "=" + AppData.MaximumTotalMember);
                Log.d("Count-MaximumTotalMember", "=" + AvailableSeatCount);
                Log.d("Count-End", "------------------------");

                temp = Fprogress;
                FemaleProgress = Fprogress;
                MaleProgress = Mprogress;

                if (AvailableSeatCount + 1 >= AppData.NumberOfAvailableSeat) {

                    AvailableSeatCount = AppData.NumberOfAvailableSeat;

                    MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));

                    Button_InviteSplitter.setVisibility(View.GONE);
                    Button_InviteSplitter.setEnabled(false);
                    Button_InviteSplitter.setClickable(false);

                    if (AppData.FromButton.equals("HostTheTable")) {

                        TExt_TableAmount.setText(String.valueOf(AppData.MaximumAmount));

                    }


                } else {

                    if (Fprogress > 0) {

                        AvailableSeatCount = AvailableSeatCount + 1;

                        MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));

                    } else if (Fprogress == 0) {

                        if (Mprogress == 0) {

                            AvailableSeatCount = AppData.NumberOfAvailableSeat;

                            MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));

                        } else {

                            AvailableSeatCount = AvailableSeatCount + 1;

                            MaximumAvailableSeat.setText(String.valueOf(AvailableSeatCount) + "/" + String.valueOf(AppData.MaximumTotalMember));

                        }

                    }

                    try {

                        if (getIntent().getExtras().getString("Purpose").equals("Edit")) {

                            Button_InviteSplitter.setVisibility(View.VISIBLE);
                            if (AppData.FromButton.equals("ContactTheHost")) {

                                Button_Cancel.setVisibility(View.VISIBLE);

                            }

                        } else if (getIntent().getExtras().getString("Purpose").equals("Notification")) {

                            Button_InviteSplitter.setVisibility(View.GONE);
                            if (AppData.FromButton.equals("ContactTheHost")) {

                                Button_Cancel.setVisibility(View.GONE);

                            }

                        } else {

                            Button_InviteSplitter.setVisibility(View.VISIBLE);
                            if (AppData.FromButton.equals("ContactTheHost")) {

                                Button_Cancel.setVisibility(View.GONE);

                            }

                        }

                    } catch (Exception e) {

                        Button_InviteSplitter.setVisibility(View.VISIBLE);
                        if (AppData.FromButton.equals("ContactTheHost")) {

                            Button_Cancel.setVisibility(View.GONE);

                        }

                    }

                    Button_InviteSplitter.setBackground(getResources().getDrawable(R.drawable.red_mold_rectangle));
                    Button_InviteSplitter.setEnabled(true);
                    Button_InviteSplitter.setClickable(true);

                    if (AppData.FromButton.equals("HostTheTable")) {

                        TExt_TableAmount.setText(String.valueOf(AppData.SelectedAmount));

                    }


                }


                Log.d("Count-MaleProgress", "=" + MaleProgress);
                Log.d("Count-FemaleProgress", "=" + FemaleProgress);
                Log.d("Count-total", "=" + total);
                Log.d("Count-temp", "=" + temp);

                male_no.setText("" + (MaleProgress));
                female_no.setText("" + FemaleProgress);

//                if ((MaleProgress + FemaleProgress) > (total)) {
//
//                    male_no.setText("" + (total - temp));
//                    female_no.setText("" + temp);
//
//                } else {
//
//                    female_no.setText("" + temp);
//
//                }


                GuestCount.setText(String.valueOf(Integer.parseInt(male_no.getText().toString()) + Integer.parseInt(female_no.getText().toString())));


            }
        });


        Button_Back = (LinearLayout) findViewById(R.id.ll_back_bookingashost);


        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < AppData.contactVOList.size(); i++) {

                    AppData.contactVOList.get(i).setCheckStatus(false);

                }

                finish();

            }
        });


        Button_InviteSplitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (AppData.contactVOList != null) {

                    if (AppData.contactVOList.size() > 0) {

                        if (AppData.ContactSynced) {

                            Intent intent = new Intent(BookingAsHost.this, ContactPage.class);
                            startActivity(intent);

                        } else {

                            Toast.makeText(BookingAsHost.this, "Contact syncing is going on!", Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(BookingAsHost.this, "Contact syncing is going on!", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(BookingAsHost.this, "Contact syncing is going on!", Toast.LENGTH_SHORT).show();

                }


            }
        });

        if (AppData.FromButton.equals("ContactTheHost")) {

            try {

                if (getIntent().getExtras().getString("Purpose").equals("Notification")) {

                    Button_Authorize.setVisibility(View.VISIBLE);

                    Button_Authorize.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.v("MaleCount", male_no.getText().toString().trim());
                            Log.v("MaleCount", Booked_MaleCount);
                            Log.v("MaleCount", female_no.getText().toString().trim());
                            Log.v("MaleCount", Booked_FemaleCount);
                            Log.v("MaleCount", TExt_TableAmount.getText().toString().trim());
                            Log.v("MaleCount", String.valueOf(AppData.MinimumAmount));


                            if (Integer.parseInt(male_no.getText().toString().trim()) == Integer.parseInt(Booked_MaleCount) && Integer.parseInt(female_no.getText().toString().trim()) == Integer.parseInt(Booked_FemaleCount) && Integer.parseInt(TExt_TableAmount.getText().toString().trim()) == AppData.MinimumAmount) {

                                Intent intent = new Intent(BookingAsHost.this, SplitPercentage.class);
                                intent.putExtra("PersonCount", String.valueOf(Integer.parseInt(male_no.getText().toString()) + Integer.parseInt(female_no.getText().toString())));
                                intent.putExtra("Amount", String.valueOf(AppData.MinimumAmount));
                                AppData.FromButton = "ContactTheHost";
                                startActivity(intent);

                            } else {

                                //open a dialog.........

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookingAsHost.this);

                                alertDialog.setMessage("You have edited your booking details.Do you want to notify the host with the new details");

                                // Setting Icon to Dialog
                                //alertDialog.setIcon(R.drawable.delete);

                                // Setting Positive "Yes" Button
                                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        // Write your code here to invoke YES event
                                        //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                        Vol_EditBooking();

                                    }
                                });

                                // Setting Negative "NO" Button
                                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to invoke NO event
                                        //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(BookingAsHost.this, SplitPercentage.class);
                                        intent.putExtra("PersonCount", String.valueOf(Integer.parseInt(male_no.getText().toString()) + Integer.parseInt(female_no.getText().toString())));
                                        intent.putExtra("Amount", String.valueOf(AppData.MinimumAmount));
                                        AppData.FromButton = "ContactTheHost";
                                        startActivity(intent);
                                    }
                                });

                                // Showing Alert Message
                                alertDialog.show();

                            }


                        }
                    });

                } else {

                    Button_Authorize.setVisibility(View.GONE);

                }

            } catch (Exception e) {

                Button_Authorize.setVisibility(View.GONE);

            }

            Button_ViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(BookingAsHost.this, EventDetails.class);
                    intent.putExtra("FromPrevious", true);
                    AppData.FromPage = "Booking";
                    startActivity(intent);

                }
            });

        }


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppData.MaleCount = male_no.getText().toString().trim();
                AppData.FemaleCount = female_no.getText().toString().trim();
                if (AppData.MaleCount.equals("0")) {

                    if (AppData.FemaleCount.equals("0")) {

                        Toast.makeText(BookingAsHost.this, "Please select atleast one male or female", Toast.LENGTH_SHORT).show();

                    } else {



                            if (AppData.FromButton.equals("ContactTheHost")) {

                                try {

                                    if (getIntent().getExtras().getString("Purpose").equals("Edit") || getIntent().getExtras().getString("Purpose").equals("Notification")) {

                                        //Fire Edit Url.............

                                        if (AppData.RateChanged || MalePlus || MaleMinus || FemalePlus || FemaleMinus) {

                                            Vol_EditBooking();

                                        } else {
                                            Toast.makeText(BookingAsHost.this, "First edit any amount", Toast.LENGTH_SHORT).show();
                                        }


                                    } else {


                                        Vol_Pay();


                                    }

                                } catch (Exception e) {


                                    Vol_Pay();


                                }


                            } else if (AppData.FromButton.equals("HostTheTable")) {

                                if (Integer.parseInt(TExt_TableAmount.getText().toString()) == 0) {

                                    Toast.makeText(BookingAsHost.this, "Please enter your offer amount", Toast.LENGTH_SHORT).show();

                                } else {

                                Log.v("Selected Amount::", TExt_TableAmount.getText().toString().trim());
                                Log.v("Selected Minimum Amount::", String.valueOf(AppData.MinimumAmount));

                                AppData.Amount = TExt_TableAmount.getText().toString().trim();

                                if (AppData.MinimumAmount == Integer.parseInt(TExt_TableAmount.getText().toString().trim())) {


                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookingAsHost.this);

                                    // Setting Dialog Title
                                    //alertDialog.setTitle("Confirm Delete...");

                                    // Setting Dialog Message
                                    alertDialog.setMessage("Are you sure you only want to paid the Min Deposit? You will be required to paid Additional amount later on this split");

                                    // Setting Icon to Dialog
                                    //alertDialog.setIcon(R.drawable.delete);

                                    // Setting Positive "Yes" Button
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            // Write your code here to invoke YES event
                                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(BookingAsHost.this, SplitPercentage.class);
                                            intent.putExtra("PersonCount", String.valueOf(Integer.parseInt(AppData.MaleCount) + Integer.parseInt(AppData.FemaleCount)));
                                            intent.putExtra("Amount", TExt_TableAmount.getText().toString());
                                            intent.putExtra("VenueName", getIntent().getExtras().getString("VenueName"));
                                            startActivity(intent);
                                        }
                                    });

                                    // Setting Negative "NO" Button
                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Write your code here to invoke NO event
                                            //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });

                                    // Showing Alert Message
                                    alertDialog.show();

                                } else {

                                    Intent intent = new Intent(BookingAsHost.this, SplitPercentage.class);
                                    intent.putExtra("PersonCount", String.valueOf(Integer.parseInt(AppData.MaleCount) + Integer.parseInt(AppData.FemaleCount)));
                                    intent.putExtra("Amount", TExt_TableAmount.getText().toString());
                                    intent.putExtra("VenueName", getIntent().getExtras().getString("VenueName"));
                                    startActivity(intent);

                                }


                            }

                            }



                    }

                } else {


                        if (AppData.FromButton.equals("ContactTheHost")) {

                            try {

                                if (getIntent().getExtras().getString("Purpose").equals("Edit") || getIntent().getExtras().getString("Purpose").equals("Notification")) {

                                    //Fire Edit Url.............

                                    if (AppData.RateChanged || MalePlus || MaleMinus || FemalePlus || FemaleMinus) {

                                        Vol_EditBooking();

                                    } else {
                                        Toast.makeText(BookingAsHost.this, "First edit any amount", Toast.LENGTH_SHORT).show();
                                    }

                                } else {

                                    Vol_Pay();

                                }

                            } catch (Exception e) {

                                Vol_Pay();

                            }


                        } else if (AppData.FromButton.equals("HostTheTable")) {

                            if (Integer.parseInt(TExt_TableAmount.getText().toString()) == 0) {

                                Toast.makeText(BookingAsHost.this, "Please enter your offer amount", Toast.LENGTH_SHORT).show();

                            } else {

                            Log.v("Selected Amount::", TExt_TableAmount.getText().toString().trim());
                            Log.v("Selected Minimum Amount::", String.valueOf(AppData.MinimumAmount));

                            AppData.Amount = TExt_TableAmount.getText().toString().trim();

                            if (AppData.MinimumAmount == Integer.parseInt(TExt_TableAmount.getText().toString().trim())) {


                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookingAsHost.this);

                                // Setting Dialog Title
                                //alertDialog.setTitle("Confirm Delete...");

                                // Setting Dialog Message
                                alertDialog.setMessage("Are you sure you only want to paid the Min Deposit? You will be required to paid Additional amount later on this split");

                                // Setting Icon to Dialog
                                //alertDialog.setIcon(R.drawable.delete);

                                // Setting Positive "Yes" Button
                                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        // Write your code here to invoke YES event
                                        //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(BookingAsHost.this, SplitPercentage.class);
                                        intent.putExtra("PersonCount", String.valueOf(Integer.parseInt(AppData.MaleCount) + Integer.parseInt(AppData.FemaleCount)));
                                        intent.putExtra("Amount", TExt_TableAmount.getText().toString());
                                        startActivity(intent);
                                    }
                                });

                                // Setting Negative "NO" Button
                                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to invoke NO event
                                        //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });

                                // Showing Alert Message
                                alertDialog.show();

                            } else {

                                Intent intent = new Intent(BookingAsHost.this, SplitPercentage.class);
                                intent.putExtra("PersonCount", String.valueOf(Integer.parseInt(AppData.MaleCount) + Integer.parseInt(AppData.FemaleCount)));
                                intent.putExtra("Amount", TExt_TableAmount.getText().toString());
                                startActivity(intent);

                            }

//                            Intent intent = new Intent(BookingAsHost.this, SplitPercentage.class);
//                            intent.putExtra("PersonCount",String.valueOf(Integer.parseInt(AppData.MaleCount)+Integer.parseInt(AppData.FemaleCount)));
//                            intent.putExtra("Amount",TExt_TableAmount.getText().toString());
//                            startActivity(intent);

                        }

                        }




                }


            }
        });


        if (AppData.FromButton.equals("ContactTheHost")) {

            MembersDetails();


            Button_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

                    if (AppData.HostId.equals(sharedPreferences.getString("UserId", ""))) {

                        //User is host....

                        if (AcceptedMeberIsAvailableOrNot) {

                            if (AcceptedMember != null) {

                                if (AcceptedMember.size() > 0) {

                                    if (AcceptedMember.size() > 1) {

                                        //More than 1 members are accepted.....
                                        //Open all accepted members list and fire the url with the choosen memberid as changeHostId.......


                                        LinearLayout Button_Cross;
                                        RecyclerView AmountList;
                                        TextView Dialog_HeaderText;

                                        View view1 = LayoutInflater.from(BookingAsHost.this).inflate(R.layout.dialog_bookasahost_advancelist, null);
                                        final Dialog dialog = new Dialog(BookingAsHost.this, R.style.MaterialDialogSheet);
                                        dialog.setContentView(view1);
                                        dialog.setCancelable(true);
                                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                        dialog.getWindow().setGravity(Gravity.CENTER);
                                        dialog.show();


                                        Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_back_dialog_bookingasahost);

                                        Dialog_HeaderText = (TextView) view1.findViewById(R.id.tv_dialog_header);

                                        Dialog_HeaderText.setText("Select Host");

                                        AmountList = (RecyclerView) view1.findViewById(R.id.rv_bookingasahost_advanceList);
                                        AmountList.setHasFixedSize(true);
                                        AmountList.setLayoutManager(new LinearLayoutManager(BookingAsHost.this));

                                        Dialog_AdvanceList_Counter_Adapter dialog_advanceList_counter_adapter = new Dialog_AdvanceList_Counter_Adapter("BookingAsHost", BookingAsHost.this, AcceptedMember, dialog);
                                        AmountList.setAdapter(dialog_advanceList_counter_adapter);


                                        Button_Cross.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                dialog.dismiss();

                                            }
                                        });


                                    } else {

                                        //Only 1 member is accepted.....
                                        //fire the url with accpted member is as changeHostId.....


                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookingAsHost.this);

                                        alertDialog.setMessage("Are you sure you want to cancel the booking?");

                                        // Setting Icon to Dialog
                                        //alertDialog.setIcon(R.drawable.delete);

                                        // Setting Positive "Yes" Button
                                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // Write your code here to invoke YES event
                                                //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                                Vol_CancelBooking(AcceptedMember.get(0).getHostId());
                                                dialog.dismiss();
                                            }
                                        });

                                        // Setting Negative "NO" Button
                                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Write your code here to invoke NO event
                                                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        });

                                        // Showing Alert Message
                                        alertDialog.show();


                                    }

                                }

                            }

                        } else {

                            //No member accepted yet.....
                            ////Fire the url with changeHostId as blank....

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookingAsHost.this);

                            alertDialog.setMessage("Are you sure you want to cancel the booking?");

                            // Setting Icon to Dialog
                            //alertDialog.setIcon(R.drawable.delete);

                            // Setting Positive "Yes" Button
                            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    // Write your code here to invoke YES event
                                    //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                    Vol_CancelBooking("");
                                    dialog.dismiss();
                                }
                            });

                            // Setting Negative "NO" Button
                            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to invoke NO event
                                    //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();

                        }


                    } else {

                        //User is not host....
                        //Fire the url with changeHostId as blank....

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookingAsHost.this);

                        alertDialog.setMessage("Are you sure you want to cancel the booking?");

                        // Setting Icon to Dialog
                        //alertDialog.setIcon(R.drawable.delete);

                        // Setting Positive "Yes" Button
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // Write your code here to invoke YES event
                                //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                Vol_CancelBooking("");
                                dialog.dismiss();
                            }
                        });

                        // Setting Negative "NO" Button
                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to invoke NO event
                                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                        // Showing Alert Message
                        alertDialog.show();

                    }

                }
            });

        }


        if (AppData.FromButton.equals("HostTheTable")) {

            Button_Promoter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LinearLayout Button_Cross;

                    RecyclerView PromoterList;


                    View view1 = LayoutInflater.from(BookingAsHost.this).inflate(R.layout.dialog_promoter, null);
                    dialog = new Dialog(BookingAsHost.this, R.style.MaterialDialogSheet);
                    dialog.setContentView(view1);
                    dialog.setCancelable(true);
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    dialog.show();


                    Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_back);

                    PromoterList = (RecyclerView) view1.findViewById(R.id.rv_promoter);

                    PromoterList.setHasFixedSize(true);
                    PromoterList.setLayoutManager(new LinearLayoutManager(BookingAsHost.this));

                    PromoterList_Adapter promoterList_adapter = new PromoterList_Adapter(BookingAsHost.this, BookingAsHost.this);

                    PromoterList.setAdapter(promoterList_adapter);


                    Button_Cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                        }
                    });

                }
            });

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.v("BookingAsHost:::", "onResume");
        Log.v("BookingAsHost:::", "AppData.FromEdit::" + AppData.FromEdit);
        Log.v("BookingAsHost:::", "AppData.Contact_Block::" + AppData.Contact_Block);

        AppData.PhoneSelectectedContacts = new ArrayList<Phone_Selected_SetterGetter>();
        AppData.SplitterSelectedContacts = new ArrayList<Splitter_Selected_SetterGetter>();

        AppData.PhoneSelectectedContacts.clear();
        AppData.SplitterSelectedContacts.clear();

        if (AppData.selectedContact != null) {

            Log.v("BookingAsHost:::", "AppData.selectedContact::" + AppData.selectedContact.size());
            Log.v("BookingAsHost:::", "AppData.selectedContact::" + AppData.selectedContact);

        }


        if (AppData.FromEdit.equals("Yes")) {

            if (AppData.Contact_Block) {

                String phone = null;
                String name = null;

                if (AppData.selectedContact != null) {
                    if (AppData.selectedContact.size() > 0) {

                        Log.v("SelectedContact_Lenth", String.valueOf(AppData.selectedContact.size()));
                        Log.v("SelectedContact_Lenth", AppData.selectedContact.toString());

                        for (int i = 0; i < AppData.selectedContact.size(); i++) {

                            if (i == 0) {

                                Log.v("SelectedContact_Lenth", AppData.selectedContact.get(i).getNumber());


                                String phone1 = (AppData.selectedContact.get(i).getNumber().replaceAll("[)\\-\\+\\.\\^:,(\\s+]", ""));

                                if (phone1.length() > 10) {

                                    phone = phone1.substring(phone1.length() - 10);

                                } else {

                                    phone = phone1;

                                }

                                name = AppData.selectedContact.get(i).getName();

                            } else {

                                Log.v("SelectedContact_Lenth", AppData.selectedContact.get(i).getNumber());

                                String phone1 = (AppData.selectedContact.get(i).getNumber().replaceAll("[)\\-\\+\\.\\^:,(\\s+]", ""));

                                if (phone1.length() > 10) {

                                    phone = phone + "," + phone1.substring(phone1.length() - 10);

                                } else {

                                    phone = phone + "," + phone1;

                                }

                                name = name + "," + AppData.selectedContact.get(i).getName();

                            }


                        }

                    } else {

                        phone = "";
                        name = "";

                    }

                } else {

                    phone = "";
                    name = "";

                }

                if (phone != "") {

                    try {
                        phone = URLEncoder.encode(phone, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                if (name != "") {

                    try {
                        name = URLEncoder.encode(name, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                Vol_InviteSplitters(phone, name);

                AppData.Contact_Block = false;

            }

        } else {

            if (AppData.Contact_Block == true) {
                //contact_block.setVisibility(View.VISIBLE);

                if (AppData.FromButton.equals("ContactTheHost")) {

                    ContactTheHostLayout.setVisibility(View.VISIBLE);
                    BookingAsHostLayout.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);

                } else if (AppData.FromButton.equals("HostTheTable")) {

                    ContactTheHostLayout.setVisibility(View.INVISIBLE);
                    BookingAsHostLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);

                }

                if (AppData.selectedContact != null) {
                    if (AppData.selectedContact.size() > 0) {

                        recyclerView.setLayoutManager(new LinearLayoutManager(BookingAsHost.this, LinearLayoutManager.HORIZONTAL, false));

                        adapter = new RecyclerSliters(BookingAsHost.this);
                        recyclerView.setAdapter(adapter);
                    }
                }

            } else {
                //contact_block.setVisibility(View.INVISIBLE);
                BookingAsHostLayout.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);

                if (AppData.FromButton.equals("ContactTheHost")) {

                    ContactTheHostLayout.setVisibility(View.VISIBLE);

                }

            }

        }


    }


    public void HideInviteList() {

        AppData.Contact_Block = false;

        BookingAsHostLayout.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        if (AppData.FromButton.equals("ContactTheHost")) {

            ContactTheHostLayout.setVisibility(View.VISIBLE);

        }

    }


    public void MembersDetails() {

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "invitehistory_control?event_id=" + AppData.EventId + "&table_id=" + AppData.TableId + "&logged_in=" + sharedPreferences.getString("UserId", "");

        Log.v("MemberDetails_Url::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

                try {

                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("success")) {

                        //AllMember = new ArrayList<MemberList_Settergetter>();

                        if (message.equals("data found")) {

                            String venue_name = response.getString("venue_name");
                            String total_seat = response.getString("total_seat");
                            String total_booked = response.getString("total_booked");
                            String remaining_book = response.getString("remaining_book");
                            String booked_date = response.getString("booked_date");


                            VenueName.setText(venue_name);
                            BookedDate.setText(booked_date);


                            JSONObject host = response.getJSONObject("host");
                            String host_name = host.getString("host_name");
                            String image = host.getString("image");
                            String host_payment = host.getString("host_payment");
                            String host_male = host.getString("host_male");
                            String host_female = host.getString("host_female");
                            String host_id = host.getString("host_id");

                            if (sharedPreferences.getString("UserId", "").equals(host_id)) {

                                Booked_MaleCount = host_male;
                                Booked_FemaleCount = host_female;

                                female_no.setText(Booked_FemaleCount);
                                male_no.setText(Booked_MaleCount);
                                Mprogress = Integer.parseInt(Booked_MaleCount);
                                Fprogress = Integer.parseInt(Booked_FemaleCount);
                                MaleProgress = Integer.parseInt(Booked_MaleCount);
                                FemaleProgress = Integer.parseInt(Booked_FemaleCount);

                                Booked_UserStatus = "";

                                GuestCount.setText(String.valueOf(Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount)));


                                try {

                                    if (getIntent().getExtras().getString("Purpose").equals("Edit")) {

                                        TExt_TableAmount.setText(host_payment);

                                        MaximumAvailableSeat.setText(String.valueOf(AppData.NumberOfAvailableSeat - (Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount))) + "/" + String.valueOf(AppData.MaximumTotalMember));
                                        AvailableSeatCount = AppData.NumberOfAvailableSeat - (Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount));

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }

                            MemberList_Settergetter memberList_settergetter = new MemberList_Settergetter(host_name, host_payment, host_male, host_female, image, "4", host_id, "", "", "", "", "", "", "", "", "", "", "", "", ""
                                    , "", "", "", "", "", "", "", "", "", "");
                            AllMember.add(memberList_settergetter);


                            //Category:1 for Invited,2 for Offer,3 for Confirmed

                            JSONArray confirmed = response.getJSONArray("confirmed");

                            if (confirmed.length() > 0) {

                                AcceptedMeberIsAvailableOrNot = true;
                                AcceptedMember = new ArrayList<MemberList_Settergetter>();

                                for (int i = 0; i < confirmed.length(); i++) {

                                    JSONObject confirmedObject = confirmed.getJSONObject(i);

                                    String name = confirmedObject.getString("confirmed_name");
                                    String price = confirmedObject.getString("confirmed_price");
                                    String male = confirmedObject.getString("confirmed_male");
                                    String female = confirmedObject.getString("confirmed_female");
                                    String confirmedimage = confirmedObject.getString("image");
                                    String id = confirmedObject.getString("splitter_id");

                                    if (sharedPreferences.getString("UserId", "").equals(id)) {

                                        Booked_MaleCount = male;
                                        Booked_FemaleCount = female;

                                        female_no.setText(Booked_FemaleCount);
                                        male_no.setText(Booked_MaleCount);
                                        Mprogress = Integer.parseInt(Booked_MaleCount);
                                        Fprogress = Integer.parseInt(Booked_FemaleCount);
                                        MaleProgress = Integer.parseInt(Booked_MaleCount);
                                        FemaleProgress = Integer.parseInt(Booked_FemaleCount);

                                        Booked_UserStatus = "C";

                                        GuestCount.setText(String.valueOf(Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount)));

                                        try {

                                            if (getIntent().getExtras().getString("Purpose").equals("Edit")) {

                                                TExt_TableAmount.setText(price);
                                                MaximumAvailableSeat.setText(String.valueOf(AppData.NumberOfAvailableSeat - (Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount))) + "/" + String.valueOf(AppData.MaximumTotalMember));
                                                AvailableSeatCount = AppData.NumberOfAvailableSeat - (Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount));


                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    MemberList_Settergetter memberList_settergetter1 = new MemberList_Settergetter(name, price, male, female, confirmedimage, "3", id, "", "", "", "", "", "", "", "", "", "", "", "", ""
                                            , "", "", "", "", "", "", "", "", "", "");
                                    AllMember.add(memberList_settergetter1);
                                    AcceptedMember.add(memberList_settergetter1);

                                }


                            } else {

                                AcceptedMeberIsAvailableOrNot = false;

                            }


                            JSONArray invited = response.getJSONArray("invited");

                            if (invited.length() > 0) {

                                for (int i = 0; i < invited.length(); i++) {

                                    JSONObject invitedObject = invited.getJSONObject(i);

                                    String name = invitedObject.getString("invited_name");
                                    String confirmedimage = invitedObject.getString("image");
                                    String invited_phone = invitedObject.getString("invited_phone");
                                    String id = invitedObject.getString("splitter_id");

                                    if (sharedPreferences.getString("UserId", "").equals(id)) {

                                        Booked_MaleCount = "0";
                                        Booked_FemaleCount = "0";

                                        female_no.setText(Booked_FemaleCount);
                                        male_no.setText(Booked_MaleCount);
                                        Mprogress = Integer.parseInt(Booked_MaleCount);
                                        Fprogress = Integer.parseInt(Booked_FemaleCount);
                                        MaleProgress = Integer.parseInt(Booked_MaleCount);
                                        FemaleProgress = Integer.parseInt(Booked_FemaleCount);

                                        Booked_UserStatus = "";

                                        GuestCount.setText(String.valueOf(Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount)));

                                        MaximumAvailableSeat.setText(String.valueOf(AppData.NumberOfAvailableSeat - (Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount))) + "/" + String.valueOf(AppData.MaximumTotalMember));
                                        AvailableSeatCount = AppData.NumberOfAvailableSeat - (Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount));

                                    }

                                    MemberList_Settergetter memberList_settergetter2 = new MemberList_Settergetter(name, invited_phone, "", "", confirmedimage, "1", id, "", "", "", "", "", "", "", "", "", "", "", "", ""
                                            , "", "", "", "", "", "", "", "", "", "");

                                    AllMember.add(memberList_settergetter2);

                                }


                            }


                            JSONArray request = response.getJSONArray("request");

                            if (request.length() > 0) {

                                for (int i = 0; i < request.length(); i++) {

                                    JSONObject requestObject = request.getJSONObject(i);

                                    String name = requestObject.getString("request_name");
                                    String price = requestObject.getString("request_payment");
                                    String male = requestObject.getString("request_male");
                                    String female = requestObject.getString("request_female");
                                    String confirmedimage = requestObject.getString("image");
                                    String id = requestObject.getString("splitter_id");
                                    String request_payment = requestObject.getString("request_payment");

                                    if (sharedPreferences.getString("UserId", "").equals(id)) {

                                        Booked_MaleCount = male;
                                        Booked_FemaleCount = female;

                                        female_no.setText(Booked_FemaleCount);
                                        male_no.setText(Booked_MaleCount);
                                        Mprogress = Integer.parseInt(Booked_MaleCount);
                                        Fprogress = Integer.parseInt(Booked_FemaleCount);
                                        MaleProgress = Integer.parseInt(Booked_MaleCount);
                                        FemaleProgress = Integer.parseInt(Booked_FemaleCount);

                                        Booked_UserStatus = "R";

                                        GuestCount.setText(String.valueOf(Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount)));
                                        MaximumAvailableSeat.setText(String.valueOf(AppData.NumberOfAvailableSeat - (Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount))) + "/" + String.valueOf(AppData.MaximumTotalMember));
                                        AvailableSeatCount = AppData.NumberOfAvailableSeat - (Integer.parseInt(Booked_MaleCount) + Integer.parseInt(Booked_FemaleCount));

                                        try {

                                            if (getIntent().getExtras().getString("Purpose").equals("Edit")) {

                                                TExt_TableAmount.setText(request_payment);

                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }


                                    String requester_id = requestObject.getString("requester_id");
                                    String event_id = requestObject.getString("event_id");
                                    String event_name = requestObject.getString("event_name");
                                    String event_day = requestObject.getString("event_day");
                                    String event_month = requestObject.getString("event_month");
                                    String event_year = requestObject.getString("event_year");
                                    String eventStartTime = requestObject.getString("eventStartTime");
                                    String eventEndTime = requestObject.getString("eventEndTime");
                                    String offer_male = requestObject.getString("offer_male");
                                    String offer_female = requestObject.getString("offer_female");
                                    String offer_amount = requestObject.getString("offer_amount");
                                    String title = requestObject.getString("title");

                                    JSONObject tabledet = requestObject.getJSONObject("tabledet");

                                    String table_id = tabledet.getString("table_id");
                                    String table_host_name = tabledet.getString("host_name");
                                    String table_host_id = tabledet.getString("host_id");
                                    String table_name = tabledet.getString("table_name");
                                    String cost = tabledet.getString("cost");
                                    String min_amount = tabledet.getString("min_amount");
                                    String remaining_amount = tabledet.getString("remaining_amount");
                                    String table_status = tabledet.getString("table_status");
                                    String number_of_available = tabledet.getString("number_of_available");
                                    String total = tabledet.getString("total");
                                    String what_to_do = tabledet.getString("what_to_do");


                                    MemberList_Settergetter memberList_settergetter3 = new MemberList_Settergetter(name, price, male, female, confirmedimage, "2", id,
                                            requester_id, event_id, event_name, event_day, event_month, event_year, eventStartTime, eventEndTime, offer_male, offer_female, offer_amount,
                                            table_id, table_host_name, table_host_id, table_name, cost, min_amount, remaining_amount, table_status, number_of_available, total, what_to_do, title);
                                    AllMember.add(memberList_settergetter3);


                                    //MemberList_Settergetter memberList_settergetter3 = new MemberList_Settergetter(name,price,male,female,confirmedimage,"2",id);
                                    //AllMember.add(memberList_settergetter3);

                                }


                            }


                            adaptersp = new SplittersAdapter(BookingAsHost.this, AllMember, BookingAsHost.this, "BookingAsHost", Loader);
                            rv_spliter.setAdapter(adaptersp);


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


    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    public void Vol_Pay() {

        String url = null;

        Loader.setVisibility(View.VISIBLE);
        done.setClickable(false);
        done.setEnabled(false);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        AppData.Amount = TExt_TableAmount.getText().toString().trim();


        String phone = null;

        String name = null;

        if (AppData.selectedContact != null) {
            if (AppData.selectedContact.size() > 0) {

                Log.v("SelectedContact_Lenth", String.valueOf(AppData.selectedContact.size()));
                Log.v("SelectedContact_Lenth", AppData.selectedContact.toString());

                for (int i = 0; i < AppData.selectedContact.size(); i++) {

                    if (i == 0) {

                        Log.v("SelectedContact_Lenth", AppData.selectedContact.get(i).getNumber());


                        String phone1 = (AppData.selectedContact.get(i).getNumber().replaceAll("[)\\-\\+\\.\\^:,(\\s+]", ""));
                        if (phone1.length() > 10) {

                            phone = phone1.substring(phone1.length() - 10);

                        } else {

                            phone = phone1;

                        }

                        name = AppData.selectedContact.get(i).getName();


                    } else {

                        Log.v("SelectedContact_Lenth", AppData.selectedContact.get(i).getNumber());

                        String phone1 = (AppData.selectedContact.get(i).getNumber().replaceAll("[)\\-\\+\\.\\^:,(\\s+]", ""));
                        if (phone1.length() > 10) {

                            phone = phone + "," + phone1.substring(phone1.length() - 10);

                        } else {

                            phone = phone + "," + phone1;

                        }

                        name = name + "," + AppData.selectedContact.get(i).getName();

                    }


                }

            } else {

                phone = "";
                name = "";

            }

        } else {

            phone = "";
            name = "";

        }

        if (phone != "") {

            try {
                phone = URLEncoder.encode(phone, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if (name != "") {

            try {
                name = URLEncoder.encode(name, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        url = AppData.DomainUrlForProfile + "requesthost_control?requester_id=" + sharedPreferences.getString("UserId", "") + "&host_id=" + AppData.HostId + "&event_id=" + AppData.EventId + "&table_id=" + AppData.TableId + "&price=0&phone=" + phone + "&name=" + name + "&male=" + AppData.MaleCount + "&female=" + AppData.FemaleCount + "&group_id=" + AppData.GroupId + "&qb_userid=" + sharedPreferences.getString("QB_UserId", "");


        Log.v("PayUrl::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("PayUrlResponse::", response.toString());

                String message = null, event_date = null, event_name = null;

                try {


                    String status = response.getString("status");

                    event_date = response.getString("event_date");

                    event_name = response.getString("event_name");

                    message = response.getString("message");

                    if (status.equals("success")) {

                        JSONArray non_appuser = response.getJSONArray("non_appuser");

                        if (non_appuser.length() > 0) {

                            NonAppUser = "YES";

                            for (int i = 0; i < non_appuser.length(); i++) {

                                if (non_appuser.length() > 1) {

                                    if (i == 0) {

                                        SendSms_PhoneNumber = non_appuser.getString(i) + ";";
                                    } else if (i == non_appuser.length() - 1) {

                                        SendSms_PhoneNumber = SendSms_PhoneNumber + non_appuser.getString(i);

                                    } else {

                                        SendSms_PhoneNumber = SendSms_PhoneNumber + non_appuser.getString(i) + ";";

                                    }

                                } else {

                                    SendSms_PhoneNumber = non_appuser.getString(i);

                                }


                            }


                        } else {

                            NonAppUser = "NO";

                        }


                        Log.v("PayUrlResponse", SendSms_PhoneNumber);

                        Intent intent = new Intent(BookingAsHost.this, HomeActivity.class);
                        intent.putExtra("FromPage", "Payment");
                        intent.putExtra("PhoneNumber", SendSms_PhoneNumber);
                        intent.putExtra("NonAppUser", NonAppUser);
                        intent.putExtra("EventDate", event_date);
                        intent.putExtra("EventName", event_name);
                        intent.putExtra("Purpose", "NormalMessage");
                        startActivity(intent);
                        finish();


                        for (int i = 0; i < AppData.contactVOList.size(); i++) {

                            AppData.contactVOList.get(i).setCheckStatus(false);

                        }

                    }

                    Loader.setVisibility(View.GONE);

                    done.setClickable(true);
                    done.setEnabled(true);

                } catch (JSONException e) {
                    e.printStackTrace();

                    Loader.setVisibility(View.GONE);
                    done.setClickable(true);
                    done.setEnabled(true);
                }

                Toast.makeText(BookingAsHost.this, message, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Loader.setVisibility(View.GONE);
                done.setClickable(true);
                done.setEnabled(true);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);


    }

    public void Vol_EditBooking() {

        Loader.setVisibility(View.VISIBLE);
        done.setEnabled(false);
        done.setClickable(false);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "editeventbook_control?eventid=" + AppData.EventId + "&hostid=" + AppData.HostId + "&userid=" + sharedPreferences.getString("UserId", "") + "&tableid=" + AppData.TableId + "&male=" + male_no.getText().toString() + "&female=" + female_no.getText().toString() + "&amount=" + TExt_TableAmount.getText().toString() + "&phone=&status=" + Booked_UserStatus;

        Log.v("EditBooking_Url::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");

                    if (status.equals("success")) {

                        Intent intent = new Intent(BookingAsHost.this, EventDetails.class);
                        intent.putExtra("FromPrevious", false);
                        AppData.FromPage = "EditBooking";
                        startActivity(intent);

                    }

                    Loader.setVisibility(View.GONE);
                    done.setEnabled(true);
                    done.setClickable(true);

                } catch (JSONException e) {
                    e.printStackTrace();

                    Loader.setVisibility(View.GONE);
                    done.setEnabled(true);
                    done.setClickable(true);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Loader.setVisibility(View.GONE);
                done.setEnabled(true);
                done.setClickable(true);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);


    }


    public void SendSms(String Number) {

        PhoneNumber = Number;

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(BookingAsHost.this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(BookingAsHost.this,
                    Manifest.permission.SEND_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                Log.v("SMS_Permission", "1");

                ActivityCompat.requestPermissions(BookingAsHost.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_SEND_SMS);


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(BookingAsHost.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_SEND_SMS);

                Log.v("SMS_Permission", "2");

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

            Log.v("SMS_Permission", "3");


            Uri sendSmsTo = Uri.parse("smsto:" + PhoneNumber);
            Intent intent = new Intent(
                    android.content.Intent.ACTION_SENDTO, sendSmsTo);
            intent.putExtra("sms_body", "Testing");
            startActivity(intent);

        }

    }

    public void Vol_InviteSplitters(String phone, String name) {

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "invite_control?table_id=" + AppData.TableId + "&host_id=" + AppData.HostId + "&phone=" + phone + "&name=" + name + "&logged_id=" + sharedPreferences.getString("UserId", "") + "&event_id=" + AppData.EventId;

        Log.v("InviteSplitters Url::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("InviteSplitters Response", response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public void Vol_CancelBooking(String changeHostId) {

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "hostchange_control?event_id=" + AppData.EventId + "&host_id=" + AppData.HostId + "&change_host_id=" + changeHostId + "&table_id=" + AppData.TableId + "&loggedin_id=" + sharedPreferences.getString("UserId", "");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            String message = null;

            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");

                    message = response.getString("message");

                    if (status.equals("success")) {

                        Intent intent = new Intent(BookingAsHost.this, HomeActivity.class);
                        intent.putExtra("FromPage", "CancelBooking");
                        startActivity(intent);
                        finish();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(BookingAsHost.this, message, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

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

                    Uri sendSmsTo = Uri.parse("smsto:" + PhoneNumber);
                    Intent intent = new Intent(
                            android.content.Intent.ACTION_SENDTO, sendSmsTo);
                    intent.putExtra("sms_body", "Testing");
                    startActivity(intent);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(BookingAsHost.this, "You have to give permission for sending sms", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void SetPromoter(int pos) {

        PromoterText.setText(AppData.PromoterDetails.get(pos).getPromoterName());
        AppData.SelectedPromoterId = AppData.PromoterDetails.get(pos).getPromoterId();
        dialog.dismiss();

    }


}

