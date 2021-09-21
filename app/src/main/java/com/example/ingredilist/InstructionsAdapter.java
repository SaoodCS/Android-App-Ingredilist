package com.example.ingredilist;
//Adapter for the recyclerview displaying the cooking instructions on the cooking timer screen from the instructions SQLite database.
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionsViewHolder> {

    private android.content.Context Context;
    private android.database.Cursor Cursor;

    public InstructionsAdapter(Context context, Cursor cursor) {
        Context = context;
        Cursor = cursor;
    }

    public class InstructionsViewHolder extends RecyclerView.ViewHolder{
        public TextView itemNameText;
        public TextView counter;

        public InstructionsViewHolder(@NonNull View itemView) {
            super(itemView);
            //getting the items from the recyclerview layout resource file
            itemNameText = itemView.findViewById(R.id.instruction_textview);
            counter = itemView.findViewById(R.id.duration_textview);
        }
    }

    @NonNull
    @Override
    //inlfating the recyclerview
    public InstructionsAdapter.InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(Context);
        View view = inflator.inflate(R.layout.instruction_list_recyclerview, parent, false);
        return new InstructionsAdapter.InstructionsViewHolder(view);
    }

    @Override
    //moving the cursor through the instructions database to get and display the instructions in the recyclerview
    public void onBindViewHolder(@NonNull InstructionsAdapter.InstructionsViewHolder holder, int position) {
        if(!Cursor.moveToPosition(position)){
            return;
        }
        String name = Cursor.getString(Cursor.getColumnIndex(InstructionsListContent.InstructionsListEntry.COLUMN_NAME));
        int amount = Cursor.getInt(Cursor.getColumnIndex(InstructionsListContent.InstructionsListEntry.COLUMN_DURATION));
        long id = Cursor.getLong(Cursor.getColumnIndex(InstructionsListContent.InstructionsListEntry._ID));
        holder.itemNameText.setText(name);
        holder.counter.setText(String.valueOf(amount));
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return Cursor.getCount();
    }
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
