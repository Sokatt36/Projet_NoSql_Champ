package metier;

import dao.Bdd;
import dao.readFile;

import java.util.Objects;

public class Applic {

    public static void creerPays(Bdd bdd) {
        for (String data : readFile.getPays()) {
//            System.out.println(data);
            bdd.creerPays(data);
        }
    }

    public static void creerChampionnat(Bdd bdd){
        for (String data: readFile.getChampionnats()) {
//            System.out.println(data);
            bdd.creerChampionnat(data);
        }
    }

    public static void creerJoueurs(Bdd bdd){
        for (String[] data : readFile.getJoueurs()){
//            System.out.println(data[0] + " " + data[1] + " " + data[2]);
            bdd.creerJoueur(data);
        }
    }

    public static void creerRelations(Bdd bdd){
        for (String[] data : Objects.requireNonNull(readFile.getJoueurs())){
            bdd.creerRelationJoueurNationnalite(data);
            bdd.creerRelationJoueurChampionnat(data);
        }
    }
}
