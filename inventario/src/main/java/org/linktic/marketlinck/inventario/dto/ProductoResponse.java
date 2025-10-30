package org.linktic.marketlinck.inventario.dto;

import lombok.Data;

@Data
public class ProductoResponse {
    private ProductoData data;

    @Data
    public static class ProductoData {
        private String type;
        private Long id;
        private ProductoAttributes attributes;
    }

    @Data
    public static class ProductoAttributes {
        private String nombre;
        private Double precio;
    }
}
