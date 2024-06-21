package controleur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.DbConnect;
import modele.Joueur;

public class JoueurController {

    private DbConnect dbConnect;

    public JoueurController() {
        dbConnect = new DbConnect();
    }

    public boolean ajouterJoueur(Joueur joueur) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "INSERT INTO joueur (pseudo, prenom, nom, nationalite, id_equipe, id_role) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, joueur.getPseudo());
                statement.setString(2, joueur.getPrenom());
                statement.setString(3, joueur.getNom());
                statement.setString(4, joueur.getNationalite());
                statement.setInt(5, joueur.getIdEquipe());
                statement.setInt(6, joueur.getIdRole());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        joueur.setIdJoueur(generatedKeys.getInt(1));
                    }
                    generatedKeys.close();
                    return true;
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean modifierJoueur(Joueur joueur) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "UPDATE joueur SET pseudo=?, prenom=?, nom=?, nationalite=?, " +
                        "id_equipe=?, id_role=? WHERE id_joueur=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, joueur.getPseudo());
                statement.setString(2, joueur.getPrenom());
                statement.setString(3, joueur.getNom());
                statement.setString(4, joueur.getNationalite());
                statement.setInt(5, joueur.getIdEquipe());
                statement.setInt(6, joueur.getIdRole());
                statement.setInt(7, joueur.getIdJoueur());

                int rowsUpdated = statement.executeUpdate();
                statement.close();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean supprimerJoueur(int idJoueur) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "DELETE FROM joueur WHERE id_joueur=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, idJoueur);

                int rowsDeleted = statement.executeUpdate();
                statement.close();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Joueur> listerJoueurs() {
        List<Joueur> joueurs = new ArrayList<>();
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM joueur";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Joueur joueur = new Joueur();
                    joueur.setIdJoueur(resultSet.getInt("id_joueur"));
                    joueur.setPseudo(resultSet.getString("pseudo"));
                    joueur.setPrenom(resultSet.getString("prenom"));
                    joueur.setNom(resultSet.getString("nom"));
                    joueur.setNationalite(resultSet.getString("nationalite"));
                    joueur.setIdEquipe(resultSet.getInt("id_equipe"));
                    joueur.setIdRole(resultSet.getInt("id_role"));

                    joueurs.add(joueur);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return joueurs;
    }


}
