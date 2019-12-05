import javax.swing.*;
import java.awt.*;

public class UIManager {
    private static UIManager _instance;
    public static MainFrame mainFrame;
    private static CalendarPanel calendarPanel;
    private static MovieSearchPanel movieSearchPanel;
    public static final int FRAME_WIDTH = 1600;
    public static final int FRAME_HEIGHT = 900;
    public CalendarPanel getCalendarPanel() {return calendarPanel;}
    public MovieSearchPanel getMovieSearchPanel() {return movieSearchPanel;};

    private UIManager(){
    }
    public static UIManager getInstance()
    {
        if(_instance == null)
            _instance = new UIManager();
        return _instance;
    }
    public void draw(){
        mainFrame = new MainFrame();
    }

    class MainFrame extends JFrame{
        CardLayout cardLayout;
        MainFrame(){
            super();
            cardLayout = new CardLayout();
            setLayout(cardLayout);
            setSize(FRAME_WIDTH,  FRAME_HEIGHT);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);

            calendarPanel = new CalendarPanel();
            add("CalendarPanel", calendarPanel);
            movieSearchPanel = new MovieSearchPanel();
            add("MovieSearchPanel", movieSearchPanel);
            setVisible(true);
        }

        public void goToMovieSearchPanel(String movieName, int year)
        {
            movieSearchPanel.setMovieSearchPanel(movieName, year);
            cardLayout.show(getContentPane(), "MovieSearchPanel");

        }
    }
}
