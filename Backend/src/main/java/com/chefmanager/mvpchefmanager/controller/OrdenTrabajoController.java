package com.chefmanager.mvpchefmanager.controller;

import com.chefmanager.mvpchefmanager.model.OrdenTrabajo;
import com.chefmanager.mvpchefmanager.service.OrdenTrabajoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ordenes-trabajo")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class OrdenTrabajoController {

    @Autowired
    private OrdenTrabajoService ordenTrabajoService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrdenesTrabajo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String search) {

        try {
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);

            Page<OrdenTrabajo> ordenPage;
            if (search != null && !search.trim().isEmpty()) {
                ordenPage = ordenTrabajoService.searchOrdenesTrabajo(search, pageable);
            } else {
                ordenPage = ordenTrabajoService.getAllOrdenesTrabajo(pageable);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("ordenes", ordenPage.getContent());
            response.put("currentPage", ordenPage.getNumber());
            response.put("totalItems", ordenPage.getTotalElements());
            response.put("totalPages", ordenPage.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenTrabajo> getOrdenTrabajoById(@PathVariable Long id) {
        return ordenTrabajoService.getOrdenTrabajoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOrdenTrabajo(@Valid @RequestBody OrdenTrabajo ordenTrabajo) {
        try {
            OrdenTrabajo createdOrden = ordenTrabajoService.createOrdenTrabajo(ordenTrabajo);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrden);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrdenTrabajo(@PathVariable Long id, @Valid @RequestBody OrdenTrabajo ordenTrabajo) {
        try {
            OrdenTrabajo updatedOrden = ordenTrabajoService.updateOrdenTrabajo(id, ordenTrabajo);
            return ResponseEntity.ok(updatedOrden);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrdenTrabajo(@PathVariable Long id) {
        try {
            ordenTrabajoService.deleteOrdenTrabajo(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}