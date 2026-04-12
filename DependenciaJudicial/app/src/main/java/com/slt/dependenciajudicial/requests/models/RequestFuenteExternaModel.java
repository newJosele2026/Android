package com.slt.dependenciajudicial.requests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nelsy Acuña on 07/03/2018.
 */

public class RequestFuenteExternaModel {


    @SerializedName("listFuenteExternaModel")
    @Expose
    private List<FuenteExternaModel> listFuenteExternaModel = null;
    @SerializedName("Authorize")
    @Expose
    private Boolean authorize;
    @SerializedName("Result")
    @Expose
    private Integer result;
    @SerializedName("Token")
    @Expose
    private String token;

    public RequestFuenteExternaModel(List<FuenteExternaModel> listFuenteExternaModel, Boolean authorize, Integer result, String token) {
        this.listFuenteExternaModel = listFuenteExternaModel;
        this.authorize = authorize;
        this.result = result;
        this.token = token;
    }

    //region Get and Set

    public List<FuenteExternaModel> getListFuenteExternaModel() {
        return listFuenteExternaModel;
    }

    public void setListFuenteExternaModel(List<FuenteExternaModel> listFuenteExternaModel) {
        this.listFuenteExternaModel = listFuenteExternaModel;
    }

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
    //endregion


}
