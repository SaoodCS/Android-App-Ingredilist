package com.example.ingredilist;
//DB Helper for the initial login screen of the application.
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
//takes the users inputted login details and compares it to what's in the SQLite database to allow access to the application.
public class DBHelper extends SQLiteOpenHelper {

    public static final String dbname = "LocalDatabase.db";
    public DBHelper(@Nullable Context context) {
        super(context,"LocalDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        mydb.execSQL("create Table userLogin(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase mydb, int oldVersion, int newVersion) {
        mydb.execSQL("drop Table if exists userLogin");
    }
    public Boolean insertData (String username, String password){
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result = mydb.insert("userLogin",null,contentValues);
        if(result == 1) return false;
        else return true;
    }
//method to check if the parameters (when called) are the same as the username and password within the SQLite database.
    public boolean checkUsernameAndPassword(String username, String password){
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("select * from userLogin where username = ? and password = ?", new String[] {username,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;

    }
}
