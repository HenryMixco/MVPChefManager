package com.chefmanager.mvpchefmanager.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrdenTrabajoDTO {
    private Long id;
    private String nombreProducto;
    private Integer cantidad;
    private String distribuidor;
    private BigDecimal precioTotal;
    private LocalDate fechaCompra;
}
