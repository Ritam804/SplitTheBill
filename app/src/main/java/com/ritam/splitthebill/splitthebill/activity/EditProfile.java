package com.ritam.splitthebill.splitthebill.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditProfile extends AppCompatActivity {

    LinearLayout Button_Back, Button_Save;

    RelativeLayout Button_Phone,Button_DOB,Button_Gender;


    TextView Phone, Gender,Splits, Splitters,ProfileName,DOB;

    EditText UserName,Email,Name;

    ImageView UserImage;

    DigitsAuthButton digitsButton;

    String ChoosenDOB,SelectedGender;

    ProgressBar EditProfile_Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        Button_Back = (LinearLayout) findViewById(R.id.ll_back_editpro);

        Button_Save = (LinearLayout) findViewById(R.id.ll_save);

        digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);

        Button_Phone = (RelativeLayout) findViewById(R.id.rl_phone);

        Button_DOB = (RelativeLayout) findViewById(R.id.rl_dob);

        Button_Gender = (RelativeLayout) findViewById(R.id.rl_gender);

        Name = (EditText) findViewById(R.id.et_name);

        EditProfile_Progress = (ProgressBar) findViewById(R.id.progressBar);


        UserName = (EditText) findViewById(R.id.et_username);
        Email = (EditText) findViewById(R.id.et_email);
        Phone = (TextView) findViewById(R.id.tv_phone);
        Gender = (TextView) findViewById(R.id.tv_gender);
        DOB = (TextView) findViewById(R.id.tv_dob);
        Splits = (TextView) findViewById(R.id.tv_followers);
        Splitters = (TextView) findViewById(R.id.tv_following);
        ProfileName = (TextView) findViewById(R.id.tv_proname);

        UserImage = (ImageView) findViewById(R.id.iv_proimage);


        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        profileInfo();


        Button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdateProfile();

            }
        });

        Button_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                digitsButton.performClick();

            }
        });

        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
//                Toast.makeText(getActivity(), "Authentication successful for "
//                        + phoneNumber, Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Digits.logout();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 3000);


                Phone.setText(phoneNumber);
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });

        Button_Gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                RelativeLayout Button_Male, Button_Female, Button_Neutral;

                View view1 = LayoutInflater.from(EditProfile.this).inflate(R.layout.dialog_gender, null);
                final Dialog dialog = new Dialog(EditProfile.this, R.style.MaterialDialogSheet);
                dialog.setContentView(view1);
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();


                Button_Male = (RelativeLayout) view1.findViewById(R.id.rl_male);
                Button_Female = (RelativeLayout) view1.findViewById(R.id.rl_female);
                Button_Neutral = (RelativeLayout) view1.findViewById(R.id.rl_nutral);

                Button_Male.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        Gender.setText("Male");
                        SelectedGender = "M";
                        dialog.dismiss();

                    }
                });


                Button_Female.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {





                        Gender.setText("Female");
                        SelectedGender = "F";
                        dialog.dismiss();

                    }
                });

                Button_Neutral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {





                        Gender.setText("Neutral");
                        SelectedGender = "N";
                        dialog.dismiss();

                    }
                });


            }
        });


        Button_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final DatePicker datePicker;
                RelativeLayout Button_OK;

                View view1 = LayoutInflater.from(EditProfile.this).inflate(R.layout.dialog_dob, null);
                final Dialog dialog = new Dialog(EditProfile.this, R.style.MaterialDialogSheet);
                dialog.setContentView(view1);
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();


                Button_OK = (RelativeLayout) view1.findViewById(R.id.rl_ok);

                datePicker = (DatePicker) view1.findViewById(R.id.dp_dob);

                datePicker.setMaxDate(System.currentTimeMillis());

                Button_OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Log.v("Selected_Year::",String.valueOf(datePicker.getYear()));
                        Log.v("Selected_Year::",String.valueOf(datePicker.getMonth()));
                        Log.v("Selected_Year::",String.valueOf(datePicker.getDayOfMonth()));

                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year = datePicker.getYear();

                        if (month+1 > 9){

                            if (day > 9){

                                DOB.setText(String.valueOf(month+1)+"-"+String.valueOf(day)+"-"+String.valueOf(year));

                            }else {

                                DOB.setText(String.valueOf(month+1)+"-0"+String.valueOf(day)+"-"+String.valueOf(year));
                            }

                        }else {

                            if (day > 9){

                                DOB.setText("0"+String.valueOf(month+1)+"-"+String.valueOf(day)+"-"+String.valueOf(year));

                            }else {

                                DOB.setText("0"+String.valueOf(month+1)+"-0"+String.valueOf(day)+"-"+String.valueOf(year));
                            }

                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");
                        ChoosenDOB = sdf.format(new Date(month, day, year));

                        //Text_DOB.setText(ChoosenDOB);

                        dialog.dismiss();

                    }
                });


            }
        });

        //View view = this.getCurrentFocus();
        //if (view != null) {

//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        //}


    }

    public void profileInfo() {

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "profile_control?userid=" + sharedPreferences.getString("UserId", "")+"&logged_in="+sharedPreferences.getString("UserId","");

        Log.d("Profile url", ":::" + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {


                    String status = response.getString("status");

                    if (status.equals("success")) {

                        JSONObject Details = response.getJSONObject("Details");

                        String name = Details.getString("name");
                        String splits = Details.getString("splits");
                        String splitter = Details.getString("splitter");
                        String rating = Details.getString("rating");
                        String image = Details.getString("image");
                        String email = Details.getString("email");
                        String gender = Details.getString("gender");
                        String handle = Details.getString("handle");
                        String dob = Details.getString("dob");
                        String first_name = Details.getString("first_name");
                        String last_name = Details.getString("last_name");
                        String usertype = Details.getString("usertype");
                        String push_status = Details.getString("push_status");
                        String phone = Details.getString("phone");


                        Name.setText(name);
                        UserName.setText(handle);
                        Email.setText(email);

                        Phone.setText(phone);
                        Gender.setText(gender);
                        DOB.setText(dob);
                        Splits.setText(splits);
                        Splitters.setText(splitter);
                        ProfileName.setText(name);

                        if (gender.equals("Male")){

                            SelectedGender = "M";

                        }else if (gender.equals("Female")){

                            SelectedGender = "F";

                        }else {

                            SelectedGender = "N";

                        }


                        ChoosenDOB = dob;



                        if (!(image.equals("")) && !(image.isEmpty()) && image != null) {

                            Picasso.with(EditProfile.this)
                                    .load(image)
                                    .placeholder(R.drawable.appicon_round)
                                    .error(R.drawable.appicon_round)
                                    .transform(new RoundedTransformation())
                                    .into(UserImage);

                        }


                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(Name.getWindowToken(), 0);

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


    public void UpdateProfile() {

        EditProfile_Progress.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
        String url = null;

        try {
            url = AppData.DomainUrlForProfile + "editprofile_control?userid=" + sharedPreferences.getString("UserId", "") + "&name=" + URLEncoder.encode(Name.getText().toString(), "utf-8") + "&email=" + URLEncoder.encode(Email.getText().toString(), "utf-8") + "&handle=" + URLEncoder.encode(UserName.getText().toString(), "utf-8")+"&phone="+URLEncoder.encode(Phone.getText().toString(), "utf-8")+"&gender="+SelectedGender+"&dob="+URLEncoder.encode(DOB.getText().toString(), "utf-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.v("Update_Url::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");

                    if (status.equals("success")) {

                        finish();

                    }

                    Toast.makeText(EditProfile.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                EditProfile_Progress.setVisibility(View.GONE);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                EditProfile_Progress.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }


}
