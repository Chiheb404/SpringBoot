package tn.esprit.autoloc.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;

import java.util.List;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {

    // Liste des Maintenances par Id Véhicule
    List<Maintenance> findByVehicule_IdVehicule(Long idVehicule);
}
