package com.android.shareride.View;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "shareride.db";

    public static final String TABLE1_NAME = "users_table";
    public static final String T1_COL1 = "UserID";
    public static final String T1_COL2 = "FullName";
    public static final String T1_COL3 = "Username";
    public static final String T1_COL4 = "DriversLicense";
    public static final String T1_COL5 = "Password";
    public static final String T1_COL6 = "ContactNum";
    public static final String T1_COL7 = "DateOfBirth";
    public static final String T1_COL8 = "Gender";
    public static final String T1_COL9 = "Description";
    public static final String T1_COL10 = "ProfilePic";

    public static final String TABLE3_NAME = "trips_table";
    public static final String T3_COL1 = "TripID";
    public static final String T3_COL2 = "UserID";
    public static final String T3_COL3 = "Origin";
    public static final String T3_COL4 = "Destination";
    public static final String T3_COL5 = "Date";
    public static final String T3_COL6 = "Time";
    public static final String T3_COL7 = "IndividualFare";

    public static final String TABLE4_NAME = "bookings_table";
    public static final String T4_COL1 = "BookingID";
    public static final String T4_COL2 = "UserID";
    public static final String T4_COL3 = "TripID";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 5);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE1_NAME + " (" +
                T1_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                T1_COL2 + " TEXT NOT NULL, " + T1_COL3 + " TEXT NOT NULL, " +
                T1_COL4 + " TEXT NOT NULL, " + T1_COL5 + " TEXT NOT NULL, " +
                T1_COL6 + " TEXT NOT NULL, " + T1_COL7 + " TEXT, " + T1_COL8 +
                " TEXT, " + T1_COL9 + " TEXT, " + T1_COL10 + " BLOB)");

        String sqlCreateTripsTable = "create table " + TABLE3_NAME + " (" +
                T3_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                T3_COL2 + " INTEGER NOT NULL, " + T3_COL3 + " TEXT NOT NULL, " +
                T3_COL4 + " TEXT NOT NULL, " + T3_COL5 + " TEXT NOT NULL, " +
                T3_COL6 + " TEXT NOT NULL, " + T3_COL7 + " REAL NOT NULL, " +
                "FOREIGN KEY(" + T3_COL2 + ") REFERENCES " + TABLE1_NAME + "(" + T1_COL1 + "))";

        db.execSQL(sqlCreateTripsTable);

        /*db.execSQL("create table " + TABLE3_NAME + " (" +
                T3_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                T3_COL2 + " INTEGER NOT NULL, " + T3_COL3 + " TEXT NOT NULL, " +
                T3_COL4 + " TEXT NOT NULL, " + T3_COL5 + " TEXT NOT NULL, " +
                T3_COL6 + " TEXT NOT NULL, " + T3_COL7 + " REAL NOT NULL, " +
                "FOREIGN KEY(" + T3_COL2 + ") REFERENCES " + TABLE1_NAME + "(" + T1_COL1 + "))");*/

        db.execSQL("create table " + TABLE4_NAME + " (" +
                T4_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                T4_COL2 + " INTEGER NOT NULL, " + T4_COL3 + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + T4_COL2 + ") REFERENCES " + TABLE1_NAME + "(" + T1_COL1 + ")," +
                "FOREIGN KEY(" + T4_COL3 + ") REFERENCES " + TABLE3_NAME + "(" + T3_COL1 + "))");
    }

    public boolean insertUserRegistrationData(String fullName, String username,
                                              String driversLicense, String Password,
                                              String contactNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1_COL2, fullName);
        contentValues.put(T1_COL3, username);
        contentValues.put(T1_COL4, driversLicense);
        contentValues.put(T1_COL5, Password);
        contentValues.put(T1_COL6, contactNum);
        long result = db.insert(TABLE1_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public User validateUserLoginData(String userNam, String pw) {
        User newUser = new User();
        SQLiteDatabase db = this.getReadableDatabase();

        String password = pw;
        String username = userNam;
        /*String USER_SELECT_QUERY =
            String.format("SELECT * FROM %s WHERE %s = %s",
                    TABLE1_NAME,
                    T1_COL3,
                    username);*/

        String USER_SELECT_QUERY = "SELECT * FROM " + TABLE1_NAME + " WHERE " + T1_COL3 + " = '" + username + "'";


        Cursor cursor = db.rawQuery(USER_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                newUser.fullName = cursor.getString(cursor.getColumnIndex(T1_COL2));
                newUser.username = cursor.getString(cursor.getColumnIndex(T1_COL3));
                newUser.driversLicense = cursor.getString(cursor.getColumnIndex(T1_COL4));
                newUser.password = cursor.getString(cursor.getColumnIndex(T1_COL5));
                newUser.contactNum = cursor.getString(cursor.getColumnIndex(T1_COL6));
                newUser.dateOfBirth = cursor.getString(cursor.getColumnIndex(T1_COL7));
                newUser.gender = cursor.getString(cursor.getColumnIndex(T1_COL8));
                newUser.description = cursor.getString(cursor.getColumnIndex(T1_COL9));
                newUser.profilePic = cursor.getBlob(cursor.getColumnIndex(T1_COL10));
            }
            else {
                return null;
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("Login Error", "This Username does not exist");
        }

        if (newUser != null)
        {
            if((newUser.username.equals(username)) && (newUser.password.equals(password)))
            {
                return newUser;
            }
            else
            {
                return null;
            }
        }
        else {
            return null;
        }
    }

    public int getUserID(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String USER_SELECT_QUERY = "SELECT * FROM " + TABLE1_NAME + " WHERE " + T1_COL3 + " = '" + username + "'";
        Cursor cursor = db.rawQuery(USER_SELECT_QUERY, null);
        if (cursor.moveToFirst())
        {
            return cursor.getInt(cursor.getColumnIndex(T1_COL1));
        }
        else return -1;
    }

    public User getUserName(int userID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String querySQL = "SELECT * FROM " + TABLE1_NAME + " WHERE " + T1_COL1 + " = '" + userID + "'";
        Cursor cursor = db.rawQuery(querySQL, null);
        if (cursor.moveToFirst())
        {
            User user = new User();
            user.fullName = cursor.getString(cursor.getColumnIndex(T1_COL2));
            user.username = cursor.getString(cursor.getColumnIndex(T1_COL3));
            user.driversLicense = cursor.getString(cursor.getColumnIndex(T1_COL4));
            user.contactNum = cursor.getString(cursor.getColumnIndex(T1_COL6));

            return user;
        }
        else return null;
    }

    public long addNewTrip(int userID, String from, String to, String date, String time, float individualFare)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T3_COL2, userID);
        contentValues.put(T3_COL3, from);
        contentValues.put(T3_COL4, to);
        contentValues.put(T3_COL5, date);
        contentValues.put(T3_COL6, time);
        contentValues.put(T3_COL7, individualFare);
        long result = db.insert(TABLE3_NAME, null, contentValues);
        return result;
    }

    public Trip getTrip(int tripID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String querySQL = "SELECT * FROM " + TABLE3_NAME + " WHERE " + T3_COL1 + " = '" + tripID + "'";
        Cursor cursor = db.rawQuery(querySQL, null);
        if (cursor.moveToFirst())
        {
            Trip trip = new Trip();
            trip.from = cursor.getString(cursor.getColumnIndex(T3_COL3));
            trip.to = cursor.getString(cursor.getColumnIndex(T3_COL4));
            trip.date = cursor.getString(cursor.getColumnIndex(T3_COL5));
            trip.time = cursor.getString(cursor.getColumnIndex(T3_COL6));
            trip.individualFare = cursor.getFloat(cursor.getColumnIndex(T3_COL7));;
            trip.userID = cursor.getInt(cursor.getColumnIndex(T3_COL2));
            trip.tripID = tripID;

            return trip;
        }
        else return null;
    }

    public List<Trip> getTrips()
    {
        List<Trip> tripList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String querySQL = "SELECT * FROM " + TABLE3_NAME;
        Cursor cursor = db.rawQuery(querySQL, null);
        while (cursor.moveToNext())
        {
            String from = cursor.getString(cursor.getColumnIndex(T3_COL3));
            String to = cursor.getString(cursor.getColumnIndex(T3_COL4));
            String date = cursor.getString(cursor.getColumnIndex(T3_COL5));
            String time = cursor.getString(cursor.getColumnIndex(T3_COL6));
            float cost = cursor.getFloat(cursor.getColumnIndex(T3_COL7));
            int userID = cursor.getInt(cursor.getColumnIndex(T3_COL2));
            int tripID = cursor.getInt(cursor.getColumnIndex(T3_COL1));

            Trip trip = new Trip(date, from, cost, time, to, userID);
            trip.tripID = tripID;
            tripList.add(trip);
        }
        cursor.close();
        return tripList;
    }

    public List<Trip> getTrips(int userID)
    {
        List<Trip> tripList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String querySQL = "SELECT * FROM " + TABLE3_NAME + " WHERE " + T3_COL2 + " = '" + userID + "'";
        Cursor cursor = db.rawQuery(querySQL, null);
        while (cursor.moveToNext())
        {
            String from = cursor.getString(cursor.getColumnIndex(T3_COL3));
            String to = cursor.getString(cursor.getColumnIndex(T3_COL4));
            String date = cursor.getString(cursor.getColumnIndex(T3_COL5));
            String time = cursor.getString(cursor.getColumnIndex(T3_COL6));
            float cost = cursor.getFloat(cursor.getColumnIndex(T3_COL7));
            int tripID = cursor.getInt(cursor.getColumnIndex(T3_COL1));

            Trip trip = new Trip(date, from, cost, time, to, userID);
            trip.tripID = tripID;
            tripList.add(trip);
        }
        cursor.close();
        return tripList;
    }

    public long newBooking(int userID, int tripID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T4_COL2, userID);
        contentValues.put(T4_COL3, tripID);
        long result = db.insert(TABLE4_NAME, null, contentValues);
        return result;
    }

    public List<Trip> getBookings(int userID)
    {
        List<Trip> bookingList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String querySQL = "SELECT * FROM " + TABLE4_NAME + " WHERE " + T4_COL2 + " = '" + userID + "'";
        Cursor cursor = db.rawQuery(querySQL, null);
        while (cursor.moveToNext())
        {
            int tripID = cursor.getInt(cursor.getColumnIndex(T4_COL3));

            Trip trip = getTrip(tripID);
            trip.tripID = tripID;
            bookingList.add(trip);
        }
        cursor.close();
        return bookingList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE4_NAME);
        onCreate(db);
    }
}
