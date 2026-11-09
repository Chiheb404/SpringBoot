package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.web.dto.ContratDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContratMapper {

    @Mapping(source = "reservation.idReservation", target = "idReservation")
    @Mapping(source = "paiements", target = "idPaiements")
    ContratDTO toDto(Contrat contrat);

    List<ContratDTO> toDtoList(List<Contrat> contrats);

    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paiements", ignore = true)
    Contrat toEntity(ContratDTO dto);

    default Long mapPaiementToId(Paiement paiement) {
        return paiement != null ? paiement.getIdPaiement() : null;
    }
}

