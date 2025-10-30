package org.linktic.marketlinck.inventario.client;

import org.linktic.marketlinck.inventario.dto.ProductoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-productos")
public interface ProductoClient {
    @GetMapping("/productos/{id}")
    ProductoResponse obtenerProducto(@PathVariable Long id);
}
