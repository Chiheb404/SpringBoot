package tn.esprit.autoloc.autolocapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.autoloc.autolocapi.domain.*;
import tn.esprit.autoloc.autolocapi.exception.GlobalExceptionHandler;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.*;
import tn.esprit.autoloc.autolocapi.service.*;
import tn.esprit.autoloc.autolocapi.web.controller.*;
import tn.esprit.autoloc.autolocapi.web.dto.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ControllerTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IClientService clientService;
    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @Mock
    private IEmployeService employeService;
    private final EmployeMapper employeMapper = Mappers.getMapper(EmployeMapper.class);

    @Mock
    private IReservationService reservationService;
    private final ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);

    @Mock
    private IContratService contratService;
    private final ContratMapper contratMapper = Mappers.getMapper(ContratMapper.class);

    @Mock
    private IPaiementService paiementService;
    private final PaiementMapper paiementMapper = Mappers.getMapper(PaiementMapper.class);

    @Mock
    private IMaintenanceService maintenanceService;
    private final MaintenanceMapper maintenanceMapper = Mappers.getMapper(MaintenanceMapper.class);

    @Mock
    private IEquipementService equipementService;
    private final EquipementMapper equipementMapper = Mappers.getMapper(EquipementMapper.class);

    private MockMvc mockMvcClient;
    private MockMvc mockMvcEmploye;
    private MockMvc mockMvcReservation;
    private MockMvc mockMvcContrat;
    private MockMvc mockMvcPaiement;
    private MockMvc mockMvcMaintenance;
    private MockMvc mockMvcEquipement;

    @BeforeEach
    void setUp() {
        mockMvcClient = MockMvcBuilders.standaloneSetup(new ClientController(clientService, clientMapper))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        mockMvcEmploye = MockMvcBuilders.standaloneSetup(new EmployeController(employeService, employeMapper))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        mockMvcReservation = MockMvcBuilders.standaloneSetup(new ReservationController(reservationService, reservationMapper))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        mockMvcContrat = MockMvcBuilders.standaloneSetup(new ContratController(contratService, contratMapper))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        mockMvcPaiement = MockMvcBuilders.standaloneSetup(new PaiementController(paiementService, paiementMapper))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        mockMvcMaintenance = MockMvcBuilders.standaloneSetup(new MaintenanceController(maintenanceService, maintenanceMapper))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        mockMvcEquipement = MockMvcBuilders.standaloneSetup(new EquipementController(equipementService, equipementMapper))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testClientCreate_Valid() throws Exception {
        ClientDTO dto = new ClientDTO();
        dto.setNom("Dupont");
        dto.setPrenom("Jean");
        dto.setEmail("jean.dupont@test.com");
        dto.setAdresse("Rue de Paris");
        dto.setTelephone("+21612345678");
        dto.setNumPermis("P123456");
        dto.setDateInscription(new Date());

        Client saved = clientMapper.toEntity(dto);
        saved.setIdClient(1L);
        when(clientService.add(any(Client.class))).thenReturn(saved);

        mockMvcClient.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idClient").value(1L))
                .andExpect(jsonPath("$.nom").value("Dupont"));
    }

    @Test
    void testClientGetById_NotFound() throws Exception {
        when(clientService.getById(99L)).thenThrow(new ResourceNotFoundException("Client", 99L));

        mockMvcClient.perform(get("/clients/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void testClientDelete() throws Exception {
        doNothing().when(clientService).delete(1L);

        mockMvcClient.perform(delete("/clients/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEmployeCreate_Invalid_Returns400() throws Exception {
        EmployeDTO dto = new EmployeDTO(); // blank fields -> validation error

        mockMvcEmploye.perform(post("/employes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void testReservationCreate() throws Exception {
        ReservationDTO dto = new ReservationDTO();
        Date start = new Date(1700000000000L);
        Date end = new Date(1700086400000L);
        dto.setDateDebut(start);
        dto.setDateFin(end);
        dto.setStatut(StatutReservation.CONFIRMEE);
        dto.setIdClient(10L);
        dto.setIdVehicule(20L);

        Reservation saved = reservationMapper.toEntity(dto);
        saved.setIdReservation(1L);
        Client c = new Client();
        c.setIdClient(10L);
        Vehicule v = new Vehicule();
        v.setIdVehicule(20L);
        saved.setClient(c);
        saved.setVehicule(v);

        when(reservationService.add(any(Reservation.class), eq(10L), eq(20L), any()))
                .thenReturn(saved);

        mockMvcReservation.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idReservation").value(1L))
                .andExpect(jsonPath("$.idClient").value(10L))
                .andExpect(jsonPath("$.idVehicule").value(20L));
    }

    @Test
    void testContratCreate() throws Exception {
        ContratDTO dto = new ContratDTO();
        dto.setDateSignature(new Date());
        dto.setMontantTotal(200);
        dto.setValide(true);
        dto.setIdReservation(1L);

        Contrat saved = contratMapper.toEntity(dto);
        saved.setIdContrat(5L);
        Reservation r = new Reservation();
        r.setIdReservation(1L);
        saved.setReservation(r);

        when(contratService.add(any(Contrat.class), eq(1L))).thenReturn(saved);

        mockMvcContrat.perform(post("/contrats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idContrat").value(5L))
                .andExpect(jsonPath("$.idReservation").value(1L));
    }

    @Test
    void testPaiementCreate() throws Exception {
        PaiementDTO dto = new PaiementDTO();
        dto.setMontant(new BigDecimal("150.00"));
        dto.setDatePaiement(new Date());
        dto.setModePaiement(ModePaiement.CARTE);
        dto.setIdContrat(5L);

        Paiement saved = paiementMapper.toEntity(dto);
        saved.setIdPaiement(10L);
        Contrat c = new Contrat();
        c.setIdContrat(5L);
        saved.setContrat(c);

        when(paiementService.add(any(Paiement.class), eq(5L))).thenReturn(saved);

        mockMvcPaiement.perform(post("/paiements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPaiement").value(10L))
                .andExpect(jsonPath("$.idContrat").value(5L));
    }

    @Test
    void testMaintenanceDelete() throws Exception {
        doNothing().when(maintenanceService).delete(7L);

        mockMvcMaintenance.perform(delete("/maintenances/7"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEquipementAffecterVehicule() throws Exception {
        doNothing().when(equipementService).affecterVehicule(1L, 2L);

        mockMvcEquipement.perform(put("/equipements/1/vehicules/2"))
                .andExpect(status().isNoContent());
    }
}

