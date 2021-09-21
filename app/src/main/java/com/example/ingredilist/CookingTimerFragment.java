package com.example.ingredilist;
// this fragment is for displaying the cooking timer as it runs as well as displaying instructions in a recyclerview that a user has appended into a SQLite database
//this is so that the user can view the cooking instructions and whats next whilst also cooking and starting the timer.
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CookingTimerFragment extends Fragment {
    Button goToTimerButton;
    Button buttonAddInstruction;
    private EditText instructionInput;
    private EditText duration;
    private SQLiteDatabase instructionsDatabase;
    private InstructionsAdapter instructionAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cookingtimer, container, false);
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);
        String name = sp.getString("Username","");
        //go to timer button to go to cooking timer activity and set and begin the timer.
        goToTimerButton = view.findViewById(R.id.GoToTimerButton);
        goToTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity().getApplicationContext(),CookingTimerActivity.class);
                startActivity(in);
            }
        });



        // abject of the InstructionsDBHelper class used to append user instructions into the SQLite instructions database.
        InstructionsDBHelper dbHelper = new InstructionsDBHelper (getActivity());
        instructionsDatabase = dbHelper.getWritableDatabase();

        //Recyclerview used to display the instructions data.
        RecyclerView recyclerView = view.findViewById(R.id.cookingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        instructionAdapter = new InstructionsAdapter(getActivity(),getAllItems());
        recyclerView.setAdapter(instructionAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            //onswiped method so that when the user swipes in any direction on the item displayed in the recyclerview it deletes that specific item.
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteItemFromList((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        instructionInput = view.findViewById(R.id.instructionInput);
        duration = view.findViewById(R.id.duration);
        buttonAddInstruction = view.findViewById(R.id.add_instruction_button);
        buttonAddInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();

            }
        });

        return view;

    }

    //additem method to add user input intructions to the SQLite database for instructions.
    private void addItem(){
        if (instructionInput.getText().toString().trim().length()==0 || duration.getText().toString().trim().length()==0){
            return;
        }
        String name = instructionInput.getText().toString();
        String d = duration.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(InstructionsListContent.InstructionsListEntry.COLUMN_NAME, name);
        cv.put(InstructionsListContent.InstructionsListEntry.COLUMN_DURATION, d);

        instructionsDatabase.insert(InstructionsListContent.InstructionsListEntry.TABLE_NAME, null, cv);
        instructionAdapter.updateCursor(getAllItems());

        instructionInput.getText().clear();

    }
//deletes items from the SQLite database and from the recyclerview
    private void deleteItemFromList(long id){
        instructionsDatabase.delete(InstructionsListContent.InstructionsListEntry.TABLE_NAME, InstructionsListContent.InstructionsListEntry._ID + "=" + id, null);
        instructionAdapter.updateCursor(getAllItems());
    }
//cursor moves through and displays all the items in the database.
    private Cursor getAllItems(){
        return instructionsDatabase.query(
                InstructionsListContent.InstructionsListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                InstructionsListContent.InstructionsListEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

}
