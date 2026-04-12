package com.slt.dependenciajudicial.requests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CiudadModel {

    @SerializedName("tIDMunicipio")
    @Expose
    private String tIDMunicipio;

    @SerializedName("tNombreMunicipio")
    @Expose
    private String tNombreMunicipio;

    @SerializedName("tIDDepto")
    @Expose
    private String tIDDepto;

    @SerializedName("iIDDivipola")
    @Expose
    private Integer iIDDivipola;


    public String getTIDMunicipio() {
        return tIDMunicipio;
    }

    public void setTIDMunicipio(String tIDMunicipio) {
        this.tIDMunicipio = tIDMunicipio;
    }

    public String getTNombreMunicipio() {
        return tNombreMunicipio;
    }

    public void setTNombreMunicipio(String tNombreMunicipio) {
        this.tNombreMunicipio = tNombreMunicipio;
    }

    public String getTIDDepto() {
        return tIDDepto;
    }

    public void setTIDDepto(String tIDDepto) {
        this.tIDDepto = tIDDepto;
    }

    public Integer getIIDDivipola() {
        return iIDDivipola;
    }

    public void setIIDDivipola(Integer iIDDivipola) {
        this.iIDDivipola = iIDDivipola;
    }

}