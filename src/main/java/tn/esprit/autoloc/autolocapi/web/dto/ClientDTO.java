package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private Long idClient;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Size(max = 50, message = "L'email ne doit pas dépasser 50 caractères")
    private String email;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 50, message = "L'adresse ne doit pas dépasser 50 caractères")
    private String adresse;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^[+]?[0-9 ]{8,15}$", message = "Numéro de téléphone invalide")
    private String telephone;

    @NotBlank(message = "Le numéro de permis est obligatoire")
    @Size(max = 50, message = "Le numéro de permis ne doit pas dépasser 50 caractères")
    private String numPermis;

    @NotNull(message = "La date d'inscription est obligatoire")
    @PastOrPresent(message = "La date d'inscription ne peut pas être dans le futur")
    private Date dateInscription;
}