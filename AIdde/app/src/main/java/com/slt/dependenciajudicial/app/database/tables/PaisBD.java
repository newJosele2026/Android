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
public class PaisBD  extends BaseModel {

    @Column
    @PrimaryKey
    int iIDPais;

    @Column
    String tNombrePais;

    public int getiIDPais() {
        return iIDPais;
    }

    public void setiIDPais(int iIDPais) {
        this.iIDPais = iIDPais;
    }

    public String gettNombrePais() {
        return tNombrePais;
    }

    public void settNombrePais(String tNombrePais) {
        this.tNombrePais = tNombrePais;
    }
}
