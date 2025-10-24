package com.chefmanager.mvpchefmanager.repository;

import com.chefmanager.mvpchefmanager.model.OrdenTrabajo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajo, Long> {
    Page<OrdenTrabajo> findByNombreProductoContainingIgnoreCaseOrDistribuidorContainingIgnoreCase(
            String nombreProducto, String distribuidor, Pageable pageable);
}
