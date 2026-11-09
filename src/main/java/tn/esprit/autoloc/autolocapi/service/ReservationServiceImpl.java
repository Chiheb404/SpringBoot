package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IClientRepository;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;

    @Override
    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional
    public Reservation add(Reservation reservation, Long idClient, Long idVehicule, Long idContrat) {
        reservation.setIdReservation(null);
        validerPeriode(reservation);

        if (idClient == null) {
            throw new IllegalArgumentException("Le client est obligatoire");
        }
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new ResourceNotFoundException("Client", idClient));

        if (idVehicule == null) {
            throw new IllegalArgumentException("Le véhicule est obligatoire");
        }
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));

        if (vehicule.getStatut() != StatutVehicule.DISPONIBLE) {
            throw new IllegalArgumentException("Le véhicule n'est pas disponible pour la réservation");
        }

        Contrat contrat = chargerContrat(idContrat);

        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        reservation.setContrat(contrat);

        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation update(Long id, Reservation reservation, Long idClient, Long idVehicule, Long idContrat) {
        Reservation existant = getById(id);
        validerPeriode(reservation);

        existant.setDateDebut(reservation.getDateDebut());
        existant.setDateFin(reservation.getDateFin());
        existant.setStatut(reservation.getStatut());

        if (idClient != null) {
            Client client = clientRepository.findById(idClient)
                    .orElseThrow(() -> new ResourceNotFoundException("Client", idClient));
            existant.setClient(client);
        }

        if (idVehicule != null) {
            Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
            existant.setVehicule(vehicule);
        }

        existant.setContrat(chargerContrat(idContrat));

        return reservationRepository.save(existant);
    }

    @Override
    public void delete(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private void validerPeriode(Reservation reservation) {
        if (reservation.getDateDebut() != null && reservation.getDateFin() != null
                && reservation.getDateFin().before(reservation.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
    }

    private Contrat chargerContrat(Long idContrat) {
        if (idContrat == null) {
            return null;
        }
        return contratRepository.findById(idContrat)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", idContrat));
    }
}
