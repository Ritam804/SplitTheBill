package com.ritam.splitthebill.splitthebill.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.provider.*;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.StoringMechanism;
import com.quickblox.core.exception.QBResponseException;
//import com.quickblox.messages.QBPushNotifications;
//import com.quickblox.messages.model.QBEnvironment;
//import com.quickblox.messages.model.QBNotificationChannel;
//import com.quickblox.messages.model.QBSubscription;
import com.quickblox.messages.QBPushNotifications;
import com.quickblox.messages.model.QBEnvironment;
import com.quickblox.messages.model.QBNotificationChannel;
import com.quickblox.messages.model.QBSubscription;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText Email, Password;
    LinearLayout Button_Login, Button_SignUp;

    CallbackManager callbackManager;

    TextView Button_ForgotPass;

    String fbID = "", fbName = "", fbEmail = "", fbUrl = "",
            fbBirthDay = "", fbFirstName = "", fbLastName = "", fbGender = "", fbLocale = "",
            fbUpdateTime = "", fbLocationID = "", fbLocationName = "", fbProfilePicLarge = "", fbProfilePicSmall = "";

    String fbIDFriend = "", fbNameFriend = "", fbEmailFriend = "", fbUrlFriend = "",
            fbBirthDayFriend = "", fbFirstNameFriend = "", fbLastNameFriend = "", fbGenderFriend = "", fbLocaleFriend = "",
            fbUpdateTimeFriend = "", fbLocationIDFriend = "", fbLocationNameFriend = "", fbProfilePicLargeFriend = "",
            fbProfilePicFriendSmall;

    String twID = "", twName = "", twAuthToken = "";

    String FbMode, fbFullName, fbUserID, fbAccessToken, FbUserId, FbSchoolId;

    String FacebookAccesToken;


    ImageView Button_TWEET;
    LinearLayout Button_FB;

    private TwitterLoginButton loginButton;

    boolean ClickedButton_FB = false, ClickedButton_TWEET = false;


    ProgressBar progressBar;

    String message = null;

    TextView Button_Help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        Email = (EditText) findViewById(R.id.et_email);
        Password = (EditText) findViewById(R.id.et_pass);

        Button_Login = (LinearLayout) findViewById(R.id.ll_login);

        Button_SignUp = (LinearLayout) findViewById(R.id.ll_signup);

        Button_FB = (LinearLayout) findViewById(R.id.ll_fblogin);

        Button_ForgotPass = (TextView) findViewById(R.id.tv_forgotpass);

        Button_Help = (TextView) findViewById(R.id.help);

        // Button_TWEET = (ImageView) findViewById(R.id.iv_tweet);

        // Button_TWEET.setEnabled(true);
        // Button_TWEET.setClickable(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Button_ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this,ForgotPassword_FirstScreen.class));

            }
        });

        Button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                intent.putExtra("from", "normal");
                startActivity(intent);

            }
        });

        Button_Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this,HelpActivity.class));

            }
        });

  /*      Button_TWEET.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                ClickedButton_FB = false;
                                                ClickedButton_TWEET = true;

                                                loginButton.performClick();

                                            }
                                        });*/




        /*loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);


        loginButton.setBackground(getResources().getDrawable(R.drawable.social_twitter));

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                //String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Log.d("Twitter response",":::"+session.toString());


                twID=String.valueOf(session.getUserId());
                twName=session.getUserName();
                twAuthToken=String.valueOf(session.getAuthToken());

                Log.d("Twitter ID",":::"+twID);
                Log.d("Twitter name",":::"+twName);
                Log.d("Twitter AuthToken",":::"+twAuthToken);

                twUserIdCheck(twID);

            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });*/


        callbackManager = CallbackManager.Factory.create();

        getFbKeyHash("com.ritam.splitthebill.splitthebill");


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
                        FacebookAccesToken = AccessToken.getCurrentAccessToken().getToken();

                        // ---- Facebook user details
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {

                                        Log.i("FaceBook JsonObject", jsonObject.toString());
                                        Log.i("FaceBook JsonObject", graphResponse.toString());

                                        try {


                                            fbID = "" + jsonObject.getString("id");
                                            fbUserID = fbID;
                                            //AppSecret.UserId = fbID;
                                            fbName = "" + jsonObject.getString("name");
                                            fbFullName = fbName;
                                            fbEmail = "" + jsonObject.getString("email");
                                            //AppSecret.UserMailId = fbEmail;
                                            fbUrl = "" + jsonObject.getString("link");
                                            //AppSecret.UserImage = fbUrl;
                                            try {
                                                //JSONObject jOBJ = jsonObject.getJSONObject("age_range");
                                                fbBirthDay = "" + jsonObject.getString("birthday");
                                            } catch (Exception e) {
                                                fbBirthDay = "Not mention";
                                            }
                                            fbFirstName = "" + jsonObject.getString("first_name");
                                            fbLastName = "" + jsonObject.getString("last_name");
                                            fbGender = "" + jsonObject.getString("gender");
                                            fbLocale = "" + jsonObject.getString("locale");
                                            fbUpdateTime = "" + jsonObject.getString("updated_time");

                                            try {
                                                JSONObject jOBJ = jsonObject.getJSONObject("location");
                                                fbLocationID = "" + jOBJ.getString("id");
                                                fbLocationName = "" + jOBJ.getString("name");
                                            } catch (Exception e) {
                                                fbLocationID = "Not mention";
                                                fbLocationName = "Not mention";
                                            }

                                            fbProfilePicLarge = "https://graph.facebook.com/" + fbID + "/picture?type=large";
                                            fbProfilePicSmall = "https://graph.facebook.com/" + fbID + "/picture?type=small";

                                            Log.i("fbID :", fbID);
                                            Log.i("fbName :", fbName);
                                            Log.i("fbEmail :", fbEmail);
                                            Log.i("fbUrl :", fbUrl);
                                            Log.i("fbBirthDay :", fbBirthDay);
                                            Log.i("fbFirstName :", fbFirstName);
                                            Log.i("fbLastName :", fbLastName);
                                            Log.i("fbGender :", fbGender);
                                            Log.i("fbLocale :", fbLocale);
                                            Log.i("fbUpdateTime :", fbUpdateTime);
                                            Log.i("fbLocationID :", fbLocationID);
                                            Log.i("fbLocationName :", fbLocationName);
                                            Log.i("fbProfilePicLarge :", fbProfilePicLarge);
                                            Log.i("fbProfilePicSmall :", fbProfilePicSmall);

                                            if (fbUserID != null && fbUserID.length() > 0) {


                                                //Here Fire the facebook check in url or registration url then login url if already registered....

                                                //Toast.makeText(LoginActivity.this, "Time To Fire Url", Toast.LENGTH_SHORT).show();

                                                fbUserIdCheck(fbID);


                                            } else {
                                                Toast.makeText(LoginActivity.this, "Login Fail", Toast.LENGTH_LONG).show();
                                            }

                                        } catch (JSONException e) {
                                            Log.i("FB JSON EXCP : ", e.toString());
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields",
                                "id, name, email, link, birthday, age_range, first_name, last_name, gender, updated_time, verified, timezone, locale, location");
                        request.setParameters(parameters);
                        request.executeAsync();
                        // ---- END

                        // ---- Facebook user friend details
                        GraphRequest requestFriendList = GraphRequest.newMyFriendsRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
                                    @Override
                                    public void onCompleted(JSONArray jsonArray, GraphResponse graphResponse) {

                                        Log.i("Friendlist Length : ", "" + jsonArray.length());

                                        try {
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                                fbIDFriend = "" + jsonObject.getString("id");
                                                fbNameFriend = "" + jsonObject.getString("name");
                                                fbUrlFriend = "" + jsonObject.getString("link");
                                                fbFirstNameFriend = "" + jsonObject.getString("first_name");
                                                fbLastNameFriend = "" + jsonObject.getString("last_name");
                                                fbGenderFriend = "" + jsonObject.getString("gender");
                                                fbLocaleFriend = "" + jsonObject.getString("locale");
                                                fbUpdateTimeFriend = "" + jsonObject.getString("updated_time");

                                                fbProfilePicLargeFriend = "https://graph.facebook.com/" + fbIDFriend + "/picture?type=large";
                                                fbProfilePicFriendSmall = "https://graph.facebook.com/" + fbIDFriend + "/picture?type=small";

                                                Log.i("START", "---------------------------------------");

                                                Log.i("fbIDFriend :", fbIDFriend);
                                                Log.i("fbNameFriend :", fbNameFriend);
                                                Log.i("fbUrlFriend :", fbUrlFriend);
                                                Log.i("fbFirstNameFriend :", fbFirstNameFriend);
                                                Log.i("fbLastNameFriend :", fbLastNameFriend);
                                                Log.i("fbGenderFriend :", fbGenderFriend);
                                                Log.i("fbLocaleFriend :", fbLocaleFriend);
                                                Log.i("fbUpdateTimeFriend :", fbUpdateTimeFriend);
                                                Log.i("fbPICLargeFriend :", fbProfilePicLargeFriend);
                                                Log.i("fbPicSmallFriend :", fbProfilePicFriendSmall);

                                                Log.i("END", "-----------------------------------------");


                                            }

                                        } catch (Exception e) {
                                            Log.i("FriendsRequest exce : ", e.toString());
                                        }
                                    }
                                });
                        Bundle parametersFriends = new Bundle();
                        parametersFriends.putString("fields",
                                "id, name, email, link, birthday, first_name, last_name, gender, updated_time, verified, timezone, locale, location");
                        requestFriendList.setParameters(parametersFriends);
                        requestFriendList.executeAsync();
                        // ---- END
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        //Toast.makeText(SignupUserActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();

                        if (exception instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                                Button_FB.performClick();
                            }
                        }

                    }
                });


        Button_FB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClickedButton_FB = true;
                ClickedButton_TWEET = false;


                LoginManager.getInstance().logInWithReadPermissions(
                        LoginActivity.this,
                        Arrays.asList("public_profile", "email", "user_friends", "user_birthday"));

            }
        });

        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(Email.getText().toString().trim().isEmpty()) && Email.getText().toString().trim().length() > 0) {


                    if (!(Password.getText().toString().isEmpty()) && Password.getText().toString().trim().length() > 0) {

                        if (Password.getText().toString().length() >= 8) {

                            Button_Login.setEnabled(false);
                            Button_Login.setClickable(false);

                            progressBar.setVisibility(View.VISIBLE);

                            try {
                                Vol_Login(URLEncoder.encode(Email.getText().toString(), "utf-8"), URLEncoder.encode(Password.getText().toString(), "utf-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }


                        } else {

                            Toast.makeText(LoginActivity.this, "Password must have minimum 8 charecters", Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        Toast.makeText(LoginActivity.this, "Please give a password", Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Toast.makeText(LoginActivity.this, "Please give an email address", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public void Vol_Login(String email, String pass) {


        progressBar.setVisibility(View.VISIBLE);

//        String Pass = null,Email = null;
//
//        try {
//            Email = URLEncoder.encode("email","utf-8");
//            Pass = URLEncoder.encode("pass","utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        AppData.DeviceToken = FirebaseInstanceId.getInstance().getToken();

        AppData.RegistrationId = FirebaseInstanceId.getInstance().getId();

        String url = AppData.DomainUrl + "user_login?email_orhandle=" + email + "&password=" + pass + "&device_type=1" + "&device_token=" + AppData.DeviceToken + "&qb_userid=";


        Log.v("Login_Url:::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                message = null;

                try {
                    boolean status = response.getBoolean("response");
                    message = response.getString("message");

                    if (status) {


                        AppData.imageUploadStatus = true;

                        JSONObject info_array = response.getJSONObject("info_array");

                        final String user_id = info_array.getString("user_id");
                        final String first_name = info_array.getString("first_name");
                        final String last_name = info_array.getString("last_name");
                        final String phone = info_array.getString("phone");
                        final String email = info_array.getString("email");
                        final String handle = info_array.getString("handle");
                        final String email_view = info_array.getString("email_view");
                        final String password = info_array.getString("password");
                        final String dob = info_array.getString("dob");
                        final String dob_view = info_array.getString("dob_view");
                        final String gender = info_array.getString("gender");
                        final String gender_view = info_array.getString("gender_view");
                        final String profile_image = info_array.getString("profile_image");
                        final String image_view = info_array.getString("image_view");
                        String added_on = info_array.getString("added_on");
                        final String statusreceive = info_array.getString("status");
                        final String verify_code = info_array.getString("verify_code");
                        final String current_city = info_array.getString("current_city");
                        final String favourite_venue = info_array.getString("favourite_venue");
                        final String entertainment_view = info_array.getString("entertainment_view");
                        String signup_status = info_array.getString("signup_status");
                        final String qb_password = info_array.getString("qb_password");

                        if (signup_status.equals("0")) {



                            message = "Please validate your account from your email";
                            progressBar.setVisibility(View.GONE);
                            Button_Login.setEnabled(true);
                            Button_Login.setClickable(true);

                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();



                        } else if (signup_status.equals("1")) {


                            QBSettings.getInstance().setStoringMehanism(StoringMechanism.UNSECURED);
                            QBSettings.getInstance().init(getApplicationContext(), AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
                            QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);
                            //QBSettings.getInstance().setEnablePushNotification(true);
                            //Performer<QBSession> performer = QBAuth.createSession();

                            //QBAuth.createSessionByEmail(Email.getText().toString().trim(),Password.getText().toString().trim());




                            QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {
                                @Override
                                public void onSuccess(final QBSession qbSession, Bundle bundle) {

                                    progressBar.setVisibility(View.VISIBLE);
                                    Button_Login.setEnabled(false);
                                    Button_Login.setClickable(true);

                                    QBUser qbUser = new QBUser(email, qb_password);

                                    QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                                        @Override
                                        public void onSuccess(QBUser qbUser, Bundle bundle) {



                                            // Subscribe to Push Notifications
                                            subscribeToPushNotifications(AppData.DeviceToken);


                                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();

                                            editor.putString("AutoLogin", "YES");
                                            editor.putString("UserId", user_id);
                                            editor.putString("first_name", first_name);
                                            editor.putString("last_name", last_name);
                                            editor.putString("phone", phone);
                                            editor.putString("email", email);
                                            editor.putString("handle", handle);
                                            editor.putString("email_view", email_view);
                                            editor.putString("password", password);
                                            editor.putString("dob", dob);
                                            editor.putString("dob_view", dob_view);
                                            editor.putString("gender", gender);
                                            editor.putString("gender_view", gender_view);
                                            editor.putString("profile_image", profile_image);
                                            editor.putString("image_view", image_view);
                                            editor.putString("statusreceive", statusreceive);
                                            editor.putString("verify_code", verify_code);
                                            editor.putString("current_city", current_city);
                                            editor.putString("favourite_venue", favourite_venue);
                                            editor.putString("entertainment_view", entertainment_view);
                                            editor.putString("QB_UserId", String.valueOf(qbUser.getId()));
                                            editor.commit();







                                        }

                                        @Override
                                        public void onError(QBResponseException e) {

                                            Toast.makeText(LoginActivity.this, "Some problem occured!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
//                                        Button_Login.setEnabled(true);
//                                        Button_Login.setClickable(true);

                                            //Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                                            progressBar.setVisibility(View.GONE);
                                            Button_Login.setEnabled(true);
                                            Button_Login.setClickable(true);

                                        }
                                    });

                                }

                                @Override
                                public void onError(QBResponseException e) {

                                    progressBar.setVisibility(View.GONE);
                                    Button_Login.setEnabled(true);
                                    Button_Login.setClickable(true);

                                }
                            });

//                            progressBar.setVisibility(View.GONE);
//                            Button_Login.setEnabled(true);
//                            Button_Login.setClickable(true);



                        }


                    }else {

                        progressBar.setVisibility(View.GONE);
                        Button_Login.setEnabled(true);
                        Button_Login.setClickable(true);

                    }




                } catch (JSONException e) {
                    e.printStackTrace();

                    progressBar.setVisibility(View.GONE);
                    Button_Login.setEnabled(true);
                    Button_Login.setClickable(true);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Button_Login.setEnabled(true);
                Button_Login.setClickable(true);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);


    }


    //
// Subscribe to Push Notifications
    public void subscribeToPushNotifications(String registrationID) {

        progressBar.setVisibility(View.VISIBLE);

        QBSubscription subscription = new QBSubscription(QBNotificationChannel.GCM);
        subscription.setEnvironment(QBEnvironment.PRODUCTION);
        //
        String deviceId;
        final TelephonyManager mTelephony = (TelephonyManager) getSystemService(
                Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            deviceId = mTelephony.getDeviceId(); //*** use for mobiles
        } else {
            deviceId = android.provider.Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID); //*** use for tablets
        }
        subscription.setDeviceUdid(deviceId);
        //
        subscription.setRegistrationID(registrationID);
        //

        QBPushNotifications.createSubscription(subscription).performAsync(new QBEntityCallback<ArrayList<QBSubscription>>() {
            @Override
            public void onSuccess(ArrayList<QBSubscription> qbSubscriptions, Bundle bundle) {

                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();



                progressBar.setVisibility(View.GONE);
                Button_Login.setEnabled(true);
                Button_Login.setClickable(true);

            }

            @Override
            public void onError(QBResponseException e) {

                progressBar.setVisibility(View.GONE);
                Button_Login.setEnabled(true);
                Button_Login.setClickable(true);

                SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear();
                editor.commit();

            }
        });

    }


//    //
//// Subscribe to Push Notifications
//    public void subscribeToPushNotifications(String regId) {
//        String deviceId = ((TelephonyManager) getBaseContext()
//                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
//
//
//
//        QBMessages.subscribeToPushNotificationsTask(regId, deviceId,
//                QBEnvironment.DEVELOPMENT,
//                new QBEntityCallbackImpl<ArrayList<QBSubscription>>() {
//                    @Override
//                    public void onSuccess(ArrayList<QBSubscription> result,
//                                          Bundle params) {
//                        Log.d("Login", "Successfully Registered");
//                    }
//
//                    @Override
//                    public void onError(List<String> errors) {
//                        Log.d("Login", "e : " + errors);
//                    }
//                });
//
//    }


    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences() {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ClickedButton_FB) {

            callbackManager.onActivityResult(requestCode, resultCode, data);


        } else {

            loginButton.onActivityResult(requestCode, resultCode, data);

        }

    }


    // ---- Generate KeyHash for Facebook
    public void getFbKeyHash(String packageName) {

        Log.i("getFbKeyHash", "call");

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                // --- >> C15+g0w3ofdlyANnJjHrf1tZbqQ=
                Log.i("KeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                // --- >> 2jmj7l5rSw0yVb/vlWAYkK/YBwk=
                System.out.println("KeyHash : " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.i("NameNotFoundExp : ", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.i("NoSuchAlgorithmExp : ", e.toString());
        }

    }

    public void fbUserIdCheck(final String fbId) {


        progressBar.setVisibility(View.VISIBLE);


        String url = AppData.DomainUrl + "fbid_check?fb_id=" + fbId;

        Log.d("fb_user_Check", ":::" + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String message = null, first_name = "", last_name = "", handle = "", email = "";


                try {
                    boolean status = response.getBoolean("response");
                    message = response.getString("message");


                    if (status == false) {

                        Log.d("Existing User", "------------------------------------");

                        JSONObject info_array = response.getJSONObject("info_array");

                        String user_id = info_array.getString("user_id");
                        first_name = info_array.getString("first_name");
                        last_name = info_array.getString("last_name");
                        String phone = info_array.getString("phone");
                        email = info_array.getString("email");
                        handle = info_array.getString("handle");
                        String email_view = info_array.getString("email_view");
                        //String password = info_array.getString("password");
                        String dob = info_array.getString("dob");
                        String dob_view = info_array.getString("dob_view");
                        String gender = info_array.getString("gender");
                        String gender_view = info_array.getString("gender_view");
                        String profile_image = info_array.getString("profile_image");
                        String image_view = info_array.getString("image_view");
                        String qb_userid = info_array.getString("qb_userid");
                        //String added_on = info_array.getString("added_on");
//                    String statusreceive = info_array.getString("status");
//                    String verify_code = info_array.getString("verify_code");
//                    String current_city = info_array.getString("current_city");
//                    String favourite_venue = info_array.getString("favourite_venue");
//                    String entertainment_view = info_array.getString("entertainment_view");

                        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("UserId", user_id);
                        editor.putString("first_name", first_name);
                        editor.putString("last_name", last_name);
                        editor.putString("phone", phone);
                        editor.putString("email", email);
                        editor.putString("handle", handle);
                        editor.putString("email_view", email_view);
                        //editor.putString("password",password);
                        editor.putString("dob", dob);
                        editor.putString("dob_view", dob_view);
                        editor.putString("gender", gender);
                        editor.putString("gender_view", gender_view);
                        editor.putString("profile_image", profile_image);
                        editor.putString("image_view", image_view);
                        editor.putString("QB_UserId", qb_userid);
//                    editor.putString("statusreceive",statusreceive);
//                    editor.putString("verify_code",verify_code);
//                    editor.putString("current_city",current_city);
//                    editor.putString("favourite_venue",favourite_venue);
//                    editor.putString("entertainment_view",entertainment_view);
                        editor.commit();


                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();


                    } else {

                        Log.d("Non Existing User", "------------------------------------");

                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                        intent.putExtra("from", "facebook");

                        intent.putExtra("fb_id", fbID);
                        intent.putExtra("fb_accesstoken", fbAccessToken);
                        intent.putExtra("name", fbFullName);
                        intent.putExtra("email", fbEmail);
                        intent.putExtra("gender", fbGender.substring(0, 1).toUpperCase() + fbGender.substring(1, fbGender.length()));

                        if (fbBirthDay.equals("Not mention")) {

                            intent.putExtra("dob", "Not mention");

                        } else {

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String dob = simpleDateFormat.format(Date.parse(fbBirthDay));

                            intent.putExtra("dob", dob);

                        }


                        startActivity(intent);

                    }

                    progressBar.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();

                    progressBar.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public void twUserIdCheck(final String twId) {


        progressBar.setVisibility(View.VISIBLE);


        String url = AppData.DomainUrl + "twtid_check?twt_id=" + twId;

        Log.d("tw_user_Check", ":::" + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String message = null, first_name = "", last_name = "", handle = "", email = "";


                try {
                    boolean status = response.getBoolean("response");
                    message = response.getString("message");


                    if (status == false) {

                        Log.d("Existing User", "------------------------------------");

                        JSONObject info_array = response.getJSONObject("info_array");

                        String user_id = info_array.getString("user_id");
                        first_name = info_array.getString("first_name");
                        last_name = info_array.getString("last_name");
                        String phone = info_array.getString("phone");
                        email = info_array.getString("email");
                        handle = info_array.getString("handle");
                        String email_view = info_array.getString("email_view");
                        //String password = info_array.getString("password");
                        String dob = info_array.getString("dob");
                        String dob_view = info_array.getString("dob_view");
                        String gender = info_array.getString("gender");
                        String gender_view = info_array.getString("gender_view");
                        String profile_image = info_array.getString("profile_image");
                        String image_view = info_array.getString("image_view");


                        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("UserId", user_id);
                        editor.putString("first_name", first_name);
                        editor.putString("last_name", last_name);
                        editor.putString("phone", phone);
                        editor.putString("email", email);
                        editor.putString("handle", handle);
                        editor.putString("email_view", email_view);
                        //editor.putString("password",password);
                        editor.putString("dob", dob);
                        editor.putString("dob_view", dob_view);
                        editor.putString("gender", gender);
                        editor.putString("gender_view", gender_view);
                        editor.putString("profile_image", profile_image);
                        editor.putString("image_view", image_view);

                        editor.commit();


                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();


                    } else {

                        Log.d("Non Existing User", "------------------------------------");

                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                        intent.putExtra("from", "twitter");

                        intent.putExtra("tw_id", twID);
                        intent.putExtra("tw_name", twName.replace("_", " "));
                        intent.putExtra("tw_authToken", twAuthToken);

                        startActivity(intent);

                    }

                    progressBar.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();

                    progressBar.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }


}
