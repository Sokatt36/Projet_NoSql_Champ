package metier;

import dao.Bdd;
import dao.ReadFile;
import domaine.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;

import java.util.Objects;
import java.util.Scanner;

public class Applic {

    public static void creerPays(Bdd bdd) {
        for (String data : ReadFile.getPays()) {
            bdd.creerPays(data);
        }
    }

    public static void creerChampionnat(Bdd bdd){
        for (String data: ReadFile.getChampionnats()) {
            bdd.creerChampionnat(data);
        }
    }
    public static void creerPoste(Bdd bdd){
        for (String data: ReadFile.getPoste()) {
            bdd.creerPoste(data);
        }
    }


    public static void creerJoueurs(Bdd bdd){
        for (String[] data : ReadFile.getJoueurs()){
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

    // L'utilisateur choisi deux joueur, un poste et le programme retourne un joueur compatible avec les deux autres au poste choisi. (Même championnat ou même nationnalité)
    public static void getMeilleurJoueur(Bdd bdd){
        Scanner scanId1 = new Scanner(System.in);
        System.out.println("Entrer le premier id de joueur ATTENTION n'entrez pas un chiffre entre [983-1015] ou plus grand que 1037 : ");
        String id1 = scanId1.nextLine();
        while (Integer.parseInt(id1) > 982 && Integer.parseInt(id1) < 1016 || Integer.parseInt(id1) > 1037) {
            scanId1 = new Scanner(System.in);
            System.out.println("ATTENTION n'entrez pas un chiffre entre [983-1015] ou plus grand que 1037 veuillez ressayer : ");
            id1 = scanId1.nextLine();
        }


        Scanner scanId2 = new Scanner(System.in);
        System.out.println("Entrer le deuxième id ATTENTION n'entrez pas un chiffre entre [983-1015] ou plus grand que 1037 : ");
        String id2 = scanId2.nextLine();
        while (Integer.parseInt(id2) > 982 && Integer.parseInt(id2) < 1016 || Integer.parseInt(id2) > 1037) {
            scanId2 = new Scanner(System.in);
            System.out.println("ATTENTION n'entrez pas un chiffre entre [983-1015] ou plus grand que 1037 veuillez ressayer : ");
            id2 = scanId2.nextLine();
        }

        Scanner scanPoste = new Scanner(System.in);
        System.out.println("Entrer un poste (Ne pas oublier la majuscule) [Attaquant, Gardien, Milieu, Defenseur] : ");
        boolean posteExist = false;
        String poste = scanPoste.nextLine();
        switch (poste) {
            case "Attaquant" -> posteExist = true;
            case "Defenseur" -> posteExist = true;
            case "Milieu" -> posteExist = true;
            case "Gardien" -> posteExist = true;
        }

        while (!posteExist) {
            scanPoste = new Scanner(System.in);
            System.out.println("ATTENTION n'entrez pas un poste inexistant veuillez entrer un de ces postes (Ne pas oublier la majuscule) [Attaquant, Gardien, Milieu, Defenseur] : ");
            poste = scanPoste.nextLine();
            posteExist = switch (poste) {
                case "Attaquant" -> true;
                case "Defenseur" -> true;
                case "Milieu" -> true;
                case "Gardien" -> true;
                default -> false;
            };
        }

        Result rqt = bdd.run("match(p:Joueur)-[:JOUE_DANS_PAYS]->(a:Pays)," +
                "(p2:Joueur)-[:JOUE_DANS_CHAMPIONNAT]->(a2:Championnat)," +
                "(p3:Joueur) -[:JOUE_DANS_PAYS]->(a3:Pays{nom:a.nom}), (p3)-[:JOUE_DANS_CHAMPIONNAT]-> (a4:Championnat{nom:a2.nom}), (p3)-[:JOUE_DANS_POSTE]-> (a5:Poste{nom:\""+ poste +"\"}) " +
                "where ID(p) = "+ id1 + " AND ID(p2) = " + id2 +
                " return p,a,p2,a2,p3,a4,a3,a5 " +
                "order by (p3.general) desc " +
                "limit 1");

        while (rqt.hasNext()){
            Record ligne = rqt.next();
            Joueur j = new Joueur(ligne.get("p3").get("general").asString(), ligne.get("p3").get("nom").asString(),ligne.get("p3").get("prenom").asString(), ligne.get("p3").get("age").asString());
            Joueur j2 = new Joueur(ligne.get("p").get("general").asString(), ligne.get("p").get("nom").asString(),ligne.get("p").get("prenom").asString(), ligne.get("p").get("age").asString());
            Joueur j3 = new Joueur(ligne.get("p2").get("general").asString(), ligne.get("p2").get("nom").asString(),ligne.get("p2").get("prenom").asString(), ligne.get("p2").get("age").asString());

            System.out.println("Joueur numéro 1 : " + j2.getNom() + " " + j2.getPrenom() + ". Nationnalité : " + ligne.get("a").get("nom").asString());
            System.out.println("Joueur numéro 2 : " + j3.getNom() + " " + j3.getPrenom() + ". Championnat : " + ligne.get("a2").get("nom").asString());
            System.out.println("Le meilleur joueur pour collaborer est " + j.getNom() + " " + j.getPrenom() + ". Ce joueur/joueuse a comme général " + j.getGeneral() + " et a " + j.getAge() + " ans. Nationnalité : " + ligne.get("a3").get("nom").asString() + ". Championnat : " + ligne.get("a4").get("nom").asString() + ". Poste : " + ligne.get("a5").get("nom").asString());
        }
    }

    // Récupère une liste des joueurs disponible à un poste spécifique. Retourne les chemins et le type de lien entre les joueurs récupéré et le joueur choisi par l'utilisateur
    public static void getListeJoueurCompatibleDansPoste(Bdd bdd){
        Scanner scanId1 = new Scanner(System.in);
        System.out.println("Entrer l'id du joueur ATTENTION n'entrez pas un chiffre entre [983-1015] ou plus grand que 1037 : ");
        String id1 = scanId1.nextLine();
        while (Integer.parseInt(id1) > 982 && Integer.parseInt(id1) < 1016 || Integer.parseInt(id1) > 1037) {
            scanId1 = new Scanner(System.in);
            System.out.println("ATTENTION n'entrez pas un chiffre entre [983-1015] ou plus grand que 1037 veuillez ressayer : ");
            id1 = scanId1.nextLine();
        }


        Scanner scanPoste = new Scanner(System.in);
        System.out.println("Entrer un poste (Ne pas oublier la majuscule) [Attaquant, Gardien, Milieu, Defenseur] : ");
        boolean posteExist = false;
        String poste = scanPoste.nextLine();
        switch (poste) {
            case "Attaquant" -> posteExist = true;
            case "Defenseur" -> posteExist = true;
            case "Milieu" -> posteExist = true;
            case "Gardien" -> posteExist = true;
        }

        while (!posteExist) {
            scanPoste = new Scanner(System.in);
            System.out.println("ATTENTION n'entrez pas un poste inexistant veuillez entrer un de ces postes (Ne pas oublier la majuscule) [Attaquant, Gardien, Milieu, Defenseur] : ");
            poste = scanPoste.nextLine();
            posteExist = switch (poste) {
                case "Attaquant" -> true;
                case "Defenseur" -> true;
                case "Milieu" -> true;
                case "Gardien" -> true;
                default -> false;
            };
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
        Nationalite n = new Nationalite(rJoueur.get("jp").get("nom").asString());
        Poste p = new Poste(rJoueur.get("p2").get("nom").asString());
        Championnat c = new Championnat(rJoueur.get("jc").get("nom").asString());

        JoueurAvecRelation jvr = new JoueurAvecRelation(j, n, c, p);

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

    //Affiche tous les joueurs d'une nationnalité spécifique, d'une tranche d'age spécifique et un général spécifique
    public static void getJoueurNationnalite(Bdd bdd, String pays) {
        Result rqt = bdd.run("match(a:Joueur)-[r:JOUE_DANS_PAYS]->(b:Pays{nom:\""+pays+"\"}) where a.general>'85' and a.age > '18' return a,b");
        while (rqt.hasNext()) {
            Record rJoueur = rqt.next();
            Value valueJoueur = rJoueur.get("a");
            Joueur j = new Joueur(valueJoueur.get("general").asString(), valueJoueur.get("nom").asString(), valueJoueur.get("prenom").asString(), valueJoueur.get("age").asString());
            System.out.println(j);
        }
    }

    // Affiche tous les joueurs d'une nationalité spécifique, d'un championnat spécifique et à un poste spécifique
    public static void getJoueurChampionnatEtNationnalite(Bdd bdd, String pays, String champ, String poste) {
        Result rqt = bdd.run("match(j:Joueur)-[r:JOUE_DANS_PAYS]->(jp:Pays{nom:\""+pays+"\"})," +
                "(j)-[c:JOUE_DANS_CHAMPIONNAT]->(jc:Championnat{nom:\""+champ+"\"}), " +
                "(j)-[p:JOUE_DANS_POSTE]->(p2:Poste{nom:\""+poste+"\"}) " +
                "return j,r,jp,jc,c,p2");
        while (rqt.hasNext()) {
            Record rJoueur = rqt.next();
            Value valueJoueur = rJoueur.get("j");
            Joueur j = new Joueur(valueJoueur.get("general").asString(), valueJoueur.get("nom").asString(), valueJoueur.get("prenom").asString(), valueJoueur.get("age").asString());
            Nationalite n = new Nationalite(rJoueur.get("jp").get("nom").asString());
            Poste p = new Poste(rJoueur.get("p2").get("nom").asString());
            Championnat c = new Championnat(rJoueur.get("jc").get("nom").asString());
            JoueurAvecRelation jvr = new JoueurAvecRelation(j, n, c, p);
            System.out.println(jvr);
        }
    }



}
