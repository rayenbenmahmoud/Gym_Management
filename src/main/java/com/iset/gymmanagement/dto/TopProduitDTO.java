package com.iset.gymmanagement.dto;

public class TopProduitDTO {

    private String nom;
    private Long quantiteVendue;

    public TopProduitDTO(String nom, Long quantiteVendue) {
        this.nom = nom;
        this.quantiteVendue = quantiteVendue;
    }

    public String getNom() {
        return nom;
    }

    public Long getQuantiteVendue() {
        return quantiteVendue;
    }
}
