package com.chefmanager.mvpchefmanager.service;

import com.chefmanager.mvpchefmanager.dto.OrdenTrabajoDTO;
import com.chefmanager.mvpchefmanager.model.OrdenTrabajo;
import com.chefmanager.mvpchefmanager.repository.OrdenTrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class OrdenTrabajoService {

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    public Page<OrdenTrabajo> getAllOrdenes(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        if (search != null && !search.trim().isEmpty()) {
            return ordenTrabajoRepository.findByNombreProductoContainingIgnoreCaseOrDistribuidorContainingIgnoreCase(
                    search, search, pageable);
        }

        return ordenTrabajoRepository.findAll(pageable);
    }

    public OrdenTrabajo getOrdenById(Long id) {
        return ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de trabajo no encontrada con id: " + id));
    }

    public OrdenTrabajo createOrden(OrdenTrabajoDTO ordenDTO) {
        OrdenTrabajo orden = new OrdenTrabajo();
        orden.setNombreProducto(ordenDTO.getNombreProducto());
        orden.setCantidad(ordenDTO.getCantidad());
        orden.setDistribuidor(ordenDTO.getDistribuidor());
        orden.setPrecioTotal(ordenDTO.getPrecioTotal());
        orden.setFechaCompra(ordenDTO.getFechaCompra() != null ? ordenDTO.getFechaCompra() : LocalDate.now());

        return ordenTrabajoRepository.save(orden);
    }

    public OrdenTrabajo updateOrden(Long id, OrdenTrabajoDTO ordenDTO) {
        OrdenTrabajo orden = getOrdenById(id);

        orden.setNombreProducto(ordenDTO.getNombreProducto());
        orden.setCantidad(ordenDTO.getCantidad());
        orden.setDistribuidor(ordenDTO.getDistribuidor());
        orden.setPrecioTotal(ordenDTO.getPrecioTotal());
        if (ordenDTO.getFechaCompra() != null) {
            orden.setFechaCompra(ordenDTO.getFechaCompra());
        }

        return ordenTrabajoRepository.save(orden);
    }

    public void deleteOrden(Long id) {
        if (!ordenTrabajoRepository.existsById(id)) {
            throw new RuntimeException("Orden de trabajo no encontrada con id: " + id);
        }
        ordenTrabajoRepository.deleteById(id);
    }
}
