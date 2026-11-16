package ma.dental_ops.ui.view;

import ma.dental_ops.service.implementation.ChargeServiceImpl;
import ma.dental_ops.service.implementation.DashboardServiceImpl;
import ma.dental_ops.ui.controller.ChargeController;
import ma.dental_ops.ui.controller.DashboardController;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        setTitle("DentalSoft - Menu principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initialiserInterface();
    }

    private void initialiserInterface() {
        JPanel principal = new JPanel(new BorderLayout(20, 20));
        principal.setBackground(Color.WHITE);
        principal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titre = new JLabel("Bienvenue dans DentalSoft", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 26));
        titre.setForeground(new Color(7, 23, 57));

        JLabel sousTitre = new JLabel(
                "Gestion du cabinet dentaire",
                SwingConstants.CENTER
        );
        sousTitre.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel entete = new JPanel(new GridLayout(2, 1, 5, 5));
        entete.setBackground(Color.WHITE);
        entete.add(titre);
        entete.add(sousTitre);

        JPanel boutons = new JPanel(new GridLayout(2, 1, 15, 15));
        boutons.setBackground(Color.WHITE);

        JButton btnDashboard = new JButton("Tableau de bord d'activité");
        JButton btnCharges = new JButton("Gestion des charges");

        styliserBouton(btnDashboard);
        styliserBouton(btnCharges);

        btnDashboard.addActionListener(e -> ouvrirDashboard());
        btnCharges.addActionListener(e -> ouvrirCharges());

        boutons.add(btnDashboard);
        boutons.add(btnCharges);

        principal.add(entete, BorderLayout.NORTH);
        principal.add(boutons, BorderLayout.CENTER);

        add(principal);
    }

    private void styliserBouton(JButton bouton) {
        bouton.setFont(new Font("Arial", Font.BOLD, 16));
        bouton.setBackground(new Color(20, 83, 45));
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
        bouton.setPreferredSize(new Dimension(300, 60));
    }

    private void ouvrirDashboard() {
        DashboardController controller =
                new DashboardController(new DashboardServiceImpl());

        DashboardView view = new DashboardView(controller);
        view.setVisible(true);
    }

    private void ouvrirCharges() {
        ChargeController controller =
                new ChargeController(new ChargeServiceImpl());

        ChargeView view = new ChargeView(controller);
        view.setVisible(true);
    }
}