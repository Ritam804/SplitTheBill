package com.ritam.splitthebill.splitthebill.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

public class SplitPercentage extends AppCompatActivity {

    ImageView back;
    TextView confirm_button,cancel,Text_Amount,Text_PersonCount,Text_VenueName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.split_percentage_layout);

        back= (ImageView) findViewById(R.id.back);
        confirm_button= (TextView) findViewById(R.id.confirm_button);
        cancel= (TextView) findViewById(R.id.cancel);
        Text_Amount = (TextView) findViewById(R.id.tv_amount);
        Text_PersonCount = (TextView) findViewById(R.id.tv_personcount);
        Text_VenueName = (TextView) findViewById(R.id.tv_venuename);

        Text_Amount.setText(getIntent().getExtras().getString("Amount"));
        Text_PersonCount.setText(getIntent().getExtras().getString("PersonCount"));
        Text_VenueName.setText(getIntent().getExtras().getString("VenueName"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppData.Amount = Text_Amount.getText().toString();
                startActivity(new Intent(SplitPercentage.this,Payment.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
