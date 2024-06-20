package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BddConnect {
    private String url;
    private String utilisateur;
    private String motDePasse;
    private String driver;

    public BddConnect(String url, String utilisateur, String motDePasse, String driver) {
        this.url = url;
        this.utilisateur = utilisateur;
        this.motDePasse = motDePasse;
        this.driver = driver;
    }

    // Méthode pour obtenir la connexion à la base de données
    public Connection getMaConnexion() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors du chargement du pilote JDBC", e);
        }
        return DriverManager.getConnection(url, utilisateur, motDePasse);
    }
    
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/lolfr";
        String utilisateur = "root";
        String motDePasse = "";
        String driver = "com.mysql.cj.jdbc.Driver"; // Assurez-vous d'utiliser le bon driver

        BddConnect bddConnect = new BddConnect(url, utilisateur, motDePasse, driver);

        try {
            Connection conn = bddConnect.getMaConnexion();
            System.out.println("Connexion réussie !");
            // Fermez la connexion après utilisation
            conn.close();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
