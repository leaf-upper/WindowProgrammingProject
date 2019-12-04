import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

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
        monthPanel.setMonthPanel(this.year, this.month);
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
    private DayPanel dayPanel;

    CalendarPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        calendar = new GregorianCalendar(Locale.KOREA);;
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
        setMaximumSize(new Dimension(1280, 80));
        //setPreferredSize(new Dimension(100, 80));
        monthField = new JTextField();
        monthField.setBorder(new EmptyBorder(0, 0, 0, 0));
        monthField.setFont(new Font("Bodoni MT", Font.BOLD, 48));

        JLabel blankLabel = new JLabel();
        blankLabel.setPreferredSize(new Dimension(24, 8));

        yearField = new JTextField();
        yearField.setBorder(new EmptyBorder(0, 0, 0, 0));
        yearField.setFont(new Font("Bodoni MT", Font.BOLD, 48));

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
        setMaximumSize(new Dimension(1280, 40));
        for(int i = 0; i < 7; i++){
            JLabel label = new JLabel(weekName[i], SwingConstants.CENTER);
            label.setFont(new Font("Bodoni MT", Font.BOLD, 16));
            label.setForeground(Color.white);
            label.setPreferredSize(new Dimension(10, 60));
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

    void setMonthPanel(int year, int month){
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, 1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for(int i = 0; i < 42; i++){
            Color color = Color.black;
            if(i % 7 == 0)
                color = Color.red;

            if(i >= dayOfWeek && i < dayOfWeek + dayOfMonth)
            {
                dayPanels[i].setDayLabelText(i + 1 - dayOfWeek, color);
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
        }
    }
}

class DayPanel extends JPanel{
    private JLabel dayLabel;
    private ArrayList<JLabel> movieLabels;

    DayPanel(){
        this.setAlignmentX(LEFT_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new LineBorder(Color.gray));

        dayLabel = new JLabel();
        dayLabel.setFont(new Font("Bodoni MT", Font.BOLD, 16));
        //dayLabel.setMaximumSize(new Dimension(40, 20));
        //dayLabel.setAlignmentX(LEFT_ALIGNMENT);

        add(dayLabel);
    }

    public void setDayLabelText(int day, Color color){
        if(day != 0)
        {
            dayLabel.setText(String.valueOf(day));
        }
        else
        {
            dayLabel.setText("");
        }
        dayLabel.setForeground(color);
    }
}