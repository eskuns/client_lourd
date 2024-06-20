package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controleur.EquipeControleur;
import modele.Equipe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EquipeVue extends JPanel {    
	private static final long serialVersionUID = 1L;

    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private EquipeControleur equipeControleur;
    private DefaultTableModel tableModel;

    public EquipeVue(EquipeControleur equipeControleur) {
        this.equipeControleur = equipeControleur;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID Equipe", "Nom", "Victoire", "Défaite", "ID Ligue", "ID Coach"};
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
                addEquipe();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editEquipe();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEquipe();
            }
        });
    }

    private void loadData() {
        List<Equipe> equipes = equipeControleur.getAllEquipes();
        for (Equipe equipe : equipes) {
            Object[] rowData = {equipe.getIdEquipe(), equipe.getNom(), equipe.getVictoire(), equipe.getDefaite(), equipe.getIdLigue(), equipe.getIdCoach()};
            tableModel.addRow(rowData);
        }
    }

    private void addEquipe() {
        // Implémentez le dialogue pour ajouter une équipe
    }

    private void editEquipe() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Implémentez le dialogue pour modifier une équipe
        }
    }

    private void deleteEquipe() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int equipeId = (int) table.getValueAt(selectedRow, 0);
            equipeControleur.deleteEquipe(equipeId);
            tableModel.removeRow(selectedRow);
        }
    }
}
