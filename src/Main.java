import dao.Bdd;

public class Main {
    public static void main(String[] args) {
        //Connection à la bdd
        Bdd bdd = new Bdd();
        bdd.connect();
        bdd.deleteBase();
        // Création des noeuds et des relations
        metier.Applic.creerBdd(bdd);
        // close bdd
        bdd.close();
    }
}
