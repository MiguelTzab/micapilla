package com.migueltzabtah.micapilla.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Evento {

    @SerializedName("eventos")
    @Expose
    private List<Evento_> eventos = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

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

}