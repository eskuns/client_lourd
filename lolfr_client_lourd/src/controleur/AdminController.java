package controleur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.DbConnect;
import modele.Admin;

public class AdminController {

    private DbConnect dbConnect;

    public AdminController() {
        dbConnect = new DbConnect();
    }

    public boolean ajouterAdmin(Admin admin) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "INSERT INTO admin (prenom, email, mdp) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, admin.getPrenom());
                statement.setString(2, admin.getEmail());
                statement.setString(3, admin.getMdp());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        admin.setIdAdmin(generatedKeys.getInt(1));
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

    public boolean modifierAdmin(Admin admin) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "UPDATE admin SET prenom=?, email=?, mdp=? WHERE id_admin=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, admin.getPrenom());
                statement.setString(2, admin.getEmail());
                statement.setString(3, admin.getMdp());
                statement.setInt(4, admin.getIdAdmin());

                int rowsUpdated = statement.executeUpdate();
                statement.close();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean supprimerAdmin(int idAdmin) {
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "DELETE FROM admin WHERE id_admin=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, idAdmin);

                int rowsDeleted = statement.executeUpdate();
                statement.close();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Admin> listerAdmins() {
        List<Admin> admins = new ArrayList<>();
        Connection connection = dbConnect.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM admin";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Admin admin = new Admin();
                    admin.setIdAdmin(resultSet.getInt("id_admin"));
                    admin.setPrenom(resultSet.getString("prenom"));
                    admin.setEmail(resultSet.getString("email"));
                    admin.setMdp(resultSet.getString("mdp"));

                    admins.add(admin);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return admins;
    }


}
