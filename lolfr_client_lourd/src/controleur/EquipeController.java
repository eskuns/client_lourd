package controleur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.DbConnect;
import modele.Equipe;

public class EquipeController {

    private DbConnect dbConnect;

    public EquipeController() {
        dbConnect = new DbConnect();
    }

    public boolean ajouterEquipe(Equipe equipe) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "INSERT INTO equipe (nom, victoire, defaite, id_ligue, id_coach) " +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, equipe.getNom());
                statement.setInt(2, equipe.getVictoire());
                statement.setInt(3, equipe.getDefaite());
                statement.setInt(4, equipe.getIdLigue());
                statement.setInt(5, equipe.getIdCoach());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        equipe.setIdEquipe(generatedKeys.getInt(1));
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

    public boolean modifierEquipe(Equipe equipe) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "UPDATE equipe SET nom=?, victoire=?, defaite=?, " +
                        "id_ligue=?, id_coach=? WHERE id_equipe=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, equipe.getNom());
                statement.setInt(2, equipe.getVictoire());
                statement.setInt(3, equipe.getDefaite());
                statement.setInt(4, equipe.getIdLigue());
                statement.setInt(5, equipe.getIdCoach());
                statement.setInt(6, equipe.getIdEquipe());

                int rowsUpdated = statement.executeUpdate();
                statement.close();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean supprimerEquipe(int idEquipe) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "DELETE FROM equipe WHERE id_equipe=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, idEquipe);

                int rowsDeleted = statement.executeUpdate();
                statement.close();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Equipe> listerEquipes() {
        List<Equipe> equipes = new ArrayList<>();
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM equipe";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Equipe equipe = new Equipe();
                    equipe.setIdEquipe(resultSet.getInt("id_equipe"));
                    equipe.setNom(resultSet.getString("nom"));
                    equipe.setVictoire(resultSet.getInt("victoire"));
                    equipe.setDefaite(resultSet.getInt("defaite"));
                    equipe.setIdLigue(resultSet.getInt("id_ligue"));
                    equipe.setIdCoach(resultSet.getInt("id_coach"));

                    equipes.add(equipe);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return equipes;
    }


}
	