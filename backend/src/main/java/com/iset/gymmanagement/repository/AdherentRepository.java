package com.iset.gymmanagement.repository;

import com.iset.gymmanagement.entity.Adherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AdherentRepository extends JpaRepository<Adherent, Long> {

    @Query(
            value = "SELECT COUNT(*) FROM adherent WHERE id = :id",
            nativeQuery = true
    )
    long existsPhysically(@Param("id") Long id);

    /**
     * Cette méthode récupère tous les adhérents
     * avec leurs cartes associées (JOIN FETCH).
     */
    @Query("""
           SELECT a
           FROM Adherent a
           LEFT JOIN FETCH a.card
           """)
    List<Adherent> findAllWithCard();
}

