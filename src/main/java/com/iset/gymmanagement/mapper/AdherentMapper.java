package com.iset.gymmanagement.mapper;

import com.iset.gymmanagement.dto.AdherentDTO;
import com.iset.gymmanagement.entity.Adherent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdherentMapper {

    Adherent toEntity(AdherentDTO dto);

    AdherentDTO toDTO(Adherent entity);
}
