package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.domain.Paiement;

public interface IPaiementService {

    Paiement getById(Long id);

    List<Paiement> getAll();

    Paiement add(Paiement paiement, Long idContrat);

    Paiement update(Long id, Paiement paiement, Long idContrat);

    void delete(Long id);

    default Paiement findById(Long id) {
        return getById(id);
    }

    default List<Paiement> findAll() {
        return getAll();
    }
}
