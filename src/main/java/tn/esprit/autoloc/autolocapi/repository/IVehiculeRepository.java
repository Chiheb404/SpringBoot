package tn.esprit.autoloc.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.util.List;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    // Liste des Véhicules par Id contrat
    List<Vehicule> findByReservations_Contrat_IdContrat(Long idContrat);

    // Liste des Véhicules par Id Maintenance
    List<Vehicule> findByMaintenances_IdMaintenance(Long idMaintenance);
}
