package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.dto.*;
import com.iset.gymmanagement.entity.Vente;
import com.iset.gymmanagement.mapper.VenteMapper;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.VenteService;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventes")
public class VenteController {

    private final VenteService venteService;
    private final VenteMapper venteMapper;

    public VenteController(VenteService venteService, VenteMapper venteMapper) {
        this.venteService = venteService;
        this.venteMapper = venteMapper;
    }

    /**
     * Cette méthode permet d'enregistrer une nouvelle vente.
     *
     * @param request les informations de la vente (adhérent et produits)
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return la vente créée
     */
    @PostMapping
    public VenteResponseDTO create(
            @Valid @RequestBody VenteRequest request,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        Vente vente = venteService.createVente(
                request.getAdherentId(),
                request.getProduits()
        );

        return venteMapper.toDto(vente);
    }

    /**
     * Cette méthode permet de récupérer la liste de toutes les ventes enregistrées.
     *
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return la liste des ventes
     */
    @GetMapping
    public List<VenteResponseDTO> getAll(HttpSession session) {

        AuthUtil.checkLogin(session);

        return venteMapper.toDtoList(
                venteService.getAllVentes()
        );
    }
}
