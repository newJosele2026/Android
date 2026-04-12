package com.slt.dependenciajudicial.views.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import com.google.firebase.iid.FirebaseInstanceId;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.slt.dependenciajudicial.BuildConfig;
import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.app.Preferences;
import com.slt.dependenciajudicial.app.database.tables.CiudadBD;
import com.slt.dependenciajudicial.app.database.tables.DepartamentoBD;
import com.slt.dependenciajudicial.app.database.tables.PaisBD;
import com.slt.dependenciajudicial.app.database.tables.UsuarioDJCiudadesBD;
import com.slt.dependenciajudicial.requests.SOService;
import com.slt.dependenciajudicial.requests.models.BasicRequestModel;
import com.slt.dependenciajudicial.requests.models.CiudadModel;
import com.slt.dependenciajudicial.requests.models.DepartamentoModel;
import com.slt.dependenciajudicial.requests.models.DivipolaModel;
import com.slt.dependenciajudicial.requests.models.ListDivipolaModel;
import com.slt.dependenciajudicial.requests.models.PaisModel;
import com.slt.dependenciajudicial.requests.models.UsuarioDJModel;
import com.slt.dependenciajudicial.requests.settings.ApiUtils;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.utils.Security;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.slt.dependenciajudicial.app.Preferences.savePreferences;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_CREAR_SOLICITUD;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.NAME_COLLECTION_PREFERENCES;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_CELULAR;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_CONFIGURACION_RED_MOVIL;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_CURRENT_SCREEN;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_EMAIL;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_FECHA_EXP_DOCUMENTO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_ID_TIPO_DOCUMENTO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_ID_TIPO_USUARIO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_IIDDJUSUARIO;
//import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_BVERSION;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_NOMBRE_USUARIO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_NOTIFICACIONES;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_NUMERO_DOCUMENTO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_PRIMER_APELLIDO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_PRIMER_NOMBRE;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_SEGUNDO_APELLIDO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_SEGUNDO_NOMBRE;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_TIPO_DOCUMENTO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_TIPO_USUARIO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_TOKEN;

public class ActivityLogin extends AppCompatActivity {

    private static final String LOG_ACTIVITY = "ActivityLogin";
    public static final String FILE_PERSISTENT_UPDATE = "FILE_PERSISTENT_UPDATE";

    //region Init Views

    public static final String EXTRA_IMAGE = "ActivityLogin:activity_login_imgv_prozess";



    @BindView(R.id.activity_login_btn_ingresar)
    Button btnIngresar;


    @BindView(R.id.activity_login_edt_email)
    TextInputEditText edtEmail;

    @BindView(R.id.activity_login_ly_edt_email)
    TextInputLayout tilEmail;


    @BindView(R.id.activity_login_edt_pass)
    TextInputEditText edtPassword;


    //endregion

    //region Init String


    @BindString(R.string.activity_login_str_requerido)
    String strActivityLoginRequerido;


    @BindString(R.string.general_sin_conexion)
    String strGeneralSinConexion;

    @BindString(R.string.general_sin_conexion_servidor)
    String strGeneralSinConexionServidor;

    @BindString(R.string.activity_login_str_progress_conten)
    String strActivityLoginProgressConten;

    @BindString(R.string.general_dialogo_titulo)
    String strDialogoTitulo;

    @BindString(R.string.general_dialogo_btn_ok)
    String strDialogoBtnOk;

    @BindString(R.string.activity_login_str_valido_email)
    String strValidoEmail;

    @BindString(R.string.activity_login_str_dialogo_cuenta_sin_validar)
    String strDialogoCuentaSinValidar;

    @BindString(R.string.activity_login_str_dialogo_error)
    String strDialogoError;

    @BindString(R.string.activity_login_str_progress_recuperacion)
    String strProgressRecuperando;


    @BindString(R.string.activity_login_str_dialogo_recuperar_exito)
    String strDialogoRecuperandoExito;

    @BindString(R.string.activity_login_str_dialogo_recuperar_error)
    String strDialogoRecuperandoError;


    //Dialog Update
    @BindString(R.string.dialog_update_titulo_actualizar)
    String strDialogoTituloActualizar;

    @BindString(R.string.dialog_update_content_actualizar)
    String strDialogoContenido;

    @BindString(R.string.dialog_update_positive_actualizar)
    String strDialogoBtnActualizar;

    @BindString(R.string.dialog_update_negative_actualizar)
    String strDialogoBtnCancelar;

    @BindString(R.string.dialog_update_play_store)
    String strLinkToPlayStore;

    @BindString(R.string.update_snackbar_content)
    String strSnackbarUpdate;


    //endregion

    @BindColor(R.color.windowBackground)
    int colorWindowBackground;


    private SOService apiService;
    private Activity activity;
    private MaterialDialog mdProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        activity = this;
        apiService = ApiUtils.getSOService();
        setupProgressDialog();
        setupEdtEmail();
    }




    private void setupEdtEmail() {

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                tilEmail.setError("");
                Boolean isEmail = DependenciaJudicialUtils.isEmailValid(edtEmail.getText().toString());
                if (!isEmail) {

                    tilEmail.setError(strValidoEmail);
                }


            }
        });
    }



    @OnClick(R.id.activity_login_btn_ingresar)
    public void btnIngresarOnClick(final View v) {

        if (DependenciaJudicialUtils.isConnected(ActivityLogin.this)) {

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            String strUsuario = edtEmail.getText().toString();
            String strPass = edtPassword.getText().toString();
            tilEmail.setError("");

            if (strUsuario.equals("") || strPass.equals("")) {

                Snackbar.make(v, strActivityLoginRequerido, Snackbar.LENGTH_SHORT).show();

            } else if (!DependenciaJudicialUtils.isEmailValid(strUsuario)) {

                tilEmail.setError(strValidoEmail);

            } else {

                //Encriptar datos

                strUsuario = Security.encodeString(strUsuario);
                strPass = Security.encodeString(strPass);

                showProgressDialog(strActivityLoginProgressConten);

                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                Log.e(LOG_ACTIVITY, "FCM  TOKEN: " + refreshedToken);

                apiService.Authentication(strUsuario, strPass, refreshedToken, BuildConfig.VERSION_NAME).enqueue(new Callback<UsuarioDJModel>() {
                    @Override
                    public void onResponse(Call<UsuarioDJModel> call, Response<UsuarioDJModel> response) {


                        if (response.isSuccessful()) {


                            switch (response.body().getResultado()) {

                                case 1:
                                    //Inicio sesion correctamente
                                    savePreferences(activity, SP_TOKEN, response.body().getToken());
                                    savePreferences(activity, SP_IIDDJUSUARIO, response.body().getIIDDJUsuario().toString());
                                    savePreferences(activity, SP_EMAIL, response.body().getTEmail());
                                    savePreferences(activity, SP_NOMBRE_USUARIO, response.body().getTUsuario());
                                    savePreferences(activity, SP_PRIMER_NOMBRE, response.body().getTPrimerNombre());
                                    savePreferences(activity, SP_PRIMER_APELLIDO, response.body().getTPrimerApellido());
                                    savePreferences(activity, SP_SEGUNDO_NOMBRE, response.body().getTSegundoNombre());
                                    savePreferences(activity, SP_SEGUNDO_APELLIDO, response.body().getTSegundoApellido());
                                    savePreferences(activity, SP_ID_TIPO_DOCUMENTO, response.body().getTIDValorTipoDoc());
                                    savePreferences(activity, SP_TIPO_DOCUMENTO, response.body().getTNombreTipoDoc());
                                    savePreferences(activity, SP_NUMERO_DOCUMENTO, response.body().getTNumDoc());
                                    savePreferences(activity, SP_FECHA_EXP_DOCUMENTO, response.body().getDtFechaExpedicionCedula());
                                    savePreferences(activity, SP_CELULAR, response.body().getTCelular());
                                    savePreferences(activity, SP_TIPO_USUARIO, response.body().getTNombreTipoUsuario());
                                    savePreferences(activity, SP_ID_TIPO_USUARIO, response.body().getTIDValorTipoUsuario());
                                    savePreferences(activity, SP_NOTIFICACIONES, response.body().getbNotificar() ? "1" : "0");
                                    //savePreferences(activity, SP_BVERSION, "1");

                                    if (Preferences.getPreferences(activity, SP_ID_TIPO_USUARIO).equals("1")) {
                                        //Pantalla de inicio para dependientes
                                        savePreferences(activity, SP_CURRENT_SCREEN, APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES);

                                    } else if (Preferences.getPreferences(activity, SP_ID_TIPO_USUARIO).equals("2")) {
                                        //Pantalla de inicio para solicitantes
                                        savePreferences(activity, SP_CURRENT_SCREEN, APP_SCREEN_FRAGMENT_CREAR_SOLICITUD);
                                    }


                                    List<DivipolaModel> listDivipolaModel = response.body().getDivipolaModel();

                                    //Insertar en la db Local
                                    for (DivipolaModel divipolaModel : listDivipolaModel) {

                                        UsuarioDJCiudadesBD usuarioDJCiudadesBD = new UsuarioDJCiudadesBD();
                                        usuarioDJCiudadesBD.setiIDDIvipola(divipolaModel.getIIDDIvipola());
                                        usuarioDJCiudadesBD.settNombreCiudad(divipolaModel.getTNombreCiudad());
                                        usuarioDJCiudadesBD.settCodigoCiudad(divipolaModel.getTCodigoCiudad());
                                        usuarioDJCiudadesBD.save();
                                    }
                                    dissProgressDialog();

                                    SharedPreferences sp =  getSharedPreferences(FILE_PERSISTENT_UPDATE, 0);
                                    final SharedPreferences.Editor editor = sp.edit();

                                    Log.d(LOG_ACTIVITY, Preferences.getPreferences(activity, "SP_BVERSION")+" La version del SP en login zzzzzzzzzzzzzzzzzzzzzzzzz");
                                    if (!( sp.getString("SP_BVERSION","0").equals("1"))) {

                                        new Delete().from(PaisBD.class).execute();
                                        new Delete().from(DepartamentoBD.class).execute();
                                        new Delete().from(CiudadBD.class).execute();

                                        apiService.GetListDivipola().enqueue(new Callback<ListDivipolaModel>() {
                                            @Override
                                            public void onResponse(Call<ListDivipolaModel> call, Response<ListDivipolaModel> response) {


                                                if (response.isSuccessful()) {


                                                    List<PaisModel> listaPaises = response.body().getPaisModel();
                                                    List<DepartamentoModel> listaDepto = response.body().getDepartamentoModel();
                                                    List<CiudadModel> listaCiudades = response.body().getCiudadModel();

                                                    for (PaisModel paises : listaPaises) {
                                                        PaisBD listPaisesDB = new PaisBD();
                                                        listPaisesDB.setiIDPais(paises.getIIDPais());
                                                        listPaisesDB.settNombrePais(paises.getTNombrePais());
                                                        listPaisesDB.async().insert();
                                                    }
                                                    for (DepartamentoModel departamentos : listaDepto) {
                                                        DepartamentoBD listDepartamentos = new DepartamentoBD();
                                                        listDepartamentos.settIDDepartamento(departamentos.getTIDDepto());
                                                        listDepartamentos.settNombreDepartamento(departamentos.getTNombreDepto());
                                                        listDepartamentos.setiIDPais(departamentos.getIIDPais());
                                                        listDepartamentos.async().insert();

                                                    }
                                                    for (CiudadModel ciudades : listaCiudades) {
                                                        CiudadBD listCiudadesDB = new CiudadBD();
                                                        listCiudadesDB.settIDCiudad(ciudades.getTIDMunicipio());
                                                        listCiudadesDB.settNombreCiudad(ciudades.getTNombreMunicipio());
                                                        listCiudadesDB.settIDDepartamento(ciudades.getTIDDepto());
                                                        listCiudadesDB.setiIDDivipola(ciudades.getIIDDivipola());
                                                        listCiudadesDB.async().insert();
                                                    }

                                                   // savePreferences(activity, "SP_BVERSION", "1");
                                                   // Log.d(LOG_ACTIVITY, Preferences.getPreferences(activity, "SP_BVERSION")+" La version del SP en login after insert zzzzzzzzzzzzzzzzzzzzzzzzz");


                                                    editor.putString("SP_BVERSION","1");
                                                    editor.apply();

                                                    Intent in = new Intent(ActivityLogin.this, ActivityHome.class);
                                                    startActivity(in);
                                                    finish();

                                                } else {
                                                    Snackbar.make(v, strGeneralSinConexion, Snackbar.LENGTH_SHORT);
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<ListDivipolaModel> call, Throwable t) {
                                                Snackbar.make(v, strGeneralSinConexion, Snackbar.LENGTH_SHORT);
                                               // savePreferences(activity, "SP_BVERSION", "0");
                                                editor.putString("SP_BVERSION","0");
                                                editor.apply();

                                            }
                                        });

                                    } else {
                                        Intent in = new Intent(ActivityLogin.this, ActivityHome.class);
                                        startActivity(in);
                                        finish();
                                    }

                                    break;
                                case 2:
                                    //Usuario correcto pero no esta validado

                                    dissProgressDialog();
                                    showAlertDialog(strDialogoCuentaSinValidar);

                                    break;
                                case 3:
                                    // El usuario no existe o esta mal

                                    dissProgressDialog();
                                    showAlertDialog(strDialogoError);
                                    break;
                                case 4:
                                    // El usuario ingresa pero no tiene una versión activa.
                                    savePreferences(activity, "SP_BVERSION", "0");

                                    final MaterialDialog dialog = new MaterialDialog.Builder(activity)
                                            .title(strDialogoTituloActualizar)
                                            .theme(Theme.LIGHT)
                                            .content(strDialogoContenido)
                                            .positiveText(strDialogoBtnActualizar)
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                                    //Intent que redirige a la tienda de google plays
                                                    intent.setData(Uri.parse(strLinkToPlayStore));
                                                    startActivity(intent);
                                                }
                                            })
                                            .negativeText(strDialogoBtnCancelar)
                                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                                    finish();
                                                }
                                            })
                                            .show();
                                    dissProgressDialog();
                                    break;
                            }


                        } else {

                            dissProgressDialog();
                            Log.d(LOG_ACTIVITY, "No isSuccessful");
                            Snackbar.make(v, strGeneralSinConexionServidor, Snackbar.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onFailure(Call<UsuarioDJModel> call, Throwable t) {
                        dissProgressDialog();
                        Snackbar.make(v, strGeneralSinConexionServidor, Snackbar.LENGTH_SHORT).show();
                        Log.d(LOG_ACTIVITY, "onFailure");

                    }
                });


            }

        } else {

            Snackbar.make(v, strGeneralSinConexion, Snackbar.LENGTH_SHORT).show();

        }


    }

    @OnClick(R.id.activity_login_txt_recuperar)
    public void txtRecuperarOnClick(View view) {

        //Snackbar.make(view, "Recuperar", Snackbar.LENGTH_SHORT).show();

        boolean wrapInScrollView = true;
        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(strDialogoTitulo)
                //  .backgroundColor(colorWindowBackground)
                // .backgroundColorAttr(colorWindowBackground)
                // .contentColor(colorWindowBackground)
                .theme(Theme.LIGHT)
                .customView(R.layout.dialog_recuperar_pass, wrapInScrollView)
                .show();

        Button btnRecuperar = (Button) dialog.findViewById(R.id.dialog_recuperar_pass_btn_recuperar_cuenta);
        final TextInputEditText edtEmailRecuperacion = (TextInputEditText) dialog.findViewById(R.id.dialog_recuperar_pass_edt_email);
        final TextInputLayout tilEamilRecuperacion = (TextInputLayout) dialog.findViewById(R.id.dialog_recuperar_pass_ly_edt_email);

        edtEmailRecuperacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                tilEamilRecuperacion.setError("");
                Boolean isEmail = DependenciaJudicialUtils.isEmailValid(edtEmailRecuperacion.getText().toString());
                if (!isEmail) {

                    tilEamilRecuperacion.setError(strValidoEmail);
                }


            }
        });


        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (DependenciaJudicialUtils.isConnected(activity)) {

                    String srtEmail = edtEmailRecuperacion.getText().toString();

                    if (DependenciaJudicialUtils.isEmailValid(srtEmail)) {
                        dialog.dismiss();

                        showProgressDialog(strProgressRecuperando);
                        apiService.RecuperaraPass(srtEmail, "", "").enqueue(new Callback<BasicRequestModel>() {
                            @Override
                            public void onResponse(Call<BasicRequestModel> call, Response<BasicRequestModel> response) {
                                dissProgressDialog();
                                if (response.isSuccessful()) {

                                    switch (response.body().getResult()) {

                                        case 1:
                                            showAlertDialog(strDialogoRecuperandoExito);

                                            break;
                                        case 2:
                                            showAlertDialog(strDialogoRecuperandoError);
                                            break;
                                    }

                                } else {

                                    Log.d(LOG_ACTIVITY, " RecuperaraPass  no isSuccessful");

                                    Snackbar.make(view, strGeneralSinConexionServidor, Snackbar.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onFailure(Call<BasicRequestModel> call, Throwable t) {
                                dissProgressDialog();
                                Snackbar.make(view, strGeneralSinConexionServidor, Snackbar.LENGTH_SHORT).show();
                                Log.d(LOG_ACTIVITY, " RecuperaraPass onFailure");
                            }
                        });


                    } else {
                        tilEamilRecuperacion.setError(strValidoEmail);
                    }


                } else {
                    dialog.dismiss();
                    Snackbar.make(view, strGeneralSinConexion, Snackbar.LENGTH_SHORT).show();

                }


            }
        });


    }




    @OnClick(R.id.activity_login_btn_registro)
    public void btnRegistroOnClick( View view) {


        Intent intent = new Intent(this, ActivityRegistro.class);
        startActivity(intent);

        }


    private void setupProgressDialog(){

        mdProgress = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .cancelable(false)
                .build();
    }

    private void showProgressDialog(String content) {
        mdProgress.setContent(content);
        mdProgress.show();
    }

    private void dissProgressDialog() {

        if (mdProgress != null && mdProgress.isShowing())
            mdProgress.dismiss();
    }

    public void showAlertDialog(String srtContenido) {

        new MaterialDialog.Builder(this)
                .title(strDialogoTitulo)
                .content(srtContenido)
                .positiveText(strDialogoBtnOk)
                .show();
    }



    @Override
    protected void onResume(){
        super.onResume();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences sp =  getSharedPreferences(FILE_PERSISTENT_UPDATE, 0);
        final SharedPreferences.Editor editor = sp.edit();
        //editor.putString("SB_VERSION", "");
        Log.d(LOG_ACTIVITY, sp.getString("SP_BVERSION","0"));



           //Log.d(LOG_ACTIVITY, Preferences.getPreferences(activity, "SP_BVERSION")+" La version del SP zzzzzzzzzzzzzzzzzzzzzzzzz");
        if (!( sp.getString("SP_BVERSION","0").equals("1"))) {

            showProgressDialog(strSnackbarUpdate);


            new Delete().from(PaisBD.class).execute();
            new Delete().from(DepartamentoBD.class).execute();
            new Delete().from(CiudadBD.class).execute();

            apiService.GetListDivipola().enqueue(new Callback<ListDivipolaModel>() {
                @Override
                public void onResponse(Call<ListDivipolaModel> call, Response<ListDivipolaModel> response) {


                    if (response.isSuccessful()) {


                        List<PaisModel> listaPaises = response.body().getPaisModel();
                        List<DepartamentoModel> listaDepto = response.body().getDepartamentoModel();
                        List<CiudadModel> listaCiudades = response.body().getCiudadModel();

                        for (PaisModel paises : listaPaises) {
                            PaisBD listPaisesDB = new PaisBD();
                            listPaisesDB.setiIDPais(paises.getIIDPais());
                            listPaisesDB.settNombrePais(paises.getTNombrePais());
                            listPaisesDB.async().insert();
                        }
                        for (DepartamentoModel departamentos : listaDepto) {
                            DepartamentoBD listDepartamentos = new DepartamentoBD();
                            listDepartamentos.settIDDepartamento(departamentos.getTIDDepto());
                            listDepartamentos.settNombreDepartamento(departamentos.getTNombreDepto());
                            listDepartamentos.setiIDPais(departamentos.getIIDPais());
                            listDepartamentos.async().insert();

                        }
                        for (CiudadModel ciudades : listaCiudades) {
                            CiudadBD listCiudadesDB = new CiudadBD();
                            listCiudadesDB.settIDCiudad(ciudades.getTIDMunicipio());
                            listCiudadesDB.settNombreCiudad(ciudades.getTNombreMunicipio());
                            listCiudadesDB.settIDDepartamento(ciudades.getTIDDepto());
                            listCiudadesDB.setiIDDivipola(ciudades.getIIDDivipola());
                            listCiudadesDB.async().insert();
                        }
                       // savePreferences(activity, "SP_BVERSION", "1");
                      //  Log.d(LOG_ACTIVITY, Preferences.getPreferences(activity, "SP_BVERSION")+" La version del SP zzzzzzzzzzzzzzzzzzzzzzzzz");
                        editor.putString("SP_BVERSION","1");
                        editor.apply();

                    } else {
                     //   savePreferences(activity, "SP_BVERSION", "0");
                    //    Log.d(LOG_ACTIVITY, Preferences.getPreferences(activity, "SP_BVERSION")+" La version del SP zzzzzzzzzzzzzzzzzzzzzzzzz");
                        editor.putString("SP_BVERSION","0");
                        editor.apply();
                    }

                }

                @Override
                public void onFailure(Call<ListDivipolaModel> call, Throwable t) {
                  //  savePreferences(activity, "SP_BVERSION", "0");
                 //   Log.d(LOG_ACTIVITY, Preferences.getPreferences(activity, "SP_BVERSION")+" La version del SP zzzzzzzzzzzzzzzzzzzzzzzzz");
                    editor.putString("SP_BVERSION","0");
                    editor.apply();
                }
            });
            dissProgressDialog();
        }
    }
}
