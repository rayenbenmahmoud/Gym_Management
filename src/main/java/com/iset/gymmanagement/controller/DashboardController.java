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

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // 📊 Dashboard (ADMIN فقط)
    @GetMapping
    public Map<String, Object> getDashboard(HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        Map<String, Object> response = new HashMap<>();

        response.put("totalVentes", dashboardService.totalVentes());
        response.put("topProduits", dashboardService.topProduits());

        return response;
    }
}
