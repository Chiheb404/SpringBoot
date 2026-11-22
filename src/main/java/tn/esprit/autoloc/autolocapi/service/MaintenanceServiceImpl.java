package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IMaintenanceRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Maintenance getById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> getAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    @Transactional
    public Maintenance add(Maintenance maintenance, Long idVehicule) {
        maintenance.setIdMaintenance(null);
        validerDates(maintenance);
        maintenance.setVehicule(chargerVehicule(idVehicule));
        return maintenanceRepository.save(maintenance);
    }

    @Override
    @Transactional
    public Maintenance update(Long id, Maintenance maintenance, Long idVehicule) {
        Maintenance existant = getById(id);
        validerDates(maintenance);
        existant.setDateDebut(maintenance.getDateDebut());
        existant.setDateFin(maintenance.getDateFin());
        existant.setDescription(maintenance.getDescription());
        existant.setVehicule(chargerVehicule(idVehicule));
        return maintenanceRepository.save(existant);
    }

    @Override
    public void delete(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    private void validerDates(Maintenance maintenance) {
        if (maintenance.getDateDebut() != null && maintenance.getDateFin() != null
                && maintenance.getDateFin().before(maintenance.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
    }

    private Vehicule chargerVehicule(Long idVehicule) {
        if (idVehicule == null) {
            return null;
        }
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
    }

    @Override
    public List<Maintenance> afficherMaintenancesParIdVehicule(Long idVehicule) {
        List<Maintenance> maintenances = maintenanceRepository.findByVehicule_IdVehicule(idVehicule);
        log.info("=== Liste des Maintenances pour le Véhicule {} ===", idVehicule);
        maintenances.forEach(m -> log.info("Maintenance: id={}, description={}, dateDebut={}, dateFin={}",
                m.getIdMaintenance(), m.getDescription(), m.getDateDebut(), m.getDateFin()));
        return maintenances;
    }
}
