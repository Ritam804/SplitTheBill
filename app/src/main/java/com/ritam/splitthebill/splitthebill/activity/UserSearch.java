package com.ritam.splitthebill.splitthebill.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.SearchUserListAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import setergeter.UserSearch_SetterGetter;

public class UserSearch extends AppCompatActivity {

    LinearLayout Button_SearchBack,Button_Cancel;
    EditText SearchText;

    RecyclerView UserList;

    TextView NoUserText;

    ArrayList<UserSearch_SetterGetter> AllUserValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        Button_SearchBack = (LinearLayout) findViewById(R.id.ll_searchback);
        Button_Cancel = (LinearLayout) findViewById(R.id.ll_cancel);
        SearchText = (EditText) findViewById(R.id.et_search);

        UserList = (RecyclerView) findViewById(R.id.rv_users);
        UserList.setHasFixedSize(true);
        UserList.setLayoutManager(new LinearLayoutManager(UserSearch.this));

        NoUserText = (TextView) findViewById(R.id.tv_nouser);

        SearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button_Cancel.setVisibility(View.VISIBLE);

            }
        });

        Button_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button_Cancel.setVisibility(View.GONE);

            }
        });

        Button_SearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        SearchText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //performSearch();
                    Vol_SearchUser();
                    return true;
                }
                return false;
            }
        });

    }


    public void Vol_SearchUser(){

        String url = null;

        SharedPreferences sharedPreferences =  getSharedPreferences("AutoLogin",MODE_PRIVATE);

        try {
            url = AppData.DomainUrlForProfile+"usersearch_control?name="+ URLEncoder.encode(SearchText.getText().toString(),"utf-8")+"&logged_id="+sharedPreferences.getString("UserId","");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("success") && message.equals("data found")){


                        JSONArray Details = response.getJSONArray("Details");

                        if (Details.length() > 0){

                            UserList.setVisibility(View.VISIBLE);
                            NoUserText.setVisibility(View.GONE);

                            AllUserValue = new ArrayList<UserSearch_SetterGetter>();

                            for (int i=0;i<Details.length();i++){

                                JSONObject UserDetails = Details.getJSONObject(i);

                                String userid = UserDetails.getString("userid");
                                String name = UserDetails.getString("name");
                                String image = UserDetails.getString("image");
                                String rating = UserDetails.getString("rating");
                                int follow_status = UserDetails.getInt("follow_status");


                                UserSearch_SetterGetter userSearch_setterGetter = new UserSearch_SetterGetter(userid,name,image,rating,follow_status);
                                AllUserValue.add(userSearch_setterGetter);

                            }

                            SearchUserListAdapter searchUserListAdapter = new SearchUserListAdapter(AllUserValue,UserSearch.this);
                            UserList.setAdapter(searchUserListAdapter);


                        }else {

                            UserList.setVisibility(View.GONE);
                            NoUserText.setVisibility(View.VISIBLE);

                        }


                    }else {

                        UserList.setVisibility(View.GONE);
                        NoUserText.setVisibility(View.VISIBLE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
