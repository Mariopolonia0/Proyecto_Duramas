package com.example.almacen;
import java.lang.ref.SoftReference;

public class Herramienta {

    private int CodigoHerramienta;
    private String DescripcionHerramienta;
    private int CantidadHerramienta;

    Herramienta(){
        CodigoHerramienta = 0 ;
        DescripcionHerramienta = "";
        CantidadHerramienta = 0;
    }

    Herramienta(int codigoHerramienta ,String descripcionHerramienta,int cantidadHerramienta){
        this.CodigoHerramienta = codigoHerramienta;
        this.DescripcionHerramienta = descripcionHerramienta;
        this.CantidadHerramienta =cantidadHerramienta;

    }

    public void setCodigoHerramienta(int codigoHerramienta) {
        CodigoHerramienta = codigoHerramienta;
    }

    public void setDescripcionHerramienta(String descripcionHerramienta) {
        DescripcionHerramienta = descripcionHerramienta;
    }

    public void setCantidadHerramienta(int cantidadHerramienta) {
        CantidadHerramienta = cantidadHerramienta;
    }

    public int getCodigoHerramienta() {
        return CodigoHerramienta;
    }

    public String getDescripcionHerramienta() {
        return DescripcionHerramienta;
    }

    public int getCantidadHerramienta() {
        return CantidadHerramienta;
    }

}
