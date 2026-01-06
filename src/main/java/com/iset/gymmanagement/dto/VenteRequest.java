package com.iset.gymmanagement.dto;

import java.util.List;

public class VenteRequest {

    private Long adherentId;
    private List<VenteProduitRequest> produits;

    public VenteRequest() {}

    public Long getAdherentId() {
        return adherentId;
    }

    public void setAdherentId(Long adherentId) {
        this.adherentId = adherentId;
    }

    public List<VenteProduitRequest> getProduits() {
        return produits;
    }

    public void setProduits(List<VenteProduitRequest> produits) {
        this.produits = produits;
    }
}
