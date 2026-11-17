
package ma.dental_ops.ui.view;

import ma.dental_ops.common.enums.Session;
import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.domain.Utilisateur;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final JPanel contentPanel;
    private final CardLayout cardLayout;

    private final Color petrolBlue = new Color(15, 85, 96);
    private final Color turquoise = new Color(14, 140, 151);
    private final Color background = new Color(245, 250, 251);

    public MainWindow() {

        // Récupérer l'utilisateur connecté
        Utilisateur utilisateur = Session.getUtilisateurConnecte();

        if (utilisateur == null) {
            throw new IllegalStateException(
                    "Aucun utilisateur connecté."
            );
        }

        // Configuration de la fenêtre
        setTitle("DentalSoft");
        ImageIcon logoIcon = new ImageIcon(
                getClass().getResource("/images/logo-dentalsoft.png")
        );

        setIconImage(logoIcon.getImage());
        setSize(1200, 750);
        setMinimumSize(new Dimension(950, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(background);

        // Barre latérale
        JPanel sidebar = createSidebar(utilisateur);

        // Partie centrale
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(background);

        // Barre supérieure
        JPanel topBar = createTopBar(utilisateur);

        // Gestion des pages
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(background);

        // Pages principales
        addPage("Tableau de bord",
                "Bienvenue sur DentalSoft",
                new JPanel());

        addPage("Patients",
                "Gestion des patients",
                new PatientPanel());

        addPage("Rendez-vous",
                "Gestion des rendez-vous",
                new RendezVousPanel());

        addPage("Dossiers médicaux",
                "Gestion des dossiers médicaux",
                new DossierMedicalPanel());

        // Page Utilisateurs réservée à l'administrateur
        if (utilisateur.getStatutUtilisateur()
                == StatutUtilisateur.ADMINISTRATEUR) {

            contentPanel.add(
                    new UtilisateurPanel(),
                    "Utilisateurs"
            );
        }

        // Assemblage de la partie centrale
        centerPanel.add(topBar, BorderLayout.NORTH);
        centerPanel.add(contentPanel, BorderLayout.CENTER);

        // Assemblage de la fenêtre
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Page affichée au démarrage
        cardLayout.show(contentPanel, "Tableau de bord");
    }

    // Création de la barre latérale
    private JPanel createSidebar(Utilisateur utilisateur) {

        JPanel sidebar = new JPanel();

        sidebar.setLayout(
                new BoxLayout(sidebar, BoxLayout.Y_AXIS)
        );

        sidebar.setBackground(petrolBlue);
        sidebar.setPreferredSize(new Dimension(230, 0));

        sidebar.setBorder(
                BorderFactory.createEmptyBorder(25, 15, 20, 15)
        );
        ImageIcon logoIcon = new ImageIcon(
                getClass().getResource("/images/logo-dentalsoft.png")
        );

        Image image = logoIcon.getImage().getScaledInstance(
                180, 120, Image.SCALE_SMOOTH
        );

        JLabel logo = new JLabel(new ImageIcon(image));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(40));

        // Menus communs
        addMenuButton(sidebar, "Tableau de bord");
        addMenuButton(sidebar, "Patients");
        addMenuButton(sidebar, "Rendez-vous");
        addMenuButton(sidebar, "Dossiers médicaux");

        // Menu réservé à l'administrateur
        if (utilisateur.getStatutUtilisateur()
                == StatutUtilisateur.ADMINISTRATEUR) {

            addMenuButton(sidebar, "Utilisateurs");
        }

        sidebar.add(Box.createVerticalGlue());

        JLabel role = new JLabel(
                "Rôle : " + utilisateur.getStatutUtilisateur()
        );

        role.setForeground(Color.WHITE);
        role.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(role);

        return sidebar;
    }

    // Création des boutons du menu
    private void addMenuButton(JPanel sidebar, String pageName) {

        JButton button = new JButton(pageName);

        button.setMaximumSize(new Dimension(200, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(petrolBlue);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addActionListener(e ->
                cardLayout.show(contentPanel, pageName)
        );

        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(10));
    }

    // Création de la barre supérieure
    private JPanel createTopBar(Utilisateur utilisateur) {

        JPanel topBar = new JPanel(new BorderLayout(20, 0));

        topBar.setBackground(Color.WHITE);

        topBar.setBorder(
                BorderFactory.createEmptyBorder(15, 25, 15, 25)
        );

        // Nom de l'utilisateur connecté
        JLabel welcome = new JLabel(
                "Bienvenue, "
                        + utilisateur.getPrenomUtilisateur()
                        + " "
                        + utilisateur.getNomUtilisateur()
        );

        welcome.setFont(new Font("SansSerif", Font.BOLD, 16));
        welcome.setForeground(petrolBlue);

        // Barre de recherche
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setToolTipText("Rechercher une page");

        JButton searchButton = new JButton("Rechercher");
        searchButton.setBackground(turquoise);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(Color.WHITE);

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Action de recherche
        searchButton.addActionListener(e ->
                rechercherPage(searchField.getText())
        );

        searchField.addActionListener(e ->
                rechercherPage(searchField.getText())
        );

        // Bouton Déconnexion
        JButton logoutButton = new JButton("Déconnexion");

        logoutButton.setBackground(turquoise);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);

        logoutButton.addActionListener(e -> deconnexion());

        // Assemblage de la barre supérieure
        topBar.add(welcome, BorderLayout.WEST);
        topBar.add(searchPanel, BorderLayout.CENTER);
        topBar.add(logoutButton, BorderLayout.EAST);

        return topBar;
    }

    // Recherche d'une page dans le menu
    private void rechercherPage(String recherche) {

        if (recherche == null || recherche.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez saisir un nom de page."
            );
            return;
        }

        String texte = recherche.trim().toLowerCase();

        String[] pages = {
                "Tableau de bord",
                "Patients",
                "Rendez-vous",
                "Dossiers médicaux",
                "Utilisateurs"
        };

        for (String page : pages) {

            if (page.toLowerCase().contains(texte)
                    && (page.equals("Tableau de bord")
                    || page.equals("Patients")
                    || page.equals("Rendez-vous")
                    || page.equals("Dossiers médicaux")
                    || Session.getUtilisateurConnecte()
                    .getStatutUtilisateur()
                    == StatutUtilisateur.ADMINISTRATEUR)) {

                cardLayout.show(contentPanel, page);
                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Aucune page trouvée."
        );
    }

    // Ajouter une page
    private void addPage(String pageName, String message, JPanel panel) {

        if (panel instanceof PatientPanel ||
                panel instanceof RendezVousPanel ||
                panel instanceof DossierMedicalPanel) {

            contentPanel.add(panel, pageName);
            return;
        }

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(background);

        JLabel title = new JLabel(message);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(petrolBlue);

        page.add(title, BorderLayout.NORTH);

        contentPanel.add(page, pageName);
    }

    // Déconnexion
    private void deconnexion() {

        int choix = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment vous déconnecter ?",
                "Déconnexion",
                JOptionPane.YES_NO_OPTION
        );

        if (choix == JOptionPane.YES_OPTION) {

            Session.fermerSession();

            dispose();

            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        }
    }
}
