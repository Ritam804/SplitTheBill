package com.ritam.splitthebill.splitthebill.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.HomeActivity;
import com.ritam.splitthebill.splitthebill.activity.LoginActivity;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.fabric.sdk.android.Fabric;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecondPage_SignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondPage_SignUp extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RelativeLayout Button_Gender;
    ImageView Logo_Gender,Logo_Gender2;
    TextView Text_Gender;

    String SelectedGender;

    RelativeLayout Button_DOB;
    TextView Text_DOB;

    String ChoosenDOB,fb_accesstoken="",tw_accesstoken="",usertype="1",fb_id="";

    boolean GenderSelection = false, DobSelection = false;

    RelativeLayout Button_SignUp;

    EditText Password, ConfirmPassword;

    ProgressBar progressBar;

    DigitsAuthButton digitsButton;

    TextView Button_TermsandCondition,Button_PrivacyPolicy;


    private OnFragmentInteractionListener mListener;


    public SecondPage_SignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment SecondPage_SignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondPage_SignUp newInstance() {
        AppData.SignUpPageNumber = 2;
        SecondPage_SignUp fragment = new SecondPage_SignUp();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_second_page__sign_up, container, false);

        TextView selectPageIndicator = (TextView) getActivity().findViewById(R.id.tv_second);
        selectPageIndicator.setBackgroundColor(Color.parseColor("#a33636"));

        Button_Gender = (RelativeLayout) view.findViewById(R.id.rl_gender);
        Logo_Gender = (ImageView) view.findViewById(R.id.iv_genderlogo);
        Logo_Gender.setVisibility(View.INVISIBLE);
        Logo_Gender2 = (ImageView) view.findViewById(R.id.iv_genderlogo2);
        Logo_Gender2.setVisibility(View.INVISIBLE);

        Text_Gender = (TextView) view.findViewById(R.id.tv_gender);

        Button_DOB = (RelativeLayout) view.findViewById(R.id.rl_dob);
        Text_DOB = (TextView) view.findViewById(R.id.tv_dob);

        Button_SignUp = (RelativeLayout) view.findViewById(R.id.ll_signupbutton);

        Password = (EditText) view.findViewById(R.id.et_pass);

        ConfirmPassword = (EditText) view.findViewById(R.id.et_repass);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);


        Button_TermsandCondition = (TextView) view.findViewById(R.id.tv_termsandconditions);
        Button_TermsandCondition.setClickable(true);
        Button_TermsandCondition.setMovementMethod(LinkMovementMethod.getInstance());



        digitsButton = (DigitsAuthButton) view.findViewById(R.id.auth_button);

        Intent intent=getActivity().getIntent();

        if(intent.getExtras().getString("from","").equals("twitter")) {

            usertype="3";
            tw_accesstoken=intent.getExtras().getString("tw_authToken","");

        }

        if(intent.getExtras().getString("from","").equals("facebook")) {

            usertype="2";
            fb_accesstoken=intent.getExtras().getString("fb_accesstoken","");
            fb_id=intent.getExtras().getString("fb_id","");
            ChoosenDOB=intent.getExtras().getString("dob", "");
            GenderSelection=true;
            DobSelection=true;

            Text_Gender.setText(intent.getExtras().getString("gender", ""));
            Text_Gender.setEnabled(false);
            Button_Gender.setEnabled(false);
            Button_DOB.setEnabled(false);

            Text_DOB.setText(intent.getExtras().getString("dob", ""));
            Text_DOB.setEnabled(false);

            SelectedGender = intent.getExtras().getString("gender", "").substring(0, 1);

            if (SelectedGender.equals("M")) {
                Logo_Gender2.setBackground(getResources().getDrawable(R.drawable.male_small));
                Logo_Gender2.setVisibility(View.VISIBLE);
            } else if (SelectedGender.equals("F")) {
                Logo_Gender.setBackground(getResources().getDrawable(R.drawable.female_small));
                Logo_Gender.setVisibility(View.VISIBLE);

            } else {
                Logo_Gender.setBackground(getResources().getDrawable(R.drawable.neutral_small));

                Logo_Gender.setVisibility(View.VISIBLE);

            }
        }


        //TwitterAuthConfig authConfig = new TwitterAuthConfig("R9r00XSQ44veYXW2rproB9S9v","6rVfGTQr1ys7CcZ11I8VuvErPknBRa9SnISw95OZbfsKQ4aA39");
        //Fabric.with(this, new Twitter(authConfig));
        //Digits.Builder digitsBuilder = new Digits.Builder().withTheme(R.style.CustomDigitsTheme);
        //Fabric.with(getActivity(), new TwitterCore(authConfig), digitsBuilder.build());



        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, final String phoneNumber) {
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

                String phoneNumber1 = null;


                if (phoneNumber.length() > 10) {

                    phoneNumber1 = phoneNumber.substring(phoneNumber.length() - 10);

                } else {

                    phoneNumber1 = phoneNumber;

                }

                        Vol_Signup(phoneNumber1);




            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });



        Log.v("Name", getArguments().getString("Name"));
        Log.v("Handle", getArguments().getString("Handle"));
        Log.v("Email", getArguments().getString("Email"));

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        Button_Gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                RelativeLayout Button_Male, Button_Female, Button_Neutral;

                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_gender, null);
                final Dialog dialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);
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

                        GenderSelection = true;

                        Logo_Gender2.setBackground(getResources().getDrawable(R.drawable.male_small));
                        Logo_Gender2.setVisibility(View.VISIBLE);
                        Logo_Gender.setVisibility(View.INVISIBLE);

                        Text_Gender.setText("Male");
                        SelectedGender = "M";
                        dialog.dismiss();

                    }
                });


                Button_Female.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        GenderSelection = true;

                        Logo_Gender.setBackground(getResources().getDrawable(R.drawable.female_small));
                        Logo_Gender.setVisibility(View.VISIBLE);
                        Logo_Gender2.setVisibility(View.INVISIBLE);



                        Text_Gender.setText("Female");
                        SelectedGender = "F";
                        dialog.dismiss();

                    }
                });

                Button_Neutral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        GenderSelection = true;

                        Logo_Gender.setBackground(getResources().getDrawable(R.drawable.neutral_small));
                        Logo_Gender.setVisibility(View.VISIBLE);
                        Logo_Gender2.setVisibility(View.INVISIBLE);



                        Text_Gender.setText("Neutral");
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

                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_dob, null);
                final Dialog dialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);
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

                        DobSelection = true;

                        Log.v("Selected_Year::",String.valueOf(datePicker.getYear()));
                        Log.v("Selected_Year::",String.valueOf(datePicker.getMonth()));
                        Log.v("Selected_Year::",String.valueOf(datePicker.getDayOfMonth()));

                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year = datePicker.getYear();

                        if (month+1 > 9){

                            if (day > 9){

                                Text_DOB.setText(String.valueOf(month+1)+"-"+String.valueOf(day)+"-"+String.valueOf(year));

                            }else {

                                Text_DOB.setText(String.valueOf(month+1)+"-0"+String.valueOf(day)+"-"+String.valueOf(year));
                            }

                        }else {

                            if (day > 9){

                                Text_DOB.setText("0"+String.valueOf(month+1)+"-"+String.valueOf(day)+"-"+String.valueOf(year));

                            }else {

                                Text_DOB.setText("0"+String.valueOf(month+1)+"-0"+String.valueOf(day)+"-"+String.valueOf(year));
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




        Button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Password.getText().toString().trim().length() > 0 && !(Password.getText().toString().trim().isEmpty())) {

                    if (Password.getText().toString().trim().length()>=8){


                        if (ConfirmPassword.getText().toString().trim().length() > 0 && !(ConfirmPassword.getText().toString().trim().isEmpty())) {

                            if (Password.getText().toString().equals(ConfirmPassword.getText().toString())) {
                                

                                if (GenderSelection) {

                                    if (DobSelection) {

                                        digitsButton.performClick();

                                        //Vol_Signup();


                                    } else {

                                        Toast.makeText(getActivity(), "Please enter date of birth", Toast.LENGTH_SHORT).show();


                                    }

                                } else {

                                    Toast.makeText(getActivity(), "Please select a gender", Toast.LENGTH_SHORT).show();

                                }


                            } else {

                                Toast.makeText(getActivity(), "Password and confirm password no matching", Toast.LENGTH_SHORT).show();

                                ConfirmPassword.requestFocus();

                                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(ConfirmPassword, InputMethodManager.SHOW_IMPLICIT);

                            }

                        } else {

                            Toast.makeText(getActivity(), "Please enter confirm password", Toast.LENGTH_SHORT).show();

                            ConfirmPassword.requestFocus();

                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(ConfirmPassword, InputMethodManager.SHOW_IMPLICIT);

                        }


                    }else {

                        Toast.makeText(getActivity(), "Password must have atleast 8 characters", Toast.LENGTH_SHORT).show();

                        Password.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(Password, InputMethodManager.SHOW_IMPLICIT);

                    }

                } else {

                    Toast.makeText(getActivity(), "Please enter a password", Toast.LENGTH_SHORT).show();

                    Password.requestFocus();

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(Password, InputMethodManager.SHOW_IMPLICIT);

                }

            }
        });


    }

    public void Vol_Signup(String phoneNumber){

        progressBar.setVisibility(View.VISIBLE);

        String url = null;
        try {
            url = AppData.DomainUrl+"user_registration?email="+getArguments().getString("Email")+"&password="+
                    URLEncoder.encode(Password.getText().toString(),"utf-8")+"&handle="+URLEncoder.encode(getArguments().getString("Handle"),"utf-8")+
                    "&name="+URLEncoder.encode(getArguments().getString("Name"),"utf-8")+"&gender="+SelectedGender
                    +"&dob="+ChoosenDOB+"&phone="+phoneNumber+"&fb_accesstoken="+fb_accesstoken+"&twt_authtoken="+tw_accesstoken+"&fb_id="+fb_id+"&usertype="+usertype;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.v("SignupUrl::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("SignupResponse::",response.toString());

                boolean response_ = false;
                String message = null;

                try {

                    response_ = response.getBoolean("response");
                    message = response.getString("message");

                    if (response_){


                        JSONObject info_array = response.getJSONObject("info_array");

                        String user_id = info_array.getString("user_id");
//                        String first_name = info_array.getString("first_name");
//                        String last_name = info_array.getString("last_name");
//                        String phone = info_array.getString("phone");
//                        String email = info_array.getString("email");
//                        String handle = info_array.getString("handle");
//                        String email_view = info_array.getString("email_view");
//                        String password = info_array.getString("password");
//                        String dob = info_array.getString("dob");
//                        String dob_view = info_array.getString("dob_view");
//                        String gender = info_array.getString("gender");
//                        String gender_view = info_array.getString("gender_view");
//                        String profile_image = info_array.getString("profile_image");
//                        String image_view = info_array.getString("image_view");
//                        String added_on = info_array.getString("added_on");
//                        String statusreceive = info_array.getString("status");
//                        String verify_code = info_array.getString("verify_code");
//                        String current_city = info_array.getString("current_city");
//                        String favourite_venue = info_array.getString("favourite_venue");
//                        String entertainment_view = info_array.getString("entertainment_view");
//                        String fb_id = info_array.getString("fb_id");

                        //getActivity().finish();

                        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences("AutoLogin",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("UserId",user_id);
//                        editor.putString("first_name",first_name);
//                        editor.putString("last_name",last_name);
//                        editor.putString("phone",phone);
//                        editor.putString("email",email);
//                        editor.putString("handle",handle);
//                        editor.putString("email_view",email_view);
//                        editor.putString("password",password);
//                        editor.putString("dob",dob);
//                        editor.putString("dob_view",dob_view);
//                        editor.putString("gender",gender);
//                        editor.putString("gender_view",gender_view);
//                        editor.putString("profile_image",profile_image);
//                        editor.putString("image_view",image_view);
//                        editor.putString("statusreceive",statusreceive);
//                        editor.putString("verify_code",verify_code);
//                        editor.putString("current_city",current_city);
//                        editor.putString("favourite_venue",favourite_venue);
//                        editor.putString("entertainment_view",entertainment_view);
                       editor.commit();

                        progressBar.setVisibility(View.VISIBLE);

                        QBSettings.getInstance().init(getActivity(), AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
                        QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);

                        //QBAuth.createSessionByEmail(getArguments().getString("Email"),Password.getText().toString().trim());

                        QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {
                            @Override
                            public void onSuccess(QBSession qbSession, Bundle bundle) {

                                QBUser qbUser = new QBUser(getArguments().getString("Email"),Password.getText().toString());

                                QBUsers.signUp(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                                    @Override
                                    public void onSuccess(QBUser qbUser, Bundle bundle) {

                                        AppData.SignUpName = "";
                                        AppData.SignUpUserName = "";
                                        AppData.SignUpEmail = "";

                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);

                                        progressBar.setVisibility(View.GONE);

                                        getActivity().finish();

                                    }

                                    @Override
                                    public void onError(QBResponseException e) {

                                        Toast.makeText(getActivity(),"Some Error Occured!",Toast.LENGTH_SHORT).show();

                                        progressBar.setVisibility(View.GONE);

                                    }
                                });

                            }

                            @Override
                            public void onError(QBResponseException e) {

                                progressBar.setVisibility(View.GONE);

                            }
                        });



                    }else {

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);

                        getActivity().finish();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    //Log.v("OnCatch::",e.getMessage());
                }


                progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                //Log.v("OnError::",error.getMessage());

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                500000, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

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
}
