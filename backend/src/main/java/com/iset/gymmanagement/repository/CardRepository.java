package com.iset.gymmanagement.repository;

import com.iset.gymmanagement.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.iset.gymmanagement.entity.Adherent;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByAdherent(Adherent adherent);
}
