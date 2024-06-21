package controleur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.DbConnect;
import modele.Compte;

public class CompteController {

    private DbConnect dbConnect;

    public CompteController() {
        dbConnect = new DbConnect();
    }

    public boolean ajouterCompte(Compte compte) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "INSERT INTO compte (pseudo, mdp, email) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, compte.getPseudo());
                statement.setString(2, compte.getMdp());
                statement.setString(3, compte.getEmail());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        compte.setIdCompte(generatedKeys.getInt(1));
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

    public boolean modifierCompte(Compte compte) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "UPDATE compte SET pseudo=?, mdp=?, email=? WHERE id_compte=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, compte.getPseudo());
                statement.setString(2, compte.getMdp());
                statement.setString(3, compte.getEmail());
                statement.setInt(4, compte.getIdCompte());

                int rowsUpdated = statement.executeUpdate();
                statement.close();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean supprimerCompte(int idCompte) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "DELETE FROM compte WHERE id_compte=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, idCompte);

                int rowsDeleted = statement.executeUpdate();
                statement.close();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Compte> listerComptes() {
        List<Compte> comptes = new ArrayList<>();
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM compte";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Compte compte = new Compte();
                    compte.setIdCompte(resultSet.getInt("id_compte"));
                    compte.setPseudo(resultSet.getString("pseudo"));
                    compte.setMdp(resultSet.getString("mdp"));
                    compte.setEmail(resultSet.getString("email"));

                    comptes.add(compte);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return comptes;
    }

}
