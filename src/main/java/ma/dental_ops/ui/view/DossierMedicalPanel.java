
package ma.dental_ops.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DossierMedicalPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public DossierMedicalPanel() {

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 250, 251));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titre = new JLabel("Gestion des dossiers médicaux");
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(new Color(15, 85, 96));

        String[] colonnes = {
                "ID", "Patient", "Antécédents",
                "Allergies", "Traitements", "Observations"
        };

        model = new DefaultTableModel(colonnes, 0);

        model.addRow(new Object[]{
                1, "Sara Alami", "Aucun",
                "Pénicilline", "Aucun", "Contrôle régulier"
        });

        model.addRow(new Object[]{
                2, "Youssef Benali", "Hypertension",
                "Aucune", "Traitement médical", "Suivi nécessaire"
        });

        table = new JTable(model);
        table.setRowHeight(35);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton ajouter = new JButton("Ajouter");
        JButton modifier = new JButton("Modifier");
        JButton supprimer = new JButton("Supprimer");

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setOpaque(false);

        actions.add(ajouter);
        actions.add(modifier);
        actions.add(supprimer);

        ajouter.addActionListener(e -> ajouterDossier());
        modifier.addActionListener(e -> modifierDossier());
        supprimer.addActionListener(e -> supprimerDossier());

        add(titre, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
    }

    private void ajouterDossier() {
        JTextField patient = new JTextField();
        JTextField antecedents = new JTextField();
        JTextField allergies = new JTextField();
        JTextField traitements = new JTextField();
        JTextField observations = new JTextField();

        Object[] champs = {
                "Nom du patient :", patient,
                "Antécédents :", antecedents,
                "Allergies :", allergies,
                "Traitements :", traitements,
                "Observations :", observations
        };

        int resultat = JOptionPane.showConfirmDialog(
                this, champs, "Ajouter un dossier médical",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultat == JOptionPane.OK_OPTION) {
            model.addRow(new Object[]{
                    model.getRowCount() + 1,
                    patient.getText(),
                    antecedents.getText(),
                    allergies.getText(),
                    traitements.getText(),
                    observations.getText()
            });
        }
    }

    private void modifierDossier() {
        int ligne = table.getSelectedRow();

        if (ligne == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un dossier.");
            return;
        }

        for (int colonne = 1; colonne < model.getColumnCount(); colonne++) {
            String valeur = JOptionPane.showInputDialog(
                    this,
                    "Modifier " + model.getColumnName(colonne),
                    model.getValueAt(ligne, colonne)
            );

            if (valeur != null) {
                model.setValueAt(valeur, ligne, colonne);
            }
        }
    }

    private void supprimerDossier() {
        int ligne = table.getSelectedRow();

        if (ligne == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un dossier.");
            return;
        }

        model.removeRow(ligne);
    }
}
