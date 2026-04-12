package com.slt.dependenciajudicial.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.requests.models.SolicitudModel;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.utils.callback.CallbackRVHistorialServicios;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by Nelsy Acuña on 22/11/2017.
 */

public class RVAdapterHistorialServicios extends RecyclerView.Adapter<RVAdapterHistorialServicios.HistorialServiciosViewHolder> {


    private List<SolicitudModel> solicitudModelList;
    private Context mContext;
    private CallbackRVHistorialServicios callbackRVHistorialServicios;

    public RVAdapterHistorialServicios(Context context, List<SolicitudModel> solicitudModelList, CallbackRVHistorialServicios callbackRVHistorialServicios) {
        this.solicitudModelList = solicitudModelList;
        this.mContext = context;
        this.callbackRVHistorialServicios = callbackRVHistorialServicios;
    }

    public class HistorialServiciosViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_historial_servicios_ciudad)
        TextView txtCiudad;

        @BindView(R.id.item_historial_servicios_despacho)
        TextView txtDespacho;

        @BindView(R.id.item_historial_servicios_numero_proceso)
        TextView txtNumeroProceso;

        @BindView(R.id.item_historial_servicios_tipo_servicio)
        TextView txtTipoServicio;

        @BindView(R.id.item_historial_servicios_detalle)
        TextView txtDescripcionServicio;

        @BindView(R.id.item_historial_servicios_fecha)
        TextView txtFecha;

        @BindView(R.id.item_historial_servicios_estado)
        TextView txtEstado;

        @BindView(R.id.item_historial_servicios_txt_fecha_asignacion)
        TextView txtdtFechaAsignacion;

        @BindView(R.id.item_historial_servicios_txt_fecha_cumplimiento)
        TextView txtdtFechaCumplimiento;

        @BindView(R.id.item_historial_servicios_txt_fecha_aprobacion)
        TextView txtdtFechaAprobacion;

        @BindView(R.id.item_historial_servicios_txt_fecha_pago)
        TextView txtdtFechaPago;

        @BindView(R.id.item_historial_servicios_card_contenido)
        CardView cardContainer;

        @BindView(R.id.item_historial_servicios_rb_calificacion)
        MaterialRatingBar rbCalificacion;

        //Inicializar vistas

        public HistorialServiciosViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }


    public void updateRVHistorialServicios2(List<SolicitudModel> viewModels) {
        this.solicitudModelList = viewModels;
        notifyDataSetChanged();
    }


    @Override
    public HistorialServiciosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_historial_servicios, viewGroup, false);

        return new HistorialServiciosViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final HistorialServiciosViewHolder customViewHolder, int i) {

        final SolicitudModel solicitudModel = solicitudModelList.get(i);

        //Asiganar datos a los controles
        customViewHolder.txtCiudad.setText(solicitudModel.getTCiudad());
        customViewHolder.txtDespacho.setText(solicitudModel.getTDespacho());
        customViewHolder.txtNumeroProceso.setText(Html.fromHtml(solicitudModel.getTNumeroProceso()));
        customViewHolder.txtTipoServicio.setText(solicitudModel.getTNombreSolicitud());
        customViewHolder.txtDescripcionServicio.setText(solicitudModel.getTDescripcionSolicitud());
        String strFecha = DependenciaJudicialUtils.getFormatoFechaDate(solicitudModel.getDtFechaSolicitud());

        customViewHolder.txtFecha.setText(strFecha);
        customViewHolder.txtEstado.setText(solicitudModel.gettEstadoSolicitud());


        //Validacion de fechas

        String strFechaAsignacion = DependenciaJudicialUtils.getFormatoFechaDateTime(solicitudModel.getDtFechaAsignacionSolicitud());
        String strFechaCumplimiento = DependenciaJudicialUtils.getFormatoFechaDateTime(solicitudModel.getDtFechaCumplimientoSolicitud());
        String strFechaAprobacion = DependenciaJudicialUtils.getFormatoFechaDateTime(solicitudModel.getDtFechaAprobacionSolicitud());
        String strFechaPago = DependenciaJudicialUtils.getFormatoFechaDateTime(solicitudModel.getDtFechaPagoSolicitud());

        customViewHolder.txtdtFechaAsignacion.setText(strFechaAsignacion);
        customViewHolder.txtdtFechaCumplimiento.setText(strFechaCumplimiento);
        customViewHolder.txtdtFechaAprobacion.setText(strFechaAprobacion);
        customViewHolder.txtdtFechaPago.setText(strFechaPago);
        customViewHolder.rbCalificacion.setRating(Float.parseFloat(solicitudModel.getdCalificacion().replace(",",".") ));


        customViewHolder.cardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callbackRVHistorialServicios.onCallbackRVHistorialServiciosTouchContainer(solicitudModel);

            }
        });

    }


    @Override
    public int getItemCount() {
        return (null != solicitudModelList ? solicitudModelList.size() : 0);
    }


}
