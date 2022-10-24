package edu.northeastern.cs5520_mobileappdev_team19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.models.GameInfo;
import edu.northeastern.cs5520_mobileappdev_team19.services.GamesService;
import edu.northeastern.cs5520_mobileappdev_team19.services.IGameService;
import edu.northeastern.cs5520_mobileappdev_team19.utils.GameViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameListActivity extends AppCompatActivity {

    private List<GameInfo> games;
    private GameViewAdapter gameViewAdapter;
    private RecyclerView linkRecyclerView;
    private IGameService gameService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        gameService = GamesService.getInstance().api();
        games = new ArrayList<>();
        linkRecyclerView = findViewById(R.id.game_recycler_view);
        linkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameViewAdapter = new GameViewAdapter(games, this);
        linkRecyclerView.setAdapter(gameViewAdapter);

        fetchGames();
    }

    private void fetchGames() {
        Call<List<GameInfo>> fetchGamesCall = gameService.getAll();
        fetchGamesCall.enqueue(new Callback<List<GameInfo>>() {
            @Override
            public void onResponse(Call<List<GameInfo>> call, Response<List<GameInfo>> response) {
                List<GameInfo> gamesInResponse = response.body();
                if (gamesInResponse == null) return;

                games.addAll(gamesInResponse);
                synchronized (gameViewAdapter) {
                    gameViewAdapter.notifyDataSetChanged();
                }

                ProgressBar spinner = findViewById(R.id.progress_bar);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<GameInfo>> call, Throwable t) {
                // TODO: Better logging maybe
                System.out.println(t.getMessage());
            }
        });
    }
}