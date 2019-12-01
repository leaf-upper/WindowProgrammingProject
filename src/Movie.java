import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Movie extends JFrame {

    GetMovieInfo movieInfo;
    JSONArray movies;

    Movie()
    {
        movieInfo = new GetMovieInfo();
        movies = movieInfo.parseJson("겨울왕국");
        for (int i = 0; i < movies.size(); i++) {
            JSONObject nowObject = (JSONObject) movies.get(i);

            URL url;
            Image image;
            try{
                url = new URL("https://image.tmdb.org/t/p/w154" + (String)nowObject.get("poster_path"));
                image = ImageIO.read(url);
                ImageIcon icon = new ImageIcon(image);
                JLabel label = new JLabel(icon);
                add(label);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        setVisible(true);
        setSize(500,500);
    }

    public static void main(String[] args)
    {
        new Movie();

    }
}

