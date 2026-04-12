package com.slt.dependenciajudicial.requests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nelsy Acuña on 28/11/2017.
 */

public class RequestSolicitudModel {

    @SerializedName("Authorize")
    @Expose
    private Boolean authorize;

    @SerializedName("Result")
    @Expose
    private Integer result;

    @SerializedName("Token")
    @Expose
    private String token;

    @SerializedName("listSolicitudModel")
    @Expose
    private List<SolicitudModel> listSolicitudModel = null;

    @SerializedName("listProcesalNumber")
    @Expose
    private List<SolicitudesProcesosNumeroMovimientoProcesalModel> listProcesalNumber=null;

    public Boolean getAuthorize() {
        return authorize;
    }

    public void setAuthorize(Boolean authorize) {
        this.authorize = authorize;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<SolicitudModel> getListSolicitudModel() {
        return listSolicitudModel;
    }

    public void setListSolicitudModel(List<SolicitudModel> listSolicitudModel) {
        this.listSolicitudModel = listSolicitudModel;
    }

    public List<SolicitudesProcesosNumeroMovimientoProcesalModel> getListProcesalNumber() {
        return listProcesalNumber;
    }

    public void setListProcesalNumber(List<SolicitudesProcesosNumeroMovimientoProcesalModel> listProcesalNumber) {
        this.listProcesalNumber = listProcesalNumber;
    }
}
