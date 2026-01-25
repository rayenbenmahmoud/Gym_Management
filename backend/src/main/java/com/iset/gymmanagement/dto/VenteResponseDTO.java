package com.iset.gymmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VenteResponseDTO {

    private Long id;
    private LocalDateTime date;
    private BigDecimal montantTotal;

    private Long adherentId;
    private String adherentNom;
    private String adherentPrenom;

    private List<VenteProduitResponseDTO> produits;

}
