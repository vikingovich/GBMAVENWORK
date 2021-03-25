package lesson2.clientside;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FileWork {

    private String filename;

    public FileWork(String filename) {
        this.filename = filename;
    }

    public void fileWriter(String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date date = new Date();
            writer.write(formater.format(date) + "\n" + text );
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> fileReader() {

        ArrayList<String> arrayList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {


            String line;
            while ((line = reader.readLine()) != null) {
                arrayList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }
}
