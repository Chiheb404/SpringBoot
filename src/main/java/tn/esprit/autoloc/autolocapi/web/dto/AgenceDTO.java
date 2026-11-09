package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgenceDTO {

    private Long idAgence;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String nom;

    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 50, message = "La ville ne doit pas dépasser 50 caractères")
    private String ville;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 50, message = "L'adresse ne doit pas dépasser 50 caractères")
    private String adresse;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^[+]?[0-9 ]{8,15}$", message = "Numéro de téléphone invalide")
    private String telephone;
}