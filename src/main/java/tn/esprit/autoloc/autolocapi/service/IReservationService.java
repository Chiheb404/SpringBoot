package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import tn.esprit.autoloc.autolocapi.domain.Reservation;

public interface IReservationService {

    Reservation getById(Long id);

    List<Reservation> getAll();

    Reservation add(Reservation reservation, Long idClient, Long idVehicule, Long idContrat);

    Reservation update(Long id, Reservation reservation, Long idClient, Long idVehicule, Long idContrat);

    void delete(Long id);

    default Reservation add(Reservation reservation, Long idClient, Long idVehicule) {
        return add(reservation, idClient, idVehicule, null);
    }

    default Reservation update(Long id, Reservation reservation, Long idClient, Long idVehicule) {
        return update(id, reservation, idClient, idVehicule, null);
    }

    default Reservation findById(Long id) {
        return getById(id);
    }

    default List<Reservation> findAll() {
        return getAll();
    }

    default void deleteById(Long id) {
        delete(id);
    }
}
