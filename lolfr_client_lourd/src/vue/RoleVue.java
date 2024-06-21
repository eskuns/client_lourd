package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import controleur.RoleController;
import modele.Role;

public class RoleVue extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField libelleField;

    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;

    private RoleController roleController;

    public RoleVue() {
        roleController = new RoleController();

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        JLabel libelleLabel = new JLabel("Libellé:");
        libelleField = new JTextField();
        inputPanel.add(libelleLabel);
        inputPanel.add(libelleField);

        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(e -> ajouterRole());
        inputPanel.add(ajouterButton);

        modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> modifierRole());
        inputPanel.add(modifierButton);

        supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(e -> supprimerRole());
        inputPanel.add(supprimerButton);

        add(inputPanel, BorderLayout.SOUTH);

        String[] columnNames = {"ID", "Libellé"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        chargerRoles();
    }

    private void chargerRoles() {
        tableModel.setRowCount(0);

        List<Role> roles = roleController.listerRoles();

        for (Role role : roles) {
            Object[] rowData = {role.getIdRole(), role.getLibelle()};
            tableModel.addRow(rowData);
        }
    }

    private void ajouterRole() {
        String libelle = libelleField.getText();

        if (!libelle.isEmpty()) {
            Role nouveauRole = new Role();
            nouveauRole.setLibelle(libelle);

            if (roleController.ajouterRole(nouveauRole)) {
                chargerRoles();
                libelleField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du rôle", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez saisir le libellé du rôle", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierRole() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idRole = (int) tableModel.getValueAt(selectedRow, 0);
            String libelle = libelleField.getText();

            if (!libelle.isEmpty()) {
                Role roleModifie = new Role();
                roleModifie.setIdRole(idRole);
                roleModifie.setLibelle(libelle);

                if (roleController.modifierRole(roleModifie)) {
                    chargerRoles();
                    libelleField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification du rôle", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez saisir le libellé du rôle", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un rôle à modifier", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerRole() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idRole = (int) tableModel.getValueAt(selectedRow, 0);
            int option = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce rôle ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (roleController.supprimerRole(idRole)) {
                    chargerRoles();
                    libelleField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du rôle", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un rôle à supprimer", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
