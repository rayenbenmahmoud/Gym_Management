package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.entity.Recharge;
import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.CardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin("*")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // 1️⃣ Consultation du solde (ADMIN + EMPLOYEE)
    @GetMapping("/solde/{adherentId}")
    public BigDecimal getSolde(
            @PathVariable Long adherentId,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        return cardService.getSolde(adherentId);
    }

    // 2️⃣ Recharge du solde (ADMIN فقط)
    @PostMapping("/recharge/{adherentId}")
    public void recharge(
            @PathVariable Long adherentId,
            @Valid @RequestParam BigDecimal montant,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        cardService.rechargeCard(adherentId, montant);
    }

    // 3️⃣ Historique des recharges (ADMIN + EMPLOYEE)
    @GetMapping("/recharges/{adherentId}")
    public List<Recharge> getRecharges(
            @PathVariable Long adherentId,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        return cardService.getRechargesByAdherent(adherentId);
    }
}
