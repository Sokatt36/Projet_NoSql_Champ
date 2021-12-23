package domaine;

public class Poste {
    String nom;

    public Poste(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Poste{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
