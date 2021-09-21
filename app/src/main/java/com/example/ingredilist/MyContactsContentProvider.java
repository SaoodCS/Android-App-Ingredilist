package com.example.ingredilist;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MyContactsContentProvider extends ContentProvider {


    public final static String DBNAME = "ContactsDatabase";

    protected static final class DBHelper extends SQLiteOpenHelper {
        DBHelper(Context context) {
            super(context, DBNAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_CONTACTSDB); }
        @Override
        public void onUpgrade(SQLiteDatabase argo, int arg1, int arg2) {
        }
    };

    public final static String TABLE_CONTACTSTABLE = "Contacts";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_NUMBER = "number";

    public static final String AUTHORITY = "com.ingredilistContacts.provider";
    public static final Uri CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY +"/" + TABLE_CONTACTSTABLE);

    private DBHelper Helper;

    private static final String SQL_CREATE_CONTACTSDB = "CREATE TABLE " +
            TABLE_CONTACTSTABLE +
            "(" +
            " _ID INTEGER PRIMARY KEY, " +
            COLUMN_NAME +
            " TEXT," +
            COLUMN_NUMBER +
            " TEXT)";



    public MyContactsContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return Helper.getWritableDatabase().delete(TABLE_CONTACTSTABLE,selection,selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String name = values.getAsString(COLUMN_NAME).trim();
        String number = values.getAsString(COLUMN_NUMBER).trim();
        if (name.equals("")){
            return null;
        }
        if (number.equals("")){
            return null;
        }


        // TODO: Implement this to handle requests to insert a new row.
        long id = Helper.getWritableDatabase().insert(TABLE_CONTACTSTABLE,null,values);
        return Uri.withAppendedPath(CONTENT_URI,"" + id);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        Helper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        return Helper.getWritableDatabase().query(TABLE_CONTACTSTABLE,projection,selection,selectionArgs,null,null,sortOrder);

    }






    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        return Helper.getReadableDatabase().update(TABLE_CONTACTSTABLE,values,selection,selectionArgs);
    }
}