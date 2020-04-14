package com.migueltzabtah.micapilla.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by migueltzabtah on 15/08/18.
 */

public class Usuario {

    @SerializedName("eventos")
    @Expose
    private List<Evento_> eventos = null;
    @SerializedName("usuarios")
    @Expose
    private Usuario_ usuarios;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public List<Evento_> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento_> eventos) {
        this.eventos = eventos;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Usuario_ getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuario_ usuarios) {
        this.usuarios = usuarios;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
