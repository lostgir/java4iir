package ma.dental_ops.ui.view;

import ma.dental_ops.dto.PageResult;
import ma.dental_ops.dto.PatientSecretariatDTO;
import ma.dental_ops.ui.controller.PatientController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientListView extends JFrame {
    private static final int PAGE_SIZE = 25;
    private final PatientController controller;
    private final JTextField recherche = new JTextField(24);
    private final JLabel pagination = new JLabel(" ");
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Nom", "Prénom", "Téléphone", "Email", "Archivé"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JButton precedent = new JButton("Précédent");
    private final JButton suivant = new JButton("Suivant");
    private int page;
    private int totalPages;

    public PatientListView(PatientController controller) {
        this.controller = controller;
        setTitle("DentalSoft - Patients");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        construire();
        chargerPage();
    }

    private void construire() {
        JPanel filtres = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton chercher = new JButton("Rechercher");
        filtres.add(new JLabel("Nom, téléphone ou n° dossier :"));
        filtres.add(recherche);
        filtres.add(chercher);
        chercher.addActionListener(e -> { page = 0; chargerPage(); });
        recherche.addActionListener(e -> { page = 0; chargerPage(); });
        add(filtres, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navigation.add(precedent); navigation.add(pagination); navigation.add(suivant);
        precedent.addActionListener(e -> { if (page > 0) { page--; chargerPage(); } });
        suivant.addActionListener(e -> { if (page + 1 < totalPages) { page++; chargerPage(); } });
        add(navigation, BorderLayout.SOUTH);
    }

    private void chargerPage() {
        try {
            PageResult<PatientSecretariatDTO> result = controller.rechercher(recherche.getText(), page, PAGE_SIZE);
            model.setRowCount(0);
            for (PatientSecretariatDTO p : result.items()) {
                model.addRow(new Object[]{p.getId(), p.getNom(), p.getPrenom(), p.getTelephone(), p.getEmail(), p.isArchive() ? "Oui" : "Non"});
            }
            totalPages = result.totalPages();
            pagination.setText(result.totalItems() + " patient(s) · page " + (totalPages == 0 ? 0 : page + 1) + " / " + totalPages);
            precedent.setEnabled(page > 0);
            suivant.setEnabled(page + 1 < totalPages);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

