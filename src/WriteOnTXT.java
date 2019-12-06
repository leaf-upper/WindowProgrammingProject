import java.io.*;
import java.util.ArrayList;

public class WriteOnTXT {
    String memo;
    String filePath;
    BufferedWriter bufferedWriter;
    WriteOnTXT(){
        this.memo = null;
        this.filePath = null;
    }

    void writeData(int year, int month, int day, String memo) throws IOException {

        filePath = "C:\\Users\\user\\Desktop\\" + year + "\\" + month + ".txt";
        this.memo = memo;

        try {
            checkBeforeWrite(year, month, day);
            bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
            bufferedWriter.write(day + "|" + memo);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            bufferedWriter.close();
        }

    }

    void checkBeforeWrite(int year, int month, int day) throws IOException {
        BufferedWriter bufferedWriter;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String temp = null;
            ArrayList<String> arrayList = new ArrayList<>();
            String[] string;
            while((temp =bufferedReader.readLine()) != null){
                string = temp.split("\\|");
                if(Integer.parseInt(string[0]) != day){
                    arrayList.add(temp);
                }
            }
            bufferedWriter = new BufferedWriter(new FileWriter(filePath, false));
            for(int i = 0; i < arrayList.size(); i++){
                bufferedWriter.write(arrayList.get(i));
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            File file = new File(filePath);
            File fileDirectory = new File("C:\\Users\\user\\Desktop\\" + year);

            if(!fileDirectory.isDirectory()){
                fileDirectory.mkdir();
            }else if(!file.isFile()){
                System.out.println("파일 만들거임");
            }
        }
    }
}
