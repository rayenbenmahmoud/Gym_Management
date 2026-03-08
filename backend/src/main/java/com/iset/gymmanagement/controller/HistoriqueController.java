package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.dto.RechargeDTO;
import com.iset.gymmanagement.dto.VenteResponseDTO;
import com.iset.gymmanagement.entity.Vente;
import com.iset.gymmanagement.mapper.VenteMapper;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.CardService;
import com.iset.gymmanagement.service.VenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historique")
public class HistoriqueController {

    private final VenteService venteService;
    private final CardService cardService;
    private final VenteMapper venteMapper;

    public HistoriqueController(VenteService venteService,
                                CardService cardService,
                                VenteMapper venteMapper) {
        this.venteService = venteService;
        this.cardService = cardService;
        this.venteMapper = venteMapper;
    }

    /**
     * Cette méthode permet de récupérer l'historique de toutes les ventes.
     *
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return la liste des ventes sous forme de DTO
     */
    @GetMapping("/ventes")
    public List<VenteResponseDTO> ventes(HttpSession session) {

        AuthUtil.checkLogin(session);

        return venteMapper.toDtoList(venteService.getAllVentes());
    }

    /**
     * Cette méthode permet de récupérer l'historique des recharges
     * effectuées par un adhérent donné.
     *
     * @param adherentId l'identifiant de l'adhérent
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return la liste des recharges sous forme de DTO
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
