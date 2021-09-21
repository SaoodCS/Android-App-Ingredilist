package com.example.ingredilist;
// This class is the adapter for the local meals recyclerview which displays all the local meals a user has created and saved in their SQLite database.


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForRecyclerView extends RecyclerView.Adapter<AdapterForRecyclerView.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList meal_id, meal_name, meal_ingredients;
// constructor for recyclerview:
    AdapterForRecyclerView(Activity activity, Context context, ArrayList meal_id, ArrayList meal_name, ArrayList meal_ingredients){
        this.activity = activity;
        this.context = context;
        this.meal_id = meal_id;
        this.meal_name = meal_name;
        this.meal_ingredients = meal_ingredients;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
//setting the edittexts on the screen to the user's meal details so that it displays in the recyclerview:
        holder.mealIDTxt.setText(String.valueOf(meal_id.get(position)));
        holder.meal_name_txt.setText(String.valueOf(meal_name.get(position)));
        holder.meal_ingredients_txt.setText(String.valueOf(meal_ingredients.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateLocalMealsActivity.class);
                intent.putExtra("id",String.valueOf(meal_id.get(position)));
                intent.putExtra("mealName",String.valueOf(meal_name.get(position)));
                intent.putExtra("mealIngredients",String.valueOf(meal_ingredients.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meal_id.size();
    } // returns the number of meals a user has saved

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mealIDTxt, meal_name_txt, meal_ingredients_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealIDTxt = itemView.findViewById(R.id.mealIDTxt);
            meal_name_txt = itemView.findViewById(R.id.meal_name_txt);
            meal_ingredients_txt = itemView.findViewById(R.id.meal_ingredients_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
