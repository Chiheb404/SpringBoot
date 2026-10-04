package tn.esprit.autoloc.autolocapi.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="Client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @Column(nullable = false, length = 50)
    private Long idClient;
    @Column(nullable = false, length = 50)
    private String nom;
    @Column(nullable = false, length = 50)
    private String prenom;
    @Column(nullable = false, length = 50)
    private String email;
    @Column(nullable = false, length = 50)
    private String adresse;
    @Column(nullable = false, length = 50)
    private String telephone;
    @Column(nullable = false, length = 50)
    private String numPermis;
    @Column(nullable = false, length = 50)
    private Date dateInscription;

}
