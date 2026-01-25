package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.dto.ProductDTO;
import com.iset.gymmanagement.entity.Product;
import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.mapper.ProductMapper;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    /**
     * Base URL du backend (ex: http://localhost:8080)
     * Définie via application.properties / variables d'environnement
     */
    @Value("${app.base-url}")
    private String baseUrl;

    public ProductController(
            ProductService productService,
            ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    /**
     * Construit l'URL complète de l'image à partir de son nom
     */
    private String buildImageUrl(String imageName) {
        return baseUrl + "/uploads/" + imageName;
    }

    /**
     * Cette méthode permet de créer un nouveau produit.
     * L'accès est réservé aux utilisateurs ayant le rôle ADMIN.
     */
    @PostMapping
    public ProductDTO create(
            @RequestParam("nom") String nom,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("prix") BigDecimal prix,
            @RequestParam("quantiteStock") Integer quantiteStock,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            HttpSession session) throws IOException {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        ProductDTO dto = new ProductDTO();
        dto.setNom(nom);
        dto.setDescription(description);
        dto.setPrix(prix);
        dto.setQuantiteStock(quantiteStock);

        Product product = productMapper.toEntity(dto);

        if (imageFile != null && !imageFile.isEmpty()) {
            String filename = UUID.randomUUID() + "_" +
                    imageFile.getOriginalFilename().replaceAll("\\s+", "_");

            Path path = Paths.get("uploads").resolve(filename);
            Files.createDirectories(path.getParent());
            Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            product.setImage(filename);
        }

        Product saved = productService.addProduct(product);
        ProductDTO result = productMapper.toDTO(saved);

        if (result.getImage() != null) {
            result.setImage(buildImageUrl(result.getImage()));
        }

        return result;
    }

    /**
     * Cette méthode retourne la liste de tous les produits.
     * L'utilisateur doit être authentifié.
     */
    @GetMapping
    public List<ProductDTO> getAll(HttpSession session) {

        AuthUtil.checkLogin(session);

        return productService.getAllProducts()
                .stream()
                .map(product -> {
                    ProductDTO dto = productMapper.toDTO(product);
                    if (dto.getImage() != null) {
                        dto.setImage(buildImageUrl(dto.getImage()));
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Cette méthode permet de récupérer les informations d'un produit
     * à partir de son identifiant.
     */
    @GetMapping("/{id}")
    public ProductDTO getById(
            @PathVariable Long id,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        ProductDTO dto = productMapper.toDTO(
                productService.getProductById(id)
        );

        if (dto.getImage() != null) {
            dto.setImage(buildImageUrl(dto.getImage()));
        }

        return dto;
    }

    /**
     * Cette méthode permet de mettre à jour les informations
     * d'un produit existant.
     */
    @PutMapping("/{id}")
    public ProductDTO update(
            @PathVariable Long id,
            @RequestParam("nom") String nom,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("prix") BigDecimal prix,
            @RequestParam("quantiteStock") Integer quantiteStock,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            HttpSession session) throws IOException {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        Product product = productService.getProductById(id);

        product.setNom(nom);
        product.setDescription(description);
        product.setPrix(prix);
        product.setQuantiteStock(quantiteStock);

        if (imageFile != null && !imageFile.isEmpty()) {
            String filename = UUID.randomUUID() + "_" +
                    imageFile.getOriginalFilename().replaceAll("\\s+", "_");

            Path path = Paths.get("uploads").resolve(filename);
            Files.createDirectories(path.getParent());
            Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            product.setImage(filename);
        }

        Product updated = productService.updateProduct(id, product);
        ProductDTO result = productMapper.toDTO(updated);

        if (result.getImage() != null) {
            result.setImage(buildImageUrl(result.getImage()));
        }

        return result;
    }

    /**
     * Cette méthode permet de supprimer un produit
     * à partir de son identifiant.
     */
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        productService.deleteProduct(id);
    }
}
