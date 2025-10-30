package org.linktic.marketlinck;

import org.linktic.marketlinck.productos.model.Producto;
import org.linktic.marketlinck.productos.repository.ProductoRepository;
import org.linktic.marketlinck.productos.dto.ProductoRequest;
import org.linktic.marketlinck.productos.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.linktic.marketlinck.productos.service.ProductoService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas unitarias simuladas para ProductoService")
class ProductoServiceMockTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private ProductoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Crear producto correctamente")
    void should_create_producto() {
        ProductoRequest dto = new ProductoRequest();
        dto.setNombre("Producto Simulado");
        dto.setPrecio(99.9);

        Producto productoGuardado = new Producto();
        productoGuardado.setId(1L);
        productoGuardado.setNombre(dto.getNombre());
        productoGuardado.setPrecio(dto.getPrecio());

        when(repository.save(any(Producto.class))).thenReturn(productoGuardado);

        Producto resultado = service.crearProducto(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Producto Simulado", resultado.getNombre());
        verify(repository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Obtener producto existente")
    void should_obtener_producto() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Existente");
        producto.setPrecio(50.0);

        when(repository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = service.obtenerProducto(1L);

        assertNotNull(resultado);
        assertEquals("Existente", resultado.getNombre());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Lanzar excepción si producto no existe")
    void should_throw_exception_producto_no_existente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.obtenerProducto(1L));

        assertEquals("Producto no encontrado con ID 1", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Listar productos con paginación")
    void should_list_productos() {
        Producto p1 = new Producto(); p1.setId(1L); p1.setNombre("P1"); p1.setPrecio(10.0);
        Producto p2 = new Producto(); p2.setId(2L); p2.setNombre("P2"); p2.setPrecio(20.0);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Producto> page = new PageImpl<>(Arrays.asList(p1, p2));

        when(repository.findAll(pageable)).thenReturn(page);

        Page<Producto> resultado = service.listarProductos(pageable);

        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Actualizar producto existente")
    void should_update_producto() {
        Producto existente = new Producto();
        existente.setId(1L);
        existente.setNombre("Antiguo");
        existente.setPrecio(100.0);

        Producto nuevo = new Producto();
        nuevo.setNombre("Nuevo");
        nuevo.setPrecio(150.0);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        Producto resultado = service.actualizarProducto(1L, nuevo);

        assertEquals("Nuevo", resultado.getNombre());
        assertEquals(150.0, resultado.getPrecio());
        verify(repository).findById(1L);
        verify(repository).save(existente);
    }

    @Test
    @DisplayName("Lanzar excepción al actualizar producto inexistente")
    void should_throw_exception_update_no_existente() {
        Producto nuevo = new Producto();
        nuevo.setNombre("Nuevo");
        nuevo.setPrecio(150.0);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.actualizarProducto(1L, nuevo));

        assertEquals("Producto no encontrado con ID 1", exception.getMessage());
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Eliminar producto existente")
    void should_delete_producto() {
        Producto existente = new Producto();
        existente.setId(1L);
        existente.setNombre("Eliminar");
        existente.setPrecio(100.0);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        doNothing().when(repository).delete(existente);

        service.eliminarProducto(1L);

        verify(repository).findById(1L);
        verify(repository).delete(existente);
    }

    @Test
    @DisplayName("Lanzar excepción al eliminar producto inexistente")
    void should_throw_exception_delete_no_existente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.eliminarProducto(1L));

        assertEquals("Producto no encontrado con ID 1", exception.getMessage());
        verify(repository).findById(1L);
    }
}