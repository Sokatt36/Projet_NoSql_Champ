import dao.Bdd;
import metier.Applic;

public class Main {
    public static void main(String[] args) {
        //Connection à la bdd
        Bdd bdd = new Bdd();
        bdd.connect();
        //Suppréssion BDD afin de rendre le script constemment exécutable
       //bdd.deleteBase();
        // Création des noeuds et des relations
        //metier.Applic.creerBdd(bdd);
        //System.out.println(Applic.getMeilleurJoueur(bdd));
       // Applic.getListeJoueurCompatibleDansPoste(bdd);
        Applic.getJoueurChampionnatEtNationnalite(bdd);
        // close bdd
        bdd.close();
    }
}
