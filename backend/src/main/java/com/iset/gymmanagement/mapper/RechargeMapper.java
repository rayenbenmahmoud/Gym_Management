package com.iset.gymmanagement.mapper;

import com.iset.gymmanagement.dto.RechargeDTO;
import com.iset.gymmanagement.entity.Recharge;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RechargeMapper {

    RechargeDTO toDTO(Recharge recharge);
}
