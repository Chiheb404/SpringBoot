package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.web.dto.VehiculeDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(source = "agence.idAgence", target = "idAgence")
    @Mapping(source = "agence.nom", target = "nomAgence")
    VehiculeDTO toDto(Vehicule vehicule);

    List<VehiculeDTO> toDtoList(List<Vehicule> vehicules);

    // L'agence est rattachée par le service (via idAgence), jamais construite depuis le DTO
    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
    @Mapping(target = "equipements", ignore = true)
    Vehicule toEntity(VehiculeDTO dto);
}
