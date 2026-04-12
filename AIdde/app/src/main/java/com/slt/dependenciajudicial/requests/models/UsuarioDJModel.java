package com.slt.dependenciajudicial.requests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nelsy Acuña on 16/11/2017.
 */

public class UsuarioDJModel {

    @SerializedName("Authorize")
    @Expose
    private Boolean authorize;
    @SerializedName("Resultado")
    @Expose
    private Integer resultado;
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("iIDDJUsuario")
    @Expose
    private Integer iIDDJUsuario;
    @SerializedName("tNombreTipoDoc")
    @Expose
    private String tNombreTipoDoc;
    @SerializedName("tIDValorTipoDoc")
    @Expose
    private String tIDValorTipoDoc;
    @SerializedName("tNumDoc")
    @Expose
    private String tNumDoc;
    @SerializedName("tUsuario")
    @Expose
    private String tUsuario;
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
    @SerializedName("tCelular")
    @Expose
    private String tCelular;
    @SerializedName("tEmail")
    @Expose
    private String tEmail;
    @SerializedName("dtFechaExpedicionCedula")
    @Expose
    private String dtFechaExpedicionCedula;
    @SerializedName("tNumeroTarjetaProfesional")
    @Expose
    private String tNumeroTarjetaProfesional;
    @SerializedName("tNombreTipoUsuario")
    @Expose
    private String tNombreTipoUsuario;
    @SerializedName("tIDValorTipoUsuario")
    @Expose
    private String tIDValorTipoUsuario;
    @SerializedName("DivipolaModel")
    @Expose
    private List<DivipolaModel> divipolaModel = null;

    @SerializedName("bNotificar")
    @Expose
    private Boolean bNotificar;


    //Constructores
    public UsuarioDJModel() {
        this.authorize = false;
        this.resultado = 0;
        this.token = "";
        this.iIDDJUsuario = 0;
        this.tNombreTipoDoc = "";
        this.tIDValorTipoDoc = "";
        this.tNumDoc = "";
        this.tUsuario = "";
        this.tPrimerNombre = "";
        this.tSegundoNombre = "";
        this.tPrimerApellido = "";
        this.tSegundoApellido = "";
        this.tCelular = "";
        this.tEmail = "";
        this.dtFechaExpedicionCedula = "";
        this.tNumeroTarjetaProfesional = "";
        this.tNombreTipoUsuario = "";
        this.tIDValorTipoUsuario = "";
        this.divipolaModel = new ArrayList<>();
}


    //Get and Set
    public Boolean getAuthorize() {
        return authorize;
    }

    public void setAuthorize(Boolean authorize) {
        this.authorize = authorize;
    }

    public Integer getResultado() {
        return resultado;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIIDDJUsuario() {
        return iIDDJUsuario;
    }

    public void setIIDDJUsuario(Integer iIDDJUsuario) {
        this.iIDDJUsuario = iIDDJUsuario;
    }

    public String getTNombreTipoDoc() {
        return tNombreTipoDoc;
    }

    public void setTNombreTipoDoc(String tNombreTipoDoc) {
        this.tNombreTipoDoc = tNombreTipoDoc;
    }

    public String getTIDValorTipoDoc() {
        return tIDValorTipoDoc;
    }

    public void setTIDValorTipoDoc(String tIDValorTipoDoc) {
        this.tIDValorTipoDoc = tIDValorTipoDoc;
    }

    public String getTNumDoc() {
        return tNumDoc;
    }

    public void setTNumDoc(String tNumDoc) {
        this.tNumDoc = tNumDoc;
    }

    public String getTUsuario() {
        return tUsuario;
    }

    public void setTUsuario(String tUsuario) {
        this.tUsuario = tUsuario;
    }

    public String getTPrimerNombre() {
        return tPrimerNombre;
    }

    public void setTPrimerNombre(String tPrimerNombre) {
        this.tPrimerNombre = tPrimerNombre;
    }

    public String getTSegundoNombre() {
        return tSegundoNombre;
    }

    public void setTSegundoNombre(String tSegundoNombre) {
        this.tSegundoNombre = tSegundoNombre;
    }

    public String getTPrimerApellido() {
        return tPrimerApellido;
    }

    public void setTPrimerApellido(String tPrimerApellido) {
        this.tPrimerApellido = tPrimerApellido;
    }

    public String getTSegundoApellido() {
        return tSegundoApellido;
    }

    public void setTSegundoApellido(String tSegundoApellido) {
        this.tSegundoApellido = tSegundoApellido;
    }

    public String getTCelular() {
        return tCelular;
    }

    public void setTCelular(String tCelular) {
        this.tCelular = tCelular;
    }

    public String getTEmail() {
        return tEmail;
    }

    public void setTEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public String getDtFechaExpedicionCedula() {
        return dtFechaExpedicionCedula;
    }

    public void setDtFechaExpedicionCedula(String dtFechaExpedicionCedula) {
        this.dtFechaExpedicionCedula = dtFechaExpedicionCedula;
    }

    public String getTNumeroTarjetaProfesional() {
        return tNumeroTarjetaProfesional;
    }

    public void setTNumeroTarjetaProfesional(String tNumeroTarjetaProfesional) {
        this.tNumeroTarjetaProfesional = tNumeroTarjetaProfesional;
    }

    public String getTNombreTipoUsuario() {
        return tNombreTipoUsuario;
    }

    public void setTNombreTipoUsuario(String tNombreTipoUsuario) {
        this.tNombreTipoUsuario = tNombreTipoUsuario;
    }

    public String getTIDValorTipoUsuario() {
        return tIDValorTipoUsuario;
    }

    public void setTIDValorTipoUsuario(String tIDValorTipoUsuario) {
        this.tIDValorTipoUsuario = tIDValorTipoUsuario;
    }

    public List<DivipolaModel> getDivipolaModel() {
        return divipolaModel;
    }

    public void setDivipolaModel(List<DivipolaModel> divipolaModel) {
        this.divipolaModel = divipolaModel;
    }

    public Boolean getbNotificar() {
        return bNotificar;
    }

    public void setbNotificar(Boolean bNotificar) {
        this.bNotificar = bNotificar;
    }
}


