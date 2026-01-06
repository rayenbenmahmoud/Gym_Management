package com.iset.gymmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le solde est obligatoire")
    @DecimalMin(value = "0.0", inclusive = true, message = "Le solde ne peut pas être négatif")
    @Digits(integer = 10, fraction = 2, message = "Format du solde invalide")
    @Column(nullable = false, precision = 12, scale = 2) // précision et décimales pour BigDecimal
    private BigDecimal solde;

    @NotNull(message = "L'adhérent est obligatoire")
    @OneToOne
    @JoinColumn(name = "adherent_id", nullable = false)
    private Adherent adherent;

    public Card() {}

    public Card(BigDecimal solde, Adherent adherent) {
        this.solde = solde;
        this.adherent = adherent;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }
}
