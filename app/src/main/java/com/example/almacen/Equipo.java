package com.example.almacen;
import java.lang.ref.SoftReference;
import java.util.Date;
//variables usadas en la base de dato
//Codigo ,Cliente ,Marca ,Modelo ,TipoEquipo ,ProblemaEquipo ,FechaEntrada ,ComentarioTecnico ,ComentarioCliente ,NumeroTelefono

public class Equipo {
    private String Codigo;
    private String Cliente;
    private String Marca;
    private String Modelo;
    private String tipoEquipo;
    private String ProblemaEquipo;
    private Date FechaEntrada;
    private String ComentarioTecnico;
    private String ComentarioCliente;
    private String Telefono;

    Equipo(){

    }

    Equipo(String codigo,String cliente,String marca,String tipoEquipo,String telefono){
        this.Cliente = cliente;
        this.Codigo = codigo;
        this.Marca = marca;
        this.tipoEquipo = tipoEquipo;
        this.Telefono = telefono;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        this.Codigo = codigo;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        this.Cliente = cliente;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca (String marca ) {
        this.Marca = marca;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTelefono (String telefono ) {
        this.Telefono = telefono;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTipoEquipo (String tipoequipo ) {
        this.tipoEquipo = tipoequipo;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getProblemaEquipo() {
        return ProblemaEquipo;
    }

    public void setProblemaEquipo(String problemaEquipo) {
        ProblemaEquipo = problemaEquipo;
    }

    public Date getFechaEntrada() {
        return FechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        FechaEntrada = fechaEntrada;
    }

    public String getComentarioTecnico() {
        return ComentarioTecnico;
    }

    public void setComentarioTecnico(String comentarioTecnico) {
        ComentarioTecnico = comentarioTecnico;
    }

    public String getComentarioCliente() {
        return ComentarioCliente;
    }

    public void setComentarioCliente(String comentarioCliente) {
        ComentarioCliente = comentarioCliente;
    }
}
