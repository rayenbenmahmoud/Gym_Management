package com.iset.gymmanagement.controller;

import com.iset.gymmanagement.entity.Product;
import com.iset.gymmanagement.entity.User;
import com.iset.gymmanagement.security.AuthUtil;
import com.iset.gymmanagement.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    // Constructor Injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1️⃣ Création d’un produit (ADMIN فقط)
    @PostMapping
    public Product createProduct(
            @Valid @RequestBody Product product,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        return productService.addProduct(product);
    }

    // 2️⃣ Consultation de la liste des produits (ADMIN + EMPLOYEE)
    @GetMapping
    public List<Product> getAllProducts(HttpSession session) {

        AuthUtil.checkLogin(session);

        return productService.getAllProducts();
    }

    // 3️⃣ Consultation du détail d’un produit (ADMIN + EMPLOYEE)
    @GetMapping("/{id}")
    public Product getProductById(
            @PathVariable Long id,
            HttpSession session) {

        AuthUtil.checkLogin(session);

        return productService.getProductById(id);
    }

    // 4️⃣ Modification d’un produit (ADMIN فقط)
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        return productService.updateProduct(id, product);
    }

    // 5️⃣ Suppression d’un produit (ADMIN فقط)
    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable Long id,
            HttpSession session) {

        User user = AuthUtil.checkLogin(session);
        AuthUtil.checkAdmin(user);

        productService.deleteProduct(id);
    }
}
