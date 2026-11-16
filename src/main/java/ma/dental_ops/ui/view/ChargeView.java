package ma.dental_ops.ui.view;

import ma.dental_ops.domain.Charge;
import ma.dental_ops.ui.controller.ChargeController;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ChargeView extends JFrame {

    private final ChargeController controller;
    private JTextField txtDateDebut;
    private JTextField txtDateFin;
    private JButton btnFiltrer;
    private JTextField txtTitre;
    private JTextField txtMontant;
    private JTextField txtDate;
    private JTextField txtAuteurId;
    private JTextArea txtDescription;
    private JTable tableCharges;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;
    private JButton btnAfficherTout;
    public ChargeView(ChargeController controller) {

        this.controller = controller;

        setTitle("DentalSoft - Gestion des charges");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 248, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));



        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titre = new JLabel("Saisie des charges du cabinet");
        titre.setForeground(new Color(20, 60, 90));
        titre.setFont(new Font("Arial", Font.BOLD, 20));


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titre, gbc);

        gbc.gridwidth = 1;

        txtTitre = new JTextField(20);
        txtMontant = new JTextField(20);
        txtDate = new JTextField(LocalDate.now().toString(), 20);
        txtAuteurId = new JTextField("1", 20);
        txtDescription = new JTextArea(4, 20);
        txtDescription.setLineWrap(true);

        ajouterChamp(panel, gbc, "Titre :", txtTitre, 1);
        ajouterChamp(panel, gbc, "Montant (MAD) :", txtMontant, 2);
        ajouterChamp(panel, gbc, "Date (AAAA-MM-JJ) :", txtDate, 3);
        ajouterChamp(panel, gbc, "ID de l'auteur :", txtAuteurId, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Description :"), gbc);

        gbc.gridx = 1;
        panel.add(new JScrollPane(txtDescription), gbc);

        JButton btnEnregistrer = new JButton("Enregistrer la charge");
        btnEnregistrer.setBackground(new Color(25, 135, 84));
        btnEnregistrer.setForeground(Color.WHITE);
        btnEnregistrer.setFont(new Font("Arial", Font.BOLD, 14));
        btnEnregistrer.setFocusPainted(false);

        btnEnregistrer.addActionListener(e -> enregistrerCharge());

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(btnEnregistrer, gbc);

// Tableau des charges
        String[] colonnes = {
                "ID", "Titre", "Montant (MAD)", "Date", "Description"
        };

        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableCharges = new JTable(tableModel);
        tableCharges.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tableCharges);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        panel.add(scrollPane, gbc);
        // Affichage du total des charges
        lblTotal = new JLabel("Total des charges : 0.00 MAD");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));

        // Filtre par période
        JPanel panelFiltre = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtDateDebut = new JTextField(10);
        txtDateFin = new JTextField(10);

        txtDateDebut.setText(LocalDate.now().withDayOfMonth(1).toString());
        txtDateFin.setText(LocalDate.now().toString());

        btnFiltrer = new JButton("Filtrer");
        btnFiltrer = new JButton("Filtrer");
        btnFiltrer.setBackground(new Color(13, 110, 253));
        btnFiltrer.setForeground(Color.WHITE);
        btnFiltrer.setFont(new Font("Arial", Font.BOLD, 13));
        btnFiltrer.setFocusPainted(false);
        btnAfficherTout = new JButton("Afficher toutes les charges");
        btnAfficherTout.setBackground(new Color(20, 60, 90));
        btnAfficherTout.setForeground(Color.WHITE);
        btnAfficherTout.setFont(new Font("Arial", Font.BOLD, 13));
        btnAfficherTout.setFocusPainted(false);
        btnFiltrer.addActionListener(e -> filtrerCharges());
        btnAfficherTout.addActionListener(e -> {
            chargerCharges();

            lblTotal.setText("Total des charges : "
                    + controller.calculerTotalCharges(
                    LocalDate.of(2000, 1, 1),
                    LocalDate.now()
            )
                    + " MAD");
        });
        panelFiltre.add(btnAfficherTout);
        panelFiltre.add(new JLabel("Date début :"));
        panelFiltre.add(txtDateDebut);
        panelFiltre.add(new JLabel("Date fin :"));
        panelFiltre.add(txtDateFin);
        panelFiltre.add(btnFiltrer);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(panelFiltre, gbc);
        btnFiltrer.addActionListener(e -> filtrerCharges());

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(lblTotal, gbc);

// Agrandir la fenêtre
        setSize(750, 650);

        add(panel);

// Charger les charges depuis MySQL
        chargerCharges();
    }

    private void filtrerCharges() {
        try {
            LocalDate dateDebut = LocalDate.parse(txtDateDebut.getText().trim());
            LocalDate dateFin = LocalDate.parse(txtDateFin.getText().trim());

            if (dateDebut.isAfter(dateFin)) {
                JOptionPane.showMessageDialog(
                        this,
                        "La date de début doit être antérieure à la date de fin.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Vider le tableau
            tableModel.setRowCount(0);

            // Récupérer les charges de la période
            List<Charge> charges = controller.afficherChargesParPeriode(
                    dateDebut, dateFin
            );

            for (Charge charge : charges) {
                Object[] ligne = {
                        charge.getIdCharge(),
                        charge.getTitre(),
                        charge.getMontant(),
                        charge.getDateCharge(),
                        charge.getDescription()
                };

                tableModel.addRow(ligne);
            }

            // Calculer le total de la période
            java.math.BigDecimal total = controller.calculerTotalCharges(
                    dateDebut, dateFin
            );

            lblTotal.setText("Total des charges de la période : " + total + " MAD");

        } catch (java.time.format.DateTimeParseException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Format de date invalide. Utilisez AAAA-MM-JJ.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors du filtrage : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void chargerCharges() {

        tableModel.setRowCount(0);

        List<Charge> charges = controller.afficherToutesLesCharges();

        for (Charge charge : charges) {
            Object[] ligne = {
                    charge.getIdCharge(),
                    charge.getTitre(),
                    charge.getMontant(),
                    charge.getDateCharge(),
                    charge.getDescription()
            };

            tableModel.addRow(ligne);
        }
        java.math.BigDecimal total = controller.calculerTotalCharges(
                LocalDate.of(2000, 1, 1),
                LocalDate.now()
        );

        lblTotal.setText("Total des charges : " + total + " MAD");
    }

    private void ajouterChamp(
            JPanel panel,
            GridBagConstraints gbc,
            String label,
            JTextField champ,
            int ligne) {

        gbc.gridx = 0;
        gbc.gridy = ligne;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(champ, gbc);
    }

    private void enregistrerCharge() {

        try {
            Charge charge = Charge.builder()
                    .titre(txtTitre.getText().trim())
                    .montant(new BigDecimal(txtMontant.getText().trim()))
                    .dateCharge(LocalDate.parse(txtDate.getText().trim()))
                    .description(txtDescription.getText().trim())
                    .auteurId(Long.parseLong(txtAuteurId.getText().trim()))
                    .build();

            controller.ajouterCharge(
                    charge.getTitre(),
                    charge.getMontant(),
                    charge.getDateCharge(),
                    charge.getDescription(),
                    charge.getAuteurId()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Charge enregistrée avec succès !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE
            );
            chargerCharges();

            txtTitre.setText("");
            txtMontant.setText("");
            txtDate.setText(LocalDate.now().toString());
            txtDescription.setText("");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erreur : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}