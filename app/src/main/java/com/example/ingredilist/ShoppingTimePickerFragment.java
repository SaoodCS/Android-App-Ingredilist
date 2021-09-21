package com.example.ingredilist;
// fragment which displays a calender (using TimePickerDialog import) allowing the user to choose a date and time to set their shopping list alarm service to.
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.format.DateFormat;
import java.util.Calendar;


public class ShoppingTimePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        //gets the time the user has selected
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener)getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}