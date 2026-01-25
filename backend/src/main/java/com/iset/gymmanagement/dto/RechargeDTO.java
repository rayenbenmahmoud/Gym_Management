package com.iset.gymmanagement.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public record RechargeDTO(
        Long id,
        BigDecimal amount,
        LocalDateTime date
) {}
