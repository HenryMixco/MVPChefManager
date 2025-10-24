package com.chefmanager.mvpchefmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El SKU es obligatorio")
    @Column(unique = true, nullable = false)
    private String sku;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotNull(message = "Las unidades son obligatorias")
    @Min(value = 0, message = "Las unidades deben ser mayor o igual a 0")
    @Column(nullable = false)
    private Integer unidades;

    @NotNull(message = "El costo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El costo debe ser mayor o igual a 0")
    @Column(nullable = false)
    private Double costo;
}