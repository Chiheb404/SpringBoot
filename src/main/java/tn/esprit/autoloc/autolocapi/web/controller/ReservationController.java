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
import tn.esprit.autoloc.autolocapi.mapper.ReservationMapper;
import tn.esprit.autoloc.autolocapi.service.IReservationService;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationDTO;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservations", description = "Gestion des réservations")
public class ReservationController {

    private final IReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @Operation(summary = "Lister les réservations")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    @GetMapping
    public List<ReservationDTO> findAll() {
        return reservationMapper.toDtoList(reservationService.getAll());
    }

    @Operation(summary = "Consulter une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation trouvée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @GetMapping("/{id}")
    public ReservationDTO findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return reservationMapper.toDto(reservationService.getById(id));
    }

    @Operation(summary = "Créer une réservation")
    @ApiResponse(responseCode = "201", description = "Réservation créée")
    @ApiResponse(responseCode = "400", description = "Données invalides (ex. date de fin antérieure)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO create(@Valid @RequestBody ReservationDTO dto) {
        return reservationMapper.toDto(
                reservationService.add(reservationMapper.toEntity(dto),
                        dto.getIdClient(),
                        dto.getIdVehicule(),
                        dto.getIdContrat()));
    }

    @Operation(summary = "Modifier une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation modifiée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public ReservationDTO update(@Parameter(description = "Identifiant") @PathVariable Long id,
                                 @Valid @RequestBody ReservationDTO dto) {
        return reservationMapper.toDto(
                reservationService.update(id, reservationMapper.toEntity(dto),
                        dto.getIdClient(),
                        dto.getIdVehicule(),
                        dto.getIdContrat()));
    }

    @Operation(summary = "Supprimer une réservation")
    @ApiResponse(responseCode = "204", description = "Réservation supprimée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        reservationService.delete(id);
    }
}
