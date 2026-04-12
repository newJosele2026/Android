package com.slt.dependenciajudicial.app.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Sergio on 09/11/2017.
 */


@Database(name = DependenciaJudicialDataBase.DATABASE_NAME, version = DependenciaJudicialDataBase.DATABASE_VERSION)
public class DependenciaJudicialDataBase {

    public static final String DATABASE_NAME = "DependenciaJudicialDataBase";
    public static final int DATABASE_VERSION = 2;

}
