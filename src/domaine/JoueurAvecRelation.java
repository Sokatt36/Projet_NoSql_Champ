package domaine;

public class JoueurAvecRelation {

    Joueur joueur;
    String nationnalite;
    String championnat;
    String poste;

    public JoueurAvecRelation(Joueur joueur, String nationnalite, String championnat, String poste) {
        this.joueur = joueur;
        this.nationnalite = nationnalite;
        this.championnat = championnat;
        this.poste = poste;
    }

    public String getNationnalite() {
        return nationnalite;
    }

    public String getChampionnat() {
        return championnat;
    }

    public String getPoste() {
        return poste;
    }

    public String getGeneral() {
        return joueur.getGeneral();
    }

    public String getNom() {
        return joueur.getNom();
    }

    public String getPrenom() {
        return joueur.getPrenom();
    }

    public String getAge() {
        return joueur.getAge();
    }

    @Override
    public String toString() {
        return "JoueurAvecRelation{" +
                "joueur=" + joueur.toString() +
                ", nationnalite='" + nationnalite + '\'' +
                ", championnat='" + championnat + '\'' +
                ", poste='" + poste + '\'' +
                '}';
    }
}
