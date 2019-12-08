import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class DataManager {
    private static DataManager _instance;
    private MovieDataStore upcomingMovies;
    private MemoDataStore memoDataStore;

    public Thread upcomingmoviedatafetchThread;
    public boolean isdataFetched;

    private DataManager()
    {
        upcomingMovies = new MovieDataStore();
        memoDataStore = new MemoDataStore();
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
    public MemoDataStore getMemoDataStore(){return memoDataStore;}
}

class MemoDataStore{
    private Vector<MemoData> memoDataVector;
    private Vector<Integer> yearVector;

    public Vector<MemoData> getMemoDataVector(){return memoDataVector;}
    public Vector<Integer> getYearVector(){return yearVector;}

    MemoDataStore(){
        memoDataVector = new Vector<MemoData>();
        yearVector = new Vector<>();
        setYearVector();
        setMemoDataVector();}

    public void addMemoData(MemoData data){memoDataVector.add(data);}
    public void addYear(int year){yearVector.add(year);}

    public void setYearVector() {
        ReadFromTXT readFromTXT = new ReadFromTXT();
        try {
            readFromTXT.readWhichYearDoYouHave();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMemoDataVector(){
        ReadFromTXT readFromTXT = new ReadFromTXT();
        for(int i = 0; i < yearVector.size(); i++){
            for(int j =1; j < 13; j++){
                if(readFromTXT.checkFileExist("C:\\Users\\user\\Desktop\\calendar\\" + yearVector.elementAt(i) + "\\" + j + ".txt") == 0){
                    for(int k = 1; k < 32; k++){
                        try {
                            if(readFromTXT.readData(i, j, k).year != 0){
                                memoDataVector.add(readFromTXT.readData(i, j, k));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

class MemoData{
    int year;
    int month;
    int day;
    String memo;
    MemoData(int year, int month, int day, String memo){
        this.year = year;
        this.month = month;
        this.day = day;
        this.memo = memo;
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
