package com.migueltzabtah.micapilla.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by migueltzabtah on 14/08/18.
 */

public class Ciudad {

    @SerializedName("ciudades")
    @Expose
    private List<Ciudade> ciudades = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public List<Ciudade> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudade> ciudades) {
        this.ciudades = ciudades;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}