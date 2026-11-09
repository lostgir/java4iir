package ma.dental_ops.domain;

import java.time.LocalDate;

public class Patient {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
    private String cin;
    private String passeport;
    private String email;
    private String telephone;
    private String adresse;
    private String assurance;
    private boolean archive;

    // Constructeur vide
    public Patient() {}

    // --- GETTERS ET SETTERS MANUELS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getPasseport() { return passeport; }
    public void setPasseport(String passeport) { this.passeport = passeport; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getAssurance() { return assurance; }
    public void setAssurance(String assurance) { this.assurance = assurance; }

    public boolean isArchive() { return archive; }
    public void setArchive(boolean archive) { this.archive = archive; }

    // --- BUILDER MANUEL (pour que Patient.builder() fonctionne) ---
    public static PatientBuilder builder() {
        return new PatientBuilder();
    }

    public static class PatientBuilder {
        private Patient patient = new Patient();

        public PatientBuilder id(Long id) { patient.setId(id); return this; }
        public PatientBuilder nom(String nom) { patient.setNom(nom); return this; }
        public PatientBuilder prenom(String prenom) { patient.setPrenom(prenom); return this; }
        public PatientBuilder dateNaissance(LocalDate dateNaissance) { patient.setDateNaissance(dateNaissance); return this; }
        public PatientBuilder sexe(String sexe) { patient.setSexe(sexe); return this; }
        public PatientBuilder cin(String cin) { patient.setCin(cin); return this; }
        public PatientBuilder passeport(String passeport) { patient.setPasseport(passeport); return this; }
        public PatientBuilder email(String email) { patient.setEmail(email); return this; }
        public PatientBuilder telephone(String telephone) { patient.setTelephone(telephone); return this; }
        public PatientBuilder adresse(String adresse) { patient.setAdresse(adresse); return this; }
        public PatientBuilder assurance(String assurance) { patient.setAssurance(assurance); return this; }
        public PatientBuilder archive(boolean archive) { patient.setArchive(archive); return this; }
        public Patient build() { return patient; }
    }
}