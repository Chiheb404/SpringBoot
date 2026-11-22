package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.domain.Contrat;

public interface IContratService {

    Contrat getById(Long id);

    List<Contrat> getAll();

    Contrat add(Contrat contrat, Long idReservation);

    Contrat update(Long id, Contrat contrat, Long idReservation);

    void delete(Long id);

    List<Contrat> afficherContratsParIdVehicule(Long idVehicule);
}
