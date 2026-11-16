package ma.dental_ops.ui.view;

import ma.dental_ops.dto.PatientEnAttente;
import ma.dental_ops.dto.RendezVousDuJour;
import ma.dental_ops.service.api.FileAttenteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

public class FileAttenteView extends JPanel {
    private static final DateTimeFormatter HEURE = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final FileAttenteService service;
    private final DefaultTableModel rdvModel = model("ID RDV", "Patient", "Heure", "Statut");
    private final DefaultTableModel queueModel = model("Position", "ID RDV", "Patient", "Heure d'arrivée");
    private final JTable rdvTable = new JTable(rdvModel);
    private final JTable queueTable = new JTable(queueModel);
    private final JButton arrive = new JButton("Enregistrer l'arrivée");
    private final JButton absent = new JButton("Absent");
    private final JButton enCours = new JButton("Passer en cours");
    private final JButton refresh = new JButton("Actualiser");

    public FileAttenteView(FileAttenteService service) {
        super(new BorderLayout());
        this.service = service;
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(arrive);
        controls.add(absent);
        controls.add(enCours);
        controls.add(refresh);
        arrive.addActionListener(e -> agir(rdvTable, 0, service::enregistrerArrivee));
        absent.addActionListener(e -> agir(rdvTable, 0, service::marquerAbsent));
        enCours.addActionListener(e -> agir(queueTable, 1, service::passerEnCours));
        refresh.addActionListener(e -> actualiser(() -> {}));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Rendez-vous du jour", new JScrollPane(rdvTable));
        tabs.addTab("File d'attente", new JScrollPane(queueTable));
        add(controls, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
        actualiser(() -> {});
    }

    private static DefaultTableModel model(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
    }

    private void agir(JTable table, int colonneId, BiConsumer<Long, LocalDate> operation) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un rendez-vous dans la liste correspondante.");
            return;
        }
        long id = ((Number) table.getModel().getValueAt(table.convertRowIndexToModel(row), colonneId)).longValue();
        LocalDate jour = LocalDate.now();
        actualiser(() -> operation.accept(id, jour));
    }

    private void actualiser(Runnable action) {
        LocalDate jour = LocalDate.now();
        activerCommandes(false);
        new SwingWorker<ListesDuJour, Void>() {
            @Override protected ListesDuJour doInBackground() {
                action.run();
                return new ListesDuJour(service.getRendezVousDuJour(jour), service.getFileAttente(jour));
            }

            @Override protected void done() {
                try {
                    afficher(get());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException ex) {
                    JOptionPane.showMessageDialog(FileAttenteView.this, ex.getCause().getMessage(),
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                } finally {
                    activerCommandes(true);
                }
            }
        }.execute();
    }

    private void afficher(ListesDuJour listes) {
        rdvModel.setRowCount(0);
        for (RendezVousDuJour rdv : listes.rendezVous()) {
            rdvModel.addRow(new Object[]{rdv.rendezVousId(), rdv.prenom() + " " + rdv.nom(), rdv.heure(), rdv.statut()});
        }
        queueModel.setRowCount(0);
        int position = 1;
        for (PatientEnAttente patient : listes.file()) {
            queueModel.addRow(new Object[]{position++, patient.rendezVousId(), patient.prenom() + " " + patient.nom(),
                    patient.heureArrivee() == null ? "-" : patient.heureArrivee().format(HEURE)});
        }
    }

    private void activerCommandes(boolean active) {
        arrive.setEnabled(active);
        absent.setEnabled(active);
        enCours.setEnabled(active);
        refresh.setEnabled(active);
    }

    private record ListesDuJour(List<RendezVousDuJour> rendezVous, List<PatientEnAttente> file) {}
}
