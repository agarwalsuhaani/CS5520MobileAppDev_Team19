package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class EditTextDatePickerDialog extends DatePickerDialog {

    public EditTextDatePickerDialog(@NonNull Context context, EditText editText, Calendar calendar) {
        super(context,
                new DateSetListener(editText, calendar),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private static class DateSetListener implements DatePickerDialog.OnDateSetListener {
        private final EditText editText;
        private final Calendar calendar;

        private DateSetListener(EditText editText, Calendar calendar) {
            this.editText = editText;
            this.calendar = calendar;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,day);
            editText.setText(DateUtils.toString(calendar));
        }
    }
}