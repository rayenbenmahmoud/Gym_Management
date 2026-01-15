package com.iset.gymmanagement.service;

import com.iset.gymmanagement.dto.RechargeDTO;
import com.iset.gymmanagement.entity.*;
import com.iset.gymmanagement.exception.ResourceNotFoundException;
import com.iset.gymmanagement.mapper.RechargeMapper;
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
    private final RechargeMapper rechargeMapper;

    public CardService(
            CardRepository cardRepository,
            AdherentRepository adherentRepository,
            RechargeRepository rechargeRepository,
            RechargeMapper rechargeMapper) {

        this.cardRepository = cardRepository;
        this.adherentRepository = adherentRepository;
        this.rechargeRepository = rechargeRepository;
        this.rechargeMapper = rechargeMapper;
    }

    /**
     * Cette méthode permet de recharger la carte d'un adhérent donné.
     * Elle effectue les opérations suivantes :
     * <ul>
     *     <li>Vérifie l'existence de l'adhérent</li>
     *     <li>Récupère la carte associée à cet adhérent</li>
     *     <li>Ajoute le montant donné au solde actuel de la carte</li>
     *     <li>Enregistre l'opération de recharge dans l'historique</li>
     * </ul>
     *
     * Si l'adhérent ou sa carte n'existent pas, une exception est levée.
     *
     * @param adherentId l'identifiant de l'adhérent dont la carte sera rechargée
     * @param montant le montant à ajouter au solde de la carte
     * @return la carte mise à jour après la recharge
     */
    public Card rechargeCard(Long adherentId, BigDecimal montant) {

        Adherent adherent = adherentRepository.findById(adherentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Adhérent avec l'id " + adherentId + " n'existe pas."
                        )
                );

        Card card = cardRepository.findByAdherent(adherent)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Carte de l'adhérent introuvable."
                        )
                );

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
     * Cette méthode permet de récupérer le solde actuel
     * de la carte associée à un adhérent donné.
     *
     * Elle vérifie d'abord l'existence de l'adhérent,
     * puis récupère la carte correspondante.
     *
     * @param adherentId l'identifiant de l'adhérent
     * @return le solde actuel de la carte
     */
    public BigDecimal getSolde(Long adherentId) {

        Adherent adherent = adherentRepository.findById(adherentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Adhérent introuvable."
                        )
                );

        Card card = cardRepository.findByAdherent(adherent)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Carte introuvable."
                        )
                );

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
        if (adherentRepository.existsPhysically(adherentId) == 0) {
            throw new ResourceNotFoundException(
                    "Adhérent avec l'id " + adherentId + " n'existe pas."
            );
        }

        return rechargeRepository.findHistoryByAdherentId(adherentId);
    }
}
