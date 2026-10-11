package tn.esprit.autoloc.autolocapi.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();


}
