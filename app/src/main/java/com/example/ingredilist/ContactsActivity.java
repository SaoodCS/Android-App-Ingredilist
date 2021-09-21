package com.example.ingredilist;
//Activity for storing the users supermarket contacts in an SQLite database, developed within a content provider so that other applications can get and set from this database and view the items too.

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContactsActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    Button callContactButton;
    Button addContactButton;
    Button getContactsButton;
    Button deleteButton;
    Button nextButton;
    Button previousButton;
    EditText personName;
    EditText phoneNumber;
    TextView id;
    TextView nameTextView;
    TextView numberTextView;
    android.database.Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        personName = findViewById(R.id.PersonName);
        phoneNumber = findViewById(R.id.phoneNumber);
        id = findViewById(R.id.id);
        nameTextView = findViewById(R.id.nameTextView);
        numberTextView = findViewById(R.id.numberTextView);

        //call contact button runs the call contact method which opens the phone if permission granted.
        callContactButton = findViewById(R.id.callContactButton);
        callContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callContact();
            }
        });



// add contact button adds a contact to the contacts content provider database
        addContactButton = findViewById(R.id.addContactButton);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((personName.getText().toString().trim()).isEmpty()){
                    personName.setError("Please Input a Valid Contact Name");
                    return;
                }

                if((phoneNumber.getText().toString().trim()).isEmpty()){
                    phoneNumber.setError("Please Input a Valid Phone Number");
                    return;
                }



                ContentValues cv = new ContentValues();
                cv.put(MyContactsContentProvider.COLUMN_NAME, personName.getText().toString().trim());
                cv.put(MyContactsContentProvider.COLUMN_NUMBER, phoneNumber.getText().toString().trim());
                getContentResolver().insert(MyContactsContentProvider.CONTENT_URI,cv);
                clear();
            }
        });



//get contacts button displays the contacts on the screen by running a cursor through the database. Does so through the provided content provider by using a content resolver.
        getContactsButton = findViewById(R.id.getContactsButton);
        getContactsButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor = getContentResolver().query(MyContactsContentProvider.CONTENT_URI, null, null, null, null);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        updateTextViews();
                    }
                }
            }});



// delete button to delete any items within the content provider database that is currently being displayed on the screen.
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = MyContactsContentProvider.COLUMN_NAME + " = ? AND " + MyContactsContentProvider.COLUMN_NUMBER + " = ? ";
                String[] selectionArgs = {nameTextView.getText().toString().trim(), numberTextView.getText().toString().trim()};
                int rowsDeleted = getContentResolver().delete(MyContactsContentProvider.CONTENT_URI,selection,selectionArgs);
                clear();
            }
        });



// next button displays the next contact the cursor moves to in the database (note that when on the Last item in database and this button is pressed the activity crashes)
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cursor != null) {
                    cursor.moveToNext();
                }
                    updateTextViews();
            }
        });


// previous button displays the previous contact the cursor moves to in the database (note that when on the First item in database and this button is pressed the activity crashes)
        previousButton=findViewById(R.id.previousButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cursor != null) {
                    cursor.moveToPrevious();
                }
                    updateTextViews();
                }
        });


    }

//callContact function for the call button to open up the phone when permission is granted and allow the user to make a phonecall to the supermarket.
    public void callContact(){
        String contactNumber = numberTextView.getText().toString().trim();
        if (contactNumber.length()>0){
            //request permission for access to the phone. If user accepts then the number is typed into the phone and called automatically
            if(ContextCompat.checkSelfPermission(ContactsActivity.this,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ContactsActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
//the number is typed into the phone and called automatically
            }else{
                String dial = "tel:" + contactNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }
        else{
            //alert dialogue pops up if no contact is displayed on the screen with instructions on how to get a number displayed.
            android.app.AlertDialog.Builder b = new AlertDialog.Builder(ContactsActivity.this);
            b.setTitle("No Number:");
            b.setMessage("Please add contacts above if you haven't already, and then press GetContacts and find the supermarket you would like to call for an enquiry");
            b.setIcon(R.drawable.settingsicon);
            b.setCancelable(true);
            b.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = b.create();
            alertDialog.show();

        }
    }
//if permission is already granted then access phone and make the call
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                callContact();
            }
        }
    }
// updating the textview of the current contact displayed.
    public void updateTextViews(){
        id.setText(cursor.getString(0));
        nameTextView.setText(cursor.getString(1)+" ");
        numberTextView.setText(cursor.getString(2));
    }

    private void clear(){
        personName.setText("");
        phoneNumber.setText("");
        id.setText("");
        nameTextView.setText("");
        numberTextView.setText("");
        cursor=null;
    }

}