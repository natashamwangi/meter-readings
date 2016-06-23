package com.nancy.loginregister.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Nancy on 05/27/2016.
 */
public class SQLiteHandler1 extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "meter";

    // Login table name
    private static final String TABLE_USER = "make_read";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ACC = "acc_no";
    private static final String KEY_METERNO = "meter_no";
    private static final String KEY_UID = "uid";
    private static final String KEY_READING= "reading";

    public SQLiteHandler1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MAKEREAD_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ACC + " INTEGER,"
                + KEY_METERNO + " INTEGER ," + KEY_UID + " TEXT,"
                + KEY_READING + " INTEGER," + ")";
        db.execSQL(CREATE_MAKEREAD_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(int acc, int meter_no, String uid, int reading) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ACC, acc); // Account Number
        values.put(KEY_METERNO, meter_no); // Meter Number
        values.put(KEY_UID, uid); // Meter Number
        values.put(KEY_READING, reading); // Reading

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New readings inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("acc_no", cursor.getString(1));
            user.put("meter_no", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("reading", cursor.getString(4));
        }

        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching readings from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user meter reading info from sqlite");
    }
}
