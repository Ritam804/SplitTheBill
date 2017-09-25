package com.ritam.splitthebill.splitthebill.apphelper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.Digits;
import com.ritam.splitthebill.splitthebill.settergetter.Contact_Show_SetterGetter;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.ArrayList;
import java.util.List;


import io.fabric.sdk.android.Fabric;
import setergeter.Contacts_SetterGetter;
import setergeter.Phone_Selected_SetterGetter;
import setergeter.Promoter_SetterGetter;
import setergeter.SelectedContact_SetterGetter;
import setergeter.Splitter_Selected_SetterGetter;
import setergeter.Spltter_Contacts_SetterGetter;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by ritam on 30/07/16.
 */
public class AppData extends MultiDexApplication{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    public static final String TWITTER_KEY = "R9r00XSQ44veYXW2rproB9S9v";
    public static final String TWITTER_SECRET = "6rVfGTQr1ys7CcZ11I8VuvErPknBRa9SnISw95OZbfsKQ4aA39";

    public static boolean Contact_Block = false;
    public static ArrayList<Contacts_SetterGetter> contactVOList;
    public static ArrayList<Spltter_Contacts_SetterGetter> Splitter_contactVOList = new ArrayList<Spltter_Contacts_SetterGetter>();

    public static ArrayList<Phone_Selected_SetterGetter> PhoneSelectectedContacts;
    public static ArrayList<Splitter_Selected_SetterGetter> SplitterSelectedContacts;

    public static ArrayList<SelectedContact_SetterGetter> selectedContact;

    private static com.ritam.splitthebill.splitthebill.apphelper.AppData mInstance;
    public RequestQueue mRequestQueue;

    public static String DomainUrl="http://esolz.co.in/lab6/stb/app_control/";
    public static String DomainUrlForProfile = "http://esolz.co.in/lab6/stb/";
    public static String EventId;
    public static String TableId;
    public static String MaleCount;
    public static String FemaleCount;
    public static String FromButton;
    public static String Amount;
    public static String HostName;
    public static String HostId;
    public static String GroupId;
    public static String FromPage;
    public static int MaximumTotalMember;
    public static int NumberOfAvailableSeat;
    public static int SignUpPageNumber;

    public static String SignUpName = "";
    public static String SignUpUserName = "";
    public static String SignUpEmail = "";

    public static int MinimumAmount;
    public static int MaximumAmount;
    public static int SelectedAmount;

    public static List<Integer> OccupantIds;




    public static RecyclerView recyclerView;

    public static TextView NoEventText;

    public static FragmentActivity EventListActivity;


    public static String DeviceToken;

    public static String RegistrationId;


    public static String FilterLat = "";

    public static String FilterLong = "";


    public static int NotificationFirstPAge = 1;


    public static String QBGroupId;





    public static boolean imageUploadStatus=false;

    //public static ArrayList<Contacts_SetterGetter> Contacts = new ArrayList<Contacts_SetterGetter>();

    public static final String TAG = AppData.class.getSimpleName();

    public static SharedPreferences preferCheckAppStateForNotification;
    public static SharedPreferences.Editor editForNotification;

    public static int ContactPageCount = 1;

    public static String FromEdit = "";

    public static String CardId;

    public static boolean ContactSynced = false;

    public static final String QB_ApplicationId = "60090";
    public static final String QB_AuthorizationKey = "YvfB5zgJWgMBgB3";
    public static final String QB_AuthorizationSecret = "a6Acyxeak3472qb";
    public static final String QB_ACCOUNT_KEY = "nDWa2RNWFwg5vxo2Uspk";


    ///////////......... For push to know the current page name..........///////////////

    public static SharedPreferences preferCheckAppStateForMessageDetails;
    public static SharedPreferences.Editor editForMessageDetails;


    public static SharedPreferences preferCheckAppStateForMessage;
    public static SharedPreferences.Editor editForMessage;

    public static boolean FromGuestList = false;
    public static boolean GroupMessage = false;
    public static boolean OwnProfile = false;
    public static boolean FromMessagePage = false;
    public static boolean FromNotificationPage = false;


    public static boolean RateChanged = false;

    public static ArrayList<Contact_Show_SetterGetter> Contacts;

    public static ArrayList<Promoter_SetterGetter> PromoterDetails;

    public static String SelectedPromoterId;

    ///////////////////////////////////////

    ///////////////////////////////////////


//    public static AppData getInstance() {
//        return singleton;
//    }
    @Override
    public void onCreate() {
        super.onCreate();
       // singleton = this;
        //extractAvenir();
//        authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new Crashlytics(), new Digits.Builder().build(), new Twitter(authConfig));
//
//        Crashlytics.setBool(CRASHLYTICS_KEY_CRASHES, areCrashesEnabled());

        mInstance = this;
        preferCheckAppStateForMessageDetails = getSharedPreferences("CHECK_MESSAGEDETAILS_STATE", Context.MODE_PRIVATE);
        preferCheckAppStateForMessage = getSharedPreferences("CHECK_MESSAGE_STATE", Context.MODE_PRIVATE);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

        //Fabric.with(this, new Twitter(authConfig));
        Fabric.with(this, new Twitter(authConfig), new TwitterCore(authConfig), new Digits.Builder().build(), new Crashlytics());

        preferCheckAppStateForNotification = getSharedPreferences("CHECK_NOTIFICATIONPAGE_STATE", Context.MODE_PRIVATE);



    }
    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(base);
        MultiDex.install(this);
        //
    }


    ///////////////////For MessageDetails PAge////////////////////////

    public static String isMessageDetails() {
        return preferCheckAppStateForMessageDetails.getString("AppState", "");
    }

    public static void setIsMessageDetails(String isAppRunning) {
        editForMessageDetails = preferCheckAppStateForMessageDetails.edit();
        editForMessageDetails.putString("AppState", isAppRunning);
        editForMessageDetails.commit();
    }

    //////////////////////////////////////////////////////////////////////

    ///////////////////For Message PAge////////////////////////

    public static String isMessage() {
        return preferCheckAppStateForMessage.getString("AppState", "");
    }

    public static void setIsMessage(String isAppRunning) {
        editForMessage = preferCheckAppStateForMessage.edit();
        editForMessage.putString("AppState", isAppRunning);
        editForMessage.commit();
    }

    //////////////////////////////////////////////////////////////////////



    public static synchronized AppData getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    ///////////////////For RestaurantInformation PAge////////////////////////

    public static String isNotificationPage() {
        return preferCheckAppStateForNotification.getString("AppState", "");
    }

    public static void setIsNotificationPage(String isAppRunning) {
        editForNotification = preferCheckAppStateForNotification.edit();
        editForNotification.putString("AppState", isAppRunning);
        editForNotification.commit();
    }

    //#################################################################//

}
