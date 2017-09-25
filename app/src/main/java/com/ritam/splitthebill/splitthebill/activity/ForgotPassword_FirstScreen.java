package com.ritam.splitthebill.splitthebill.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ForgotPassword_FirstScreen extends AppCompatActivity {

    LinearLayout Button_Submit,Button_Back;
    EditText Email;
    ProgressBar ForgorProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password__first_screen);

        Button_Submit = (LinearLayout) findViewById(R.id.request);
        Button_Back = (LinearLayout) findViewById(R.id.back);
        Email = (EditText) findViewById(R.id.et_email);

        ForgorProgress = (ProgressBar) findViewById(R.id.progressBar);

        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        Button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Email.getText().toString().trim().length() > 0){

                    try {
                        Vol_ForgetPass(URLEncoder.encode(Email.getText().toString().trim(),"utf-8"));
                        Email.setText("");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }else {

                    Toast.makeText(ForgotPassword_FirstScreen.this,"Please give your email id",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void Vol_ForgetPass(String email){

        ForgorProgress.setVisibility(View.VISIBLE);

        String url = AppData.DomainUrlForProfile+"forgetpassword_control?email="+email;

        Log.v("ForgotPass_First_Url::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String message = null;

                try {
                    String status = response.getString("status");
                    message = response.getString("message");

                    if (status.equals("success")){

                        startActivity(new Intent(ForgotPassword_FirstScreen.this,ForgotPassword_SecondScreen.class));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(ForgotPassword_FirstScreen.this,message,Toast.LENGTH_SHORT).show();

                ForgorProgress.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ForgorProgress.setVisibility(View.GONE);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
