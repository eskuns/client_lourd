package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controleur.RoleControleur;
import modele.Role;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RoleVue extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private RoleControleur roleControleur;
    private DefaultTableModel tableModel;

    public RoleVue(RoleControleur roleControleur) {
        this.roleControleur = roleControleur;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID Rôle", "Nom"};
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
                addRole();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editRole();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRole();
            }
        });
    }

    private void loadData() {
        List<Role> roles = roleControleur.getAllRoles();
        for (Role role : roles) {
            Object[] rowData = {role.getIdRole(), role.getNom()};
            tableModel.addRow(rowData);
        }
    }

    private void addRole() {
        // Implémentez le dialogue pour ajouter un rôle
    }

    private void editRole() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Implémentez le dialogue pour modifier un rôle
        }
    }

    private void deleteRole() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int roleId = (int) table.getValueAt(selectedRow, 0);
            roleControleur.deleteRole(roleId);
            tableModel.removeRow(selectedRow);
        }
    }
}
