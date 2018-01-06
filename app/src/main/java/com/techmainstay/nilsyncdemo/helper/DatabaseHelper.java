package com.techmainstay.nilsyncdemo.helper;
/**
 * Created by Nilesh on 04-Jan-18.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */

public class DatabaseHelper extends SQLiteOpenHelper {

    public SQLiteDatabase db;
    static final String DATABASE_NAME = "College";
    static final String STUDENTS_TABLE_NAME = "students";
    static final int DATABASE_VERSION = 1;


    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + STUDENTS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT NOT NULL, " +
                    " grade TEXT NOT NULL);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(this.getWritableDatabase());
    }


        @Override
        public void onCreate(SQLiteDatabase db) {
         try{
             db.execSQL(CREATE_DB_TABLE);

          }catch(Exception e){
             System.out.println(e.toString());
         }

            System.out.println("Database table created.................");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  STUDENTS_TABLE_NAME);
            onCreate(db);
        }
}



