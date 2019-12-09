import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

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
        System.setProperty("file.encoding","UTF-8");
        Field charset = null;
        try {
            charset = Charset.class.getDeclaredField("defaultCharset");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        charset.setAccessible(true);
        try {
            charset.set(null,null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        MovieCalendar movieCalendar = new MovieCalendar();
        movieCalendar.uiManager.draw();

    }
}
