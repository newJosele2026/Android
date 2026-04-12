package com.slt.dependenciajudicial.views.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.requests.models.SolicitudModel;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.utils.callback.CallbackRVServiciosDisponibles;
import com.slt.dependenciajudicial.utils.callback.DiffCallbackRVServiciosDisponibles;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nelsy Acuña on 22/11/2017.
 */

public class RVAdapterServiciosDisponible extends RecyclerView.Adapter<RVAdapterServiciosDisponible.ServiciosDisponiblesViewHolder> {

    private static final String LOG_ACTIVITY="RVAServiciosDisponible";

    private List<SolicitudModel> solicitudModelList;
    private Context mContext;
    private CallbackRVServiciosDisponibles callbackRVServiciosDisponibles;

    public RVAdapterServiciosDisponible(Context context, List<SolicitudModel> solicitudModelList, CallbackRVServiciosDisponibles callbackRVServiciosDisponibles) {
        this.solicitudModelList = solicitudModelList;
        this.mContext = context;
        this.callbackRVServiciosDisponibles = callbackRVServiciosDisponibles;
    }

    public class ServiciosDisponiblesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_servicios_disponibles_ciudad)
        TextView txtCiudad;

        @BindView(R.id.item_servicios_disponibles_despacho)
        TextView txtDespacho;

        @BindView(R.id.item_servicios_disponibles_numero_proceso)
        TextView txtNumeroProceso;

        @BindView(R.id.item_servicios_disponibles_tipo_servicio)
        TextView txtTipoServicio;

        @BindView(R.id.item_servicios_disponibles_detalle)
        TextView txtDescripcionServicio;

        @BindView(R.id.item_servicios_disponibles_fecha)
        TextView txtFecha;

        @BindView(R.id.item_servicios_disponibles_atender_servicio)
        Button btnTomarServicio;

        @BindView(R.id.item_servicios_disponibles_spn_tiempo)
        AppCompatSpinner spnTiempo;

        @BindView(R.id.item_servicios_disponibles_spn_unidad)
        AppCompatSpinner spnUnidad;

        //Inicializar vistas

        public ServiciosDisponiblesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

 //   public void updateRVServiciosDisponible(List<SolicitudModel> solicitudModelList) {
  //      DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallbackRVServiciosDisponibles(solicitudModelList, this.solicitudModelList));
 //       this.solicitudModelList.clear();
 //       this.solicitudModelList.addAll(solicitudModelList);
 //       diffResult.dispatchUpdatesTo(this);
//    }

  //  public void updateEmployeeListItems(List<SolicitudModel> employees) {
  //      final DiffCallbackRVServiciosDisponibles diffCallback = new DiffCallbackRVServiciosDisponibles(this.solicitudModelList, employees);
  //      final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
   //     diffResult.dispatchUpdatesTo(this);
 //       this.solicitudModelList.clear();
 //       this.solicitudModelList.addAll(employees);
  //  }

    public void updateRVServiciosDisponible2(List<SolicitudModel> viewModels) {
        this.solicitudModelList = viewModels;
        notifyDataSetChanged();
    }


    @Override
    public ServiciosDisponiblesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_servicios_disponibles, viewGroup, false);

        return new ServiciosDisponiblesViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final ServiciosDisponiblesViewHolder customViewHolder, int i) {

        final SolicitudModel solicitudModel = solicitudModelList.get(i);

        //Asiganar datos a los controles
        customViewHolder.txtCiudad.setText(solicitudModel.getTCiudad());
        customViewHolder.txtDespacho.setText(solicitudModel.getTDespacho());
        customViewHolder.txtNumeroProceso.setText(Html.fromHtml(solicitudModel.getTNumeroProceso()));
        customViewHolder.txtTipoServicio.setText(solicitudModel.getTNombreSolicitud());
        customViewHolder.txtDescripcionServicio.setText(solicitudModel.getTDescripcionSolicitud());
        final String strFecha = DependenciaJudicialUtils.getFormatoFechaDate(solicitudModel.getDtFechaSolicitud());
        customViewHolder.txtFecha.setText(strFecha);

        Date dtFechaSolicitud = DependenciaJudicialUtils.getDateString(solicitudModel.getDtFechaSolicitud());

        Log.d(LOG_ACTIVITY,"dtFechaSolicitud:"+dtFechaSolicitud.toString());

        final AppCompatSpinner spnTiempo = customViewHolder.spnTiempo;
        final AppCompatSpinner spnUnidad = customViewHolder.spnUnidad;

        spnTiempo.setAdapter(new ArrayAdapter<String>(mContext, R.layout.dropdown_simple_content, DependenciaJudicialUtils.arrTiempo));
        spnUnidad.setAdapter(new ArrayAdapter<String>(mContext, R.layout.dropdown_simple_content, DependenciaJudicialUtils.arrUnidad));

        customViewHolder.btnTomarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callbackRVServiciosDisponibles.onCallbackRVServicioDisponibleBtnTomar(solicitudModel, spnTiempo, spnUnidad);
            }
        });
    }


    @Override
    public int getItemCount() {
        return (null != solicitudModelList ? solicitudModelList.size() : 0);
    }


}
