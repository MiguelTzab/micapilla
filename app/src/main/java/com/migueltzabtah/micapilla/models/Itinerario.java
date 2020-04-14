package com.migueltzabtah.micapilla.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by migueltzabtah on 15/08/18.
 */

public class Itinerario {
    @SerializedName("itinerarios")
    @Expose
    private List<Itinerario_> itinerarios = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public List<Itinerario_> getItinerarios() {
        return itinerarios;
    }

    public void setItinerarios(List<Itinerario_> itinerarios) {
        this.itinerarios = itinerarios;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
