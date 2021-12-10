package dao;

import org.neo4j.driver.*;

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


    public void creerJoueur(String... data){
        run("CREATE (n:Joueur {nom: '"+data[0]+"',prenom :'"+data[1]+"', age: '"+data[2]+"', general :'"+data[3]+"'})");
    }
    public void creerPays(String data){
        run("CREATE (n:Pays {nom: '"+data+"'})");
    }
    public void creerChampionnat(String data){
        run("CREATE (n:Championnat {nom: '"+data+"'})");
    }
    public void deleteJoueur(){
        run("match(a:Joueur) detach delete a");
    }
    public void creerRelationJoueurNationnalite(String pays){
        run("match(a:Joueur {pays: '"+pays+"'}),(b:Joueur {pays: '"+pays+"'}) where id(b)<>id(a) create (a)-[r:MEME_NATIONALITE]->(b) return a,b,r");
    }
    public void creerRelationJoueurChampionnat(String championnat){
        run("match(a:Joueur {championnat: '"+championnat+"'}),(b:Joueur {championnat: '"+championnat+"'}) where id(b)<>id(a) create (a)-[r:MEME_CHAMPIONNAT]->(b) return a,b,r");
    }
    public void deleteRelationJoueurNationnalite(){
        run("MATCH (n:Joueur)-[r:MEME_NATIONALITE]->() delete r");
        close();
    }
    public void deleteRalationJoueurChampionnat(){
        run("MATCH (n:Joueur)-[r:MEME_CHAMPIONNAT]->() delete r");
        close();

    }


}
