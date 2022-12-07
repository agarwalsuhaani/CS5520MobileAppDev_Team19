package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.MainActivity;
import edu.northeastern.cs5520_mobileappdev_team19.R;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Amenity;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Location;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.models.Property;
import edu.northeastern.cs5520_mobileappdev_team19.find_a_home.services.PropertyService;

public class AddProperty extends Activity {
    private Button submit;
    private EditText bedCount, bathCount, streetAddress, zipcode, rent, squareFeet, city;
    private Spinner spinner;
    private String selectedState;
    private CheckBox checkBoxYes, checkBoxNo;
    boolean isStudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        isStudio = false;

        submit = findViewById(R.id.submit);

        bedCount = findViewById(R.id.editText_bedCount);
        bathCount = findViewById(R.id.editText_bathCount);
        streetAddress = findViewById(R.id.editText_streetAddress);
        zipcode = findViewById(R.id.editText_zipcode);
        rent = findViewById(R.id.editText_rent);
        squareFeet = findViewById(R.id.editText_squareFeet);
        city = findViewById(R.id.editText_city);

        spinner = findViewById(R.id.spinner_state);

        checkBoxYes = findViewById(R.id.checkBox_isStudioYes);
        checkBoxNo = findViewById(R.id.checkBox_isStudioNo);

        List<String> states = new ArrayList<String>();
        states.add("Alabama");
        states.add("Alaska");
        states.add("Arizona");
        states.add("Arkansas");
        states.add("California");
        states.add("Colorado");
        states.add("Connecticut");
        states.add("Delaware");
        states.add("Florida");
        states.add("Georgia");
        states.add("Hawaii");
        states.add("Idaho");
        states.add("Illinois");
        states.add("Indiana");
        states.add("Iowa");
        states.add("Kansas");
        states.add("Kentucky");
        states.add("Louisiana");
        states.add("Maine");
        states.add("Maryland");
        states.add("Massachusetts");
        states.add("Michigan");
        states.add("Minnesota");
        states.add("Mississippi");
        states.add("Missouri");
        states.add("Montana");
        states.add("Nebraska");
        states.add("Nevada");
        states.add("New Hampshire");
        states.add("New Jersey");
        states.add("New Mexico");
        states.add("New York");
        states.add("North Carolina");
        states.add("North Dakota");
        states.add("Ohio");
        states.add("Oklahoma");
        states.add("Oregon");
        states.add("Pennsylvania");
        states.add("Rhode Island");
        states.add("South Carolina");
        states.add("South Dakota");
        states.add("Tennessee");
        states.add("Texas");
        states.add("Utah");
        states.add("Vermont");
        states.add("Virginia");
        states.add("Washington");
        states.add("West Virgina");
        states.add("Wisconsin");
        states.add("Wyoming");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, states);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);


        if(checkBoxYes.isChecked()) {
            isStudio = true;
        }
        if(checkBoxNo.isChecked()) {
            isStudio = false;
        }
    }

    public void submit(View view) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Amenity> amenities = new ArrayList<>();
                Date in = new Date();
                Property newProperty = new Property(null, Integer.parseInt(bedCount.getText().toString()),
                        Integer.parseInt(bathCount.getText().toString()),isStudio,47,122, streetAddress.getText().toString(),
                        city.getText().toString(), "Massachusetts", Integer.parseInt(zipcode.getText().toString()),
                        Double.parseDouble(rent.getText().toString()), Double.parseDouble(squareFeet.getText().toString()), in, in ,amenities );
                PropertyService.getInstance().add(newProperty);
            }
        });
    }


}