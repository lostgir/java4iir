package ma.dental_ops.ui.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.dental_ops.domain.Charge;
import ma.dental_ops.service.api.ChargeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ChargeController {

    private ChargeService service;

    public ChargeController() {
    }

    // Ajouter une charge
    public Charge ajouterCharge(
            String titre,
            BigDecimal montant,
            LocalDate dateCharge,
            String description,
            Long auteurId) {

        Charge charge = Charge.builder()
                .titre(titre)
                .montant(montant)
                .dateCharge(dateCharge)
                .description(description)
                .auteurId(auteurId)
                .build();

        return service.ajouterCharge(charge);
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

    // Calculer le total des charges
    public BigDecimal calculerTotalCharges(
            LocalDate dateDebut,
            LocalDate dateFin) {

        return service.calculerTotalCharges(dateDebut, dateFin);
    }
}