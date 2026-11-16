package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.dto.PatientEnAttente;
import ma.dental_ops.dto.RendezVousDuJour;
import ma.dental_ops.repository.api.FileAttenteRepository;
import ma.dental_ops.service.implementation.FileAttenteServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileAttenteServiceTest extends TestCase {
    public void testArrivalAndTransitionDelegateForSelectedDate() {
        FakeRepository repository = new FakeRepository();
        FileAttenteServiceImpl service = new FileAttenteServiceImpl(repository);
        LocalDate date = LocalDate.of(2026, 11, 12);

        service.enregistrerArrivee(7, date);
        service.passerEnCours(7, date);

        assertEquals(List.of("arrive:7:2026-11-12", "en-cours:7:2026-11-12"), repository.calls);
    }

    public void testInvalidAppointmentIdIsRejectedBeforeRepositoryCall() {
        FakeRepository repository = new FakeRepository();
        FileAttenteServiceImpl service = new FileAttenteServiceImpl(repository);
        try {
            service.enregistrerArrivee(0, LocalDate.now());
            fail("Expected an invalid id to be rejected");
        } catch (IllegalArgumentException expected) {
            assertTrue(repository.calls.isEmpty());
        }
    }

    private static final class FakeRepository implements FileAttenteRepository {
        private final List<String> calls = new ArrayList<>();
        @Override public void enregistrerArrivee(long id, LocalDate date) {
            calls.add("arrive:" + id + ":" + date);
        }
        @Override public void passerEnCours(long id, LocalDate date) {
            calls.add("en-cours:" + id + ":" + date);
        }
        @Override public List<PatientEnAttente> findArriveesDuJour(LocalDate date) {
            return List.of();
        }
        @Override public List<RendezVousDuJour> findRendezVousDuJour(LocalDate date) { return List.of(); }
    }
}
