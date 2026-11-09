package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.web.dto.EmployeDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeMapper {

    @Mapping(source = "agence.idAgence", target = "idAgence")
    EmployeDTO toDto(Employe employe);

    List<EmployeDTO> toDtoList(List<Employe> employes);

    @Mapping(target = "agence", ignore = true)
    Employe toEntity(EmployeDTO dto);
}

