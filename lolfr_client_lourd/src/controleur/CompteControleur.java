package controleur;

import modele.Compte;
import modele.BddConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteControleur {
    private BddConnect bdd;

    public CompteControleur(BddConnect bdd) {
        this.bdd = bdd;
    }


    public Compte getCompteById(int idCompte) {
        String query = "SELECT * FROM Compte WHERE id_Compte = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCompte);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCompte(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Compte> getAllComptes() {
        List<Compte> Comptes = new ArrayList<>();
        String query = "SELECT * FROM Compte";
        try (Connection conn = bdd.getMaConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Comptes.add(mapResultSetToCompte(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comptes;
    }

    public boolean addCompte(Compte Compte) {
        String query = "INSERT INTO Compte (id_Compte, pseudo, mdp, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Compte.getIdCompte());
            stmt.setString(2, Compte.getPseudo());
            stmt.setString(3, Compte.getMdp());
            stmt.setString(4, Compte.getEmail());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCompte(Compte Compte) {
        String query = "UPDATE Compte SET pseudo = ?, mdp = ?, email = ? WHERE id_Compte = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, Compte.getPseudo());
            stmt.setString(2, Compte.getMdp());
            stmt.setString(3, Compte.getEmail());
            stmt.setInt(4, Compte.getIdCompte());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCompte(int idCompte) {
        String query = "DELETE FROM Compte WHERE id_Compte = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCompte);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Compte mapResultSetToCompte(ResultSet rs) throws SQLException {
        Compte Compte = new Compte();
        Compte.setIdCompte(rs.getInt("id_Compte"));
        Compte.setPseudo(rs.getString("pseudo"));
        Compte.setMdp(rs.getString("mdp"));
        Compte.setEmail(rs.getString("email"));
        return Compte;
    }
}
