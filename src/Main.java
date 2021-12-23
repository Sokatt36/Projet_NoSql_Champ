import dao.Bdd;
import metier.Applic;

public class Main {
    public static void main(String[] args) {
        //Connection à la bdd
        Bdd bdd = new Bdd();
        bdd.connect();
        //Suppréssion BDD afin de rendre le script constemment exécutable /!\ LIGNE DU DESSOUS (10) Á DECOMMENTER SI BESOIN DE CRÉATION DE LA BASE /!\
        //bdd.deleteBase();
        //Création des noeuds et des relations /!\ LIGNE DU DESSOUS (12) Á DECOMMENTER SI BESOIN DE CRÉATION DE LA BASE /!\
        //metier.Applic.creerBdd(bdd);
        System.out.println("Bonjour et bienvenue sur notre application. Pour le bon fonctionnement des requêtes vous devrez leurs fournir les ID en input (system.in)");
        System.out.println("---------------------------REQUÊTE 1-----------------------------------------");
        Applic.getMeilleurJoueur(bdd);
        System.out.println("---------------------------REQUÊTE 2-----------------------------------------");
        Applic.getListeJoueurCompatibleDansPoste(bdd);
        System.out.println("---------------------------REQUÊTE 3-----------------------------------------");
        Applic.getJoueurNationnalite(bdd, "Portugal");
        System.out.println("---------------------------REQUÊTE 4-----------------------------------------");
        Applic.getJoueurChampionnatEtNationnalite(bdd,"France", "Ligue 1", "Attaquant");
        // close bdd
        bdd.close();
    }
}
