package controleur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.DbConnect;
import modele.Ligue;

public class LigueController {

    private DbConnect dbConnect;

    public LigueController() {
        dbConnect = new DbConnect();
    }

    public boolean ajouterLigue(Ligue ligue) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "INSERT INTO ligue (nom, region) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, ligue.getNom());
                statement.setString(2, ligue.getRegion());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        ligue.setIdLigue(generatedKeys.getInt(1));
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

    public boolean modifierLigue(Ligue ligue) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "UPDATE ligue SET nom=?, region=? WHERE id_ligue=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, ligue.getNom());
                statement.setString(2, ligue.getRegion());
                statement.setInt(3, ligue.getIdLigue());

                int rowsUpdated = statement.executeUpdate();
                statement.close();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean supprimerLigue(int idLigue) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "DELETE FROM ligue WHERE id_ligue=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, idLigue);

                int rowsDeleted = statement.executeUpdate();
                statement.close();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Ligue> listerLigues() {
        List<Ligue> ligues = new ArrayList<>();
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM ligue";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Ligue ligue = new Ligue();
                    ligue.setIdLigue(resultSet.getInt("id_ligue"));
                    ligue.setNom(resultSet.getString("nom"));
                    ligue.setRegion(resultSet.getString("region"));

                    ligues.add(ligue);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ligues;
    }

    // Vous pouvez ajouter d'autres méthodes selon vos besoins

}
