package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.domain.Vehicule;

public interface IVehiculeService {

    Vehicule getById(Long id);

    List<Vehicule> getAll();

    boolean update(Long id, Vehicule vehicule);

    boolean delete(Long id);

    Vehicule add(Vehicule vehicule);
}