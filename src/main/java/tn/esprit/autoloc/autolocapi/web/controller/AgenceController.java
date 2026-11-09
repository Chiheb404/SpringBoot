package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.mapper.AgenceMapper;
import tn.esprit.autoloc.autolocapi.service.IAgenceService;
import tn.esprit.autoloc.autolocapi.web.dto.AgenceDTO;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences")
public class AgenceController {

    private final IAgenceService agenceService;
    private final AgenceMapper agenceMapper;

    @Operation(summary = "Lister les agences")
    @GetMapping
    public List<AgenceDTO> findAll() {
        return agenceMapper.toDtoList(agenceService.findAll());
    }

    @Operation(summary = "Consulter une agence")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @GetMapping("/{id}")
    public AgenceDTO findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return agenceMapper.toDto(agenceService.findById(id));
    }

    @Operation(summary = "Créer une agence")
    @ApiResponse(responseCode = "201", description = "Agence créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceDTO create(@Valid @RequestBody AgenceDTO dto) {
        return agenceMapper.toDto(agenceService.create(agenceMapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier une agence")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public AgenceDTO update(@Parameter(description = "Identifiant") @PathVariable Long id,
                            @Valid @RequestBody AgenceDTO dto) {
        return agenceMapper.toDto(agenceService.update(id, agenceMapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer une agence")
    @ApiResponse(responseCode = "204", description = "Agence supprimée")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        agenceService.deleteById(id);
    }
}