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

    // ✅ عدد عمليات البيع
    public long totalVentes() {
        return venteRepository.count();
    }

    // ✅ Top 5 produits
    public List<TopProduitDTO> topProduits() {
        return venteProduitRepository.findTopProduits()
                .stream()
                .limit(5)
                .toList();
    }
}
