package com.slt.dependenciajudicial.views.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.requests.models.SolicitudModel;
import com.slt.dependenciajudicial.requests.models.SolicitudesProcesosNumeroMovimientoProcesalModel;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.utils.callback.CallbackRVServiciosAceptados;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nelsy Acuña on 22/11/2017.
 */

public class RVAdapterServiciosAceptados extends RecyclerView.Adapter<RVAdapterServiciosAceptados.ServiciosAceptadosViewHolder> {

    private static  final Integer INTERVALO_COUNTDOWN_TIMER=20000;

    private static final String LOG_ACTIVITY = "RVAServiciosAceptados";
    private List<SolicitudModel> solicitudModelList;
    private List<SolicitudesProcesosNumeroMovimientoProcesalModel> solicitudesNumeroMovimientoProcesalModelList;
    private Context mContext;
    private CallbackRVServiciosAceptados callbackRVServiciosAceptados;
    public List<CountDownTimer> listCountDownTimer;

    public RVAdapterServiciosAceptados(Context context, List<SolicitudModel> solicitudModelList, List<SolicitudesProcesosNumeroMovimientoProcesalModel> solicitudesNumeroMovimientoProcesalModelList , CallbackRVServiciosAceptados callbackRVServiciosAceptados) {
        this.solicitudModelList = solicitudModelList;
        this.solicitudesNumeroMovimientoProcesalModelList = solicitudesNumeroMovimientoProcesalModelList;
        this.mContext = context;
        this.callbackRVServiciosAceptados = callbackRVServiciosAceptados;
        listCountDownTimer = new ArrayList<>();

    }

    public class ServiciosAceptadosViewHolder extends RecyclerView.ViewHolder {

        @BindView( R.id.item_servicios_aceptados_ciudad)
        TextView txtCiudad;

        @BindView( R.id.item_servicios_aceptados_despacho)
        TextView txtDespacho;

        @BindView( R.id.item_servicios_aceptados_numero_proceso)
        TextView txtNumeroProceso;

        @BindView( R.id.item_servicios_aceptados_tipo_servicio)
        TextView txtTipoServicio;

        @BindView( R.id.item_servicios_aceptados_detalle)
        TextView txtDescripcionServicio;

        @BindView( R.id.item_servicios_aceptados_fecha)
        TextView txtFecha;

        @BindView( R.id.item_servicios_count_expire)
        TextView txtCountExpire;

        @BindView( R.id.item_servicios_aceptados_card_container)
        CardView cardContainer;

        @BindView( R.id.item_servicios_aceptados_actuaciones)
        TextView txtActuaciones;

        //Inicializar vistas

        public ServiciosAceptadosViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public void updateRVServiciosAceptado2(List<SolicitudModel> viewModels, List<SolicitudesProcesosNumeroMovimientoProcesalModel> viewModels2) {
        this.solicitudModelList = viewModels;
        this.solicitudesNumeroMovimientoProcesalModelList = viewModels2;

        for (CountDownTimer countDownTimer : listCountDownTimer) {
            countDownTimer.cancel();
        }
        listCountDownTimer = new ArrayList<>();
        notifyDataSetChanged();

    }


    @Override
    public ServiciosAceptadosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_servicios_aceptados, viewGroup, false);

        return new ServiciosAceptadosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServiciosAceptadosViewHolder customViewHolder, int i) {

        final SolicitudModel solicitudModel = solicitudModelList.get(i);
        StringBuilder stb = new StringBuilder();
        int y =1;
        if(solicitudModel.getIIDTipoSolicitud() != 6) {

            //Asiganar datos a los controles
            customViewHolder.txtCiudad.setText(solicitudModel.getTCiudad());
            customViewHolder.txtDespacho.setText(solicitudModel.getTDespacho());
            customViewHolder.txtNumeroProceso.setText(Html.fromHtml(solicitudModel.getTNumeroProceso()));
            customViewHolder.txtTipoServicio.setText(solicitudModel.getTNombreSolicitud());



            customViewHolder.txtDescripcionServicio.setText(solicitudModel.getTDescripcionSolicitud());
            String strFecha = DependenciaJudicialUtils.getFormatoFechaDate(solicitudModel.getDtFechaSolicitud());
            customViewHolder.txtFecha.setText(strFecha);



            Date dtFechaSolicitud = DependenciaJudicialUtils.getDateString(solicitudModel.getDtFechaSolicitud());

            Integer iTiempoSolicitudEnMinutos = DependenciaJudicialUtils.pasarTiempoSolicitudMinutos(solicitudModel.getiTiempoSolicitudDependiente(), solicitudModel.getTiIDUnidadTiempoDependiente());
            Log.d(LOG_ACTIVITY, "iTiempoSolicitudEnMinutos:" + iTiempoSolicitudEnMinutos);
            final Date dtFechaVenceSolicitud = DependenciaJudicialUtils.sumarMinutosFecha(dtFechaSolicitud, iTiempoSolicitudEnMinutos);
            Log.d(LOG_ACTIVITY, "dtFecha Asignacion:" + solicitudModel.getDtFechaSolicitud());
            Log.d(LOG_ACTIVITY, "dtFechaVenceSolicitud:" + dtFechaVenceSolicitud.toString());
            Log.d(LOG_ACTIVITY, "dtFechaSistema:" + new Date().toString());
            //Calcular la diferencia en segundos de la fecha actual y la fecha de vencimiento
            Long segundosDiferencia = DependenciaJudicialUtils.calcularDiferenciaFechasSegundos(new Date(), dtFechaVenceSolicitud);

            Log.d(LOG_ACTIVITY, "segundosDiferencia:" + segundosDiferencia);
            customViewHolder.txtCountExpire.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            if (segundosDiferencia > 0) {

                CountDownTimer countDownTimer = new CountDownTimer(segundosDiferencia * 1000, INTERVALO_COUNTDOWN_TIMER) {

                    public void onTick(long millisUntilFinished) {

                        String strFinal = DependenciaJudicialUtils.calcularDiferenciaFechasString(new Date(), dtFechaVenceSolicitud);
                        Log.d(LOG_ACTIVITY, solicitudModel.getIIDSolicitud() + " Falta: " + strFinal);
                        if(strFinal.equals("Expirado")){
                            customViewHolder.txtCountExpire.setTextColor(mContext.getResources().getColor(R.color.colorError));
                        }
                        customViewHolder.txtCountExpire.setText(Html.fromHtml(strFinal));
                    }

                    public void onFinish() {
                        customViewHolder.txtCountExpire.setText(R.string.item_servicios_aceptados_str_vigencia_expirada);
                        customViewHolder.txtCountExpire.setTextColor(mContext.getResources().getColor(R.color.colorError));
                    }
                }.start();

                this.listCountDownTimer.add(countDownTimer);
            } else {
                customViewHolder.txtCountExpire.setText(R.string.item_servicios_aceptados_str_vigencia_expirada);
                customViewHolder.txtCountExpire.setTextColor(mContext.getResources().getColor(R.color.colorError));
            }


            customViewHolder.cardContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callbackRVServiciosAceptados.onCallbackRVServicioAceptadosTouch(solicitudModel);
                }
            });

        }else{

            customViewHolder.txtNumeroProceso.setText(Html.fromHtml(solicitudModel.getTNumeroProceso()));
            customViewHolder.txtTipoServicio.setText(solicitudModel.getTNombreSolicitud());
            customViewHolder.txtDescripcionServicio.setText(solicitudModel.getTDescripcionSolicitud());
            solicitudesNumeroMovimientoProcesalModelList.size();

            customViewHolder.txtActuaciones.setText(Html.fromHtml(("<font color='#607d8b'>Actuaciones:</font>")));
          //  customViewHolder.txtSeparator.setText(Html.fromHtml("<font color='#b2b2b2'>____________________________________________</font>"));
            for(int count =0; count<solicitudesNumeroMovimientoProcesalModelList.size();count++){
                 //#607d8b accent color
                if(solicitudesNumeroMovimientoProcesalModelList.get(count).getiDSolicitud().equals(solicitudModel.getIIDSolicitud())){
                    stb.append("<b> Actuación #</b>");
                    stb.append("<b>"+ y +": </b>");
                    stb.append("<br>");
                    stb.append(  solicitudesNumeroMovimientoProcesalModelList.get(count).gettNumeroProceso());
                    stb.append("<br>");
                    stb.append( solicitudesNumeroMovimientoProcesalModelList.get(count).gettNombreDespacho() );
                    stb.append("<br>");
                    stb.append( solicitudesNumeroMovimientoProcesalModelList.get(count).gettEstructuraConceptualRelacionActuacion() );
                    stb.append("<br>");
                    stb.append("<b>" +solicitudesNumeroMovimientoProcesalModelList.get(count).gettMunicipio()+ "</b>"  );
                    stb.append("<br>");

                        stb.append("<font color='#D8D8D8'>______________________________________________</font>");

                    y++;
                }
            }

            Log.d(LOG_ACTIVITY, "Texto cuando ya esta " + stb.toString());

            customViewHolder.txtDespacho.setText(Html.fromHtml(stb.toString()));




            String strFecha = DependenciaJudicialUtils.getFormatoFechaDate(solicitudModel.getDtFechaSolicitud());
            customViewHolder.txtFecha.setText(strFecha);


            Date dtFechaSolicitud = DependenciaJudicialUtils.getDateString(solicitudModel.getDtFechaSolicitud());

            Integer iTiempoSolicitudEnMinutos = DependenciaJudicialUtils.pasarTiempoSolicitudMinutos(solicitudModel.getiTiempoSolicitudDependiente(), solicitudModel.getTiIDUnidadTiempoDependiente());
            Log.d(LOG_ACTIVITY, "iTiempoSolicitudEnMinutos:" + iTiempoSolicitudEnMinutos);
            final Date dtFechaVenceSolicitud = DependenciaJudicialUtils.sumarMinutosFecha(dtFechaSolicitud, iTiempoSolicitudEnMinutos);
            Log.d(LOG_ACTIVITY, "dtFecha Asignacion:" + solicitudModel.getDtFechaSolicitud());
            Log.d(LOG_ACTIVITY, "dtFechaVenceSolicitud:" + dtFechaVenceSolicitud.toString());
            Log.d(LOG_ACTIVITY, "dtFechaSistema:" + new Date().toString());
            //Calcular la diferencia en segundos de la fecha actual y la fecha de vencimiento
            Long segundosDiferencia = DependenciaJudicialUtils.calcularDiferenciaFechasSegundos(new Date(), dtFechaVenceSolicitud);

            Log.d(LOG_ACTIVITY, "segundosDiferencia:" + segundosDiferencia);
            customViewHolder.txtCountExpire.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            if (segundosDiferencia > 0) {

                CountDownTimer countDownTimer = new CountDownTimer(segundosDiferencia * 1000, INTERVALO_COUNTDOWN_TIMER) {

                    public void onTick(long millisUntilFinished) {

                        String strFinal = DependenciaJudicialUtils.calcularDiferenciaFechasString(new Date(), dtFechaVenceSolicitud);
                        Log.d(LOG_ACTIVITY, solicitudModel.getIIDSolicitud() + " Falta: " + strFinal);
                        if(strFinal.equals("Expirado")){
                            customViewHolder.txtCountExpire.setTextColor(mContext.getResources().getColor(R.color.colorError));
                        }
                        customViewHolder.txtCountExpire.setText(Html.fromHtml(strFinal));
                    }

                    public void onFinish() {
                        customViewHolder.txtCountExpire.setText(R.string.item_servicios_aceptados_str_vigencia_expirada);
                        customViewHolder.txtCountExpire.setTextColor(mContext.getResources().getColor(R.color.colorError));
                    }
                }.start();

                this.listCountDownTimer.add(countDownTimer);
            } else {
                customViewHolder.txtCountExpire.setText(R.string.item_servicios_aceptados_str_vigencia_expirada);
                customViewHolder.txtCountExpire.setTextColor(mContext.getResources().getColor(R.color.colorError));
            }


            customViewHolder.cardContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callbackRVServiciosAceptados.onCallbackRVServicioAceptadosTouch(solicitudModel);
                }
            });


        }


    }


    @Override
    public int getItemCount() {
        return (null != solicitudModelList ? solicitudModelList.size() : 0);
    }


}
