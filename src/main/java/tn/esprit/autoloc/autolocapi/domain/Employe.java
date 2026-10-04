package tn.esprit.autoloc.autolocapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Employe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employe {
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idEmploye;
    @Column
    private String nom;
    @Column
    private String prenom;
    @Enumerated(EnumType.STRING)
    private RoleEmploye roleEmploye;

}
