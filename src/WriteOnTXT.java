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

        filePath = "C:\\Users\\user\\Desktop\\calendar\\" + year + "\\" + (month+1) + ".txt";
        this.memo = memo;

        writeWhichYearDoYouHave(year);

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
            File fileDirectory = new File("C:\\Users\\user\\Desktop\\calendar\\" + year);

            if(!fileDirectory.isDirectory()){
                fileDirectory.mkdir();
            }else if(!file.isFile()){
                System.out.println("파일 만들거임");
            }
        }
    }

    void writeWhichYearDoYouHave(int year) throws IOException {
        boolean haveSameYear = false;
        File fileDirectory = new File("C:\\Users\\user\\Desktop\\calendar");
        if(!fileDirectory.isDirectory()){
            fileDirectory.mkdirs();
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDirectory.getPath() + "\\year.txt"));
        while(bufferedReader.readLine() != null){
            if(bufferedReader.readLine() == Integer.toString(year)){
                haveSameYear = true;
            }
        }
        if(haveSameYear == false){
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileDirectory.getPath() + "\\year.txt", false));
            bufferedWriter.write(year);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }
}
