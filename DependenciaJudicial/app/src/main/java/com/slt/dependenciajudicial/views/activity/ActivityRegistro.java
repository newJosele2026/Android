package com.slt.dependenciajudicial.views.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.app.database.tables.CiudadBD;
import com.slt.dependenciajudicial.app.database.tables.CiudadBD_Table;
import com.slt.dependenciajudicial.app.database.tables.DepartamentoBD;
import com.slt.dependenciajudicial.app.database.tables.DepartamentoBD_Table;
import com.slt.dependenciajudicial.app.database.tables.PaisBD;
import com.slt.dependenciajudicial.app.database.tables.TipoIdentificacionBD;
import com.slt.dependenciajudicial.requests.SOService;
import com.slt.dependenciajudicial.requests.models.BasicRequestModel;
import com.slt.dependenciajudicial.requests.models.RegistroModel;
import com.slt.dependenciajudicial.requests.settings.ApiUtils;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.utils.Security;
import com.slt.dependenciajudicial.views.adapters.AdapterCiudad;
import com.slt.dependenciajudicial.views.adapters.AdapterDepartamento;
import com.slt.dependenciajudicial.views.adapters.AdapterPais;
import com.slt.dependenciajudicial.views.adapters.AdapterTipoIdentificacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegistro extends AppCompatActivity {


    private static final String LOG_ACTIVITY = "ActivityRegistro";

    //region Init Views

    @BindView(R.id.activity_registro_edt_tarjeta_profesional)
    TextInputEditText edtTarjetaProfesional;


    @BindView(R.id.activity_registro_ly_edt_celular)
    TextInputLayout tilCelular;

    @BindView(R.id.activity_registro_edt_celular)
    TextInputEditText edtCelular;

    @BindView(R.id.activity_registro_edt_fecha_exp_doc)
    TextInputEditText edtFechaExpDoc;

    @BindView(R.id.activity_registro_nsv_container)
    NestedScrollView nsvContainer;

    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    @BindView(R.id.activity_registro_ly_edt_email)
    TextInputLayout tilEmail;

    @BindView(R.id.activity_registro_edt_email)
    TextInputEditText edtEmail;


    @BindView(R.id.activity_registro_ly_edt_identificacion)
    TextInputLayout tilIdentificacion;

    @BindView(R.id.activity_registro_edt_identificacion)
    TextInputEditText edtIdentificacion;

    @BindView(R.id.activity_registro_ly_edt_pass)
    TextInputLayout tilPass;

    @BindView(R.id.activity_registro_edt_pass)
    TextInputEditText edtPass;

    @BindView(R.id.activity_registro_ly_edt_primer_nombre)
    TextInputLayout tilPrimerNombre;

    @BindView(R.id.activity_registro_edt_primer_nombre)
    TextInputEditText edtPrimerNombre;

    @BindView(R.id.activity_registro_edt_segundo_nombre)
    TextInputEditText edtSegundoNombre;

    @BindView(R.id.activity_registro_ly_edt_primer_apellido)
    TextInputLayout tilPrimerApellido;

    @BindView(R.id.activity_registro_edt_primer_apellido)
    TextInputEditText edtPrimerApellido;

    @BindView(R.id.activity_registro_edt_segundo_apellido)
    TextInputEditText edtSegundoApellido;

    @BindView(R.id.activity_registro_ddl_identificacion)
    Spinner ddlTipoIdentificacion;

    @BindView(R.id.activity_registro_ddl_pais)
    Spinner ddlPais;

    @BindView(R.id.activity_registro_ddl_departamento)
    Spinner ddlDepartamento;

    @BindView(R.id.activity_registro_ddl_ciudad)
    Spinner ddlCiudad;


    @BindView(R.id.activity_registro_chips_input)
    ChipsInput chipsInput;

    @BindView(R.id.activity_registro_btn_add_chip)
    Button btnAddChip;

    //endregion

    //region Init String
    @BindString(R.string.activity_registro_str_bar_title)
    String strBarTitle;


    @BindString(R.string.general_sin_conexion_servidor)
    String strGeneralSinConexionServidor;

    @BindString(R.string.activity_registro_str_obligatorio_email)
    String strObligatorioEmail;

    @BindString(R.string.activity_registro_str_obligatorio_pass)
    String strObligatorioPass;

    @BindString(R.string.activity_registro_str_obligatorio_primer_nombre)
    String strObligatorioPrimerNombre;

    @BindString(R.string.activity_registro_str_obligatorio_primer_apellido)
    String strObligatorioPrimerApellido;

    @BindString(R.string.activity_registro_str_obligatorio_identificacion)
    String strObligatorioIdentificacion;

    @BindString(R.string.activity_registro_str_valido_email)
    String strValidoEmail;

    @BindString(R.string.activity_registro_str_valido_pass)
    String strValidoPass;

    @BindString(R.string.general_dialogo_titulo)
    String strDialogoTitulo;

    @BindString(R.string.activity_registro_str_datos_obligatorios)
    String strDatosObligatorios;

    @BindString(R.string.activity_registro_str_sin_ciudad)
    String strSinCiudad;

    @BindString(R.string.general_dialogo_btn_ok)
    String strDialogoBtnOk;

    @BindString(R.string.activity_registro_str_obligatorio_celular)
    String strObligatorioCelular;

    @BindString(R.string.activity_registro_str_progress_registro)
    String strProgressRegistro;

    @BindString(R.string.general_sin_conexion)
    String strGeneralSinConexion;


    @BindString(R.string.activity_registro_str_dialogo_cuenta_creada)
    String strDialogoCuentaRegistrada;

    @BindString(R.string.activity_registro_str_dialogo_cuenta_sin_validar)
    String strDialogoCuentaValidaNoActiva;

    @BindString(R.string.activity_registro_str_dialogo_cuenta_validada)
    String strDialogoCuentaValida;

    //endregion

    private Activity activity;
    private MaterialDialog mdProgress;
    private SOService apiService;
    private Date dtFechaExpDocumento = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);


        activity = this;
        apiService = ApiUtils.getSOService();

        setupToolbar(strBarTitle, true);
        setupTipoIdentificacion();
        setupInitClearFocus();
        setupUbicacion();
        setupChip();
        setupDialogoFechaExpDoc();
        setupProgressDialog();

        setupValidacionesEdt();


    }

    private void setupValidacionesEdt() {

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

        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                tilPass.setError("");
                Boolean isEmail = DependenciaJudicialUtils.isPasswordValid(edtPass.getText().toString());
                if (!isEmail) {
                    tilPass.setError(strValidoPass);
                }

            }
        });

    }

    private void setupProgressDialog() {
        mdProgress = new MaterialDialog.Builder(this)
                .content(strProgressRegistro)
                .progress(true, 0)
                .cancelable(false)
                .build();

    }

    private void showProgressDialog() {

        mdProgress.show();
    }

    private void dissProgressDialog() {

        if (mdProgress != null && mdProgress.isShowing())
            mdProgress.dismiss();
    }

    private void setupDialogoFechaExpDoc() {

        edtFechaExpDoc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();


                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(ActivityRegistro.this,R.style.DependenciaJudicialDatePicker, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */

                        //  edtFechaExpDoc.setText(selectedday + "/" + (selectedmonth + 1) + "/" + selectedyear);

                        dtFechaExpDocumento = new Date(selectedyear + "/" + (selectedmonth + 1) + "/" + selectedday);

                        String strFecha = new SimpleDateFormat("dd/MM/yyyy").format(dtFechaExpDocumento);

                        edtFechaExpDoc.setText(strFecha);

                    }
                }, mYear, mMonth, mDay);

                mDatePicker.show();
            }
        });
    }

    private void setupInitClearFocus() {

        //Se agrega el evento touch al contenedor para que limpie el focus del container de los chips
        // ya que por default al agregar un chip este bloqueaba el scroll


        nsvContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if (chipsInput.hasFocus()) {
                    chipsInput.clearFocus();
                }


                return false;
            }
        });

    }

    private void setupChip() {

        chipsInput.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chip, int newSize) {
                // chip added
                // newSize is the size of the updated selected chip list


            }

            @Override
            public void onChipRemoved(ChipInterface chip, int newSize) {
                // chip removed
                // newSize is the size of the updated selected chip list
                if (chipsInput.getSelectedChipList().size() == 0) {
                    chipsInput.setVisibility(View.GONE);
                }

            }

            @Override
            public void onTextChanged(CharSequence text) {
                // text changed
            }
        });
    }

    private void setupUbicacion() {


        List<PaisBD> paisBDList = new Select().from(PaisBD.class).queryList();

        final List<DepartamentoBD> departamentoBDList = new Select()
                .from(DepartamentoBD.class)
                .where(DepartamentoBD_Table.iIDPais.eq(48))
                .queryList();


        List<CiudadBD> ciudadBDList = new Select()
                .from(CiudadBD.class)
                .where(CiudadBD_Table.tIDDepartamento.eq("11"))
                .queryList();

        AdapterPais adapterPais = new AdapterPais(this, R.layout.spinner_content, paisBDList);
        ddlPais.setAdapter(adapterPais);


        ddlPais.setEnabled(false);

        AdapterDepartamento adapterDepartamento = new AdapterDepartamento(this, R.layout.spinner_content, departamentoBDList);
        ddlDepartamento.setAdapter(adapterDepartamento);

        AdapterCiudad adapterCiudad = new AdapterCiudad(this, R.layout.spinner_content, ciudadBDList);
        ddlCiudad.setAdapter(adapterCiudad);


        ddlDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                DepartamentoBD departamentoBD = departamentoBDList.get(i);

                List<CiudadBD> ciudadBDList2 = new Select()
                        .from(CiudadBD.class)
                        .where(CiudadBD_Table.tIDDepartamento.eq(departamentoBD.gettIDDepartamento()))
                        .queryList();


                AdapterCiudad adapterCiudad2 = new AdapterCiudad(activity, R.layout.spinner_content, ciudadBDList2);
                ddlCiudad.setAdapter(adapterCiudad2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //

    }

    private void setupTipoIdentificacion() {

        List<TipoIdentificacionBD> identificacionBDList = new Select().from(TipoIdentificacionBD.class).queryList();

        AdapterTipoIdentificacion adapterTipoIdentificacion = new AdapterTipoIdentificacion(this, R.layout.spinner_content, identificacionBDList);
        ddlTipoIdentificacion.setAdapter(adapterTipoIdentificacion);


    }

    public void setupToolbar(String tittle, boolean upButton) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    @OnClick(R.id.activity_registro_btn_add_chip)
    public void btnAddChipOnClick(View v) {


        CiudadBD ciudadBD = (CiudadBD) ddlCiudad.getSelectedItem();

        chipsInput.addChip(ciudadBD.gettNombreCiudad(), "" + ciudadBD.getiIDDivipola());


        if (chipsInput.getSelectedChipList().size() != 0) {
            chipsInput.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.activity_registro_btn_crear_cuenta)
    public void btnCrearCuentaOnClick(final View v) {


        if (DependenciaJudicialUtils.isConnected(activity)) {

            String strEmail = edtEmail.getText().toString();

            String strCelular = edtCelular.getText().toString();

            String strPass = edtPass.getText().toString();
            String strPrimerNombre = edtPrimerNombre.getText().toString();
            String strSegundoNombre = edtSegundoNombre.getText().toString();
            String strPrimerApellido = edtPrimerApellido.getText().toString();
            String strSegundoApellido = edtSegundoApellido.getText().toString();

            String strIdentificacion = edtIdentificacion.getText().toString();

            String strFechaExpDoc = edtFechaExpDoc.getText().toString();
            String strTarjetaProfesional = edtTarjetaProfesional.getText().toString();

            Log.d(LOG_ACTIVITY, "dtFechaExp()" + (edtFechaExpDoc != null ? edtFechaExpDoc : " Fecha Nula"));

            //region validar campos obligatorios
            tilEmail.setError("");
            tilPass.setError("");
            tilPrimerNombre.setError("");
            tilPrimerApellido.setError("");
            tilCelular.setError("");

            if (strCelular.equals("")) {
                tilCelular.setError(strObligatorioCelular);
            }


            if (strEmail.equals("")) {
                tilEmail.setError(strObligatorioEmail);
            }

            if (strPass.equals("")) {
                tilPass.setError(strObligatorioPass);
            }

            if (strPrimerNombre.equals("")) {
                tilPrimerNombre.setError(strObligatorioPrimerNombre);
            }

            if (strPrimerApellido.equals("")) {
                tilPrimerApellido.setError(strObligatorioPrimerApellido);
            }

            if (strIdentificacion.equals("")) {
                tilIdentificacion.setError(strObligatorioIdentificacion);
            }

            //endregion

            if (!strCelular.equals("") && !strEmail.equals("") && !strPass.equals("") && !strPrimerNombre.equals("") && !strPrimerApellido.equals("") && !strIdentificacion.equals("")) {

                int iNumMunicipios = chipsInput.getSelectedChipList().size();

                if (iNumMunicipios != 0) {


                    if (!DependenciaJudicialUtils.isEmailValid(strEmail)) {

                        Log.d(LOG_ACTIVITY, "El correo electronico no es valido");

                        showAlertDialog(strValidoEmail);

                    } else if (!DependenciaJudicialUtils.isPasswordValid(strPass)) {

                        Log.d(LOG_ACTIVITY, "El password no es valido");
                        showAlertDialog(strValidoPass);

                    } else {

                        //insertar

                        RegistroModel registroModel = new RegistroModel();

                        registroModel.settEmail(Security.encodeString(strEmail));
                        registroModel.settClave(Security.convertMd5(strPass));
                        registroModel.settPrimerNombre(Security.encodeString(strPrimerNombre));
                        registroModel.settSegundoNombre(strSegundoNombre.equals("") ? "" : Security.encodeString(strSegundoNombre));
                        registroModel.settPrimerApellido(Security.encodeString(strPrimerApellido));
                        registroModel.settSegundoApellido(strSegundoApellido.equals("") ? "" : Security.encodeString(strSegundoApellido));
                        registroModel.settIDValorTipoDoc(((TipoIdentificacionBD) ddlTipoIdentificacion.getSelectedItem()).getiIDTipoIdentificacion() + "");
                        registroModel.settNumDoc(Security.encodeString(strIdentificacion));


                        if (dtFechaExpDocumento != null) {
                            registroModel.setDtFechaExpedicionCedula(dtFechaExpDocumento);
                        } else {
                            registroModel.setDtFechaExpedicionCedula(new Date("1000/01/01"));
                        }


                        registroModel.settNumeroTarjetaProfesional(strTarjetaProfesional.equals("") ? "" : Security.encodeString(strTarjetaProfesional));
                        registroModel.settCelular(Security.encodeString(strIdentificacion));


                        List<Integer> listDivipola = new ArrayList<>();

                        for (ChipInterface ci : chipsInput.getSelectedChipList()) {


                            listDivipola.add(Integer.parseInt(ci.getInfo()));
                        }

                        registroModel.setListDivipola(listDivipola);


                        showProgressDialog();

                        apiService.Registro(registroModel).enqueue(new Callback<BasicRequestModel>() {
                            @Override
                            public void onResponse(Call<BasicRequestModel> call, Response<BasicRequestModel> response) {

                                Log.d(LOG_ACTIVITY, "onResponse");

                                dissProgressDialog();

                                if (response.isSuccessful()) {

                                    Log.d(LOG_ACTIVITY, "onResponse isSuccessful");


                                    if (response.body().getResult() == 1) {


                                        //Cuenta creada con exito
                                        showAlertEventDialog(strDialogoCuentaRegistrada);

                                    } else if (response.body().getResult() == 2) {

                                        //la cuenta ya existe y no esta activada
                                        showAlertEventDialog(strDialogoCuentaValidaNoActiva);

                                    } else if (response.body().getResult() == 3) {

                                        //la cuenta ya existe y esta activada
                                        showAlertEventDialog(strDialogoCuentaValida);
                                    }

                                } else {

                                    Log.d(LOG_ACTIVITY, "onResponse erro " + response.errorBody());

                                    Snackbar.make(v, strGeneralSinConexionServidor, Snackbar.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onFailure(Call<BasicRequestModel> call, Throwable t) {
                                dissProgressDialog();
                                Snackbar.make(v, strGeneralSinConexionServidor, Snackbar.LENGTH_SHORT).show();

                                Log.d(LOG_ACTIVITY, "onFailure");
                            }
                        });


                    }


                } else {

                    Log.d(LOG_ACTIVITY, "No se a seleccionado una ciudad por lo menos");

                    showAlertDialog(strSinCiudad);

                }

            } else {

                Log.d(LOG_ACTIVITY, "Datos obligatorios faltantes ");

                showAlertDialog(strDatosObligatorios);


            }


        } else {

            Snackbar.make(v, strGeneralSinConexion, Snackbar.LENGTH_SHORT).show();

        }


    }

    public void showAlertDialog(String srtContenido) {

        new MaterialDialog.Builder(this)
                .title(strDialogoTitulo)
                .content(srtContenido)
                .positiveText(strDialogoBtnOk)
                .show();
    }

    public void showAlertEventDialog(String srtContenido) {

        new MaterialDialog.Builder(activity)
                .title(strDialogoTitulo)
                .content(srtContenido)
                .cancelable(false)
                .positiveText(strDialogoBtnOk)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO

                        finish();
                    }
                })
                .show();
    }

}


