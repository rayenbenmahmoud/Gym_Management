package com.iset.gymmanagement.security;

import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.entity.Role;
import jakarta.servlet.http.HttpSession;

import com.iset.gymmanagement.exception.UnauthorizedException;
import com.iset.gymmanagement.exception.ForbiddenException;

public class AuthUtil {

    /**
     * Vérifie si un utilisateur est connecté en
     * consultant la session HTTP.
     *
     * @param session session HTTP courante
     * @return utilisateur authentifié
     * @throws UnauthorizedException si aucun utilisateur n'est connecté
     */
    public static User checkLogin(HttpSession session) {
        Object obj = session.getAttribute("USER");
        if (obj == null) {
            throw new UnauthorizedException("Utilisateur non authentifié");
        }
        return (User) obj;
    }

    /**
     * Vérifie si l'utilisateur possède le rôle ADMIN.
     *
     * @param user utilisateur connecté
     * @throws ForbiddenException si l'utilisateur n'a pas les droits administrateur
     */
    public static void checkAdmin(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new ForbiddenException("Accès réservé à l'administrateur");
        }
    }
}
