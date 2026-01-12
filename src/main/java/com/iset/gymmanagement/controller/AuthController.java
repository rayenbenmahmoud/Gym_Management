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

    // 🔐 LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User loginRequest, HttpSession session) {

        User user = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Identifiants invalides"));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new UnauthorizedException("Identifiants invalides");
        }

        // ✅ Sauvegarder l'utilisateur dans la session
        session.setAttribute("USER", user);

        return "Connexion réussie";
    }

    // 🔓 LOGOUT
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Déconnexion réussie";
    }
}
