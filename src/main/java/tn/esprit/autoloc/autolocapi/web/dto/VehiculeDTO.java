package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeDTO {

    private Long idVehicule;

    @NotBlank(message = "L'immatriculation est obligatoire")
    @Size(max = 20, message = "L'immatriculation ne doit pas dépasser 20 caractères")
    private String immatriculation;

    @NotBlank(message = "La marque est obligatoire")
    @Size(max = 50, message = "La marque ne doit pas dépasser 50 caractères")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    @Size(max = 50, message = "Le modèle ne doit pas dépasser 50 caractères")
    private String modele;

    @NotNull(message = "La catégorie est obligatoire")
    private CategorieVehicule categorie;

    @NotNull(message = "Le tarif journalier est obligatoire")
    @PositiveOrZero(message = "Le tarif journalier ne peut pas être négatif")
    @Digits(integer = 8, fraction = 2, message = "Format du tarif invalide (8 entiers, 2 décimales max)")
    private BigDecimal tarifJournalier;

    @NotNull(message = "Le statut est obligatoire")
    private StatutVehicule statut;

    // Relation ManyToOne : on expose l'id (et le nom pour la lecture)
    private Long idAgence;
    private String nomAgence;
}