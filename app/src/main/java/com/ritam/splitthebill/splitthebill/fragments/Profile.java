package com.ritam.splitthebill.splitthebill.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.EditProfile;
import com.ritam.splitthebill.splitthebill.activity.Splits;
import com.ritam.splitthebill.splitthebill.activity.Splitters;
import com.ritam.splitthebill.splitthebill.adapter.BookingAdapter_Upcoming;
import com.ritam.splitthebill.splitthebill.adapter.ProfileFollowingOrFollowerAdapter;
import com.ritam.splitthebill.splitthebill.adapter.UserFeedList_Adapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView FollowingPrFollowerList;
    ProfileFollowingOrFollowerAdapter adapter1;
    BookingAdapter_Upcoming adapter;

    private OnFragmentInteractionListener mListener;


    RelativeLayout Button_Spliter, Button_Upcoming, Button_Previous;
    TextView Line_Spliter, Line_Upcoming, Line_Previous;

    ImageView ProfileImage;
    TextView FollowersCount, FollowingCount, UserName;

    public static final int REQUEST_CAMERA = 0;
    public static final int REQUEST_GALLERY = 1;
    File photofile = null;
    Uri filePath;
    String encodedImage = "";
    String url = null;
    ArrayList<Booking_SetterGetter> BookingHistoryValues;

    TextView NoBookingText;
    boolean FromPrevious = false;

    LinearLayout FullUpperView,NoFeedText;


    AlertDialog.Builder builder;

    ArrayList<FeedList_SetterGetter> FeedListDetails;

    RatingBar ProfileRating;

    LinearLayout Button_Splitter,Button_Splits;

    TextView Button_EditProfile;


    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance() {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppData.OwnProfile = true;

        Button_Spliter = (RelativeLayout) view.findViewById(R.id.rl_spliter);
        Button_Upcoming = (RelativeLayout) view.findViewById(R.id.rl_upcoming);
        Button_Previous = (RelativeLayout) view.findViewById(R.id.rl_previous);
        FullUpperView = (LinearLayout) view.findViewById(R.id.ll_fullview);

        ProfileRating = (RatingBar) view.findViewById(R.id.rating_bar);

        Button_Splitter = (LinearLayout) view.findViewById(R.id.ll_splitter);

        Button_Splits = (LinearLayout) view.findViewById(R.id.ll_splits);

        Button_EditProfile = (TextView) view.findViewById(R.id.tv_edit);


        FullUpperView.setGravity(Gravity.CENTER);

        Button_Upcoming.setVisibility(View.GONE);
        Button_Previous.setVisibility(View.GONE);

        Line_Spliter = (TextView) view.findViewById(R.id.tl_spliter_line);
        Line_Upcoming = (TextView) view.findViewById(R.id.tl_upcoming_line);
        Line_Previous = (TextView) view.findViewById(R.id.tl_previous_line);

        ProfileImage = (ImageView) view.findViewById(R.id.iv_proimage);
        UserName = (TextView) view.findViewById(R.id.tv_proname);
        FollowersCount = (TextView) view.findViewById(R.id.tv_followers);
        FollowingCount = (TextView) view.findViewById(R.id.tv_following);

        NoBookingText = (TextView) view.findViewById(R.id.tv_nobooking);

        NoFeedText = (LinearLayout) view.findViewById(R.id.nofeed);

        FollowingPrFollowerList = (RecyclerView) view.findViewById(R.id.rv_profile);
        FollowingPrFollowerList.setHasFixedSize(true);
        FollowingPrFollowerList.setLayoutManager(new LinearLayoutManager(getActivity()));

        //adapter=new EventsAdapter();
        adapter1 = new ProfileFollowingOrFollowerAdapter(1);
        FollowingPrFollowerList.setAdapter(adapter1);

        PRofileDetails();



        FullUpperView.setVisibility(View.GONE);

        Button_EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), EditProfile.class));

            }
        });

        Button_Splits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AutoLogin", getActivity().MODE_PRIVATE);

                Intent intent = new Intent(getActivity(),Splits.class);
                intent.putExtra("UserId",sharedPreferences.getString("UserId",""));
                startActivity(intent);

            }
        });


        Button_Splitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AutoLogin", getActivity().MODE_PRIVATE);

                Intent intent = new Intent(getActivity(),Splitters.class);
                intent.putExtra("UserId",sharedPreferences.getString("UserId",""));
                startActivity(intent);

            }
        });

        Button_Spliter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Line_Spliter.setVisibility(View.VISIBLE);
                Line_Upcoming.setVisibility(View.GONE);
                Line_Previous.setVisibility(View.GONE);

//                adapter1 = new ProfileFollowingOrFollowerAdapter(1);
//                FollowingPrFollowerList.setAdapter(adapter1);

                Vol_FeedList();

            }
        });

        Button_Upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Line_Spliter.setVisibility(View.GONE);
                Line_Upcoming.setVisibility(View.VISIBLE);
                Line_Previous.setVisibility(View.GONE);

                FromPrevious = false;

                adapter = null;

                BookingHistory("1", "1");

            }
        });

        Button_Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Line_Spliter.setVisibility(View.GONE);
                Line_Upcoming.setVisibility(View.GONE);
                Line_Previous.setVisibility(View.VISIBLE);

                FromPrevious = true;

                adapter = null;

                BookingHistory("2", "1");

            }
        });

        if (AppData.imageUploadStatus == true)
            ProfileImage.setEnabled(true);
        else
            ProfileImage.setEnabled(false);

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void PRofileDetails() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AutoLogin", getActivity().MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "profile_control?userid=" + sharedPreferences.getString("UserId", "")+"&logged_in="+sharedPreferences.getString("UserId","");

        Log.d("Profile url", ":::" + url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {


                    String status = response.getString("status");
                    String message = response.getString("message");


                    if (status.equals("success")) {

                        if (message.equals("data found")) {

                            JSONObject Details = response.getJSONObject("Details");

                            String name = Details.getString("name");
                            int splits = Details.getInt("splits");
                            int splitter = Details.getInt("splitter");
                            String image = Details.getString("image");
                            String push_status = Details.getString("push_status");
                            String private_status = Details.getString("private_status");
                            String rating = Details.getString("rating");


                            ProfileRating.setRating(Float.parseFloat(rating));
                            UserName.setText(name);
                            FollowersCount.setText(String.valueOf(splits));
                            FollowingCount.setText(String.valueOf(splitter));

                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AutoLogin", getActivity().MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("Push_Status", push_status);

                            editor.putString("Private_Status", private_status);

                            editor.commit();

                            if (!(image.equals("")) && !(image.isEmpty()) && image != null) {

                                Picasso.with(getActivity())
                                        .load(image)
                                        .placeholder(R.drawable.appicon_round)
                                        .error(R.drawable.appicon_round)
                                        .transform(new RoundedTransformation())
                                        .into(ProfileImage);

                            }


                        }

                    }

                    Vol_FeedList();

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

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {


                    try {

                        Log.d("Open Camera", "----------------------------");

                        photofile = createImageFile();
                        Log.d("image path--", String.valueOf(photofile));


                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photofile));
                        startActivityForResult(intent, REQUEST_CAMERA);

                        dialog.dismiss();

                    } catch (Exception e) {

                        Log.v("Catch Exception", e.toString());

                    }


                } else if (items[item].equals("Choose from Library")) {

                    Log.d("Open Gallary", "----------------------------");

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

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);


        UploadImage upload = new UploadImage();
        upload.execute();

        return encImage;
    }

    File createImageFile() throws IOException {

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());///new approach
        String imagefilename = "IMAGE_" + timestamp + "_";///new approach

        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        File image = File.createTempFile(imagefilename, ".jpg", storageDirectory);/////new approach

        return image;


    }

    public void rotateimage(Bitmap bitm) {

        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(filePath.toString());
        } catch (Exception e) {


            e.printStackTrace();
        }
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix = new Matrix();
        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            default:

        }
        encodedImage = encodeImage(Bitmap.createBitmap(bitm, 0, 0, bitm.getWidth(), bitm.getHeight(), matrix, true));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null)
            Log.d("on Activity-----", "requestcode-" + requestCode + ",resultcode-" + resultCode + ",data-" + data.getData());

        if (requestCode == REQUEST_GALLERY && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery

                Log.d("Image from Gallary", "----------------------------" + filePath);


                //  bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                if (!filePath.equals("") || !filePath.equals(null)) {
                    Picasso.with(getActivity()).load(filePath)
                            .placeholder(R.drawable.cemera)
                            .fit()
                            .transform(new RoundedTransformation())
                            .into(ProfileImage);
                } else {

                    Picasso.with(getActivity()).load(R.drawable.cemera)
                            .placeholder(R.drawable.cemera)
                            .fit()
                            .transform(new RoundedTransformation())
                            .into(ProfileImage);

                }

                InputStream imageStream = getActivity().getContentResolver().openInputStream(filePath);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                Bitmap resized = Bitmap.createScaledBitmap(selectedImage, 100, 100, true);

                encodedImage = encodeImage(resized);

                //uploadImage();
            } catch (IOException e) {
                e.printStackTrace();

            }
        } else if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK && photofile != null) {
            filePath = Uri.fromFile(photofile);
            try {
                //Getting the Bitmap from Gallery

                Log.d("Image from Camera", "----------------------------" + filePath);


                // bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                if (!filePath.equals("") || !filePath.equals(null)) {
                    Picasso.with(getActivity()).load(filePath)
                            .placeholder(R.drawable.cemera)
                            .fit()
                            .transform(new RoundedTransformation())
                            .into(ProfileImage);
                } else {

                    Picasso.with(getActivity()).load(R.drawable.cemera)
                            .placeholder(R.drawable.cemera)
                            .fit()
                            .transform(new RoundedTransformation())
                            .into(ProfileImage);

                }

                InputStream imageStream = getActivity().getContentResolver().openInputStream(filePath);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //encodedImage = encodeImage(selectedImage);
                Bitmap resized = Bitmap.createScaledBitmap(selectedImage, 100, 100, true);
                rotateimage(resized);

                //uploadImage();
            } catch (IOException e) {
                e.printStackTrace();

            }

        } else {
            Toast.makeText(getActivity(), "Image Error", Toast.LENGTH_SHORT).show();
        }
    }


    class UploadImage extends AsyncTask<Void, Void, Void> {
        JSONObject jsonObject;
        String erormessage, result;
        okhttp3.Request req;
        String responce_code = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... params) {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AutoLogin", getActivity().MODE_PRIVATE);

            String url = AppData.DomainUrlForProfile + "userimageupload_control?user_id=" + sharedPreferences.getString("UserId", "")
                    + "&user_image=" + encodedImage;


            try {

                Log.v("Signup url", ":::" + url);

                OkHttpClient okHttpClient = new OkHttpClient();
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

                    okhttp3.Response httpResponse = okHttpClient.newCall(req).execute();
                    responce_code = String.valueOf(httpResponse.code());

                    Log.d("responce_code : ", (responce_code));
                    //  Log.d("response", String.valueOf(httpResponse));
                    result = httpResponse.body().string();
                    Log.d("response:::", result);

                    if (responce_code.equals("200")) {

                        JSONObject Jobject = new JSONObject(result);
                        erormessage = Jobject.getString("message");
                        Log.d("erormessage:::", erormessage);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("ResultException", e.toString());
                }
            } catch (Exception e) {

                e.printStackTrace();
                Log.d("Submit Exception : ", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (responce_code == null) {

                Toast.makeText(getActivity(), "Server failed to respond", Toast.LENGTH_SHORT).show();

            } else if (responce_code.equals("200")) {

                Toast.makeText(getActivity(), "" + erormessage, Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(getActivity(), "Server failed to respond", Toast.LENGTH_SHORT).show();

        }


    }


    public void BookingHistory(String status, String Page) {

        NoFeedText.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AutoLogin", getActivity().MODE_PRIVATE);


        String url = AppData.DomainUrlForProfile + "bookinghistory_control?userid=" + sharedPreferences.getString("UserId", "") + "&status=" + status + "&page=" + Page + "&limit=10";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Log.v("BookingHistory Response::", response.toString());

                try {


                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("success")) {

                        if (message.equals("data found")) {

                            BookingHistoryValues = new ArrayList<Booking_SetterGetter>();
                            BookingHistoryValues.clear();


                            JSONArray Details = response.getJSONArray("Details");

                            if (Details.length() > 0) {

                                NoBookingText.setVisibility(View.GONE);
                                FollowingPrFollowerList.setVisibility(View.VISIBLE);


                                for (int i = 0; i < Details.length(); i++) {

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

                                    Booking_SetterGetter booking_setterGetter = new Booking_SetterGetter(event_id, eventname, venue_name, venue_address, reference_id, table_name, event_month, event_date, event_time, table_id, host_id,host_name,total_seat,remaining_seat,table_cost,group_id);
                                    BookingHistoryValues.add(booking_setterGetter);


                                }


                                if (adapter == null) {

                                    adapter = new BookingAdapter_Upcoming(BookingHistoryValues, getActivity(), Profile.this, status, FromPrevious, "Profile");
                                    FollowingPrFollowerList.setAdapter(adapter);

                                } else {

                                    adapter.LoadMore(BookingHistoryValues);

                                }

                            } else {

                                FollowingPrFollowerList.setVisibility(View.GONE);
                                NoBookingText.setVisibility(View.VISIBLE);

                            }


                        } else {

                            FollowingPrFollowerList.setVisibility(View.GONE);
                            NoBookingText.setVisibility(View.VISIBLE);

                        }


                    } else {

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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AutoLogin", getActivity().MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"feed_control?userid="+sharedPreferences.getString("UserId", "");

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

                            NoFeedText.setVisibility(View.GONE);
                            FollowingPrFollowerList.setVisibility(View.VISIBLE);

                            UserFeedList_Adapter userFeedList_adapter = new UserFeedList_Adapter(FeedListDetails,getActivity());
                            FollowingPrFollowerList.setAdapter(userFeedList_adapter);


                        }else {

                            NoFeedText.setVisibility(View.VISIBLE);
                            FollowingPrFollowerList.setVisibility(View.GONE);

                        }

                    }else {

                        NoFeedText.setVisibility(View.VISIBLE);
                        FollowingPrFollowerList.setVisibility(View.GONE);

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


    @Override
    public void onResume() {
        super.onResume();

        Log.v("OwnProfile:::::::","onResume");

        PRofileDetails();

    }
}
