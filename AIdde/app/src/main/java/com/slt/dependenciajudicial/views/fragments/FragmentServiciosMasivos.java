package com.slt.dependenciajudicial.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.slt.dependenciajudicial.requests.models.SolicitudesProcesosNumeroMovimientoProcesalModel;
import com.slt.dependenciajudicial.requests.settings.ApiUtils;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.utils.Security;
import com.slt.dependenciajudicial.utils.callback.CallbackServiciosDisponiblesMasivos;
import com.slt.dependenciajudicial.views.activity.ActivityLogin;
import com.slt.dependenciajudicial.views.adapters.RVAdapterExpansionPanel;

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
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_CODE_ACCION_FCM_NUEVO_SERVICIO_MASIVO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_SERVICIOS_MASIVOS;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_CURRENT_SCREEN;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */


public class FragmentServiciosMasivos extends Fragment {


    private final static String LOG_ACTIVITY = "FServiciosMasivos";
    private DrawerLayout drawer;


    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    @BindView(R.id.servicios_masivos_recycler)
    RecyclerView rvServicios;

    @BindString(R.string.fragment_servicios_masivos_disponibles_str_bar_title)
    String strBarTitulo;

    @BindString(R.string.general_sesion_finalizada)
    String strGeneralSesionFinalizada;

    @BindString(R.string.general_sin_conexion_servidor)
    String strGeneralSinConexionServidor;

    @BindView(R.id.fragment_servicios_disponibles_ly_no_servicios_disponibles_masivo)
    LinearLayout lyNoServiciosDisponibles;

    @BindView(R.id.fragment_servicios_disponibles_swipe_refresh_container_masivo)
    SwipeRefreshLayout swipeRefreshContainer;

    @BindString(R.string.general_sin_conexion)
    String strGeneralSinConexion;

    @BindString(R.string.fragment_servicios_disponibles_str_aceptacion)
    String strAceptacion;

    @BindString(R.string.fragment_servicios_disponibles_str_solicitud_no_valida)
    String srtSolicitudNoValida;

    @BindString(R.string.fragment_servicios_disponibles_str_progress_aceptacion)
    String strProgressAceptacion;


    private Call<RequestSolicitudModel> callSolicitudesNtoficadosAceptadosMasivos;
    private Call<BasicRequestModel> callAceptaRSolicitud;

    private SOService apiService;
    private List<SolicitudModel> solicitudModels;
    private RVAdapterExpansionPanel adapter;
    private MaterialDialog mdProgress;
    private View root;



    public FragmentServiciosMasivos() {
        // Required empty public constructor
        solicitudModels = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_fragment_servicios_masivos, container, false);
        ButterKnife.bind(this, root);


        apiService = ApiUtils.getSOService();
        setupProgressDialog();
        setUpToolBar();
        setupDrawerLayout();
        setupSwipeRefreshContainer();
        setUpServices();
        return  root;
    }

    @Override
    public void onResume(){
        super.onResume();
        setUpToolBar();
        setupDrawerLayout();
        resetNotificationMenuCountServicioDisponibleMasivo();
        getListServiciosMasivos();

        savePreferences(getActivity(), SP_CURRENT_SCREEN  , APP_SCREEN_FRAGMENT_SERVICIOS_MASIVOS);

    }

    //Configurar el titulo de la toolbar
    private  void setUpToolBar(){
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


    private void setUpServices(){

        rvServicios = root.findViewById(R.id.servicios_masivos_recycler);
        final FragmentActivity fc = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fc);
        rvServicios.setLayoutManager(linearLayoutManager);

        //Obtener datos de los servicios disponibles
        //  LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        //   llm.setOrientation(LinearLayoutManager.VERTICAL);
        //   rvServicios.setLayoutManager(llm);

        CallbackServiciosDisponiblesMasivos callbackServiciosDisponiblesMasivos = new CallbackServiciosDisponiblesMasivos() {
            @Override
            public void onCallbackRVServicioDisponibleBtnTomar(SolicitudModel itemModel, AppCompatSpinner spnTiempo, AppCompatSpinner spnUnidad) {



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
                                    getListServiciosMasivos();

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

        adapter = new RVAdapterExpansionPanel(getContext(), solicitudModels, callbackServiciosDisponiblesMasivos);
        rvServicios.setAdapter(adapter);
    }



    private void getListServiciosMasivos(){

        //rvServicios = findViewById(R.id.recyclerView);

        if (DependenciaJudicialUtils.isConnected(getActivity())) {

            swipeRefreshContainer.setRefreshing(true);

        String token = getPreferences(getActivity(), SP_TOKEN);

        callSolicitudesNtoficadosAceptadosMasivos = apiService.SolicitudesEstadoNotificadoAceptado(token);
        callSolicitudesNtoficadosAceptadosMasivos.enqueue(new Callback<RequestSolicitudModel>() {
            @Override
            public void onResponse(Call<RequestSolicitudModel> call, Response<RequestSolicitudModel> response) {
                if(response.isSuccessful()){
                    if(response.code() != CODE_SESION_EXPIRE){

                        if(response.body() != null && response.body().getToken() != null){

                            Preferences.savePreferencesContext(getContext(), DependenciaJudicialUtils.SP_TOKEN, response.body().getToken());

                            if (response.body().getListSolicitudModel() != null && response.body().getListSolicitudModel().size() != 0) {

                                solicitudModels = new ArrayList<>();
                                for(int i =0; i <response.body().getListSolicitudModel().size(); i++){
                                    SolicitudModel sm = response.body().getListSolicitudModel().get(i);
                                    if(sm.getIIDTipoSolicitud() == 6){
                                        List<SolicitudesProcesosNumeroMovimientoProcesalModel> listActuaciones = response.body().getListProcesalNumber();
                                        List<SolicitudesProcesosNumeroMovimientoProcesalModel> listActuacionesTempo = new ArrayList<>();
                                        Log.d("TAMAÑO DE LA LISTA" , Integer.toString(listActuaciones.size()));

                                        for(int y =0; y < listActuaciones.size(); y++) {
                                            Log.d("ID DE LA SOLICITUD", Integer.toString(sm.getIIDSolicitud()));
                                            Log.d("ID DE LA RELACION", Integer.toString(listActuaciones.get(y).getiDSolicitud()));

                                            if (sm.getIIDSolicitud().equals(listActuaciones.get(y).getiDSolicitud())) {
                                                Log.d("SABER (TIPO SOPORTE)", String.valueOf(listActuaciones.get(y).getiIDTipoSoporteTipo()));

                                                listActuacionesTempo.add(listActuaciones.get(y));
                                                Log.d("ENTRADA LISTA RECORRIDA", Integer.toString(y));
                                             }
                                        }
                                        sm.setListActuaciones(listActuacionesTempo);
                                        solicitudModels.add(sm);
                                    }
                                }

                         //       Log.d("ETC_ETC","EL LARGO DE LA LISTA DE SOLICITUDESNUMEROPRTOCESAL ES" +   Integer.toString(response.body().getListProcesalNumber().size())  );
                        //        List<SolicitudesProcesosNumeroMovimientoProcesalModel> listActuaciones = response.body().getListProcesalNumber();
                          //      for(int i =0;i<listActuaciones.size();i++){
                          //          SolicitudesProcesosNumeroMovimientoProcesalModel p = listActuaciones.get(i);
                          //          Log.d("ETC_ETC","ESTA ES LA SOLICITUD MASIVO id actuación zzzzzzzzz#: "+ p.getiDProcesoMovimientoProcesal());
                          //          Log.d("ETC_ETC","ESTA ES LA SOLICITUD MASIVO Numero de la solicitud zzzzzzzzz#: "+ p.getiDSolicitud());
                            //        Log.d("ETC_ETC","ESTA ES LA SOLICITUD MASIVO id Tip ode soporte zzzzzzzzz#: "+ p.getiIDTipoSoporteTipo());
                           //     }

                                if(solicitudModels != null && solicitudModels.size() != 0 ) {
                                    adapter.setItems(solicitudModels);
                                    lyNoServiciosDisponibles.setVisibility(View.GONE);
                                }else{
                                    solicitudModels = new ArrayList<>();
                                    adapter.setItems(solicitudModels);
                                    lyNoServiciosDisponibles.setVisibility(View.VISIBLE);


                                    Log.d(LOG_ACTIVITY, "No hay servicios ");
                                    //No hay servicios de este tipo de solicitud
                                }
                            }else{
                                solicitudModels = new ArrayList<>();
                                adapter.setItems(solicitudModels);
                                lyNoServiciosDisponibles.setVisibility(View.VISIBLE);

                                Log.d(LOG_ACTIVITY, "No hay servicios ");
                                //No hay servicios de este tipo de solicitud
                            }

                        }else{
                            if (solicitudModels != null && solicitudModels.size() == 0)
                                lyNoServiciosDisponibles.setVisibility(View.VISIBLE);



                            Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_LONG).show();
                        }

                    }else{
                        finalizarSesion();
                    }


                }else{
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

                    Snackbar.make(getView(), strGeneralSinConexionServidor, Snackbar.LENGTH_LONG).show();

                }
            }
        });

        } else {


            Snackbar.make(getView(), strGeneralSinConexion, Snackbar.LENGTH_LONG).show();
        }

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


    // Metodo del evento de actualizar
    private void setupSwipeRefreshContainer() {

        swipeRefreshContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
            public void onRefresh() {

                Log.d(LOG_ACTIVITY, "sertupSwipeRefreshContainer");
                getListServiciosMasivos();

            }
        });
    }

    //metodo que limbia el count del menu de servicios disponibles
    private void resetNotificationMenuCountServicioDisponibleMasivo() {

        Preferences.savePreferences(getActivity(), DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_SOLICITUD_SERVICIO_MASIVO, "");
        NavigationView navView = getActivity().findViewById(R.id.navview);
        DependenciaJudicialUtils.showMenuCountBag(getActivity(), navView, R.id.menu_dependiente_item_mn_servicios_evento_procesal_masivo, "");
        DependenciaJudicialUtils.clearNotificacion(getActivity().getApplicationContext(), APP_CODE_ACCION_FCM_NUEVO_SERVICIO_MASIVO);

    }




    //Hacer uso del RecyclerView
/*    private void setUpRVAdapterExpansionPanels(View v){


        //fill with empty objects
        final List<Object> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Object());
        }
        adapter.setItems(list);
    }
*/

}
