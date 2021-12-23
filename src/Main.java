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
        System.out.println("---------------------------REQUÊTE 1-----------------------------------------");
        Applic.getMeilleurJoueur(bdd);
        System.out.println("---------------------------REQUÊTE 2-----------------------------------------");
        Applic.getListeJoueurCompatibleDansPoste(bdd);
        System.out.println("---------------------------REQUÊTE 3-----------------------------------------");
        Applic.getJoueurChampionnatEtNationnalite(bdd,"France", "Ligue 1", "Attaquant");
        System.out.println("---------------------------REQUÊTE 4-----------------------------------------");
        Applic.getJoueurNationnalite(bdd, "Portugal");
        // close bdd
        bdd.close();
    }
}
