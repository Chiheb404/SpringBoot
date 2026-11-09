package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.web.dto.MaintenanceDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    @Mapping(source = "vehicule.idVehicule", target = "idVehicule")
    MaintenanceDTO toDto(Maintenance maintenance);

    List<MaintenanceDTO> toDtoList(List<Maintenance> maintenances);

    @Mapping(target = "vehicule", ignore = true)
    Maintenance toEntity(MaintenanceDTO dto);
}

