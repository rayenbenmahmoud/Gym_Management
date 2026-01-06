package com.iset.gymmanagement.service;

import com.iset.gymmanagement.entity.*;
import com.iset.gymmanagement.exception.ResourceNotFoundException;
import com.iset.gymmanagement.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CardService {

    private final CardRepository cardRepository;
    private final AdherentRepository adherentRepository;
    private final RechargeRepository rechargeRepository;

    public CardService(CardRepository cardRepository,
                       AdherentRepository adherentRepository,
                       RechargeRepository rechargeRepository) {
        this.cardRepository = cardRepository;
        this.adherentRepository = adherentRepository;
        this.rechargeRepository = rechargeRepository;
    }

    // 1️⃣ شحن الرصيد
    public Card rechargeCard(Long adherentId, BigDecimal montant) {
        Adherent adherent = adherentRepository.findById(adherentId)
                .orElseThrow(() -> new ResourceNotFoundException("Adhérent avec l'id " + adherentId + " n'existe pas."));

        Card card = cardRepository.findByAdherent(adherent)
                .orElseThrow(() -> new ResourceNotFoundException("Carte de l'adhérent introuvable."));

        // زيادة الرصيد
        card.setSolde(card.getSolde().add(montant));
        cardRepository.save(card);

        // تسجيل العملية في historique
        Recharge recharge = new Recharge();
        recharge.setAdherent(adherent);
        recharge.setAmount(montant);
        recharge.setDate(LocalDateTime.now());
        rechargeRepository.save(recharge);

        return card;
    }

    // 2️⃣ عرض الرصيد
    public BigDecimal getSolde(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Carte avec l'id " + cardId + " n'existe pas."));
        return card.getSolde();
    }

    // 3️⃣ عرض كل عمليات الشحن لمشترك
    public List<Recharge> getRechargesByAdherent(Long adherentId) {
        Adherent adherent = adherentRepository.findById(adherentId)
                .orElseThrow(() -> new ResourceNotFoundException("Adhérent avec l'id " + adherentId + " n'existe pas."));
        return rechargeRepository.findByAdherent(adherent);
    }

}
