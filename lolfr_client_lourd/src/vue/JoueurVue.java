package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controleur.JoueurControleur;
import modele.Joueur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JoueurVue extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private JoueurControleur joueurControleur;
    private DefaultTableModel tableModel;

    public JoueurVue(JoueurControleur joueurControleur) {
        this.joueurControleur = joueurControleur;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID Joueur", "Pseudo", "Prénom", "Nom", "Date de Naissance", "Nationalité", "ID Rôle", "ID Equipe"};
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
                addJoueur();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editJoueur();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteJoueur();
            }
        });
    }

    private void loadData() {
        List<Joueur> joueurs = joueurControleur.getAllJoueurs();
        for (Joueur joueur : joueurs) {
            Object[] rowData = {joueur.getIdJoueur(), joueur.getPseudo(), joueur.getPrenom(), joueur.getNom(), joueur.getDateNaissance(), joueur.getNationalite(), joueur.getIdRole(), joueur.getIdEquipe()};
            tableModel.addRow(rowData);
        }
    }

    private void addJoueur() {
        // Implémentez le dialogue pour ajouter un joueur
    }

    private void editJoueur() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Implémentez le dialogue pour modifier un joueur
        }
    }

    private void deleteJoueur() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int joueurId = (int) table.getValueAt(selectedRow, 0);
            joueurControleur.deleteJoueur(joueurId);
            tableModel.removeRow(selectedRow);
        }
    }
}
