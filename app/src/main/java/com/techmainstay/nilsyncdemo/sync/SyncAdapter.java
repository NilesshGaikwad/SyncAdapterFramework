package com.techmainstay.nilsyncdemo.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.techmainstay.nilsyncdemo.model.Person;
import com.techmainstay.nilsyncdemo.provider.StubProvider;

import org.json.JSONArray;

import java.util.ArrayList;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

/**
 * Created by Nilesh on 27-Dec-17.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {


    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;
    ArrayList<Person> list;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        System.out.println("sync constructor");
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter
     */

    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();

        System.out.println("In sync adapter");

    }


    @Override
    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {
    /*
     * Put the data transfer code here.
     */


        System.out.println("In on perform sync adapter");
        //get data from content provider
        ArrayList<Person> mydata = getdata();

        Gson gson = new Gson();
        String gdata = gson.toJson(mydata).toString();
System.out.println("data is--------------"+ gdata);

        AndroidNetworking.initialize(getContext());
        AndroidNetworking.post("ur server url ")
                .addBodyParameter("data",gson.toJson(mydata).toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        System.out.println("Response is--------------->>"+ response.toString());
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        System.out.println("Response is error--------------->>"+ error.toString());
                    }
                });

        //send data to server which is collected from content provider


        //update code after insertion to server
        ContentValues values = new ContentValues();
        values.put("syncflag", "1");
        getContext().getContentResolver().update(StubProvider.CONTENT_URI, values, null, null);


    }

    public ArrayList getdata() {
        String sort_ordre = "name" + " ASC";
        String selection = "syncflag"+ " = ?";
        String selectionargs[] = {String.valueOf(0)};
        Cursor cursor = getContext().getContentResolver().query(StubProvider.CONTENT_URI, null,
                selection, selectionargs, sort_ordre);
        String data = "";
        list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                cursor.getInt(0);
                String name = cursor.getString(1);
                String age = cursor.getString(2);
                int flag = cursor.getInt(3);

                Person person = new Person(name, age, flag);
                list.add(person);

                data = data + "---" + "->" + cursor.getInt(0) + "->" + cursor.getString(1)
                        + "->" + cursor.getString(2) + "->" + cursor.getInt(3);


                //showData.setText(data);
            } while (cursor.moveToNext());
        }
        return list;
    }

}
