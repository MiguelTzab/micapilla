package com.migueltzabtah.micapilla.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by migueltzabtah on 19/08/18.
 */

public class Estado {
    @SerializedName("estados")
    @Expose
    private List<Estado_> estados = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public List<Estado_> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado_> estados) {
        this.estados = estados;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
