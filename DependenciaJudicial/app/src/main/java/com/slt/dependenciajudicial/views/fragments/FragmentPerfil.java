package com.slt.dependenciajudicial.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pchmn.materialchips.ChipView;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.app.Preferences;
import com.slt.dependenciajudicial.app.database.tables.CiudadBD;
import com.slt.dependenciajudicial.app.database.tables.CiudadBD_Table;
import com.slt.dependenciajudicial.app.database.tables.DepartamentoBD;
import com.slt.dependenciajudicial.app.database.tables.DepartamentoBD_Table;
import com.slt.dependenciajudicial.app.database.tables.PaisBD;
import com.slt.dependenciajudicial.app.database.tables.TipoUsuarioBD;
import com.slt.dependenciajudicial.app.database.tables.UsuarioDJCiudadesBD;
import com.slt.dependenciajudicial.requests.SOService;
import com.slt.dependenciajudicial.requests.models.BasicRequestModel;
import com.slt.dependenciajudicial.requests.models.DivipolaModel;
import com.slt.dependenciajudicial.requests.models.UsuarioDJModel;
import com.slt.dependenciajudicial.requests.settings.ApiUtils;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.views.activity.ActivityHome;
import com.slt.dependenciajudicial.views.activity.ActivityLogin;
import com.slt.dependenciajudicial.views.adapters.AdapterCiudad;
import com.slt.dependenciajudicial.views.adapters.AdapterDepartamento;
import com.slt.dependenciajudicial.views.adapters.AdapterPais;
import com.slt.dependenciajudicial.views.adapters.AdapterTipoUsuario;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.slt.dependenciajudicial.app.Preferences.savePreferences;
import static com.slt.dependenciajudicial.requests.settings.ApiUtils.CODE_SESION_EXPIRE;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_PERFIL;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_CURRENT_SCREEN;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPerfil extends Fragment {


    private static final String LOG_ACTIVITY = "FragmentPerfil";

    private DrawerLayout drawer;
    private SOService apiService;

    private List<String> listCiudades;

    //region Init view

    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    @BindView(R.id.fragment_perfil_ddl_tipo_usuario)
    Spinner ddlTipoUsuario;

    @BindView(R.id.fragment_perfil_switch_movile)
    SwitchCompat scDataMovil;

    @BindView(R.id.fragment_perfil_switch_notificaciones)
    SwitchCompat scNotificaciones;

    @BindView(R.id.fragment_perfil_ddl_pais)
    Spinner ddlPais;

    @BindView(R.id.fragment_perfil_ddl_departamento)
    Spinner ddlDepartamento;

    @BindView(R.id.fragment_perfil_ddl_ciudad)
    Spinner ddlCiudad;

    @BindView(R.id.fragment_perfil_btn_add_chip)
    Button btnAddChip;

    @BindView(R.id.fragment_perfil_nsv_container)
    NestedScrollView nsvContainer;

    @BindView(R.id.fragment_perfil_ly_prueba)
    LinearLayout lyPruebaChip;

    //endregion

    //region Init String

    @BindString(R.string.general_sin_conexion)
    String strGeneralSinConexion;

    @BindString(R.string.general_actualizando)
    String strGeneralActualizando;

    @BindString(R.string.general_sin_conexion_servidor)
    String strGeneralSinConexionServidor;

    @BindString(R.string.general_sesion_finalizada)
    String strGeneralSesionFinalizada;

    @BindString(R.string.fragment_perfil_str_progress_actualizar_)
    String strProgressActualizar;

    @BindString(R.string.fragment_perfil_str_dialogo_actualizacion_ok)
    String strDialogActualizacionOk;

    @BindString(R.string.general_dialogo_titulo)
    String strDialogoTitulo;

    @BindString(R.string.general_dialogo_btn_ok)
    String strDialogoBtnOk;

    @BindString(R.string.fragment_perfil_str_sin_ciudad)
    String strSinCiudad;

    //endregion


    private MaterialDialog mdProgress;

    public FragmentPerfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        ButterKnife.bind(this, root);
        apiService = ApiUtils.getSOService();

        listCiudades = new ArrayList<>();

        setupToolBar();
        setupDrawerLayout();
        setupProgressDialog();
        setupFragment();

        setupUbicacion();
        setupChip();


        return root;
    }
    private void setupChip() {

        Log.d(LOG_ACTIVITY, "setupChip");


        new Select().from(UsuarioDJCiudadesBD.class).async().queryListResultCallback(new QueryTransaction.QueryResultListCallback<UsuarioDJCiudadesBD>() {
            @Override
            public void onListQueryResult(QueryTransaction transaction, @NonNull List<UsuarioDJCiudadesBD> tResult) {

                //List<UsuarioDJCiudadesBD> ciudadBDList = new Select().from(UsuarioDJCiudadesBD.class). queryList();


                Log.d(LOG_ACTIVITY, "setupChip ciudadBDList.size()" + tResult.size());
                for (UsuarioDJCiudadesBD usuarioDJCiudadesBD : tResult) {

                    listCiudades.add(usuarioDJCiudadesBD.gettNombreCiudad());

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 4, 0, 4);


                    final ChipView chipView = new ChipView(getActivity());
                    chipView.setLabel(usuarioDJCiudadesBD.gettNombreCiudad());
                    chipView.setHasAvatarIcon(true);
                    chipView.setLayoutParams(layoutParams);
                    chipView.setDeletable(true);
                    chipView.setOnDeleteClicked(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            chipView.setVisibility(View.GONE);

                            listCiudades.remove(chipView.getLabel());

                            Log.d(LOG_ACTIVITY, "setupChip " + listCiudades.size());
                            Log.d(LOG_ACTIVITY, "setupChip " + chipView.getLabel());
                        }
                    });

                    lyPruebaChip.addView(chipView);


                }

            }
        }).execute();

    }


    @OnClick(R.id.fragment_perfil_btn_add_chip)
    public void btnAddChipOnClick(View v) {

        Log.d(LOG_ACTIVITY, "btnAddChipOnClick");

        CiudadBD ciudadBD = (CiudadBD) ddlCiudad.getSelectedItem();


        Boolean existe = false;
        for (String cExite : listCiudades) {

            if (cExite.equals(ciudadBD.gettNombreCiudad())) {
                existe = true;
                break;
            }

        }

        if (!existe) {

            listCiudades.add(ciudadBD.gettNombreCiudad());


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 4, 0, 4);

            final ChipView chipView = new ChipView(getActivity());
            chipView.setLabel(ciudadBD.gettNombreCiudad());
            chipView.setHasAvatarIcon(true);
            chipView.setLayoutParams(layoutParams);
            chipView.setDeletable(true);
            chipView.setOnDeleteClicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    chipView.setVisibility(View.GONE);

                    listCiudades.remove(chipView.getLabel());

                    Log.d(LOG_ACTIVITY, "setupChip " + listCiudades.size());
                    Log.d(LOG_ACTIVITY, "setupChip " + chipView.getLabel());
                }
            });

            lyPruebaChip.addView(chipView);
        }

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

        AdapterPais adapterPais = new AdapterPais(getActivity(), R.layout.spinner_content, paisBDList);
        ddlPais.setAdapter(adapterPais);


        ddlPais.setEnabled(false);

        AdapterDepartamento adapterDepartamento = new AdapterDepartamento(getActivity(), R.layout.spinner_content, departamentoBDList);
        ddlDepartamento.setAdapter(adapterDepartamento);

        AdapterCiudad adapterCiudad = new AdapterCiudad(getActivity(), R.layout.spinner_content, ciudadBDList);
        ddlCiudad.setAdapter(adapterCiudad);


        ddlDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                DepartamentoBD departamentoBD = departamentoBDList.get(i);

                List<CiudadBD> ciudadBDList2 = new Select()
                        .from(CiudadBD.class)
                        .where(CiudadBD_Table.tIDDepartamento.eq(departamentoBD.gettIDDepartamento()))
                        .queryList();


                AdapterCiudad adapterCiudad2 = new AdapterCiudad(getActivity(), R.layout.spinner_content, ciudadBDList2);
                ddlCiudad.setAdapter(adapterCiudad2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //

    }

    //metodo que inicializa la toolbar
    private void setupToolBar() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.fragment_perfil_str_bar_title);
    }

    //metodo que inicializa las acciones del menu
    private void setupDrawerLayout() {

        drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    //Metodo que contiene las funcionalidades del fragment
    private void setupFragment() {

        List<TipoUsuarioBD> tipoUsuarioBDS = new Select().from(TipoUsuarioBD.class).queryList();
        AdapterTipoUsuario adapterTipoUsuario = new AdapterTipoUsuario(getActivity(), R.layout.spinner_content, tipoUsuarioBDS);
        ddlTipoUsuario.setAdapter(adapterTipoUsuario);

        Integer iIDTipoUsuario = Integer.parseInt(Preferences.getPreferences(getActivity(), DependenciaJudicialUtils.SP_ID_TIPO_USUARIO));
        int index = 0;
        for (TipoUsuarioBD tipoUsuarioBD : tipoUsuarioBDS) {

            if (iIDTipoUsuario.equals(tipoUsuarioBD.getiIDTipoUsuario())) {
                ddlTipoUsuario.setSelection(index);
                break;
            }
            ++index;
        }


        // Evebto change del swich de Archivos

        scDataMovil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    Log.d(LOG_ACTIVITY, "scDataMovil - On");

                    savePreferences(getActivity(), DependenciaJudicialUtils.SP_CONFIGURACION_RED_MOVIL, "1");

                } else {
                    Log.d(LOG_ACTIVITY, "scDataMovil - off");
                    savePreferences(getActivity(), DependenciaJudicialUtils.SP_CONFIGURACION_RED_MOVIL, "0");
                }

            }
        });

        scDataMovil.setChecked(Preferences.getPreferences(getActivity(), DependenciaJudicialUtils.SP_CONFIGURACION_RED_MOVIL).equals("1"));

        // Evento change del swich de notificaciones


        scNotificaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(DependenciaJudicialUtils.isConnected(getActivity())){

                    if(scNotificaciones.getTag()== "1"){


                        BasicRequestModel basicRequestModel = new BasicRequestModel();
                        basicRequestModel.setResult( isChecked ? 1 : 0 );

                        String token = Preferences.getPreferences(getActivity(),SP_TOKEN);


                        showProgressDialog(strGeneralActualizando);

                        apiService.ActualizarNotificacion(token,basicRequestModel).enqueue(new Callback<BasicRequestModel>() {
                            @Override
                            public void onResponse(Call<BasicRequestModel> call, Response<BasicRequestModel> response) {

                                dissProgressDialog();

                                if (response.isSuccessful()) {

                                    if (response.code() != CODE_SESION_EXPIRE) {


                                        Preferences.savePreferences(getActivity(), DependenciaJudicialUtils.SP_TOKEN, response.body().getToken());

                                        savePreferences(getActivity(), DependenciaJudicialUtils.SP_NOTIFICACIONES, response.body().getResult() == 1 ? "1":"0");


                                    }else{
                                        finalizarSesion();
                                    }


                                }else {

                                    selectCurrentTipoUsuario();
                                    Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onFailure(Call<BasicRequestModel> call, Throwable t) {

                                dissProgressDialog();
                            }
                        });

                    }

                }else{

                    Snackbar.make(getView(), strGeneralSinConexion, Snackbar.LENGTH_SHORT).show();
                }



                /*
                if (isChecked) {

                    Log.d(LOG_ACTIVITY, "scDataMovil - On");



                } else {
                    Log.d(LOG_ACTIVITY, "scDataMovil - off");
                    savePreferences(getActivity(), DependenciaJudicialUtils.SP_NOTIFICACIONES, "0");
                }
                */

                // actualziar en el servidor

            }
        });

        // El tag se configura de esa esa forma para evitar el evento en el oncreate
        scNotificaciones.setTag("0");
        scNotificaciones.setChecked(Preferences.getPreferences(getActivity(), DependenciaJudicialUtils.SP_NOTIFICACIONES).equals("1"));
        scNotificaciones.setTag("1");
        // scNotificaciones.setva(Preferences.getPreferences(getActivity(), DependenciaJudicialUtils.SP_NOTIFICACIONES).equals("1"));


    }

    @OnClick(R.id.fragment_perfil_btn_actualizar)
    public void btnActualizarOnClick(View view) {

        if (DependenciaJudicialUtils.isConnected(getActivity())) {

            UsuarioDJModel usuarioDJModel = new UsuarioDJModel();

            int iNumMunicipios = listCiudades.size();

            if (iNumMunicipios != 0) {

                List<DivipolaModel> listDivipola = new ArrayList<>();

                for (String tCiudad : listCiudades) {

                    CiudadBD ciudadBD = new Select().from(CiudadBD.class).where(CiudadBD_Table.tNombreCiudad.eq(tCiudad)).querySingle();

                    DivipolaModel divipolaModel = new DivipolaModel();
                    divipolaModel.setIIDDIvipola(ciudadBD.getiIDDivipola());
                    listDivipola.add(divipolaModel);

                    Log.d(LOG_ACTIVITY,"CiudadBD :"+ciudadBD.gettNombreCiudad()+"ciudadBD.getiIDDivipola()"+ciudadBD.getiIDDivipola());
                }

                usuarioDJModel.setDivipolaModel(listDivipola);

                String tIDValorTipoUsuario = ((TipoUsuarioBD) ddlTipoUsuario.getSelectedItem()).getiIDTipoUsuario().toString();

                usuarioDJModel.setTIDValorTipoUsuario(tIDValorTipoUsuario);

                String strToken = Preferences.getPreferences(getActivity(), DependenciaJudicialUtils.SP_TOKEN);

                showProgressDialog(strProgressActualizar);
                apiService.ActualizarUsuario(strToken, usuarioDJModel).enqueue(new Callback<UsuarioDJModel>() {
                    @Override
                    public void onResponse(Call<UsuarioDJModel> call, Response<UsuarioDJModel> response) {

                        dissProgressDialog();
                        if (response.isSuccessful()) {

                            if (response.code() != CODE_SESION_EXPIRE) {

                                Preferences.savePreferences(getActivity(), DependenciaJudicialUtils.SP_TOKEN, response.body().getToken());
                                Preferences.savePreferences(getActivity(), DependenciaJudicialUtils.SP_ID_TIPO_USUARIO, response.body().getTIDValorTipoUsuario());
                                Preferences.savePreferences(getActivity(), DependenciaJudicialUtils.SP_TIPO_USUARIO, ((TipoUsuarioBD) ddlTipoUsuario.getSelectedItem()).gettNombreTipoUsuario());

                                new Delete().from(UsuarioDJCiudadesBD.class).execute();

                                List<DivipolaModel> listDivipolaModel = response.body().getDivipolaModel();

                                //Insertar en la db Local
                                for (DivipolaModel divipolaModel : listDivipolaModel) {

                                    UsuarioDJCiudadesBD usuarioDJCiudadesBD = new UsuarioDJCiudadesBD();
                                    usuarioDJCiudadesBD.setiIDDIvipola(divipolaModel.getIIDDIvipola());
                                    usuarioDJCiudadesBD.settNombreCiudad(divipolaModel.getTNombreCiudad());
                                    usuarioDJCiudadesBD.settCodigoCiudad(divipolaModel.getTCodigoCiudad());
                                    usuarioDJCiudadesBD.save();
                                }

                                ((ActivityHome) getActivity()).setupMenu();
                                ((ActivityHome) getActivity()).setupDrawerHeader();
                                showAlertDialog(strDialogActualizacionOk);

                            } else {

                                finalizarSesion();

                            }

                        } else {

                            selectCurrentTipoUsuario();
                            Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<UsuarioDJModel> call, Throwable t) {
                        dissProgressDialog();
                        selectCurrentTipoUsuario();
                        Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_SHORT).show();

                    }
                });


            } else {

                Log.d(LOG_ACTIVITY, "No se a seleccionado una ciudad por lo menos");
                showAlertDialog(strSinCiudad);

            }


        } else {

            selectCurrentTipoUsuario();
            Snackbar.make(view, strGeneralSinConexion, Snackbar.LENGTH_SHORT).show();

        }


    }

    private void setupProgressDialog() {

        mdProgress = new MaterialDialog.Builder(getActivity())
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

    private void finalizarSesion() {

        Preferences.clearPreferences(getActivity());
        Toast.makeText(getActivity().getBaseContext(), strGeneralSesionFinalizada, Toast.LENGTH_LONG).show();
        Intent i = new Intent(getActivity(), ActivityLogin.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        getActivity().finish();
    }

    public void showAlertDialog(String srtContenido) {

        new MaterialDialog.Builder(getActivity())
                .title(strDialogoTitulo)
                .content(srtContenido)
                .positiveText(strDialogoBtnOk)
                .show();
    }

    public void selectCurrentTipoUsuario() {

        List<TipoUsuarioBD> tipoUsuarioBDS = new Select().from(TipoUsuarioBD.class).queryList();
        Integer iIDTipoUsuario = Integer.parseInt(Preferences.getPreferences(getActivity(), DependenciaJudicialUtils.SP_ID_TIPO_USUARIO));
        int index = 0;
        for (TipoUsuarioBD tipoUsuarioBD : tipoUsuarioBDS) {

            if (iIDTipoUsuario.equals(tipoUsuarioBD.getiIDTipoUsuario())) {
                ddlTipoUsuario.setSelection(index);
                break;
            }
            ++index;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        setupToolBar();
        setupDrawerLayout();

        savePreferences(getActivity(), SP_CURRENT_SCREEN, APP_SCREEN_FRAGMENT_PERFIL);

        Log.d(LOG_ACTIVITY, "FragmentPerfil onResume");



    }

}
