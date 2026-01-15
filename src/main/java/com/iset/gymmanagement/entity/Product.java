package com.iset.gymmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id=?")
@SQLRestriction("deleted = false")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du produit est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String nom;

    @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    @Digits(integer = 10, fraction = 2, message = "Format du prix invalide")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal prix;

    @NotNull(message = "La quantité en stock est obligatoire")
    @Min(value = 0, message = "La quantité ne peut pas être négative")
    @Column(name = "quantite_stock", nullable = false)
    private Integer quantiteStock;

    @Size(max = 150, message = "Le nom de l'image est trop long")
    private String image;

    @Column(nullable = false)
    private boolean deleted = false;
}
