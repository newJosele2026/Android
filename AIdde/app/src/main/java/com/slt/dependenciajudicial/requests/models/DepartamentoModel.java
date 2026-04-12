package com.slt.dependenciajudicial.requests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepartamentoModel {

    @SerializedName("tIDDepto")
    @Expose
    private String tIDDepto;
    @SerializedName("tNombreDepto")
    @Expose
    private String tNombreDepto;
    @SerializedName("iIDPais")
    @Expose
    private Integer iIDPais;

    public String getTIDDepto() {
        return tIDDepto;
    }

    public void setTIDDepto(String tIDDepto) {
        this.tIDDepto = tIDDepto;
    }

    public String getTNombreDepto() {
        return tNombreDepto;
    }

    public void setTNombreDepto(String tNombreDepto) {
        this.tNombreDepto = tNombreDepto;
    }

    public Integer getIIDPais() {
        return iIDPais;
    }

    public void setIIDPais(Integer iIDPais) {
        this.iIDPais = iIDPais;
    }

}