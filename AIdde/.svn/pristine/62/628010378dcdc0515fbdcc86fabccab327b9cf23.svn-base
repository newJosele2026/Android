package com.slt.dependenciajudicial.app.database.tables;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.slt.dependenciajudicial.app.database.DependenciaJudicialDataBase;

/**
 * Created by Sergio on 12/11/17.
 */

@Table(database = DependenciaJudicialDataBase.class)
public class TipoIdentificacionBD extends BaseModel {

    @Column
    @PrimaryKey
    int iIDTipoIdentificacion;

    @Column
    String tNombreTipoIdentificacion;



    public int getiIDTipoIdentificacion() {
        return iIDTipoIdentificacion;
    }

    public void setiIDTipoIdentificacion(int iIDTipoIdentificacion) {
        this.iIDTipoIdentificacion = iIDTipoIdentificacion;
    }

    public String gettNombreTipoIdentificacion() {
        return tNombreTipoIdentificacion;
    }

    public void settNombreTipoIdentificacion(String tNombreTipoIdentificacion) {
        this.tNombreTipoIdentificacion = tNombreTipoIdentificacion;
    }
}
