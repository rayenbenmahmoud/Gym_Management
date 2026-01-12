package com.iset.gymmanagement.security;

import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.entity.Role;
import jakarta.servlet.http.HttpSession;

import com.iset.gymmanagement.exception.UnauthorizedException;
import com.iset.gymmanagement.exception.ForbiddenException;

public class AuthUtil {

    public static User checkLogin(HttpSession session) {
        Object obj = session.getAttribute("USER");
        if (obj == null) {
            throw new UnauthorizedException("Utilisateur non authentifié");
        }
        return (User) obj;
    }

    public static void checkAdmin(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new ForbiddenException("Accès réservé à l'administrateur");
        }
    }
}
