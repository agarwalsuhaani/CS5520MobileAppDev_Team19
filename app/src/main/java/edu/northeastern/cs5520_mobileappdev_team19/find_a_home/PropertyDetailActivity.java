package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;

public class PropertyDetailActivity extends AppCompatActivity {
    public static final String PROPERTY_ID = "PROPERTY_ID";
    private TextView streetAddressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);

        streetAddressTextView = this.findViewById(R.id.property_detail_street_address);

        String propertyId = getIntent().getStringExtra(PROPERTY_ID);
        if (propertyId != null) {
            PropertyService.getInstance().get(propertyId, property -> {
                streetAddressTextView.setText(property.getStreetAddress());
            });
        }
    }
}