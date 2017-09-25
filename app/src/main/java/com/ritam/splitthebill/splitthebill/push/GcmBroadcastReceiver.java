package com.ritam.splitthebill.splitthebill.push;///**
// *   EsolzApp [com.developer.esolzapp.pushnotification]
// *
// * This page is created for Esolz Technologies Pvt Ltd.
// *
// * All Copyright © protected 2014.
// *
// * Page is created on 30-Dec-2014, 3:39:08 PM by Dinabandhu Mondal.
// *
// **/
//
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * @author Ritam Bose
 *
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) {

		// Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                MyFirebaseMessagingService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
	}

}
