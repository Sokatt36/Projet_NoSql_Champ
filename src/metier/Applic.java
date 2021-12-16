package metier;

import dao.Bdd;
import dao.ReadFile;
import domaine.Joueur;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;

import java.util.Objects;
import java.util.Scanner;

public class Applic {

    public static void creerPays(Bdd bdd) {
        for (String data : ReadFile.getPays()) {
//            System.out.println(data);
            bdd.creerPays(data);
        }
    }

    public static void creerChampionnat(Bdd bdd){
        for (String data: ReadFile.getChampionnats()) {
//            System.out.println(data);
            bdd.creerChampionnat(data);
        }
    }
    public static void creerPoste(Bdd bdd){
        for (String data: ReadFile.getPoste()) {
//            System.out.println(data);
            bdd.creerPoste(data);
        }
    }


    public static void creerJoueurs(Bdd bdd){
        for (String[] data : ReadFile.getJoueurs()){
//            System.out.println(data[0] + " " + data[1] + " " + data[2]);
            bdd.creerJoueur(data);
        }
    }

    public static void creerRelations(Bdd bdd){
        for (String[] data : Objects.requireNonNull(ReadFile.getJoueurs())){
            bdd.creerRelationJoueurNationnalite(data);
            bdd.creerRelationJoueurChampionnat(data);
            bdd.creerRelationJoueurPoste(data);
        }
    }
    public  static void creerBdd(Bdd bdd){
        creerJoueurs(bdd);
        creerPays(bdd);
        creerChampionnat(bdd);
        creerPoste(bdd);
        creerRelations(bdd);
    }

    public static String getMeilleurJoueur(Bdd bdd){
        Scanner scanId1 = new Scanner(System.in);
        System.out.println("Entrer le premier id de joueur ATTENTION n'entrez pas un chiffre entre [995-1023] : ");
        String id1 = scanId1.nextLine();
        while (Integer.parseInt(id1) > 994) {
            scanId1 = new Scanner(System.in);
            System.out.println("ATTENTION n'entrez pas un chiffre entre [995-1023] veuillez ressayer : ");
            id1 = scanId1.nextLine();
        }


        Scanner scanId2 = new Scanner(System.in);
        System.out.println("Entrer le deuxième id ATTENTION n'entrez pas un chiffre entre [995-1023] : ");
        String id2 = scanId2.nextLine();
        while (Integer.parseInt(id2) > 994) {
            scanId2 = new Scanner(System.in);
            System.out.println("ATTENTION n'entrez pas un chiffre entre [995-1023] veuillez ressayer : ");
            id2 = scanId2.nextLine();
        }

        Result rqt = bdd.run("match(p:Joueur)-[:JOUE_DANS_PAYS]->(a:Pays)," +
                "(p2:Joueur)-[:JOUE_DANS_CHAMPIONNAT]->(a2:Championnat)," +
                "(p3:Joueur) -[:JOUE_DANS_PAYS]->(a3:Pays{nom:a.nom}), (p3)-[:JOUE_DANS_CHAMPIONNAT]-> (a4:Championnat{nom:a2.nom}) " +
                "where ID(p) = "+ id1 + " AND ID(p2) = " + id2 +
                " return p3 " +
                "order by (p3.general) desc " +
                "limit 1");

        String rows = "";

        while (rqt.hasNext()){
            Record ligne = rqt.next();
            Joueur j = new Joueur(bdd.enleverGuillemet(ligne.get(0).get("general").toString()), bdd.enleverGuillemet(ligne.get(0).get("nom").toString()),bdd.enleverGuillemet(ligne.get(0).get("prenom").toString()), bdd.enleverGuillemet(ligne.get(0).get("age").toString()));
            rows = "Le meilleur joueur pour collaborer est " + j.getNom() + " " + j.getPrenom() + ". Ce joueur/joueuse a comme général " + j.getGeneral() + " et a " + j.getAge() + " ans.";
        }
        return rows;
    }


}
