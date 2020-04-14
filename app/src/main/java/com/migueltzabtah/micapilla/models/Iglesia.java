package com.migueltzabtah.micapilla.models;

/**
 * Created by migueltzabtah on 14/08/18.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Iglesia {

    @SerializedName("iglesias")
    @Expose
    private List<Iglesia_> iglesias = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public List<Iglesia_> getIglesias() {
        return iglesias;
    }

    public void setIglesias(List<Iglesia_> iglesias) {
        this.iglesias = iglesias;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}