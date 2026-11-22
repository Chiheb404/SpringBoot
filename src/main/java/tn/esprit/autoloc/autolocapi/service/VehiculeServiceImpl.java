package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    @Override
    public Vehicule getById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> getAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    @Transactional
    public Vehicule update(Long id, Vehicule vehicule, Long idAgence) {
        Vehicule existant = getById(id);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        existant.setAgence(chargerAgence(idAgence));
        return vehiculeRepository.save(existant);
    }

    @Override
    public void delete(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Vehicule add(Vehicule vehicule, Long idAgence) {
        vehicule.setIdVehicule(null);
        vehicule.setAgence(chargerAgence(idAgence));
        return vehiculeRepository.save(vehicule);
    }

    // idAgence est optionnel : null = véhicule sans agence
    private Agence chargerAgence(Long idAgence) {
        if (idAgence == null) {
            return null;
        }
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
    }

    @Override
    @Transactional
    public Vehicule affecterAAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = getById(idVehicule);
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        vehicule.setAgence(agence);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicule.getEquipements().add(equipement);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicule.getEquipements().remove(equipement);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public List<Vehicule> afficherVehiculesParIdContrat(Long idContrat) {
        List<Vehicule> vehicules = vehiculeRepository.findByReservations_Contrat_IdContrat(idContrat);
        log.info("=== Liste des Véhicules pour le Contrat {} ===", idContrat);
        vehicules.forEach(v -> log.info("Vehicule: id={}, immatriculation={}, marque={}, modele={}",
                v.getIdVehicule(), v.getImmatriculation(), v.getMarque(), v.getModele()));
        return vehicules;
    }

    @Override
    public List<Vehicule> afficherVehiculesParIdMaintenance(Long idMaintenance) {
        List<Vehicule> vehicules = vehiculeRepository.findByMaintenances_IdMaintenance(idMaintenance);
        log.info("=== Liste des Véhicules pour la Maintenance {} ===", idMaintenance);
        vehicules.forEach(v -> log.info("Vehicule: id={}, immatriculation={}, marque={}, modele={}",
                v.getIdVehicule(), v.getImmatriculation(), v.getMarque(), v.getModele()));
        return vehicules;
    }
}