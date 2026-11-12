package ma.dental_ops.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RendezVous {

    private Long idRendezVous;
    private Long patientId;
    private LocalDate dateRendezVous;
    private LocalTime heureRendezVous;
    private String statut;
}