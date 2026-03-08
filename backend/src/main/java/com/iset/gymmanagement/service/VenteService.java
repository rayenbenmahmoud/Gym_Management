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

    /**
     * Cette méthode permet de créer une nouvelle vente pour un adhérent donné.
     * Elle vérifie la disponibilité du stock pour chaque produit,
     * calcule le montant total de la vente, vérifie le solde de la carte,
     * met à jour le stock des produits ainsi que le solde de la carte,
     * puis enregistre la vente et ses produits associés.
     * @param adherentId l'identifiant de l'adhérent
     * @param produitsRequest la liste des produits vendus avec leurs quantités
     * @return la vente enregistrée
     */
    public Vente createVente(Long adherentId, List<VenteProduitRequest> produitsRequest) {

        Adherent adherent = adherentRepository.findById(adherentId)
                .orElseThrow(() -> new ResourceNotFoundException("Adhérent avec l'id " + adherentId + " n'existe pas."));

        Card card = cardRepository.findByAdherent(adherent)
                .orElseThrow(() -> new ResourceNotFoundException("Carte de l'adhérent introuvable."));

        Vente vente = new Vente();
        vente.setAdherent(adherent);
        vente.setDate(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        List<VenteProduit> produitsFinal = new ArrayList<>();

        for (VenteProduitRequest vpRequest : produitsRequest) {

            Product product = productRepository.findById(vpRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit avec l'id " + vpRequest.getProductId() + " n'existe pas."));

            if (product.getQuantiteStock() < vpRequest.getQuantite()) {
                throw new StockUnavailableException("Stock insuffisant pour le produit: " + product.getNom());
            }

            BigDecimal lineTotal = product.getPrix().multiply(BigDecimal.valueOf(vpRequest.getQuantite()));
            total = total.add(lineTotal);

            product.setQuantiteStock(product.getQuantiteStock() - vpRequest.getQuantite());
            productRepository.save(product);

            VenteProduit vp = new VenteProduit();
            vp.setProduit(product);
            vp.setQuantite(vpRequest.getQuantite());
            vp.setPrixUnitaire(product.getPrix());
            vp.setVente(vente);

            produitsFinal.add(vp);
        }

        if (card.getSolde().compareTo(total) < 0) {
            throw new InsufficientBalanceException("Solde insuffisant pour effectuer cette vente.");
        }

        card.setSolde(card.getSolde().subtract(total));
        cardRepository.save(card);

        vente.setMontantTotal(total);
        vente.addProduits(produitsFinal);

        return venteRepository.save(vente);
    }

    /**
     * Cette méthode retourne la liste de toutes les ventes
     * enregistrées dans la base de données.
     * @return la liste des ventes
     */
    public List<Vente> getAllVentes() {
        return venteRepository.findAll();
    }
}
