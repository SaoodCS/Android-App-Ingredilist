package com.example.ingredilist;
//fragment to display the local saved meals of the user from the database into the recycler view on this screen.
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyMealsFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton addbutton;
    Context thiscontext;
    LocalMealsDBHelper myDB;
    ArrayList<String> meal_id, meal_name, meal_ingredients;
    AdapterForRecyclerView adapterForRecyclerView;
    ImageView empty_imageview;
    TextView no_savedmeals;

    TextView usersNameTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mymeals, container, false);

//logged in users name placed on the top of the screen using shared preferences gained from the initial login screen.
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);
        String name = sp.getString("Username","");

        recyclerView = view.findViewById(R.id.recyclerView);
        addbutton = view.findViewById(R.id.addbutton);
        usersNameTextView = view.findViewById(R.id.usersNameTextView);
        usersNameTextView.setText(name + "'s Saved Meals");
        empty_imageview = view.findViewById(R.id.noDataImageView);
        no_savedmeals = view.findViewById(R.id.noSavedMealstextView);
// button for adding a meal to the database which takes the user to the add local meal activity where they can do so
        addbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), AddNewLocalMeal.class);
                in.putExtra("usersName",name);
                startActivity(in);
            }
        });
        thiscontext = container.getContext();


        //getting the database and storing the data in an arraylist to display in the recyclerview.
        myDB = new LocalMealsDBHelper(getActivity());
        meal_id = new ArrayList<>();
        meal_name = new ArrayList<>();
        meal_ingredients = new ArrayList<>();
        storeLocalMealsInArray();
        adapterForRecyclerView = new AdapterForRecyclerView(getActivity(),thiscontext,meal_id,meal_name,meal_ingredients);
        recyclerView.setAdapter(adapterForRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            getActivity().recreate();
        }
    }

    //method for getting and storing the locally saved user meals into an array
    void storeLocalMealsInArray(){
        Cursor cursor = myDB.readUsersSavedMealsData();
        if (cursor.getCount()==0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_savedmeals.setVisibility(View.VISIBLE);
        }
        else{
            while (cursor.moveToNext()){
                meal_id.add(cursor.getString(0));
                meal_name.add(cursor.getString(2));
                meal_ingredients.add(cursor.getString(3));
            }
            empty_imageview.setVisibility(View.GONE);
            no_savedmeals.setVisibility(View.GONE);
        }
    }
}
