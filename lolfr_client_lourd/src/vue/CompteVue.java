package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controleur.CompteControleur;
import modele.Compte;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CompteVue extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private CompteControleur compteControleur;
    private DefaultTableModel tableModel;

    public CompteVue(CompteControleur compteControleur) {
        this.compteControleur = compteControleur;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID Compte", "Pseudo", "Mot de Passe", "Email"};
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
                addCompte();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editCompte();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCompte();
            }
        });
    }

    private void loadData() {
        List<Compte> comptes = compteControleur.getAllComptes();
        for (Compte compte : comptes) {
            Object[] rowData = {compte.getIdCompte(), compte.getPseudo(), compte.getMdp(), compte.getEmail()};
            tableModel.addRow(rowData);
        }
    }

    private void addCompte() {
        // Implémentez le dialogue pour ajouter un compte
    }

    private void editCompte() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Implémentez le dialogue pour modifier un compte
        }
    }

    private void deleteCompte() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int compteId = (int) table.getValueAt(selectedRow, 0);
            compteControleur.deleteCompte(compteId);
            tableModel.removeRow(selectedRow);
        }
    }
}
