package com.slt.dependenciajudicial.utils.callback;

import android.support.v7.widget.AppCompatSpinner;

import com.slt.dependenciajudicial.requests.models.SolicitudModel;

public interface CallbackServiciosDisponiblesMasivos {
    void onCallbackRVServicioDisponibleBtnTomar(SolicitudModel itemModel, AppCompatSpinner spnTiempo, AppCompatSpinner spnUnidad);
}
