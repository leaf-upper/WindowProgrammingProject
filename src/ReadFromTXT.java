import java.io.*;

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
    String readData(int year, int month, int day) throws IOException {
        this.filePath = "C:\\Users\\user\\Desktop\\" + year + "\\" + month + ".txt";
        if(checkFileExist(filePath) == 0){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String temp = null;
            String[] string;
            while((temp =bufferedReader.readLine()) != null){
                string = temp.split("\\|");
                if(Integer.parseInt(string[0]) == day){
                    return string[1];
                }
            }
        }
        return "error";
    }
}
