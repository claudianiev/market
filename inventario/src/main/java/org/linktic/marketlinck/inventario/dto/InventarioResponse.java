package org.linktic.marketlinck.inventario.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Data
@Builder
public class InventarioResponse {
    private Map<String, Object> data;
    private List<Map<String, Object>> included;

    public static InventarioResponse of(
            Long idInventario,
            int cantidad,
            LocalDateTime ultimaActualizacion,
            Long productoId,
            String nombre,
            Double precio
    ) {
        Map<String, Object> data = Map.of(
                "type", "inventario",
                "id", idInventario,
                "attributes", Map.of(
                        "cantidad_disponible", cantidad,
                        "ultima_actualizacion", ultimaActualizacion
                ),
                "relationships", Map.of(
                        "producto", Map.of(
                                "data", Map.of(
                                        "type", "producto",
                                        "id", productoId
                                )
                        )
                )
        );

        Map<String, Object> includedProducto = Map.of(
                "type", "producto",
                "id", productoId,
                "attributes", Map.of(
                        "nombre", nombre,
                        "precio", precio
                )
        );

        return InventarioResponse.builder()
                .data(data)
                .included(List.of(includedProducto))
                .build();
    }
}
