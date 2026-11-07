package ma.dental_ops.service.implementation;

import ma.dental_ops.domain.Charge;
import ma.dental_ops.repository.api.ChargeRepository;
import ma.dental_ops.repository.jdbcMySql.ChargeRepositoryImplt;
import ma.dental_ops.service.api.ChargeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ChargeServiceImpl implements ChargeService {

    private final ChargeRepository chargeRepository;

    public ChargeServiceImpl() {
        this.chargeRepository = new ChargeRepositoryImplt();
    }

    @Override
    public List<Charge> getAllCharges() {
        return chargeRepository.findAllCharges();
    }

    @Override
    public Charge getChargeById(Long idCharge) {
        return chargeRepository.findChargeById(idCharge);
    }

    @Override
    public Charge ajouterCharge(Charge charge) {

        if (charge == null) {
            throw new IllegalArgumentException("La charge est obligatoire.");
        }

        if (charge.getTitre() == null || charge.getTitre().trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre est obligatoire.");
        }

        if (charge.getMontant() == null ||
                charge.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif.");
        }

        if (charge.getDateCharge() == null) {
            throw new IllegalArgumentException("La date est obligatoire.");
        }

        if (charge.getAuteurId() == null) {
            throw new IllegalArgumentException("L'auteur est obligatoire.");
        }

        return chargeRepository.save(charge);
    }

    @Override
    public List<Charge> getChargesParPeriode(
            LocalDate dateDebut, LocalDate dateFin) {

        if (dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("Les deux dates sont obligatoires.");
        }

        if (dateDebut.isAfter(dateFin)) {
            throw new IllegalArgumentException(
                    "La date de début doit être antérieure à la date de fin."
            );
        }

        return chargeRepository.findChargesParPeriode(dateDebut, dateFin);
    }

    @Override
    public BigDecimal calculerTotalCharges(
            LocalDate dateDebut, LocalDate dateFin) {

        if (dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("Les deux dates sont obligatoires.");
        }

        if (dateDebut.isAfter(dateFin)) {
            throw new IllegalArgumentException(
                    "La date de début doit être antérieure à la date de fin."
            );
        }

        return chargeRepository.calculerTotalCharges(dateDebut, dateFin);
    }
}