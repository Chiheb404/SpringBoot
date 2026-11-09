package tn.esprit.autoloc.autolocapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.autolocapi.domain.*;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.*;
import tn.esprit.autoloc.autolocapi.service.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceTests {

    @Mock
    private IClientRepository clientRepository;
    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private IEmployeRepository employeRepository;
    @Mock
    private IAgenceRepository agenceRepository;
    @InjectMocks
    private EmployeServiceImpl employeService;

    @Mock
    private IReservationRepository reservationRepository;
    @Mock
    private IVehiculeRepository vehiculeRepository;
    @Mock
    private IContratRepository contratRepository;
    @InjectMocks
    private ReservationServiceImpl reservationService;

    @InjectMocks
    private ContratServiceImpl contratService;

    @Mock
    private IPaiementRepository paiementRepository;
    @InjectMocks
    private PaiementServiceImpl paiementService;

    @Mock
    private IMaintenanceRepository maintenanceRepository;
    @InjectMocks
    private MaintenanceServiceImpl maintenanceService;

    @Mock
    private IEquipementRepository equipementRepository;
    @InjectMocks
    private EquipementServiceImpl equipementService;

    @Test
    void testClientService_AddAndGetById() {
        Client client = new Client();
        client.setIdClient(99L);
        client.setNom("Ali");

        when(clientRepository.save(any(Client.class))).thenAnswer(i -> {
            Client c = i.getArgument(0);
            assertNull(c.getIdClient()); // ID was reset to null
            c.setIdClient(1L);
            return c;
        });

        Client created = clientService.add(client);
        assertEquals(1L, created.getIdClient());

        when(clientRepository.findById(1L)).thenReturn(Optional.of(created));
        Client found = clientService.getById(1L);
        assertEquals("Ali", found.getNom());

        when(clientRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> clientService.getById(2L));
    }

    @Test
    void testEmployeService_AddWithAgence() {
        Agence agence = new Agence();
        agence.setIdAgence(10L);

        when(agenceRepository.findById(10L)).thenReturn(Optional.of(agence));
        when(employeRepository.save(any(Employe.class))).thenAnswer(i -> {
            Employe e = i.getArgument(0);
            e.setIdEmploye(1L);
            return e;
        });

        Employe employe = new Employe();
        employe.setNom("Karim");

        Employe saved = employeService.add(employe, 10L);
        assertNotNull(saved.getAgence());
        assertEquals(10L, saved.getAgence().getIdAgence());

        when(agenceRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeService.add(new Employe(), 999L));
    }

    @Test
    void testReservationService_VehiculeNonDisponibleThrowsException() {
        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(1L);
        vehicule.setStatut(StatutVehicule.MAINTENANCE);

        Client client = new Client();
        client.setIdClient(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(vehicule));

        Reservation reservation = new Reservation();
        reservation.setDateDebut(new Date(1000000));
        reservation.setDateFin(new Date(2000000));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> reservationService.add(reservation, 1L, 1L, null));
        assertTrue(ex.getMessage().contains("disponible"));
    }

    @Test
    void testReservationService_DateInvalideThrowsException() {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(new Date(2000000));
        reservation.setDateFin(new Date(1000000)); // fin before debut

        assertThrows(IllegalArgumentException.class,
                () -> reservationService.add(reservation, 1L, 1L, null));
    }

    @Test
    void testContratService_CalculeMontantTotal() {
        Vehicule vehicule = new Vehicule();
        vehicule.setTarifJournalier(new BigDecimal("100.00"));

        Reservation res = new Reservation();
        res.setIdReservation(1L);
        res.setVehicule(vehicule);
        long now = System.currentTimeMillis();
        res.setDateDebut(new Date(now));
        res.setDateFin(new Date(now + 3 * 24 * 60 * 60 * 1000L)); // 3 days

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(res));
        when(contratRepository.save(any(Contrat.class))).thenAnswer(i -> i.getArgument(0));

        Contrat contrat = new Contrat();
        contrat.setMontantTotal(0); // auto-calculate

        Contrat saved = contratService.add(contrat, 1L);
        assertEquals(300, saved.getMontantTotal());
    }

    @Test
    void testPaiementService_AddThrowsWhenContratMissing() {
        when(contratRepository.findById(99L)).thenReturn(Optional.empty());

        Paiement p = new Paiement();
        assertThrows(ResourceNotFoundException.class, () -> paiementService.add(p, 99L));
    }

    @Test
    void testMaintenanceService_DeleteNotFound() {
        when(maintenanceRepository.existsById(99L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> maintenanceService.delete(99L));
    }

    @Test
    void testEquipementService_AffecterEtDesaffecterVehicule() {
        Equipement eq = new Equipement();
        eq.setIdEquipement(5L);
        eq.setLibelle("GPS");

        Vehicule v = new Vehicule();
        v.setIdVehicule(10L);
        v.setEquipements(new HashSet<>());

        when(equipementRepository.findById(5L)).thenReturn(Optional.of(eq));
        when(vehiculeRepository.findById(10L)).thenReturn(Optional.of(v));

        equipementService.affecterVehicule(5L, 10L);
        assertTrue(v.getEquipements().contains(eq));

        equipementService.desaffecterVehicule(5L, 10L);
        assertFalse(v.getEquipements().contains(eq));
    }
}

