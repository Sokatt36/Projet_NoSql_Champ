package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class readFile {
    private static final String FILENAME = "Club.csv";

    public static List<String[]> getClub(){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
                List<String[]> aLst = new ArrayList<>();
                String ligne;
                while ((ligne = reader.readLine()) != null){
                    String[] data = ligne.split(";");
                    aLst.add(data);
                }
                reader.close();
                return aLst;

            }  catch (IOException e) { e.printStackTrace(); return null;}
    }
}
