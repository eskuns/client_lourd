package controleur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.DbConnect;
import modele.Role;

public class RoleController {

    private DbConnect dbConnect;

    public RoleController() {
        dbConnect = new DbConnect();
    }

    public boolean ajouterRole(Role role) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "INSERT INTO role (libelle) VALUES (?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, role.getLibelle());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        role.setIdRole(generatedKeys.getInt(1));
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

    public boolean modifierRole(Role role) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "UPDATE role SET libelle=? WHERE id_role=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, role.getLibelle());
                statement.setInt(2, role.getIdRole());

                int rowsUpdated = statement.executeUpdate();
                statement.close();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean supprimerRole(int idRole) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "DELETE FROM role WHERE id_role=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, idRole);

                int rowsDeleted = statement.executeUpdate();
                statement.close();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Role> listerRoles() {
        List<Role> roles = new ArrayList<>();
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM role";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Role role = new Role();
                    role.setIdRole(resultSet.getInt("id_role"));
                    role.setLibelle(resultSet.getString("libelle"));

                    roles.add(role);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return roles;
    }

    // Vous pouvez ajouter d'autres méthodes selon vos besoins

}
