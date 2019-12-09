import javax.xml.crypto.Data;

public class MovieCalendar {
    private UIManager uiManager;
    private GetUpcomingMovies movies;
    private  TodayMovieScreen movies2;

    MovieCalendar(){
        DataManager.getInstance();
        DataManager.getInstance().getMemoDataStore().init();
        uiManager = UIManager.getInstance();
        movies = new GetUpcomingMovies();
        movies2 = new TodayMovieScreen();
    }

    public static void main(String[] args){
        MovieCalendar movieCalendar = new MovieCalendar();
        movieCalendar.uiManager.draw();

    }
}
