import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MovieSearchPanel extends JPanel implements Scrollable {
    GetMovieInfo getMovieInfo;
    JSONArray movieArray;
    BoxLayout boxLayout;
    String default_imagepath = "https://image.tmdb.org/t/p/w500";


    TitleGenrePanel titleGenrePanel;
    PosterPanel posterPanel;
    OverViewPanel overViewPanel;

    MovieSearchPanel() {
        super();
        setLayout(new BorderLayout());

        titleGenrePanel = new TitleGenrePanel();
        add(titleGenrePanel, BorderLayout.NORTH);

        posterPanel = new PosterPanel();
        add(posterPanel, BorderLayout.EAST);

        overViewPanel = new OverViewPanel();
        add(overViewPanel, BorderLayout.CENTER);

        overViewPanel.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.mainFrame.cardLayout.next(UIManager.mainFrame.getContentPane());
            }
        });
    }

    // 패널 바뀔 때마다 호출할 함수
    public void setMovieSearchPanel(String moviename, int primary_release_year) {
        getMovieInfo = new GetMovieInfo();
        movieArray = getMovieInfo.getMovieJSONArray(moviename, primary_release_year);

        if(movieArray.size() >= 1) {
            JSONObject nowObject = (JSONObject) movieArray.get(0);
            //포스터 할당
            ImageIcon icon = getImage(default_imagepath + nowObject.get("poster_path"));
            posterPanel.getPosterLabel().setIcon(icon);
            JSONArray array = (JSONArray) nowObject.get("genre_ids");
            // 장르 할당
            StringBuilder genreText = new StringBuilder();
            for (int idx = 0; idx < array.size(); idx++) {
                Long genre = (Long) array.get(idx);
                String genreString = MovieGenre.getGenre().get(genre);
                genreText.append(genreString);
                if (idx + 1 != array.size())
                    genreText.append(", ");
            }
            titleGenrePanel.getGenrePanel().getGenreLabel().setText(genreText.toString());
            // 영화 제목 할당
            titleGenrePanel.getTitlePanel().getTitleLabel().setText((String) nowObject.get("title"));
            // 줄거리 할당
            overViewPanel.getOverViewArea().setText((String) nowObject.get("overview"));
        }else if(movieArray.size() == 0)
        {
            titleGenrePanel.getTitlePanel().getTitleLabel().setText("찾고계신 영화에 대한 정보가 없습니다.");
            titleGenrePanel.getGenrePanel().getGenreLabel().setText("");
            posterPanel.getPosterLabel().setIcon(null);
            overViewPanel.getOverViewArea().setText(null);
        }

    }

    public ImageIcon getImage(String path) {
        Image image;
        try {
            URL url = new URL(path);
            image = ImageIO.read(url);

            return new ImageIcon(image);
        } catch (Exception e) {
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

class PosterPanel extends JPanel {
    private JLabel posterLabel;

    public JLabel getPosterLabel() {
        return posterLabel;
    }

    PosterPanel() {
        setLayout(new GridLayout(1, 1));
        setBackground(Color.BLACK);
        posterLabel = new JLabel();
        add(posterLabel);
    }
}

class TitleGenrePanel extends JPanel {
    TitlePanel titlePanel;

    public TitlePanel getTitlePanel() {
        return titlePanel;
    }

    GenrePanel genrePanel;

    public GenrePanel getGenrePanel() {
        return genrePanel;
    }

    TitleGenrePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        titlePanel = new TitlePanel();
        add(titlePanel);
        genrePanel = new GenrePanel();
        add(genrePanel);
    }
}

class TitlePanel extends JPanel {
    private JLabel titleLabel;

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    TitlePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.darkGray);
        setForeground(Color.white);
        titleLabel = new JLabel("영화 제목");
        titleLabel.setFont(new Font("돋움체", Font.BOLD, 70));
        titleLabel.setForeground(Color.white);
        add(titleLabel);
    }
}

class GenrePanel extends JPanel {
    private JLabel genreLabel;

    public JLabel getGenreLabel() {
        return genreLabel;
    }

    GenrePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.BLACK);

        genreLabel = new JLabel("장르");
        genreLabel.setFont(new Font("돋움체", Font.PLAIN, 30));
        genreLabel.setForeground(Color.white);
        add(genreLabel);
    }
}

class OverViewPanel extends JPanel {
    private JTextArea overViewArea;

    public JTextArea getOverViewArea() {
        return overViewArea;
    }

    JButton backButton;

    public JButton getBackButton() {
        return backButton;
    }

    OverViewPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);

        JLabel blank = new JLabel(" ");
        blank.setMaximumSize(new Dimension(10, 30));
        blank.setBackground(Color.white);
        add(blank);

        overViewArea = new JTextArea(20, 55);
        overViewArea.setFont(new Font("바탕체", Font.BOLD, 28));
        overViewArea.setBackground(Color.white);
        overViewArea.setLineWrap(true);
        overViewArea.setFocusable(false);
        overViewArea.setMaximumSize(new Dimension(Math.round(UIManager.getInstance().FRAME_WIDTH / (16 / 9) * 0.65f), UIManager.getInstance().FRAME_WIDTH));
        backButton = new JButton("캘린더로 돌아가기");
        backButton.setFont(new Font("돋움체", Font.BOLD, 28));
        backButton.setOpaque(false);
        backButton.setBackground(Color.white);
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setBorder(new LineBorder(Color.black));

        add(overViewArea);
        add(backButton);
    }
}
