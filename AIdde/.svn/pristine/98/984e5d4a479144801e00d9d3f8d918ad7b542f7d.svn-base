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
public class DepartamentoBD extends BaseModel {

    @Column
    @PrimaryKey
    String tIDDepartamento;

    @Column
    String tNombreDepartamento;

    @Column
    int iIDPais;


    public String gettIDDepartamento() {
        return tIDDepartamento;
    }

    public void settIDDepartamento(String tIDDepartamento) {
        this.tIDDepartamento = tIDDepartamento;
    }

    public String gettNombreDepartamento() {
        return tNombreDepartamento;
    }

    public void settNombreDepartamento(String tNombreDepartamento) {
        this.tNombreDepartamento = tNombreDepartamento;
    }

    public int getiIDPais() {
        return iIDPais;
    }

    public void setiIDPais(int iIDPais) {
        this.iIDPais = iIDPais;
    }
}
