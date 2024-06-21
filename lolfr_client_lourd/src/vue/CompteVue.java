package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import controleur.CompteController;
import modele.Compte;

public class CompteVue extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField pseudoField;
    private JTextField mdpField;
    private JTextField emailField;

    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;

    private CompteController compteController;

    public CompteVue() {
        compteController = new CompteController();

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JLabel pseudoLabel = new JLabel("Pseudo:");
        pseudoField = new JTextField();
        inputPanel.add(pseudoLabel);
        inputPanel.add(pseudoField);

        JLabel mdpLabel = new JLabel("Mot de passe:");
        mdpField = new JTextField();
        inputPanel.add(mdpLabel);
        inputPanel.add(mdpField);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);

        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(e -> ajouterCompte());
        inputPanel.add(ajouterButton);

        modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> modifierCompte());
        inputPanel.add(modifierButton);

        supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(e -> supprimerCompte());
        inputPanel.add(supprimerButton);

        add(inputPanel, BorderLayout.SOUTH);

        String[] columnNames = {"ID", "Pseudo", "Mot de passe", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        chargerComptes();
    }

    private void chargerComptes() {
        tableModel.setRowCount(0);

        List<Compte> comptes = compteController.listerComptes();

        for (Compte compte : comptes) {
            Object[] rowData = {compte.getIdCompte(), compte.getPseudo(), compte.getMdp(), compte.getEmail()};
            tableModel.addRow(rowData);
        }
    }

    private void ajouterCompte() {
        String pseudo = pseudoField.getText();
        String mdp = mdpField.getText();
        String email = emailField.getText();

        if (!pseudo.isEmpty() && !mdp.isEmpty() && !email.isEmpty()) {
            Compte nouveauCompte = new Compte();
            nouveauCompte.setPseudo(pseudo);
            nouveauCompte.setMdp(mdp);
            nouveauCompte.setEmail(email);

            if (compteController.ajouterCompte(nouveauCompte)) {
                chargerComptes();
                pseudoField.setText("");
                mdpField.setText("");
                emailField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du compte", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierCompte() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idCompte = (int) tableModel.getValueAt(selectedRow, 0);
            String pseudo = pseudoField.getText();
            String mdp = mdpField.getText();
            String email = emailField.getText();

            if (!pseudo.isEmpty() && !mdp.isEmpty() && !email.isEmpty()) {
                Compte compteModifie = new Compte();
                compteModifie.setIdCompte(idCompte);
                compteModifie.setPseudo(pseudo);
                compteModifie.setMdp(mdp);
                compteModifie.setEmail(email);

                if (compteController.modifierCompte(compteModifie)) {
                    chargerComptes();
                    pseudoField.setText("");
                    mdpField.setText("");
                    emailField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification du compte", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un compte à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerCompte() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idCompte = (int) tableModel.getValueAt(selectedRow, 0);

            int confirmation = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce compte ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                if (compteController.supprimerCompte(idCompte)) {
                    chargerComptes();
                    pseudoField.setText("");
                    mdpField.setText("");
                    emailField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du compte", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un coach à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
