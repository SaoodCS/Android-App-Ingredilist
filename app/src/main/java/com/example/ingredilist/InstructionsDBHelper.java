// database helper for the instructions database containing the cooking instructions
package com.example.ingredilist;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


public class InstructionsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "instructionslist.db";
    public static final int DATABASE_VERSION = 1;

    public InstructionsDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
//creating the database table containing all the different variables of the cooking instructions database
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String INSTRUCTIONSLIST_DATABASE_TABLE = "CREATE TABLE " +
                InstructionsListContent.InstructionsListEntry.TABLE_NAME + " (" +
                InstructionsListContent.InstructionsListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InstructionsListContent.InstructionsListEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                InstructionsListContent.InstructionsListEntry.COLUMN_DURATION + " INTEGER NOT NULL, " +
                InstructionsListContent.InstructionsListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(INSTRUCTIONSLIST_DATABASE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsListContent.InstructionsListEntry.TABLE_NAME);
        onCreate(db);
    }
}
