package com.slt.dependenciajudicial.requests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nelsy Acuña on 16/11/2017.
 */

public class DivipolaModel {

    @SerializedName("iIDDIvipola")
    @Expose
    private Integer iIDDIvipola;
    @SerializedName("tNombreCiudad")
    @Expose
    private String tNombreCiudad;
    @SerializedName("tCodigoCiudad")
    @Expose
    private String tCodigoCiudad;

    public Integer getIIDDIvipola() {
        return iIDDIvipola;
    }

    public void setIIDDIvipola(Integer iIDDIvipola) {
        this.iIDDIvipola = iIDDIvipola;
    }

    public String getTNombreCiudad() {
        return tNombreCiudad;
    }

    public void setTNombreCiudad(String tNombreCiudad) {
        this.tNombreCiudad = tNombreCiudad;
    }

    public String getTCodigoCiudad() {
        return tCodigoCiudad;
    }

    public void setTCodigoCiudad(String tCodigoCiudad) {
        this.tCodigoCiudad = tCodigoCiudad;
    }

}
