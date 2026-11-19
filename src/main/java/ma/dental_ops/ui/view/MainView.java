package ma.dental_ops.ui.view;

import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.config.ApplicationContext;
import ma.dental_ops.service.api.ChargeService;
import ma.dental_ops.service.api.DashboardService;
import ma.dental_ops.service.api.FileAttenteService;
import ma.dental_ops.service.api.PatientService;
import ma.dental_ops.service.api.UtilisateurService;
import ma.dental_ops.ui.controller.ChargeController;
import ma.dental_ops.ui.controller.DashboardController;
import ma.dental_ops.ui.controller.PatientController;
import ma.dental_ops.ui.controller.UtilisateurController;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private Utilisateur utilisateurConnecte;
    private final ApplicationContext context;

    public MainView() {
        this(null);
    }

    public MainView(Utilisateur utilisateurConnecte) {
        this(utilisateurConnecte, ApplicationContext.getInstance());
    }

    MainView(Utilisateur utilisateurConnecte, ApplicationContext context) {
        this.utilisateurConnecte = utilisateurConnecte;
        this.context = context;
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

        JLabel sousTitre = new JLabel("Gestion du cabinet dentaire", SwingConstants.CENTER);
        sousTitre.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel entete = new JPanel(new GridLayout(2, 1, 5, 5));
        entete.setBackground(Color.WHITE);
        entete.add(titre);
        entete.add(sousTitre);

        boolean medecin = utilisateurConnecte != null
                && utilisateurConnecte.getStatutUtilisateur() == StatutUtilisateur.MEDECIN;
        int nombreBoutons = 3 + (medecin ? 1 : 0) + (utilisateurConnecte != null ? 1 : 0);
        JPanel boutons = new JPanel(new GridLayout(nombreBoutons, 1, 15, 15));
        boutons.setBackground(Color.WHITE);

        JButton btnDashboard = new JButton("Tableau de bord d'activité");
        JButton btnCharges = new JButton("Gestion des charges");
        JButton btnFileAttente = new JButton("File d'attente du jour");
        JButton btnPatients = new JButton("Rechercher des patients");
        JButton btnProfil = new JButton("Mon profil");

        styliserBouton(btnDashboard);
        if (medecin) styliserBouton(btnCharges);
        styliserBouton(btnFileAttente);
        styliserBouton(btnPatients);
        if (utilisateurConnecte != null) styliserBouton(btnProfil);

        btnDashboard.addActionListener(e -> ouvrirDashboard());
        if (medecin) btnCharges.addActionListener(e -> ouvrirCharges());
        btnFileAttente.addActionListener(e -> ouvrirFileAttente());
        btnPatients.addActionListener(e -> new PatientListView(new PatientController(
                context.getBean(PatientService.class)))
                .setVisible(true));
        if (utilisateurConnecte != null) {
            btnProfil.addActionListener(e -> new MonProfilView(this, this.utilisateurConnecte,
                    new UtilisateurController(context.getBean(UtilisateurService.class)),
                    profil -> this.utilisateurConnecte = profil).setVisible(true));
        }

        boutons.add(btnDashboard);
        if (medecin) boutons.add(btnCharges);
        boutons.add(btnFileAttente);
        boutons.add(btnPatients);
        if (utilisateurConnecte != null) boutons.add(btnProfil);

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

    private void ouvrirFileAttente() {
        JFrame fenetre = new JFrame("DentalSoft - Arrivées et file d'attente");
        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetre.setContentPane(creerFileAttente());
        fenetre.setSize(800, 540);
        fenetre.setLocationRelativeTo(this);
        fenetre.setVisible(true);
    }

    private FileAttenteView creerFileAttente() {
        return new FileAttenteView(context.getBean(FileAttenteService.class));
    }

    private void ouvrirDashboard() {
        DashboardController controller = new DashboardController(context.getBean(DashboardService.class));
        DashboardView view = new DashboardView(controller, this::creerFileAttente);
        view.setVisible(true);
    }

    private void ouvrirCharges() {
        ChargeController controller = new ChargeController(context.getBean(ChargeService.class), utilisateurConnecte);
        ChargeView view = new ChargeView(controller);
        view.setVisible(true);
    }
}
