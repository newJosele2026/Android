package com.slt.dependenciajudicial.app.database.tables;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.slt.dependenciajudicial.app.database.DependenciaJudicialDataBase;

/**
 * Created by Nelsy Acuña on 16/11/2017.
 */

@Table(database = DependenciaJudicialDataBase.class)
public class UsuarioDJCiudadesBD extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    long iIDUsuarioDJCiudadesBD;

    @Column
    Integer iIDDIvipola;

    @Column
    String tNombreCiudad;

    @Column
    String tCodigoCiudad;


    public long getiIDUsuarioDJCiudadesBD() {
        return iIDUsuarioDJCiudadesBD;
    }

    public void setiIDUsuarioDJCiudadesBD(long iIDUsuarioDJCiudadesBD) {
        this.iIDUsuarioDJCiudadesBD = iIDUsuarioDJCiudadesBD;
    }

    public Integer getiIDDIvipola() {
        return iIDDIvipola;
    }

    public void setiIDDIvipola(Integer iIDDIvipola) {
        this.iIDDIvipola = iIDDIvipola;
    }

    public String gettNombreCiudad() {
        return tNombreCiudad;
    }

    public void settNombreCiudad(String tNombreCiudad) {
        this.tNombreCiudad = tNombreCiudad;
    }

    public String gettCodigoCiudad() {
        return tCodigoCiudad;
    }

    public void settCodigoCiudad(String tCodigoCiudad) {
        this.tCodigoCiudad = tCodigoCiudad;
    }
}
