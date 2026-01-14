package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.DashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private static final String TOTAL_VENTES = "totalVentes";
    private static final String TOP_PRODUITS = "topProduits";

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * Cette méthode permet de récupérer les informations du tableau de bord.
     * Elle retourne le nombre total de ventes ainsi que la liste des produits
     * les plus vendus. L'accès est réservé aux utilisateurs ayant le rôle ADMIN.
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return une map contenant les données du tableau de bord
     */
    @GetMapping
    public Map<String, Object> getDashboard(HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        Map<String, Object> response = new HashMap<>();

        response.put(TOTAL_VENTES, dashboardService.totalVentes());
        response.put(TOP_PRODUITS, dashboardService.topProduits());

        return response;
    }
}
