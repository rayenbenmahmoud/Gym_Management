package com.iset.gymmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, max = 50, message = "Le mot de passe doit contenir entre 6 et 50 caractères")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Le rôle est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
