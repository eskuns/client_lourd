package modele;

public class Compte {
    private int idCompte;
    private String pseudo;
    private String mdp;
    private String email;

    public Compte() {
    }

    public Compte(int idCompte, String pseudo, String mdp, String email) {
        this.idCompte = idCompte;
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.email = email;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}