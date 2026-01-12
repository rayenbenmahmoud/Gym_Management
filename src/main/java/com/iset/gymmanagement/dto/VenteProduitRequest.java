package com.iset.gymmanagement.dto;

import java.math.BigDecimal;

public class VenteProduitRequest {

    private Long productId;
    private Integer quantite;

    public VenteProduitRequest() {}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
