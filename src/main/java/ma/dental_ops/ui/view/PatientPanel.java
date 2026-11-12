
package ma.dental_ops.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class PatientPanel extends JPanel {

    private final DefaultTableModel model;
    private final JTable table;
    private final TableRowSorter<DefaultTableModel> sorter;

    public PatientPanel() {

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 250, 251));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titre = new JLabel("Gestion des patients");
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(new Color(11, 60, 73));

        // Barre de recherche
        JTextField rechercheField = new JTextField(18);
        rechercheField.setPreferredSize(new Dimension(180, 35));

        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        recherchePanel.setOpaque(false);
        recherchePanel.add(new JLabel("Rechercher :"));
        recherchePanel.add(rechercheField);

        // Bouton Ajouter
        JButton btnAjouter = new JButton("+ Ajouter patient");
        btnAjouter.setBackground(new Color(14, 140, 151));
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.setFocusPainted(false);

        JPanel header = new JPanel(new BorderLayout(10, 10));
        header.setOpaque(false);
        header.add(titre, BorderLayout.WEST);
        header.add(btnAjouter, BorderLayout.EAST);

        JPanel haut = new JPanel(new BorderLayout(10, 10));
        haut.setOpaque(false);
        haut.add(header, BorderLayout.NORTH);
        haut.add(recherchePanel, BorderLayout.SOUTH);

        // Tableau
        String[] colonnes = {
                "ID", "Nom", "Prénom", "Téléphone", "Email", "Adresse"
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
                1, "Alami", "Sara", "0612345678",
                "sara@gmail.com", "Rabat"
        });

        model.addRow(new Object[]{
                2, "Benali", "Youssef", "0623456789",
                "youssef@gmail.com", "Salé"
        });

        model.addRow(new Object[]{
                3, "Amrani", "Salma", "0634567890",
                "salma@gmail.com", "Témara"
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

        // Organisation
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

        // Actions
        btnAjouter.addActionListener(e -> ajouterPatient());
        btnModifier.addActionListener(e -> modifierPatient());
        btnSupprimer.addActionListener(e -> supprimerPatient());
    }

    private void ajouterPatient() {

        JTextField nom = new JTextField();
        JTextField prenom = new JTextField();
        JTextField telephone = new JTextField();
        JTextField email = new JTextField();
        JTextField adresse = new JTextField();

        JPanel formulaire = new JPanel(new GridLayout(0, 1, 5, 5));

        formulaire.add(new JLabel("Nom :"));
        formulaire.add(nom);
        formulaire.add(new JLabel("Prénom :"));
        formulaire.add(prenom);
        formulaire.add(new JLabel("Téléphone :"));
        formulaire.add(telephone);
        formulaire.add(new JLabel("Email :"));
        formulaire.add(email);
        formulaire.add(new JLabel("Adresse :"));
        formulaire.add(adresse);

        int resultat = JOptionPane.showConfirmDialog(
                this, formulaire, "Ajouter un patient",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultat == JOptionPane.OK_OPTION) {

            if (nom.getText().trim().isEmpty()
                    || prenom.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this, "Le nom et le prénom sont obligatoires."
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
                    nom.getText().trim(),
                    prenom.getText().trim(),
                    telephone.getText().trim(),
                    email.getText().trim(),
                    adresse.getText().trim()
            });

            JOptionPane.showMessageDialog(
                    this, "Patient ajouté avec succès !"
            );
        }
    }

    private void modifierPatient() {

        int ligneVue = table.getSelectedRow();

        if (ligneVue == -1) {
            JOptionPane.showMessageDialog(
                    this, "Veuillez sélectionner un patient."
            );
            return;
        }

        int ligne = table.convertRowIndexToModel(ligneVue);

        JTextField nom = new JTextField(model.getValueAt(ligne, 1).toString());
        JTextField prenom = new JTextField(model.getValueAt(ligne, 2).toString());
        JTextField telephone = new JTextField(model.getValueAt(ligne, 3).toString());
        JTextField email = new JTextField(model.getValueAt(ligne, 4).toString());
        JTextField adresse = new JTextField(model.getValueAt(ligne, 5).toString());

        JPanel formulaire = new JPanel(new GridLayout(0, 1, 5, 5));

        formulaire.add(new JLabel("Nom :"));
        formulaire.add(nom);
        formulaire.add(new JLabel("Prénom :"));
        formulaire.add(prenom);
        formulaire.add(new JLabel("Téléphone :"));
        formulaire.add(telephone);
        formulaire.add(new JLabel("Email :"));
        formulaire.add(email);
        formulaire.add(new JLabel("Adresse :"));
        formulaire.add(adresse);

        int resultat = JOptionPane.showConfirmDialog(
                this, formulaire, "Modifier un patient",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultat == JOptionPane.OK_OPTION) {

            if (nom.getText().trim().isEmpty()
                    || prenom.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this, "Le nom et le prénom sont obligatoires."
                );
                return;
            }

            model.setValueAt(nom.getText().trim(), ligne, 1);
            model.setValueAt(prenom.getText().trim(), ligne, 2);
            model.setValueAt(telephone.getText().trim(), ligne, 3);
            model.setValueAt(email.getText().trim(), ligne, 4);
            model.setValueAt(adresse.getText().trim(), ligne, 5);

            JOptionPane.showMessageDialog(
                    this, "Patient modifié avec succès !"
            );
        }
    }

    private void supprimerPatient() {

        int ligneVue = table.getSelectedRow();

        if (ligneVue == -1) {
            JOptionPane.showMessageDialog(
                    this, "Veuillez sélectionner un patient."
            );
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment supprimer ce patient ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {

            int ligne = table.convertRowIndexToModel(ligneVue);
            model.removeRow(ligne);

            JOptionPane.showMessageDialog(
                    this, "Patient supprimé avec succès !"
            );
        }
    }
}
