import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class TodayMovieScreen extends JLabel
{

    JSONObject jsonObject;
    JSONParser jsonParser;
    JSONArray jsonArray;

    MovieDataStore todayMovieStore;
    GetTodayMovieScreenInfo movieScreenInfo;
    ChangeLabelThread changeLabelThread;
    TodayMovieScreen()
    {
        super();
        setFont(new Font("궁서",Font.PLAIN, 20));
        todayMovieStore = new MovieDataStore();

        movieScreenInfo = new GetTodayMovieScreenInfo();
        movieScreenInfo.makeDayScreenMovieInfo();
        todayMovieStore.printMovieData();
        changeLabelThread = new ChangeLabelThread(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JLabel label = (JLabel)e.getSource();
                UIManager.getInstance().mainFrame.goToMovieSearchPanel(label.getText(), Integer.parseInt(new DateTime().getCurrentYear()));
            }
        });
    }

    public void runLabelThread()
    {
        changeLabelThread.start();
    }

    class ChangeLabelThread extends Thread
    {
        JLabel label;
        ChangeLabelThread(JLabel label)
        {
            this.label = label;
        }

        @Override
        public void run()
        {
            try {
                while (true) {
                    for (int i = 0; i < todayMovieStore.getMovieDataVector().size(); i++) {
                        label.setText(todayMovieStore.getMovieDataVector().get((i)).getTitle());
                        sleep(3000);
                    }
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    class GetTodayMovieScreenInfo
    {
        private final static String key = "key=9b5ebb9d1d75e5bab3e8904587d8cfd9";
        private final static String urlString = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?";
        private final static int ITEM_PER_PAGE = 10;

        private String targetDt;
        private URL url;
        private HttpURLConnection httpURLConnection;

        GetTodayMovieScreenInfo()
        {
            targetDt = new DateTime().getBeforeOneDayDateTime();
        }

        public void setDateTime(String s)
        {
            targetDt = s;
        }

        public void makeDayScreenMovieInfo()
        {
            parseJson();
        }

        public void parseJson()
        {
            jsonParser = new JSONParser();
            try {

                jsonObject = (JSONObject)jsonParser.parse(movieScreenInfo.getTodayMovieScreen().toString());
                JSONObject boxofficeType = (JSONObject)jsonObject.get("boxOfficeResult");
                jsonArray =  (JSONArray)boxofficeType.get("dailyBoxOfficeList");

                for(int i = 0; i < jsonArray.size(); i++)
                {
                    JSONObject nowObject = (JSONObject)jsonArray.get(i);
                    todayMovieStore.addMovieData(new MovieData((String)nowObject.get("openDt"), (String)nowObject.get("movieNm")));
                }
            }catch(ParseException e)
            {
                e.printStackTrace();
            }
        }

        public StringBuffer getTodayMovieScreen()
        {
            StringBuffer buffer = new StringBuffer();
            try {
                url = new URL(urlString + "&" + "targetDt=" + targetDt + "&itemPerPage=" + ITEM_PER_PAGE + "&" + key);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                int responseCode = httpURLConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK)
                {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    String line;
                    while((line = bufferedReader.readLine()) != null)
                    {
                        buffer.append(line+"\n");
                    }
                }else
                    System.out.println(httpURLConnection.getResponseMessage());
                httpURLConnection.disconnect();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            return buffer;
        }
    }
}
