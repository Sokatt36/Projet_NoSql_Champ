package domaine;

public class Joueur {
    private String general;
    private String nom;
    private String prenom;
    private String age;

    public Joueur(String general, String nom, String prenom, String age) {
        this.general = general;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public String getGeneral() {
        return general;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAge() {
        return age;
    }
}
