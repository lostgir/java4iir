package ma.dental_ops.service.implementation;

import ma.dental_ops.domain.Charge;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.dto.PageResult;
import ma.dental_ops.repository.api.ChargeRepository;
import ma.dental_ops.repository.jdbcMySql.ChargeRepositoryImplt;
import ma.dental_ops.service.api.ChargeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ChargeServiceImpl implements ChargeService {

    private final ChargeRepository chargeRepository;

    public ChargeServiceImpl() {
        this(new ChargeRepositoryImplt());
    }

    public ChargeServiceImpl(ChargeRepository chargeRepository) {
        this.chargeRepository = java.util.Objects.requireNonNull(chargeRepository);
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
    public Charge ajouterCharge(Charge charge, Utilisateur auteur) {

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

        if (auteur == null || auteur.getIdUtilisateur() == null
                || auteur.getStatutUtilisateur() != StatutUtilisateur.MEDECIN) {
            throw new SecurityException("Seul un médecin peut enregistrer une charge.");
        }

        charge.setTitre(charge.getTitre().trim());
        charge.setDescription(charge.getDescription() == null || charge.getDescription().isBlank()
                ? null : charge.getDescription().trim());
        charge.setAuteurId(auteur.getIdUtilisateur());

        return chargeRepository.save(charge);
    }

    @Override
    public PageResult<Charge> getChargesParPeriode(
            LocalDate dateDebut, LocalDate dateFin, int page, int pageSize) {
        validerPeriode(dateDebut, dateFin);
        if (page < 0 || pageSize < 1 || pageSize > 500) {
            throw new IllegalArgumentException("La page doit être positive et la taille comprise entre 1 et 500.");
        }
        return chargeRepository.findChargesParPeriode(dateDebut, dateFin, page, pageSize);
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

        validerPeriode(dateDebut, dateFin);

        return chargeRepository.calculerTotalCharges(dateDebut, dateFin);
    }

    @Override
    public BigDecimal calculerSolde(LocalDate dateDebut, LocalDate dateFin) {
        validerPeriode(dateDebut, dateFin);
        return chargeRepository.calculerSolde(dateDebut, dateFin);
    }

    private void validerPeriode(LocalDate dateDebut, LocalDate dateFin) {
        if (dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("Les deux dates sont obligatoires.");
        }
        if (dateDebut.isAfter(dateFin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }
    }
}
