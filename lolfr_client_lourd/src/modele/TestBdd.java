package modele;

import java.sql.Connection;
import java.sql.SQLException;

public class TestBdd {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/lolfr";
        String utilisateur = "root";
        String motDePasse = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        BddConnect bddConnect = new BddConnect(url, utilisateur, motDePasse, driver);

        try {
            Connection conn = bddConnect.getMaConnexion();
            System.out.println("Connexion réussie !");
            // Utilisation de la connexion ici
            conn.close(); // Fermeture de la connexion
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
