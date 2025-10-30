package org.linktic.marketlinck.inventario.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.linktic.marketlinck.inventario.client.ProductoClient;
import org.linktic.marketlinck.inventario.dto.ProductoResponse;
import org.linktic.marketlinck.inventario.model.Inventario;
import org.linktic.marketlinck.inventario.model.ProductoRef;
import org.linktic.marketlinck.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class InventarioService {
    private final InventarioRepository inventarioRepository;
    private final ProductoClient productoClient;

    public InventarioService(InventarioRepository inventarioRepository, ProductoClient productoClient) {
        this.inventarioRepository = inventarioRepository;
        this.productoClient = productoClient;
    }

    @CircuitBreaker(name = "inventarioService", fallbackMethod = "fallbackProducto")
    @Retry(name = "inventarioService")
    public Inventario obtenerInventario(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    }

    @Transactional
    public ProductoResponse obtenerDetallesProducto(Long productoId) {
        // Consulta al microservicio ms_productos vía Eureka
        return productoClient.obtenerProducto(productoId);
    }

    @CircuitBreaker(name = "inventarioService", fallbackMethod = "fallbackProducto")
    @Retry(name = "inventarioService")
    public Inventario obtenerPorProducto(Long productoId) {
        return inventarioRepository.findByProductoRefId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para el producto con ID: " + productoId));

    }

    public Inventario fallbackProducto(Long id, Throwable ex) {
        Inventario inventario = new Inventario();
        ProductoRef p = new ProductoRef();
        inventario.setId(id);
        inventario.setCantidadDisponible(0);
        p.setId(id);
        p.setNombre("Inventario no disponible temporalmente");
        p.setPrecio(new BigDecimal(0));
        inventario.setProductoRef(p);
        System.out.println("Fallback activado: " + ex.getMessage());
        return inventario;
    }
}