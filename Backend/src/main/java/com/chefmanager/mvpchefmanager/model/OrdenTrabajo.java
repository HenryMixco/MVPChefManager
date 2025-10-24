package com.chefmanager.mvpchefmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "orden_trabajo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Column(nullable = false)
    private String nombreProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Column(nullable = false)
    private Integer cantidad;

    @NotBlank(message = "El distribuidor/empresa es obligatorio")
    @Column(nullable = false)
    private String distribuidor;

    @NotNull(message = "El precio total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a 0")
    @Column(nullable = false)
    private Double precioTotal;

    @Column(nullable = false)
    private LocalDate fechaCompra;

    @PrePersist
    protected void onCreate() {
        if (fechaCompra == null) {
            fechaCompra = LocalDate.now();
        }
    }
}
