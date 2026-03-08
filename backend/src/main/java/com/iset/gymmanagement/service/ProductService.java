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

    /**
     * Cette méthode ajoute un nouveau produit dans la base de données.
     * @param product le produit à ajouter
     * @return le produit enregistré
     */
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Cette méthode met à jour les informations d'un produit existant
     * en utilisant son identifiant. Si le produit n'existe pas, une
     * exception est levée.
     * @param id l'identifiant du produit à modifier
     * @param updatedProduct les nouvelles données du produit
     * @return le produit mis à jour
     */
    public Product updateProduct(Long id, Product updatedProduct) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit avec l'id " + id + " n'existe pas."));

        existing.setNom(updatedProduct.getNom());
        existing.setPrix(updatedProduct.getPrix());
        existing.setQuantiteStock(updatedProduct.getQuantiteStock());
        existing.setDescription(updatedProduct.getDescription());
        existing.setImage(updatedProduct.getImage());

        return productRepository.save(existing);
    }

    /**
     * Cette méthode supprime logiquement un produit de la base de données
     * à partir de son identifiant. Si le produit n'existe pas,
     * une exception est levée. C'est un soft delete.
     * @param id l'identifiant du produit à supprimer
     */
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produit avec l'id " + id + " n'existe pas.")
                );

        product.setDeleted(true);
        productRepository.save(product);
    }

    /**
     * Cette méthode retourne la liste de tous les produits
     * non supprimés.
     * @return la liste des produits
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Cette méthode récupère un produit en utilisant son identifiant.
     * Si aucun produit n'est trouvé, une exception est levée.
     * @param id l'identifiant du produit recherché
     * @return le produit correspondant à l'identifiant
     */
    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit avec l'id " + id + " n'existe pas."));
    }

    /**
     * Cette méthode met à jour la quantité en stock d'un produit
     * après une vente. Elle vérifie d'abord si la quantité demandée
     * est disponible en stock. Si le stock est insuffisant, une
     * exception est levée.
     * @param productId l'identifiant du produit vendu
     * @param quantitySold la quantité vendue
     */
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
