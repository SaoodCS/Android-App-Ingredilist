// Adapter for the shapping list recyclerview of the SQLite related database
package com.example.ingredilist;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {
    private Context Context;
    private Cursor Cursor;

///cursor and context to move through the database.
    public ShoppingListAdapter(Context context, Cursor cursor) {
        Context = context;
        Cursor = cursor;
    }

// getting the displays on the recyclerview to place the data into

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder{
        public TextView itemNameText;
        public TextView counter;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameText = itemView.findViewById(R.id.item_name_textview);
            counter = itemView.findViewById(R.id.item_quantity_textview);
        }
    }

    // inflating the related recyclerview
    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(Context);
        View view = inflator.inflate(R.layout.shopping_list_recyclerview, parent, false);
        return new ShoppingListViewHolder(view);
    }
//using the cursor to move through all items in the database and display it in recyclerview
    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        if(!Cursor.moveToPosition(position)){
            return;
        }
        String name = Cursor.getString(Cursor.getColumnIndex(ShoppingListContent.ShoppingListEntry.COLUMN_NAME));
        int amount = Cursor.getInt(Cursor.getColumnIndex(ShoppingListContent.ShoppingListEntry.COLUMN_QUANTITY));
        long id = Cursor.getLong(Cursor.getColumnIndex(ShoppingListContent.ShoppingListEntry._ID));
        holder.itemNameText.setText(name);
        holder.counter.setText(String.valueOf(amount));
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return Cursor.getCount();
    }
//updating the cursor and closing the cursor once its done
    public void updateCursor(Cursor latestCursor){
        if (Cursor != null){
            Cursor.close();
        }
        Cursor = latestCursor;

        if (latestCursor != null){
            notifyDataSetChanged();
        }
    }
}
