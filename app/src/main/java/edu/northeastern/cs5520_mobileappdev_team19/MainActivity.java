package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_AtYourService, btn_firebase, btn_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void atYourService(View v) {
        startActivity(new Intent(MainActivity.this, GameListActivity.class));
    }

    public void firebase(View v) {
        btn_firebase = findViewById(R.id.btn_Firebase);
    }

    public void groupProject(View v) {
        btn_group = findViewById(R.id.btn_group);
    }
}