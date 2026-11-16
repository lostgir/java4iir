package ma.dental_ops.ui.view;

import ma.dental_ops.ui.controller.DashboardController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

public class DashboardView extends JFrame {

    private final DashboardController controller;

    private final JLabel lblRendezVousJour = new JLabel("0");
    private final JLabel lblRendezVous = new JLabel("0");
    private final JLabel lblPatientsRecus = new JLabel("0");
    private final JLabel lblConsultations = new JLabel("0");

    private final JTextField txtDateDebut =
            new JTextField(LocalDate.now().toString(), 12);

    private final JTextField txtDateFin =
            new JTextField(LocalDate.now().toString(), 12);

    private final Color bleuFonce = new Color(7, 23, 57);
    private final Color vert = new Color(20, 130, 90);
    private final Color bleu = new Color(45, 105, 190);
    private final Color orange = new Color(225, 145, 45);
    private final Color violet = new Color(130, 90, 180);
    private final Color fond = new Color(242, 245, 249);

    public DashboardView(DashboardController controller) {
        this.controller = controller;

        setTitle("DentalSoft - Tableau de bord d'activité");
        setSize(1000, 650);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initialiserInterface();
        actualiserStatistiques();
    }

    private void initialiserInterface() {

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(new Color(242, 245, 249));

        // BARRE LATERALE
        JPanel menu = creerMenuLateral();
        principal.add(menu, BorderLayout.WEST);

        // ZONE PRINCIPALE
        JPanel contenu = new JPanel(new BorderLayout(20, 20));
        contenu.setBackground(new Color(242, 245, 249));
        contenu.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // EN-TETE
        JPanel entete = new JPanel(new BorderLayout());
        entete.setBackground(Color.WHITE);
        entete.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel titre = new JLabel("Tableau de bord");
        titre.setFont(new Font("Arial", Font.BOLD, 25));
        titre.setForeground(new Color(7, 23, 57));

        JLabel description = new JLabel("Vue générale de l'activité du cabinet");
        description.setFont(new Font("Arial", Font.PLAIN, 13));
        description.setForeground(Color.GRAY);

        JPanel textes = new JPanel(new GridLayout(2, 1, 0, 5));
        textes.setBackground(Color.WHITE);
        textes.add(titre);
        textes.add(description);

        entete.add(textes, BorderLayout.WEST);

// PROFIL UTILISATEUR
        JPanel profil = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        profil.setBackground(Color.WHITE);

        JLabel avatar = new JLabel("AD");
        avatar.setOpaque(true);
        avatar.setBackground(new Color(15, 145, 170));
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("Arial", Font.BOLD, 15));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setPreferredSize(new Dimension(40, 40));

        JPanel informations = new JPanel(new GridLayout(2, 1, 0, 4));
        informations.setBackground(Color.WHITE);

        JLabel nomUtilisateur = new JLabel("Administrateur");
        nomUtilisateur.setFont(new Font("Arial", Font.BOLD, 13));

        JLabel roleUtilisateur = new JLabel("Gestion du cabinet");
        roleUtilisateur.setFont(new Font("Arial", Font.PLAIN, 11));
        roleUtilisateur.setForeground(Color.GRAY);

        informations.add(nomUtilisateur);
        informations.add(roleUtilisateur);

        profil.add(avatar);
        profil.add(informations);

        entete.add(profil, BorderLayout.EAST);

        contenu.add(entete, BorderLayout.NORTH);

        // ZONE DES STATISTIQUES
        JPanel statistiques = new JPanel(new GridLayout(2, 2, 20, 20));
        statistiques.setBackground(new Color(242, 245, 249));

        statistiques.add(creerCarte(
                "RENDEZ-VOUS AUJOURD'HUI",
                lblRendezVousJour, "RDV", vert));

        statistiques.add(creerCarte(
                "RENDEZ-VOUS SUR LA PÉRIODE",
                lblRendezVous, "CAL", bleu));

        statistiques.add(creerCarte(
                "PATIENTS REÇUS",
                lblPatientsRecus, "PAT", orange));

        statistiques.add(creerCarte(
                "CONSULTATIONS",
                lblConsultations, "CONS", violet));

        contenu.add(statistiques, BorderLayout.CENTER);

        // FILTRE ET ACTUALISATION
        JPanel bas = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        bas.setBackground(Color.WHITE);
        bas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bas.add(new JLabel("Date début :"));
        styliserChampDate(txtDateDebut);
        bas.add(txtDateDebut);

        bas.add(new JLabel("Date fin :"));
        styliserChampDate(txtDateFin);
        bas.add(txtDateFin);

        JButton btnFiltrer = new JButton("Filtrer");
        styliserBouton(btnFiltrer, bleu);
        btnFiltrer.addActionListener(e -> filtrerStatistiques());
        bas.add(btnFiltrer);

        JButton btnActualiser = new JButton("Actualiser");
        styliserBouton(btnActualiser, vert);
        btnActualiser.addActionListener(e -> actualiserStatistiques());
        bas.add(btnActualiser);

        contenu.add(bas, BorderLayout.SOUTH);

        principal.add(contenu, BorderLayout.CENTER);

        add(principal);
    }

    private JPanel creerCarte(
            String titre,
            JLabel valeur,
            String symbole,
            Color couleur
    ) {

        JPanel carte = new JPanel(new BorderLayout(10, 15));
        carte.setBackground(Color.WHITE);
        carte.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, couleur),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblSymbole = new JLabel(symbole);
        lblSymbole.setFont(new Font("Arial", Font.BOLD, 16));
        lblSymbole.setForeground(couleur);

        JLabel lblTitre = new JLabel(titre);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 13));
        lblTitre.setForeground(new Color(90, 100, 115));

        JPanel haut = new JPanel(new BorderLayout());
        haut.setBackground(Color.WHITE);
        haut.add(lblSymbole, BorderLayout.WEST);
        haut.add(lblTitre, BorderLayout.SOUTH);

        valeur.setFont(new Font("Arial", Font.BOLD, 38));
        valeur.setForeground(couleur);
        valeur.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel lblDescription = new JLabel("Données issues de la base de données");
        lblDescription.setFont(new Font("Arial", Font.PLAIN, 11));
        lblDescription.setForeground(Color.GRAY);

        JPanel bas = new JPanel(new BorderLayout(5, 5));
        bas.setBackground(Color.WHITE);
        bas.add(valeur, BorderLayout.CENTER);
        bas.add(lblDescription, BorderLayout.SOUTH);

        carte.add(haut, BorderLayout.NORTH);
        carte.add(bas, BorderLayout.CENTER);

        return carte;
    }

    private void styliserChampDate(JTextField champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 13));
        champ.setPreferredSize(new Dimension(125, 35));
        champ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                new EmptyBorder(5, 8, 5, 8)
        ));
    }

    private void styliserBouton(JButton bouton, Color couleur) {
        bouton.setFont(new Font("Arial", Font.BOLD, 13));
        bouton.setBackground(couleur);
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
        bouton.setBorder(new EmptyBorder(10, 18, 10, 18));
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void actualiserStatistiques() {
        LocalDate aujourdHui = LocalDate.now();
        afficherStatistiques(aujourdHui, aujourdHui);
    }

    private void filtrerStatistiques() {
        try {
            LocalDate debut =
                    LocalDate.parse(txtDateDebut.getText().trim());

            LocalDate fin =
                    LocalDate.parse(txtDateFin.getText().trim());

            if (debut.isAfter(fin)) {
                JOptionPane.showMessageDialog(
                        this,
                        "La date de début doit être antérieure à la date de fin.",
                        "Erreur de date",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            afficherStatistiques(debut, fin);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Format invalide. Utilisez le format AAAA-MM-JJ.",
                    "Erreur de date",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void afficherStatistiques(LocalDate debut, LocalDate fin) {
        try {
            lblRendezVousJour.setText(
                    String.valueOf(controller.afficherNombreRendezVousDuJour())
            );

            lblRendezVous.setText(
                    String.valueOf(controller.afficherNombreRendezVous(debut, fin))
            );

            lblPatientsRecus.setText(
                    String.valueOf(controller.afficherNombrePatientsRecus(debut, fin))
            );

            lblConsultations.setText(
                    String.valueOf(controller.afficherNombreConsultations(debut, fin))
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors du chargement des statistiques : "
                            + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private JPanel creerMenuLateral() {

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(7, 23, 57));
        menu.setPreferredSize(new Dimension(210, 0));
        menu.setBorder(BorderFactory.createEmptyBorder(25, 15, 20, 15));

        JLabel logo = new JLabel("DentalSoft");
        logo.setFont(new Font("Arial", Font.BOLD, 23));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sousTitre = new JLabel("Gestion du cabinet dentaire");
        sousTitre.setFont(new Font("Arial", Font.PLAIN, 10));
        sousTitre.setForeground(new Color(180, 205, 225));
        sousTitre.setAlignmentX(Component.LEFT_ALIGNMENT);

        menu.add(logo);
        menu.add(Box.createVerticalStrut(5));
        menu.add(sousTitre);
        menu.add(Box.createVerticalStrut(45));

        menu.add(creerBoutonMenu("Tableau de bord"));
        menu.add(Box.createVerticalStrut(12));

        menu.add(creerBoutonMenu("Patients"));
        menu.add(Box.createVerticalStrut(12));

        menu.add(creerBoutonMenu("Rendez-vous"));
        menu.add(Box.createVerticalStrut(12));

        menu.add(creerBoutonMenu("Charges"));
        menu.add(Box.createVerticalStrut(12));

        menu.add(creerBoutonMenu("Rapports"));

        return menu;
    }

    private JButton creerBoutonMenu(String texte) {

        JButton bouton = new JButton(texte);

        bouton.setFont(new Font("Arial", Font.BOLD, 13));
        bouton.setForeground(Color.WHITE);
        bouton.setBackground(new Color(7, 23, 57));
        bouton.setHorizontalAlignment(SwingConstants.LEFT);
        bouton.setMaximumSize(new Dimension(190, 42));
        bouton.setPreferredSize(new Dimension(190, 42));
        bouton.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 5));
        bouton.setFocusPainted(false);
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (texte.equals("Tableau de bord")) {
            bouton.setBackground(new Color(15, 145, 170));
        }

        return bouton;
    }
}