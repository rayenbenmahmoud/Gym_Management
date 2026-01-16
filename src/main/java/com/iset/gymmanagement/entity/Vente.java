package com.iset.gymmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vente")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de la vente est obligatoire")
    @Column(nullable = false)
    private LocalDateTime date;

    @NotNull(message = "L'adhérent est obligatoire")
    @ManyToOne(optional = false)
    @JoinColumn(name = "adherent_id", nullable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Adherent adherent;

    @NotNull(message = "Le montant total est obligatoire")
    @DecimalMin(value = "0.01", inclusive = true, message = "Le montant total doit être supérieur à 0")
    @Digits(integer = 10, fraction = 2, message = "Format du montant total invalide")
    @Column(name = "montant_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal montantTotal;

    @OneToMany(
            mappedBy = "vente",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<VenteProduit> produits;

    public void addProduits(List<VenteProduit> produits) {
        this.produits = produits;
        if (produits != null) {
            for (VenteProduit vp : produits) {
                vp.setVente(this);
            }
        }
    }

}
