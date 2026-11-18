package ma.dental_ops.ui.view;

import ma.dental_ops.domain.Charge;
import ma.dental_ops.dto.PageResult;
import ma.dental_ops.ui.controller.ChargeController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class ChargeView extends JFrame {
    private static final int PAGE_SIZE = 20;
    private final ChargeController controller;
    private final JTextField titre = new JTextField(22);
    private final JTextField montant = new JTextField(12);
    private final JTextField dateCharge = new JTextField(LocalDate.now().toString(), 12);
    private final JTextArea description = new JTextArea(3, 22);
    private final JTextField dateDebut = new JTextField(LocalDate.now().withDayOfMonth(1).toString(), 10);
    private final JTextField dateFin = new JTextField(LocalDate.now().toString(), 10);
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Date", "Titre", "Description", "Montant (MAD)"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JLabel total = new JLabel("Total de la période : 0,00 MAD");
    private final JLabel pageInfo = new JLabel("Page 0 / 0");
    private final JButton precedent = new JButton("Précédent");
    private final JButton suivant = new JButton("Suivant");
    private int page;

    public ChargeView(ChargeController controller) {
        this.controller = java.util.Objects.requireNonNull(controller);
        setTitle("DentalSoft - Charges du cabinet");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(820, 580));
        setSize(980, 680);
        setLocationRelativeTo(null);
        construireInterface();
        actualiser();
    }

    private void construireInterface() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        root.setBackground(new Color(245, 248, 250));

        JLabel titrePage = new JLabel("Charges du cabinet");
        titrePage.setFont(new Font("Arial", Font.BOLD, 24));
        titrePage.setForeground(new Color(20, 60, 90));
        root.add(titrePage, BorderLayout.NORTH);

        JPanel centre = new JPanel(new BorderLayout(12, 12));
        centre.setOpaque(false);
        centre.add(creerFormulaire(), BorderLayout.NORTH);

        table.setRowHeight(28);
        table.setFillsViewportHeight(true);
        centre.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bas = new JPanel(new BorderLayout(8, 8));
        bas.setOpaque(false);
        JPanel filtre = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        filtre.setOpaque(false);
        filtre.add(new JLabel("Période du")); filtre.add(dateDebut);
        filtre.add(new JLabel("au")); filtre.add(dateFin);
        JButton filtrer = new JButton("Appliquer");
        filtrer.addActionListener(e -> { page = 0; actualiser(); });
        filtre.add(filtrer);
        total.setFont(new Font("Arial", Font.BOLD, 15));
        bas.add(filtre, BorderLayout.NORTH);
        bas.add(total, BorderLayout.CENTER);
        JPanel pagination = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pagination.setOpaque(false);
        precedent.addActionListener(e -> { if (page > 0) { page--; actualiser(); } });
        suivant.addActionListener(e -> { page++; actualiser(); });
        pagination.add(precedent); pagination.add(pageInfo); pagination.add(suivant);
        bas.add(pagination, BorderLayout.SOUTH);
        centre.add(bas, BorderLayout.SOUTH);
        root.add(centre, BorderLayout.CENTER);
        add(root);
    }

    private JPanel creerFormulaire() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Enregistrer une charge"),
                BorderFactory.createEmptyBorder(8, 10, 10, 10)));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 6, 5, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 0;
        champ(form, c, "Titre", titre, 0, 0);
        champ(form, c, "Montant (MAD)", montant, 2, 0);
        champ(form, c, "Date (AAAA-MM-JJ)", dateCharge, 0, 1);
        c.gridx = 2; c.gridy = 1; form.add(new JLabel("Description"), c);
        c.gridx = 3; c.weightx = 1; c.weighty = 1; c.fill = GridBagConstraints.BOTH;
        description.setLineWrap(true); description.setWrapStyleWord(true);
        form.add(new JScrollPane(description), c);
        JButton enregistrer = new JButton("Enregistrer");
        enregistrer.setBackground(new Color(25, 135, 84));
        enregistrer.setForeground(Color.WHITE);
        enregistrer.addActionListener(e -> enregistrer());
        c.gridx = 4; c.gridy = 0; c.gridheight = 2; c.weightx = 0; c.weighty = 0;
        c.fill = GridBagConstraints.NONE; c.anchor = GridBagConstraints.CENTER;
        form.add(enregistrer, c);
        return form;
    }

    private void champ(JPanel panel, GridBagConstraints c, String label, JComponent input, int x, int y) {
        c.gridx = x; c.gridy = y; c.weightx = 0; c.weighty = 0; c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL; panel.add(new JLabel(label), c);
        c.gridx = x + 1; c.weightx = 1; panel.add(input, c);
    }

    private void enregistrer() {
        try {
            BigDecimal valeur = new BigDecimal(montant.getText().trim().replace(',', '.'));
            LocalDate date = LocalDate.parse(dateCharge.getText().trim());
            controller.ajouterCharge(titre.getText(), valeur, date, description.getText());
            JOptionPane.showMessageDialog(this, "La charge a été enregistrée.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            titre.setText(""); montant.setText(""); description.setText("");
            dateCharge.setText(LocalDate.now().toString());
            page = 0;
            actualiser();
        } catch (DateTimeParseException ex) {
            erreur("Date invalide. Utilise le format AAAA-MM-JJ.");
        } catch (NumberFormatException ex) {
            erreur("Montant invalide. Exemple : 200,00");
        } catch (Exception ex) {
            erreur(ex.getMessage() == null ? "Impossible d'enregistrer la charge." : ex.getMessage());
        }
    }

    private void actualiser() {
        try {
            LocalDate debut = LocalDate.parse(dateDebut.getText().trim());
            LocalDate fin = LocalDate.parse(dateFin.getText().trim());
            PageResult<Charge> resultat = controller.afficherChargesParPeriode(debut, fin, page, PAGE_SIZE);
            model.setRowCount(0);
            NumberFormat format = NumberFormat.getNumberInstance(Locale.FRANCE);
            format.setMinimumFractionDigits(2); format.setMaximumFractionDigits(2);
            for (Charge charge : resultat.items()) {
                model.addRow(new Object[]{charge.getDateCharge(), charge.getTitre(),
                        charge.getDescription() == null ? "" : charge.getDescription(),
                        format.format(charge.getMontant())});
            }
            total.setText("Total des charges de la période : " + format.format(
                    controller.calculerTotalCharges(debut, fin)) + " MAD — Solde : "
                    + format.format(controller.calculerSolde(debut, fin)) + " MAD");
            int pages = resultat.totalPages();
            pageInfo.setText("Page " + (pages == 0 ? 0 : page + 1) + " / " + pages
                    + " — " + resultat.totalItems() + " charge(s)");
            precedent.setEnabled(page > 0);
            suivant.setEnabled(page + 1 < pages);
        } catch (DateTimeParseException ex) {
            erreur("Période invalide. Utilise le format AAAA-MM-JJ.");
        } catch (Exception ex) {
            erreur(ex.getMessage() == null ? "Impossible de charger les charges." : ex.getMessage());
        }
    }

    private void erreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
