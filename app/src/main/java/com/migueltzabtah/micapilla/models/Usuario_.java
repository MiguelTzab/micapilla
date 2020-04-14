package com.migueltzabtah.micapilla.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by migueltzabtah on 15/08/18.
 */

public class Usuario_ {
    @SerializedName("usuario_id")
    @Expose
    private String usuarioId;
    @SerializedName("usuario_nombre")
    @Expose
    private String usuarioNombre;
    @SerializedName("usuario_email")
    @Expose
    private String usuarioEmail;
    @SerializedName("usuario_pass")
    @Expose
    private String usuarioPass;
    @SerializedName("usuario_fechaRegistro")
    @Expose
    private String usuarioFechaRegistro;

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuarioPass() {
        return usuarioPass;
    }

    public void setUsuarioPass(String usuarioPass) {
        this.usuarioPass = usuarioPass;
    }

    public String getUsuarioFechaRegistro() {
        return usuarioFechaRegistro;
    }

    public void setUsuarioFechaRegistro(String usuarioFechaRegistro) {
        this.usuarioFechaRegistro = usuarioFechaRegistro;
    }

}
