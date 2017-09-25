package com.ritam.splitthebill.splitthebill.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.BookingAdapter_Upcoming;
import com.ritam.splitthebill.splitthebill.adapter.ProfileFollowingOrFollowerAdapter;
import com.ritam.splitthebill.splitthebill.adapter.UserFeedList_Adapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.ritam.splitthebill.splitthebill.fragments.Profile;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import setergeter.Booking_SetterGetter;
import setergeter.FeedList_SetterGetter;

public class OthersProfile extends AppCompatActivity {

    RecyclerView FollowingPrFollowerList;
    ProfileFollowingOrFollowerAdapter adapter1;
    BookingAdapter_Upcoming adapter;

    private Profile.OnFragmentInteractionListener mListener;


    RelativeLayout Button_Spliter,Button_Upcoming,Button_Previous;
    //TextView Line_Spliter,Line_Upcoming,Line_Previous;

    ImageView ProfileImage;
    TextView FollowersCount,FollowingCount,UserName;

    public static final int REQUEST_CAMERA = 0;
    public static final int REQUEST_GALLERY = 1;
    File photofile = null;
    Uri filePath;
    String encodedImage="";
    String url= null;
    ArrayList<Booking_SetterGetter> BookingHistoryValues;

    TextView NoBookingText,HeaderText;
    ImageView Image_Back,Image_Settings;
    boolean FromPrevious = false;

    RelativeLayout BackButton;


    AlertDialog.Builder builder;

    String Type;

    ArrayList<FeedList_SetterGetter> FeedListDetails;

    LinearLayout NoFeedText,PrivateAccountText;

    String private_status;
    TextView FollowText,UnfollowTExt,RequestedText;
    RelativeLayout Button_FollowOrUnFollow;
    int FollowOrUnFollow = 0;

    ProgressBar ProfileProgress;

    LinearLayout Button_Splits,Button_Splitters;

    RelativeLayout Button_Settings;

    int follow_status;

    int SelectedTab;

    RatingBar ProfileRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_profile);

        AppData.OwnProfile = false;


        BackButton = (RelativeLayout) findViewById(R.id.ll_back_othersprofile);


        HeaderText = (TextView) findViewById(R.id.header);
        Image_Back = (ImageView) findViewById(R.id.iv_back);
        Image_Settings = (ImageView) findViewById(R.id.iv_settings);

        NoFeedText = (LinearLayout) findViewById(R.id.tv_nofeed);

        FollowText = (TextView) findViewById(R.id.tv_follow);

        UnfollowTExt = (TextView) findViewById(R.id.tv_unfollow);

        RequestedText = (TextView) findViewById(R.id.tv_requested);

        Button_FollowOrUnFollow = (RelativeLayout) findViewById(R.id.rl_followunfollow);

        PrivateAccountText = (LinearLayout) findViewById(R.id.ll_private);
        PrivateAccountText.setClickable(true);
        PrivateAccountText.setEnabled(true);

        ProfileProgress = (ProgressBar) findViewById(R.id.progressBar);

        Button_Splitters = (LinearLayout) findViewById(R.id.ll_splitters);

        Button_Splits = (LinearLayout) findViewById(R.id.ll_splits);

        Button_Settings = (RelativeLayout) findViewById(R.id.ll_settings);

        ProfileRating = (RatingBar) findViewById(R.id.rating_bar);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        if (sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("UserId"))){

            Type = "Host";

            HeaderText.setText("Edit Profile");
            Image_Back.setVisibility(View.GONE);
            Image_Settings.setVisibility(View.VISIBLE);
            Button_Settings.setVisibility(View.VISIBLE);

            Button_FollowOrUnFollow.setVisibility(View.GONE);

        }else {

            Type = "Splitters";

            HeaderText.setText("Profile");
            Image_Back.setVisibility(View.VISIBLE);
            Image_Settings.setVisibility(View.GONE);
            Button_Settings.setVisibility(View.GONE);

            Button_FollowOrUnFollow.setVisibility(View.VISIBLE);

        }



        Button_Spliter = (RelativeLayout) findViewById(R.id.rl_spliter);
        Button_Upcoming = (RelativeLayout) findViewById(R.id.rl_upcoming);
        Button_Previous = (RelativeLayout) findViewById(R.id.rl_previous);

//        Line_Spliter = (TextView) findViewById(R.id.tl_spliter_line);
//        Line_Upcoming = (TextView) findViewById(R.id.tl_upcoming_line);
//        Line_Previous = (TextView) findViewById(R.id.tl_previous_line);

        ProfileImage = (ImageView) findViewById(R.id.iv_proimage);
        UserName = (TextView) findViewById(R.id.tv_proname);
        FollowersCount = (TextView) findViewById(R.id.tv_followers);
        FollowingCount = (TextView) findViewById(R.id.tv_following);

        NoBookingText = (TextView) findViewById(R.id.tv_nobooking);

        FollowingPrFollowerList= (RecyclerView) findViewById(R.id.rv_profile);
        FollowingPrFollowerList.setHasFixedSize(true);
        FollowingPrFollowerList.setLayoutManager(new LinearLayoutManager(OthersProfile.this));

        //adapter=new EventsAdapter();
//        adapter1=new ProfileFollowingOrFollowerAdapter(1);
//        FollowingPrFollowerList.setAdapter(adapter1);

        Button_Spliter.setSelected(true);

        PRofileDetails();

        SelectedTab = 1;


        Button_Splits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((private_status.equals("0"))){

                    Intent intent = new Intent(OthersProfile.this,Splits.class);
                    intent.putExtra("UserId",getIntent().getExtras().getString("UserId"));
                    startActivity(intent);

                }else {

                    if (follow_status == 1){

                        Intent intent = new Intent(OthersProfile.this,Splitters.class);
                        intent.putExtra("UserId",getIntent().getExtras().getString("UserId"));
                        startActivity(intent);

                    }

                }



            }
        });


        Button_Splitters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((private_status.equals("0"))){

                    Intent intent = new Intent(OthersProfile.this,Splitters.class);
                    intent.putExtra("UserId",getIntent().getExtras().getString("UserId"));
                    startActivity(intent);

                }else {

                    if (follow_status == 1){

                        Intent intent = new Intent(OthersProfile.this,Splitters.class);
                        intent.putExtra("UserId",getIntent().getExtras().getString("UserId"));
                        startActivity(intent);

                    }

                }



            }
        });


        Button_Spliter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelectedTab = 1;

                Button_Spliter.setSelected(true);
                Button_Upcoming.setSelected(false);
                Button_Previous.setSelected(false);

//                Line_Spliter.setVisibility(View.VISIBLE);
//                Line_Upcoming.setVisibility(View.GONE);
//                Line_Previous.setVisibility(View.GONE);


//                if (private_status.equals("0")){
//
//                    NoFeedText.setVisibility(View.GONE);

                    Vol_FeedList();

//                }else {
//
//                    SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//
//                    if (sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("UserId"))){
//
//                        NoFeedText.setVisibility(View.GONE);
//
//                        Vol_FeedList();
//
//                    }else {
//
//                        NoFeedText.setVisibility(View.VISIBLE);
//
//                    }
//
//                }



            }
        });

        Button_Upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelectedTab = 2;

                Button_Spliter.setSelected(false);
                Button_Upcoming.setSelected(true);
                Button_Previous.setSelected(false);

//                Line_Spliter.setVisibility(View.GONE);
//                Line_Upcoming.setVisibility(View.VISIBLE);
//                Line_Previous.setVisibility(View.GONE);

                FromPrevious = false;

                adapter = null;

//                if (private_status.equals("0")){
//
//                    NoFeedText.setVisibility(View.GONE);

                    BookingHistory("1","1");

//                }else {
//
//                    SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//
//                    if (sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("UserId"))){
//
//                        NoFeedText.setVisibility(View.GONE);
//
//                        BookingHistory("1","1");
//
//                    }else {
//
//                        NoFeedText.setVisibility(View.VISIBLE);
//
//                    }
//
//                }



            }
        });

        Button_Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelectedTab = 3;

                Button_Spliter.setSelected(false);
                Button_Upcoming.setSelected(false);
                Button_Previous.setSelected(true);

//                Line_Spliter.setVisibility(View.GONE);
//                Line_Upcoming.setVisibility(View.GONE);
//                Line_Previous.setVisibility(View.VISIBLE);

                FromPrevious = true;

                adapter = null;

//                if (private_status.equals("0")){
//
//                    NoFeedText.setVisibility(View.GONE);

                    BookingHistory("2","1");

//                }else {
//
//                    SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//
//                    if (sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("UserId"))){
//
//                        NoFeedText.setVisibility(View.GONE);
//
//                        BookingHistory("2","1");
//
//                    }else {
//
//                        NoFeedText.setVisibility(View.VISIBLE);
//
//                    }
//
//                }



            }
        });


        Button_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OthersProfile.this,Settings.class));

            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Type.equals("Splitters")){

                    finish();

                }

            }
        });


        Button_FollowOrUnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Vol_FollowOrUnfollow();

            }
        });



        if (AppData.imageUploadStatus==true)
            ProfileImage.setEnabled(true);
        else
            ProfileImage.setEnabled(false);

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

                if (sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("UserId"))){

                    selectImage();

                }


            }
        });


    }



    public void PRofileDetails(){

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"profile_control?userid="+getIntent().getExtras().getString("UserId")+"&logged_in="+sharedPreferences.getString("UserId","");

        Log.d("Profile url",":::"+url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {


                    String status = response.getString("status");
                    String message = response.getString("message");


                    if (status.equals("success")){

                        if (message.equals("data found")){

                            JSONObject Details = response.getJSONObject("Details");

                            String name = Details.getString("name");
                            int splits = Details.getInt("splits");
                            int splitter = Details.getInt("splitter");
                            String image = Details.getString("image");
                            String push_status = Details.getString("push_status");
                            private_status = Details.getString("private_status");
                            follow_status = Details.getInt("follow_status");
                            String rating = Details.getString("rating");

                            ProfileRating.setRating(Float.parseFloat(rating));

                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

                            if (!(sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("UserId")))){

                                if (private_status.equals("0")){

                                    PrivateAccountText.setVisibility(View.GONE);

                                }else {
                                    if (follow_status == 0){

                                        PrivateAccountText.setVisibility(View.VISIBLE);

                                        FollowText.setVisibility(View.VISIBLE);
                                        UnfollowTExt.setVisibility(View.GONE);
                                        RequestedText.setVisibility(View.GONE);

                                        FollowOrUnFollow = 0;

                                    }else if (follow_status == 1){

                                        PrivateAccountText.setVisibility(View.GONE);

                                        UnfollowTExt.setVisibility(View.VISIBLE);
                                        FollowText.setVisibility(View.GONE);
                                        RequestedText.setVisibility(View.GONE);

                                        FollowOrUnFollow = 1;

                                    }else if (follow_status == 2){

                                        PrivateAccountText.setVisibility(View.VISIBLE);

                                        FollowText.setVisibility(View.GONE);
                                        UnfollowTExt.setVisibility(View.GONE);
                                        RequestedText.setVisibility(View.VISIBLE);

                                        FollowOrUnFollow = 2;

                                    }


                                }



                            }



                            UserName.setText(name);
                            FollowersCount.setText(String.valueOf(splits));
                            FollowingCount.setText(String.valueOf(splitter));


                            if (sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("UserId"))){

                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("Push_Status",push_status);

                                editor.commit();

                            }



                            if (!(image.equals("")) && !(image.isEmpty()) && image != null){

                                Picasso.with(OthersProfile.this)
                                        .load(image)
                                        .placeholder(R.drawable.appicon_round)
                                        .error(R.drawable.appicon_round)
                                        .transform(new RoundedTransformation())
                                        .into(ProfileImage);

                            }


                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (SelectedTab == 1){

                    Vol_FeedList();
                }else if (SelectedTab == 2){

                    adapter = null;

                    BookingHistory("1","1");

                }else if (SelectedTab == 3){

                    adapter = null;
                    BookingHistory("2","1");

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        builder = new AlertDialog.Builder(OthersProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {


                    try {

                        Log.d("Open Camera","----------------------------");

                        photofile = createImageFile();
                        Log.d("image path--", String.valueOf(photofile));


                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile(photofile));
                        startActivityForResult(intent,REQUEST_CAMERA);

                        dialog.dismiss();

                    } catch (Exception e) {

                        Log.v("Catch Exception", e.toString());

                    }


                }
                else if (items[item].equals("Choose from Library")) {

                    Log.d("Open Gallary","----------------------------");

                    try {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, REQUEST_GALLERY);
                        dialog.dismiss();

                    } catch (Exception e) {

                        Log.v("Catch Exception", e.toString());
                    }


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,25,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);


        UploadImage upload=new UploadImage();
        upload.execute();

        return encImage;
    }

    File createImageFile() throws IOException {

        String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());///new approach
        String imagefilename="IMAGE_"+timestamp+"_";///new approach

        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        File image=File.createTempFile(imagefilename,".jpg",storageDirectory);/////new approach

        return  image;


    }

    public void rotateimage(Bitmap bitm)
    {

        ExifInterface exifInterface=null;
        try {
            exifInterface = new ExifInterface(filePath.toString());
        }
        catch (Exception e){



            e.printStackTrace();
        }
        int orientation=exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix=new Matrix();
        switch (orientation)
        {

            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            default:

        }
        encodedImage=encodeImage(Bitmap.createBitmap(bitm,0,0,bitm.getWidth(),bitm.getHeight(),matrix,true));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null)
            Log.d("on Activity-----","requestcode-"+requestCode+",resultcode-"+resultCode+",data-"+data.getData());

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery

                Log.d("Image from Gallary","----------------------------"+filePath);


                //  bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                if(!filePath.equals("") || !filePath.equals(null)) {
                    Picasso.with(OthersProfile.this).load(filePath)
                            .placeholder(R.drawable.cemera)
                            .fit()
                            .transform(new RoundedTransformation())
                            .into(ProfileImage);
                }else{

                    Picasso.with(OthersProfile.this).load(R.drawable.cemera)
                            .placeholder(R.drawable.cemera)
                            .fit()
                            .transform(new RoundedTransformation())
                            .into(ProfileImage);

                }

                InputStream imageStream = getContentResolver().openInputStream(filePath);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                Bitmap resized = Bitmap.createScaledBitmap(selectedImage, 100, 100, true);

                encodedImage = encodeImage(resized);

                //uploadImage();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK  && photofile != null) {
            filePath = Uri.fromFile(photofile);
            try {
                //Getting the Bitmap from Gallery

                Log.d("Image from Camera", "----------------------------" + filePath);


                // bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                if (!filePath.equals("") || !filePath.equals(null)) {
                    Picasso.with(OthersProfile.this).load(filePath)
                            .placeholder(R.drawable.cemera)
                            .fit()
                            .transform(new RoundedTransformation())
                            .into(ProfileImage);
                } else {

                    Picasso.with(OthersProfile.this).load(R.drawable.cemera)
                            .placeholder(R.drawable.cemera)
                            .fit()
                            .transform(new RoundedTransformation())
                            .into(ProfileImage);

                }

                InputStream imageStream = getContentResolver().openInputStream(filePath);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //encodedImage = encodeImage(selectedImage);
                Bitmap resized = Bitmap.createScaledBitmap(selectedImage, 100, 100, true);
                rotateimage(resized);

                //uploadImage();
            }catch (IOException e){
                e.printStackTrace();

            }

        }else{
            Toast.makeText(OthersProfile.this,"Image Error",Toast.LENGTH_SHORT).show();
        }
    }


    class  UploadImage extends AsyncTask<Void,Void,Void> {
        JSONObject jsonObject;
        String erormessage,result;
        okhttp3.Request req;
        String responce_code=null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... params) {

            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);

            String url = AppData.DomainUrlForProfile+"userimageupload_control?user_id="+getIntent().getExtras().getString("UserId")
                    +"&user_image="+encodedImage;


            try{

                Log.v("Signup url", ":::"+url);

                OkHttpClient okHttpClient=new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("userimage", encodedImage)
                        .build();




                req = new okhttp3.Request.Builder()
                        .url(url)
                        .method("POST", RequestBody.create(null, new byte[0]))
                        .post(requestBody)
                        .build();

                Log.d("request2", String.valueOf(req));

                try {

                    okhttp3.Response httpResponse =okHttpClient.newCall(req).execute();
                    responce_code = String.valueOf(httpResponse.code());

                    Log.d("responce_code : ",(responce_code));
                    //  Log.d("response", String.valueOf(httpResponse));
                    result = httpResponse.body().string();
                    Log.d("response:::", result);

                    if(responce_code.equals("200")) {

                        JSONObject Jobject = new JSONObject(result);
                        erormessage = Jobject.getString("message");
                        Log.d("erormessage:::", erormessage);
                    }

                }

                catch (IOException e){
                    e.printStackTrace();
                    Log.d("ResultException",e.toString());
                }
            }catch (Exception e){

                e.printStackTrace();
                Log.d("Submit Exception : ", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (responce_code == null) {

                Toast.makeText(OthersProfile.this, "Server failed to respond", Toast.LENGTH_SHORT).show();

            }else if(responce_code.equals("200")){

                Toast.makeText(OthersProfile.this, ""+erormessage, Toast.LENGTH_SHORT).show();

            }else
                Toast.makeText(OthersProfile.this, "Server failed to respond", Toast.LENGTH_SHORT).show();

        }


    }


    public void BookingHistory(String status,String Page){

        NoFeedText.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);


        String url = AppData.DomainUrlForProfile+"bookinghistory_control?userid="+getIntent().getExtras().getString("UserId")+"&status="+status+"&page="+Page+"&limit=10";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Log.v("BookingHistory Response::",response.toString());

                try {


                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("success")){

                        if (message.equals("data found")){

                            BookingHistoryValues = new ArrayList<Booking_SetterGetter>();
                            BookingHistoryValues.clear();


                            JSONArray Details = response.getJSONArray("Details");

                            if (Details.length() > 0){

                                NoBookingText.setVisibility(View.GONE);
                                FollowingPrFollowerList.setVisibility(View.VISIBLE);


                                for (int i = 0;i<Details.length();i++){

                                    JSONObject BookingObject = Details.getJSONObject(i);

                                    String event_id = BookingObject.getString("event_id");
                                    String eventdate = BookingObject.getString("eventdate");
                                    String eventname = BookingObject.getString("eventname");
                                    String venue_name = BookingObject.getString("venue_name");
                                    String venue_address = BookingObject.getString("venue_address");
                                    String reference_id = BookingObject.getString("reference_id");
                                    String table_name = BookingObject.getString("table_name");
                                    String event_month = BookingObject.getString("event_month");
                                    String event_date = BookingObject.getString("event_date");
                                    String event_time = BookingObject.getString("event_time");
                                    String table_id = BookingObject.getString("table_id");
                                    String host_id = BookingObject.getString("host_id");

                                    String host_name = BookingObject.getString("host_name");
                                    String total_seat = BookingObject.getString("total_seat");
                                    String remaining_seat = BookingObject.getString("remaining_seat");
                                    String table_cost = BookingObject.getString("remaining_amount");
                                    String group_id = BookingObject.getString("group_id");

                                    Booking_SetterGetter booking_setterGetter = new Booking_SetterGetter(event_id,eventname,venue_name,venue_address,reference_id,table_name,event_month,event_date,event_time,table_id,host_id,host_name,total_seat,remaining_seat,table_cost,group_id);
                                    BookingHistoryValues.add(booking_setterGetter);



                                }


                                if (adapter == null){

                                    adapter=new BookingAdapter_Upcoming(BookingHistoryValues,OthersProfile.this,OthersProfile.this,status,FromPrevious,"OtherProfile");
                                    FollowingPrFollowerList.setAdapter(adapter);

                                }else {

                                    adapter.LoadMore(BookingHistoryValues);

                                }

                            }else {

                                FollowingPrFollowerList.setVisibility(View.GONE);
                                NoBookingText.setVisibility(View.VISIBLE);

                            }



                        }else {

                            FollowingPrFollowerList.setVisibility(View.GONE);
                            NoBookingText.setVisibility(View.VISIBLE);

                        }


                    }else {

                        FollowingPrFollowerList.setVisibility(View.GONE);
                        NoBookingText.setVisibility(View.VISIBLE);


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

    public void Vol_FeedList(){

        NoBookingText.setVisibility(View.GONE);

        String url = AppData.DomainUrlForProfile+"feed_control?userid="+getIntent().getExtras().getString("UserId");

        Log.v("FeedList_Url::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("status");

                    if (status.equals("success")){


                        JSONArray Details = response.getJSONArray("Details");

                        if (Details.length() > 0){

                            FeedListDetails = new ArrayList<FeedList_SetterGetter>();

                            for (int i=0;i<Details.length();i++){

                                JSONObject feedDetails = Details.getJSONObject(i);

                                String user_id = feedDetails.getString("user_id");
                                String host_id = feedDetails.getString("host_id");
                                int rating = feedDetails.getInt("rating");
                                String booked_date = feedDetails.getString("booked_date");
                                String payment = feedDetails.getString("payment");
                                String follow_name = feedDetails.getString("follow_name");
                                String follow_image = feedDetails.getString("follow_image");
                                String tablename = feedDetails.getString("tablename");
                                String eventname = feedDetails.getString("eventname");
                                String eventcover_image = feedDetails.getString("eventcover_image");

                                String totalpeople = feedDetails.getString("totalpeople");
                                String venue_name = feedDetails.getString("venue_name");

                                String event_id = feedDetails.getString("event_id");
                                String table_id = feedDetails.getString("table_id");

                                String eventexpire = feedDetails.getString("eventexpire");

                                FeedList_SetterGetter feedList_setterGetter = new FeedList_SetterGetter(user_id,host_id,booked_date,payment,follow_name,follow_image,tablename,eventname,eventcover_image,rating,totalpeople,venue_name,event_id,table_id,eventexpire);
                                FeedListDetails.add(feedList_setterGetter);

                            }

//                            if (private_status.equals("0")){
//
//                                NoFeedText.setVisibility(View.GONE);
//                                FollowingPrFollowerList.setVisibility(View.VISIBLE);
//
//                            }else {
//
//                                SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//
//                                if (sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("UserId"))){
//
//                                    NoFeedText.setVisibility(View.GONE);
//                                    FollowingPrFollowerList.setVisibility(View.VISIBLE);
//
//                                }else{
//
//                                    NoFeedText.setVisibility(View.VISIBLE);
//                                    FollowingPrFollowerList.setVisibility(View.GONE);
//
//                                }
//
//                            }



                            UserFeedList_Adapter userFeedList_adapter = new UserFeedList_Adapter(FeedListDetails,OthersProfile.this);
                            FollowingPrFollowerList.setAdapter(userFeedList_adapter);


                        }else {

                            //if (private_status.equals("0")){

                                NoFeedText.setVisibility(View.VISIBLE);
                                FollowingPrFollowerList.setVisibility(View.GONE);

//                            }else {
//
//                                SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//
//                                if (sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("UserId"))){
//
//                                    NoFeedText.setVisibility(View.VISIBLE);
//                                    FollowingPrFollowerList.setVisibility(View.GONE);
//
//                                }
//                            }



                        }

                    }else {

                        //if (private_status.equals("0")){

                            NoFeedText.setVisibility(View.VISIBLE);
                            FollowingPrFollowerList.setVisibility(View.GONE);

//                        }else {
//
//                            SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
//
//                            if (sharedPreferences.getString("UserId","").equals(getIntent().getExtras().getString("UserId"))){
//
//                                NoFeedText.setVisibility(View.VISIBLE);
//                                FollowingPrFollowerList.setVisibility(View.GONE);
//
//                            }
//
//                        }

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


    public void Vol_FollowOrUnfollow(){

        ProfileProgress.setVisibility(View.VISIBLE);

        Button_FollowOrUnFollow.setClickable(false);
        Button_FollowOrUnFollow.setEnabled(false);

        SharedPreferences sharedPreferences =  getSharedPreferences("AutoLogin",MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"follow_control?to_id="+getIntent().getExtras().getString("UserId")+"&from_id="+sharedPreferences.getString("UserId","");

        Log.v("FollowUrl:::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("FollowResponse:::",response.toString());

                String message = null;

                try {

                    String status = response.getString("status");
                    message = response.getString("message");

                    if (status.equals("success")){

                        if (FollowOrUnFollow == 0){

                            FollowOrUnFollow = 1;
                            private_status = "1";

                            FollowText.setVisibility(View.GONE);
                            UnfollowTExt.setVisibility(View.GONE);
                            RequestedText.setVisibility(View.VISIBLE);

                            //PrivateAccountText.setVisibility(View.GONE);



                        }else if (FollowOrUnFollow == 1){

                            FollowOrUnFollow = 0;
                            private_status = "0";

                            UnfollowTExt.setVisibility(View.GONE);
                            RequestedText.setVisibility(View.GONE);
                            FollowText.setVisibility(View.VISIBLE);

                            PrivateAccountText.setVisibility(View.VISIBLE);




                        }else if (FollowOrUnFollow == 2){

                            FollowOrUnFollow = 0;
                            private_status = "0";

                            UnfollowTExt.setVisibility(View.GONE);
                            RequestedText.setVisibility(View.GONE);
                            FollowText.setVisibility(View.VISIBLE);

                            PrivateAccountText.setVisibility(View.VISIBLE);

                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Button_FollowOrUnFollow.setClickable(true);
                Button_FollowOrUnFollow.setEnabled(true);

                ProfileProgress.setVisibility(View.GONE);

                Toast.makeText(OthersProfile.this,message,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Button_FollowOrUnFollow.setClickable(true);
                Button_FollowOrUnFollow.setEnabled(true);

                ProfileProgress.setVisibility(View.GONE);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    @Override
    protected void onResume() {
        super.onResume();

        PRofileDetails();
    }
}
