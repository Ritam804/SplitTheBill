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

public class ForgotPassword_SecondScreen extends AppCompatActivity {

    LinearLayout Button_Back, Button_Submit;

    EditText VerificationCode, NewPassword, ConfirmNewPassword;

    ProgressBar ForgotProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password__second_screen);


        Button_Back = (LinearLayout) findViewById(R.id.ll_back_changepass);
        Button_Submit = (LinearLayout) findViewById(R.id.submit);

        VerificationCode = (EditText) findViewById(R.id.et_verification);
        NewPassword = (EditText) findViewById(R.id.newpass);
        ConfirmNewPassword = (EditText) findViewById(R.id.confirmnewpass);

        ForgotProgress = (ProgressBar) findViewById(R.id.progressBar);

        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        Button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (VerificationCode.getText().toString().trim().length() > 0) {

                    if (NewPassword.getText().toString().trim().length() > 0) {

                        if (NewPassword.getText().toString().trim().length() >= 8){

                            if (ConfirmNewPassword.getText().toString().trim().length() > 0) {

                                if (NewPassword.getText().toString().trim().equals(ConfirmNewPassword.getText().toString().trim())) {


                                    Vol_ForgotPassword();

                                } else {

                                    Toast.makeText(ForgotPassword_SecondScreen.this, "New password and confirm new password are not same", Toast.LENGTH_SHORT).show();


                                }

                            } else {

                                Toast.makeText(ForgotPassword_SecondScreen.this, "Please enter confirm new password", Toast.LENGTH_SHORT).show();


                            }

                        }else {
                            Toast.makeText(ForgotPassword_SecondScreen.this, "Please must have more than 8 charecters", Toast.LENGTH_SHORT).show();
                        }



                    } else {

                        Toast.makeText(ForgotPassword_SecondScreen.this, "Please enter new password", Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Toast.makeText(ForgotPassword_SecondScreen.this, "Please enter the verification code", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public void Vol_ForgotPassword() {

        ForgotProgress.setVisibility(View.VISIBLE);

        String url = AppData.DomainUrlForProfile + "forgetpasswordnext_control?code=" + VerificationCode.getText().toString().trim() + "&password=" + NewPassword.getText().toString().trim();

        Log.v("ForgotPass_Second_Url::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String message = null;

                try {
                    String status = response.getString("status");
                    message = response.getString("message");

                    if (status.equals("success")) {

                        startActivity(new Intent(ForgotPassword_SecondScreen.this, LoginActivity.class));
                        finish();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(ForgotPassword_SecondScreen.this, message, Toast.LENGTH_SHORT).show();

                ForgotProgress.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ForgotProgress.setVisibility(View.GONE);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
