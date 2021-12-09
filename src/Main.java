import dao.Bdd;

public class Main {
    public static void main(String[] args) {
        Bdd bdd= new Bdd();
        bdd.connect();
        bdd.creerClub("CREATE (n:Test3 {name: 'Andy3', title: 'Developer3'})");
        bdd.close();
    }
}
