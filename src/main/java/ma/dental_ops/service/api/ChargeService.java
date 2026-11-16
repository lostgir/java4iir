package ma.dental_ops.service.api;

import ma.dental_ops.domain.Charge;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ChargeService {

    List<Charge> getAllCharges();

    Charge getChargeById(Long idCharge);

    Charge ajouterCharge(Charge charge);

    List<Charge> getChargesParPeriode(
            LocalDate dateDebut,
            LocalDate dateFin
    );

    BigDecimal calculerTotalCharges(
            LocalDate dateDebut,
            LocalDate dateFin
    );
}