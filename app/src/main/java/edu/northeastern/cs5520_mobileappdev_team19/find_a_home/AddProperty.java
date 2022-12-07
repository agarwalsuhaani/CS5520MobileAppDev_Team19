package edu.northeastern.cs5520_mobileappdev_team19.find_a_home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import edu.northeastern.cs5520_mobileappdev_team19.R;

public class AddProperty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
    }

//    private boolean CheckAllFields() {
//        if (etFirstName.length() == 0) {
//            etFirstName.setError("This field is required");
//            return false;
//        }
//
//        if (etLastName.length() == 0) {
//            etLastName.setError("This field is required");
//            return false;
//        }
//
//        if (etEmail.length() == 0) {
//            etEmail.setError("Email is required");
//            return false;
//        }
//
//        if (etPassword.length() == 0) {
//            etPassword.setError("Password is required");
//            return false;
//        } else if (etPassword.length() < 8) {
//            etPassword.setError("Password must be minimum 8 characters");
//            return false;
//        }
//
//        // after all validation return true.
//        return true;
//    }
}