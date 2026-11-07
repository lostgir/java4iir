package ma.dental_ops.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Charge {

    private Long idCharge;
    private String titre;
    private BigDecimal montant;
    private LocalDate dateCharge;
    private String description;
    private Long auteurId;
}