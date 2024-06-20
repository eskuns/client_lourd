package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controleur.LigueControleur;
import modele.Ligue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LigueVue extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private LigueControleur ligueControleur;
    private DefaultTableModel tableModel;

    public LigueVue(LigueControleur ligueControleur) {
        this.ligueControleur = ligueControleur;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID Ligue", "Nom", "Région"};
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
                addLigue();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editLigue();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLigue();
            }
        });
    }

    private void loadData() {
        List<Ligue> ligues = ligueControleur.getAllLigues();
        for (Ligue ligue : ligues) {
            Object[] rowData = {ligue.getIdLigue(), ligue.getNom(), ligue.getRegion()};
            tableModel.addRow(rowData);
        }
    }

    private void addLigue() {
        // Implémentez le dialogue pour ajouter une ligue
    }

    private void editLigue() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Implémentez le dialogue pour modifier une ligue
        }
    }

    private void deleteLigue() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int ligueId = (int) table.getValueAt(selectedRow, 0);
            ligueControleur.deleteLigue(ligueId);
            tableModel.removeRow(selectedRow);
        }
    }
}
