package ma.dental_ops.ui.controller;

import ma.dental_ops.dto.PageResult;
import ma.dental_ops.dto.PatientSecretariatDTO;
import ma.dental_ops.service.api.PatientService;

import java.util.Objects;

public class PatientController {
    private final PatientService service;
    public PatientController(PatientService service) { this.service = Objects.requireNonNull(service); }
    public PageResult<PatientSecretariatDTO> rechercher(String terme, int page, int pageSize) {
        return service.rechercherPatients(terme, page, pageSize);
    }
}
