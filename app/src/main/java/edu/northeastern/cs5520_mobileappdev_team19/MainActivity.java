package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void atYourServiceClick(View v) {
        startActivity(new Intent(MainActivity.this, GameListActivity.class));
    }

    public void stickItToEmClick(View v) {
        startActivity(new Intent(MainActivity.this, UserListActivity.class));
    }

    public void groupProjectClick(View v) {

    }

    public void aboutClick(View v) {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }
}