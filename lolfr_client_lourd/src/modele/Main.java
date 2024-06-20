package modele;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Obtenir une connexion à la base de données
            connection = DbCon.getConnection();
            System.out.println("Connexion réussie !");

            // Créer un objet statement pour exécuter des requêtes SQL
            statement = connection.createStatement();
            
            // Définir la requête SQL
            String query = "SELECT * FROM joueur";

            // Exécuter la requête et obtenir les résultats
            resultSet = statement.executeQuery(query);

            // Parcourir les résultats
            while (resultSet.next()) {
                int id = resultSet.getInt("id_joueur");
                String nom = resultSet.getString("nom");
                // Ajouter d'autres colonnes selon vos besoins

                // Afficher les résultats
                System.out.println("ID: " + id + ", Nom: " + nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer les ressources dans le bloc finally pour garantir leur fermeture
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
	