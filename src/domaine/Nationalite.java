package domaine;

public class Nationalite {
    String nom;

    public Nationalite(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Nationalite{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
