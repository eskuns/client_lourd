package vue;

import javax.swing.*;
import java.awt.*;
import vue.CoachVue;
import vue.JoueurVue;
import vue.EquipeVue;
import vue.LigueVue;
import vue.RoleVue;
import vue.CompteVue;
import vue.AdminVue;

public class MainApp {

    private JFrame frame;
    private JTabbedPane tabbedPane;
    private CoachVue coachVue;
    private JoueurVue joueurVue;
    private EquipeVue equipeVue;
    private LigueVue ligueVue;
    private RoleVue roleVue;
    private CompteVue compteVue;
    private AdminVue adminVue;

    public MainApp() {
        frame = new JFrame("Application de Gestion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        tabbedPane = new JTabbedPane();

        coachVue = new CoachVue();
        tabbedPane.addTab("Gestion des Coachs", coachVue);

        joueurVue = new JoueurVue();
        tabbedPane.addTab("Gestion des Joueurs", joueurVue);

        equipeVue = new EquipeVue();
        tabbedPane.addTab("Gestion des Équipes", equipeVue);

        ligueVue = new LigueVue();
        tabbedPane.addTab("Gestion des Ligues", ligueVue);

        roleVue = new RoleVue();
        tabbedPane.addTab("Gestion des Rôles", roleVue);

        compteVue = new CompteVue();
        tabbedPane.addTab("Gestion des Comptes", compteVue);

        adminVue = new AdminVue();
        tabbedPane.addTab("Gestion des Administrateurs", adminVue);

        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApp();
        });
    }
}
