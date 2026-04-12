package com.slt.dependenciajudicial.requests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaisModel {

    @SerializedName("iIDPais")
    @Expose
    private Integer iIDPais;
    @SerializedName("tNombrePais")
    @Expose
    private String tNombrePais;

    public Integer getIIDPais() {
        return iIDPais;
    }

    public void setIIDPais(Integer iIDPais) {
        this.iIDPais = iIDPais;
    }

    public String getTNombrePais() {
        return tNombrePais;
    }

    public void setTNombrePais(String tNombrePais) {
        this.tNombrePais = tNombrePais;
    }

}