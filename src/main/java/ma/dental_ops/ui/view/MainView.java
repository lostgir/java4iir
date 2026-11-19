package ma.dental_ops.ui.view;

import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.common.enums.Droit;
import ma.dental_ops.common.security.Session;
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
    private final Runnable onLogout;
    private final ApplicationContext context;

    public MainView() {
        this(null, null);
    }

    public MainView(Utilisateur utilisateurConnecte) {
        this(utilisateurConnecte, null);
    }

    public MainView(Utilisateur utilisateurConnecte, Runnable onLogout) {
        this.utilisateurConnecte = utilisateurConnecte;
        this.onLogout = onLogout;
        this.context = ApplicationContext.getInstance();
        setTitle("DentalSoft - Menu principal");
        setSize(600, 400);
        setDefaultCloseOperation(onLogout != null ? JFrame.DISPOSE_ON_CLOSE : JFrame.EXIT_ON_CLOSE);
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

        // Les menus suivent les droits du rôle connecté (§1.4). Masquer un
        // menu ne suffit pas : chaque service vérifie aussi le droit (BNF-01).
        Session session = Session.getInstance();
        boolean dashboard = session.peut(Droit.CONSULTER_TABLEAU_BORD);
        boolean charges = session.peut(Droit.GERER_CHARGES);
        boolean fileAttente = session.peut(Droit.GERER_AGENDA);
        boolean patients = session.peut(Droit.GERER_PATIENTS);
        boolean profil = utilisateurConnecte != null && session.peut(Droit.GERER_PROFIL);
        int nombreBoutons = Math.max(1, (dashboard ? 1 : 0) + (charges ? 1 : 0) + (fileAttente ? 1 : 0)
                + (patients ? 1 : 0) + (profil ? 1 : 0) + (onLogout != null ? 1 : 0));
        JPanel boutons = new JPanel(new GridLayout(nombreBoutons, 1, 15, 15));
        boutons.setBackground(Color.WHITE);

        if (dashboard) {
            JButton btnDashboard = new JButton("Tableau de bord d'activité");
            styliserBouton(btnDashboard);
            btnDashboard.addActionListener(e -> ouvrirDashboard());
            boutons.add(btnDashboard);
        }
        if (charges) {
            JButton btnCharges = new JButton("Gestion des charges");
            styliserBouton(btnCharges);
            btnCharges.addActionListener(e -> ouvrirCharges());
            boutons.add(btnCharges);
        }
        if (fileAttente) {
            JButton btnFileAttente = new JButton("File d'attente du jour");
            styliserBouton(btnFileAttente);
            btnFileAttente.addActionListener(e -> ouvrirFileAttente());
            boutons.add(btnFileAttente);
        }
        if (patients) {
            JButton btnPatients = new JButton("Rechercher des patients");
            styliserBouton(btnPatients);
            btnPatients.addActionListener(e -> new PatientListView(new PatientController(
                    context.getBean(PatientService.class)))
                    .setVisible(true));
            boutons.add(btnPatients);
        }
        if (profil) {
            JButton btnProfil = new JButton("Mon profil");
            styliserBouton(btnProfil);
            btnProfil.addActionListener(e -> new MonProfilView(this, this.utilisateurConnecte,
                    new UtilisateurController(context.getBean(UtilisateurService.class)), p -> this.utilisateurConnecte = p).setVisible(true));
            boutons.add(btnProfil);
        }
        if (onLogout != null) {
            JButton btnLogout = new JButton("Se déconnecter");
            styliserBouton(btnLogout);
            btnLogout.addActionListener(e -> {
                dispose();
                onLogout.run();
            });
            boutons.add(btnLogout);
        }

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
