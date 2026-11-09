package ma.dental_ops.domain;

import java.time.LocalDateTime;

public class DossierMedical {
    private Long id;
    private Long patientId;
    private LocalDateTime dateCreation;
    private Long medecinId;

    public DossierMedical() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public Long getMedecinId() { return medecinId; }
    public void setMedecinId(Long medecinId) { this.medecinId = medecinId; }

    public static DossierMedicalBuilder builder() {
        return new DossierMedicalBuilder();
    }

    public static class DossierMedicalBuilder {
        private DossierMedical dossier = new DossierMedical();

        public DossierMedicalBuilder id(Long id) { dossier.setId(id); return this; }
        public DossierMedicalBuilder patientId(Long patientId) { dossier.setPatientId(patientId); return this; }
        public DossierMedicalBuilder dateCreation(LocalDateTime dateCreation) { dossier.setDateCreation(dateCreation); return this; }
        public DossierMedicalBuilder medecinId(Long medecinId) { dossier.setMedecinId(medecinId); return this; }
        public DossierMedical build() { return dossier; }
    }
}