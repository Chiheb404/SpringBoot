package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.mapper.VehiculeMapper;
import tn.esprit.autoloc.autolocapi.service.IVehiculeService;
import tn.esprit.autoloc.autolocapi.web.dto.VehiculeDTO;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;
    private final VehiculeMapper vehiculeMapper;

    @Operation(summary = "Lister les véhicules")
    @GetMapping
    public List<VehiculeDTO> findAll() {
        return vehiculeMapper.toDtoList(vehiculeService.getAll());
    }

    @Operation(summary = "Consulter un véhicule")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @GetMapping("/{id}")
    public VehiculeDTO findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return vehiculeMapper.toDto(vehiculeService.getById(id));
    }

    @Operation(summary = "Créer un véhicule")
    @ApiResponse(responseCode = "201", description = "Véhicule créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDTO create(@Valid @RequestBody VehiculeDTO dto) {
        return vehiculeMapper.toDto(
                vehiculeService.add(vehiculeMapper.toEntity(dto), dto.getIdAgence()));
    }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public VehiculeDTO update(@Parameter(description = "Identifiant") @PathVariable Long id,
                              @Valid @RequestBody VehiculeDTO dto) {
        return vehiculeMapper.toDto(
                vehiculeService.update(id, vehiculeMapper.toEntity(dto), dto.getIdAgence()));
    }

    @Operation(summary = "Supprimer un véhicule")
    @ApiResponse(responseCode = "204", description = "Véhicule supprimé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        vehiculeService.delete(id);
    }

    @Operation(summary = "Affecter un véhicule à une agence")
    @ApiResponse(responseCode = "200", description = "Véhicule affecté")
    @ApiResponse(responseCode = "400", description = "Identifiant invalide")
    @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")
    @PutMapping("/{idVehicule}/agence/{idAgence}")
    public VehiculeDTO affecterAAgence(
            @Parameter(description = "Identifiant du véhicule") @PathVariable @Positive Long idVehicule,
            @Parameter(description = "Identifiant de l'agence") @PathVariable @Positive Long idAgence) {
        return vehiculeMapper.toDto(vehiculeService.affecterAAgence(idVehicule, idAgence));
    }

    @Operation(summary = "Associer un équipement à un véhicule")
    @ApiResponse(responseCode = "200", description = "Équipement associé")
    @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")
    @PutMapping("/{idVehicule}/equipements/{idEquipement}")
    public VehiculeDTO affecterEquipement(
            @Parameter(description = "Identifiant du véhicule") @PathVariable @Positive Long idVehicule,
            @Parameter(description = "Identifiant de l'équipement") @PathVariable @Positive Long idEquipement) {
        return vehiculeMapper.toDto(vehiculeService.affecterEquipement(idVehicule, idEquipement));
    }

    @Operation(summary = "Dissocier un équipement d'un véhicule")
    @ApiResponse(responseCode = "200", description = "Équipement dissocié")
    @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")
    @DeleteMapping("/{idVehicule}/equipements/{idEquipement}")
    public VehiculeDTO desaffecterEquipement(
            @Parameter(description = "Identifiant du véhicule") @PathVariable @Positive Long idVehicule,
            @Parameter(description = "Identifiant de l'équipement") @PathVariable @Positive Long idEquipement) {
        return vehiculeMapper.toDto(vehiculeService.desaffecterEquipement(idVehicule, idEquipement));
    }
}
