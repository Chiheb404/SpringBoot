package tn.esprit.autoloc.autolocapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.autolocapi.domain.RoleEmploye;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeDTO {

    private Long idEmploye;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 255, message = "Le nom est trop long")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 255, message = "Le prénom est trop long")
    private String prenom;

    @NotNull(message = "Le rôle est obligatoire")
    private RoleEmploye roleEmploye;

    private Long idAgence;
}