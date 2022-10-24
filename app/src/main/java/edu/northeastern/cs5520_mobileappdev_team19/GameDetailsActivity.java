package edu.northeastern.cs5520_mobileappdev_team19;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import edu.northeastern.cs5520_mobileappdev_team19.databinding.ActivityGameDetailsBinding;
import edu.northeastern.cs5520_mobileappdev_team19.models.GameDetailedInfo;
import edu.northeastern.cs5520_mobileappdev_team19.models.GameInfo;
import edu.northeastern.cs5520_mobileappdev_team19.models.MinimumSystemRequirements;
import edu.northeastern.cs5520_mobileappdev_team19.services.GamesService;
import edu.northeastern.cs5520_mobileappdev_team19.services.IGameService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameDetailsActivity extends AppCompatActivity {

    public final static String GAME_ID = "GAME_ID";

    private ActivityGameDetailsBinding binding;
    private CollapsingToolbarLayout toolBarLayout;
    private IGameService gameService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;

        gameService = GamesService.getInstance().api();

        int gameId = getIntent().getIntExtra(GAME_ID, -1);

        if (gameId != -1) {
            populateData(gameId);
        }
    }

    public void populateData(int gameId) {
        Call<GameDetailedInfo> fetchGameDetails = gameService.getById(gameId);
        fetchGameDetails.enqueue(new Callback<GameDetailedInfo>() {
            @Override
            public void onResponse(Call<GameDetailedInfo> call, Response<GameDetailedInfo> response) {
                GameDetailedInfo gameDetailsResponse = response.body();
                if (gameDetailsResponse == null) return;

                populateData(gameDetailsResponse);
            }

            @Override
            public void onFailure(Call<GameDetailedInfo> call, Throwable t) {
                // TODO: Better logging maybe
                System.out.println(t.getMessage());
            }
        });
    }

    private Map<String, String> simpleFieldData(GameDetailedInfo gameDetailedInfo) {
        Map<String, String> fieldData = new HashMap<>();

        fieldData.put("Title", gameDetailedInfo.getTitle());
        fieldData.put("Description", gameDetailedInfo.getDescription());
        fieldData.put("Short Description", gameDetailedInfo.getShortDescription());
        fieldData.put("Developer", gameDetailedInfo.getDeveloper());
        fieldData.put("Game URL", gameDetailedInfo.getGameUrl());
        fieldData.put("Genre", gameDetailedInfo.getGenre());
        fieldData.put("Platform", gameDetailedInfo.getPlatform());
        fieldData.put("Publisher", gameDetailedInfo.getPublisher());
        fieldData.put("Release Date", gameDetailedInfo.getReleaseDate());
        fieldData.put("Status", gameDetailedInfo.getStatus());

        return fieldData;
    }

    private Map<String, String> getSystemRequirements(GameDetailedInfo gameDetailedInfo) {
        Map<String, String> fieldData = new HashMap<>();

        MinimumSystemRequirements sysReqs = gameDetailedInfo.getMinimumSystemRequirements();

        if (sysReqs == null) {
            return fieldData;
        }
        fieldData.put("Graphics", sysReqs.getGraphics());
        fieldData.put("Memory", sysReqs.getMemory());
        fieldData.put("OS", sysReqs.getOs());
        fieldData.put("Processor", sysReqs.getProcessor());
        fieldData.put("Storage", sysReqs.getStorage());

        return fieldData;
    }

    public void populateData(GameDetailedInfo gameDetailedInfo) {
        toolBarLayout.setTitle(gameDetailedInfo.getTitle());

        ImageView header = findViewById(R.id.header);
        header.setImageURI(Uri.parse(gameDetailedInfo.getThumbnail()));
        Picasso.get().load(gameDetailedInfo.getThumbnail()).into(header);
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Map<String, String> fieldData = simpleFieldData(gameDetailedInfo);

        // insert into main view
        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.fieldContainer);
        int posCounter = 1;
        for (Map.Entry<String, String> entry : fieldData.entrySet()) {
            // Start repeat for each field
            View v = vi.inflate(R.layout.game_details_field, null);

            // fill in any details dynamically here
            TextView textView = v.findViewById(R.id.fieldName);
            textView.setText(entry.getKey());

            TextView textViewValue = (TextView) v.findViewById(R.id.fieldValue);
            textViewValue.setText(entry.getValue());


            insertPoint.addView(v, posCounter++, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

            //End repeat for each field
        }
        Map<String, String> sysFieldData = getSystemRequirements(gameDetailedInfo);
        // insert into main view
        ViewGroup sysInsertPoint = (ViewGroup) findViewById(R.id.sysRequirementsContainer);
        for (Map.Entry<String, String> entry : sysFieldData.entrySet()) {
            // Start repeat for each field
            View v = vi.inflate(R.layout.game_details_field, null);

            // fill in any details dynamically here
            TextView textView = v.findViewById(R.id.fieldName);
            textView.setText(entry.getKey());

            TextView textViewValue = (TextView) v.findViewById(R.id.fieldValue);
            textViewValue.setText(entry.getValue());


            sysInsertPoint.addView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

            //End repeat for each field
        }
        ProgressBar spinner = findViewById(R.id.progress_bar);
        spinner.setVisibility(View.GONE);
    }
}