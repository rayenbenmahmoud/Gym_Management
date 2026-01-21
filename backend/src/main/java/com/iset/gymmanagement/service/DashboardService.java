package com.iset.gymmanagement.service;

import com.iset.gymmanagement.dto.TopProduitDTO;
import com.iset.gymmanagement.repository.VenteProduitRepository;
import com.iset.gymmanagement.repository.VenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final VenteRepository venteRepository;
    private final VenteProduitRepository venteProduitRepository;

    public DashboardService(VenteRepository venteRepository,
                            VenteProduitRepository venteProduitRepository) {
        this.venteRepository = venteRepository;
        this.venteProduitRepository = venteProduitRepository;
    }

    /**
     * Cette méthode retourne le nombre total de ventes
     * enregistrées dans la base de données.
     * @return le nombre total de ventes
     */
    public long totalVentes() {
        return venteRepository.count();
    }

    /**
     * Cette méthode retourne la liste des produits les plus vendus
     * en se basant sur les ventes enregistrées. Elle limite le résultat
     * aux cinq premiers produits.
     * @return la liste des cinq produits les plus vendus
     */
    public List<TopProduitDTO> topProduits() {
        return venteProduitRepository.findTopProduits()
                .stream()
                .limit(5)
                .toList();
    }
}
