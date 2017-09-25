package com.ritam.splitthebill.splitthebill.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ritam.splitthebill.splitthebill.R;

public class HelpActivity extends AppCompatActivity {

    LinearLayout Button_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Button_Back = (LinearLayout) findViewById(R.id.ll_back_help);


        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });
    }
}
