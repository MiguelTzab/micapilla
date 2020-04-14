package com.migueltzabtah.micapilla.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by migueltzabtah on 14/08/18.
 */

public class Ciudade {

    @SerializedName("ciudad_id")
    @Expose
    private String ciudadId;
    @SerializedName("ciudad_nombre")
    @Expose
    private String ciudadNombre;
    @SerializedName("ciudad_estado")
    @Expose
    private String ciudadEstado;
    @SerializedName("cp")
    @Expose
    private String cp;

    public String getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(String ciudadId) {
        this.ciudadId = ciudadId;
    }

    public String getCiudadNombre() {
        return ciudadNombre;
    }

    public void setCiudadNombre(String ciudadNombre) {
        this.ciudadNombre = ciudadNombre;
    }

    public String getCiudadEstado() {
        return ciudadEstado;
    }

    public void setCiudadEstado(String ciudadEstado) {
        this.ciudadEstado = ciudadEstado;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    @Override
    public String toString() {
        return ciudadNombre;
    }

}
