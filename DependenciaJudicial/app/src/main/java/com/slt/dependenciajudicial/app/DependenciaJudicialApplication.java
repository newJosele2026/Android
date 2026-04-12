package com.slt.dependenciajudicial.app;

import android.app.Application;
//import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;
import com.slt.dependenciajudicial.app.database.DependenciaJudicialDataBase;
import com.slt.dependenciajudicial.app.database.tables.CiudadBD;
import com.slt.dependenciajudicial.app.database.tables.DepartamentoBD;
import com.slt.dependenciajudicial.app.database.tables.PaisBD;
import com.slt.dependenciajudicial.app.database.tables.TipoIdentificacionBD;
import com.slt.dependenciajudicial.app.database.tables.TipoUsuarioBD;
import com.slt.dependenciajudicial.utils.FileUtils;

import static com.slt.dependenciajudicial.app.database.BDUtils.QUERY_INIT_DEPARTAMENTOBD;
import static com.slt.dependenciajudicial.app.database.BDUtils.QUERY_INIT_MUNICIPIOBD1;
import static com.slt.dependenciajudicial.app.database.BDUtils.QUERY_INIT_MUNICIPIOBD2;
import static com.slt.dependenciajudicial.app.database.BDUtils.QUERY_INIT_MUNICIPIOBD3;
import static com.slt.dependenciajudicial.app.database.BDUtils.QUERY_INIT_PAISBD;
import static com.slt.dependenciajudicial.app.database.BDUtils.QUERY_INIT_TIPOIDENTIFICACIONBD;
import static com.slt.dependenciajudicial.app.database.BDUtils.QUERY_INIT_TIPO_USUARIO;


/**
 * Created by Sergio on 10/11/2017.
 * Modifid by Herman José on 28/10/2018.
 */

public class DependenciaJudicialApplication extends Application {
//public class DependenciaJudicialApplication extends MultiDexApplication {

    String LOG_ACTIVITY="Application";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_ACTIVITY,"init DependenciaJudicialApplication");

        //Create Folder
        FileUtils.folderApp();
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V); // set to verbose logging
        //Init DbFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowManager.getDatabase(DependenciaJudicialDataBase.class).reset();

        //region DbFlow Tables Init Data
        //Tipo Identificacion
        new Delete().from(TipoIdentificacionBD.class).execute();
        StringQuery<TipoIdentificacionBD> stringQueryTI = new StringQuery<>(TipoIdentificacionBD.class, QUERY_INIT_TIPOIDENTIFICACIONBD);
        stringQueryTI.executeInsert();
/*
        //Pais
        new Delete().from(PaisBD.class).execute();
        StringQuery<PaisBD> stringQueryP = new StringQuery<>(PaisBD.class, QUERY_INIT_PAISBD);
        //stringQueryP.query()
        stringQueryP.executeInsert();

        //Departamento
        new Delete().from(DepartamentoBD.class).execute();
        StringQuery<DepartamentoBD> stringQueryD = new StringQuery<>(DepartamentoBD.class, QUERY_INIT_DEPARTAMENTOBD);
        stringQueryD.executeInsert();

        //Municipio1
        new Delete().from(CiudadBD.class).execute();
        StringQuery<CiudadBD> stringQueryM1 = new StringQuery<>(CiudadBD.class, QUERY_INIT_MUNICIPIOBD1);
        stringQueryM1.executeInsert();

        //Municipio2
        StringQuery<CiudadBD> stringQueryM2 = new StringQuery<>(CiudadBD.class, QUERY_INIT_MUNICIPIOBD2);
        stringQueryM2.executeInsert();

        //Municipio3
        StringQuery<CiudadBD> stringQueryM3 = new StringQuery<>(CiudadBD.class, QUERY_INIT_MUNICIPIOBD3);
        stringQueryM3.executeInsert();
*/
        //usuario
        new Delete().from(TipoUsuarioBD.class).execute();
        StringQuery<TipoUsuarioBD> stringQueryTU = new StringQuery<>(TipoUsuarioBD.class, QUERY_INIT_TIPO_USUARIO);
        stringQueryTU.executeInsert();
        //endregion

        Log.d(LOG_ACTIVITY,"finish DependenciaJudicialApplication");
        FlowManager.getDatabase(DependenciaJudicialDataBase.class).close();

        /*FlowManager.getDatabase(DependenciaJudicialDataBase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<User>() {
                            @Override
                            public void processModel(User user) {
                                // do work here -- i.e. user.delete() or user.update()
                                user.save();
                            }
                        }).addAll(users).build())  // add elements (can also handle multiple)
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {

                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {

                    }
                }).build().execute();*/
        FlowManager.init(new FlowConfig.Builder(this).build());

    }
}
