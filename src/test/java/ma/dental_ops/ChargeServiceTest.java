package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.domain.Charge;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.dto.PageResult;
import ma.dental_ops.repository.api.ChargeRepository;
import ma.dental_ops.service.implementation.ChargeServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ChargeServiceTest extends TestCase {
    private FakeChargeRepository repository;
    private ChargeServiceImpl service;

    @Override
    protected void setUp() {
        repository = new FakeChargeRepository();
        service = new ChargeServiceImpl(repository);
    }

    public void testAjoutChargeAttribueAuteurConnecte() {
        Charge charge = Charge.builder().titre("  Loyer  ").montant(new BigDecimal("200.00"))
                .dateCharge(LocalDate.of(2026, 11, 12)).description("  Cabinet  ").auteurId(999L).build();

        Charge saved = service.ajouterCharge(charge, medecin(7L));

        assertSame(charge, saved);
        assertEquals("Loyer", saved.getTitre());
        assertEquals("Cabinet", saved.getDescription());
        assertEquals(Long.valueOf(7L), saved.getAuteurId());
        assertEquals(new BigDecimal("200.00"), repository.saved.getMontant());
    }

    public void testRefuseChargePourSecretaire() {
        try {
            service.ajouterCharge(charge("200"), Utilisateur.builder()
                    .idUtilisateur(8L).statutUtilisateur(StatutUtilisateur.SECRETAIRE).build());
            fail("Un non-médecin ne doit pas pouvoir enregistrer une charge.");
        } catch (SecurityException expected) {
            assertEquals("Seul un médecin peut enregistrer une charge.", expected.getMessage());
            assertNull(repository.saved);
        }
    }

    public void testRefuseMontantNulOuNegatif() {
        for (String value : new String[]{"0", "-0.01"}) {
            try {
                service.ajouterCharge(charge(value), medecin(7L));
                fail("Le montant doit être strictement positif.");
            } catch (IllegalArgumentException expected) {
                assertNull(repository.saved);
            }
        }
    }

    public void testRecherchePagineeEtTotalDePeriode() {
        LocalDate debut = LocalDate.of(2026, 11, 1);
        LocalDate fin = LocalDate.of(2026, 11, 30);
        PageResult<Charge> page = service.getChargesParPeriode(debut, fin, 1, 20);

        assertEquals(1, page.page());
        assertEquals(20, page.pageSize());
        assertEquals(21L, page.totalItems());
        assertEquals(2, page.totalPages());
        assertEquals(new BigDecimal("200.00"), service.calculerTotalCharges(debut, fin));
        assertEquals(1, repository.requestedPage);
    }

    public void testRefusePeriodeInversee() {
        try {
            service.getChargesParPeriode(LocalDate.of(2026, 11, 30), LocalDate.of(2026, 11, 1), 0, 20);
            fail("Une période inversée doit être refusée.");
        } catch (IllegalArgumentException expected) {
            assertNull(repository.pageResult);
        }
    }

    private Utilisateur medecin(Long id) {
        return Utilisateur.builder().idUtilisateur(id).statutUtilisateur(StatutUtilisateur.MEDECIN).build();
    }

    private Charge charge(String amount) {
        return Charge.builder().titre("Frais").montant(new BigDecimal(amount))
                .dateCharge(LocalDate.of(2026, 11, 12)).build();
    }

    private static final class FakeChargeRepository implements ChargeRepository {
        private Charge saved;
        private int requestedPage = -1;
        private PageResult<Charge> pageResult;

        @Override public List<Charge> findAllCharges() { return List.of(); }
        @Override public Charge findChargeById(Long idCharge) { return null; }
        @Override public Charge save(Charge charge) { saved = charge; return charge; }
        @Override public List<Charge> findChargesParPeriode(LocalDate start, LocalDate end) { return List.of(); }
        @Override public PageResult<Charge> findChargesParPeriode(LocalDate start, LocalDate end, int page, int pageSize) {
            requestedPage = page;
            pageResult = new PageResult<>(List.of(), page, pageSize, 21);
            return pageResult;
        }
        @Override public BigDecimal calculerSolde(LocalDate debut, LocalDate fin) { return BigDecimal.ZERO; }
        @Override public BigDecimal calculerTotalCharges(LocalDate start, LocalDate end) {
            return new BigDecimal("200.00");
        }
    }
}
