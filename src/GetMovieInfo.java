import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetMovieInfo
{
    JSONObject jsonObject;
    JSONParser jsonParser;
    JSONArray movies;

    private final static String api_key = "api_key=e3f2eb568a44846e3e8c3bb859efc5e0";
    private final static String urlString = "https://api.themoviedb.org/3/search/movie?";
    private final static String language = "ko";
    private final static String region = "KR";

    private URL url;
    private HttpURLConnection httpURLConnection;
    private int page;

    private String moviename;
    private int primary_release_year;

    GetMovieInfo()
    {
        page = 1;
    }

    public JSONArray parseJson(String moviename,int primary_release_year) {
        jsonParser = new JSONParser();
        try {

            jsonObject = (JSONObject) jsonParser.parse(getSearchMovie(moviename, primary_release_year).toString());
            movies = (JSONArray) jsonObject.get("results");

            for (int i = 0; i < movies.size(); i++) {
                JSONObject nowObject = (JSONObject) movies.get(i);
                System.out.print(nowObject.get("title") + ":");
                System.out.println(nowObject.get("release_date"));
                System.out.println(nowObject.get("poster_path"));

                System.out.println(nowObject.get("overview"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public JSONArray getMovieJSONArray(String moviename, int primary_release_year) {
        jsonParser = new JSONParser();
        try {

            jsonObject = (JSONObject) jsonParser.parse(getSearchMovie(moviename,primary_release_year).toString());
            movies = (JSONArray) jsonObject.get("results");
        }catch (ParseException e)
        {
            e.printStackTrace();
        }
        return movies;
    }

    public StringBuffer getSearchMovie(String movie, int primary_release_year)
    {
        moviename = movie;
        this.primary_release_year = primary_release_year;

        StringBuffer buffer = new StringBuffer();
        try {
            url = new URL(urlString + api_key + "&page=" + page + "&language=" + language + "&region=" + region + "&query=" + moviename+ "&primary_release_year=" + this.primary_release_year);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();

            System.out.println("요청: URL  : " + url);
            System.out.println("Get Searching movies: " + responseCode);

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
