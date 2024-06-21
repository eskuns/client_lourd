package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import controleur.JoueurController;
import modele.Joueur;

public class JoueurVue extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField pseudoField;
    private JTextField prenomField;
    private JTextField nomField;
    private JTextField nationaliteField;
    private JTextField equipeField;
    private JTextField roleField;
    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;

    private JoueurController joueurController;

    public JoueurVue() {
        joueurController = new JoueurController();

        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Pseudo", "Prénom", "Nom", "Nationalité", "ID Équipe", "ID Rôle"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));

        JLabel pseudoLabel = new JLabel("Pseudo:");
        pseudoField = new JTextField();
        inputPanel.add(pseudoLabel);
        inputPanel.add(pseudoField);

        JLabel prenomLabel = new JLabel("Prénom:");
        prenomField = new JTextField();
        inputPanel.add(prenomLabel);
        inputPanel.add(prenomField);

        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField();
        inputPanel.add(nomLabel);
        inputPanel.add(nomField);

        JLabel nationaliteLabel = new JLabel("Nationalité:");
        nationaliteField = new JTextField();
        inputPanel.add(nationaliteLabel);
        inputPanel.add(nationaliteField);

        JLabel equipeLabel = new JLabel("ID Équipe:");
        equipeField = new JTextField();
        inputPanel.add(equipeLabel);
        inputPanel.add(equipeField);

        JLabel roleLabel = new JLabel("ID Rôle:");
        roleField = new JTextField();
        inputPanel.add(roleLabel);
        inputPanel.add(roleField);

        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(e -> ajouterJoueur());
        inputPanel.add(ajouterButton);

        modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> modifierJoueur());
        inputPanel.add(modifierButton);

        supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(e -> supprimerJoueur());
        inputPanel.add(supprimerButton);

        add(inputPanel, BorderLayout.SOUTH);

        List<Joueur> joueurs = joueurController.listerJoueurs();
        for (Joueur joueur : joueurs) {
            ajouterLigneDansTableau(joueur);
        }
    }

    private void ajouterJoueur() {
        String pseudo = pseudoField.getText();
        String prenom = prenomField.getText();
        String nom = nomField.getText();
        String nationalite = nationaliteField.getText();
        int idEquipe = Integer.parseInt(equipeField.getText());
        int idRole = Integer.parseInt(roleField.getText());

        Joueur nouveauJoueur = new Joueur();
        nouveauJoueur.setPseudo(pseudo);
        nouveauJoueur.setPrenom(prenom);
        nouveauJoueur.setNom(nom);
        nouveauJoueur.setNationalite(nationalite);
        nouveauJoueur.setIdEquipe(idEquipe);
        nouveauJoueur.setIdRole(idRole);

        if (joueurController.ajouterJoueur(nouveauJoueur)) {
            ajouterLigneDansTableau(nouveauJoueur);
            viderChamps();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du joueur.");
        }
    }

    private void modifierJoueur() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idJoueur = (int) table.getValueAt(selectedRow, 0);
            String pseudo = pseudoField.getText();
            String prenom = prenomField.getText();
            String nom = nomField.getText();
            String nationalite = nationaliteField.getText();
            int idEquipe = Integer.parseInt(equipeField.getText());
            int idRole = Integer.parseInt(roleField.getText());

            Joueur joueurModifie = new Joueur();
            joueurModifie.setIdJoueur(idJoueur);
            joueurModifie.setPseudo(pseudo);
            joueurModifie.setPrenom(prenom);
            joueurModifie.setNom(nom);
            joueurModifie.setNationalite(nationalite);
            joueurModifie.setIdEquipe(idEquipe);
            joueurModifie.setIdRole(idRole);

            if (joueurController.modifierJoueur(joueurModifie)) {
                mettreAJourLigneDansTableau(selectedRow, joueurModifie);
                viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification du joueur.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un joueur à modifier.");
        }
    }

    private void supprimerJoueur() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idJoueur = (int) table.getValueAt(selectedRow, 0);
            if (joueurController.supprimerJoueur(idJoueur)) {
                tableModel.removeRow(selectedRow);
                viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du joueur.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un joueur à supprimer.");
        }
    }

    private void ajouterLigneDansTableau(Joueur joueur) {
        Object[] row = {joueur.getIdJoueur(), joueur.getPseudo(), joueur.getPrenom(), joueur.getNom(),
                joueur.getNationalite(), joueur.getIdEquipe(), joueur.getIdRole()};
        tableModel.addRow(row);
    }

    private void mettreAJourLigneDansTableau(int rowIndex, Joueur joueur) {
        tableModel.setValueAt(joueur.getIdJoueur(), rowIndex, 0);
        tableModel.setValueAt(joueur.getPseudo(), rowIndex, 1);
        tableModel.setValueAt(joueur.getPrenom(), rowIndex, 2);
        tableModel.setValueAt(joueur.getNom(), rowIndex, 3);
        tableModel.setValueAt(joueur.getNationalite(), rowIndex, 4);
        tableModel.setValueAt(joueur.getIdEquipe(), rowIndex, 5);
        tableModel.setValueAt(joueur.getIdRole(), rowIndex, 6);
    }

    private void viderChamps() {
        pseudoField.setText("");
        prenomField.setText("");
        nomField.setText("");
        nationaliteField.setText("");
        equipeField.setText("");
        roleField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestion des Joueurs");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JoueurVue joueurVue = new JoueurVue();
            frame.add(joueurVue);

            frame.setVisible(true);
        });
    }
}
