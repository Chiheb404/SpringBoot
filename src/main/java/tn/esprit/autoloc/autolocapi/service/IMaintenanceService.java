package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.domain.Maintenance;

public interface IMaintenanceService {

    Maintenance getById(Long id);

    List<Maintenance> getAll();

    Maintenance add(Maintenance maintenance, Long idVehicule);

    Maintenance update(Long id, Maintenance maintenance, Long idVehicule);

    void delete(Long id);

    List<Maintenance> afficherMaintenancesParIdVehicule(Long idVehicule);

    List<Maintenance> afficherMaintenancesParIdVehiculeJPQL(Long idVehicule);

}
