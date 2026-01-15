package com.iset.gymmanagement.dto;

import java.util.List;

/**
 * DTO représentant les données du tableau de bord.
 * Il contient le nombre total de ventes ainsi que
 * la liste des produits les plus vendus.
 */
public record DashboardDTO(
        long totalVentes,
        List<TopProduitDTO> topProduits
) {}
