package com.iset.gymmanagement.mapper;

import com.iset.gymmanagement.dto.*;
import com.iset.gymmanagement.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VenteMapper {

    @Mapping(source = "adherent.id", target = "adherentId")
    @Mapping(source = "adherent.nom", target = "adherentNom")
    @Mapping(source = "adherent.prenom", target = "adherentPrenom")
    VenteResponseDTO toDto(Vente vente);

    List<VenteResponseDTO> toDtoList(List<Vente> ventes);

    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "produit.nom", target = "produitNom")
    VenteProduitResponseDTO toProduitDto(VenteProduit venteProduit);
}
