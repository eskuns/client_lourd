package modele;

public class Ligue {
    private int idLigue;
    private String nom;
    private String region;

    // Constructeur par défaut
    public Ligue() {
    }

    // Getters et Setters
    public int getIdLigue() {
        return idLigue;
    }

    public void setIdLigue(int idLigue) {
        this.idLigue = idLigue;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
