package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.dto.DashboardDTO;
import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.DashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * Cette méthode permet de récupérer les informations du tableau de bord.
     * Elle retourne le nombre total de ventes ainsi que la liste des produits
     * les plus vendus. L'accès est réservé aux utilisateurs ayant le rôle ADMIN.
     *
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return les données du tableau de bord sous forme de DTO
     */
    @GetMapping
    public DashboardDTO getDashboard(HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        return new DashboardDTO(
                dashboardService.totalVentes(),
                dashboardService.topProduits()
        );
    }
}
