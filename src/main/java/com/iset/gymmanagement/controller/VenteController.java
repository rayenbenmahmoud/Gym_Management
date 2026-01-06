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

    // 🛒 Créer une vente (ADMIN + EMPLOYEE)
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

    // 📊 Historique des ventes (ADMIN + EMPLOYEE)
    @GetMapping
    public List<Vente> getAll(HttpSession session) {

        AuthUtil.checkLogin(session);

        return venteService.getAllVentes();
    }
}
