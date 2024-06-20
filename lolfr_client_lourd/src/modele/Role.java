package modele;

public class Role {
    private int idRole;
    private String libelle;

    // Constructeur par défaut
    public Role() {
    }

    // Getters et Setters
    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNom() {
        return libelle;
    }
}

