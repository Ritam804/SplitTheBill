package com.ritam.splitthebill.splitthebill.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;

public class AddCard extends AppCompatActivity {

    TextView pay;
    ImageView checkbox;
    LinearLayout back;

    boolean check_status=true;

    CardInputWidget mCardInputWidget;

    LinearLayout mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);

        pay= (TextView) findViewById(R.id.pay);
        //checkbox= (ImageView) findViewById(R.id.checkbox);
        back= (LinearLayout) findViewById(R.id.back);

        mProgress = (LinearLayout) findViewById(R.id.ll_progress);

        mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);




//        String s = "Pay <b>$ 200</b>";
//
//        pay.setText(Html.fromHtml(s));

//        checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(check_status==true) {
//                    checkbox.setImageResource(R.drawable.check);
//                    check_status=false;
//                }
//                else {
//                    checkbox.setImageResource(R.drawable.uncheck);
//                    check_status=true;
//                }
//
//
//            }
//        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgress.setVisibility(View.VISIBLE);

                Log.v("Add::::","Clicked");

                Card cardToSave = mCardInputWidget.getCard();
                if (cardToSave == null) {

                    mProgress.setVisibility(View.GONE);
                    Toast.makeText(AddCard.this,"Invalid Card Data",Toast.LENGTH_SHORT).show();
                }else {

                    Log.v("Add::::","Valid");

                    Stripe stripe = new Stripe(AddCard.this, "pk_test_0lk1ou6myiZRaphGQeWYILkt");
                    stripe.createToken(
                            cardToSave,
                            new TokenCallback() {
                                public void onSuccess(Token token) {
                                    // Send token to your server

                                    Log.v("AddToken::::",token.getId());

                                    mProgress.setVisibility(View.GONE);

                                    Vol_CardAdd(token.getId());

                                }
                                public void onError(Exception error) {
                                    // Show localized error message

                                    mProgress.setVisibility(View.GONE);

                                    Toast.makeText(AddCard.this,"Something went wrong",Toast.LENGTH_SHORT).show();

                                }
                            }
                    );

                }

            }
        });


    }



    public void Vol_CardAdd(String token){


        mProgress.setVisibility(View.VISIBLE);

        final SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"cardadd_control?token="+token+"&userid="+sharedPreferences.getString("UserId","");

        Log.v("AddUrl::::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("AddResponce:::::",response.toString());

                String message = null;

                try {
                    String status = response.getString("status");

                    message = response.getString("message");

                    if (status.equals("success")){

                        startActivity(new Intent(AddCard.this,Payment.class));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(AddCard.this,message,Toast.LENGTH_SHORT).show();

                mProgress.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                mProgress.setVisibility(View.GONE);

                Log.v("AddResponce:::::",error.toString());

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
