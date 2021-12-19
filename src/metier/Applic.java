package metier;

import dao.Bdd;
import dao.ReadFile;
import domaine.Joueur;
import domaine.JoueurAvecRelation;
import jdk.swing.interop.SwingInterOpUtils;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Value;
import org.neo4j.driver.exceptions.NoSuchRecordException;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;

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
    public static void getJoueurNationnalite(Bdd bdd) {
        //Scanner scanId1 = new Scanner(System.in);
        //System.out.println("Entrez une nationnalité ");
        //String nationalite = scanId1.nextLine();
        //todo Ajouter le scanner avec les whiles qui correspondent aux bonnes conditions (Nationnalité)
        //todo remplacer la rqt avec les param reçu du scanner -> b:Pays{nom:'_PARAM_}
        Result rqt = bdd.run("match(a:Joueur)-[r:JOUE_DANS_PAYS]->(b:Pays{nom:'France'}) where a.general>'85' and a.age > '18' return a,b");
        while (rqt.hasNext()) {
            Record rJoueur = rqt.next();
            Value valueJoueur = rJoueur.get("j");
            Joueur j = new Joueur(valueJoueur.get("general").asString(), valueJoueur.get("nom").asString(), valueJoueur.get("prenom").asString(), valueJoueur.get("age").asString());
            JoueurAvecRelation jvr = new JoueurAvecRelation(j, rJoueur.get("jp").get("nom").asString(), rJoueur.get("jc").get("nom").asString(), rJoueur.get("p2").get("nom").asString());
            System.out.println(j);
        }
    }

    public static void getJoueurChampionnatEtNationnalite(Bdd bdd) {
        //Scanner scanId1 = new Scanner(System.in);
        //System.out.println("Entrez une nationnalité ");
        //String nationalite = scanId1.nextLine();
        //todo Ajouter le scanner avec les whiles qui correspondent aux bonnes conditions (Nationnalité et championnat existant)
        //todo trouver les bons champs pour ajouter un joueur avec relation
        //todo remplacer la rqt avec les param reçu du scanner -> b:Pays{nom:'_PARAM_}[..] jc:Championnat{nom : _PARAM'}
        Result rqt = bdd.run("match(j:Joueur)-[r:JOUE_DANS_PAYS]->(jp:Pays{nom:'France'}),(j)-[c:JOUE_DANS_CHAMPIONNAT]->(jc:Championnat{nom:'Premier League'}) return j,r,jp,jc,c");
        while (rqt.hasNext()) {
            Record rJoueur = rqt.next();
            Value valueJoueur = rJoueur.get("j");
            Joueur j = new Joueur(valueJoueur.get("general").asString(), valueJoueur.get("nom").asString(), valueJoueur.get("prenom").asString(), valueJoueur.get("age").asString());
            JoueurAvecRelation jvr = new JoueurAvecRelation(j, rJoueur.get("jp").get("nom").asString(), rJoueur.get("jc").get("nom").asString(), rJoueur.get("p2").get("nom").asString());
            System.out.println(j);
        }
    }



    public static void getListeJoueurCompatibleDansPoste(Bdd bdd){
        Scanner scanId1 = new Scanner(System.in);
        System.out.println("Entrer l'id du joueur ATTENTION n'entrez pas un chiffre entre [995-1023] : ");
        String id1 = scanId1.nextLine();
        while (Integer.parseInt(id1) > 994) {
            scanId1 = new Scanner(System.in);
            System.out.println("ATTENTION n'entrez pas un chiffre entre [995-1023] veuillez ressayer : ");
            id1 = scanId1.nextLine();
        }


        Scanner scanPoste = new Scanner(System.in);
        System.out.println("Entrer un poste [Attaquant, Gardien, Milieu, Defenseur] : ");
        Boolean posteExist = false;
        String poste = scanPoste.nextLine();
        switch (poste){
            case "Attaquant":
                posteExist = true;
                break;
            case "Defenseur":
                posteExist = true;
                break;
            case "Milieu":
                posteExist = true;
                break;
            case "Gardien":
                posteExist = true;
                break;
        }

        while (!posteExist) {
            scanPoste = new Scanner(System.in);
            System.out.println("ATTENTION n'entrez pas un poste inexistant veuillez entrer un de ces postes [Attaquant, Gardien, Milieu, Defenseur] : ");
            poste = scanPoste.nextLine();
            switch (poste){
                case "Attaquant":
                    posteExist = true;
                    break;
                case "Defenseur":
                    posteExist = true;
                    break;
                case "Milieu":
                    posteExist = true;
                    break;
                case "Gardien":
                    posteExist = true;
                    break;
            }
        }
        Result rqt = bdd.run("match(j:Joueur) -[:JOUE_DANS_POSTE]-> (p2:Poste)," +
                "(j) -[:JOUE_DANS_PAYS]-> (jp:Pays)," +
                "(j) -[:JOUE_DANS_CHAMPIONNAT]-> (jc:Championnat)," +
                "(j2:Joueur) -[:JOUE_DANS_POSTE]-> (p:Poste{nom:\""+ poste +"\"})," +
                "(j2) -[:JOUE_DANS_PAYS]-> (jp2:Pays)," +
                "(j2) -[:JOUE_DANS_CHAMPIONNAT]-> (jc2:Championnat)," +
                "path = shortestPath((j)-[*]-(j2)) " +
                "where ID(j) = "+ id1 +
                " return j,path,p,p2,jp,jc,jp2,jc2" +
                " order by (j2.general) desc" +
                " limit 18");
        Record rJoueur = rqt.next();
        Value valueJoueur = rJoueur.get("j");

        Joueur j = new Joueur(valueJoueur.get("general").asString(), valueJoueur.get("nom").asString(), valueJoueur.get("prenom").asString(), valueJoueur.get("age").asString());
        JoueurAvecRelation jvr = new JoueurAvecRelation(j, rJoueur.get("jp").get("nom").asString(), rJoueur.get("jc").get("nom").asString(), rJoueur.get("p2").get("nom").asString());

        System.out.println(j.getNom() + " " + j.getPrenom() + " souhaite trouver un " + poste + ". Voici les différents chemin afin d'accéder aux différentes possibilités : \n");
        while (rqt.hasNext()) {
            Record rJoueur2 = rqt.next();
            Path path = rJoueur2.get("path").asPath();

            System.out.print("Chemin : ");
            for (Node node : path.nodes()) {
                System.out.print(node.get("nom").asString() + " --> ");
            }

            if(jvr.getChampionnat().equals(rJoueur2.get("jc2").get("nom").asString()) && jvr.getNationnalite().equals(rJoueur2.get("jp2").get("nom").asString())){
                System.out.print("Parfait ! Lien vert, ils jouent pour le même pays et le même championnat");
            }else if (jvr.getNationnalite().equals(rJoueur2.get("jp2").get("nom").asString())){
                System.out.print("Pas mal ! Lien jaune car ils ont la même nationnalité mais pas le même championnat.");
            }else if(jvr.getChampionnat().equals(rJoueur2.get("jc2").get("nom").asString())){
                System.out.print("Pas mal ! Lien jaune car ils ont le même championnat mais pas la même nationnalité.");
            }else{
                System.out.print("Aïe... Lien rouge. Ni le même championnat, ni le même pays.");
            }
            System.out.println();
        }
    }

}
