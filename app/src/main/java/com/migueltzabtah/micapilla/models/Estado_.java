package com.migueltzabtah.micapilla.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by migueltzabtah on 19/08/18.
 */

public class Estado_ {
    @SerializedName("estado_id")
    @Expose
    private String estadoId;
    @SerializedName("estado_nombre")
    @Expose
    private String estadoNombre;

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getEstadoNombre() {
        return estadoNombre;
    }

    public void setEstadoNombre(String estadoNombre) {
        this.estadoNombre = estadoNombre;
    }

    @Override
    public String toString() {
        return estadoNombre;
    }
}
