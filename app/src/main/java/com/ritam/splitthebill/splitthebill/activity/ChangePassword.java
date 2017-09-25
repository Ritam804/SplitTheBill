package com.ritam.splitthebill.splitthebill.activity;

import android.content.SharedPreferences;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {

    LinearLayout Button_Submit,Button_Back;

    EditText CurrentPass,NewPass,ConfirmNewPass;

    ProgressBar ChangeProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Button_Back = (LinearLayout) findViewById(R.id.ll_back_changepass);
        Button_Submit = (LinearLayout) findViewById(R.id.submit);

        CurrentPass = (EditText) findViewById(R.id.et_currentpass);
        NewPass = (EditText) findViewById(R.id.newpass);
        ConfirmNewPass = (EditText) findViewById(R.id.confirmnewpass);

        ChangeProgress = (ProgressBar) findViewById(R.id.progressBar);

        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        Button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!CurrentPass.getText().toString().trim().equals("") && !CurrentPass.getText().toString().trim().equals("null") && !CurrentPass.getText().toString().trim().equals(null) && CurrentPass.getText().toString().trim().length() > 0){

                    if (!NewPass.getText().toString().trim().equals("") && !NewPass.getText().toString().trim().equals("null") && !NewPass.getText().toString().trim().equals(null) && NewPass.getText().toString().trim().length() > 0){

                        if (!ConfirmNewPass.getText().toString().trim().equals("") && !ConfirmNewPass.getText().toString().trim().equals("null") && !ConfirmNewPass.getText().toString().trim().equals(null) && ConfirmNewPass.getText().toString().trim().length() > 0){


                            if (NewPass.getText().toString().trim().equals(ConfirmNewPass.getText().toString().trim())){

                                if (NewPass.getText().toString().length() >= 8){


                                    //fire the url to change password..............

                                    Vol_ChangePass();


                                }else {

                                    Toast.makeText(ChangePassword.this,"Password must have minimum 8 characters",Toast.LENGTH_SHORT).show();

                                }

                            }else {

                                Toast.makeText(ChangePassword.this,"Naew Password and Confirm New Password are not matching",Toast.LENGTH_SHORT).show();

                            }


                        }else {

                            Toast.makeText(ChangePassword.this,"Please enter confirm new password",Toast.LENGTH_SHORT).show();

                        }

                    }else {

                        Toast.makeText(ChangePassword.this,"Please enter new password",Toast.LENGTH_SHORT).show();

                    }

                }else {

                    Toast.makeText(ChangePassword.this,"Please enter current password",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public void Vol_ChangePass(){

        ChangeProgress.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"changepassword_control?id="+sharedPreferences.getString("UserId","")+"&current_pass="+CurrentPass.getText().toString().trim()+"&new_pass="+NewPass.getText().toString().trim();

        Log.v("ChangePassword_Url::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String message = null;

                try {
                    String status = response.getString("status");
                    message = response.getString("message");

                    if (status.equals("success")){

                        finish();

                    }

                    ChangeProgress.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();

                    ChangeProgress.setVisibility(View.GONE);
                }

                Toast.makeText(ChangePassword.this,message,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ChangeProgress.setVisibility(View.GONE);

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
