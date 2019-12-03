public class MovieCalendar {
    private UIManager uiManager;
    MovieCalendar(){
        uiManager = UIManager.getInstance();
    }

    public static void main(String[] args){
        MovieCalendar movieCalendar = new MovieCalendar();
        movieCalendar.uiManager.draw();
    }
}
