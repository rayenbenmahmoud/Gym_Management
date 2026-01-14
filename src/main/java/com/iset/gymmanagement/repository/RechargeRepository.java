package com.iset.gymmanagement.repository;

import com.iset.gymmanagement.dto.RechargeDTO;
import com.iset.gymmanagement.entity.Recharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RechargeRepository extends JpaRepository<Recharge, Long> {

    @Query("""
        SELECT new com.iset.gymmanagement.dto.RechargeDTO(
            r.id,
            r.amount,
            r.date
        )
        FROM Recharge r
        WHERE r.adherent.id = :id
    """)
    List<RechargeDTO> findHistoryByAdherentId(@Param("id") Long id);
}
