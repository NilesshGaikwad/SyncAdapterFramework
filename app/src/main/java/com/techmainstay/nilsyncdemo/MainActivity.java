package com.techmainstay.nilsyncdemo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.techmainstay.nilsyncdemo.helper.DatabaseHelper;
import com.techmainstay.nilsyncdemo.provider.StubProvider;

public class MainActivity  extends AppCompatActivity {
    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.techmainstay.nilsyncdemo.provider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "com.techmainstay.nilsyncdemo";
    // The account name
    public static final String ACCOUNT = "nilesh";
    // Instance fields
    Account mAccount;
    TextView showData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the dummy account
        mAccount = CreateSyncAccount(MainActivity.this);


        if (mAccount == null) {
            Log.d("nil", "<<<<Failed to create sync account.");
        } else {
            Log.d("nil", "<<<<Adding periodic sync");
          //  ContentResolver.addPeriodicSync(mAccount, AUTHORITY, Bundle.EMPTY,
            //        5);
        }


        //final ContentResolver[] mresolver = new ContentResolver[1];
        Button ref = (Button) findViewById(R.id.refresh);
         showData = (TextView) findViewById(R.id.show);

        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  mresolver[0] = getContentResolver();

                /*// Pass the settings flags by inserting them in a bundle
                Bundle settingsBundle = new Bundle();
                settingsBundle.putBoolean(
                        ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundle.putBoolean(
                        ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        *//*
         * Request the sync for the default account, authority, and
         * manual sync settings
         *//*

                ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);
                //ContentResolver.setSyncAutomatically(mAccount,AUTHORITY,true);
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
*/
                add();
                getdata();
            }
        });

    }


    public void getdata(){
        String sort_ordre = "name"+" ASC";
        String selection = null;
        String selectionargs[] = null;
        Cursor cursor = getContentResolver().query(StubProvider.CONTENT_URI, null,
                selection, selectionargs, sort_ordre);
        String data="";
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                cursor.getInt(0);
                cursor.getString(1);
                cursor.getString(2);
                data = data+"---"+"->" + cursor.getInt(0) + "->" + cursor.getString(1)
                        + "->" + cursor.getString(2)+ "->" + cursor.getInt(3);


                showData.setText(data);
            } while (cursor.moveToNext());
        }

    }

    public void add(){

        DatabaseHelper help = new DatabaseHelper(MainActivity.this);

        ContentValues values = new ContentValues();
        values.put("name", "nilesh");
        values.put("grade", "78");
        values.put("syncflag", "0");
        Uri uri = getContentResolver().insert(StubProvider.CONTENT_URI, values);

        Long id = Long.valueOf(uri.getLastPathSegment());

        if (id > 0) {
            System.out.println("Added ");
        } else {
            System.out.println("not inserted");
        }

        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
       

        ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);





    }












    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }
}
