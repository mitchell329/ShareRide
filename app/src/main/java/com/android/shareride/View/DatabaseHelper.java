package com.android.shareride.View;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "shareride.db";

    public static final String TABLE1_NAME = "users_table";
    public static final String T1_COL1 = "UserID";
    public static final String T1_COL2 = "FullName";
    public static final String T1_COL3 = "Username";
    public static final String T1_COL4 = "EmailAddress";
    public static final String T1_COL5 = "Password";
    public static final String T1_COL6 = "UserCategory";
    public static final String T1_COL7 = "DateOfBirth";
    public static final String T1_COL8 = "Gender";
    public static final String T1_COL9 = "Description";
    public static final String T1_COL10 = "ProfilePic";

    public static final String TABLE2_NAME = "drivers_table";
    public static final String T2_COL1 = "DriverID";
    public static final String T2_COL2 = "UserID";
    public static final String T2_COL3 = "DriversLicense";
    public static final String T2_COL4 = "CarDetails";
    public static final String T2_COL5 = "TotalSeats";
    public static final String T2_COL6 = "TripFare";

    public static final String TABLE3_NAME = "trips_table";
    public static final String T3_COL1 = "TripID";
    public static final String T3_COL2 = "DriverID";
    public static final String T3_COL3 = "Origin";
    public static final String T3_COL4 = "Destination";
    public static final String T3_COL5 = "Departure";
    public static final String T3_COL6 = "Arrival";
    public static final String T3_COL7 = "IndividualFare";

    public static final String TABLE4_NAME = "bookings_table";
    public static final String T4_COL1 = "BookingID";
    public static final String T4_COL2 = "UserID";
    public static final String T4_COL3 = "TripID";
    public static final String T4_COL4 = "DriverID";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE1_NAME + " (" +
                T1_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                T1_COL2 + " TEXT NOT NULL, " + T1_COL3 + " TEXT NOT NULL, " +
                T1_COL4 + " TEXT NOT NULL, " + T1_COL5 + " TEXT NOT NULL, " +
                T1_COL6 + " TEXT NOT NULL, " + T1_COL7 + " TEXT, " + T1_COL8 +
                " TEXT, " + T1_COL9 + " TEXT, " + T1_COL10 + " BLOB)");

        db.execSQL("create table " + TABLE2_NAME + " (" +
                T2_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                T2_COL2 + " INTEGER NOT NULL, " + T2_COL3 + " TEXT, " +
                T2_COL4 + " TEXT, " + T2_COL5 + " INTEGER, " +
                T2_COL6 + " REAL, " +
                "FOREIGN KEY(" + T2_COL2 + ") REFERENCES " + TABLE1_NAME + "(" + T1_COL1 + "))");

        db.execSQL("create table " + TABLE3_NAME + " (" +
                T3_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                T3_COL2 + " INTEGER NOT NULL, " + T3_COL3 + " TEXT NOT NULL, " +
                T3_COL4 + " TEXT NOT NULL, " + T3_COL5 + " TEXT NOT NULL, " +
                T3_COL6 + " TEXT NOT NULL, " + T3_COL7 + " REAL NOT NULL, "  +
                "FOREIGN KEY(" + T3_COL2 + ") REFERENCES " + TABLE2_NAME + "(" + T2_COL1 + "))");

        db.execSQL("create table " + TABLE4_NAME + " (" +
                T4_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                T4_COL2 + " INTEGER NOT NULL, " + T4_COL3 + " INTEGER NOT NULL, " +
                T4_COL4 + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + T4_COL2 + ") REFERENCES " + TABLE1_NAME + "(" + T1_COL1 + ")," +
                "FOREIGN KEY(" + T4_COL3 + ") REFERENCES " + TABLE3_NAME + "(" + T3_COL1 + ")," +
                "FOREIGN KEY(" + T4_COL4 + ") REFERENCES " + TABLE2_NAME + "(" + T2_COL1 + "))");
    }

    public boolean insertUserRegistrationData(String fullName, String username,
                                              String emailAddress, String Password,
                                              String userCategory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T2_COL2, fullName);
        contentValues.put(T2_COL3, username);
        contentValues.put(T2_COL4, emailAddress);
        contentValues.put(T2_COL5, Password);
        contentValues.put(T2_COL6, userCategory);
        long result = db.insert(TABLE1_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public User validateUserLoginData(String usernameOrEmail, String pw) {
        User newUser = new User();
        SQLiteDatabase db = this.getReadableDatabase();

        String email = "";
        String username = "";
        String password = pw;
        String USER_SELECT_QUERY = "";

        if(usernameOrEmail.contains("@")) {
            email = usernameOrEmail;
            USER_SELECT_QUERY =
                    String.format("SELECT * FROM %s WHERE %s = %s",
                            TABLE1_NAME,
                            T1_COL4,
                            email);
        } else {
            username = usernameOrEmail;
            USER_SELECT_QUERY =
                    String.format("SELECT * FROM %s WHERE %s = %s",
                            TABLE1_NAME,
                            T1_COL3,
                            username);
        }

        Cursor cursor = db.rawQuery(USER_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                newUser.fullName = cursor.getString(cursor.getColumnIndex(T1_COL2));
                newUser.username = cursor.getString(cursor.getColumnIndex(T1_COL3));
                newUser.emailAddress = cursor.getString(cursor.getColumnIndex(T1_COL4));
                newUser.password = cursor.getString(cursor.getColumnIndex(T1_COL5));
                newUser.userCategory = cursor.getString(cursor.getColumnIndex(T1_COL6));
                newUser.dateOfBirth = cursor.getString(cursor.getColumnIndex(T1_COL7));
                newUser.gender = cursor.getString(cursor.getColumnIndex(T1_COL8));
                newUser.description = cursor.getString(cursor.getColumnIndex(T1_COL9));
                newUser.profilePic = cursor.getBlob(cursor.getColumnIndex(T1_COL10));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("Login Error", "This Username or Email Address does not exist");
        }
        return newUser;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE4_NAME);
        onCreate(db);
    }
}
