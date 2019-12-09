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

        if(year == 0 && month == 0){
            return;
        }

        filePath = "C:\\calendar\\" + year + "\\" + month + ".txt";
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
            //bufferedWriter.close();
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
                try {
                    if(string[0].equals("")){
                        continue;
                    }
                    if(Integer.parseInt(string[0]) != day){
                        arrayList.add(temp);
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
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
            File fileDirectory = new File("C:\\calendar\\" + year);

            if(!fileDirectory.isDirectory()){
                fileDirectory.mkdir();
            }else if(!file.isFile()){
                System.out.println("파일 만들거임");
            }
        }
    }

    void writeWhichYearDoYouHave(int year) throws IOException {
        boolean haveSameYear = false;
        File fileDirectory = new File("C:\\calendar");
        if(!fileDirectory.isDirectory()){
            fileDirectory.mkdirs();
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDirectory.getPath() + "\\year.txt"));
            while(bufferedReader.readLine() != null){
                if(bufferedReader.readLine() == Integer.toString(year)){
                    haveSameYear = true;
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            File file = new File(filePath);
            File fileDirect = new File("C:\\calendar\\" + year);

            if(!fileDirect.isDirectory()){
                fileDirect.mkdir();
            }else if(!file.isFile()){
                System.out.println("파일 만들거임");
            }
        }


        if(haveSameYear == false){
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileDirectory.getPath() + "\\year.txt", true));
            bufferedWriter.write(""+year);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

    }
}
