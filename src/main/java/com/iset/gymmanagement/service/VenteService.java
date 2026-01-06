package com.iset.gymmanagement.service;

import com.iset.gymmanagement.dto.VenteProduitRequest;
import com.iset.gymmanagement.entity.*;
import com.iset.gymmanagement.exception.ResourceNotFoundException;
import com.iset.gymmanagement.exception.StockUnavailableException;
import com.iset.gymmanagement.exception.InsufficientBalanceException;
import com.iset.gymmanagement.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VenteService {

    private final VenteRepository venteRepository;
    private final AdherentRepository adherentRepository;
    private final ProductRepository productRepository;
    private final CardRepository cardRepository;

    public VenteService(
            VenteRepository venteRepository,
            AdherentRepository adherentRepository,
            ProductRepository productRepository,
            CardRepository cardRepository
    ) {
        this.venteRepository = venteRepository;
        this.adherentRepository = adherentRepository;
        this.productRepository = productRepository;
        this.cardRepository = cardRepository;
    }

    public Vente createVente(Long adherentId, List<VenteProduitRequest> produitsRequest) {

        // 1️⃣ جلب المشترك
        Adherent adherent = adherentRepository.findById(adherentId)
                .orElseThrow(() -> new ResourceNotFoundException("Adhérent avec l'id " + adherentId + " n'existe pas."));

        // 2️⃣ جلب البطاقة
        Card card = cardRepository.findByAdherent(adherent)
                .orElseThrow(() -> new ResourceNotFoundException("Carte de l'adhérent introuvable."));

        // 3️⃣ إنشاء vente جديدة
        Vente vente = new Vente();
        vente.setAdherent(adherent);
        vente.setDate(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        List<VenteProduit> produitsFinal = new ArrayList<>();

        for (VenteProduitRequest vpRequest : produitsRequest) {

            Product product = productRepository.findById(vpRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit avec l'id " + vpRequest.getProductId() + " n'existe pas."));

            // التحقق من stock
            if (product.getQuantiteStock() < vpRequest.getQuantite()) {
                throw new StockUnavailableException("Stock insuffisant pour le produit: " + product.getNom());
            }

            // حساب المبلغ لكل منتج
            BigDecimal lineTotal = product.getPrix().multiply(BigDecimal.valueOf(vpRequest.getQuantite()));
            total = total.add(lineTotal);

            // إنقاص stock
            product.setQuantiteStock(product.getQuantiteStock() - vpRequest.getQuantite());
            productRepository.save(product);

            // إنشاء VenteProduit
            VenteProduit vp = new VenteProduit();
            vp.setProduit(product);
            vp.setQuantite(vpRequest.getQuantite());
            vp.setPrixUnitaire(product.getPrix());
            vp.setVente(vente);

            produitsFinal.add(vp);
        }

        // التحقق من solde
        if (card.getSolde().compareTo(total) < 0) {
            throw new InsufficientBalanceException("Solde insuffisant pour effectuer cette vente.");
        }

        // خصم الرصيد
        card.setSolde(card.getSolde().subtract(total));
        cardRepository.save(card);

        // تسجيل vente
        vente.setMontantTotal(total);
        vente.setProduits(produitsFinal);

        return venteRepository.save(vente);
    }

    public List<Vente> getAllVentes() {
        return venteRepository.findAll();
    }
}
