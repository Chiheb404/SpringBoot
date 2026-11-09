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
import tn.esprit.autoloc.autolocapi.mapper.EquipementMapper;
import tn.esprit.autoloc.autolocapi.service.IEquipementService;
import tn.esprit.autoloc.autolocapi.web.dto.EquipementDTO;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(name = "Équipements", description = "Gestion des équipements")
public class EquipementController {

    private final IEquipementService equipementService;
    private final EquipementMapper equipementMapper;

    @Operation(summary = "Lister les équipements")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    @GetMapping
    public List<EquipementDTO> findAll() {
        return equipementMapper.toDtoList(equipementService.getAll());
    }

    @Operation(summary = "Consulter un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement trouvé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @GetMapping("/{id}")
    public EquipementDTO findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return equipementMapper.toDto(equipementService.getById(id));
    }

    @Operation(summary = "Créer un équipement")
    @ApiResponse(responseCode = "201", description = "Équipement créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementDTO create(@Valid @RequestBody EquipementDTO dto) {
        return equipementMapper.toDto(
                equipementService.add(equipementMapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement modifié")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public EquipementDTO update(@Parameter(description = "Identifiant") @PathVariable Long id,
                                @Valid @RequestBody EquipementDTO dto) {
        return equipementMapper.toDto(
                equipementService.update(id, equipementMapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer un équipement")
    @ApiResponse(responseCode = "204", description = "Équipement supprimé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        equipementService.delete(id);
    }

    @Operation(summary = "Associer un véhicule à un équipement")
    @ApiResponse(responseCode = "204", description = "Équipement associé")
    @ApiResponse(responseCode = "404", description = "Équipement ou véhicule introuvable")
    @PutMapping("/{idEquipement}/vehicules/{idVehicule}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void affecterVehicule(
            @Parameter(description = "Identifiant de l'équipement") @PathVariable Long idEquipement,
            @Parameter(description = "Identifiant du véhicule") @PathVariable Long idVehicule) {
        equipementService.affecterVehicule(idEquipement, idVehicule);
    }

    @Operation(summary = "Dissocier un véhicule d'un équipement")
    @ApiResponse(responseCode = "204", description = "Équipement dissocié")
    @ApiResponse(responseCode = "404", description = "Équipement ou véhicule introuvable")
    @DeleteMapping("/{idEquipement}/vehicules/{idVehicule}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desaffecterVehicule(
            @Parameter(description = "Identifiant de l'équipement") @PathVariable Long idEquipement,
            @Parameter(description = "Identifiant du véhicule") @PathVariable Long idVehicule) {
        equipementService.desaffecterVehicule(idEquipement, idVehicule);
    }
}
