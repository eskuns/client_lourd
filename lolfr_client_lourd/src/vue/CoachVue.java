package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import controleur.CoachController;
import modele.Coach;

public class CoachVue extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField pseudoField;
    private JTextField prenomField;
    private JTextField nomField;
    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;

    private CoachController coachController;

    public CoachVue() {
        coachController = new CoachController();

        setLayout(new BorderLayout());

        // Tableau pour afficher les coachs
        String[] columnNames = {"ID", "Pseudo", "Prénom", "Nom"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel pour les champs de texte et boutons
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

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

        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(e -> ajouterCoach());
        inputPanel.add(ajouterButton);

        modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> modifierCoach());
        inputPanel.add(modifierButton);

        supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(e -> supprimerCoach());
        inputPanel.add(supprimerButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Charger les coachs existants au démarrage
        List<Coach> coachs = coachController.listerCoachs();
        for (Coach coach : coachs) {
            ajouterLigneDansTableau(coach);
        }
    }

    private void ajouterCoach() {
        String pseudo = pseudoField.getText();
        String prenom = prenomField.getText();
        String nom = nomField.getText();

        Coach nouveauCoach = new Coach();
        nouveauCoach.setPseudo(pseudo);
        nouveauCoach.setPrenom(prenom);
        nouveauCoach.setNom(nom);

        if (coachController.ajouterCoach(nouveauCoach)) {
            ajouterLigneDansTableau(nouveauCoach);
            viderChamps();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du coach.");
        }
    }

    private void modifierCoach() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idCoach = (int) table.getValueAt(selectedRow, 0);
            String pseudo = pseudoField.getText();
            String prenom = prenomField.getText();
            String nom = nomField.getText();

            Coach coachModifie = new Coach();
            coachModifie.setIdCoach(idCoach);
            coachModifie.setPseudo(pseudo);
            coachModifie.setPrenom(prenom);
            coachModifie.setNom(nom);

            if (coachController.modifierCoach(coachModifie)) {
                mettreAJourLigneDansTableau(selectedRow, coachModifie);
                viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification du coach.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un coach à modifier.");
        }
    }

    private void supprimerCoach() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idCoach = (int) table.getValueAt(selectedRow, 0);
            if (coachController.supprimerCoach(idCoach)) {
                tableModel.removeRow(selectedRow);
                viderChamps();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du coach.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un coach à supprimer.");
        }
    }

    private void ajouterLigneDansTableau(Coach coach) {
        Object[] row = {coach.getIdCoach(), coach.getPseudo(), coach.getPrenom(), coach.getNom()};
        tableModel.addRow(row);
    }

    private void mettreAJourLigneDansTableau(int rowIndex, Coach coach) {
        tableModel.setValueAt(coach.getIdCoach(), rowIndex, 0);
        tableModel.setValueAt(coach.getPseudo(), rowIndex, 1);
        tableModel.setValueAt(coach.getPrenom(), rowIndex, 2);
        tableModel.setValueAt(coach.getNom(), rowIndex, 3);
    }

    private void viderChamps() {
        pseudoField.setText("");
        prenomField.setText("");
        nomField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestion des Coachs");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            CoachVue coachVue = new CoachVue();
            frame.add(coachVue);

            frame.setVisible(true);
        });
    }
}
