public class MovieCalendar {
    private UIManager uiManager;
    private GetUpcomingMovies movies;
    private  TodayMovieScreen movies2;
    MovieCalendar(){
        uiManager = UIManager.getInstance();
        movies = new GetUpcomingMovies();
        movies2 = new TodayMovieScreen();
    }

    public static void main(String[] args){
        MovieCalendar movieCalendar = new MovieCalendar();
        movieCalendar.uiManager.draw();
    }
}
