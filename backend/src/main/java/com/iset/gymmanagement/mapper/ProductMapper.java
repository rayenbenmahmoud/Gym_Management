package com.iset.gymmanagement.mapper;

import com.iset.gymmanagement.dto.ProductDTO;
import com.iset.gymmanagement.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO dto);
}
