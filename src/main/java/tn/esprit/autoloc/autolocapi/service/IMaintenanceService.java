package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.domain.Maintenance;

public interface IMaintenanceService {

    Maintenance getById(Long id);

    List<Maintenance> getAll();

    Maintenance add(Maintenance maintenance, Long idVehicule);

    Maintenance update(Long id, Maintenance maintenance, Long idVehicule);

    void delete(Long id);

    default Maintenance findById(Long id) {
        return getById(id);
    }

    default List<Maintenance> findAll() {
        return getAll();
    }

    default Maintenance create(Maintenance maintenance) {
        return add(maintenance, null);
    }

    default Maintenance update(Long id, Maintenance maintenance) {
        return update(id, maintenance, null);
    }

    default void deleteById(Long id) {
        delete(id);
    }
}
