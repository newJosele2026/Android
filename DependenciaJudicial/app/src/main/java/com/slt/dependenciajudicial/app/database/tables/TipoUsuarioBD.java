package com.slt.dependenciajudicial.app.database.tables;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.slt.dependenciajudicial.app.database.DependenciaJudicialDataBase;

/**
 * Created by Nelsy Acuña on 17/11/2017.
 */

@Table(database = DependenciaJudicialDataBase.class)
public class TipoUsuarioBD extends BaseModel {

    @Column
    @PrimaryKey
    Integer iIDTipoUsuario;

    @Column
    String tNombreTipoUsuario;


    public Integer getiIDTipoUsuario() {
        return iIDTipoUsuario;
    }

    public void setiIDTipoUsuario(Integer iIDTipoUsuario) {
        this.iIDTipoUsuario = iIDTipoUsuario;
    }

    public String gettNombreTipoUsuario() {
        return tNombreTipoUsuario;
    }

    public void settNombreTipoUsuario(String tNombreTipoUsuario) {
        this.tNombreTipoUsuario = tNombreTipoUsuario;
    }
}
