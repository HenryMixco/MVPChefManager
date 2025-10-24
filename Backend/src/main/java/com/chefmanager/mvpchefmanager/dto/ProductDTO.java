package com.chefmanager.mvpchefmanager.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private String sku;
    private String nombre;
    private Integer unidades;
    private BigDecimal costo;
}
