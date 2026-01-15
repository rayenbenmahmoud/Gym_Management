package com.iset.gymmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VenteProduitResponseDTO {

    private Long produitId;
    private String produitNom;
    private Integer quantite;
    private BigDecimal prixUnitaire;
}
