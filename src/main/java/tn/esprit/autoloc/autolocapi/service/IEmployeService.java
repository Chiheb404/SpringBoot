package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.domain.Employe;

public interface IEmployeService {

    Employe getById(Long id);

    List<Employe> getAll();

    Employe add(Employe employe, Long idAgence);

    Employe update(Long id, Employe employe, Long idAgence);

    void delete(Long id);

    default Employe findById(Long id) {
        return getById(id);
    }

    default List<Employe> findAll() {
        return getAll();
    }

    default Employe create(Employe employe) {
        return add(employe, null);
    }

    default Employe update(Long id, Employe employe) {
        return update(id, employe, null);
    }

    default void deleteById(Long id) {
        delete(id);
    }
}
