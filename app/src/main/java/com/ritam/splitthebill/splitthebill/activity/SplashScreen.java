package com.ritam.splitthebill.splitthebill.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import java.util.ArrayList;

import services.FetchingContactService;

public class SplashScreen extends AppCompatActivity {

    public static final int REQUEST_READ_CON = 0;
    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    public static final int REQUEST_READ_EXTERNAL_STORAGE = 3;
    public static final int READ_PHONE_STATE = 4;

    int UnreadMessageCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        checkPermission();
    }

    public void checkPermission(){
        if ((int)Build.VERSION.SDK_INT < 23)
        {



            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Log.v("FetchingService","Calling Service");


                    SharedPreferences sharedPreferences =  getSharedPreferences("AutoLogin",MODE_PRIVATE);

                    try {

                        if (sharedPreferences.getString("AutoLogin","").equals("YES")){

                            startActivity(new Intent(SplashScreen.this,HomeActivity.class));
                            finish();

                        }else {

                            startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                            finish();

                        }

                    }catch (Exception e){

                        startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                        finish();

                    }

                }
            }, 3000);


            Intent i = new Intent(getBaseContext(), FetchingContactService.class);
            i.setAction("Hello");
            startService(i);







        }else {

            if (ContextCompat.checkSelfPermission(SplashScreen.this,
                    Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {


                Log.d("Request for Contacts permission","--------------------");

                ActivityCompat.requestPermissions(SplashScreen.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_READ_CON);



            }else if (ContextCompat.checkSelfPermission(SplashScreen.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {


                Log.d("Request for Camera permission","--------------------");

                ActivityCompat.requestPermissions(SplashScreen.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);

            }else if(ContextCompat.checkSelfPermission(SplashScreen.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {

                Log.d("Request for Write and write external storage permission","--------------------");

                ActivityCompat.requestPermissions(SplashScreen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);


            }else if (ContextCompat.checkSelfPermission(SplashScreen.this,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {


                Log.d("Request for Camera permission","--------------------");

                ActivityCompat.requestPermissions(SplashScreen.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        READ_PHONE_STATE);

            }else {


                Log.d("user already given all permission","--------------------");



                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Log.v("FetchingService","Calling Service");



                        SharedPreferences sharedPreferences =  getSharedPreferences("AutoLogin",MODE_PRIVATE);

                        try {

                            if (sharedPreferences.getString("AutoLogin","").equals("YES")){

                                startActivity(new Intent(SplashScreen.this,HomeActivity.class));
                                finish();

                            }else {

                                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                                finish();

                            }

                        }catch (Exception e){

                            startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                            finish();

                        }

                    }
                }, 3000);


//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {


                        Log.v("FetchingService","Calling Service");
                        Intent i = new Intent(getBaseContext(), FetchingContactService.class);
                        i.setAction("Hello");
                        startService(i);

//                    }
//                }, 0000);




            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CON:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("Granted Contacts permission","--------------------");

                    checkPermission();


                } else {
                    Log.d("Denied Contacts permission","--------------------");

                    checkPermission();

                    Toast.makeText(SplashScreen.this, "You need to grant the permission for using phone Contacts", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case REQUEST_CAMERA:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    Log.d("Granted Camera permission","--------------------");

                    checkPermission();

                } else {
                    Log.d("Denied Camera permission","--------------------");

                    checkPermission();

                    Toast.makeText(SplashScreen.this, "You need to grant the permission for using Camera", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case REQUEST_WRITE_EXTERNAL_STORAGE:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    Log.d("Granted External read and write permission","--------------------");

                    checkPermission();

                } else {
                    Log.d("Denied External read and Write permission","--------------------");

                    checkPermission();

                    Toast.makeText(SplashScreen.this, "You need to grant the permission for Read and Write External Storage", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case READ_PHONE_STATE:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    Log.d("Granted Camera permission","--------------------");

                    checkPermission();

                } else {
                    Log.d("Denied Camera permission","--------------------");

                    checkPermission();

                    Toast.makeText(SplashScreen.this, "You need to grant this permission", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
