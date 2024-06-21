package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import controleur.LigueController;
import modele.Ligue;

public class LigueVue extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField nomField;
    private JTextField regionField;

    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;

    private LigueController ligueController;

    public LigueVue() {
        ligueController = new LigueController();

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 10, 10));

        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField();
        inputPanel.add(nomLabel);
        inputPanel.add(nomField);

        JLabel regionLabel = new JLabel("Région:");
        regionField = new JTextField();
        inputPanel.add(regionLabel);
        inputPanel.add(regionField);

        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(e -> ajouterLigue());
        inputPanel.add(ajouterButton);

        modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> modifierLigue());
        inputPanel.add(modifierButton);

        supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(e -> supprimerLigue());
        inputPanel.add(supprimerButton);

        add(inputPanel, BorderLayout.SOUTH);

        String[] columnNames = {"ID", "Nom", "Région"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        chargerLigues();
    }

    private void chargerLigues() {
        tableModel.setRowCount(0);

        List<Ligue> ligues = ligueController.listerLigues();

        for (Ligue ligue : ligues) {
            Object[] rowData = {ligue.getIdLigue(), ligue.getNom(), ligue.getRegion()};
            tableModel.addRow(rowData);
        }
    }

    private void ajouterLigue() {
        String nom = nomField.getText();
        String region = regionField.getText();

        if (!nom.isEmpty() && !region.isEmpty()) {
            Ligue nouvelleLigue = new Ligue();
            nouvelleLigue.setNom(nom);
            nouvelleLigue.setRegion(region);

            if (ligueController.ajouterLigue(nouvelleLigue)) {
                chargerLigues();
                nomField.setText("");
                regionField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la ligue", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierLigue() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idLigue = (int) tableModel.getValueAt(selectedRow, 0);
            String nom = nomField.getText();
            String region = regionField.getText();

            if (!nom.isEmpty() && !region.isEmpty()) {
                Ligue ligueModifiee = new Ligue();
                ligueModifiee.setIdLigue(idLigue);
                ligueModifiee.setNom(nom);
                ligueModifiee.setRegion(region);

                if (ligueController.modifierLigue(ligueModifiee)) {
                    chargerLigues();
                    nomField.setText("");
                    regionField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification de la ligue", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligue à modifier", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerLigue() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idLigue = (int) tableModel.getValueAt(selectedRow, 0);
            int option = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette ligue ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (ligueController.supprimerLigue(idLigue)) {
                    chargerLigues();
                    nomField.setText("");
                    regionField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de la ligue", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligue à supprimer", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
