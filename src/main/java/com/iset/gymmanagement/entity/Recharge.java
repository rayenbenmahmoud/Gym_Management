package com.iset.gymmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recharge")
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
    @Column(nullable = false, precision = 12, scale = 2) // précision pour BigDecimal
    private BigDecimal amount;

    @NotNull(message = "L'adhérent est obligatoire")
    @ManyToOne(optional = false)
    @JoinColumn(name = "adherent_id")
    private Adherent adherent;

    // ===== Constructors =====

    public Recharge() {}

    public Recharge(LocalDateTime date, BigDecimal amount, Adherent adherent) {
        this.date = date;
        this.amount = amount;
        this.adherent = adherent;
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }
}
