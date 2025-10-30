package org.linktic.marketlinck.productos.controller;

/*import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;*/
import org.linktic.marketlinck.productos.dto.ProductoRequest;
import org.linktic.marketlinck.productos.model.Producto;
import org.linktic.marketlinck.productos.service.ProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
//@Tag(name = "Productos", description = "Gestión de productos")
public class ProductosController {

    private final ProductoService service;

    public ProductosController(ProductoService service) {
        this.service = service;
    }

   /* @Operation(summary = "Crear un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })]*/
    @PostMapping(
        consumes = "application/vnd.api+json",
        produces = "application/vnd.api+json"
    )
    public ResponseEntity<Map<String, Object>> crearProducto(@RequestBody ProductoRequest dto) {
        Producto producto = service.crearProducto(dto);
        Map<String, Object> response = Map.of(
                "data", Map.of(
                        "type", "producto",
                        "id", producto.getId(),
                        "attributes", Map.of(
                                "nombre", producto.getNombre(),
                                "precio", producto.getPrecio()
                        )
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /*@Operation(summary = "Obtener producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })*/
    @GetMapping(
        value = "/{id}",
        produces = "application/vnd.api+json"
    )
    public ResponseEntity<Map<String, Object>> obtener(@PathVariable Long id) {
        Producto producto = service.obtenerProducto(id);
        Map<String, Object> response = Map.of(
                "data", Map.of(
                        "type", "producto",
                        "id", producto.getId(),
                        "attributes", Map.of(
                                "nombre", producto.getNombre(),
                                "precio", producto.getPrecio()
                        )
                )
        );

        return ResponseEntity.ok(response);
    }


   // @Operation(summary = "Listar todos los productos con paginación")
    @GetMapping(produces = "application/vnd.api+json")
    public ResponseEntity<Map<String, Object>> listar(Pageable pageable) {
        Page<Producto> productos = service.listarProductos(pageable);
        List<Map<String, Object>> data = productos.getContent().stream()
                .map(p -> Map.of(
                        "type", "producto",
                        "id", p.getId(),
                        "attributes", Map.of(
                                "nombre", p.getNombre(),
                                "precio", p.getPrecio()
                        )
                ))
                .toList();

        Map<String, Object> response = Map.of(
                "data", data,
                "meta", Map.of(
                        "page", pageable.getPageNumber(),
                        "size", pageable.getPageSize(),
                        "totalElements", productos.getTotalElements(),
                        "totalPages", productos.getTotalPages()
                )
        );

        return ResponseEntity.ok(response);
    }


  /*  @Operation(summary = "Actualizar producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })*/
    @PutMapping(
        value = "/{id}",
        consumes = "application/vnd.api+json",
        produces = "application/vnd.api+json"
    )
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Map<String, Object> data = (Map<String, Object>) request.get("data");
        Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");

        Producto p = new Producto();
        p.setNombre((String) attributes.get("nombre"));
        p.setPrecio(Double.parseDouble(attributes.get("precio").toString()));

        Producto actualizado = service.actualizarProducto(id, p);

        Map<String, Object> response = Map.of(
                "data", Map.of(
                        "type", "producto",
                        "id", actualizado.getId(),
                        "attributes", Map.of(
                                "nombre", actualizado.getNombre(),
                                "precio", actualizado.getPrecio()
                        )
                )
        );

        return ResponseEntity.ok(response);
    }


   /* @Operation(summary = "Eliminar producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })*/
    @DeleteMapping(
            value = "/{id}",
            produces = "application/vnd.api+json"
    )
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        service.eliminarProducto(id);

        Map<String, Object> response = Map.of(
                "meta", Map.of(
                        "message", "Producto eliminado correctamente",
                        "timestamp", LocalDateTime.now().toString()
                )
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}