package com.ritam.splitthebill.splitthebill.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.ContactAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.fragments.EventListing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import setergeter.Contacts_SetterGetter;
import setergeter.SelectedContact_SetterGetter;
import setergeter.SpliterList_SetterGetter;
import setergeter.Spltter_Contacts_SetterGetter;

public class ContactPage extends AppCompatActivity {

    ArrayList<Contacts_SetterGetter> Contacts = new ArrayList<Contacts_SetterGetter>();
    ArrayList<Spltter_Contacts_SetterGetter> Contacts1 = new ArrayList<Spltter_Contacts_SetterGetter>();
    ArrayList<Contacts_SetterGetter> SearchContacts = new ArrayList<Contacts_SetterGetter>();
    ArrayList<Spltter_Contacts_SetterGetter> SPLITTER_SearchContacts = new ArrayList<Spltter_Contacts_SetterGetter>();


    ProgressBar ContactProgress;

    RecyclerView recyclerView;

    ContactAdapter adapter=null;
    public ImageView check_id;

    LinearLayout ll_back;
    TextView no_contact,button_share;
    public boolean check_status=true;

    LinearLayout Close_SearchBar;
    EditText SearchText;
    ImageView Button_Search;
    RelativeLayout SearchBar;
    FastScroller fastScroller;


    RelativeLayout Button_PhoneContacts,Button_Splitters;

    ArrayList<SpliterList_SetterGetter> AllSplitterDetails;

    LinearLayout Progress;

    String SelectedTab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);


//        AppData.Splitter_contactVOList = new ArrayList<Spltter_Contacts_SetterGetter>();
//        AppData.Splitter_contactVOList.clear();

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        fastScroller = (FastScroller) findViewById(R.id.fastscroll);


        no_contact = (TextView) findViewById(R.id.no_contact);
        button_share = (TextView) findViewById(R.id.button_share);
        //check_id = (ImageView) findViewById(R.id.check_id);


        Button_Search = (ImageView) findViewById(R.id.iv_search);
        SearchText = (EditText) findViewById(R.id.et_search);
        Close_SearchBar = (LinearLayout) findViewById(R.id.ll_close);
        SearchBar = (RelativeLayout) findViewById(R.id.rl_search);

        Button_PhoneContacts = (RelativeLayout) findViewById(R.id.rl_phonecontacts);
        Button_Splitters = (RelativeLayout) findViewById(R.id.rl_splitters);

        Button_PhoneContacts.setSelected(true);
        Button_Splitters.setSelected(false);

        Progress = (LinearLayout) findViewById(R.id.ll_progress);

        ll_back = (LinearLayout) findViewById(R.id.ll_back);

        ll_back.setOnClickListener(new View.OnClickListener() {

            int counter;
            @Override
            public void onClick(View v) {
                //AppData.Contact_Block = false;

//                Log.v("ContactPage::","BackButton");
//                Log.v("ContactPage::","AppData.ContactPageCount::"+AppData.ContactPageCount);
//                Log.v("ContactPage::","AppData.contactVOList::"+AppData.contactVOList.size());
//                Log.v("ContactPage::","AppData.Splitter_contactVOList::"+AppData.Splitter_contactVOList.size());
//                Log.v("ContactPage::","AppData.selectedContact::"+AppData.selectedContact.size());


                if (AppData.ContactPageCount == 1){

                    for (int i = 0; i < AppData.contactVOList.size(); i++) {

                        AppData.contactVOList.get(i).setCheckStatus(false);


                    }

                    for (int i = 0; i < AppData.Splitter_contactVOList.size(); i++) {

                        AppData.Splitter_contactVOList.get(i).setCheckStatus(false);


                    }

                    AppData.Contact_Block = false;
                    finish();

                }else {


                    for (int i = 0; i < AppData.contactVOList.size(); i++) {

                        AppData.contactVOList.get(i).setCheckStatus(false);

                    }

                    for (int i = 0; i < AppData.Splitter_contactVOList.size(); i++) {

                        AppData.Splitter_contactVOList.get(i).setCheckStatus(false);

                    }


                    for (int i=0;i<AppData.selectedContact.size();i++){

                        Log.v("ContactPage::","AppData.selectedContact_Purpose::"+AppData.selectedContact.get(i).getPupose());

                        if (!(AppData.selectedContact.get(i).getPupose().equals("Phone"))){

                            AppData.Splitter_contactVOList.get(AppData.selectedContact.get(i).getSelectedPosition()).setCheckStatus(true);

                        }



                    }

                    for (int i=0;i<AppData.selectedContact.size();i++){

                        Log.v("ContactPage::","AppData.selectedContact_Purpose::::::::::::::");

                        Log.v("ContactPage::","AppData.selectedContact_Purpose::"+AppData.selectedContact.get(i).getPupose());

                        if (AppData.selectedContact.get(i).getPupose().equals("Phone")){

                            AppData.contactVOList.get(AppData.selectedContact.get(i).getSelectedPosition()).setCheckStatus(true);

                        }



                    }

                    Log.v("ContactPage::","AppData.selectedContact::"+AppData.selectedContact.size());

                    counter = 0;
                    AppData.selectedContact = new ArrayList<SelectedContact_SetterGetter>();
                    for (int i = 0; i < Contacts.size(); i++) {
                        if (Contacts.get(i).getCheckStatus() == true) {
                            counter++;

                            SelectedContact_SetterGetter contacts_setterGetter = new SelectedContact_SetterGetter(Contacts.get(i).getName(),Contacts.get(i).getNumber(),Contacts.get(i).getPhoto_uri(),Contacts.get(i).getCheckStatus(),i,"Phone");



                            AppData.selectedContact.add(contacts_setterGetter);
                        }
                    }

                    Log.v("ContactPage::","AppData.selectedContact::"+AppData.selectedContact.size());

                    counter = 0;
                    for (int i = 0; i < AppData.Splitter_contactVOList.size(); i++) {
                        if (AppData.Splitter_contactVOList.get(i).isCheckStatus() == true) {
                            counter++;

                            SelectedContact_SetterGetter contacts_setterGetter = new SelectedContact_SetterGetter(AppData.Splitter_contactVOList.get(i).getName(),AppData.Splitter_contactVOList.get(i).getNumber(),AppData.Splitter_contactVOList.get(i).getPhoto_uri(),AppData.Splitter_contactVOList.get(i).isCheckStatus(),i,"Splitter");



                            AppData.selectedContact.add(contacts_setterGetter);
                        }
                    }

                    Log.v("ContactPage::","AppData.selectedContact::"+AppData.selectedContact.size());

                    if (AppData.selectedContact.size() > 0) {
                        AppData.Contact_Block = true;
                        //Toast.makeText(getApplication(),""+counter,Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        AppData.Contact_Block = false;
                        finish();
                    }

                }



            }
        });


        ContactProgress = (ProgressBar) findViewById(R.id.progressBar3);



        SelectedTab = "Phone";

        Contacts = AppData.contactVOList;

        Log.v("Button_PhoneContacts",""+String.valueOf(Contacts.size()));

        if (Contacts != null) {

            Log.v("ContactPage_Recycleview::","1");

            no_contact.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            if (Contacts.size() > 0) {

                Contacts = AppData.contactVOList;
                adapter = new ContactAdapter(ContactPage.this, Contacts,"Phone");
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ContactPage.this));
                recyclerView.setAdapter(adapter);
                fastScroller.setRecyclerView(recyclerView);

            } else {
                Log.v("ContactPage_Recycleview::", "2");
                no_contact.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        } else {
            Log.v("ContactPage_Recycleview::", "1");
            no_contact.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }


        Button_PhoneContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button_PhoneContacts.setSelected(true);
                Button_Splitters.setSelected(false);

                SelectedTab = "Phone";



                Log.v("Button_PhoneContacts",""+String.valueOf(AppData.contactVOList.size()));

                //Contacts.clear();
                Contacts = AppData.contactVOList;

                Log.v("Button_PhoneContacts",""+String.valueOf(Contacts.size()));

                if (Contacts != null) {

                    no_contact.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    Log.v("Button_PhoneContacts",""+String.valueOf(Contacts.size()));

                    if (Contacts.size() > 0) {

                        Log.v("Button_PhoneContacts",""+String.valueOf(Contacts.size()));

                        Contacts = AppData.contactVOList;
                        adapter = new ContactAdapter(ContactPage.this, Contacts,"Phone");
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ContactPage.this));
                        recyclerView.setAdapter(adapter);
                        fastScroller.setRecyclerView(recyclerView);

                    } else {
                        no_contact.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    no_contact.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

            }
        });


        Button_Splitters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button_PhoneContacts.setSelected(false);
                Button_Splitters.setSelected(true);

                SelectedTab = "Splitter";

                Vol_Splitters(1);

            }
        });


        Log.v("Button_PhoneContacts",""+String.valueOf(AppData.contactVOList.size()));

        //Contacts.clear();




        SearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               if (charSequence.length() > 0){

                   no_contact.setVisibility(View.GONE);



                   if (SelectedTab.equals("Phone")){

                       SearchContacts.clear();

                       for(int j=0;j<Contacts.size();j++){
                           if(Contacts.get(j).getName().toString().toLowerCase().contains(charSequence.toString().toLowerCase())){

                               Contacts_SetterGetter contacts_setterGetter=Contacts.get(j);

                               SearchContacts.add(contacts_setterGetter);

                           }
                       }

                   }else {

                       SPLITTER_SearchContacts.clear();

                       for(int j=0;j<AppData.Splitter_contactVOList.size();j++){
                           if(AppData.Splitter_contactVOList.get(j).getName().toString().toLowerCase().contains(charSequence.toString().toLowerCase())){

                               Spltter_Contacts_SetterGetter contacts_setterGetter=AppData.Splitter_contactVOList.get(j);

                               SPLITTER_SearchContacts.add(contacts_setterGetter);

                           }
                       }

                   }




                   adapter=null;

                   if (SelectedTab.equals("Phone")){

                       if (SearchContacts != null) {

                           no_contact.setVisibility(View.GONE);
                           recyclerView.setVisibility(View.VISIBLE);

                           if (SearchContacts.size() > 0) {

                               adapter = new ContactAdapter(ContactPage.this, SearchContacts,"Phone");
                               recyclerView.setHasFixedSize(true);
                               recyclerView.setLayoutManager(new LinearLayoutManager(ContactPage.this));
                               recyclerView.setAdapter(adapter);
                           } else {
                               no_contact.setText("No Search Contact Found");
                               no_contact.setVisibility(View.VISIBLE);
                               recyclerView.setVisibility(View.GONE);
                               //  Toast.makeText(ContactPage.this,"okk",Toast.LENGTH_SHORT).show();
                           }
                       } else {
                           no_contact.setText("No Search Contact Found");
                           no_contact.setVisibility(View.VISIBLE);
                           recyclerView.setVisibility(View.GONE);
                           //Toast.makeText(ContactPage.this,"okk",Toast.LENGTH_SHORT).show();

                       }

                   }else {

                       if (SPLITTER_SearchContacts != null) {

                           if (SPLITTER_SearchContacts.size() > 0) {

                               no_contact.setVisibility(View.GONE);
                               recyclerView.setVisibility(View.VISIBLE);

                               adapter = new ContactAdapter(ContactPage.this,ContactPage.this,SPLITTER_SearchContacts,"Splitter");
                               recyclerView.setHasFixedSize(true);
                               recyclerView.setLayoutManager(new LinearLayoutManager(ContactPage.this));
                               recyclerView.setAdapter(adapter);
                           } else {
                               no_contact.setText("No Search Contact Found");
                               no_contact.setVisibility(View.VISIBLE);
                               recyclerView.setVisibility(View.GONE);
                               //  Toast.makeText(ContactPage.this,"okk",Toast.LENGTH_SHORT).show();
                           }
                       } else {
                           no_contact.setText("No Search Contact Found");
                           no_contact.setVisibility(View.VISIBLE);
                           recyclerView.setVisibility(View.GONE);
                           //Toast.makeText(ContactPage.this,"okk",Toast.LENGTH_SHORT).show();

                       }

                   }



                }else{

                   if (SelectedTab.equals("Phone")){

                       if (Contacts != null) {

                           no_contact.setVisibility(View.GONE);
                           recyclerView.setVisibility(View.VISIBLE);

                           if (Contacts.size() > 0) {

                               Contacts = AppData.contactVOList;
                               adapter = new ContactAdapter(ContactPage.this, Contacts,"Phone");
                               recyclerView.setHasFixedSize(true);
                               recyclerView.setLayoutManager(new LinearLayoutManager(ContactPage.this));
                               recyclerView.setAdapter(adapter);
                           } else {
                               no_contact.setVisibility(View.VISIBLE);
                               recyclerView.setVisibility(View.GONE);
                           }
                       } else {
                           no_contact.setVisibility(View.VISIBLE);
                           recyclerView.setVisibility(View.GONE);

                       }

                   }else {

                       if (AppData.Splitter_contactVOList != null) {

                           no_contact.setVisibility(View.GONE);
                           recyclerView.setVisibility(View.VISIBLE);

                           if (AppData.Splitter_contactVOList.size() > 0) {

                               //Contacts = AppData.contactVOList;
                               adapter = new ContactAdapter(ContactPage.this,ContactPage.this, AppData.Splitter_contactVOList,"Splitter");
                               recyclerView.setHasFixedSize(true);
                               recyclerView.setLayoutManager(new LinearLayoutManager(ContactPage.this));
                               recyclerView.setAdapter(adapter);
                           } else {
                               no_contact.setVisibility(View.VISIBLE);
                               recyclerView.setVisibility(View.GONE);
                           }
                       } else {
                           no_contact.setVisibility(View.VISIBLE);
                           recyclerView.setVisibility(View.GONE);
                       }

                   }



                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        Button_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SearchBar.setVisibility(View.VISIBLE);
                SearchText.setText("");
                Animation RightSwipe = AnimationUtils.loadAnimation(ContactPage.this, R.anim.right_to_left);
                SearchBar.startAnimation(RightSwipe);


            }
        });

        Close_SearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SearchText.setText("");

                SearchBar.setVisibility(View.GONE);
                Animation LeftSwipe = AnimationUtils.loadAnimation(ContactPage.this, R.anim.left_to_right);
                SearchBar.startAnimation(LeftSwipe);

                View view1 = getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                adapter=null;


                if (SelectedTab.equals("Phone")){

                    if (Contacts != null) {

                        no_contact.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        if (Contacts.size() > 0) {

                            Contacts = AppData.contactVOList;
                            adapter = new ContactAdapter(ContactPage.this, Contacts,"Phone");
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ContactPage.this));
                            recyclerView.setAdapter(adapter);
                        } else {
                            no_contact.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        no_contact.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                }else {

                    if (AppData.Splitter_contactVOList != null) {

                        no_contact.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        if (AppData.Splitter_contactVOList.size() > 0) {

                            //Contacts = AppData.contactVOList;
                            adapter = new ContactAdapter(ContactPage.this, ContactPage.this,AppData.Splitter_contactVOList,"Splitter");
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ContactPage.this));
                            recyclerView.setAdapter(adapter);
                        } else {
                            no_contact.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        no_contact.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                }



            }
        });

/*
        check_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (check_status == true) {

                    check_id.setImageResource(R.drawable.check);

                    for (int i = 0; i < Contacts.size(); i++) {
                        Contacts.get(i).setCheckStatus(true);
                    }
                    adapter.update(Contacts);

                    check_status = false;

                } else {

                    check_id.setImageResource(R.drawable.uncheck);

                    for (int i = 0; i < Contacts.size(); i++) {
                        Contacts.get(i).setCheckStatus(false);

                    }

                    adapter.update(Contacts);

                    check_status = true;

                }

            }
        });*/

        button_share.setOnClickListener(new View.OnClickListener() {
            int counter;
            @Override
            public void onClick(View v) {

                AppData.selectedContact = new ArrayList<SelectedContact_SetterGetter>();

                for (int i = 0; i < AppData.Splitter_contactVOList.size(); i++) {
                    if (AppData.Splitter_contactVOList.get(i).isCheckStatus() == true) {
                        counter++;

                        SelectedContact_SetterGetter contacts_setterGetter = new SelectedContact_SetterGetter(AppData.Splitter_contactVOList.get(i).getName(),AppData.Splitter_contactVOList.get(i).getNumber(),AppData.Splitter_contactVOList.get(i).getPhoto_uri(),AppData.Splitter_contactVOList.get(i).isCheckStatus(),i,"Splitter");



                        AppData.selectedContact.add(contacts_setterGetter);
                    }
                }

                counter = 0;

                for (int i = 0; i < Contacts.size(); i++) {
                    if (Contacts.get(i).getCheckStatus() == true) {
                        counter++;

                        boolean AllreadyExist = true;

                        for (int j = 0 ; j<AppData.selectedContact.size();j++){

                            if (Contacts.get(i).getNumber().equals(AppData.selectedContact.get(j).getNumber())){

                                AllreadyExist = false;
                                break;

                            }

                        }

                        if (AllreadyExist){

                            SelectedContact_SetterGetter contacts_setterGetter = new SelectedContact_SetterGetter(Contacts.get(i).getName(),Contacts.get(i).getNumber(),Contacts.get(i).getPhoto_uri(),Contacts.get(i).getCheckStatus(),i,"Phone");



                            AppData.selectedContact.add(contacts_setterGetter);

                        }


                    }
                }


                if (AppData.selectedContact.size() > 0) {
                    AppData.Contact_Block = true;
                    AppData.ContactPageCount = 2;
                    //Toast.makeText(getApplication(),""+counter,Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplication(),"Select Atleast One Contact",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void Vol_Splitters(int page){

        Progress.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

         String url = AppData.DomainUrlForProfile+"followfollowing_control?user_id="+sharedPreferences.getString("UserId","")+"&logged_in="+sharedPreferences.getString("UserId","")+"&page="+page+"&limit=10";

        Log.v("Splitter_Url::::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("success") && message.equals("data found")){

                        JSONArray Details = response.getJSONArray("Details");

                        if (Details.length() > 0){

                            Contacts1 = new ArrayList<Spltter_Contacts_SetterGetter>();
                            Contacts1.clear();

                            for (int i=0;i<Details.length();i++){

                                JSONObject DetailsObject = Details.getJSONObject(i);

                                String userid = DetailsObject.getString("userid");
                                String rating = DetailsObject.getString("rating");
                                String image = DetailsObject.getString("image");
                                String name = DetailsObject.getString("name");
                                String splits = DetailsObject.getString("splits");
                                String splitter = DetailsObject.getString("splitter");
                                int status_check = DetailsObject.getInt("status_check");
                                
                                String phone = DetailsObject.getString("phone");

                                Spltter_Contacts_SetterGetter spltter_contacts_setterGetter = new Spltter_Contacts_SetterGetter(name,phone,image,"1",false);


                                Contacts1.add(spltter_contacts_setterGetter);



                            }

//                            NoSplitters.setVisibility(View.GONE);
//                            SplitterList.setVisibility(View.VISIBLE);

//                            if (splitterlistAdapter == null){
//
//                                splitterlistAdapter = new SplitterlistAdapter(AllSplitterDetails,ContactPage.this);
//                                SplitterList.setAdapter(splitterlistAdapter);
//
//                            }else {
//
//                                splitterlistAdapter.Update(AllSplitterDetails);
//                            }



                            if (AppData.Splitter_contactVOList.size() > 0){

                                if (Contacts1 != null) {

                                    if (Contacts1.size() > 0) {

                                        //Contacts = AppData.contactVOList;

//                                        boolean matched = false;
//                                        int pos;

                                        for (int i=0;i<Contacts1.size();i++){

                                            for (int j=0;j<AppData.Splitter_contactVOList.size();j++){

                                                if (AppData.Splitter_contactVOList.get(j).getNumber().equals(Contacts1.get(i).getNumber())){

                                                    Contacts1.get(i).setCheckStatus(true);

                                                }

                                            }

                                        }

                                        for (int i=0;i<Contacts1.size();i++){

                                            if (!(Contacts1.get(i).isCheckStatus())){

                                                AppData.Splitter_contactVOList.add(Contacts1.get(i));

                                            }

                                        }

                                        no_contact.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        adapter = new ContactAdapter(ContactPage.this,ContactPage.this, AppData.Splitter_contactVOList,"Splitter");
                                        recyclerView.setHasFixedSize(true);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(ContactPage.this));
                                        recyclerView.setAdapter(adapter);
                                    } else {
                                        no_contact.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);
                                    }
                                } else {
                                    no_contact.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }

                            }else {

                                if (Contacts1 != null) {

                                    Log.v("SplitterList::","1");

                                    no_contact.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);

                                    if (Contacts1.size() > 0) {

                                        Log.v("SplitterList::","2");

                                        //Contacts = AppData.contactVOList;
                                        no_contact.setVisibility(View.GONE);
                                        AppData.Splitter_contactVOList = Contacts1;
                                        adapter = new ContactAdapter(ContactPage.this,ContactPage.this, AppData.Splitter_contactVOList,"Splitter");
                                        recyclerView.setHasFixedSize(true);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(ContactPage.this));
                                        recyclerView.setAdapter(adapter);
                                    } else {
                                        Log.v("SplitterList::","3");
                                        no_contact.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);
                                    }
                                } else {
                                    Log.v("SplitterList::","4");
                                    no_contact.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }

                            }


                            Progress.setVisibility(View.GONE);


                        }else {

//                            NoSplitters.setVisibility(View.VISIBLE);
//                            SplitterList.setVisibility(View.GONE);
                            no_contact.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            Progress.setVisibility(View.GONE);

                        }

                    }else {

//                        NoSplitters.setVisibility(View.VISIBLE);
//                        SplitterList.setVisibility(View.GONE);
                        no_contact.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        Progress.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

//                    NoSplitters.setVisibility(View.VISIBLE);
//                    SplitterList.setVisibility(View.GONE);
                    no_contact.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Progress.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                NoSplitters.setVisibility(View.VISIBLE);
//                SplitterList.setVisibility(View.GONE);
                no_contact.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Progress.setVisibility(View.GONE);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
