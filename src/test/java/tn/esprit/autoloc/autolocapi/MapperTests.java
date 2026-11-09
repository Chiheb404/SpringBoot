package tn.esprit.autoloc.autolocapi;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import tn.esprit.autoloc.autolocapi.domain.*;
import tn.esprit.autoloc.autolocapi.mapper.*;
import tn.esprit.autoloc.autolocapi.web.dto.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapperTests {

    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);
    private final EmployeMapper employeMapper = Mappers.getMapper(EmployeMapper.class);
    private final ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);
    private final ContratMapper contratMapper = Mappers.getMapper(ContratMapper.class);
    private final PaiementMapper paiementMapper = Mappers.getMapper(PaiementMapper.class);
    private final MaintenanceMapper maintenanceMapper = Mappers.getMapper(MaintenanceMapper.class);
    private final EquipementMapper equipementMapper = Mappers.getMapper(EquipementMapper.class);

    @Test
    void testClientMapper() {
        Client client = new Client();
        client.setIdClient(1L);
        client.setNom("Ben Ali");
        client.setPrenom("Mohamed");
        client.setEmail("mohamed@example.com");
        client.setAdresse("Tunis");
        client.setTelephone("+21612345678");
        client.setNumPermis("B123456");
        client.setDateInscription(new Date());

        ClientDTO dto = clientMapper.toDto(client);
        assertNotNull(dto);
        assertEquals(1L, dto.getIdClient());
        assertEquals("Ben Ali", dto.getNom());

        Client entity = clientMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals("Ben Ali", entity.getNom());
        assertNotNull(entity.getReservations());
    }

    @Test
    void testEmployeMapper() {
        Agence agence = new Agence();
        agence.setIdAgence(10L);

        Employe employe = new Employe();
        employe.setIdEmploye(2L);
        employe.setNom("Salah");
        employe.setPrenom("Ahmed");
        employe.setRoleEmploye(RoleEmploye.AGENT);
        employe.setAgence(agence);

        EmployeDTO dto = employeMapper.toDto(employe);
        assertNotNull(dto);
        assertEquals(2L, dto.getIdEmploye());
        assertEquals(10L, dto.getIdAgence());

        Employe entity = employeMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals("Salah", entity.getNom());
        assertNull(entity.getAgence());
    }

    @Test
    void testReservationMapper() {
        Client client = new Client();
        client.setIdClient(1L);

        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(2L);

        Contrat contrat = new Contrat();
        contrat.setIdContrat(3L);

        Reservation reservation = new Reservation();
        reservation.setIdReservation(5L);
        reservation.setDateDebut(new Date());
        reservation.setDateFin(new Date());
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        reservation.setContrat(contrat);

        ReservationDTO dto = reservationMapper.toDto(reservation);
        assertNotNull(dto);
        assertEquals(5L, dto.getIdReservation());
        assertEquals(1L, dto.getIdClient());
        assertEquals(2L, dto.getIdVehicule());
        assertEquals(3L, dto.getIdContrat());

        Reservation entity = reservationMapper.toEntity(dto);
        assertNotNull(entity);
        assertNull(entity.getClient());
        assertNull(entity.getVehicule());
        assertNull(entity.getContrat());
    }

    @Test
    void testContratMapper() {
        Reservation reservation = new Reservation();
        reservation.setIdReservation(10L);

        Paiement p1 = new Paiement();
        p1.setIdPaiement(101L);
        Paiement p2 = new Paiement();
        p2.setIdPaiement(102L);

        Contrat contrat = new Contrat();
        contrat.setIdContrat(1L);
        contrat.setDateSignature(new Date());
        contrat.setMontantTotal(500);
        contrat.setValide(true);
        contrat.setReservation(reservation);
        contrat.setPaiements(List.of(p1, p2));

        ContratDTO dto = contratMapper.toDto(contrat);
        assertNotNull(dto);
        assertEquals(1L, dto.getIdContrat());
        assertEquals(10L, dto.getIdReservation());
        assertEquals(2, dto.getIdPaiements().size());
        assertTrue(dto.getIdPaiements().contains(101L));
        assertTrue(dto.getIdPaiements().contains(102L));

        Contrat entity = contratMapper.toEntity(dto);
        assertNotNull(entity);
        assertNull(entity.getReservation());
        assertNotNull(entity.getPaiements());
    }

    @Test
    void testPaiementMapper() {
        Contrat contrat = new Contrat();
        contrat.setIdContrat(20L);

        Paiement paiement = new Paiement();
        paiement.setIdPaiement(30L);
        paiement.setMontant(new BigDecimal("250.50"));
        paiement.setDatePaiement(new Date());
        paiement.setModePaiement(ModePaiement.CARTE);
        paiement.setContrat(contrat);

        PaiementDTO dto = paiementMapper.toDto(paiement);
        assertNotNull(dto);
        assertEquals(30L, dto.getIdPaiement());
        assertEquals(new BigDecimal("250.50"), dto.getMontant());
        assertEquals(20L, dto.getIdContrat());

        Paiement entity = paiementMapper.toEntity(dto);
        assertNotNull(entity);
        assertNull(entity.getContrat());
    }

    @Test
    void testMaintenanceMapper() {
        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(40L);

        Maintenance maintenance = new Maintenance();
        maintenance.setIdMaintenance(50L);
        maintenance.setDateDebut(new Date());
        maintenance.setDateFin(new Date());
        maintenance.setDescription("Vidange");
        maintenance.setVehicule(vehicule);

        MaintenanceDTO dto = maintenanceMapper.toDto(maintenance);
        assertNotNull(dto);
        assertEquals(50L, dto.getIdMaintenance());
        assertEquals(40L, dto.getIdVehicule());

        Maintenance entity = maintenanceMapper.toEntity(dto);
        assertNotNull(entity);
        assertNull(entity.getVehicule());
    }

    @Test
    void testEquipementMapper() {
        Equipement equipement = new Equipement();
        equipement.setIdEquipement(60L);
        equipement.setLibelle("GPS");

        EquipementDTO dto = equipementMapper.toDto(equipement);
        assertNotNull(dto);
        assertEquals(60L, dto.getIdEquipement());
        assertEquals("GPS", dto.getLibelle());

        Equipement entity = equipementMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals("GPS", entity.getLibelle());
        assertNotNull(entity.getVehicules());
    }
}
