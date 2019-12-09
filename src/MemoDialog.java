import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MemoDialog extends JDialog{
    int year;
    int month;
    int day;
    JTextArea jTextArea;
    public MemoDialog(int year, int month, int day) {
        super();
        this.year = year;
        this.month = month+1;
        this.day = day;
        setTitle(this.year +"."+ this.month +"."+this.day);
        init();
    }

    void init() {
        jTextArea = new JTextArea(7, 20);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        ReadFromTXT readFromTXT = new ReadFromTXT();
        try {
            if(readFromTXT.readData(year, month, day).memo != null){
                jTextArea.setText(readFromTXT.readData(year, month, day).memo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JButton jButton1 = new JButton("확인");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WriteOnTXT writeOnTXT = new WriteOnTXT();
                try {
                    writeOnTXT.writeData(year, month, day, jTextArea.getText());
                    MemoData memoData = new MemoData(year, month, day, jTextArea.getText());
                    DataManager.getInstance().getMemoDataStore().getMemoDataVector().add(memoData);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
                dispose();
            }
        });
        JButton jButton2 = new JButton("취소");
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(jScrollPane);
        getContentPane().add(jButton1);
        getContentPane().add(jButton2);
        setSize(250, 200);
        setResizable(false);
        setVisible(true);
    }
}
