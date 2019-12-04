import javax.swing.*;

public class UIManager {
    private static UIManager _instance;
    private static MainFrame mainFrame;
    private static CalendarPanel calendarPanel;
    public static final int FRAME_WIDTH = 1600;
    public static final int FRAME_HEIGHT = 900;
    public CalendarPanel getCalendarPanel() {return calendarPanel;}

    private UIManager(){
    }
    public static UIManager getInstance()
    {
        if(_instance == null)
            _instance = new UIManager();
        return _instance;
    }
    public void draw(){
        mainFrame = new MainFrame();
    }

    class MainFrame extends JFrame{
        MainFrame(){
            super();
            setSize(FRAME_WIDTH,  FRAME_HEIGHT);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            calendarPanel = new CalendarPanel();
            add(calendarPanel);
            setVisible(true);
        }
    }
}
