package com.ritam.splitthebill.splitthebill.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.fragments.FirstPage_SignUp;
import com.ritam.splitthebill.splitthebill.R;

public class SignUpActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    RelativeLayout Button_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first__sign_up);

        Button_Back = (RelativeLayout) findViewById(R.id.rl_back);

        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppData.SignUpPageNumber == 1){

                    AppData.SignUpName = "";
                    AppData.SignUpUserName = "";
                    AppData.SignUpEmail = "";

                    finish();

                }else {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    fragmentTransaction.replace(R.id.fl_signup, FirstPage_SignUp.newInstance());
                    fragmentTransaction.commit();

                }



            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_signup, FirstPage_SignUp.newInstance());
        fragmentTransaction.commit();



    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
