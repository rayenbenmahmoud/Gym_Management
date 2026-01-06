package com.iset.gymmanagement.repository;

import com.iset.gymmanagement.dto.TopProduitDTO;
import com.iset.gymmanagement.entity.VenteProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VenteProduitRepository extends JpaRepository<VenteProduit, Long> {

    @Query("""
        SELECT new com.iset.gymmanagement.dto.TopProduitDTO(
            vp.produit.nom,
            SUM(vp.quantite)
        )
        FROM VenteProduit vp
        GROUP BY vp.produit.nom
        ORDER BY SUM(vp.quantite) DESC
    """)
    List<TopProduitDTO> findTopProduits();
}
