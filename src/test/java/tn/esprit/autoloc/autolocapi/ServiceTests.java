
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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

    @InjectMocks
    private VehiculeServiceImpl vehiculeService;


    @Test
    void testClientService_AddAndGetById() {
        Client client = new Client();
        client.setIdClient(99L);
        client.setNom("Ali");

        when(clientRepository.save(any(Client.class))).thenAnswer(i -> {
            Client c = i.getArgument(0);
            assertNull(c.getIdClient());
            c.setIdClient(1L);
            return c;
        });

        Client created = clientService.add(client);
        assertEquals(1L, created.getIdClient());

        when(clientRepository.findById(1L)).thenReturn(Optional.of(created));
        Client found = clientService.getById(1L);
        assertEquals("Ali", found.getNom());

        when(clientRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(
                ResourceNotFoundException.class,
                () -> clientService.getById(2L)
        );
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

        Employe invalidEmploye = new Employe();

        assertThrows(
                ResourceNotFoundException.class,
                () -> employeService.add(invalidEmploye, 999L)
        );
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

        Long clientId = 1L;
        Long vehiculeId = 1L;

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.add(reservation, clientId, vehiculeId, null)
        );

        assertTrue(ex.getMessage().contains("disponible"));
    }


    @Test
    void testReservationService_DateInvalideThrowsException() {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(new Date(2000000));
        reservation.setDateFin(new Date(1000000));

        Long clientId = 1L;
        Long vehiculeId = 1L;

        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.add(reservation, clientId, vehiculeId, null)
        );
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
        res.setDateFin(new Date(now + 3 * 24 * 60 * 60 * 1000L));

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(res));
        when(contratRepository.save(any(Contrat.class)))
                .thenAnswer(i -> i.getArgument(0));

        Contrat contrat = new Contrat();
        contrat.setMontantTotal(0);

        Contrat saved = contratService.add(contrat, 1L);
        assertEquals(300, saved.getMontantTotal());
    }


    @Test
    void testPaiementService_AddThrowsWhenContratMissing() {
        when(contratRepository.findById(99L)).thenReturn(Optional.empty());

        Paiement paiement = new Paiement();
        Long contratId = 99L;

        assertThrows(
                ResourceNotFoundException.class,
                () -> paiementService.add(paiement, contratId)
        );
    }


    @Test
    void testMaintenanceService_DeleteNotFound() {
        when(maintenanceRepository.existsById(99L)).thenReturn(false);

        Long maintenanceId = 99L;

        assertThrows(
                ResourceNotFoundException.class,
                () -> maintenanceService.delete(maintenanceId)
        );
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

    @Test
    void testAfficherVehiculesParIdContrat() {
        Vehicule v = new Vehicule();
        v.setIdVehicule(1L);
        v.setImmatriculation("123-TN-456");

        when(vehiculeRepository.findByReservations_Contrat_IdContrat(10L))
                .thenReturn(List.of(v));

        List<Vehicule> result = vehiculeService.afficherVehiculesParIdContrat(10L);
        assertEquals(1, result.size());
        assertEquals("123-TN-456", result.get(0).getImmatriculation());
    }

    @Test
    void testAfficherContratsParIdVehicule() {
        Contrat c = new Contrat();
        c.setIdContrat(20L);
        c.setMontantTotal(500);

        when(contratRepository.findByReservation_Vehicule_IdVehicule(1L))
                .thenReturn(List.of(c));

        List<Contrat> result = contratService.afficherContratsParIdVehicule(1L);
        assertEquals(1, result.size());
        assertEquals(500, result.get(0).getMontantTotal());
    }

    @Test
    void testAfficherMaintenancesParIdVehicule() {
        Maintenance m = new Maintenance();
        m.setIdMaintenance(30L);
        m.setDescription("Changement plaquettes");

        when(maintenanceRepository.findByVehicule_IdVehicule(1L))
                .thenReturn(List.of(m));

        List<Maintenance> result = maintenanceService.afficherMaintenancesParIdVehicule(1L);
        assertEquals(1, result.size());
        assertEquals("Changement plaquettes", result.get(0).getDescription());
    }

    @Test
    void testAfficherVehiculesParIdMaintenance() {
        Vehicule v = new Vehicule();
        v.setIdVehicule(1L);
        v.setMarque("Renault");

        when(vehiculeRepository.findVehiculesByIdMaintenance(30L))
                .thenReturn(List.of(v));

        List<Vehicule> result = vehiculeService.afficherVehiculesParIdMaintenance(30L);
        assertEquals(1, result.size());
        assertEquals("Renault", result.get(0).getMarque());
    }
}
