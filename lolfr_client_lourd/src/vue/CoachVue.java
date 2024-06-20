package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controleur.CoachControleur;
import modele.Coach;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CoachVue extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private CoachControleur coachControleur;
    private DefaultTableModel tableModel;

    public CoachVue(CoachControleur coachControleur) {
        this.coachControleur = coachControleur;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID Coach", "Pseudo", "Prénom", "Nom"};
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
                addCoach();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editCoach();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCoach();
            }
        });
    }

    private void loadData() {
        List<Coach> coachs = coachControleur.getAllCoachs();
        for (Coach coach : coachs) {
            Object[] rowData = {coach.getIdCoach(), coach.getPseudo(), coach.getPrenom(), coach.getNom()};
            tableModel.addRow(rowData);
        }
    }

    private void addCoach() {
        // Implémentez le dialogue pour ajouter un coach
    }

    private void editCoach() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Implémentez le dialogue pour modifier un coach
        }
    }

    private void deleteCoach() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int coachId = (int) table.getValueAt(selectedRow, 0);
            coachControleur.deleteCoach(coachId);
            tableModel.removeRow(selectedRow);
        }
    }
}
