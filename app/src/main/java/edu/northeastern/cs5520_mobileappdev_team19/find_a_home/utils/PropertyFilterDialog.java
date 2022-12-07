package edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.function.Consumer;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class PropertyFilterDialog extends AlertDialog {
    private static final String DATE_FORMAT = "MM/dd/yyyy";

    public PropertyFilterDialog(Context context, Consumer<FilterParams> callback) {
        super(context);
        setTitle("Filter properties");

        View propertyFilterView = LayoutInflater.from(getContext()).inflate(R.layout.property_filter, null);
        setView(propertyFilterView);

        Calendar availableFrom = Calendar.getInstance();
        Calendar availableTo = Calendar.getInstance();

        EditText filterAvailableFrom = propertyFilterView.findViewById(R.id.filter_available_from);
        filterAvailableFrom.setOnClickListener(getDateOnClickListener(availableFrom));

        EditText filterAvailableTo = propertyFilterView.findViewById(R.id.filter_available_to);
        filterAvailableTo.setOnClickListener(getDateOnClickListener(availableTo));

        setButton(AlertDialog.BUTTON_POSITIVE, "Filter", (dialog, which) -> {
            FilterParams params = new FilterParams();
            params.availableFromInMillis = availableFrom.getTimeInMillis();
            params.availableToInMillis = availableTo.getTimeInMillis();
            callback.accept(params);
        });

        setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> {});
    }

    private View.OnClickListener getDateOnClickListener(Calendar calendar) {
        return view -> {
            DatePickerDialog dialog = new DatePickerDialog(
                    getContext(),
                    new DateSetListener((EditText) view, calendar),
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        };
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
            SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_FORMAT, Locale.US);
            editText.setText(dateFormat.format(calendar.getTime()));
        }
    }

    public static class FilterParams {
        private long availableFromInMillis;
        private long availableToInMillis;
    }
}
