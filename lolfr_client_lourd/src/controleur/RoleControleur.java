package controleur;

import modele.Role;
import modele.BddConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleControleur {
    private BddConnect bdd;

    public RoleControleur(BddConnect bdd) {
        this.bdd = bdd;
    }

    public Role getRoleById(int idRole) {
        String query = "SELECT * FROM role WHERE id_role = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idRole);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToRole(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT * FROM role";
        try (Connection conn = bdd.getMaConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                roles.add(mapResultSetToRole(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public boolean addRole(Role role) {
        String query = "INSERT INTO role (id_role, libelle) VALUES (?, ?)";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, role.getIdRole());
            stmt.setString(2, role.getLibelle());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRole(Role role) {
        String query = "UPDATE role SET libelle = ? WHERE id_role = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, role.getLibelle());
            stmt.setInt(2, role.getIdRole());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRole(int idRole) {
        String query = "DELETE FROM role WHERE id_role = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idRole);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Role mapResultSetToRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setIdRole(rs.getInt("id_role"));
        role.setLibelle(rs.getString("libelle"));
        return role;
    }
}
