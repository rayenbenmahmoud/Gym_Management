package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.dto.AdherentDTO;
import com.iset.gymmanagement.entity.Adherent;
import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.mapper.AdherentMapper;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.AdherentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/adherents")
@CrossOrigin("*")
public class AdherentController {

    private final AdherentService adherentService;
    private final AdherentMapper adherentMapper;

    public AdherentController(
            AdherentService adherentService,
            AdherentMapper adherentMapper) {
        this.adherentService = adherentService;
        this.adherentMapper = adherentMapper;
    }

    /**
     * Cette méthode permet de créer un nouvel adhérent.
     * L'accès est réservé aux utilisateurs ayant le rôle ADMIN.
     *
     * @param dto les informations de l'adhérent à créer
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return l'adhérent créé
     */
    @PostMapping
    public AdherentDTO create(
            @Valid @RequestBody AdherentDTO dto,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        Adherent entity = adherentMapper.toEntity(dto);
        Adherent saved = adherentService.addAdherent(entity);

        return adherentMapper.toDTO(saved);
    }

    /**
     * Cette méthode retourne la liste de tous les adhérents.
     * L'utilisateur doit être authentifié.
     *
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return la liste des adhérents
     */
    @GetMapping
    public List<AdherentDTO> getAll(HttpSession session) {

        AuthUtil.checkLogin(session);

        return adherentService.getAllAdherents()
                .stream()
                .map(adherentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cette méthode permet de récupérer les informations d'un adhérent
     * à partir de son identifiant.
     * L'utilisateur doit être authentifié.
     *
     * @param id l'identifiant de l'adhérent
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return l'adhérent correspondant à l'identifiant
     */
    @GetMapping("/{id}")
    public AdherentDTO getById(
            @PathVariable Long id,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        return adherentMapper.toDTO(
                adherentService.getAdherentById(id)
        );
    }

    /**
     * Cette méthode permet de mettre à jour les informations
     * d'un adhérent existant.
     * L'accès est réservé aux utilisateurs ayant le rôle ADMIN.
     *
     * @param id l'identifiant de l'adhérent à modifier
     * @param dto les nouvelles informations de l'adhérent
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return l'adhérent mis à jour
     */
    @PutMapping("/{id}")
    public AdherentDTO update(
            @PathVariable Long id,
            @Valid @RequestBody AdherentDTO dto,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        Adherent entity = adherentMapper.toEntity(dto);
        return adherentMapper.toDTO(
                adherentService.updateAdherent(id, entity)
        );
    }

    /**
     * Cette méthode permet de supprimer un adhérent
     * à partir de son identifiant.
     * L'accès est réservé aux utilisateurs ayant le rôle ADMIN.
     *
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
