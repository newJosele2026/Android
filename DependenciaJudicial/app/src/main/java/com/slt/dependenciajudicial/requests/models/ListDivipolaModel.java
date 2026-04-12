package com.slt.dependenciajudicial.requests.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListDivipolaModel {

    @SerializedName("PaisModel")
    @Expose
    private List<PaisModel> paisModel = null;

    @SerializedName("DepartamentoModel")
    @Expose
    private List<DepartamentoModel> departamentoModel = null;


    @SerializedName("CiudadModel")
    @Expose
    private List<CiudadModel> ciudadModel = null;

    public List<PaisModel> getPaisModel() {
        return paisModel;
    }

    public void setPaisModel(List<PaisModel> paisModel) {
        this.paisModel = paisModel;
    }

    public List<DepartamentoModel> getDepartamentoModel() {
        return departamentoModel;
    }

    public void setDepartamentoModel(List<DepartamentoModel> departamentoModel) {
        this.departamentoModel = departamentoModel;
    }

    public List<CiudadModel> getCiudadModel() {
        return ciudadModel;
    }

    public void setCiudadModel(List<CiudadModel> ciudadModel) {
        this.ciudadModel = ciudadModel;
    }

}