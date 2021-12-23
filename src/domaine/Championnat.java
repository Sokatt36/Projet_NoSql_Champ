package domaine;

public class Championnat {
    String nom;

    public Championnat(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Championnat{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
