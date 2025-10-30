package org.linktic.marketlinck.productos.service;

import org.linktic.marketlinck.productos.dto.ProductoRequest;
import org.linktic.marketlinck.productos.exception.ResourceNotFoundException;
import org.linktic.marketlinck.productos.model.Producto;
import org.linktic.marketlinck.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }


    public Producto crearProducto(ProductoRequest dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        return repository.save(producto);
    }

    public Producto obtenerProducto(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID " + id));
    }

    public Page<Producto> listarProductos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Producto actualizarProducto(Long id, Producto p) {
        Producto productoExistente  = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID " + id));

        productoExistente.setNombre(p.getNombre());
        productoExistente.setPrecio(p.getPrecio());

        return repository.save(productoExistente);
    }

    public void eliminarProducto(Long id) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID " + id));
        repository.delete(producto);
    }
}