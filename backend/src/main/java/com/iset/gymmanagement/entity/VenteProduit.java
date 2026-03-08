package com.iset.gymmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.math.BigDecimal;

@Entity
@Table(name = "vente_produit")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VenteProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La vente est obligatoire")
    @ManyToOne(optional = false)
    @JoinColumn(name = "vente_id", nullable = false)
    private Vente vente;

    @NotNull(message = "Le produit est obligatoire")
    @ManyToOne(optional = false)
    @JoinColumn(name = "produit_id", nullable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Product produit;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être au moins 1")
    @Column(nullable = false)
    private Integer quantite;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @DecimalMin(value = "0.01", inclusive = true, message = "Le prix unitaire doit être supérieur à 0")
    @Digits(integer = 10, fraction = 2, message = "Format du prix unitaire invalide")
    @Column(name = "prix_unitaire", nullable = false, precision = 12, scale = 2)
    private BigDecimal prixUnitaire;
}
