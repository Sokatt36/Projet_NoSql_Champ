package domaine;

public class JoueurAvecRelation {

    Joueur joueur;
    Nationalite nationnalite;
    Championnat championnat;
    Poste poste;

    public JoueurAvecRelation(Joueur joueur, Nationalite nationnalite, Championnat championnat, Poste poste) {
        this.joueur = joueur;
        this.nationnalite = nationnalite;
        this.championnat = championnat;
        this.poste = poste;
    }

    public Nationalite getNationnalite() {
        return nationnalite;
    }

    public Championnat getChampionnat() {
        return championnat;
    }

    public Poste getPoste() {
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
                "joueur=" + joueur +
                ", nationnalite='" + nationnalite + '\'' +
                ", championnat='" + championnat + '\'' +
                ", poste='" + poste + '\'' +
                '}';
    }
}
