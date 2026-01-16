package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.dto.ProductDTO;
import com.iset.gymmanagement.entity.Product;
import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.mapper.ProductMapper;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    /**
     * Cette méthode permet d'ajouter un nouveau produit au système.
     * Seul un administrateur authentifié est autorisé à effectuer cette action.
     * @param dto les informations du produit à ajouter
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return le produit créé
     */
    @PostMapping
    public ProductDTO createProduct(
            @Valid @RequestBody ProductDTO dto,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        Product product = productMapper.toEntity(dto);
        Product saved = productService.addProduct(product);

        return productMapper.toDTO(saved);
    }

    /**
     * Cette méthode permet de récupérer la liste de tous les produits disponibles.
     * L'utilisateur doit être authentifié.
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return la liste des produits
     */
    @GetMapping
    public List<ProductDTO> getAllProducts(HttpSession session) {

        AuthUtil.checkLogin(session);

        return productService.getAllProducts()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cette méthode permet de récupérer les informations d'un produit
     * à partir de son identifiant.
     * L'utilisateur doit être authentifié.
     * @param id l'identifiant du produit
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return le produit correspondant
     */
    @GetMapping("/{id}")
    public ProductDTO getProductById(
            @PathVariable Long id,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        return productMapper.toDTO(
                productService.getProductById(id)
        );
    }

    /**
     * Cette méthode permet de mettre à jour les informations d'un produit existant.
     * Seul un administrateur authentifié est autorisé.
     * @param id l'identifiant du produit à modifier
     * @param dto les nouvelles informations du produit
     * @param session la session HTTP utilisée pour vérifier l'authentification
     * @return le produit mis à jour
     */
    @PutMapping("/{id}")
    public ProductDTO updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO dto,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        Product product = productMapper.toEntity(dto);
        Product updated = productService.updateProduct(id, product);

        return productMapper.toDTO(updated);
    }

    /**
     * Cette méthode permet de supprimer un produit du système.
     * Seul un administrateur authentifié est autorisé.
     * @param id l'identifiant du produit à supprimer
     * @param session la session HTTP utilisée pour vérifier l'authentification
     */
    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable Long id,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        productService.deleteProduct(id);
    }
}
