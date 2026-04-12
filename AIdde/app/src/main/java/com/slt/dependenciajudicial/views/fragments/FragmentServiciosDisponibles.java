package com.slt.dependenciajudicial.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.app.Preferences;
import com.slt.dependenciajudicial.requests.SOService;
import com.slt.dependenciajudicial.requests.models.AceptacionModel;
import com.slt.dependenciajudicial.requests.models.BasicRequestModel;
import com.slt.dependenciajudicial.requests.models.RequestSolicitudModel;
import com.slt.dependenciajudicial.requests.models.SolicitudModel;
import com.slt.dependenciajudicial.requests.settings.ApiUtils;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.utils.Security;
import com.slt.dependenciajudicial.utils.callback.CallbackRVServiciosDisponibles;
import com.slt.dependenciajudicial.views.activity.ActivityLogin;
import com.slt.dependenciajudicial.views.adapters.RVAdapterServiciosDisponible;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.slt.dependenciajudicial.app.Preferences.getPreferences;
import static com.slt.dependenciajudicial.app.Preferences.savePreferences;
import static com.slt.dependenciajudicial.requests.settings.ApiUtils.CODE_SESION_EXPIRE;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_CODE_ACCION_FCM_NUEVO_SERVICIO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_CODE_ACCION_FCM_NUEVO_SERVICIO_MASIVO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_CURRENT_SCREEN;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_TOKEN;

/**
 * Created by Nelsy Acuña on 21/11/2017.
 */

public class FragmentServiciosDisponibles extends Fragment {

    private static final String LOG_ACTIVITY = "FServiciosDisponibles";
    private DrawerLayout drawer;
    private RVAdapterServiciosDisponible adapter;
    private List<SolicitudModel> solicitudModels;
    private SOService apiService;
    private MaterialDialog mdProgress;

    private Call<RequestSolicitudModel> callSolicitudesEstadoNotificadoAceptado;
    private Call<BasicRequestModel> callAceptaRSolicitud;

    //region Init view

    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    @BindView(R.id.fragment_servicios_disponibles_rv_servicios)
    RecyclerView rvServicios;

    @BindView(R.id.fragment_servicios_disponibles_ly_no_servicios_disponibles)
    LinearLayout lyNoServiciosDisponibles;


    @BindView(R.id.fragment_servicios_disponibles_swipe_refresh_container)
    SwipeRefreshLayout swipeRefreshContainer;

    //endregion


    //region init String

    @BindString(R.string.fragment_servicios_disponibles_str_bar_title)
    String strBarTitulo;

    @BindString(R.string.general_sin_conexion)
    String strGeneralSinConexion;

    @BindString(R.string.general_sin_conexion_servidor)
    String strGeneralSinConexionServidor;

    @BindString(R.string.general_sesion_finalizada)
    String strGeneralSesionFinalizada;

    @BindString(R.string.fragment_servicios_disponibles_str_progress_aceptacion)
    String strProgressAceptacion;

    @BindString(R.string.fragment_servicios_disponibles_str_aceptacion)
    String strAceptacion;

    @BindString(R.string.fragment_servicios_disponibles_str_solicitud_no_valida)
    String srtSolicitudNoValida;


    //endregion


    public FragmentServiciosDisponibles() {
        // Required empty public constructor

        solicitudModels = new ArrayList<>();

        Log.d(LOG_ACTIVITY, " FragmentServiciosDisponibles Constructor ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_servicios_disponibles, container, false);
        ButterKnife.bind(this, root);

        apiService = ApiUtils.getSOService();
        setupProgressDialog();
        setupToolBar();
        setupDrawerLayout();
        setupSwipeRefreshContainer();
        setupRVServicios();
        // setHasOptionsMenu(true);


        Log.d(LOG_ACTIVITY, "FragmentServiciosDisponibles onCreateView");
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        setupToolBar();
        setupDrawerLayout();
        resetNotificationMenuCountServicioDisponible();
        getUpdateListSolicitudModel();

        savePreferences(getActivity(), SP_CURRENT_SCREEN, APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES);

        Log.d(LOG_ACTIVITY, "FragmentServiciosDisponibles onResume");

    }

    //metodo que inicializa la toolbar
    private void setupToolBar() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(strBarTitulo);
    }

    //metodo que inicializa las acciones del menu
    private void setupDrawerLayout() {

        drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    //Metodo que inicializa la lista de servicios
    private void setupRVServicios() {

        //Obtener datos de los servicios disponibles
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        // rvServicios.setHasFixedSize(true);
        rvServicios.setLayoutManager(llm);


        // Callback de los eventos del item de la lista
        CallbackRVServiciosDisponibles cbRVServicioDisponible = new CallbackRVServiciosDisponibles() {
            @Override
            public void onCallbackRVServicioDisponibleBtnTomar(SolicitudModel itemModel, AppCompatSpinner spnTiempo, AppCompatSpinner spnUnidad) {


                Log.d(LOG_ACTIVITY, itemModel.getiTiempoSolicitud().toString());
                Log.d(LOG_ACTIVITY, itemModel.gettUnidadTiempoSolicitud());
                Log.d(LOG_ACTIVITY, itemModel.getTNumeroProceso());
                Log.d(LOG_ACTIVITY, spnTiempo.getSelectedItem().toString());
                Log.d(LOG_ACTIVITY, spnUnidad.getSelectedItem().toString());

                if (DependenciaJudicialUtils.isConnected(getActivity())) {

                    showProgressDialog(strProgressAceptacion);

                    String token = getPreferences(getActivity(), SP_TOKEN);

                    String strUnidad = spnUnidad.getSelectedItem().toString();
                    strUnidad = strUnidad.equals("Minutos") ? "1" : strUnidad.equals("Horas") ? "2" : strUnidad.equals("Días") ? "3" : "1";

                    AceptacionModel aceptacionModel = new AceptacionModel();
                    aceptacionModel.setiIDDJSolicitudEnc(Security.encodeString(itemModel.getIIDSolicitud().toString()));
                    aceptacionModel.setiTiempoServicioEnc(Security.encodeString(spnTiempo.getSelectedItem().toString()));
                    aceptacionModel.setTiIDValorUnidadTiempoEnc(Security.encodeString(strUnidad));

                    callAceptaRSolicitud = apiService.AceptarSolicitud(token, aceptacionModel);

                    callAceptaRSolicitud.enqueue(new Callback<BasicRequestModel>() {
                        @Override
                        public void onResponse(Call<BasicRequestModel> call, Response<BasicRequestModel> response) {

                            dissProgressDialog();

                            if (response.isSuccessful()) {

                                if (response.code() != CODE_SESION_EXPIRE) {

                                    Preferences.savePreferences(getActivity(), DependenciaJudicialUtils.SP_TOKEN, response.body().getToken());
                                    getUpdateListSolicitudModel();

                                    if (response.body().getResult() == 1) {
                                        Snackbar.make(getView(), strAceptacion, DependenciaJudicialUtils.LONG_SNACKBAR).show();
                                    } else {
                                        Snackbar.make(getView(), srtSolicitudNoValida, Snackbar.LENGTH_LONG).show();
                                    }

                                } else {

                                    finalizarSesion();
                                }


                            } else {

                                Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_LONG).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<BasicRequestModel> call, Throwable t) {

                            dissProgressDialog();
                            Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_LONG).show();
                        }
                    });


                } else {

                    Snackbar.make(getView(), strGeneralSinConexion, Snackbar.LENGTH_LONG).show();
                }

            }
        };

        adapter = new RVAdapterServiciosDisponible(getContext(), solicitudModels, cbRVServicioDisponible);
        rvServicios.setAdapter(adapter);
    }

    // Metodo que obtienen los datos de la lista y la actualzia
    private void getUpdateListSolicitudModel() {


        if (DependenciaJudicialUtils.isConnected(getActivity())) {

            swipeRefreshContainer.setRefreshing(true);

            //Consulta al servicio

            String token = getPreferences(getActivity(), SP_TOKEN);

            callSolicitudesEstadoNotificadoAceptado = apiService.SolicitudesEstadoNotificadoAceptado(token);

            callSolicitudesEstadoNotificadoAceptado.enqueue(new Callback<RequestSolicitudModel>() {
                @Override
                public void onResponse(Call<RequestSolicitudModel> call, Response<RequestSolicitudModel> response) {

                    if (response.isSuccessful()) {

                        if (response.code() != CODE_SESION_EXPIRE) {

                            if (response.body() != null && response.body().getToken() != null) {


                                Preferences.savePreferencesContext(getContext(), DependenciaJudicialUtils.SP_TOKEN, response.body().getToken());

                                if (response.body().getListSolicitudModel() != null && response.body().getListSolicitudModel().size() != 0) {


                                    solicitudModels = new ArrayList<>();
                                    for(int i =0; i <response.body().getListSolicitudModel().size(); i++){
                                        SolicitudModel sm = response.body().getListSolicitudModel().get(i);
                                        if(sm.getIIDTipoSolicitud() != 6){
                                            solicitudModels.add(sm);
                                        }
                                    }

                                    if(solicitudModels != null && solicitudModels.size() != 0 ) {
                                        adapter.updateRVServiciosDisponible2(solicitudModels);
                                        lyNoServiciosDisponibles.setVisibility(View.GONE);
                                    }else{
                                        solicitudModels = new ArrayList<>();
                                        adapter.updateRVServiciosDisponible2(solicitudModels);
                                        lyNoServiciosDisponibles.setVisibility(View.VISIBLE);

                                        Log.d(LOG_ACTIVITY, "No hay servicios ");
                                        //No hay servicios de este tipo de solicitud
                                    }



                                   // solicitudModels = response.body().getListSolicitudModel();

                                } else {

                                    solicitudModels = new ArrayList<>();
                                    adapter.updateRVServiciosDisponible2(solicitudModels);
                                    lyNoServiciosDisponibles.setVisibility(View.VISIBLE);

                                    Log.d(LOG_ACTIVITY, "No hay servicios ");
                                    //No hay resuldados
                                }

                            } else {

                                if (solicitudModels != null && solicitudModels.size() == 0)
                                    lyNoServiciosDisponibles.setVisibility(View.VISIBLE);

                                Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_LONG).show();
                            }


                        } else {
                            finalizarSesion();
                        }


                    } else {

                        if (solicitudModels != null && solicitudModels.size() == 0)
                            lyNoServiciosDisponibles.setVisibility(View.VISIBLE);

                        Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_LONG).show();
                    }

                    swipeRefreshContainer.setRefreshing(false);

                }

                @Override
                public void onFailure(Call<RequestSolicitudModel> call, Throwable t) {

                    Log.d(LOG_ACTIVITY, "call isCanceled() :" + call.isCanceled());
                    if (!call.isCanceled()) {

                        if (solicitudModels != null && solicitudModels.size() == 0)
                            lyNoServiciosDisponibles.setVisibility(View.VISIBLE);

                        swipeRefreshContainer.setRefreshing(false);
                        Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_LONG).show();

                    }
                }
            });


        } else {

            swipeRefreshContainer.setRefreshing(false);
            Snackbar.make(getView(), strGeneralSinConexion, Snackbar.LENGTH_LONG).show();
        }


    }

    // Metodo del evento de actualizar
    private void setupSwipeRefreshContainer() {

        swipeRefreshContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.d(LOG_ACTIVITY, "sertupSwipeRefreshContainer");
                getUpdateListSolicitudModel();

            }
        });
    }

    //metodo que limbia el count del menu de servicios disponibles
    private void resetNotificationMenuCountServicioDisponible() {

        Preferences.savePreferences(getActivity(), DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_SOLICITUD_SERVICIO, "");
        NavigationView navView = getActivity().findViewById(R.id.navview);
        DependenciaJudicialUtils.showMenuCountBag(getActivity(), navView, R.id.menu_dependiente_item_mn_servicios_disponibles, "");
        DependenciaJudicialUtils.clearNotificacion(getActivity().getApplicationContext(), APP_CODE_ACCION_FCM_NUEVO_SERVICIO);

    }




    private void finalizarSesion() {

        Preferences.clearPreferences(getActivity());
        Toast.makeText(getActivity().getBaseContext(), strGeneralSesionFinalizada, Toast.LENGTH_LONG).show();
        Intent i = new Intent(getActivity(), ActivityLogin.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        getActivity().finish();
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(LOG_ACTIVITY, "onDestroy");

        if (callSolicitudesEstadoNotificadoAceptado != null) {

            Log.d(LOG_ACTIVITY, "onDestroy solicitudesEstadoNotificadoAceptado");
            callSolicitudesEstadoNotificadoAceptado.cancel();
        }

        if (callAceptaRSolicitud != null) {

            Log.d(LOG_ACTIVITY, "onDestroy aceptaRSolicitud");
            callAceptaRSolicitud.cancel();
        }


    }

    //region Metodos de los item del menu de la tool bar

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //TODO Add your menu entries here
        inflater.inflate(R.menu.menu_fragment_servicios_disponibles, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_fragment_servicios_disponibles_item_mn_filtrar:

                Log.d(LOG_ACTIVITY, "onOptionsItemSelected Filtrar");
                break;
        }

        return true;
    }
    */
    //endregion


}
