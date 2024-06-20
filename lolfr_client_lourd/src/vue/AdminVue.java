package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controleur.AdminControleur;
import modele.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminVue extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private AdminControleur adminControleur;
    private DefaultTableModel tableModel;

    public AdminVue(AdminControleur adminControleur) {
        this.adminControleur = adminControleur;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID Admin", "Prénom", "Email", "Mot de Passe"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Ajouter");
        editButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAdmin();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAdmin();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAdmin();
            }
        });
    }

    private void loadData() {
        List<Admin> admins = adminControleur.getAllAdmins();
        for (Admin admin : admins) {
            Object[] rowData = {admin.getIdAdmin(), admin.getPrenom(), admin.getEmail(), admin.getMotDePasse()};
            tableModel.addRow(rowData);
        }
    }

    private void addAdmin() {
        // Implémentez le dialogue pour ajouter un admin
    }

    private void editAdmin() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Implémentez le dialogue pour modifier un admin
        }
    }

    private void deleteAdmin() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int adminId = (int) table.getValueAt(selectedRow, 0);
            adminControleur.deleteAdmin(adminId);
            tableModel.removeRow(selectedRow);
        }
    }
}
