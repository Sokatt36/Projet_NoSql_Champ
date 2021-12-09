package dao;

import org.neo4j.driver.*;

public class Bdd {
    private Driver driver;
    private Session session;
    public void connect(){
        Driver driver =  GraphDatabase.driver("neo4j+s://50878d8c.databases.neo4j.io", AuthTokens.basic("neo4j","BciHCOaxbCa9dzzTBq3JHoM76YVSVwLHYFpWItNruNg"));
       this.driver = driver;
        Session session = driver.session();
        this.session = session;
    }
    public void close() {
        session.close();
        driver.close();
    }

    public Result run(String insrt) {
        return session.run(insrt);
    }

    public void creerClub(String data){
        run(data);

    }


}
