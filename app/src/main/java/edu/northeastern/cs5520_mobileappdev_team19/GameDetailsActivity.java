package edu.northeastern.cs5520_mobileappdev_team19;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URI;

import edu.northeastern.cs5520_mobileappdev_team19.databinding.ActivityGameDetailsBinding;
import edu.northeastern.cs5520_mobileappdev_team19.models.GameDetailedInfo;

public class GameDetailsActivity extends AppCompatActivity {

    private ActivityGameDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;

        GameDetailedInfo gameDetailedInfo = new GameDetailedInfo();
        gameDetailedInfo.setTitle("Test Game");
        gameDetailedInfo.setThumbnail("https://www.freetogame.com/g/452/thumbnail.jpg");

        toolBarLayout.setTitle(gameDetailedInfo.getTitle());
        populateData(gameDetailedInfo);
    }

    public void populateData(GameDetailedInfo gameDetailedInfo) {

        ImageView header = findViewById(R.id.header);
        header.setImageURI(Uri.parse(gameDetailedInfo.getThumbnail()));
        Picasso.get().load(gameDetailedInfo.getThumbnail()).into(header);
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Start repeat for each field
        View v = vi.inflate(R.layout.game_details_field, null);

        // fill in any details dynamically here
        TextView textView = (TextView) v.findViewById(R.id.fieldName);
        textView.setText("Game Title");

        TextView textViewValue = (TextView) v.findViewById(R.id.fieldValue);
        textViewValue.setText(gameDetailedInfo.getTitle());

        // insert into main view
        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.fieldContainer);
        insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        //End repeat for each field

    }
}