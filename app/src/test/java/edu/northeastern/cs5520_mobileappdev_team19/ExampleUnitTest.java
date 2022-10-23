package edu.northeastern.cs5520_mobileappdev_team19;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import edu.northeastern.cs5520_mobileappdev_team19.models.GameInfo;
import edu.northeastern.cs5520_mobileappdev_team19.models.Category;
import edu.northeastern.cs5520_mobileappdev_team19.services.GamesService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testService() throws IOException {
        GamesService service = GamesService.getInstance();
        final String TAG = "RetrofitActivity";
        Call<List<GameInfo>> call = service.api().getByCategory(Category.FLIGHT);
        // call.execute() runs on the current thread, which is main at the moment. This will crash
        // use Retrofit's method enqueue. This will automatically push the network call to background thread

        Response<List<GameInfo>> res = call.execute();

        List<GameInfo> gameInfoList = res.body();
        assert gameInfoList != null;
        StringBuilder str = new StringBuilder();
        for (GameInfo post : gameInfoList) {
            str.append("Code:: ")
                    .append(res.code())
                    .append("ID : ")
                    .append(post.getId())
                    .append("\n")
                    .append("Type : ")
                    .append(post.getGenre())
                    .append("\n")
                    .append("UserID :")
                    .append(post.getId())
                    .append("\n")
                    .append("Title: ")
                    .append(post.getTitle())
                    .append("\n")
                    .append("Description: ")
                    .append(post.getShortDescription())
                    .append("\n")
                    .append("Date: ")
                    .append(post.getReleaseDate())
                    .append("\n")
                    .append("Url: ")
                    .append(post.getFreetogameProfileUrl())
                    .append("\n");
        }
        System.out.println(str);
    }
}