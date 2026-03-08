package com.iset.gymmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Le nom du produit est obligatoire")
    @Size(min = 2, max = 100)
    private String nom;

    @Size(max = 255)
    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal prix;

    @NotNull(message = "La quantité en stock est obligatoire")
    @Min(0)
    private Integer quantiteStock;

    @Size(max = 150, message = "Le nom de l'image est trop long")
    private String image;

    private MultipartFile imageFile;
}
