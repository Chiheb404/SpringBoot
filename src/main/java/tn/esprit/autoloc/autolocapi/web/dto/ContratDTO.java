package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContratDTO {

    private Long idContrat;

    @NotNull(message = "La date de signature est obligatoire")
    private Date dateSignature;

    @PositiveOrZero(message = "Le montant total ne peut pas être négatif")
    private int montantTotal;

    private boolean valide;

    @NotNull(message = "La réservation est obligatoire")
    private Long idReservation;

    // Lecture seule : ids des paiements associés
    private List<Long> idPaiements;
}