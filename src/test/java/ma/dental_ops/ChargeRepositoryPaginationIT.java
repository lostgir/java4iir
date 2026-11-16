package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.domain.Charge;
import ma.dental_ops.dto.PageResult;
import ma.dental_ops.repository.jdbcMySql.ChargeRepositoryImplt;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;

public class ChargeRepositoryPaginationIT extends TestCase {
    private final ChargeRepositoryImplt repository = new ChargeRepositoryImplt();

    @Override
    protected void setUp() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:h2:mem:dental_profile_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE", "sa", "");
             Statement sql = connection.createStatement()) {
            sql.execute("CREATE TABLE IF NOT EXISTS charge (id_charge BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "titre VARCHAR(150) NOT NULL, montant DECIMAL(10,2) NOT NULL, "
                    + "date_charge DATE NOT NULL, description VARCHAR(500), auteur_id BIGINT NOT NULL)");
            sql.execute("CREATE TABLE IF NOT EXISTS paiement (montant DECIMAL(10,2), date_paiement DATE, statut VARCHAR(10))");
            sql.execute("DELETE FROM paiement");
            sql.execute("INSERT INTO paiement VALUES (500, DATE '2026-11-12', 'PAYE'), (100, DATE '2026-11-12', 'ANNULE')");
            sql.execute("DELETE FROM charge");
            for (int i = 1; i <= 21; i++) {
                sql.execute("INSERT INTO charge (titre, montant, date_charge, description, auteur_id) "
                        + "VALUES ('Charge " + i + "', 10.00, DATE '2026-11-12', '', 1)");
            }
        }
    }

    public void testPaginationAndTotalAreConsistent() {
        LocalDate date = LocalDate.of(2026, 11, 12);
        PageResult<Charge> first = repository.findChargesParPeriode(date, date, 0, 20);
        PageResult<Charge> second = repository.findChargesParPeriode(date, date, 1, 20);

        assertEquals(21L, first.totalItems());
        assertEquals(2, first.totalPages());
        assertEquals(20, first.items().size());
        assertEquals(1, second.items().size());
        assertTrue(first.items().get(0).getIdCharge() > first.items().get(19).getIdCharge());
        assertEquals(new BigDecimal("210.00"), repository.calculerTotalCharges(date, date));
        assertEquals(new BigDecimal("290.00"), repository.calculerSolde(date, date));
        repository.save(Charge.builder().titre("Charge test").montant(new BigDecimal("200.00"))
                .dateCharge(date).auteurId(1L).build());
        assertEquals(new BigDecimal("90.00"), repository.calculerSolde(date, date));
    }
}
