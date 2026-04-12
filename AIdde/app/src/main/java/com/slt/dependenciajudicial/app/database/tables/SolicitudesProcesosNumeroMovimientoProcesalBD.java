package com.slt.dependenciajudicial.app.database.tables;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.slt.dependenciajudicial.app.database.DependenciaJudicialDataBase;

@Table(database = DependenciaJudicialDataBase.class)
public class SolicitudesProcesosNumeroMovimientoProcesalBD extends BaseModel {

    @Column
    @PrimaryKey
     int iDProcesoMovimientoProcesal;

    @Column
     int iDSolicitud;

    @Column
     int iDTipoSoporteTipo;

    @Column
     String tNombreDespacho;

    @Column
     String tNumeroProceso;

    @Column
     String tMunicipio;

    @Column
     String tEstructuraConceptualRelacionActuacion;

    public int getiDProcesoMovimientoProcesal() {
        return iDProcesoMovimientoProcesal;
    }

    public void setiDProcesoMovimientoProcesal(int iDProcesoMovimientoProcesal) {
        this.iDProcesoMovimientoProcesal = iDProcesoMovimientoProcesal;
    }

    public int getiDSolicitud() {
        return iDSolicitud;
    }

    public void setiDSolicitud(int iDSolicitud) {
        this.iDSolicitud = iDSolicitud;
    }

    public int getiDTipoSoporteTipo() {
        return iDTipoSoporteTipo;
    }

    public void setiDTipoSoporteTipo(int iDTipoSoporteTipo) {
        this.iDTipoSoporteTipo = iDTipoSoporteTipo;
    }

    public String gettNombreDespacho() {
        return tNombreDespacho;
    }

    public void settNombreDespacho(String tNombreDespacho) {
        this.tNombreDespacho = tNombreDespacho;
    }

    public String gettNumeroProceso() {
        return tNumeroProceso;
    }

    public void settNumeroProceso(String tNumeroProceso) {
        this.tNumeroProceso = tNumeroProceso;
    }

    public String gettMunicipio() {
        return tMunicipio;
    }

    public void settMunicipio(String tMunicipio) {
        this.tMunicipio = tMunicipio;
    }

    public String gettEstructuraConceptualRelacionActuacion() {
        return tEstructuraConceptualRelacionActuacion;
    }

    public void settEstructuraConceptualRelacionActuacion(String tEstructuraConceptualRelacionActuacion) {
        this.tEstructuraConceptualRelacionActuacion = tEstructuraConceptualRelacionActuacion;
    }
}
