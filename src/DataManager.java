import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.xml.crypto.Data;
import java.util.Vector;

public class DataManager {
    private static DataManager _instance;
    private MovieDataStore upcomingMovies;

    public Thread upcomingmoviedatafetchThread;
    public boolean isdataFetched;

    private DataManager()
    {
        upcomingMovies = new MovieDataStore();
        isdataFetched = false;

        GetUpcomingMovies getUpcomingMovies = new GetUpcomingMovies();
        upcomingmoviedatafetchThread = new Thread()
        {
            @Override
            public void run()
            {
                getUpcomingMovies.parseJson(upcomingMovies);
                isdataFetched = true;
            }
        };
        upcomingmoviedatafetchThread.start();
    }

    public static DataManager getInstance()
    {
        if(_instance == null)
            _instance = new DataManager();

        return _instance;
    }

    public MovieDataStore getUpcomingMovies()
    {
        return upcomingMovies;
    }
}

class MovieDataStore{
    private Vector<MovieData> movieDataVector;

    public Vector<MovieData> getMovieDataVector() {return movieDataVector;}

    MovieDataStore(){
        movieDataVector = new Vector<MovieData>();
    }

    public void addMovieData(MovieData data){
        movieDataVector.add(data);
    }

    public void printMovieData(){
        for(MovieData movieData : movieDataVector){
            System.out.println(movieData.getReleaseDate() + " " + movieData.getTitle());
        }
    }
}


class MovieData{
    private String releaseDate;
    public String getReleaseDate() {return releaseDate;}
    private int year;
    public int getYear() {return year;}
    private int month;
    public int getMonth() {return month;}
    private int day;
    public int getDay() {return day;}
    private String title;
    public String getTitle() {return title;}

    MovieData(String releaseDate, String title)
    {
        this.releaseDate = releaseDate;
        this.title = title;
        String[] dateArray = releaseDate.split("-");
        this.year = Integer.valueOf(dateArray[0]);
        this.month = Integer.valueOf(dateArray[1]);
        this.day = Integer.valueOf(dateArray[2]);
    }
}
