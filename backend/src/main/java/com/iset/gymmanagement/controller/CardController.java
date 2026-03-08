package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.dto.RechargeDTO;
import com.iset.gymmanagement.entity.Recharge;
import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.CardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * Cette méthode permet de récupérer le solde actuel
     * de la carte d'un adhérent. L'utilisateur doit être authentifié.
     * @param adherentId l'identifiant de l'adhérent
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return le solde actuel de la carte
     */
    @GetMapping("/solde/{adherentId}")
    public BigDecimal getSolde(
            @PathVariable Long adherentId,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        return cardService.getSolde(adherentId);
    }

    /**
     * Cette méthode permet de recharger la carte d'un adhérent
     * avec un montant donné. L'accès est réservé aux utilisateurs
     * ayant le rôle ADMIN.
     * @param adherentId l'identifiant de l'adhérent
     * @param montant le montant à ajouter au solde de la carte
     * @param session la session HTTP utilisée pour vérifier l'authentification
     */
    @PostMapping("/recharge/{adherentId}")
    public void recharge(
            @PathVariable Long adherentId,
            @Valid @RequestParam BigDecimal montant,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        cardService.rechargeCard(adherentId, montant);
    }

    /**
     * Cette méthode retourne l'historique des recharges
     * effectuées par un adhérent donné. L'utilisateur doit être authentifié.
     * @param adherentId l'identifiant de l'adhérent
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return la liste des recharges de l'adhérent
     */
    @GetMapping("/recharges/{adherentId}")
    public ResponseEntity<List<RechargeDTO>> getRecharges(
            @PathVariable Long adherentId,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        List<RechargeDTO> recharges = cardService.getRechargesByAdherent(adherentId);
        return ResponseEntity.ok(recharges);
    }

}
