package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.web.dto.PaiementDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaiementMapper {

    @Mapping(source = "contrat.idContrat", target = "idContrat")
    PaiementDTO toDto(Paiement paiement);

    List<PaiementDTO> toDtoList(List<Paiement> paiements);

    @Mapping(target = "contrat", ignore = true)
    Paiement toEntity(PaiementDTO dto);
}

