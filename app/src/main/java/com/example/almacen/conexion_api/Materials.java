package com.example.almacen.conexion_api;

public class Materials {
    //[{"materialId":1,"descripcion":"Detonillador Detria","cantidad":2,"marca":"Truper"}]
    public int materialId;
    private String descripcion;
    private int cantidad;
    private String marca;

    public Materials() {

    }
    public Materials( String descripcion, int cantidad, String marca) {
        this.materialId = 0;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.marca = marca;
    }

    public int getMaterialId() {
        return materialId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getMarca() {
        return marca;
    }
}
