package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.web.dto.AgenceDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgenceMapper {

    AgenceDTO toDto(Agence agence);

    List<AgenceDTO> toDtoList(List<Agence> agences);

    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "employes", ignore = true)
    Agence toEntity(AgenceDTO dto);
}
