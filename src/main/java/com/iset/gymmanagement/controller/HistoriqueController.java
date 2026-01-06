package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.entity.Recharge;
import com.iset.gymmanagement.entity.Vente;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.CardService;
import com.iset.gymmanagement.service.VenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historique")
@CrossOrigin("*")
public class HistoriqueController {

    private final VenteService venteService;
    private final CardService cardService;

    public HistoriqueController(VenteService venteService,
                                CardService cardService) {
        this.venteService = venteService;
        this.cardService = cardService;
    }

    // 📊 Historique des ventes (ADMIN + EMPLOYEE)
    @GetMapping("/ventes")
    public List<Vente> ventes(HttpSession session) {

        AuthUtil.checkLogin(session);

        return venteService.getAllVentes();
    }

    // 💳 Historique des recharges (ADMIN + EMPLOYEE)
    @GetMapping("/recharges/{adherentId}")
    public List<Recharge> recharges(
            @PathVariable Long adherentId,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        return cardService.getRechargesByAdherent(adherentId);
    }
}
