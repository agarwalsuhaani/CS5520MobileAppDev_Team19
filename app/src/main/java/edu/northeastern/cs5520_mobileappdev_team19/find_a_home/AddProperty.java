package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Amenity;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Location;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;

public class AddProperty extends Activity {
    private Button submit;
    private List<Property> propertyList;
    private EditText bedCount, bathCount, streetAddress, zipcode, state, rent, squareFeet, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        propertyList = new ArrayList<Property>();

        bedCount = findViewById(R.id.editText_bedCount);
        bathCount = findViewById(R.id.editText_bathCount);
        streetAddress = findViewById(R.id.editText_streetAddress);
        zipcode = findViewById(R.id.editText_zipcode);
        rent = findViewById(R.id.editText_rent);
        squareFeet = findViewById(R.id.editText_squareFeet);

    }

    public void submit(View view) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Amenity> amenities = new ArrayList<>();
                Date in = new Date();
                Property newProperty = new Property("1", Integer.getInteger(bedCount.getText().toString()),
                        Integer.getInteger(bathCount.getText().toString()),false,25.23,45.23, streetAddress.getText().toString(),
                        city.getText().toString(), "Massachusetts", Integer.getInteger(zipcode.getText().toString()),
                        Double.parseDouble(rent.getText().toString()), Double.parseDouble(squareFeet.getText().toString()), in, in ,amenities );
                PropertyService.getInstance().add(newProperty);
            }
        });
    }


}