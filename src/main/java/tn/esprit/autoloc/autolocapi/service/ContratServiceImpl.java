package tn.esprit.autoloc.autolocapi.service;

import java.math.BigDecimal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;

    @Override
    public Contrat getById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> getAll() {
        return contratRepository.findAll();
    }

    @Override
    @Transactional
    public Contrat add(Contrat contrat, Long idReservation) {
        contrat.setIdContrat(null);

        if (idReservation == null) {
            throw new IllegalArgumentException("La réservation est obligatoire");
        }
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", idReservation));

        if (contrat.getMontantTotal() <= 0 && reservation.getVehicule() != null
                && reservation.getVehicule().getTarifJournalier() != null
                && reservation.getDateDebut() != null && reservation.getDateFin() != null) {
            long diffMillis = reservation.getDateFin().getTime() - reservation.getDateDebut().getTime();
            long days = Math.max(1, diffMillis / (1000L * 60 * 60 * 24));
            int montantCalcule = reservation.getVehicule().getTarifJournalier()
                    .multiply(BigDecimal.valueOf(days))
                    .intValue();
            contrat.setMontantTotal(montantCalcule);
        }

        contrat.setReservation(reservation);
        return contratRepository.save(contrat);
    }

    @Override
    @Transactional
    public Contrat update(Long id, Contrat contrat, Long idReservation) {
        Contrat existant = getById(id);
        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.isValide());

        if (idReservation != null) {
            Reservation reservation = reservationRepository.findById(idReservation)
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", idReservation));
            existant.setReservation(reservation);
        }

        return contratRepository.save(existant);
    }

    @Override
    public void delete(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }
}
