import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.net.URL;

public class MovieSearchPanel extends JPanel implements Scrollable
{
    GetMovieInfo getMovieInfo;
    JSONArray movieArray;
    BoxLayout boxLayout;
    String default_imagepath = "https://image.tmdb.org/t/p/w300";

    MovieSearchPanel(String moviename, int primary_release_year)
    {
        super();
        getMovieInfo = new GetMovieInfo();
        movieArray = getMovieInfo.getMovieJSONArray(moviename, primary_release_year);
        boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

        setLayout(boxLayout);
        for (int i = 0; i < movieArray.size(); i++)
        {
            JSONObject nowObject = (JSONObject) movieArray.get(i);

            Box movieBox = Box.createHorizontalBox();
            add(movieBox);


            ImageIcon icon = getImage(default_imagepath + nowObject.get("poster_path"));
            movieBox.add(new JLabel(icon));
            movieBox.setAlignmentX(LEFT_ALIGNMENT);
            movieBox.setPreferredSize(new Dimension(1200, 400));

            JPanel movieInfoPanel = new JPanel();
            movieInfoPanel.setLayout(new BoxLayout(movieInfoPanel, BoxLayout.Y_AXIS));
            JLabel title = new JLabel((String)nowObject.get("title"));
            title.setFont(new Font("맑은 고딕",Font.CENTER_BASELINE , 50));
            title.setAlignmentX(LEFT_ALIGNMENT);
            movieInfoPanel.add(title);

            JSONArray array = (JSONArray) nowObject.get("genre_ids");

            for(int idx = 0; idx < array.size(); idx++)
            {
                Long genre =  (Long)array.get(idx);
                JLabel genreText = new JLabel(MovieGenre.getGenre().get(genre));
                genreText.setFont(new Font("궁서", Font.PLAIN, 30));
                movieInfoPanel.add(genreText);
            }

            JPanel overViewpanel = new JPanel();
            JTextArea overview = new JTextArea(5,50);

            overview.setText((String)nowObject.get("overview"));
            overview.setLineWrap(true);
            overview.setFocusable(false);
            overview.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            overview.setFont(new Font("돋움", Font.PLAIN, 18));
            overViewpanel.add(overview);
            movieBox.add(movieInfoPanel);
            overViewpanel.setAlignmentX(LEFT_ALIGNMENT);

            movieInfoPanel.add(overViewpanel);
            movieBox.setBorder(BorderFactory.createEtchedBorder());
        }

    }

    public ImageIcon getImage(String path)
    {
        Image image;
        try {
            URL url = new URL(path);
            image = ImageIO.read(url);

            return new ImageIcon(image);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
