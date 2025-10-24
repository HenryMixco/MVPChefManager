package com.chefmanager.mvpchefmanager.service;

import com.chefmanager.mvpchefmanager.model.Product;
import com.chefmanager.mvpchefmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    @Transactional
    public Product createProduct(Product product) {
        // Validar que el SKU sea único
        if (productRepository.findBySku(product.getSku()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un producto con el SKU: " + product.getSku());
        }
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id: " + id));

        // Validar que el SKU sea único si se está cambiando
        if (!product.getSku().equals(productDetails.getSku())) {
            if (productRepository.findBySku(productDetails.getSku()).isPresent()) {
                throw new IllegalArgumentException("Ya existe un producto con el SKU: " + productDetails.getSku());
            }
        }

        product.setSku(productDetails.getSku());
        product.setNombre(productDetails.getNombre());
        product.setUnidades(productDetails.getUnidades());
        product.setCosto(productDetails.getCosto());

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado con id: " + id);
        }
        productRepository.deleteById(id);
    }

    public Page<Product> searchProducts(String search, Pageable pageable) {
        return productRepository.findBySkuContainingOrNombreContaining(search, search, pageable);
    }
}
