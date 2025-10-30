package org.linktic.marketlinck.inventario.controller;

import org.linktic.marketlinck.inventario.client.ProductoClient;
import org.linktic.marketlinck.inventario.dto.InventarioResponse;
import org.linktic.marketlinck.inventario.dto.ProductoResponse;
import org.linktic.marketlinck.inventario.model.Inventario;
import org.linktic.marketlinck.inventario.service.InventarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService inventarioService;
    private final ProductoClient productoClient;

    public InventarioController(InventarioService inventarioService, ProductoClient productoClient) {
        this.inventarioService = inventarioService;
        this.productoClient = productoClient;
    }

    @GetMapping("/{id}")
    public Inventario obtenerInventario(@PathVariable Long id) {
        return inventarioService.obtenerInventario(id);
    }

    @GetMapping("/producto/{id}")
    public ProductoResponse obtenerProductoDesdeProductos(@PathVariable Long id) {
        return inventarioService.obtenerDetallesProducto(id);
    }

    @GetMapping("/consultar/{productoId}")
    public InventarioResponse obtenerInventarioPorProducto(@PathVariable Long productoId) {
        Inventario inventario = inventarioService.obtenerPorProducto(productoId);
        ProductoResponse producto = productoClient.obtenerProducto(productoId);

        return InventarioResponse.of(
                inventario.getId(),
                inventario.getCantidadDisponible(),
                inventario.getUltimaActualizacion(),
                producto.getData().getId(),
                producto.getData().getAttributes().getNombre(),
                producto.getData().getAttributes().getPrecio()
        );
    }
}