package com.slt.dependenciajudicial.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.app.Preferences;
import com.slt.dependenciajudicial.app.database.tables.CiudadBD;
import com.slt.dependenciajudicial.app.database.tables.CiudadBD_Table;
import com.slt.dependenciajudicial.app.database.tables.DepartamentoBD;
import com.slt.dependenciajudicial.app.database.tables.DepartamentoBD_Table;
import com.slt.dependenciajudicial.app.database.tables.PaisBD;
import com.slt.dependenciajudicial.requests.SOService;
import com.slt.dependenciajudicial.requests.models.FuenteExternaModel;
import com.slt.dependenciajudicial.requests.models.RequestFuenteExternaModel;
import com.slt.dependenciajudicial.requests.settings.ApiUtils;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.views.activity.ActivityLogin;
import com.slt.dependenciajudicial.views.adapters.AdapterCiudad;
import com.slt.dependenciajudicial.views.adapters.AdapterDepartamento;
import com.slt.dependenciajudicial.views.adapters.AdapterNombreFuenteExterna;
import com.slt.dependenciajudicial.views.adapters.AdapterPais;

import java.util.ArrayList;
import java.util.List;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.slt.dependenciajudicial.app.Preferences.getPreferences;
import static com.slt.dependenciajudicial.app.Preferences.savePreferences;
import static com.slt.dependenciajudicial.requests.settings.ApiUtils.CODE_SESION_EXPIRE;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_ACERCA_DE;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_CREAR_SOLICITUD;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_CURRENT_SCREEN;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCrearSolicitud extends Fragment {

    private static final String LOG_ACTIVITY = "FragmentCrearSolicitud";
    private DrawerLayout drawer;
    private SOService apiService;
    private Call<RequestFuenteExternaModel> callCrearSolicitud;
    private MaterialDialog mdProgress;

    private List<FuenteExternaModel> fuenteExternaModels ;

    //region init view
    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    @BindView(R.id.fragment_crear_solicitud_ddl_pais)
    Spinner ddlPais;

    @BindView(R.id.fragment_crear_solicitud_ddl_departamento)
    Spinner ddlDepartamento;

    @BindView(R.id.fragment_crear_solicitud_ddl_entidad)
    Spinner ddlEntidad;

    @BindView(R.id.fragment_crear_solicitud_ddl_ciudad)
    Spinner ddlCiudad;


    @BindView(R.id.fragment_crear_solicitud_edt_num_proceso)
    MaskedEditText edtNumProceso;

    @BindView(R.id.fragment_crear_solicitud_edt_descripcion)
    EditText edtDescripcion;

    @BindView(R.id.fragment_crear_solicitud_ly_rama_judicial_container)
    LinearLayout lyRamaJudicialContainer;

    @BindView(R.id.fragment_crear_solicitud_edt_ciudad)
    EditText edtCiudad;


    //endregion

    //region init String
    @BindString(R.string.fragment_crear_solicitud_str_bar_title)
    String strBarTitulo;


    @BindString(R.string.general_dialogo_titulo)
    String strDialogoTitulo;

    @BindString(R.string.general_dialogo_cargando)
    String strDialogCargando;

    @BindString(R.string.general_sin_conexion)
    String strGeneralSinConexion;

    @BindString(R.string.general_sin_conexion_servidor)
    String strGeneralSinConexionServidor;

    @BindString(R.string.general_sesion_finalizada)
    String strGeneralSesionFinalizada;

    //endregion

    public FragmentCrearSolicitud() {
        // Required empty public constructor
        fuenteExternaModels = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_crear_solicitud, container, false);
        ButterKnife.bind(this, root);


        setupProgressDialog();

        apiService = ApiUtils.getSOService();
        showProgressDialog(strDialogCargando);

        setupToolBar();
        setupDrawerLayout();
        setupUbicacion();
        setupFragment();

        setupEntidades();

        dissProgressDialog();

        return root;
    }

    private void setupEntidades() {

        if(DependenciaJudicialUtils.isConnected(getActivity())){


            String token = getPreferences(getActivity(), SP_TOKEN);
            callCrearSolicitud = apiService.NombreFuentesExternas(token);

            callCrearSolicitud.enqueue(new Callback<RequestFuenteExternaModel>() {
                @Override
                public void onResponse(Call<RequestFuenteExternaModel> call, Response<RequestFuenteExternaModel> response) {

                    if (response.isSuccessful()) {

                        if (response.code() != CODE_SESION_EXPIRE) {

                            if (response.body() != null && response.body().getToken() != null) {

                                AdapterNombreFuenteExterna adapterNombreFuenteExterna = new AdapterNombreFuenteExterna(getActivity(), R.layout.spinner_content, response.body().getListFuenteExternaModel());
                                ddlEntidad.setAdapter(adapterNombreFuenteExterna);

                                fuenteExternaModels = response.body().getListFuenteExternaModel();

                                Log.d(LOG_ACTIVITY, "FuenteExterna :"+ response.body().getListFuenteExternaModel().size() );
                            }
                        }else {

                            finalizarSesion();

                        }
                    }else {

                        Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<RequestFuenteExternaModel> call, Throwable t) {
                    Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_SHORT).show();

                }
            });


            ddlEntidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    FuenteExternaModel fuenteExternaModel = fuenteExternaModels.get(position);

                    if(fuenteExternaModel!=null)
                        validarFuenteExterna (fuenteExternaModel.getIIDFuentesExternas());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }else{

            Snackbar.make(getView(), strGeneralSinConexion, Snackbar.LENGTH_SHORT).show();
        }

    }

    private void validarFuenteExterna(Integer iidFuentesExternas) {


        switch ( iidFuentesExternas){

            case 1: // Rama judicial

                lyRamaJudicialContainer.setVisibility(View.VISIBLE);

                break;
            case 2:
                break;

        }

    }

    private void setupFragment() {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        edtNumProceso.clearFocus();
        edtDescripcion.clearFocus();

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

        ddlCiudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               // ddlCiudad.get

                //CiudadBD ciudadBD = ciudadBDList.get(position);

                //edtCiudad.setText(ciudadBD.gettNombreCiudad());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //


    }

    private void setupDrawerLayout() {
        drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupToolBar() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(strBarTitulo);
    }

    @Override
    public void onResume() {
        super.onResume();

        setupToolBar();
        setupDrawerLayout();

        savePreferences(getActivity(), SP_CURRENT_SCREEN, APP_SCREEN_FRAGMENT_CREAR_SOLICITUD);

        Log.d(LOG_ACTIVITY, "FragmentCrearSolicitud onResume");
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

}
