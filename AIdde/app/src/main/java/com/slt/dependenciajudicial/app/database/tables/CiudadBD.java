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
public class CiudadBD extends BaseModel  {

    @Column
    @PrimaryKey
    String tIDCiudad;

    @Column
    String tNombreCiudad;

    @Column
    String tIDDepartamento;

    @Column
    int iIDDivipola;

    public String gettIDCiudad() {
        return tIDCiudad;
    }

    public void settIDCiudad(String tIDCiudad) {
        this.tIDCiudad = tIDCiudad;
    }

    public String gettNombreCiudad() {
        return tNombreCiudad;
    }

    public void settNombreCiudad(String tNombreCiudad) {
        this.tNombreCiudad = tNombreCiudad;
    }

    public String gettIDDepartamento() {
        return tIDDepartamento;
    }

    public void settIDDepartamento(String tIDDepartamento) {
        this.tIDDepartamento = tIDDepartamento;
    }

    public int getiIDDivipola() {
        return iIDDivipola;
    }

    public void setiIDDivipola(int iIDDivipola) {
        this.iIDDivipola = iIDDivipola;
    }
}
