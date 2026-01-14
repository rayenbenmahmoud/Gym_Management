package com.iset.gymmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


@Entity
@Table(name = "vente")
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de la vente est obligatoire")
    private LocalDateTime date;

    @NotNull(message = "L'adhérent est obligatoire")
    @ManyToOne
    @JoinColumn(name = "adherent_id", nullable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Adherent adherent;

    @NotNull(message = "Le montant total est obligatoire")
    @DecimalMin(value = "0.01", inclusive = true, message = "Le montant total doit être supérieur à 0")
    @Digits(integer = 10, fraction = 2, message = "Format du montant total invalide")
    @Column(name = "montant_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal montantTotal;

    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<VenteProduit> produits;


    public Vente() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public List<VenteProduit> getProduits() {
        return produits;
    }

    public void setProduits(List<VenteProduit> produits) {
        this.produits = produits;
        if (produits != null) {
            for (VenteProduit vp : produits) {
                vp.setVente(this);
            }
        }
    }
}
