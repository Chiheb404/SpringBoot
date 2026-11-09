package tn.esprit.autoloc.autolocapi.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long idReservation;

    @NotNull(message = "La date de début est obligatoire")
    private Date dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private Date dateFin;

    @NotNull(message = "Le statut est obligatoire")
    private StatutReservation statut;

    @NotNull(message = "Le client est obligatoire")
    private Long idClient;

    @NotNull(message = "Le véhicule est obligatoire")
    private Long idVehicule;

    private Long idContrat;

    // Validation croisée : dateFin >= dateDebut
    @JsonIgnore
    @AssertTrue(message = "La date de fin doit être postérieure ou égale à la date de début")
    public boolean isPeriodeValide() {
        return dateDebut == null || dateFin == null || !dateFin.before(dateDebut);
    }
}