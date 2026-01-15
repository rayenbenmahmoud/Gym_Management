package com.iset.gymmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDate;

@Entity
@Table(name = "adherent")
@SQLDelete(sql = "UPDATE adherent SET deleted = true WHERE id=?")
@SQLRestriction("deleted = false")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adherent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    private String email;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(
            regexp = "^[0-9]{8,15}$",
            message = "Le téléphone doit contenir entre 8 et 15 chiffres"
    )
    private String telephone;

    @NotNull(message = "La date de naissance est obligatoire")
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    private boolean deleted = false;
}
