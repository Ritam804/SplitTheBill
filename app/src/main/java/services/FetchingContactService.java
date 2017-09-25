package services;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.settergetter.Contact_Show_SetterGetter;

import java.util.ArrayList;
import java.util.List;

import setergeter.Contacts_SetterGetter;

/**
 * Created by ritam on 27/04/17.
 */

public class FetchingContactService extends IntentService {
    public FetchingContactService() {
        super("");
    }

    @Override
    protected void onHandleIntent( Intent intent) {


        Log.v("FetchingService","onHandleIntent");

        AppData.contactVOList = new ArrayList();
        Contacts_SetterGetter contactVO;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String photo = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                    Log.d("Photo Uri",":::"+photo);


                    contactVO = new Contacts_SetterGetter();
                    contactVO.setName(name);
                   // contactVO.setStb_status("0");

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);

                    if (phoneCursor != null){

                        if (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contactVO.setNumber(phoneNumber);
                            contactVO.setPhoto_uri(photo);
                            contactVO.setCheckStatus(false);
                        }

                        phoneCursor.close();

                    }



//                    Cursor emailCursor = contentResolver.query(
//                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                            new String[]{id}, null);

//                    Cursor emailCursor = contentResolver.query(
//                            ContactsContract.Data.CONTENT_URI,
//                            null,
//                            ContactsContract.Data.MIMETYPE + "=? OR " + ContactsContract.Data.MIMETYPE + "=?",
//                            new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE},
//                            ContactsContract.Data.DISPLAY_NAME);
//
//                    if (emailCursor != null){
//
//
//                        AppData.Contacts = new ArrayList<Contact_Show_SetterGetter>();
//                        String Phone = null;
//                        boolean EmailHas = true;
//
//                        while (emailCursor.moveToNext()) {
//
//
//
//                            String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//                            String Username = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
////                            Log.v("EmailId::",emailId);
////                            Log.v("EmailId::",""+emailCursor.getPosition());
////                            Log.v("EmailId::",""+emailCursor.getType(emailCursor.getPosition()));
////                            Log.v("EmailId::",""+emailCursor.getColumnName(emailCursor.getPosition()));
//
//
//
//                            if (emailCursor.getType(emailCursor.getPosition()) == 3){
//
//                                if (EmailHas){
//
//                                    Phone = emailId;
//                                    EmailHas = false;
//
//                                }else {
//
//                                    Contact_Show_SetterGetter contact_show_setterGetter = new Contact_Show_SetterGetter(emailId,Phone,Username);
//                                    AppData.Contacts.add(contact_show_setterGetter);
//
//                                    EmailHas = true;
//
//                                }
//
//
//
//                            }else {
//
//                                Contact_Show_SetterGetter contact_show_setterGetter = new Contact_Show_SetterGetter("No",emailId,Username);
//                                AppData.Contacts.add(contact_show_setterGetter);
//
//                            }
//
//
//                        }
//
//                    }
//
//                    Log.v("AllFields::",AppData.Contacts.toString());
//
//                    for (int i=0;i<AppData.Contacts.size();i++){
//
//                        Log.v("ArrayValue::",AppData.Contacts.get(i).getName() + " And " + AppData.Contacts.get(i).getNumber() + " And " +AppData.Contacts.get(i).getEmail());
//
//                    }

                    AppData.contactVOList.add(contactVO);
                }
            }


        }

    }

    @Override
    public void onCreate() {
        super.onCreate();




    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Log.v("FetchingService","OnStart");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.v("FetchingService","OnDestroy");

        Log.v("FetchingService2","Calling Service2");
        if (AppData.contactVOList.size()>0) {
            Intent i = new Intent(getBaseContext(), StbCheckingContactService.class);
            i.setAction("");
            startService(i);
        }

    }
}
