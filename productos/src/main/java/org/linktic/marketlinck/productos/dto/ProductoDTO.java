package org.linktic.marketlinck.productos.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.linktic.marketlinck.productos.model.Producto;

@Data
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private Double precio;

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.precio = producto.getPrecio();
    }
}