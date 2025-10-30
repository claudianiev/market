package org.linktic.marketlinck.inventario.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.linktic.marketlinck.inventario.client.ProductoClient;
import org.linktic.marketlinck.inventario.dto.ProductoResponse;
import org.linktic.marketlinck.inventario.model.Inventario;
import org.linktic.marketlinck.inventario.model.ProductoRef;
import org.linktic.marketlinck.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    public Inventario obtenerPorProducto(Long productoId) {
        return inventarioRepository.findByProductoRefId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para el producto con ID: " + productoId));

    }

    public Inventario fallbackProducto(Long id, Throwable ex) {
        Inventario inventario = new Inventario();
        inventario.setId(id);
       // inventario.setNombre("Inventario no disponible temporalmente");
        System.out.println("Fallback activado: " + ex.getMessage());
        return inventario;
    }
}