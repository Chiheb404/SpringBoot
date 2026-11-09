package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.autolocapi.mapper.EmployeMapper;
import tn.esprit.autoloc.autolocapi.service.IEmployeService;
import tn.esprit.autoloc.autolocapi.web.dto.EmployeDTO;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés")
public class EmployeController {

    private final IEmployeService employeService;
    private final EmployeMapper employeMapper;

    @Operation(summary = "Lister les employés")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    @GetMapping
    public List<EmployeDTO> findAll() {
        return employeMapper.toDtoList(employeService.getAll());
    }

    @Operation(summary = "Consulter un employé")
    @ApiResponse(responseCode = "200", description = "Employé trouvé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @GetMapping("/{id}")
    public EmployeDTO findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return employeMapper.toDto(employeService.getById(id));
    }

    @Operation(summary = "Créer un employé")
    @ApiResponse(responseCode = "201", description = "Employé créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeDTO create(@Valid @RequestBody EmployeDTO dto) {
        return employeMapper.toDto(
                employeService.add(employeMapper.toEntity(dto), dto.getIdAgence()));
    }

    @Operation(summary = "Modifier un employé")
    @ApiResponse(responseCode = "200", description = "Employé modifié")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public EmployeDTO update(@Parameter(description = "Identifiant") @PathVariable Long id,
                             @Valid @RequestBody EmployeDTO dto) {
        return employeMapper.toDto(
                employeService.update(id, employeMapper.toEntity(dto), dto.getIdAgence()));
    }

    @Operation(summary = "Supprimer un employé")
    @ApiResponse(responseCode = "204", description = "Employé supprimé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        employeService.delete(id);
    }
}
