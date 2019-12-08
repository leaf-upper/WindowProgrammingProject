import java.io.*;
import java.util.Vector;

public class ReadFromTXT {
    String filePath;
    public ReadFromTXT() {
        this.filePath = null;
    }
    int checkFileExist(String filePath){
        File file = new File(filePath);
        if(!file.isFile()){
            return -1;
        }
        return 0;
    }
    MemoData readData(int year, int month, int day) throws IOException {
        this.filePath = "C:\\calendar\\" + year + "\\" + month + ".txt";
        if(checkFileExist(filePath) == 0){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String temp = null;
            String[] string;
            while((temp =bufferedReader.readLine()) != null){
                string = temp.split("\\|");
                if(Integer.parseInt(string[0]) == day){
                    MemoData memoData = new MemoData(year, month, day, string[1]);
                    return memoData;
                }
            }
        }
        return new MemoData(0, 0, 0, " ");
    }

    void readWhichYearDoYouHave() throws IOException {
        if(checkFileExist("C:\\calendar\\year.txt") == -1){
            System.out.println("아직 생성이 한번도 안됨");
        }else {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\calendar\\year.txt"));
            DataManager dataManager = DataManager.getInstance();
            while(bufferedReader.readLine() != null){
                dataManager.getMemoDataStore().getYearVector().add(Integer.parseInt(bufferedReader.readLine()));
            }
        }
    }
}
