
package ma.dental_ops.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UtilisateurPanel extends JPanel {

    private final DefaultTableModel model;
    private final JTable table;

    public UtilisateurPanel() {

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 250, 251));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titre = new JLabel("Gestion des utilisateurs");
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(new Color(11, 60, 73));

        // Bouton Ajouter
        JButton btnAjouter = new JButton("+ Ajouter utilisateur");
        btnAjouter.setBackground(new Color(14, 140, 151));
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.setFocusPainted(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(titre, BorderLayout.WEST);
        header.add(btnAjouter, BorderLayout.EAST);

        // Tableau
        String[] colonnes = {"ID", "Nom", "Prénom", "Rôle"};

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
        model.addRow(new Object[]{1, "Alami", "Sara", "ADMINISTRATEUR"});
        model.addRow(new Object[]{2, "Benali", "Youssef", "REGULATEUR"});
        model.addRow(new Object[]{3, "Amrani", "Salma", "REGULATEUR"});

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
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        // Événements
        btnAjouter.addActionListener(e -> ajouterUtilisateur());
        btnModifier.addActionListener(e -> modifierUtilisateur());
        btnSupprimer.addActionListener(e -> supprimerUtilisateur());
    }

    // Ajouter un utilisateur
    private void ajouterUtilisateur() {

        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();

        String[] roles = {"ADMINISTRATEUR", "REGULATEUR"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        JPanel formulaire = new JPanel(new GridLayout(0, 1, 5, 5));

        formulaire.add(new JLabel("Nom :"));
        formulaire.add(nomField);
        formulaire.add(new JLabel("Prénom :"));
        formulaire.add(prenomField);
        formulaire.add(new JLabel("Rôle :"));
        formulaire.add(roleBox);

        int resultat = JOptionPane.showConfirmDialog(
                this, formulaire, "Ajouter un utilisateur",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultat == JOptionPane.OK_OPTION) {

            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();

            if (nom.isEmpty() || prenom.isEmpty()) {
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
                    nouvelId, nom, prenom, roleBox.getSelectedItem()
            });

            JOptionPane.showMessageDialog(
                    this, "Utilisateur ajouté avec succès !"
            );
        }
    }

    // Modifier un utilisateur
    private void modifierUtilisateur() {

        int ligne = table.getSelectedRow();

        if (ligne == -1) {
            JOptionPane.showMessageDialog(
                    this, "Veuillez sélectionner un utilisateur."
            );
            return;
        }

        JTextField nomField = new JTextField(
                model.getValueAt(ligne, 1).toString()
        );

        JTextField prenomField = new JTextField(
                model.getValueAt(ligne, 2).toString()
        );

        String[] roles = {"ADMINISTRATEUR", "REGULATEUR"};

        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setSelectedItem(model.getValueAt(ligne, 3).toString());

        JPanel formulaire = new JPanel(new GridLayout(0, 1, 5, 5));

        formulaire.add(new JLabel("Nom :"));
        formulaire.add(nomField);
        formulaire.add(new JLabel("Prénom :"));
        formulaire.add(prenomField);
        formulaire.add(new JLabel("Rôle :"));
        formulaire.add(roleBox);

        int resultat = JOptionPane.showConfirmDialog(
                this, formulaire, "Modifier un utilisateur",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultat == JOptionPane.OK_OPTION) {

            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();

            if (nom.isEmpty() || prenom.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Veuillez remplir tous les champs."
                );
                return;
            }

            model.setValueAt(nom, ligne, 1);
            model.setValueAt(prenom, ligne, 2);
            model.setValueAt(roleBox.getSelectedItem(), ligne, 3);

            JOptionPane.showMessageDialog(
                    this, "Utilisateur modifié avec succès !"
            );
        }
    }

    // Supprimer un utilisateur
    private void supprimerUtilisateur() {

        int ligne = table.getSelectedRow();

        if (ligne == -1) {
            JOptionPane.showMessageDialog(
                    this, "Veuillez sélectionner un utilisateur."
            );
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment supprimer cet utilisateur ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {

            model.removeRow(ligne);

            JOptionPane.showMessageDialog(
                    this, "Utilisateur supprimé avec succès !"
            );
        }
    }
}
