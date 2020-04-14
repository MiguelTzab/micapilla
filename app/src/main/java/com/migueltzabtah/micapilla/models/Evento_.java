package com.migueltzabtah.micapilla.models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by migueltzabtah on 08/08/18.
 */

public class Evento_ implements Serializable{

    @SerializedName("evento_id")
    @Expose
    private String eventoId;
    @SerializedName("evento_nombre")
    @Expose
    private String eventoNombre;
    @SerializedName("evento_descripcion")
    @Expose
    private String eventoDescripcion;
    @SerializedName("evento_horaInicio")
    @Expose
    private String eventoHoraInicio;
    @SerializedName("evento_fecha")
    @Expose
    private String eventoFecha;
    @SerializedName("evento_imagen")
    @Expose
    private String eventoImagen;
    @SerializedName("evento_fechaCreacion")
    @Expose
    private String eventoFechaCreacion;
    @SerializedName("evento_estatus")
    @Expose
    private String eventoEstatus;
    @SerializedName("iglesia_id")
    @Expose
    private String iglesiaId;
    @SerializedName("marked")
    @Expose
    private String marked;

    public String getEventoId() {
        return eventoId;
    }

    public void setEventoId(String eventoId) {
        this.eventoId = eventoId;
    }

    public String getEventoNombre() {
        return eventoNombre;
    }

    public void setEventoNombre(String eventoNombre) {
        this.eventoNombre = eventoNombre;
    }

    public String getEventoDescripcion() {
        return eventoDescripcion;
    }

    public void setEventoDescripcion(String eventoDescripcion) {
        this.eventoDescripcion = eventoDescripcion;
    }

    public String getEventoHoraInicio() {
        return eventoHoraInicio;
    }

    public void setEventoHoraInicio(String eventoHoraInicio) {
        this.eventoHoraInicio = eventoHoraInicio;
    }

    public String getEventoFecha() {
        return eventoFecha;
    }

    public void setEventoFecha(String eventoFecha) {
        this.eventoFecha = eventoFecha;
    }

    public String getEventoImagen() {
        return eventoImagen;
    }

    public void setEventoImagen(String eventoImagen) {
        this.eventoImagen = eventoImagen;
    }

    public String getEventoFechaCreacion() {
        return eventoFechaCreacion;
    }

    public void setEventoFechaCreacion(String eventoFechaCreacion) {
        this.eventoFechaCreacion = eventoFechaCreacion;
    }

    public String getMarked() {
        return marked;
    }

    public void setMarked(String marked) {
        this.marked = marked;
    }

    public String getEventoEstatus() {
        return eventoEstatus;
    }

    public void setEventoEstatus(String eventoEstatus) {
        this.eventoEstatus = eventoEstatus;
    }

    public String getIglesiaId() {
        return iglesiaId;
    }

    public void setIglesiaId(String iglesiaId) {
        this.iglesiaId = iglesiaId;
    }

}