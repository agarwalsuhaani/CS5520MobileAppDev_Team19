package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.AmenityService;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.AmenitiesSelectorViewAdapter;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.DateUtils;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.utils.EditTextDatePickerDialog;

public class AddPropertyActivity extends AppCompatActivity {
    private EditText editTextStreetAddress;
    private EditText editTextBedCount;
    private EditText editTextBathCount;
    private CheckBox checkBoxIsStudio;
    private EditText editTextRent;
    private EditText editTextArea;
    private Calendar availableFrom;
    private Calendar availableTo;
    private AmenitiesSelectorViewAdapter amenitiesSelectorViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        editTextStreetAddress = findViewById(R.id.edit_text_street_address);
        editTextBedCount = findViewById(R.id.edit_text_bed_count);
        editTextBathCount = findViewById(R.id.edit_text_bath_count);
        checkBoxIsStudio = findViewById(R.id.check_box_is_studio);
        editTextRent = findViewById(R.id.edit_text_rent);
        editTextArea = findViewById(R.id.edit_text_area);

        EditText editTextAvailableFrom = findViewById(R.id.edit_text_available_from);
        availableFrom = Calendar.getInstance();
        editTextAvailableFrom.setText(DateUtils.toString(availableFrom));
        editTextAvailableFrom.setOnClickListener(getDateOnClickListener(this, availableFrom));

        EditText editTextAvailableTo = findViewById(R.id.edit_text_available_to);
        availableTo = Calendar.getInstance();
        editTextAvailableTo.setText(DateUtils.toString(availableTo));
        editTextAvailableTo.setOnClickListener(getDateOnClickListener(this, availableTo));

        RecyclerView amenitiesSelectorRecyclerView = findViewById(R.id.amenities_selector_recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        amenitiesSelectorRecyclerView.setLayoutManager(layoutManager);
        amenitiesSelectorViewAdapter = new AmenitiesSelectorViewAdapter(this);
        amenitiesSelectorRecyclerView.setAdapter(amenitiesSelectorViewAdapter);

        AmenityService.getInstance().getAll(amenitiesSelectorViewAdapter::setAmenities);

        setUpSubmitButton();
    }

    private void setUpSubmitButton() {
        Geocoder geocoder = new Geocoder(this);
        Button submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(view -> {
            try {
                List<Address> addressList = geocoder.getFromLocationName(editTextStreetAddress.getText().toString(), 1);
                if (!addressList.isEmpty()) {
                    Address address = addressList.get(0);
                    Property newProperty = new Property(
                            null,
                            Integer.parseInt(editTextBedCount.getText().toString()),
                            Integer.parseInt(editTextBathCount.getText().toString()),
                            checkBoxIsStudio.isChecked(),
                            address.getLatitude(),
                            address.getLongitude(),
                            address.getAddressLine(0),
                            address.getLocality(),
                            address.getAdminArea(),
                            Integer.parseInt(address.getPostalCode()),
                            Double.parseDouble(editTextRent.getText().toString()),
                            Double.parseDouble(editTextArea.getText().toString()),
                            DateUtils.toLocalDate(availableFrom),
                            DateUtils.toLocalDate(availableTo),
                            amenitiesSelectorViewAdapter.getSelectedAmenities());
                    PropertyService.getInstance().add(newProperty);
                } else {
                    showAlert("Please enter a valid address");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private View.OnClickListener getDateOnClickListener(Context context, Calendar calendar) {
        return view -> {
            EditTextDatePickerDialog dialog = new EditTextDatePickerDialog(context, (EditText) view, calendar);
            dialog.show();
        };
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.setMessage(message).setCancelable(true).create();
        alertDialog.show();
    }
}