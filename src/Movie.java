import javax.swing.*;
import java.awt.*;

public class Movie extends JFrame {

    private MovieSearchPanel movieSearchPanel;
    private TodayMovieScreen todayMovieScreen;

    private Container contentpane;
    Movie()
    {
        setTitle("GetMovie");
        contentpane = getContentPane();
        contentpane.setLayout(new FlowLayout());

        movieSearchPanel = new MovieSearchPanel();
        todayMovieScreen = new TodayMovieScreen();

        contentpane.add(movieSearchPanel);
        contentpane.add(todayMovieScreen);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,500);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new Movie();
    }
}

