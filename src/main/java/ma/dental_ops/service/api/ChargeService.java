package ma.dental_ops.service.api;

import ma.dental_ops.domain.Charge;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.dto.PageResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ChargeService {

    List<Charge> getAllCharges();

    Charge getChargeById(Long idCharge);

    Charge ajouterCharge(Charge charge, Utilisateur auteur);

    PageResult<Charge> getChargesParPeriode(LocalDate dateDebut, LocalDate dateFin, int page, int pageSize);

    List<Charge> getChargesParPeriode(
            LocalDate dateDebut,
            LocalDate dateFin
    );

    BigDecimal calculerSolde(LocalDate dateDebut, LocalDate dateFin);

    BigDecimal calculerTotalCharges(
            LocalDate dateDebut,
            LocalDate dateFin
    );
}
