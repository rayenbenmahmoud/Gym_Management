package com.iset.gymmanagement.dto;

import java.util.List;

public class VenteRequest {

    private Long adherentId;
    private List<VenteProduitRequest> produits;

    /**
     * Constructeur par défaut utilisé pour
     * la conversion automatique des requêtes JSON.
     */
    public VenteRequest() {}

    /**
     * Retourne l'identifiant de l'adhérent
     * qui effectue la vente.
     *
     * @return identifiant de l'adhérent
     */
    public Long getAdherentId() {
        return adherentId;
    }

    /**
     * Définit l'identifiant de l'adhérent
     * qui effectue la vente.
     *
     * @param adherentId identifiant de l'adhérent
     */
    public void setAdherentId(Long adherentId) {
        this.adherentId = adherentId;
    }

    /**
     * Retourne la liste des produits
     * inclus dans la vente.
     *
     * @return liste des produits vendus
     */
    public List<VenteProduitRequest> getProduits() {
        return produits;
    }

    /**
     * Définit la liste des produits
     * inclus dans la vente.
     *
     * @param produits liste des produits vendus
     */
    public void setProduits(List<VenteProduitRequest> produits) {
        this.produits = produits;
    }
}
