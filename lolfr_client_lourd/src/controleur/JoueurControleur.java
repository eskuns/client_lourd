package controleur;

import modele.Joueur;
import modele.BddConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurControleur {
    private BddConnect bdd;

    public JoueurControleur(BddConnect bdd) {
        this.bdd = bdd;
    }

    public Joueur getJoueurById(int idJoueur) {
        String query = "SELECT * FROM joueur WHERE id_joueur = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idJoueur);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToJoueur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Joueur> getAllJoueurs() {
        List<Joueur> joueurs = new ArrayList<>();
        String query = "SELECT * FROM joueur";
        try (Connection conn = bdd.getMaConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                joueurs.add(mapResultSetToJoueur(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joueurs;
    }

    public boolean addJoueur(Joueur joueur) {
        String query = "INSERT INTO joueur (id_joueur, pseudo, prenom, nom, date_naissance, nationalite, id_role, id_equipe) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, joueur.getIdJoueur());
            stmt.setString(2, joueur.getPseudo());
            stmt.setString(3, joueur.getPrenom());
            stmt.setString(4, joueur.getNom());
            stmt.setDate(5, new java.sql.Date(joueur.getDateNaissance().getTime()));
            stmt.setString(6, joueur.getNationalite());
            stmt.setInt(7, joueur.getIdRole());
            stmt.setInt(8, joueur.getIdEquipe());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateJoueur(Joueur joueur) {
        String query = "UPDATE joueur SET pseudo = ?, prenom = ?, nom = ?, date_naissance = ?, nationalite = ?, id_role = ?, id_equipe = ? WHERE id_joueur = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, joueur.getPseudo());
            stmt.setString(2, joueur.getPrenom());
            stmt.setString(3, joueur.getNom());
            stmt.setDate(4, new java.sql.Date(joueur.getDateNaissance().getTime()));
            stmt.setString(5, joueur.getNationalite());
            stmt.setInt(6, joueur.getIdRole());
            stmt.setInt(7, joueur.getIdEquipe());
            stmt.setInt(8, joueur.getIdJoueur());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteJoueur(int idJoueur) {
        String query = "DELETE FROM joueur WHERE id_joueur = ?";
        try (Connection conn = bdd.getMaConnexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idJoueur);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Joueur mapResultSetToJoueur(ResultSet rs) throws SQLException {
        Joueur joueur = new Joueur();
        joueur.setIdJoueur(rs.getInt("id_joueur"));
        joueur.setPseudo(rs.getString("pseudo"));
        joueur.setPrenom(rs.getString("prenom"));
        joueur.setNom(rs.getString("nom"));
        joueur.setDateNaissance(rs.getDate("date_naissance"));
        joueur.setNationalite(rs.getString("nationalite"));
        joueur.setIdRole(rs.getInt("id_role"));
        joueur.setIdEquipe(rs.getInt("id_equipe"));
        return joueur;
    }
}
