import dao.Bdd;
import dao.readFile;
import org.neo4j.driver.Result;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Bdd bdd= new Bdd();
        bdd.connect();

        System.out.println(bdd.getRandomChamp());
        System.out.println(bdd.getRandomPays());

//        for (String[] data : Objects.requireNonNull(readFile.getJoueurs())){
//            System.out.println(data[0] + " " + data[1] + " " + data[2]);
//            bdd.creerJoueur(data);
//        }

        //bdd.deleteJoueur();
//
//
//        for (String data: Objects.requireNonNull(readFile.getPays())) {
//            System.out.println(data);
//            bdd.creerPays(data);
//        }
////
//       for (String data: Objects.requireNonNull(readFile.getChampionnats())) {
//            System.out.println(data);
//            bdd.creerChampionnat(data);
//        }

//        Result res = bdd.run("match(a:Joueur) return a");
//        System.out.println(res);
//        int cpt = 0;
//        while (res.hasNext()){
//            System.out.println(cpt);
//            System.out.println(res.stream());
//            cpt++;
//        }
        bdd.close();
    }
}
