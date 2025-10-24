package com.chefmanager.mvpchefmanager.repository;

import com.chefmanager.mvpchefmanager.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    Page<Product> findBySkuContainingOrNombreContaining(String sku, String nombre, Pageable pageable);
}
