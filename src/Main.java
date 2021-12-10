import dao.Bdd;
import dao.readFile;
import metier.Applic;
import org.neo4j.driver.Result;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        //Connection à la bdd
        Bdd bdd= new Bdd();
        bdd.connect();
        bdd.deleteBase();
        // Création des noeuds et des relations
        metier.Applic.creerPays(bdd);
        metier.Applic.creerChampionnat(bdd);
        metier.Applic.creerJoueursEtRelations(bdd);
        // close bdd
        bdd.close();
    }
}
