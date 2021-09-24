package com.example.almacen;

public class DetalleMaterial {

    private String Descripcion;
    private String Precio;
    private String Cantidad;
    private String ITBIS;
    private String Total;


    DetalleMaterial(){
        this.Descripcion="";
        this.Precio="0";

    }

    DetalleMaterial(String descripcion,String precio,String cantidad,String total){
        this.Cantidad = cantidad;
        this.Total=total;
        this.Descripcion=descripcion;
        this.Precio=precio;
    }

    DetalleMaterial(String descripcion,String precio,String cantidad,String total,String  itbis){
        this.Cantidad = cantidad;
        this.Total=total;
        this.Descripcion=descripcion;
        this.Precio=precio;
        this.ITBIS = itbis;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public String getTotal() { return Total; }

    public String getITBIS() {
        return ITBIS;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setCantidad(String cantidad) { Cantidad = cantidad; }

    public void setTotal(String total) {
        Total = total;
    }

    public void setITBIS(String ITBIS) {
        this.ITBIS = ITBIS;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    public void setPrecio(String precio) { Precio = precio; }



}

