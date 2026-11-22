package tn.esprit.autoloc.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Contrat;

import java.util.List;

public interface IContratRepository extends JpaRepository<Contrat, Long> {

    // Liste des contrats par Id Véhicule
    List<Contrat> findByReservation_Vehicule_IdVehicule(Long idVehicule);
}
