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
import tn.esprit.autoloc.autolocapi.mapper.PaiementMapper;
import tn.esprit.autoloc.autolocapi.service.IPaiementService;
import tn.esprit.autoloc.autolocapi.web.dto.PaiementDTO;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "Gestion des paiements")
public class PaiementController {

    private final IPaiementService paiementService;
    private final PaiementMapper paiementMapper;

    @Operation(summary = "Lister les paiements")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    @GetMapping
    public List<PaiementDTO> findAll() {
        return paiementMapper.toDtoList(paiementService.getAll());
    }

    @Operation(summary = "Consulter un paiement")
    @ApiResponse(responseCode = "200", description = "Paiement trouvé")
    @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    @GetMapping("/{id}")
    public PaiementDTO findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return paiementMapper.toDto(paiementService.getById(id));
    }

    @Operation(summary = "Créer un paiement")
    @ApiResponse(responseCode = "201", description = "Paiement créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaiementDTO create(@Valid @RequestBody PaiementDTO dto) {
        return paiementMapper.toDto(
                paiementService.add(paiementMapper.toEntity(dto), dto.getIdContrat()));
    }

    @Operation(summary = "Modifier un paiement")
    @ApiResponse(responseCode = "200", description = "Paiement modifié")
    @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public PaiementDTO update(@Parameter(description = "Identifiant") @PathVariable Long id,
                              @Valid @RequestBody PaiementDTO dto) {
        return paiementMapper.toDto(
                paiementService.update(id, paiementMapper.toEntity(dto), dto.getIdContrat()));
    }

    @Operation(summary = "Supprimer un paiement")
    @ApiResponse(responseCode = "204", description = "Paiement supprimé")
    @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        paiementService.delete(id);
    }
}
