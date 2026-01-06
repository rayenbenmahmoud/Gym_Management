package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.entity.Adherent;
import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.AdherentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;  // ✅ مهم جدًا
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adherents")
@CrossOrigin("*")
public class AdherentController {

    private final AdherentService adherentService;

    public AdherentController(AdherentService adherentService) {
        this.adherentService = adherentService;
    }

    // ➕ Ajouter adhérent (ADMIN فقط)
    @PostMapping
    public Adherent create(
            @Valid @RequestBody Adherent adherent,  // ✅ @Valid هنا
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        return adherentService.addAdherent(adherent);
    }

    // 📋 Liste des adhérents (ADMIN + EMPLOYEE)
    @GetMapping
    public List<Adherent> getAll(HttpSession session) {

        AuthUtil.checkLogin(session);

        return adherentService.getAllAdherents();
    }

    // 🔍 Détail adhérent (ADMIN + EMPLOYEE)
    @GetMapping("/{id}")
    public Adherent getById(
            @PathVariable Long id,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        return adherentService.getAdherentById(id);
    }

    // ✏️ Modifier adhérent (ADMIN فقط)
    @PutMapping("/{id}")
    public Adherent update(
            @PathVariable Long id,
            @Valid @RequestBody Adherent adherent,  // ✅ @Valid هنا أيضًا
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        return adherentService.updateAdherent(id, adherent);
    }

    // 🗑️ Supprimer adhérent (ADMIN فقط)
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        adherentService.deleteAdherent(id);
    }
}
