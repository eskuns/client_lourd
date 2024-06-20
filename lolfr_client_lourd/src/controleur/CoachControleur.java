package controleur;

import modele.Coach;
import modele.BddConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoachControleur {
    private BddConnect bdd;

    public CoachControleur(BddConnect bdd) {
        this.bdd = bdd;
    }


    public Coach getCoachById(int idCoach) {
        String query = "SELECT * FROM coach WHERE id_coach = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCoach);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCoach(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Coach> getAllCoachs() {
        List<Coach> coachs = new ArrayList<>();
        String query = "SELECT * FROM coach";
        try (Connection conn = bdd.getMaConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                coachs.add(mapResultSetToCoach(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coachs;
    }

    public boolean addCoach(Coach coach) {
        String query = "INSERT INTO coach (id_coach, pseudo, prenom, nom) VALUES (?, ?, ?, ?)";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, coach.getIdCoach());
            stmt.setString(2, coach.getPseudo());
            stmt.setString(3, coach.getPrenom());
            stmt.setString(4, coach.getNom());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCoach(Coach coach) {
        String query = "UPDATE coach SET pseudo = ?, prenom = ?, nom = ? WHERE id_coach = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, coach.getPseudo());
            stmt.setString(2, coach.getPrenom());
            stmt.setString(3, coach.getNom());
            stmt.setInt(4, coach.getIdCoach());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCoach(int idCoach) {
        String query = "DELETE FROM coach WHERE id_coach = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCoach);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Coach mapResultSetToCoach(ResultSet rs) throws SQLException {
        Coach coach = new Coach();
        coach.setIdCoach(rs.getInt("id_coach"));
        coach.setPseudo(rs.getString("pseudo"));
        coach.setPrenom(rs.getString("prenom"));
        coach.setNom(rs.getString("nom"));
        return coach;
    }
}
