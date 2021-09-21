package com.example.ingredilist;
//fragment for shopping list displaying the shopping list in a recyclerview and allowing the user to swipe to delete items off the list
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingListFragment extends Fragment {
    private EditText editTextItemName;
    private TextView textViewQuantity;
    private int quantity = 0;
    private SQLiteDatabase listDatabase;
    private ShoppingListAdapter shoppingAdapter;
    Button toShoppingAlarmBtn;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppinglist, container, false);

        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);
        String name = sp.getString("Username","");

        // object of the DBHelper to access read and write from the shopping list database
        ShoppingListDBHelper dbHelper = new ShoppingListDBHelper(getActivity());
        listDatabase = dbHelper.getWritableDatabase();
//recyclerview for displaying the data from the database onto the screen
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        shoppingAdapter = new ShoppingListAdapter(getActivity(),getAllItems());
        recyclerView.setAdapter(shoppingAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
//swipe delete an item in any direction: (touch gestures as such also used for the instructions list on the cooking timer screen)
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteItemFromList((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        editTextItemName = view.findViewById(R.id.itemName);
        textViewQuantity = view.findViewById(R.id.quantity);
// takes the user to the shopping alarm activity where they can set an alarm service
        toShoppingAlarmBtn = view.findViewById(R.id.toShoppingAlarmBtn);
        toShoppingAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), ShoppingAlarmSettings.class);
                startActivity(in);
            }
        });
//increase or decrease the quantity of an item in your shopping list.
        Button buttonIncrease = view.findViewById(R.id.increase_quantity_button);
        Button buttonDecrease = view.findViewById(R.id.decrease_quantity_button);
        Button buttonAddItem = view.findViewById(R.id.add_item_button);

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseItem();

            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseItem();

            }
        });

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();

            }
        });

        return view;



    }

    //methods of increasing and decreasing the quantity of an item you want to add to your shopping list
    private void increaseItem(){
        quantity++;
        textViewQuantity.setText(String.valueOf(quantity + "x"));
    }

    private void decreaseItem(){
        if (quantity>0){
            quantity--;
            textViewQuantity.setText(String.valueOf(quantity +"x"));
        }
    }
//method to add the item to the shopping list database once the add button is pressed (method placed inside add button)
    private void addItem(){
        if (editTextItemName.getText().toString().trim().length()==0 || quantity == 0){
            return;
        }
        String name = editTextItemName.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(ShoppingListContent.ShoppingListEntry.COLUMN_NAME, name);
        cv.put(ShoppingListContent.ShoppingListEntry.COLUMN_QUANTITY, quantity);

        listDatabase.insert(ShoppingListContent.ShoppingListEntry.TABLE_NAME, null, cv);
        shoppingAdapter.updateCursor(getAllItems());

        editTextItemName.getText().clear();

    }
    //method to delete the item to the shopping list database once the add button is pressed (method placed inside delete button)
    private void deleteItemFromList(long id){
        listDatabase.delete(ShoppingListContent.ShoppingListEntry.TABLE_NAME, ShoppingListContent.ShoppingListEntry._ID + "=" + id, null);
        shoppingAdapter.updateCursor(getAllItems());
    }
    //method to get all items from the shopping list database so that the method can then be called to display it in the recyclerview
    private Cursor getAllItems(){
        return listDatabase.query(
                ShoppingListContent.ShoppingListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ShoppingListContent.ShoppingListEntry.COLUMN_TIMESTAMP + " DESC"

        );
    }

}
