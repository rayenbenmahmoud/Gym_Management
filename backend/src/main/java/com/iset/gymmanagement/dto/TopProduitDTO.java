package com.iset.gymmanagement.dto;

public class TopProduitDTO {

    private String nom;
    private Long quantiteVendue;

    /**
     * Construit un objet TopProduitDTO avec le nom du produit
     * et la quantité totale vendue.
     *
     * @param nom nom du produit
     * @param quantiteVendue quantité totale vendue
     */
    public TopProduitDTO(String nom, Long quantiteVendue) {
        this.nom = nom;
        this.quantiteVendue = quantiteVendue;
    }

    /**
     * Retourne le nom du produit.
     *
     * @return nom du produit
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la quantité totale vendue du produit.
     *
     * @return quantité vendue
     */
    public Long getQuantiteVendue() {
        return quantiteVendue;
    }
}
