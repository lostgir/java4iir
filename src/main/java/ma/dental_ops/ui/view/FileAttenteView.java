package ma.dental_ops.ui.view;

import ma.dental_ops.dto.PatientEnAttente;
import ma.dental_ops.dto.RendezVousDuJour;
import ma.dental_ops.service.api.FileAttenteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Reception desk view for recording arrivals and managing today's queue. */
public class FileAttenteView extends JFrame {
    private final FileAttenteService service;
    private final DefaultTableModel rdvModel = model("ID RDV", "Patient", "Heure", "Statut");
    private final DefaultTableModel queueModel = model("ID RDV", "Patient", "Heure d'arrivée");
    private final JTable rdvTable = new JTable(rdvModel);
    private final JTable queueTable = new JTable(queueModel);

    public FileAttenteView(FileAttenteService service) {
        this.service = service;
        setTitle("DentalSoft - Arrivées et file d'attente");
        setSize(800, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        construire();
        actualiser();
    }

    private static DefaultTableModel model(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
    }

    private void construire() {
        JButton arrive = new JButton("Enregistrer l'arrivée du rendez-vous sélectionné");
        JButton enCours = new JButton("Passer le patient sélectionné à En cours");
        JButton refresh = new JButton("Actualiser");
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(arrive); controls.add(enCours); controls.add(refresh);
        arrive.addActionListener(e -> enregistrerArrivee());
        enCours.addActionListener(e -> passerEnCours());
        refresh.addActionListener(e -> actualiser());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Rendez-vous du jour", new JScrollPane(rdvTable));
        tabs.addTab("File d'attente", new JScrollPane(queueTable));
        add(controls, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }

    private void enregistrerArrivee() {
        int row = rdvTable.getSelectedRow();
        if (row < 0) { info("Sélectionnez un rendez-vous du jour."); return; }
        try {
            long id = ((Number) rdvModel.getValueAt(rdvTable.convertRowIndexToModel(row), 0)).longValue();
            service.enregistrerArrivee(id, LocalDate.now());
            actualiser();
        } catch (RuntimeException ex) { erreur(ex); }
    }

    private void passerEnCours() {
        int row = queueTable.getSelectedRow();
        if (row < 0) { info("Sélectionnez un patient dans la file d'attente."); return; }
        try {
            long id = ((Number) queueModel.getValueAt(queueTable.convertRowIndexToModel(row), 0)).longValue();
            service.passerEnCours(id, LocalDate.now());
            actualiser();
        } catch (RuntimeException ex) { erreur(ex); }
    }

    private void actualiser() {
        try {
            rdvModel.setRowCount(0);
            for (RendezVousDuJour r : service.getRendezVousDuJour(LocalDate.now())) {
                rdvModel.addRow(new Object[]{r.rendezVousId(), r.prenom() + " " + r.nom(), r.heure(), r.statut()});
            }
            queueModel.setRowCount(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            for (PatientEnAttente p : service.getFileAttente(LocalDate.now())) {
                queueModel.addRow(new Object[]{p.rendezVousId(), p.prenom() + " " + p.nom(),
                        p.heureArrivee() == null ? "—" : p.heureArrivee().format(formatter)});
            }
        } catch (RuntimeException ex) { erreur(ex); }
    }

    private void info(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void erreur(RuntimeException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
