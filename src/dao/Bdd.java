package dao;

import org.neo4j.driver.*;

import java.util.*;

public class Bdd {
    private Driver driver;
    private Session session;
    public void connect(){
        Driver driver =  GraphDatabase.driver("neo4j+s://50878d8c.databases.neo4j.io", AuthTokens.basic("neo4j","BciHCOaxbCa9dzzTBq3JHoM76YVSVwLHYFpWItNruNg"));
       this.driver = driver;
        this.session = driver.session();
    }
    public void close() {
        session.close();
        driver.close();
    }

    public Result run(String insrt) {
        return session.run(insrt);
    }

    public String getRandomChamp(){
        Random rd = new Random();
        String[] lst = ReadFile.getChampionnats().toArray(new String[ReadFile.getChampionnats().size()]);
        int random = rd.nextInt(9);
        return lst[random];
    }

    public String getRandomPays(){
        Random rd = new Random();
        String[] lst = ReadFile.getPays().toArray(new String[ReadFile.getPays().size()]);
        int random = rd.nextInt(20);
        return lst[random];
    }

    public void creerJoueur(String... data){
        run("CREATE (n:Joueur {nom: '"+data[0]+"',prenom :'"+data[1]+"', age: '"+data[2]+"', general :'"+data[3]+"'})");
    }

    public void creerPays(String data){
        run("CREATE (n:Pays {nom: '"+data+"'})");
    }

    public void creerChampionnat(String data){
        run("CREATE (n:Championnat {nom: '"+data+"'})");
    }

    public void deleteBase(){
        run("match(a) detach delete a");
    }

    public void deleteJoueur(){
        run("match(a:Joueur) detach delete a");
    }

    public void creerRelationJoueurNationnalite(String ...data){
        run("match(a:Joueur{nom: '"+data[0]+"',prenom :'"+data[1]+"', age: '"+data[2]+"', general :'"+data[3]+"'}),(b:Pays{nom:'"+getRandomPays()+"'})create (a)-[r:JOUE_DANS_PAYS]->(b) return a,b,r ");
    }

    public void creerRelationJoueurChampionnat(String...data){
        run("match(a:Joueur{nom: '"+data[0]+"',prenom :'"+data[1]+"', age: '"+data[2]+"', general :'"+data[3]+"'}),(b:Championnat{nom:'"+getRandomChamp()+"'})create (a)-[r:JOUE_DANS_CHAMPIONNAT]->(b) return a,b,r ");
    }

}
