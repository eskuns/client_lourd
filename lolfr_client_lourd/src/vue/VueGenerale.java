package vue;

import controleur.AdminControleur;
import controleur.CoachControleur;
import controleur.CompteControleur;
import controleur.EquipeControleur;
import controleur.JoueurControleur;
import controleur.LigueControleur;
import controleur.RoleControleur;
import modele.BddConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class VueGenerale extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private AdminVue adminVue;
    private CoachVue coachVue;
    private CompteVue compteVue;
    private EquipeVue equipeVue;
    private JoueurVue joueurVue;
    private LigueVue ligueVue;
    private RoleVue roleVue;

    public VueGenerale(AdminControleur adminControleur, CoachControleur coachControleur, 
    				   CompteControleur compteControleur, EquipeControleur equipeControleur, 
    				   JoueurControleur joueurControleur, LigueControleur ligueControleur, 
    				   RoleControleur roleControleur) {

        setTitle("Gestion des Ligues");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Initialisation du CardLayout et du JPanel pour les vues
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initialisation des vues avec les contrôleurs correspondants
        adminVue = new AdminVue(adminControleur);
        coachVue = new CoachVue(coachControleur);
        compteVue = new CompteVue(compteControleur);
        equipeVue = new EquipeVue(equipeControleur);
        joueurVue = new JoueurVue(joueurControleur);
        ligueVue = new LigueVue(ligueControleur);
        roleVue = new RoleVue(roleControleur);

        // Ajout des vues au cardPanel avec des noms correspondant à chaque vue
        cardPanel.add(adminVue, "Admin");
        cardPanel.add(coachVue, "Coach");
        cardPanel.add(compteVue, "Compte");
        cardPanel.add(equipeVue, "Equipe");
        cardPanel.add(joueurVue, "Joueur");
        cardPanel.add(ligueVue, "Ligue");
        cardPanel.add(roleVue, "Role");

        // Ajout du cardPanel au centre de JFrame
        add(cardPanel, BorderLayout.CENTER);

        // Création du panel pour les boutons avec un GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(1, 9));
        // Ajout des boutons avec les noms correspondant aux vues
        addButton(buttonPanel, "Admin");
        addButton(buttonPanel, "Coach");
        addButton(buttonPanel, "Compte");
        addButton(buttonPanel, "Equipe");
        addButton(buttonPanel, "Joueur");
        addButton(buttonPanel, "Ligue");
        addButton(buttonPanel, "Role");
        // Ajout du panel de boutons au bas du JFrame
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Méthode privée pour ajouter un bouton au panel avec un ActionListener
    private void addButton(JPanel panel, final String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Changer la vue affichée dans le cardPanel
                cardLayout.show(cardPanel, name);
            }
        });
        panel.add(button);
    }

    // Méthode main pour tester la VueGenerale
    public static void main(String[] args) {
        // Création des contrôleurs avec une instance BDD
        BddConnect bddConnect = new BddConnect("jdbc:mysql://localhost:3306/lolfr", "root", "", "com.mysql.cj.jdbc.Driver");
        AdminControleur adminControleur = new AdminControleur(bddConnect);
        CoachControleur coachControleur = new CoachControleur(bddConnect);
        CompteControleur compteControleur = new CompteControleur(bddConnect);
        EquipeControleur equipeControleur = new EquipeControleur(bddConnect);
        JoueurControleur joueurControleur = new JoueurControleur(bddConnect);
        LigueControleur ligueControleur = new LigueControleur(bddConnect);
        RoleControleur roleControleur = new RoleControleur(bddConnect);

        // Création de la VueGenerale avec les contrôleurs initialisés
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VueGenerale vueGenerale = new VueGenerale(adminControleur, coachControleur, compteControleur,
                        equipeControleur, joueurControleur, ligueControleur, roleControleur);

                // Rendre la VueGenerale visible
                vueGenerale.setVisible(true);
            }
        });
    }
}
