package com.ritam.splitthebill.splitthebill.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONException;
import org.json.JSONObject;

public class Settings extends AppCompatActivity {

    LinearLayout Button_Back;

    RelativeLayout Button_Logout, Button_Notifications, Button_TermsAndCondition, Button_Privacy, Button_Share, Button_PrivateAccount,Button_ChangePass;

    ProgressBar LogoutProgress;

    ToggleButton Toggle_Button_Notification, Toggle_Button_PrivateAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Button_Back = (LinearLayout) findViewById(R.id.ll_back_settings);

        Button_Logout = (RelativeLayout) findViewById(R.id.rl_logout);

        Button_Notifications = (RelativeLayout) findViewById(R.id.rl_notifivations);

        Button_TermsAndCondition = (RelativeLayout) findViewById(R.id.rl_terms);
        Button_Privacy = (RelativeLayout) findViewById(R.id.rl_privacy);

        Button_Share = (RelativeLayout) findViewById(R.id.rl_share);

        Toggle_Button_Notification = (ToggleButton) findViewById(R.id.tb_notification);

        Button_PrivateAccount = (RelativeLayout) findViewById(R.id.rl_privateac);

        Toggle_Button_PrivateAccount = (ToggleButton) findViewById(R.id.private_ac);

        LogoutProgress = (ProgressBar) findViewById(R.id.progressBar7);

        Button_ChangePass = (RelativeLayout) findViewById(R.id.rl_changepass);


        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);


        if (sharedPreferences.getString("Push_Status", "").equals("1")) {

            Toggle_Button_Notification.setChecked(true);

        } else if (sharedPreferences.getString("Push_Status", "").equals("0")) {

            Toggle_Button_Notification.setChecked(false);

        }

        if (sharedPreferences.getString("Private_Status", "").equals("1")) {

            Toggle_Button_PrivateAccount.setChecked(true);

        } else if (sharedPreferences.getString("Private_Status", "").equals("0")) {

            Toggle_Button_PrivateAccount.setChecked(false);

        }


        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        Button_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Vol_Logout();

            }
        });

        Button_Notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Vol_Notifications();

            }
        });

        Button_PrivateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Vol_PrivateAccount();

            }
        });

        Button_ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Settings.this,ChangePassword.class);
                startActivity(intent);

            }
        });


        Button_TermsAndCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout Button_Cross;
                WebView webView;
                TextView txt_header;

                View view1 = LayoutInflater.from(Settings.this).inflate(R.layout.dialog_terms, null);
                final Dialog dialog = new Dialog(Settings.this, R.style.MaterialDialogSheet);
                dialog.setContentView(view1);
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();


                Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_dialog_cross);
                txt_header = (TextView) view1.findViewById(R.id.txt_header);
                txt_header.setText("Terms & Conditions");

                webView = (WebView) view1.findViewById(R.id.wv_terms);

                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);

                //webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("https://esolz.co.in/lab6/stb/termsprivacy_control?mode=t");


                Button_Cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });

            }
        });

        Button_Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout Button_Cross;
                WebView webView;
                TextView txt_header;

                View view1 = LayoutInflater.from(Settings.this).inflate(R.layout.dialog_terms, null);
                final Dialog dialog = new Dialog(Settings.this, R.style.MaterialDialogSheet);
                dialog.setContentView(view1);
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();


                Button_Cross = (LinearLayout) view1.findViewById(R.id.ll_dialog_cross);
                txt_header = (TextView) view1.findViewById(R.id.txt_header);
                txt_header.setText("Privacy Policy");

                webView = (WebView) view1.findViewById(R.id.wv_terms);

                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);

                //webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("https://esolz.co.in/lab6/stb/termsprivacy_control?mode=p");


                Button_Cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });
            }
        });


        Button_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                //shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "STB");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Join me on Split The Bill (STB) the newest Entertainment Nightlife app. Browse events in your city, split the bill anywhere and with everyone and book or join a V.I.P table at your favorite clubs!\n" +
                        "Download the app for free at:");
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "STB"));

            }
        });


    }


    public void Vol_Logout() {

        LogoutProgress.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "logout_control?id=" + sharedPreferences.getString("UserId", "");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String message = null;

                try {
                    String status = response.getString("status");
                    message = response.getString("message");


                    if (status.equals("success")) {

                        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        startActivity(new Intent(Settings.this, LoginActivity.class));
                        finish();

                    }


                    LogoutProgress.setVisibility(View.GONE);
                    Toast.makeText(Settings.this, message, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    LogoutProgress.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                LogoutProgress.setVisibility(View.GONE);

            }
        });

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public void Vol_Notifications() {

        LogoutProgress.setVisibility(View.VISIBLE);

        String url = null;

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        if (sharedPreferences.getString("Push_Status", "").equals("1")) {

            url = AppData.DomainUrlForProfile + "pushsettings_control?userid=" + sharedPreferences.getString("UserId", "") + "&status=0";


        } else if (sharedPreferences.getString("Push_Status", "").equals("0")) {

            url = AppData.DomainUrlForProfile + "pushsettings_control?userid=" + sharedPreferences.getString("UserId", "") + "&status=1";


        }


        Log.v("Notification_OnOff_Url::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("success")) {

                        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);


                        if (sharedPreferences.getString("Push_Status", "").equals("1")) {


                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Push_Status", "0");

                            editor.commit();

                            Toggle_Button_Notification.setChecked(false);


                        } else if (sharedPreferences.getString("Push_Status", "").equals("0")) {


                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Push_Status", "1");

                            editor.commit();

                            Toggle_Button_Notification.setChecked(true);


                        }

                    }

                    LogoutProgress.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogoutProgress.setVisibility(View.GONE);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                LogoutProgress.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);


    }

    public void Vol_PrivateAccount() {

        LogoutProgress.setVisibility(View.VISIBLE);

        String url = null;

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        if (sharedPreferences.getString("Private_Status", "").equals("1")) {

            url = AppData.DomainUrlForProfile + "privatesettings_control?userid=" + sharedPreferences.getString("UserId", "") + "&status=0";


        } else if (sharedPreferences.getString("Private_Status", "").equals("0")) {

            url = AppData.DomainUrlForProfile + "privatesettings_control?userid=" + sharedPreferences.getString("UserId", "") + "&status=1";


        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String message = null;

                try {


                    String status = response.getString("status");
                    message = response.getString("message");


                    if (status.equals("success")) {


                        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

                        if (sharedPreferences.getString("Private_Status", "").equals("1")) {

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Private_Status", "0");

                            editor.commit();

                            Toggle_Button_PrivateAccount.setChecked(false);

                        } else if (sharedPreferences.getString("Private_Status", "").equals("0")) {

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Private_Status", "1");

                            editor.commit();

                            Toggle_Button_PrivateAccount.setChecked(true);

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                LogoutProgress.setVisibility(View.GONE);

                Toast.makeText(Settings.this, message, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                LogoutProgress.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
