package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReadFile {
    private static final String FILENAME = "joueurs.csv";
    private static final String FILENAME2 = "pays.csv";
    private static final String FILENAME3 = "championnat.csv";

    public static List<String[]> getJoueurs(){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
                List<String[]> aLst = new ArrayList<>();
                String ligne;
                while ((ligne = reader.readLine()) != null){
                    String[] data = ligne.split(",");
                    aLst.add(data);
                }
                reader.close();
                return aLst;
            }  catch (IOException e) { e.printStackTrace(); return null;}
    }

    public static Set<String> getPays(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILENAME2));
            Set<String> champ = new HashSet<>();
            String ligne;
            while ((ligne = reader.readLine()) != null){
                champ.add(ligne);
            }
            reader.close();
            return champ;

        }  catch (IOException e) { e.printStackTrace(); return null;}
    }

    public static Set<String> getChampionnats(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILENAME3));
            Set<String> champ = new HashSet<>();
            String ligne;
            while ((ligne = reader.readLine()) != null){
                champ.add(ligne);
            }
            reader.close();
            return champ;

        }  catch (IOException e) { e.printStackTrace(); return null;}
    }
}