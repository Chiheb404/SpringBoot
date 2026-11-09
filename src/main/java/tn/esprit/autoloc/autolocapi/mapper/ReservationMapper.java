package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.web.dto.ReservationDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "client.idClient", target = "idClient")
    @Mapping(source = "vehicule.idVehicule", target = "idVehicule")
    @Mapping(source = "contrat.idContrat", target = "idContrat")
    ReservationDTO toDto(Reservation reservation);

    List<ReservationDTO> toDtoList(List<Reservation> reservations);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "contrat", ignore = true)
    Reservation toEntity(ReservationDTO dto);
}

