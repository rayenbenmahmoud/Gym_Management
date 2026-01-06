package com.iset.gymmanagement.security;

import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.entity.Role;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {

    // 🔐 1. Check login
    public static User checkLogin(HttpSession session) {
        Object obj = session.getAttribute("USER");
        if (obj == null) {
            throw new RuntimeException("Utilisateur non authentifié");
        }
        return (User) obj;
    }

    // 🔒 2. Check admin
    public static void checkAdmin(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Accès interdit (ADMIN seulement)");
        }
    }
}
