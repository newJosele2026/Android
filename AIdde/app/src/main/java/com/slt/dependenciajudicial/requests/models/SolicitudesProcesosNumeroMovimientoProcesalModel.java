package com.slt.dependenciajudicial.requests.models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SolicitudesProcesosNumeroMovimientoProcesalModel {

    @SerializedName("iDProcesoMovimientoProcesal")
    @Expose
    private Integer iDProcesoMovimientoProcesal;

    @SerializedName("iDSolicitud")
    @Expose
    private Integer iDSolicitud;

    @SerializedName("iDTipoSoporteTipo")
    @Expose
    private Integer iIDTipoSoporteTipo;

    @SerializedName("tNombreDespacho")
    @Expose
    private String tNombreDespacho;

    @SerializedName("tNumeroProceso")
    @Expose
    private String tNumeroProceso;

    @SerializedName("tMunicipio")
    @Expose
    private String tMunicipio;

    @SerializedName("tEstructuraConceptualRelacionActuacion")
    @Expose
    private String tEstructuraConceptualRelacionActuacion;



    public Integer getiDProcesoMovimientoProcesal() {
        return iDProcesoMovimientoProcesal;
    }

    public void setiDProcesoMovimientoProcesal(Integer iDProcesoMovimientoProcesal) {
        this.iDProcesoMovimientoProcesal = iDProcesoMovimientoProcesal;
    }

    public Integer getiDSolicitud() {
        return iDSolicitud;
    }

    public void setiDSolicitud(Integer iDSolicitud) {
        this.iDSolicitud = iDSolicitud;
    }

    public Integer getiIDTipoSoporteTipo() {
        return iIDTipoSoporteTipo;
    }

    public void setiIDTipoSoporteTipo(Integer iIDTipoSoporteTipo) {
        this.iIDTipoSoporteTipo = iIDTipoSoporteTipo;
    }

    public String gettNombreDespacho() {
        return tNombreDespacho;
    }

    public void settNombreDespacho(String tNombreDespacho) {
        this.tNombreDespacho = tNombreDespacho;
    }

    public String gettMunicipio() {
        return tMunicipio;
    }

    public void settMunicipio(String tMunicipio) {
        this.tMunicipio = tMunicipio;
    }

    public String gettNumeroProceso() {
        return tNumeroProceso;
    }

    public void settNumeroProceso(String tNumeroProceso) {
        this.tNumeroProceso = tNumeroProceso;
    }

    public String gettEstructuraConceptualRelacionActuacion() {
        return tEstructuraConceptualRelacionActuacion;
    }

    public void settEstructuraConceptualRelacionActuacion(String tEstructuraConceptualRelacionActuacion) {
        this.tEstructuraConceptualRelacionActuacion = tEstructuraConceptualRelacionActuacion;
    }
}
