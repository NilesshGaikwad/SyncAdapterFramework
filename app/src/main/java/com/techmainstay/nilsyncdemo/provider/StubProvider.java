package com.techmainstay.nilsyncdemo.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.techmainstay.nilsyncdemo.MainActivity;
import com.techmainstay.nilsyncdemo.helper.DatabaseHelper;
import com.techmainstay.nilsyncdemo.model.Person;

import java.util.HashMap;

import io.realm.Realm;

import static com.techmainstay.nilsyncdemo.MainActivity.AUTHORITY;
import static com.techmainstay.nilsyncdemo.MainActivity.CreateSyncAccount;
import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;


public class StubProvider extends ContentProvider {
    /*
     * Always return true, indicating that the
     * provider loaded correctly.
     */
    static final String DATABASE_NAME = "College";
    static final String STUDENTS_TABLE_NAME = "students";

    static final String PROVIDER_NAME = "com.techmainstay.nilsyncdemo.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/students";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String NAME = "name";
    static final String GRADE = "grade";
    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    static final int STUDENTS = 1;
    static final int STUDENT_ID = 2;
    DatabaseHelper dbHelper;
    public static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
        uriMatcher.addURI(PROVIDER_NAME, "students/*", STUDENT_ID);
    }

    public SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        System.out.println("In Provider Oncreate");
        Context context = getContext();

         return true;
    }





    /*
     * Return no type for MIME type
     */
    @Override
    public String getType(Uri uri) {

        System.out.println("In Provider gettype");
        return null;
    }
    /*
     * query() always returns no results
     *
     */
    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {
        System.out.println("In Provider cursor query");
        Cursor cursor = null;
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase sqdb = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {

            case STUDENTS:
                cursor = sqdb.query(STUDENTS_TABLE_NAME, projection, selection,

                        selectionArgs, null, null, sortOrder);

                break;
        }


        return cursor;
    }

    /*
     * insert() always returns null (no URI)
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        System.out.println("In Provider insert.......................................");
        /**
         * Add a new student record
         */
        Uri _uri=null;
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase sqdb = dbHelper.getWritableDatabase();
        switch(uriMatcher.match(uri)){
            case STUDENTS:
                long rowID = sqdb.insert(	STUDENTS_TABLE_NAME, null, values);
                /**
                 * If record is added successfully
                 */
                if (rowID > 0) {
                     _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    System.out.println("inserted baba..................................");
            /*Bundle settingsBundle = new Bundle();
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_MANUAL, true);
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        *//*
         * Request the sync for the default account, authority, and
         * manual sync settings
         *//*

            ContentResolver.requestSync(CreateSyncAccount(getContext()), AUTHORITY, settingsBundle);*/
                    //return _uri;
                }
                break;
           default: throw new SQLException("Failed to add a record into " + uri);
}


return _uri;

}
    /*
     * delete() always returns "no rows affected" (0)
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        System.out.println("In Provider delete");
        return 0;
    }
    /*
     * update() always returns "no rows affected" (0)
     */
    public int update(
            Uri uri,
            ContentValues values,
            String selection,
            String[] selectionArgs) {
        System.out.println("In Provider update");
        return 0;
    }
}
