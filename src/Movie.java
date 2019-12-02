import javax.swing.*;
import java.awt.*;

public class Movie extends JFrame {

    MovieSearchPanel panel;
    JScrollPane pane;
    Movie()
    {
        panel = new MovieSearchPanel("어벤져스", 2019);
        pane = new JScrollPane(panel);

        add(pane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1280,720);
    }

    public static void main(String[] args)
    {
        new Movie();
    }
}

