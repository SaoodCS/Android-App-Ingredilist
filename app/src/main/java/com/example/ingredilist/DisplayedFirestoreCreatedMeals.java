package com.example.ingredilist;
// This screen displays all documents within the meals collection of firestore. These are all meals that registered users have created and uploaded.
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DisplayedFirestoreCreatedMeals extends AppCompatActivity {
    FirebaseFirestore ff;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView firestoreMealsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayed_firestore_created_meals);

        ff = FirebaseFirestore.getInstance();
        firestoreMealsList = findViewById(R.id.recyclerViewFeed);

        Query query = ff.collection("meals");
        //making use of the Firebase UI library defined in the Gradle file in order to display the firestore data in a recyclerview with ease.
        FirestoreRecyclerOptions<FirebaseFeedContentModel> options = new FirestoreRecyclerOptions.Builder<FirebaseFeedContentModel>().setQuery(query,FirebaseFeedContentModel.class).build();
        adapter = new FirestoreRecyclerAdapter<FirebaseFeedContentModel, DisplayedFirestoreCreatedMeals.MealsViewHolder>(options) {
            @NonNull
            @Override
            public DisplayedFirestoreCreatedMeals.MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_feed_recyclerview,parent,false);
                return new MealsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DisplayedFirestoreCreatedMeals.MealsViewHolder holder, int position, @NonNull FirebaseFeedContentModel model) {
                //displaying all the documents with the meal collection in the recyclerview (social_feed_recyclerview)
                holder.mealNameTV1.setText(model.getMealname());
                holder.usersName1.setText("Created By: " + model.getUsersname());
                holder.ingredientsTV1.setText("Ingredients: " + model.getIngredients());
                holder.dietTypeTV1.setText("Meal Type: " + model.getDiettype());
            }
        };

        firestoreMealsList.setHasFixedSize(true);
        firestoreMealsList.setLayoutManager(new LinearLayoutManager(this));
        firestoreMealsList.setAdapter(adapter);
    }

    private class MealsViewHolder extends RecyclerView.ViewHolder {
        private TextView mealNameTV1;
        private TextView usersName1;
        private TextView ingredientsTV1;
        private  TextView dietTypeTV1;

        public MealsViewHolder(@NonNull View itemView) {
            super(itemView);
//getting the textviews from the social feed recyclerview xml file
            mealNameTV1 = itemView.findViewById(R.id.mealNameTV);
            usersName1 = itemView.findViewById(R.id.usersName);
            ingredientsTV1 = itemView.findViewById(R.id.ingredientsTV);
            dietTypeTV1 = itemView.findViewById(R.id.dietTypeTV);


        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}