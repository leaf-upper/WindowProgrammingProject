import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class TodayMovieScreen extends JPanel
{
    JLabel message;
    CardLayout cardLayout;

    JSONObject jsonObject;
    JSONParser jsonParser;
    JSONArray jsonArray;


    GetTodayMovieScreenInfo movieScreenInfo;

    TodayMovieScreen()
    {
        super();
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        message = new JLabel("오늘 상영중인 영화");
        movieScreenInfo = new GetTodayMovieScreenInfo();
        movieScreenInfo.makeDayScreenMovieInfo();
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
                    System.out.print(nowObject.get("movieNm") + ":");
                    System.out.println(nowObject.get("openDt"));
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

                System.out.println("요청: URL  : " + url);
                System.out.println("Today Movie Screen Info ResponseCode : " + responseCode);

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
