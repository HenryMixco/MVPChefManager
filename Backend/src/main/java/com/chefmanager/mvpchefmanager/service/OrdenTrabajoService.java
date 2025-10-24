package com.chefmanager.mvpchefmanager.service;

import com.chefmanager.mvpchefmanager.model.OrdenTrabajo;
import com.chefmanager.mvpchefmanager.repository.OrdenTrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenTrabajoService {

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    public Page<OrdenTrabajo> getAllOrdenesTrabajo(Pageable pageable) {
        return ordenTrabajoRepository.findAll(pageable);
    }

    public List<OrdenTrabajo> getAllOrdenesTrabajo() {
        return ordenTrabajoRepository.findAll();
    }

    public Optional<OrdenTrabajo> getOrdenTrabajoById(Long id) {
        return ordenTrabajoRepository.findById(id);
    }

    @Transactional
    public OrdenTrabajo createOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        return ordenTrabajoRepository.save(ordenTrabajo);
    }

    @Transactional
    public OrdenTrabajo updateOrdenTrabajo(Long id, OrdenTrabajo ordenTrabajoDetails) {
        OrdenTrabajo ordenTrabajo = ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden de trabajo no encontrada con id: " + id));

        ordenTrabajo.setNombreProducto(ordenTrabajoDetails.getNombreProducto());
        ordenTrabajo.setCantidad(ordenTrabajoDetails.getCantidad());
        ordenTrabajo.setDistribuidor(ordenTrabajoDetails.getDistribuidor());
        ordenTrabajo.setPrecioTotal(ordenTrabajoDetails.getPrecioTotal());

        if (ordenTrabajoDetails.getFechaCompra() != null) {
            ordenTrabajo.setFechaCompra(ordenTrabajoDetails.getFechaCompra());
        }

        return ordenTrabajoRepository.save(ordenTrabajo);
    }

    @Transactional
    public void deleteOrdenTrabajo(Long id) {
        if (!ordenTrabajoRepository.existsById(id)) {
            throw new IllegalArgumentException("Orden de trabajo no encontrada con id: " + id);
        }
        ordenTrabajoRepository.deleteById(id);
    }

    public Page<OrdenTrabajo> searchOrdenesTrabajo(String search, Pageable pageable) {
        return ordenTrabajoRepository.findByNombreProductoContainingOrDistribuidorContaining(
                search, search, pageable);
    }
}
