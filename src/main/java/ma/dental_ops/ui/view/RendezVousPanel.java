
package ma.dental_ops.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class RendezVousPanel extends JPanel {

    private final DefaultTableModel model;
    private final JTable table;
    private final TableRowSorter<DefaultTableModel> sorter;

    public RendezVousPanel() {

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 250, 251));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titre = new JLabel("Gestion des rendez-vous");
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(new Color(11, 60, 73));

        // Recherche
        JTextField rechercheField = new JTextField(18);

        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        recherchePanel.setOpaque(false);
        recherchePanel.add(new JLabel("Rechercher :"));
        recherchePanel.add(rechercheField);

        // Bouton Ajouter
        JButton btnAjouter = new JButton("+ Ajouter rendez-vous");
        btnAjouter.setBackground(new Color(14, 140, 151));
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.setFocusPainted(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(titre, BorderLayout.WEST);
        header.add(btnAjouter, BorderLayout.EAST);

        JPanel haut = new JPanel(new BorderLayout(10, 10));
        haut.setOpaque(false);
        haut.add(header, BorderLayout.NORTH);
        haut.add(recherchePanel, BorderLayout.SOUTH);

        // Tableau
        String[] colonnes = {
                "ID", "Patient", "Date", "Heure", "Motif", "Statut"
        };

        model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));

        table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        table.getTableHeader().setBackground(
                new Color(224, 244, 245)
        );

        // Données temporaires
        model.addRow(new Object[]{
                1, "Sara Alami", "15/11/2026",
                "09:00", "Consultation", "CONFIRME"
        });

        model.addRow(new Object[]{
                2, "Youssef Benali", "16/11/2026",
                "10:30", "Détartrage", "EN ATTENTE"
        });

        model.addRow(new Object[]{
                3, "Salma Amrani", "17/11/2026",
                "14:00", "Contrôle", "ANNULE"
        });

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        // Boutons Modifier et Supprimer
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");

        btnModifier.setBackground(new Color(245, 158, 11));
        btnModifier.setForeground(Color.WHITE);
        btnModifier.setFocusPainted(false);

        btnSupprimer.setBackground(new Color(229, 57, 53));
        btnSupprimer.setForeground(Color.WHITE);
        btnSupprimer.setFocusPainted(false);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setOpaque(false);
        actions.add(btnModifier);
        actions.add(btnSupprimer);

        add(haut, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        // Recherche
        rechercheField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    private void filtrer() {
                        String texte = rechercheField.getText().trim();

                        if (texte.isEmpty()) {
                            sorter.setRowFilter(null);
                        } else {
                            sorter.setRowFilter(
                                    RowFilter.regexFilter(
                                            "(?i)" + java.util.regex.Pattern.quote(texte)
                                    )
                            );
                        }
                    }

                    @Override
                    public void insertUpdate(javax.swing.event.DocumentEvent e) {
                        filtrer();
                    }

                    @Override
                    public void removeUpdate(javax.swing.event.DocumentEvent e) {
                        filtrer();
                    }

                    @Override
                    public void changedUpdate(javax.swing.event.DocumentEvent e) {
                        filtrer();
                    }
                }
        );

        btnAjouter.addActionListener(e -> ajouterRendezVous());
        btnModifier.addActionListener(e -> modifierRendezVous());
        btnSupprimer.addActionListener(e -> supprimerRendezVous());
    }

    // Ajouter un rendez-vous
    private void ajouterRendezVous() {

        JTextField patient = new JTextField();
        JTextField date = new JTextField();
        JTextField heure = new JTextField();

        JTextField motif = new JTextField();

        String[] statuts = {
                "CONFIRME", "EN ATTENTE", "ANNULE"
        };

        JComboBox<String> statut = new JComboBox<>(statuts);

        JPanel formulaire = new JPanel(new GridLayout(0, 1, 5, 5));

        formulaire.add(new JLabel("Nom du patient :"));
        formulaire.add(patient);

        formulaire.add(new JLabel("Date (JJ/MM/AAAA) :"));
        formulaire.add(date);

        formulaire.add(new JLabel("Heure (HH:MM) :"));
        formulaire.add(heure);

        formulaire.add(new JLabel("Motif :"));
        formulaire.add(motif);

        formulaire.add(new JLabel("Statut :"));
        formulaire.add(statut);

        int resultat = JOptionPane.showConfirmDialog(
                this, formulaire, "Ajouter un rendez-vous",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultat == JOptionPane.OK_OPTION) {

            if (patient.getText().trim().isEmpty()
                    || date.getText().trim().isEmpty()
                    || heure.getText().trim().isEmpty()
                    || motif.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this, "Veuillez remplir tous les champs."
                );
                return;
            }

            long nouvelId = 1;

            for (int i = 0; i < model.getRowCount(); i++) {
                long id = ((Number) model.getValueAt(i, 0)).longValue();
                nouvelId = Math.max(nouvelId, id + 1);
            }

            model.addRow(new Object[]{
                    nouvelId,
                    patient.getText().trim(),
                    date.getText().trim(),
                    heure.getText().trim(),
                    motif.getText().trim(),
                    statut.getSelectedItem()
            });

            JOptionPane.showMessageDialog(
                    this, "Rendez-vous ajouté avec succès !"
            );
        }
    }

    // Modifier un rendez-vous
    private void modifierRendezVous() {

        int ligneVue = table.getSelectedRow();

        if (ligneVue == -1) {
            JOptionPane.showMessageDialog(
                    this, "Veuillez sélectionner un rendez-vous."
            );
            return;
        }

        int ligne = table.convertRowIndexToModel(ligneVue);

        JTextField patient = new JTextField(model.getValueAt(ligne, 1).toString());
        JTextField date = new JTextField(model.getValueAt(ligne, 2).toString());
        JTextField heure = new JTextField(model.getValueAt(ligne, 3).toString());
        JTextField motif = new JTextField(model.getValueAt(ligne, 4).toString());

        String[] statuts = {
                "CONFIRME", "EN ATTENTE", "ANNULE"
        };

        JComboBox<String> statut = new JComboBox<>(statuts);
        statut.setSelectedItem(model.getValueAt(ligne, 5).toString());

        JPanel formulaire = new JPanel(new GridLayout(0, 1, 5, 5));

        formulaire.add(new JLabel("Nom du patient :"));
        formulaire.add(patient);
        formulaire.add(new JLabel("Date (JJ/MM/AAAA) :"));
        formulaire.add(date);
        formulaire.add(new JLabel("Heure (HH:MM) :"));
        formulaire.add(heure);
        formulaire.add(new JLabel("Motif :"));
        formulaire.add(motif);
        formulaire.add(new JLabel("Statut :"));
        formulaire.add(statut);

        int resultat = JOptionPane.showConfirmDialog(
                this, formulaire, "Modifier un rendez-vous",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultat == JOptionPane.OK_OPTION) {

            if (patient.getText().trim().isEmpty()
                    || date.getText().trim().isEmpty()
                    || heure.getText().trim().isEmpty()
                    || motif.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this, "Veuillez remplir tous les champs."
                );
                return;
            }

            model.setValueAt(patient.getText().trim(), ligne, 1);
            model.setValueAt(date.getText().trim(), ligne, 2);
            model.setValueAt(heure.getText().trim(), ligne, 3);
            model.setValueAt(motif.getText().trim(), ligne, 4);
            model.setValueAt(statut.getSelectedItem(), ligne, 5);

            JOptionPane.showMessageDialog(
                    this, "Rendez-vous modifié avec succès !"
            );
        }
    }

    // Supprimer un rendez-vous
    private void supprimerRendezVous() {

        int ligneVue = table.getSelectedRow();

        if (ligneVue == -1) {
            JOptionPane.showMessageDialog(
                    this, "Veuillez sélectionner un rendez-vous."
            );
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment supprimer ce rendez-vous ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {

            int ligne = table.convertRowIndexToModel(ligneVue);
            model.removeRow(ligne);

            JOptionPane.showMessageDialog(
                    this, "Rendez-vous supprimé avec succès !"
            );
        }
    }
}
