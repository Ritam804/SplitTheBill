package com.ritam.splitthebill.splitthebill.push;

/**
 * Created by ritam on 15/12/16.
 */


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.adapter.MessageAdapter;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import setergeter.ChatList_SetterGetter;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    String SenderName, Content, SentDate, StudentName, NotificationType;
    int SenderUserId, SenderUserType, Id, ClassId, StudentId, Task_StudentId, Task_StudentTaskId;
    boolean IsRead, Task_IsDeleted;
    String Task_TeacherName, Task_DueDate, Task_StudentName;

    String message = null, header = null, groupid = null, fromid = null, fromname = null, groupname = null;

    public static String MessageDetails_Action = "MessageDetails";

    public static String Message_Action = "Message";

    public static String Update_BadgeCount_Action = "Update_BadgeCount";

    public static String Review_Action = "Review";

    String username, date, restuarantname, restuarantaddress, price, orderid, orderstatus, quantity, ordernumber, type, homedelivery, deliveryaddress, deliveryphone, confirmcode;
    int totalcount;

    int commentid;
    String comment, commentdate;

    public static String Notification_Action = "NotificationOrder";


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            //etay asche message ta.........


            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

//            if (remoteMessage.getNotification().getClickAction().equals("Currentorders")) {
//
//                JSONObject data = null;
//
//                data = new JSONObject(remoteMessage.getData());
//
//                try {
//                    type = data.getString("type");
//
//                    if (type.equals("review")) {
//
//                        username = data.getString("username");
//                        totalcount = data.getInt("totalcount");
//                        commentid = data.getInt("commentid");
//                        comment = data.getString("comment");
//                        comment = data.getString("comment");
//                        commentdate = data.getString("commentdate");
//
//                    } else if (type.equals("Currentorders")) {
//
//                        username = data.getString("username");
//                        date = data.getString("date");
//                        restuarantname = data.getString("restuarantname");
//                        restuarantaddress = data.getString("restuarantaddress");
//                        price = data.getString("price");
//                        orderid = data.getString("orderid");
//                        orderstatus = data.getString("orderstatus");
//                        quantity = data.getString("quantity");
//                        ordernumber = data.getString("ordernumber");
//                        totalcount = data.getInt("totalcount");
//                        homedelivery = data.getString("homedelivery");
//                        deliveryaddress = data.getString("deliveryaddress");
//                        deliveryphone = data.getString("deliveryphone");
//                        confirmcode = data.getString("confirmcode");
//
//                        SharedPreferences AutoLoginShare = getSharedPreferences("AUTOLOGIN", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = AutoLoginShare.edit();
//                        editor.putString("CurrentOrderCount", String.valueOf(totalcount));
//                        editor.commit();
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            Log.d(TAG, "Message Notification Click_Action: " + remoteMessage.getNotification().getClickAction());

            Log.d(TAG, "Message Notification Type: " + remoteMessage.getNotification().getTag());

            Log.d(TAG, "Message Notification Type: " + remoteMessage.getNotification().getBodyLocalizationKey());
            Log.d(TAG, "Message Notification Type: " + remoteMessage.getNotification().getColor());
            Log.d(TAG, "Message Notification Type: " + remoteMessage.getNotification().getIcon());
            Log.d(TAG, "Message Notification Type: " + remoteMessage.getNotification().getSound());
            Log.d(TAG, "Message Notification Type: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Message Notification Type: " + remoteMessage.getNotification().getTitleLocalizationKey());

            NotificationType = remoteMessage.getNotification().getTag();


            if (AppData.isNotificationPage().equals("YES")) {


                Intent NormalUser = new Intent();
                NormalUser.setAction(Notification_Action);

                sendBroadcast(NormalUser);

            } else {

                sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getClickAction(), remoteMessage.getNotification().getTitle());

                Intent NormalUser = new Intent();
                NormalUser.setAction(Update_BadgeCount_Action);
                NormalUser.putExtra("Purpose","Notification");

                sendBroadcast(NormalUser);


            }

//            Log.v("Onpage:", AppData.isCurrentOrder());
//            Log.v("Onpage:", AppData.isMenuPage());
//
//
//            if (type.equals("Currentorders")) {
//
//                if (AppData.isCurrentOrder().equals("YES")) {
//
//                    Intent NormalUser = new Intent();
//                    NormalUser.setAction(CurrentOrder_Action);
//
//
//                    NormalUser.putExtra("username", "" + username);
//                    NormalUser.putExtra("date", "" + date);
//                    NormalUser.putExtra("restuarantname", "" + restuarantname);
//                    NormalUser.putExtra("restuarantaddress", "" + restuarantaddress);
//                    NormalUser.putExtra("price", "" + price);
//                    NormalUser.putExtra("orderid", "" + orderid);
//                    NormalUser.putExtra("orderstatus", "" + orderstatus);
//                    NormalUser.putExtra("quantity", "" + quantity);
//                    NormalUser.putExtra("ordernumber", "" + ordernumber);
//                    NormalUser.putExtra("totalcount", "" + totalcount);
//                    NormalUser.putExtra("FromPush", true);
//                    NormalUser.putExtra("type", type);
//                    NormalUser.putExtra("homedelivery", homedelivery);
//                    NormalUser.putExtra("deliveryaddress", deliveryaddress);
//                    NormalUser.putExtra("deliveryphone", deliveryphone);
//                    NormalUser.putExtra("confirmcode",confirmcode);
//
//                    sendBroadcast(NormalUser);
//
//                } else if (AppData.isMenuPage().equals("YES") || AppData.isHomePage().equals("YES")) {
//
//                    Intent NormalUser = new Intent();
//                    NormalUser.setAction(CurrentOrder_Action);
//
//
//                    NormalUser.putExtra("username", "" + username);
//                    NormalUser.putExtra("date", "" + date);
//                    NormalUser.putExtra("restuarantname", "" + restuarantname);
//                    NormalUser.putExtra("restuarantaddress", "" + restuarantaddress);
//                    NormalUser.putExtra("price", "" + price);
//                    NormalUser.putExtra("orderid", "" + orderid);
//                    NormalUser.putExtra("orderstatus", "" + orderstatus);
//                    NormalUser.putExtra("quantity", "" + quantity);
//                    NormalUser.putExtra("ordernumber", "" + ordernumber);
//                    NormalUser.putExtra("totalcount", "" + totalcount);
//                    NormalUser.putExtra("FromPush", true);
//                    NormalUser.putExtra("type", type);
//                    NormalUser.putExtra("homedelivery", homedelivery);
//                    NormalUser.putExtra("deliveryaddress", deliveryaddress);
//                    NormalUser.putExtra("deliveryphone", deliveryphone);
//                    NormalUser.putExtra("confirmcode",confirmcode);
//
//                    sendBroadcast(NormalUser);
//
//                    sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getClickAction(), remoteMessage.getNotification().getTitle());
//
//
//                } else {
//
//                    sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getClickAction(), remoteMessage.getNotification().getTitle());
//
//
//                }
//
//            } else if (type.equals("review")) {
//
//                if (AppData.isRestaurantInformationPage().equals("YES")) {
//
//                    Intent NormalUser = new Intent();
//                    NormalUser.setAction(Review_Action);
//                    sendBroadcast(NormalUser);
//
//                } else {
//
//                    sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getClickAction(), remoteMessage.getNotification().getTitle());
//
//
//                }
//
//            }


        } else {

            Log.v("Message::",AppData.isMessage());
            Log.v("MessageDetails::",AppData.isMessageDetails());

            Log.v(TAG, "No NotificationType");

            Log.d(TAG, "Message data payload2: " + remoteMessage.getData());


            JSONObject data = new JSONObject(remoteMessage.getData());

            Log.d(TAG, "Message data payload3: " + data);


            try {

                Log.d(TAG, "Message data payload4: " + data.getString("message"));

//                Log.d(TAG, "Message data payload5: " + data.getJSONObject("message"));
//
//                JSONObject FullMessage = new JSONObject(data.getString("message"));


                message = data.getString("message");
                header = data.getString("header");
                groupid = data.getString("groupID");
                fromid = data.getString("senderID");
                fromname = data.getString("senderName");
                groupname = data.getString("groupName");


                Log.v(TAG, "Message::" + message);
                Log.v(TAG, "Header::" + header);
                Log.v(TAG, "GroupId::" + groupid);
                Log.v(TAG, "FromId::" + fromid);
                Log.v(TAG, "FromName::" + fromname);
                Log.v(TAG, "GroupName::" + groupname);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (AppData.isMessage().equals("NO") && AppData.isMessageDetails().equals("NO")) {

                sendNotification(message, "message", header);

                Intent NormalUser = new Intent();
                NormalUser.setAction(Update_BadgeCount_Action);
                NormalUser.putExtra("Purpose","Message");

                sendBroadcast(NormalUser);

            } else if (AppData.isMessageDetails().equals("YES")) {

                if (AppData.isMessage().equals("YES")){

                    Intent NormalUser = new Intent();
                    NormalUser.setAction(Message_Action);
                    NormalUser.putExtra("senderId", "" + fromid);
                    NormalUser.putExtra("message", "" + message);

                    sendBroadcast(NormalUser);

                }else {

                    if (AppData.QBGroupId.equals(groupid)) {

                        Intent NormalUser = new Intent();
                        NormalUser.setAction(MessageDetails_Action);
                        NormalUser.putExtra("senderId", "" + fromid);
                        NormalUser.putExtra("message", "" + message);

                        sendBroadcast(NormalUser);

                    } else {

                        sendNotification(message, "message", header);

                    }

                }




            }else if (AppData.isMessage().equals("YES")){

                Intent NormalUser = new Intent();
                NormalUser.setAction(Message_Action);
                NormalUser.putExtra("senderId", "" + fromid);
                NormalUser.putExtra("message", "" + message);

                sendBroadcast(NormalUser);

            }else {

                sendNotification(message, "message", header);

            }


        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sendNotification(String messageBody, String Click_Action, String title)

    {

        Log.d("Send_Notification", "---------------");

        if (Click_Action.equals("notification")) {

            Intent intent = null;


            intent = new Intent(Click_Action);
            intent.putExtra("totalcount", "" + totalcount);
            intent.putExtra("FromPush", true);
            intent.putExtra("type", "notification");


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification n = new Notification.Builder(this)
                    .setContentTitle("SPLIT THE BILL")
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.appicon_big)
                    .setColor(getResources().getColor(R.color.colorAccent)).build();

            n.flags |= Notification.FLAG_AUTO_CANCEL;


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, n);

        } else {


//                QBSettings.getInstance().init(getApplicationContext(), AppData.QB_ApplicationId, AppData.QB_AuthorizationKey, AppData.QB_AuthorizationSecret);
//                QBSettings.getInstance().setAccountKey(AppData.QB_ACCOUNT_KEY);
//
//                QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
//                requestBuilder.setLimit(100);
//
//                QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
//                        new QBEntityCallback<ArrayList<QBChatDialog>>() {
//                            @Override
//                            public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {
//
//
//
//
//
//
//                                Log.v("QBChatDialog::",result.toString());
//
//                                Log.v("QBChatDialog_Bundle::",params.toString());
//
//                                if (result.size() > 0){
//
//
//                                    for (int i=0;i<result.size();i++){
//
//
//
//                                        if (groupid.equals(result.get(i).getDialogId())) {
//
//                                            AppData.OccupantIds = result.get(i).getOccupants();
//
//                                        }
//
//
//
//
//
//                                    }
//
//
//
//                                }
//
//
//
//                            }
//
//                            @Override
//                            public void onError(QBResponseException responseException) {
//
//
//
//                            }
//                        });

            Intent intent = null;
            intent = new Intent("messageDetails");
            intent.putExtra("GroupName", groupname);
            intent.putExtra("GroupId", groupid);
            intent.putExtra("FromPush", true);
            intent.putExtra("type", "message");


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification n = new Notification.Builder(this)
                    .setContentTitle("SPLIT THE BILL")
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.appicon_big)
                    .setColor(getResources().getColor(R.color.colorAccent)).build();

            n.flags |= Notification.FLAG_AUTO_CANCEL;


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, n);

        }


    }
}
