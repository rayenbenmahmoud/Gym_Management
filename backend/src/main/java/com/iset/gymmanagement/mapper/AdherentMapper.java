package com.iset.gymmanagement.mapper;

import com.iset.gymmanagement.dto.AdherentDTO;
import com.iset.gymmanagement.entity.Adherent;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AdherentMapper {

    @Mapping(
            target = "cardNumber",
            source = "card.cardNumber"
    )
    AdherentDTO toDTO(Adherent entity);

    Adherent toEntity(AdherentDTO dto);
}
