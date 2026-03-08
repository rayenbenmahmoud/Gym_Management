package com.iset.gymmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recharge")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de recharge est obligatoire")
    @Column(nullable = false)
    private LocalDateTime date;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.1", inclusive = true, message = "Le montant doit être supérieur à 0")
    @Digits(integer = 10, fraction = 2, message = "Format du montant invalide")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "L'adhérent est obligatoire")
    @ManyToOne(optional = false)
    @JoinColumn(name = "adherent_id", nullable = false)
    private Adherent adherent;
}
