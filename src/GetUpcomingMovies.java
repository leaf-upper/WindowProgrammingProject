import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUpcomingMovies{

    JSONObject jsonObject;
    JSONParser jsonParser;
    JSONArray movies;

    private final static String api_key = "api_key=e3f2eb568a44846e3e8c3bb859efc5e0";
    private final static String urlString = "https://api.themoviedb.org/3/movie/upcoming?";
    private final static String language = "ko";
    private final static String region = "KR";

    private URL url;
    private HttpURLConnection httpURLConnection;
    private int page;

    GetUpcomingMovies() {
        page = 1;
    }

    public JSONArray parseJson(MovieDataStore movieDataStore) {
        jsonParser = new JSONParser();
        try {

            jsonObject = (JSONObject) jsonParser.parse(getUpComingMovies().toString());
            movies = (JSONArray) jsonObject.get("results");

            for (int i = 0; i < movies.size(); i++) {
                JSONObject nowObject = (JSONObject) movies.get(i);
                movieDataStore.addMovieData(new MovieData((String)nowObject.get("release_date"), (String)nowObject.get("title")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return movies;
    }


    public StringBuffer getUpComingMovies() {
        StringBuffer buffer = new StringBuffer();
        try {
            url = new URL(urlString + api_key + "&page=" + page + "&language=" + language + "&region=" + region);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();

            System.out.println("요청: URL  : " + url);
            System.out.println("Get Upcoming movies: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
            } else
                System.out.println(httpURLConnection.getResponseMessage());

            httpURLConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
