package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.web.dto.EquipementDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipementMapper {

    EquipementDTO toDto(Equipement equipement);

    List<EquipementDTO> toDtoList(List<Equipement> equipements);

    @Mapping(target = "vehicules", ignore = true)
    Equipement toEntity(EquipementDTO dto);
}

