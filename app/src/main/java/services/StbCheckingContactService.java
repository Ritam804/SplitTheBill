package services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ritam.splitthebill.splitthebill.activity.ContactPage;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import setergeter.Contacts_SetterGetter;

/**
 * Created by su on 9/6/17.
 */

public class StbCheckingContactService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    JSONArray DataArray;
    int updated_counter=0,remaining=0,total=0;


    public StbCheckingContactService() {
        super("");
    }

    @Override
    protected void onHandleIntent( Intent intent) {

        AppData.ContactSynced = false;

        DataArray=new JSONArray();

        total=AppData.contactVOList.size();
        remaining=AppData.contactVOList.size();

        for(int i=0;i<AppData.contactVOList.size();i++){

            Contacts_SetterGetter contacts_setterGetter=AppData.contactVOList.get(i);

            try {


                JSONObject jsonObject=new JSONObject();

                jsonObject.put("Phone",contacts_setterGetter.getNumber());

                DataArray.put(i,jsonObject);


                // jsonArray.put(i,jsonObject);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Log.d("DataArray",DataArray.toString());

        sendContact();

    }

    public void sendContact(){


        String url=AppData.DomainUrlForProfile+"contactfetch_control?";

        Log.d("url",":::"+url);

        StringRequest jsonObjectRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response", ":::" + response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("status").equals("success")) {

                        JSONArray jsonArray=jsonObject.getJSONArray("details");

                        Log.d("size",""+jsonArray.length());


                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject object=jsonArray.getJSONObject(i);

                            AppData.contactVOList.get(i).setStb_status(object.getString("status"));

                            //updated_counter++;

                           // Log.d("return",""+AppData.contactVOList.get(i).getStb_status());

                        }


                    } else {


                    }

                    AppData.ContactSynced = true;

                } catch (JSONException e) {
                    e.printStackTrace();

                    AppData.ContactSynced = false;

                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error","--------------"+error.toString());

                AppData.ContactSynced = false;

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();


                params.put("phone",DataArray.toString());


                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
