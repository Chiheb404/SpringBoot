package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.domain.Vehicule;

public interface IVehiculeService {

    Vehicule getById(Long id);

    List<Vehicule> getAll();

    Vehicule update(Long id, Vehicule vehicule, Long idAgence);

    void delete(Long id);

    Vehicule add(Vehicule vehicule, Long idAgence);

    Vehicule affecterAAgence(Long idVehicule, Long idAgence);

    Vehicule affecterEquipement(Long idVehicule, Long idEquipement);

    Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement);
}