import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalendarPanel extends JPanel {

    private Calendar calendar;
    public Calendar getCalendar() { return calendar; }

    public void setCalendar(int year, int month, int day){
        setYear(year);
        setMonth(month);
        setDay(day);
        calendar.set(this.year, this.month, this.day);
        infoPanel.setMonthFieldText(this.month);
        infoPanel.setYearFieldText(this.year);

        if(!DataManager.getInstance().isdataFetched)
        {
            try {
                DataManager.getInstance().upcomingmoviedatafetchThread.join(5000);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        monthPanel.setMonthPanel(this.year, this.month, upcomingMovieDataStore);
    }
    private int year;
    private int month;
    private int day;
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }
    public void setYear(int value) {
        if(value < 0 )
            year = 0;
        else
            year = value;
    }
    public void setMonth(int value) {
        if(value > 11)
        {
            month = 0;
            setYear(getYear() + 1);
        }
        else if(value < 0)
        {
            month = 11;
            setYear(getYear() - 1);
        }
        else
            month = value;
    }
    public void setDay(int value){
        day = value;
    }

    private InfoPanel infoPanel;
    private WeekPanel weekPanel;
    private MonthPanel monthPanel;
    private MovieDataStore upcomingMovieDataStore;

    CalendarPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        calendar = new DateTime().getCalendar();
        setYear(calendar.get(Calendar.YEAR));
        setMonth(calendar.get(Calendar.MONTH));;
        setDay(calendar.get(Calendar.DATE));
        infoPanel = new InfoPanel();
        //infoPanel.addMouseListener(new DisableFocusMouseListener());
        infoPanel.getMonthField().addActionListener(new MonthInfoListener());
        infoPanel.getYearField().addActionListener(new YearInfoListener());
        addMouseListener(new FocusMouseListener());
        add(infoPanel);
        weekPanel = new WeekPanel();
        add(weekPanel);
        monthPanel = new MonthPanel();
        addMouseWheelListener(new MonthWheelListener());
        add(monthPanel);

        upcomingMovieDataStore = DataManager.getInstance().getUpcomingMovies();
        setCalendar(getYear(), getMonth(), getDay());
    }

    CalendarPanel(int year, int month, int day){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        calendar = new GregorianCalendar(Locale.KOREA);;
        calendar.set(year, month, day);
        setYear(calendar.get(Calendar.YEAR));
        setMonth(calendar.get(Calendar.MONTH));;
        setDay(calendar.get(Calendar.DATE));
        infoPanel = new InfoPanel();
        //infoPanel.addMouseListener(new DisableFocusMouseListener());
        infoPanel.getMonthField().addActionListener(new MonthInfoListener());
        infoPanel.getYearField().addActionListener(new YearInfoListener());
        addMouseListener(new FocusMouseListener());
        add(infoPanel);
        weekPanel = new WeekPanel();
        add(weekPanel);
        monthPanel = new MonthPanel();
        addMouseWheelListener(new MonthWheelListener());
        add(monthPanel);

        setCalendar(getYear(), getMonth(), getDay());
    }

    class MonthWheelListener implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            int n = e.getWheelRotation();
            if(n<0){
                setCalendar(getYear(), getMonth() + 1, getDay());
            }
            else{
                setCalendar(getYear(), getMonth() - 1, getDay());
            }
        }
    }

    class MonthInfoListener implements ActionListener{
        private String[] monthNames = infoPanel.getMonthNames();
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField textField = (JTextField)e.getSource();
            String inputText = textField.getText();
            for(int i = 0; i < monthNames.length; i++){
                System.out.println(monthNames[i] + " vs " + inputText);
                if(inputText.equals(monthNames[i]) || inputText.equals(String.valueOf(i + 1))){
                    infoPanel.setMonthFieldText(i);
                    setMonth(i);
                    setCalendar(getYear(), getMonth(), getDay());
                    return;
                }
            }
            infoPanel.setMonthFieldText(getMonth());
        }
    }

    class YearInfoListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField textField = (JTextField)e.getSource();
            String inputText = textField.getText();
            boolean isNumber = true;
            char checkChar;
            for(int i = 0; i<inputText.length(); i++){
                checkChar = inputText.charAt(i);
                if( checkChar < 48 || checkChar > 58)
                {
                    isNumber = false;
                    break;
                }
            }
            if(isNumber)
            {
                if(Integer.valueOf(inputText) > 0 && Integer.valueOf(inputText) < Long.MAX_VALUE)
                {
                    infoPanel.setYearFieldText(Integer.valueOf(inputText));
                    setYear(Integer.valueOf(inputText));
                    setCalendar(getYear(), getMonth(), getDay());
                }
                else
                    infoPanel.setYearFieldText(getYear());
            }
            else
            {
                infoPanel.setYearFieldText(getYear());
            }
        }
    }

    class FocusMouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
            requestFocus();
        }
    }
}

class InfoPanel extends JPanel{

    private String[] monthNames = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
            "AUG", "SEP", "OCT", "NOV", "DEC"};
    public String[] getMonthNames() {return monthNames;}
    private JTextField monthField;
    public JTextField getMonthField() {return monthField;}
    private JTextField yearField;
    public JTextField getYearField() {return yearField;}

    InfoPanel(){
        setBackground(Color.white);
        //setLayout(new FlowLayout(FlowLayout.CENTER));\
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setMaximumSize(new Dimension(UIManager.FRAME_WIDTH,  UIManager.FRAME_HEIGHT));
        //setPreferredSize(new Dimension(100, 80));
        monthField = new JTextField();
        monthField.setBorder(new EmptyBorder(0, 0, 0, 0));
        monthField.setFont(new Font("Bodoni MT", Font.BOLD, 42));

        JLabel blankLabel = new JLabel();
        blankLabel.setPreferredSize(new Dimension(24, 4));

        yearField = new JTextField();
        yearField.setBorder(new EmptyBorder(0, 0, 0, 0));
        yearField.setFont(new Font("Bodoni MT", Font.BOLD, 42));

        add(monthField);
        add(blankLabel);
        add(yearField);
    }

    public void setMonthFieldText(int month){
        monthField.setText(monthNames[month]);
    }
    public void setYearFieldText(int year){
        yearField.setText(String.valueOf(year));
    }
}

class WeekPanel extends JPanel{

    private String[] weekName = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
    WeekPanel(){
        setBackground(Color.darkGray);
        setLayout(new GridLayout(1, 7));
        setMaximumSize(new Dimension(UIManager.FRAME_WIDTH, UIManager.FRAME_HEIGHT));
        for(int i = 0; i < 7; i++){
            JLabel label = new JLabel(weekName[i], SwingConstants.CENTER);
            label.setFont(new Font("Bodoni MT", Font.BOLD, 16));
            label.setForeground(Color.white);
            label.setPreferredSize(new Dimension(10, 40));
            add(label);
        }
    }
}

class MonthPanel extends JPanel{
    private DayPanel[] dayPanels;
    MonthPanel(){
        setBackground(Color.white);
        setLayout(new GridLayout(6, 7));
        dayPanels = new DayPanel[42];
        for(int i = 0; i < 42; i++){
            dayPanels[i] = new DayPanel();
            dayPanels[i].setBackground(Color.white);
            if(i % 7 == 0 || i % 7 == 6){
                dayPanels[i].setBackground(Color.lightGray);
            }
            add(dayPanels[i]);
        }
    }

    void setMonthPanel(int year, int month, MovieDataStore movieDataStore){
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, 1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for(int i = 0; i < 42; i++){
            Color color = Color.black;
            if(i % 7 == 0)
                color = Color.red;
            dayPanels[i].getMovieDataVector().clear();

            if(i >= dayOfWeek && i < dayOfWeek + dayOfMonth)
            {
                dayPanels[i].setDayLabelText(i + 1 - dayOfWeek, color);
                for(int j = 0; j < movieDataStore.getMovieDataVector().size(); j++)
                {
                    if(movieDataStore.getMovieDataVector().get(j).getYear() != year)
                        continue;
                    if(movieDataStore.getMovieDataVector().get(j).getMonth() - 1 != month)
                        continue;
                    if(movieDataStore.getMovieDataVector().get(j).getDay() == i)
                        dayPanels[i].addMovieData(movieDataStore.getMovieDataVector().get(j));
                }
            }
            else if(i < dayOfWeek)
            {
                int prevMonth = month;
                int prevYear = year;
                if(month - 1 < 0)
                {
                    prevMonth = 11;
                    if(prevYear - 1 < 0)
                        prevYear = 0;
                    else
                        prevYear--;
                }
                else
                    prevMonth--;
                calendar.set(prevYear, prevMonth, 1);
                int prevDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - (dayOfWeek - i - 1);
                dayPanels[i].setDayLabelText(prevDay, Color.gray);
            }
            else if(i > dayOfWeek + dayOfMonth - 1)
            {
                int nextDay = i - (dayOfWeek + dayOfMonth);
                dayPanels[i].setDayLabelText(nextDay + 1, Color.gray);
            }
            dayPanels[i].setMovieLabels();
        }
    }
}

class DayPanel extends JPanel{
    private JLabel dayLabel;
    private Vector<MovieData> movieDataVector;
    private Vector<JLabel> movieLabels;

    private boolean isMemoPanel;
    public boolean getIsMemoPanel() {return isMemoPanel;}

    private static final int MAX_MOVIE = 6;

    public Vector<MovieData> getMovieDataVector() {return movieDataVector;}

    DayPanel(){
        this.setAlignmentX(LEFT_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new LineBorder(Color.gray));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CalendarPanel calendar = UIManager.getInstance().getCalendarPanel();
                MemoDialog memoDialog = new MemoDialog(calendar.getYear(), calendar.getMonth(), Integer.parseInt(dayLabel.getText()));
                memoDialog.setVisible(true);
            }
        });
        dayLabel = new JLabel();
        dayLabel.setFont(new Font("Bodoni MT", Font.BOLD, 16));
        add(dayLabel);

        movieDataVector = new Vector<MovieData>();
        movieLabels = new Vector<JLabel>();
        for(int i = 0; i < MAX_MOVIE; i++)
        {
            JLabel movieLabel = new JLabel(" ");
            movieLabel.setFont(new Font("바탕체", Font.PLAIN, 12));
            movieLabel.setForeground(Color.blue);
            movieLabel.addMouseListener(new MovieLabelMouseListener());
            movieLabels.add(movieLabel);
            add(movieLabel);
        }

    }

    public void setDayLabelText(int day, Color color){
        dayLabel.setText(String.valueOf(day));
        dayLabel.setForeground(color);
    }

    public void addMovieData(MovieData data){
        movieDataVector.add(data);
    }

    public void setMovieLabels(){
        for(int i = 0; i < movieLabels.size(); i++){
            if(i < movieDataVector.size())
                movieLabels.get(i).setText(movieDataVector.get(i).getTitle());
            else
                movieLabels.get(i).setText(" ");
        }
    }

    class MovieLabelMouseListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            JLabel label = (JLabel)e.getSource();
            for(int i = 0; i < movieDataVector.size(); i++)
            {
                if(label.getText().equals(movieDataVector.get(i).getTitle()))
                {
                    System.out.println(movieDataVector.get(i).getYear() + "-" + movieDataVector.get(i).getTitle());
                    UIManager.getInstance().mainFrame.goToMovieSearchPanel(movieDataVector.get(i).getTitle(), movieDataVector.get(i).getYear());
                    break;
                }
            }

        }
    }
}

