package ma.dental_ops.ui.controller;

import ma.dental_ops.domain.Charge;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.dto.PageResult;
import ma.dental_ops.service.api.ChargeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ChargeController {

    private final ChargeService service;
    private final Utilisateur utilisateurConnecte;

    public ChargeController(ChargeService service, Utilisateur utilisateurConnecte) {
        this.service = java.util.Objects.requireNonNull(service);
        this.utilisateurConnecte = utilisateurConnecte;
        if (utilisateurConnecte == null || utilisateurConnecte.getStatutUtilisateur() != StatutUtilisateur.MEDECIN) {
            throw new SecurityException("La gestion des charges est réservée au médecin.");
        }
    }

    // Ajouter une charge
    public Charge ajouterCharge(
            String titre,
            BigDecimal montant,
            LocalDate dateCharge,
            String description) {

        Charge charge = Charge.builder()
                .titre(titre)
                .montant(montant)
                .dateCharge(dateCharge)
                .description(description)
                .build();

        return service.ajouterCharge(charge, utilisateurConnecte);
    }

    // Afficher toutes les charges
    public List<Charge> afficherToutesLesCharges() {
        return service.getAllCharges();
    }

    // Afficher les charges d'une période
    public List<Charge> afficherChargesParPeriode(
            LocalDate dateDebut,
            LocalDate dateFin) {

        return service.getChargesParPeriode(dateDebut, dateFin);
    }

    public PageResult<Charge> afficherChargesParPeriode(
            LocalDate dateDebut, LocalDate dateFin, int page, int pageSize) {
        return service.getChargesParPeriode(dateDebut, dateFin, page, pageSize);
    }

    public BigDecimal calculerSolde(LocalDate dateDebut, LocalDate dateFin) {
        return service.calculerSolde(dateDebut, dateFin);
    }

    // Calculer le total des charges
    public BigDecimal calculerTotalCharges(
            LocalDate dateDebut,
            LocalDate dateFin) {

        return service.calculerTotalCharges(dateDebut, dateFin);
    }
}
