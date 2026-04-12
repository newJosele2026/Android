package com.slt.dependenciajudicial.requests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nelsy Acuña on 15/11/2017.
 */

public class BasicRequestModel {

    @SerializedName("Authorize")
    @Expose
    private Boolean Authorize ;

    @SerializedName("Result")
    @Expose
    private Integer Result ;

    public Boolean getAuthorize() {
        return Authorize;
    }

    @SerializedName("Token")
    @Expose
    private String Token ;

    public void setAuthorize(Boolean authorize) {
        Authorize = authorize;
    }

    public Integer getResult() {
        return Result;
    }

    public void setResult(Integer result) {
        Result = result;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
