package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.entity.Adherent;
import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.AdherentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

    /**
     * Cette méthode permet de créer un nouvel adhérent.
     * L'accès est réservé aux utilisateurs ayant le rôle ADMIN.
     * @param adherent les informations de l'adhérent à créer
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return l'adhérent créé
     */
    @PostMapping
    public Adherent create(
            @Valid @RequestBody Adherent adherent,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        return adherentService.addAdherent(adherent);
    }

    /**
     * Cette méthode retourne la liste de tous les adhérents.
     * L'utilisateur doit être authentifié.
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return la liste des adhérents
     */
    @GetMapping
    public List<Adherent> getAll(HttpSession session) {

        AuthUtil.checkLogin(session);

        return adherentService.getAllAdherents();
    }

    /**
     * Cette méthode permet de récupérer les informations d'un adhérent
     * à partir de son identifiant. L'utilisateur doit être authentifié.
     * @param id l'identifiant de l'adhérent
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return l'adhérent correspondant à l'identifiant
     */
    @GetMapping("/{id}")
    public Adherent getById(
            @PathVariable Long id,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        return adherentService.getAdherentById(id);
    }

    /**
     * Cette méthode permet de mettre à jour les informations d'un adhérent existant.
     * L'accès est réservé aux utilisateurs ayant le rôle ADMIN.
     * @param id l'identifiant de l'adhérent à modifier
     * @param adherent les nouvelles informations de l'adhérent
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return l'adhérent mis à jour
     */
    @PutMapping("/{id}")
    public Adherent update(
            @PathVariable Long id,
            @Valid @RequestBody Adherent adherent,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        return adherentService.updateAdherent(id, adherent);
    }

    /**
     * Cette méthode permet de supprimer un adhérent à partir de son identifiant.
     * L'accès est réservé aux utilisateurs ayant le rôle ADMIN.
     * @param id l'identifiant de l'adhérent à supprimer
     * @param session la session HTTP utilisée pour vérifier l'authentification
     */
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        adherentService.deleteAdherent(id);
    }
}
