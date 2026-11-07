package ma.dental_ops.repository.api;

import ma.dental_ops.domain.Charge;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ChargeRepository {

    List<Charge> findAllCharges();

    Charge findChargeById(Long idCharge);

    Charge save(Charge charge);

    List<Charge> findChargesParPeriode(
            LocalDate dateDebut,
            LocalDate dateFin
    );

    BigDecimal calculerTotalCharges(
            LocalDate dateDebut,
            LocalDate dateFin
    );
}