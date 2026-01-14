package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.dto.RechargeDTO;
import com.iset.gymmanagement.entity.Recharge;
import com.iset.gymmanagement.entity.Vente;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.CardService;
import com.iset.gymmanagement.service.VenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
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

    /**
     * Cette méthode permet de récupérer l'historique de toutes les ventes
     * enregistrées dans le système. L'utilisateur doit être authentifié.
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return la liste des ventes
     */
    @GetMapping("/ventes")
    public List<Vente> ventes(HttpSession session) {

        AuthUtil.checkLogin(session);

        return venteService.getAllVentes();
    }

    /**
     * Cette méthode permet de récupérer l'historique des recharges
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
