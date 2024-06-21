package controleur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.Coach;
import modele.DbConnect;

public class CoachController {

    private DbConnect dbConnect;

    public CoachController() {
        dbConnect = new DbConnect();
    }

    public boolean ajouterCoach(Coach coach) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "INSERT INTO coach (pseudo, prenom, nom) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, coach.getPseudo());
                statement.setString(2, coach.getPrenom());
                statement.setString(3, coach.getNom());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        coach.setIdCoach(generatedKeys.getInt(1));
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

    public boolean modifierCoach(Coach coach) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "UPDATE coach SET pseudo=?, prenom=?, nom=? WHERE id_coach=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, coach.getPseudo());
                statement.setString(2, coach.getPrenom());
                statement.setString(3, coach.getNom());
                statement.setInt(4, coach.getIdCoach());

                int rowsUpdated = statement.executeUpdate();
                statement.close();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean supprimerCoach(int idCoach) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "DELETE FROM coach WHERE id_coach=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, idCoach);

                int rowsDeleted = statement.executeUpdate();
                statement.close();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Coach> listerCoachs() {
        List<Coach> coachs = new ArrayList<>();
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM coach";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Coach coach = new Coach();
                    coach.setIdCoach(resultSet.getInt("id_coach"));
                    coach.setPseudo(resultSet.getString("pseudo"));
                    coach.setPrenom(resultSet.getString("prenom"));
                    coach.setNom(resultSet.getString("nom"));

                    coachs.add(coach);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return coachs;
    }

    // Vous pouvez ajouter d'autres méthodes selon vos besoins

}
