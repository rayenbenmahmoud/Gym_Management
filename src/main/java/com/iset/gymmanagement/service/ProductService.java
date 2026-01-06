package com.iset.gymmanagement.service;

import com.iset.gymmanagement.entity.Product;
import com.iset.gymmanagement.exception.ResourceNotFoundException;
import com.iset.gymmanagement.exception.StockUnavailableException;
import com.iset.gymmanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ===========================
    // 1️⃣ إضافة منتج
    // ===========================
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // ===========================
    // 2️⃣ تعديل منتج
    // ===========================
    public Product updateProduct(Long id, Product updatedProduct) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit avec l'id " + id + " n'existe pas."));

        existing.setNom(updatedProduct.getNom());
        existing.setPrix(updatedProduct.getPrix()); // BigDecimal
        existing.setQuantiteStock(updatedProduct.getQuantiteStock());

        return productRepository.save(existing);
    }

    // ===========================
    // 3️⃣ حذف منتج
    // ===========================
    public void deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produit avec l'id " + id + " n'existe pas.");
        }

        productRepository.deleteById(id);
    }

    // ===========================
    // 4️⃣ إرجاع كل المنتجات
    // ===========================
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ===========================
    // 5️⃣ إرجاع منتج حسب ID
    // ===========================
    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit avec l'id " + id + " n'existe pas."));
    }

    // ===========================
    // 6️⃣ تحديث المخزون بعد بيع
    // ===========================
    public void updateStockAfterSale(Long productId, int quantitySold) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit avec l'id " + productId + " n'existe pas."));

        int newQuantity = product.getQuantiteStock() - quantitySold;

        if (newQuantity < 0) {
            throw new StockUnavailableException("Stock insuffisant pour le produit : " + product.getNom());
        }

        product.setQuantiteStock(newQuantity);

        productRepository.save(product);
    }
}
