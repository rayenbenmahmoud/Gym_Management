package com.iset.gymmanagement.service;

import com.iset.gymmanagement.dto.RechargeDTO;
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

    /**
     * Cette méthode permet de recharger la carte d'un adhérent
     * avec un montant donné. Elle met à jour le solde de la carte
     * puis enregistre l'opération de recharge dans l'historique.
     * @param adherentId l'identifiant de l'adhérent dont la carte sera rechargée
     * @param montant le montant à ajouter au solde de la carte
     * @return la carte mise à jour après la recharge
     */
    public Card rechargeCard(Long adherentId, BigDecimal montant) {
        Adherent adherent = adherentRepository.findById(adherentId)
                .orElseThrow(() -> new ResourceNotFoundException("Adhérent avec l'id " + adherentId + " n'existe pas."));

        Card card = cardRepository.findByAdherent(adherent)
                .orElseThrow(() -> new ResourceNotFoundException("Carte de l'adhérent introuvable."));

        card.setSolde(card.getSolde().add(montant));
        cardRepository.save(card);

        Recharge recharge = new Recharge();
        recharge.setAdherent(adherent);
        recharge.setAmount(montant);
        recharge.setDate(LocalDateTime.now());
        rechargeRepository.save(recharge);

        return card;
    }

    /**
     * Cette méthode retourne le solde actuel d'une carte
     * en utilisant son identifiant. Si la carte n'existe pas,
     * une exception est levée.
     * @param cardId l'identifiant de la carte
     * @return le solde de la carte
     */
    public BigDecimal getSolde(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Carte avec l'id " + cardId + " n'existe pas."));
        return card.getSolde();
    }

    /**
     * Cette méthode récupère la liste de toutes les recharges
     * effectuées par un adhérent donné. Si l'adhérent n'existe pas,
     * une exception est levée.
     * @param adherentId l'identifiant de l'adhérent
     * @return la liste des recharges associées à cet adhérent
     */
    public List<RechargeDTO> getRechargesByAdherent(Long adherentId) {
        return rechargeRepository.findHistoryByAdherentId(adherentId);
    }


}
