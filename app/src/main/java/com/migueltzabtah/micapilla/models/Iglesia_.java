package com.migueltzabtah.micapilla.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Iglesia_ implements Serializable{

    @SerializedName("iglesia_id")
    @Expose
    private String iglesiaId;
    @SerializedName("iglesia_nombre")
    @Expose
    private String iglesiaNombre;
    @SerializedName("iglesia_ubicacion")
    @Expose
    private String iglesiaUbicacion;
    @SerializedName("iglesia_descripcion")
    @Expose
    private String iglesiaDescripcion;
    @SerializedName("iglesia_imagen")
    @Expose
    private String iglesiaImagen;
    @SerializedName("ciudades_id")
    @Expose
    private String ciudadesId;

    public String getIglesiaId() {
        return iglesiaId;
    }

    public void setIglesiaId(String iglesiaId) {
        this.iglesiaId = iglesiaId;
    }

    public String getIglesiaNombre() {
        return iglesiaNombre;
    }

    public void setIglesiaNombre(String iglesiaNombre) {
        this.iglesiaNombre = iglesiaNombre;
    }

    public String getIglesiaUbicacion() {
        return iglesiaUbicacion;
    }

    public void setIglesiaUbicacion(String iglesiaUbicacion) {
        this.iglesiaUbicacion = iglesiaUbicacion;
    }

    public String getIglesiaDescripcion() {
        return iglesiaDescripcion;
    }

    public void setIglesiaDescripcion(String iglesiaDescripcion) {
        this.iglesiaDescripcion = iglesiaDescripcion;
    }

    public String getIglesiaImagen() {
        return iglesiaImagen;
    }

    public void setIglesiaImagen(String iglesiaImagen) {
        this.iglesiaImagen = iglesiaImagen;
    }

    public String getCiudadesId() {
        return ciudadesId;
    }

    public void setCiudadesId(String ciudadesId) {
        this.ciudadesId = ciudadesId;
    }

}