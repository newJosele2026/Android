package com.slt.dependenciajudicial.requests.models;

/**
 * Created by Nelsy Acuña on 06/12/2017.
 */

public class AceptacionModel {

    private String iIDDJSolicitudEnc;
    private String iTiempoServicioEnc;
    private String tiIDValorUnidadTiempoEnc;


    public String getiIDDJSolicitudEnc() {
        return iIDDJSolicitudEnc;
    }

    public void setiIDDJSolicitudEnc(String iIDDJSolicitudEnc) {
        this.iIDDJSolicitudEnc = iIDDJSolicitudEnc;
    }

    public String getiTiempoServicioEnc() {
        return iTiempoServicioEnc;
    }

    public void setiTiempoServicioEnc(String iTiempoServicioEnc) {
        this.iTiempoServicioEnc = iTiempoServicioEnc;
    }

    public String getTiIDValorUnidadTiempoEnc() {
        return tiIDValorUnidadTiempoEnc;
    }

    public void setTiIDValorUnidadTiempoEnc(String tiIDValorUnidadTiempoEnc) {
        this.tiIDValorUnidadTiempoEnc = tiIDValorUnidadTiempoEnc;
    }
}
