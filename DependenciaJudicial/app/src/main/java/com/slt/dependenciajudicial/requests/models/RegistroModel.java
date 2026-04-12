package com.slt.dependenciajudicial.requests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Nelsy Acuña on 15/11/2017.
 */

public class RegistroModel {

    @SerializedName("tEmail")
    @Expose
    private String tEmail;

    @SerializedName("tClave")
    @Expose
    private String tClave;

    @SerializedName("tPrimerNombre")
    @Expose
    private String tPrimerNombre;

    @SerializedName("tSegundoNombre")
    @Expose
    private String tSegundoNombre;

    @SerializedName("tPrimerApellido")
    @Expose
    private String tPrimerApellido;

    @SerializedName("tSegundoApellido")
    @Expose
    private String tSegundoApellido;

    @SerializedName("tIDValorTipoDoc")
    @Expose
    private String tIDValorTipoDoc;

    @SerializedName("tNumDoc")
    @Expose
    private String tNumDoc;

    @SerializedName("dtFechaExpedicionCedula")
    @Expose
    private Date dtFechaExpedicionCedula;

    @SerializedName("tNumeroTarjetaProfesional")
    @Expose
    private String tNumeroTarjetaProfesional;

    @SerializedName("tCelular")
    @Expose
    private String tCelular;

    @SerializedName("listDivipola")
    @Expose
    private List<Integer> listDivipola;


    public String gettEmail() {
        return tEmail;
    }

    public void settEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public String gettClave() {
        return tClave;
    }

    public void settClave(String tClave) {
        this.tClave = tClave;
    }

    public String gettPrimerNombre() {
        return tPrimerNombre;
    }

    public void settPrimerNombre(String tPrimerNombre) {
        this.tPrimerNombre = tPrimerNombre;
    }

    public String gettSegundoNombre() {
        return tSegundoNombre;
    }

    public void settSegundoNombre(String tSegundoNombre) {
        this.tSegundoNombre = tSegundoNombre;
    }

    public String gettPrimerApellido() {
        return tPrimerApellido;
    }

    public void settPrimerApellido(String tPrimerApellido) {
        this.tPrimerApellido = tPrimerApellido;
    }

    public String gettSegundoApellido() {
        return tSegundoApellido;
    }

    public void settSegundoApellido(String tSegundoApellido) {
        this.tSegundoApellido = tSegundoApellido;
    }

    public String gettIDValorTipoDoc() {
        return tIDValorTipoDoc;
    }

    public void settIDValorTipoDoc(String tIDValorTipoDoc) {
        this.tIDValorTipoDoc = tIDValorTipoDoc;
    }

    public String gettNumDoc() {
        return tNumDoc;
    }

    public void settNumDoc(String tNumDoc) {
        this.tNumDoc = tNumDoc;
    }

    public Date getDtFechaExpedicionCedula() {
        return dtFechaExpedicionCedula;
    }

    public void setDtFechaExpedicionCedula(Date dtFechaExpedicionCedula) {
        this.dtFechaExpedicionCedula = dtFechaExpedicionCedula;
    }

    public String gettNumeroTarjetaProfesional() {
        return tNumeroTarjetaProfesional;
    }

    public void settNumeroTarjetaProfesional(String tNumeroTarjetaProfesional) {
        this.tNumeroTarjetaProfesional = tNumeroTarjetaProfesional;
    }

    public String gettCelular() {
        return tCelular;
    }

    public void settCelular(String tCelular) {
        this.tCelular = tCelular;
    }

    public List<Integer> getListDivipola() {
        return listDivipola;
    }

    public void setListDivipola(List<Integer> listDivipola) {
        this.listDivipola = listDivipola;
    }
}
