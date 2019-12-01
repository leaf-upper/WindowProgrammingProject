//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class MovieSearchPanel extends JPanel
//{
//    private JLabel message;
//    private TextField textField;
//    private JButton button;
//    private GetMovieInfo getMovieInfo;
//    private StringBuffer movieInfo;
//    private MovieSearchButtonActionListener movieSearchButtonActionListener;
//
//    MovieSearchPanel()
//    {
//        super();
//        message = new JLabel("찾으실 영화를 입력해주세요 : ");
//        textField = new TextField(10);
//        button = new JButton("입력");
//        movieSearchButtonActionListener = new MovieSearchButtonActionListener();
//
//        button.addActionListener(movieSearchButtonActionListener);
//        textField.addActionListener(movieSearchButtonActionListener);
//
//        getMovieInfo = new GetMovieInfo();
//
//        add(message);
//        add(textField);
//        add(button);
//    }
//
//
//    class MovieSearchButtonActionListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String moviename = textField.getText();
//            if(moviename.equals(""))
//            {
//                System.out.println("Movie name is blank string");
//                return;
//            }
//
//            getMovieInfo.setMoviename(moviename);
//            movieInfo = getMovieInfo.getMovieInformation();
//
//            textField.setText("");
//            System.out.println(movieInfo.toString());
//        }
//    }
//
//    class GetMovieInfo
//    {
//        private final static String key = "ServiceKey=IZ694D1W40NBPM4QT3T1";
//        private final static String urlString = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json.jsp?";
//        private final static String sort = "sort=prodYear";
//        private final static String collection = "collection=kmdb_new";
//        private final static int LIST_COUNT = 5;
//        private HttpURLConnection httpURLConnection;
//
//        String moviename;
//        URL url;
//
//        GetMovieInfo(){
//            moviename = "";
//        }
//
//        public void setMoviename(String name)
//        {
//            this.moviename = name;
//        }
//
//        public StringBuffer getMovieInformation()
//        {
//            StringBuffer buffer = new StringBuffer();
//            try {
//                url = new URL(urlString + collection + "&" + "title=" + moviename + "&" + sort + "&listCount=" + LIST_COUNT+ "&" + key);
//                httpURLConnection = (HttpURLConnection)url.openConnection();
//                int responseCode = httpURLConnection.getResponseCode();
//
//                System.out.println("요청: URL  : " + url);
//                System.out.println("Get Movie Info ResponseCode : " + responseCode);
//
//                if(responseCode == HttpURLConnection.HTTP_OK)
//                {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//
//                    String line;
//                    while((line = bufferedReader.readLine()) != null)
//                    {
//                        buffer.append(line+"\n");
//                    }
//                }else
//                    System.out.println(httpURLConnection.getResponseMessage());
//
//                httpURLConnection.disconnect();
//            }catch(Exception e)
//            {
//                e.printStackTrace();
//            }
//            return buffer;
//        }
//    }
//}
