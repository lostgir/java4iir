package ma.dental_ops.ui.view;

import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.ui.controller.UtilisateurController;

import javax.swing.*;
import java.awt.*;

/** Profile editor for the authenticated user. Login and role are intentionally read-only. */
public class MonProfilView extends JDialog {
    private final UtilisateurController controller;
    private Utilisateur utilisateur;
    private final JTextField nom = new JTextField(22);
    private final JTextField prenom = new JTextField(22);
    private final JTextField telephone = new JTextField(22);
    private final JTextField email = new JTextField(22);
    private final JTextField role = new JTextField(22);
    private final JButton modifier = new JButton("Modifier");
    private final JButton enregistrer = new JButton("Enregistrer");
    private final JButton annuler = new JButton("Annuler");

    public MonProfilView(Window owner, Utilisateur utilisateur, UtilisateurController controller) {
        super(owner, "Mon profil", ModalityType.APPLICATION_MODAL);
        this.utilisateur = utilisateur;
        this.controller = controller;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(440, 330);
        setLocationRelativeTo(owner);
        construireFormulaire();
        afficher(utilisateur);
    }

    private void construireFormulaire() {
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 24, 12, 24));
        form.add(new JLabel("Nom")); form.add(nom);
        form.add(new JLabel("Prénom")); form.add(prenom);
        form.add(new JLabel("Téléphone")); form.add(telephone);
        form.add(new JLabel("Identifiant (email)")); form.add(email);
        form.add(new JLabel("Rôle")); form.add(role);
        email.setEditable(false);
        role.setEditable(false);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(modifier); actions.add(enregistrer); actions.add(annuler);
        modifier.addActionListener(e -> modeEdition(true));
        annuler.addActionListener(e -> { afficher(utilisateur); modeEdition(false); });
        enregistrer.addActionListener(e -> sauvegarder());
        enregistrer.setEnabled(false); annuler.setEnabled(false);
        add(form, BorderLayout.CENTER); add(actions, BorderLayout.SOUTH);
    }

    private void afficher(Utilisateur u) {
        nom.setText(u.getNomUtilisateur()); prenom.setText(u.getPrenomUtilisateur());
        telephone.setText(u.getTelephone() == null ? "" : u.getTelephone());
        email.setText(u.getEmail());
        role.setText(u.getStatutUtilisateur() == null ? "" : u.getStatutUtilisateur().name());
    }

    private void modeEdition(boolean edition) {
        nom.setEditable(edition); prenom.setEditable(edition); telephone.setEditable(edition);
        modifier.setEnabled(!edition); enregistrer.setEnabled(edition); annuler.setEnabled(edition);
    }

    private void sauvegarder() {
        try {
            utilisateur = controller.enregistrerProfil(utilisateur.getIdUtilisateur(),
                    nom.getText(), prenom.getText(), telephone.getText());
            afficher(utilisateur); modeEdition(false);
            JOptionPane.showMessageDialog(this, "Profil enregistré.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
