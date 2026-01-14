package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.entity.Vente;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.VenteService;
import com.iset.gymmanagement.dto.VenteRequest;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventes")
@CrossOrigin("*")
public class VenteController {

    private final VenteService venteService;

    public VenteController(VenteService venteService) {
        this.venteService = venteService;
    }

    /**
     * Cette méthode permet d'enregistrer une nouvelle vente pour un adhérent.
     * Elle vérifie le stock des produits et le solde de la carte avant validation.
     * L'utilisateur doit être authentifié.
     * @param request les informations de la vente (adhérent et produits)
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return la vente créée
     */
    @PostMapping
    public Vente create(
            @Valid @RequestBody VenteRequest request,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        return venteService.createVente(
                request.getAdherentId(),
                request.getProduits()
        );
    }

    /**
     * Cette méthode permet de récupérer la liste de toutes les ventes enregistrées.
     * L'utilisateur doit être authentifié.
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return la liste des ventes
     */
    @GetMapping
    public List<Vente> getAll(HttpSession session) {

        AuthUtil.checkLogin(session);

        return venteService.getAllVentes();
    }
}
