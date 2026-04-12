package com.slt.dependenciajudicial.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.requests.models.SoportesModel;
import com.slt.dependenciajudicial.requests.models.SoportesModel;
import com.slt.dependenciajudicial.utils.callback.CallbackRVSoportes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by Nelsy Acuña on 22/11/2017.
 */

public class RVAdapterSoportes extends RecyclerView.Adapter<RVAdapterSoportes.SoportesViewHolder> {


    private List<SoportesModel> soportesModels;
    private Context mContext;
    private CallbackRVSoportes callbackRVSoportes;


    public RVAdapterSoportes(Context context, List<SoportesModel> soportesModels, CallbackRVSoportes callbackRVSoportes) {
        this.soportesModels = soportesModels;
        this.mContext = context;
        this.callbackRVSoportes = callbackRVSoportes;

    }

    public class SoportesViewHolder extends RecyclerView.ViewHolder {

        @BindView( R.id.item_soporte_img)
        ImageView imgFormato;

        @BindView( R.id.item_soporte_nombre)
        TextView txtNombreArchivo;

        @BindView( R.id.item_soporte_ly_container)
        LinearLayout lyContenido;

        @BindView( R.id.item_soporte_delete)
        ImageView imgDelete;

        //Inicializar vistas

        public SoportesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public void updateRVAdapterSoportes(List<SoportesModel> viewModels) {
        this.soportesModels = viewModels;
        notifyDataSetChanged();
    }


    @Override
    public SoportesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_soportes, viewGroup, false);

        return new SoportesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SoportesViewHolder customViewHolder, int i) {

        final SoportesModel soportesModel = soportesModels.get(i);

        //Asiganar datos a los controles
        customViewHolder.txtNombreArchivo.setText(soportesModel.gettNombreArchivo());

        if (soportesModel.gettExtension().equals("pdf")) {

            //customViewHolder.imgFormato.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
            customViewHolder.imgFormato.setImageResource(R.drawable.ic_action_picture_as_pdf);
        }


        customViewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callbackRVSoportes.onCallbackSoportesBtnDelete(soportesModel);
            }
        });


        customViewHolder.lyContenido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callbackRVSoportes.onCallbackSoportesTouchContent(soportesModel);
            }
        });

    }


    @Override
    public int getItemCount() {
        return (null != soportesModels ? soportesModels.size() : 0);
    }


}
