package com.example.ingredilist;
// getters for firestore for the firestorerecyclerview in order to get and display the content from firestore to the socials feed
public class FirebaseFeedContentModel {

    private String usersname;
    private String mealname;
    private String ingredients;
    private String diettype;


    public FirebaseFeedContentModel() {}

    public FirebaseFeedContentModel(String username, String mealname, String ingredients, String diettype) {
        this.usersname = username;
        this.mealname = mealname;
        this.ingredients = ingredients;
        this.diettype = diettype;

    }



    public String getUsersname() {
        return usersname;
    }

    public void setUsersname(String usersname) {
        this.usersname = usersname;
    }

    public String getMealname() {
        return mealname;
    }

    public void setMealname(String mealname) {
        this.mealname = mealname;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDiettype() {
        return diettype;
    }

    public void setDiettype(String diettype) { this.diettype = diettype; }
}
