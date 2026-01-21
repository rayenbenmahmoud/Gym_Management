package com.iset.gymmanagement.dto;

public class VenteProduitRequest {

    private Long productId;
    private Integer quantite;

    /**
     * Constructeur par défaut utilisé lors
     * de la désérialisation des requêtes JSON.
     */
    public VenteProduitRequest() {}

    /**
     * Retourne l'identifiant du produit concerné par la vente.
     *
     * @return identifiant du produit
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Définit l'identifiant du produit concerné par la vente.
     *
     * @param productId identifiant du produit
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * Retourne la quantité demandée pour ce produit.
     *
     * @return quantité du produit
     */
    public Integer getQuantite() {
        return quantite;
    }

    /**
     * Définit la quantité demandée pour ce produit.
     *
     * @param quantite quantité du produit
     */
    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
