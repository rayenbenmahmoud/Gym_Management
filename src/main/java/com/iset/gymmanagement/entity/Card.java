package com.iset.gymmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "card")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le solde est obligatoire")
    @DecimalMin(value = "0.0", inclusive = true, message = "Le solde ne peut pas être négatif")
    @Digits(integer = 10, fraction = 2, message = "Format du solde invalide")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal solde;

    @NotNull(message = "L'adhérent est obligatoire")
    @OneToOne
    @JoinColumn(name = "adherent_id", nullable = false, unique = true)
    private Adherent adherent;

    @Column(nullable = false)
    private boolean active = true;
}
