package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import controleur.EquipeController;
import modele.Equipe;

public class EquipeVue extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nomField;
    private JTextField victoireField;
    private JTextField defaiteField;
    private JTextField ligueField;
    private JTextField coachField;
    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;

    private EquipeController equipeController;

    public EquipeVue() {
        equipeController = new EquipeController();

        setLayout(new BorderLayout());

        // Tableau pour afficher les équipes
        String[] columnNames = {"ID", "Nom", "Victoire", "Défaite", "ID Ligue", "ID Coach"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel pour les champs de texte et boutons
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));

        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField();
        inputPanel.add(nomLabel);
        inputPanel.add(nomField);

        JLabel victoireLabel = new JLabel("Victoire:");
        victoireField = new JTextField();
        inputPanel.add(victoireLabel);
        inputPanel.add(victoireField);

        JLabel defaiteLabel = new JLabel("Défaite:");
        defaiteField = new JTextField();
        inputPanel.add(defaiteLabel);
        inputPanel.add(defaiteField);

        JLabel ligueLabel = new JLabel("ID Ligue:");
        ligueField = new JTextField();
        inputPanel.add(ligueLabel);
        inputPanel.add(ligueField);

        JLabel coachLabel = new JLabel("ID Coach:");
        coachField = new JTextField();
        inputPanel.add(coachLabel);
        inputPanel.add(coachField);

        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(e -> ajouterEquipe());
        inputPanel.add(ajouterButton);

        modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> modifierEquipe());
        inputPanel.add(modifierButton);

        supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(e -> supprimerEquipe());
        inputPanel.add(supprimerButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Charger les équipes existantes au démarrage
        List<Equipe> equipes = equipeController.listerEquipes();
        for (Equipe equipe : equipes) {
            ajouterLigneDansTableau(equipe);
        }
    }

    private void ajouterEquipe() {
        String nom = nomField.getText();
        int victoire = Integer.parseInt(victoireField.getText());
        int defaite = Integer.parseInt(defaiteField.getText());
        int idLigue = Integer.parseInt(ligueField.getText());
        int idCoach = Integer.parseInt(coachField.getText());

        Equipe nouvelleEquipe = new Equipe();
        nouvelleEquipe.setNom(nom);
        nouvelleEquipe.setVictoire(victoire);
        nouvelleEquipe.setDefaite(defaite);
        nouvelleEquipe.setIdLigue(idLigue);
        nouvelleEquipe.setIdCoach(idCoach);

        if (equipeController.ajouterEquipe(nouvelleEquipe)) {
            ajouterLigneDansTableau(nouvelleEquipe);
            viderChamps();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'équipe.");
        }
    }

    private void modifierEquipe() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idEquipe = (int) table.getValueAt(selectedRow, 0);
            String nom = nomField.getText();
            int victoire = Integer.parseInt(victoireField.getText());
            int defaite = Integer.parseInt(defaiteField.getText());
            int idLigue = Integer.parseInt(ligueField.getText());
            int idCoach = Integer.parseInt(coachField.getText());

            Equipe equipeModifiee = new Equipe();
            equipeModifiee.setIdEquipe(idEquipe);
            equipeModifiee.setNom(nom);
            equipeModifiee.setVictoire(victoire);
            equipeModifiee.setDefaite(defaite);
            equipeModifiee.setIdLigue(idLigue);
            equipeModifiee.setIdCoach(idCoach);

            if (equipeController.modifierEquipe(equipeModifiee)) {
                mettreAJourLigneDansTableau(selectedRow, equipeModifiee);
                viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification de l'équipe.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une équipe à modifier.");
        }
    }

    private void supprimerEquipe() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idEquipe = (int) table.getValueAt(selectedRow, 0);
            if (equipeController.supprimerEquipe(idEquipe)) {
                tableModel.removeRow(selectedRow);
                viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'équipe.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une équipe à supprimer.");
        }
    }

    private void ajouterLigneDansTableau(Equipe equipe) {
        Object[] row = {equipe.getIdEquipe(), equipe.getNom(), equipe.getVictoire(), equipe.getDefaite(),
                equipe.getIdLigue(), equipe.getIdCoach()};
        tableModel.addRow(row);
    }

    private void mettreAJourLigneDansTableau(int rowIndex, Equipe equipe) {
        tableModel.setValueAt(equipe.getIdEquipe(), rowIndex, 0);
        tableModel.setValueAt(equipe.getNom(), rowIndex, 1);
        tableModel.setValueAt(equipe.getVictoire(), rowIndex, 2);
        tableModel.setValueAt(equipe.getDefaite(), rowIndex, 3);
        tableModel.setValueAt(equipe.getIdLigue(), rowIndex, 4);
        tableModel.setValueAt(equipe.getIdCoach(), rowIndex, 5);
    }

    private void viderChamps() {
        nomField.setText("");
        victoireField.setText("");
        defaiteField.setText("");
        ligueField.setText("");
        coachField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestion des Équipes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            EquipeVue equipeVue = new EquipeVue();
            frame.add(equipeVue);

            frame.setVisible(true);
        });
    }
}
