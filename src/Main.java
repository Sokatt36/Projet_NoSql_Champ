import dao.readFile;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        for (String[] data : Objects.requireNonNull(readFile.getClub())){
            System.out.println(data[0] + " " + data[1] + " " + data[2]);
        }
        Bdd bdd= new Bdd();
        bdd.connect();
        //bdd.creerClub("CREATE (n:Joueur {name: 'Alex',prenom :'Santana', pays: 'France', championnat :'BPL'})");
        bdd.creerJoueur("CREATE (n:Joueur {name: 'Seb',prenom :'Jemini', pays: 'France', championnat :'Ligue 1'})");
        bdd.creerRelation("match(p1:Joueur{name:'Seb', prenom:'Jemini'}), (p2:Person{prenom:'Santana', name:'Alex'})" +"create (p1) -[r:MEME_PAYS]-> (p2) return p1, p2,r");
        bdd.close();
    }
}
