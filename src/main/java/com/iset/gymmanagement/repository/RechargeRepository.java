package com.iset.gymmanagement.repository;

import com.iset.gymmanagement.entity.Recharge;
import com.iset.gymmanagement.entity.Adherent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RechargeRepository extends JpaRepository<Recharge, Long> {

    List<Recharge> findByAdherent(Adherent adherent);
}
