package com.slt.dependenciajudicial.views.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.requests.models.SolicitudModel;
import com.slt.dependenciajudicial.requests.models.SolicitudesProcesosNumeroMovimientoProcesalModel;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.utils.callback.CallbackRVServiciosDisponibles;
import com.slt.dependenciajudicial.utils.callback.CallbackServiciosDisponiblesMasivos;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RVAdapterExpansionPanel extends RecyclerView.Adapter<RVAdapterExpansionPanel.RecyclerHolder> {

    private List<SolicitudModel> list;

    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();

    private Context mContext;
    private CallbackServiciosDisponiblesMasivos callbackRVServiciosDisponiblesMasivos;


    public RVAdapterExpansionPanel(Context context, List<SolicitudModel> solicitudModelList, CallbackServiciosDisponiblesMasivos callbackRVServiciosDisponiblesMasivos) {

        this.list = solicitudModelList;
        this.mContext = context;
        this.callbackRVServiciosDisponiblesMasivos = callbackRVServiciosDisponiblesMasivos;

        expansionsCollection.openOnlyOne(true);
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      //  return RecyclerHolder.buildFor(parent);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_solicitudes, parent, false);

        return new RecyclerHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, int position) {

        final SolicitudModel solicitudModel = list.get(position);

        holder.strHeader.setText( "Solicitud Masiva en: ");
        holder.strSecHeader.setText(solicitudModel.getTCiudad());
        holder.strContent.setText("");
        solicitudModel.getDtFechaSolicitud();

            if(solicitudModel.getListActuaciones() != null){
                int y =1;

                holder.strContent.append(Html.fromHtml("<h2><font color='#009688'>"+ solicitudModel.getTNombreSolicitud() + "</font></h2>"  ));

                holder.strContent.append(Html.fromHtml("<h4><font color='#607d8b'>"+solicitudModel.getTNumeroProceso() +"</font></h4>"));

                int size = solicitudModel.getListActuaciones().size();
                holder.strContent.append("\n\n");
                for(int i =0; i<size; i++) {
                  SolicitudesProcesosNumeroMovimientoProcesalModel p = solicitudModel.getListActuaciones().get(i);

                    holder.strContent.append(Html.fromHtml("<b>Actuación #" + y + ":  </b>"));
                    holder.strContent.append("\n");


                    holder.strContent.append(p.gettNombreDespacho());
                    holder.strContent.append(Html.fromHtml("<br>"));
                    holder.strContent.append(p.gettMunicipio());
                    holder.strContent.append(Html.fromHtml("<br>"));
                    holder.strContent.append(p.gettEstructuraConceptualRelacionActuacion());
                    holder.strContent.append(Html.fromHtml("<br>"));
                    if( !((i + 1) == size) ){
                        holder.strContent.append("__________________________________________________" + "\n");
                    }
                    holder.strContent.append("\n");
                y++;
                }

            }
        final AppCompatSpinner spnTiempo = holder.spnTiempo;
        final AppCompatSpinner spnUnidad = holder.spnUnidad;

        spnTiempo.setAdapter(new ArrayAdapter<String>(mContext, R.layout.dropdown_simple_content, DependenciaJudicialUtils.arrTiempo));
        spnUnidad.setAdapter(new ArrayAdapter<String>(mContext, R.layout.dropdown_simple_content, DependenciaJudicialUtils.arrUnidad));

        expansionsCollection.add(holder.getExpansionLayout());

        holder.btnTomarServicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                callbackRVServiciosDisponiblesMasivos.onCallbackRVServicioDisponibleBtnTomar(solicitudModel, spnTiempo, spnUnidad);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<SolicitudModel> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    public final static class RecyclerHolder extends RecyclerView.ViewHolder {

        //private static final int LAYOUT = R.layout.item_solicitudes;

        @BindView(R.id.expansionLayout)
        ExpansionLayout expansionLayout;

        @BindView(R.id.str_header)
        TextView strHeader;

        @BindView(R.id.second_header)
        TextView strSecHeader;

        @BindView(R.id.str_content_actuacion)
        TextView strContent;

        @BindView(R.id.item_servicios_disponibles_atender_servicio_masivo)
        Button btnTomarServicio;

        @BindView(R.id.item_servicios_disponibles_spn_tiempo_masivo)
        AppCompatSpinner spnTiempo;

        @BindView(R.id.item_servicios_disponibles_spn_unidad_masivo)
        AppCompatSpinner spnUnidad;


        //  public static RecyclerHolder buildFor(ViewGroup viewGroup){
    //        return new RecyclerHolder(LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
      //  }

        public RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Object object){
            expansionLayout.collapse(false);
        }

        public ExpansionLayout getExpansionLayout() {
            return expansionLayout;
        }
    }
}