
package ma.dental_ops.ui.view;

import ma.dental_ops.service.api.DashboardService;
import ma.dental_ops.service.implementation.DashboardServiceImpl;
import ma.dental_ops.ui.controller.DashboardController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

public class DashboardPanel extends JPanel {

    private final Color petrolBlue = new Color(15, 85, 96);
    private final Color turquoise = new Color(14, 140, 151);
    private final Color background = new Color(245, 250, 251);
    private final Color textColor = new Color(11, 60, 73);

    private JLabel rendezVousJourValue;
    private JLabel rendezVousMoisValue;
    private JLabel patientsRecusValue;
    private JLabel consultationsValue;

    private final DashboardController controller;

    public DashboardPanel() {
        this(new DashboardController(new DashboardServiceImpl()));
    }

    public DashboardPanel(DashboardController controller) {
        this.controller = controller;

        setLayout(new BorderLayout(20, 20));
        setBackground(background);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        construireInterface();
        chargerStatistiques();
    }

    private void construireInterface() {

        // En-tête
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        JLabel title = new JLabel("Tableau de bord");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(petrolBlue);

        JLabel subtitle = new JLabel(
                "Bienvenue dans votre espace de gestion DentalSoft"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(textColor);

        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // Contenu
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        // Cartes statistiques
        JPanel statistics = new JPanel(new GridLayout(1, 4, 15, 15));
        statistics.setOpaque(false);

        rendezVousJourValue = new JLabel("...");
        rendezVousMoisValue = new JLabel("...");
        patientsRecusValue = new JLabel("...");
        consultationsValue = new JLabel("...");

        statistics.add(createStatCard(
                "Rendez-vous aujourd'hui",
                rendezVousJourValue,
                "Planning du jour"
        ));

        statistics.add(createStatCard(
                "Rendez-vous du mois",
                rendezVousMoisValue,
                "Hors rendez-vous annulés"
        ));

        statistics.add(createStatCard(
                "Patients reçus",
                patientsRecusValue,
                "Patients ayant terminé leur rendez-vous"
        ));

        statistics.add(createStatCard(
                "Consultations",
                consultationsValue,
                "Consultations enregistrées"
        ));

        content.add(statistics);
        content.add(Box.createVerticalStrut(25));

        // Activité récente
        JPanel activity = createSection("Activité récente");

        JLabel activityMessage = new JLabel(
                "Les statistiques sont calculées à partir des données enregistrées."
        );
        activityMessage.setFont(new Font("SansSerif", Font.PLAIN, 14));
        activityMessage.setForeground(Color.GRAY);

        activity.add(activityMessage, BorderLayout.CENTER);

        content.add(activity);

        add(content, BorderLayout.CENTER);
    }

    private JPanel createStatCard(
            String title,
            JLabel valueLabel,
            String description) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 235, 237)
                ),
                new EmptyBorder(18, 15, 18, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setForeground(textColor);

        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        valueLabel.setForeground(turquoise);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        descriptionLabel.setForeground(Color.GRAY);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(descriptionLabel);

        return card;
    }

    private JPanel createSection(String title) {

        JPanel section = new JPanel(new BorderLayout(10, 15));
        section.setBackground(Color.WHITE);

        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 235, 237)
                ),
                new EmptyBorder(18, 20, 18, 20)
        ));

        JLabel sectionTitle = new JLabel(title);
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 17));
        sectionTitle.setForeground(petrolBlue);

        section.add(sectionTitle, BorderLayout.NORTH);

        return section;
    }

    private void chargerStatistiques() {

        try {
            LocalDate aujourdHui = LocalDate.now();
            LocalDate debutMois = aujourdHui.withDayOfMonth(1);

            long rendezVousJour =
                    controller.afficherNombreRendezVousDuJour();

            long rendezVousMois =
                    controller.afficherNombreRendezVous(
                            debutMois, aujourdHui
                    );

            long patientsRecus =
                    controller.afficherNombrePatientsRecus(
                            debutMois, aujourdHui
                    );

            long consultations =
                    controller.afficherNombreConsultations(
                            debutMois, aujourdHui
                    );

            rendezVousJourValue.setText(String.valueOf(rendezVousJour));
            rendezVousMoisValue.setText(String.valueOf(rendezVousMois));
            patientsRecusValue.setText(String.valueOf(patientsRecus));
            consultationsValue.setText(String.valueOf(consultations));

        } catch (RuntimeException e) {
            e.printStackTrace();
            rendezVousJourValue.setText("!");
            rendezVousMoisValue.setText("!");
            patientsRecusValue.setText("!");
            consultationsValue.setText("!");

            System.err.println(
                    "Erreur lors du chargement du tableau de bord : "
                            + e.getMessage()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Impossible de charger les statistiques.\n"
                            + "Vérifiez la connexion à la base de données.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
