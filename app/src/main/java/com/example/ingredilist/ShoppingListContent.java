package com.example.ingredilist;
//class for the content within the shopping list database table that is accessed on the shopping list fragment.
import android.provider.BaseColumns;

public class ShoppingListContent {

    private ShoppingListContent(){}

    public static final class ShoppingListEntry implements BaseColumns{

        public static final String TABLE_NAME = "shoppingList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }
}
