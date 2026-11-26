package tn.esprit.autoloc.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.util.List;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    List<Vehicule> findByReservations_Contrat_IdContrat(Long idContrat);


    //List<Vehicule> findByMaintenances_IdMaintenance(Long idMaintenance);

    @Query("SELECT m.vehicule FROM Maintenance m WHERE m.idMaintenance = :idMaintenance")
    List<Vehicule> findVehiculesByIdMaintenance(@Param("idMaintenance") Long idMaintenance);

    //SQL

    @Query(value = "SELECT v.* FROM vehicule v " +
            "JOIN maintenance m ON m.vehicule_id_vehicule = v.id_vehicule " +
            "WHERE m.id_maintenance = :idMaintenance",
            nativeQuery = true)
    List<Vehicule> findVehiculesByIdMaintenanceSQL(@Param("idMaintenance") Long idMaintenance);


}
