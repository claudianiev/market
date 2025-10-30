package org.linktic.marketlinck.productos.dto;




public class ProductoRequest {


    private int id;
    private String nombre;
    private Double precio;

    public ProductoRequest() {

    }

    public ProductoRequest(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }


}
