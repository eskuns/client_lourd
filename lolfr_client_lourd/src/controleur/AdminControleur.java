package controleur;

import modele.Admin;
import modele.BddConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminControleur {
    private BddConnect bdd;

    public AdminControleur(BddConnect bdd) {
        this.bdd = bdd;
    }

    public Admin getAdminById(int idAdmin) {
        String query = "SELECT * FROM admin WHERE ID_Admin = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idAdmin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToAdmin(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM admin";
        try (Connection conn = bdd.getMaConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                admins.add(mapResultSetToAdmin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public boolean addAdmin(Admin admin) {
        String query = "INSERT INTO admin (ID_Admin, Prenom, Email, MotDePasse) VALUES (?, ?, ?, ?)";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, admin.getIdAdmin());
            stmt.setString(2, admin.getPrenom());
            stmt.setString(3, admin.getEmail());
            stmt.setString(4, admin.getMotDePasse());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAdmin(Admin admin) {
        String query = "UPDATE admin SET Prenom = ?, Email = ?, MotDePasse = ? WHERE ID_Admin = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, admin.getPrenom());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getMotDePasse());
            stmt.setInt(4, admin.getIdAdmin());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAdmin(int idAdmin) {
        String query = "DELETE FROM admin WHERE ID_Admin = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idAdmin);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setIdAdmin(rs.getInt("ID_Admin"));
        admin.setPrenom(rs.getString("Prenom"));
        admin.setEmail(rs.getString("Email"));
        admin.setMotDePasse(rs.getString("MotDePasse"));
        return admin;
    }
}
