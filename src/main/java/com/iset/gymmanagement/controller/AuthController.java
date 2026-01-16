package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.exception.UnauthorizedException;
import com.iset.gymmanagement.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Cette méthode permet à un utilisateur de se connecter
     * en vérifiant son nom d'utilisateur et son mot de passe.
     * Si les informations sont incorrectes, une exception est levée.
     * En cas de succès, l'utilisateur est enregistré dans la session HTTP.
     * @param loginRequest les informations de connexion (username et password)
     * @param session la session HTTP utilisée pour stocker l'utilisateur connecté
     * @return un message indiquant le succès de la connexion
     */
    @PostMapping("/login")
    public String login(@RequestBody User loginRequest, HttpSession session) {

        User user = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Identifiants invalides"));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new UnauthorizedException("Identifiants invalides");
        }

        session.setAttribute("USER", user);

        return "Connexion réussie";
    }

    /**
     * Cette méthode permet de déconnecter l'utilisateur actuellement connecté
     * en invalidant la session HTTP.
     * @param session la session HTTP à invalider
     * @return un message indiquant le succès de la déconnexion
     */
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Déconnexion réussie";
    }
}
