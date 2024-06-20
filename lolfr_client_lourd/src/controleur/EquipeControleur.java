package controleur;

import modele.Equipe;
import modele.BddConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeControleur {
    private BddConnect bdd;

    public EquipeControleur(BddConnect bdd) {
        this.bdd = bdd;
    }

    public Equipe getEquipeById(int idEquipe) {
        String query = "SELECT * FROM equipe WHERE id_equipe = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idEquipe);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEquipe(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Equipe> getAllEquipes() {
        List<Equipe> equipes = new ArrayList<>();
        String query = "SELECT * FROM equipe";
        try (Connection conn = bdd.getMaConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                equipes.add(mapResultSetToEquipe(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipes;
    }

    public boolean addEquipe(Equipe equipe) {
        String query = "INSERT INTO equipe (id_equipe, nom, victoire, defaite, id_ligue, id_coach) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, equipe.getIdEquipe());
            stmt.setString(2, equipe.getNom());
            stmt.setInt(3, equipe.getVictoire());
            stmt.setInt(4, equipe.getDefaite());
            stmt.setInt(5, equipe.getIdLigue());
            stmt.setInt(6, equipe.getIdCoach());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEquipe(Equipe equipe) {
        String query = "UPDATE equipe SET nom = ?, victoire = ?, defaite = ?, id_ligue = ?, id_coach = ? WHERE id_equipe = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, equipe.getNom());
            stmt.setInt(2, equipe.getVictoire());
            stmt.setInt(3, equipe.getDefaite());
            stmt.setInt(4, equipe.getIdLigue());
            stmt.setInt(5, equipe.getIdCoach());
            stmt.setInt(6, equipe.getIdEquipe());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEquipe(int idEquipe) {
        String query = "DELETE FROM equipe WHERE id_equipe = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idEquipe);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Equipe mapResultSetToEquipe(ResultSet rs) throws SQLException {
        Equipe equipe = new Equipe();
        equipe.setIdEquipe(rs.getInt("id_equipe"));
        equipe.setNom(rs.getString("nom"));
        equipe.setVictoire(rs.getInt("victoire"));
        equipe.setDefaite(rs.getInt("defaite"));
        equipe.setIdLigue(rs.getInt("id_ligue"));
        equipe.setIdCoach(rs.getInt("id_coach"));
        return equipe;
    }
}
