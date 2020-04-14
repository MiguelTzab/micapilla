package com.migueltzabtah.micapilla.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by migueltzabtah on 15/08/18.
 */

public class Itinerario_ {
    @SerializedName("itinerario_id")
    @Expose
    private String itinerarioId;
    @SerializedName("itinerario_nombre")
    @Expose
    private String itinerarioNombre;
    @SerializedName("itinerario_descripcion")
    @Expose
    private String itinerarioDescripcion;
    @SerializedName("itinerario_dia")
    @Expose
    private String itinerarioDia;
    @SerializedName("itinerario_fechaCreacion")
    @Expose
    private String itinerarioFechaCreacion;
    @SerializedName("itinerario_estatus")
    @Expose
    private String itinerarioEstatus;
    @SerializedName("iglesia_id")
    @Expose
    private String iglesiaId;

    public String getItinerarioId() {
        return itinerarioId;
    }

    public void setItinerarioId(String itinerarioId) {
        this.itinerarioId = itinerarioId;
    }

    public String getItinerarioNombre() {
        return itinerarioNombre;
    }

    public void setItinerarioNombre(String itinerarioNombre) {
        this.itinerarioNombre = itinerarioNombre;
    }

    public String getItinerarioDescripcion() {
        return itinerarioDescripcion;
    }

    public void setItinerarioDescripcion(String itinerarioDescripcion) {
        this.itinerarioDescripcion = itinerarioDescripcion;
    }

    public String getItinerarioDia() {
        return itinerarioDia;
    }

    public void setItinerarioDia(String itinerarioDia) {
        this.itinerarioDia = itinerarioDia;
    }

    public String getItinerarioFechaCreacion() {
        return itinerarioFechaCreacion;
    }

    public void setItinerarioFechaCreacion(String itinerarioFechaCreacion) {
        this.itinerarioFechaCreacion = itinerarioFechaCreacion;
    }

    public String getItinerarioEstatus() {
        return itinerarioEstatus;
    }

    public void setItinerarioEstatus(String itinerarioEstatus) {
        this.itinerarioEstatus = itinerarioEstatus;
    }

    public String getIglesiaId() {
        return iglesiaId;
    }

    public void setIglesiaId(String iglesiaId) {
        this.iglesiaId = iglesiaId;
    }
}
