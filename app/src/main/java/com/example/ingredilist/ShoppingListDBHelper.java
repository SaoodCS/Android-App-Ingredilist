package com.example.ingredilist;
// database helper for the Shopping list database containing the items the user has added to their shopping list.
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.ingredilist.ShoppingListContent.*;

public class ShoppingListDBHelper extends SQLiteOpenHelper {
    //defining the database and its parameters take place in this class
    public static final String DATABASE_NAME = "shoppinglist.db";
    public static final int DATABASE_VERSION = 1;

    public ShoppingListDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SHOPPINGLIST_DATABASE_TABLE = "CREATE TABLE " +
                ShoppingListEntry.TABLE_NAME + " (" +
                ShoppingListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ShoppingListEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ShoppingListEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                ShoppingListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SHOPPINGLIST_DATABASE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ShoppingListEntry.TABLE_NAME);
        onCreate(db);
    }
}
