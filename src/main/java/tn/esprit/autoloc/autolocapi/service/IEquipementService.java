package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.domain.Equipement;

public interface IEquipementService {

    Equipement getById(Long id);

    List<Equipement> getAll();

    Equipement add(Equipement equipement);

    Equipement update(Long id, Equipement equipement);

    void delete(Long id);

    void affecterVehicule(Long idEquipement, Long idVehicule);

    void desaffecterVehicule(Long idEquipement, Long idVehicule);

    default Equipement findById(Long id) {
        return getById(id);
    }

    default List<Equipement> findAll() {
        return getAll();
    }

    default Equipement create(Equipement equipement) {
        return add(equipement);
    }

    default void deleteById(Long id) {
        delete(id);
    }
}
