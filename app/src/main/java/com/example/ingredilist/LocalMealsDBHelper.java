package com.example.ingredilist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class LocalMealsDBHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "LocalMealsDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME="my_local_meals";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_LOGGEDINUSER = "logged_in_user";
    private static final String COLUMN_MEALNAME = "meal_name";
    private static final String COLUMN_INGREDIENTS = "ingredients_list";





    LocalMealsDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_LOGGEDINUSER + " TEXT, " +
                        COLUMN_MEALNAME + " TEXT, " +
                        COLUMN_INGREDIENTS + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addNewLocalMeal(String loggedInUser, String mealName, String ingredients){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_LOGGEDINUSER, loggedInUser);
        cv.put(COLUMN_MEALNAME, mealName);
        cv.put(COLUMN_INGREDIENTS, ingredients);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result ==-1){
            Toast.makeText(context,"Error: Failed to add meal",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Successfully: added meal",Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readUsersSavedMealsData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
        }

        void updateMeals(String row_id, String mealName, String mealIngredients){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_MEALNAME,mealName);
            cv.put(COLUMN_INGREDIENTS,mealIngredients);

            long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
            if (result == -1){
                Toast.makeText(context,"Failed to Update",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Successfully Updated",Toast.LENGTH_SHORT).show();
            }

    }

    void deleteCurrentMeal(String row_id){
      SQLiteDatabase db = this.getWritableDatabase();
      long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
      if (result == -1){
          Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show();
      }
      else{
          Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show();
      }
    }


    }

