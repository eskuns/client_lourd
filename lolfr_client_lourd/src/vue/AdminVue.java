package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import controleur.AdminController;
import modele.Admin;

public class AdminVue extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField prenomField;
    private JTextField emailField;
    private JTextField mdpField;

    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;

    private AdminController adminController;

    public AdminVue() {
        adminController = new AdminController();

        setLayout(new BorderLayout());

        // Panel pour les champs de texte et les boutons
        JPanel inputPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JLabel prenomLabel = new JLabel("Prénom:");
        prenomField = new JTextField();
        inputPanel.add(prenomLabel);
        inputPanel.add(prenomField);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);

        JLabel mdpLabel = new JLabel("Mot de passe:");
        mdpField = new JTextField();
        inputPanel.add(mdpLabel);
        inputPanel.add(mdpField);

        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(e -> ajouterAdmin());
        inputPanel.add(ajouterButton);

        modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> modifierAdmin());
        inputPanel.add(modifierButton);

        supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(e -> supprimerAdmin());
        inputPanel.add(supprimerButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Tableau pour afficher les admins
        String[] columnNames = {"ID", "Prénom", "Email", "Mot de passe"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Chargement initial des admins
        chargerAdmins();
    }

    private void chargerAdmins() {
        // Effacer le tableau existant
        tableModel.setRowCount(0);

        // Charger les admins depuis la base de données
        List<Admin> admins = adminController.listerAdmins();

        // Ajouter les admins au tableau
        for (Admin admin : admins) {
            Object[] rowData = {admin.getIdAdmin(), admin.getPrenom(), admin.getEmail(), admin.getMdp()};
            tableModel.addRow(rowData);
        }
    }

    private void ajouterAdmin() {
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String mdp = mdpField.getText();

        if (!prenom.isEmpty() && !email.isEmpty() && !mdp.isEmpty()) {
            Admin nouvelAdmin = new Admin();
            nouvelAdmin.setPrenom(prenom);
            nouvelAdmin.setEmail(email);
            nouvelAdmin.setMdp(mdp);

            if (adminController.ajouterAdmin(nouvelAdmin)) {
                // Ajout réussi, mettre à jour le tableau
                chargerAdmins();
                // Effacer les champs
                prenomField.setText("");
                emailField.setText("");
                mdpField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'administrateur", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierAdmin() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idAdmin = (int) tableModel.getValueAt(selectedRow, 0);
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String mdp = mdpField.getText();

            if (!prenom.isEmpty() && !email.isEmpty() && !mdp.isEmpty()) {
                Admin adminModifie = new Admin();
                adminModifie.setIdAdmin(idAdmin);
                adminModifie.setPrenom(prenom);
                adminModifie.setEmail(email);
                adminModifie.setMdp(mdp);

                if (adminController.modifierAdmin(adminModifie)) {
                    // Modification réussie, mettre à jour le tableau
                    chargerAdmins();
                    // Effacer les champs
                    prenomField.setText("");
                    emailField.setText("");
                    mdpField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification de l'administrateur", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un administrateur à modifier", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerAdmin() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idAdmin = (int) tableModel.getValueAt(selectedRow, 0);

            int confirmation = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet administrateur ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                if (adminController.supprimerAdmin(idAdmin)) {
                    // Suppression réussie, mettre à jour le tableau
                    chargerAdmins();
                    // Effacer les champs
                    prenomField.setText("");
                    emailField.setText("");
                    mdpField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'administrateur", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un administrateur à supprimer", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
