package controleur;

import modele.Ligue;
import modele.BddConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LigueControleur {
    private BddConnect bdd;

    public LigueControleur(BddConnect bdd) {
        this.bdd = bdd;
    }
    public Ligue getLigueById(int idLigue) {
        String query = "SELECT * FROM ligue WHERE id_ligue = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idLigue);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToLigue(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ligue> getAllLigues() {
        List<Ligue> ligues = new ArrayList<>();
        String query = "SELECT * FROM ligue";
        try (Connection conn = bdd.getMaConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ligues.add(mapResultSetToLigue(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ligues;
    }

    public boolean addLigue(Ligue ligue) {
        String query = "INSERT INTO ligue (id_ligue, nom, region) VALUES (?, ?, ?)";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ligue.getIdLigue());
            stmt.setString(2, ligue.getNom());
            stmt.setString(3, ligue.getRegion());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLigue(Ligue ligue) {
        String query = "UPDATE ligue SET nom = ?, region = ? WHERE id_ligue = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ligue.getNom());
            stmt.setString(2, ligue.getRegion());
            stmt.setInt(3, ligue.getIdLigue());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLigue(int idLigue) {
        String query = "DELETE FROM ligue WHERE id_ligue = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idLigue);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Ligue mapResultSetToLigue(ResultSet rs) throws SQLException {
        Ligue ligue = new Ligue();
        ligue.setIdLigue(rs.getInt("id_ligue"));
        ligue.setNom(rs.getString("nom"));
        ligue.setRegion(rs.getString("region"));
        return ligue;
    }
}
