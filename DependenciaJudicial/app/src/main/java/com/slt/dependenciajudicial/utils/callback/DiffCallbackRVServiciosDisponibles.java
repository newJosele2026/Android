package com.slt.dependenciajudicial.utils.callback;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.slt.dependenciajudicial.requests.models.SolicitudModel;

import java.util.List;

/**
 * Created by Nelsy Acuña on 22/11/2017.
 */

public class DiffCallbackRVServiciosDisponibles extends DiffUtil.Callback {
    List<SolicitudModel> oldSolicitudModel;
    List<SolicitudModel> newSolicitudModel;

    public DiffCallbackRVServiciosDisponibles( List<SolicitudModel> oldSolicitudModel,List<SolicitudModel> newSolicitudModel) {
        this.newSolicitudModel = newSolicitudModel;
        this.oldSolicitudModel = oldSolicitudModel;
    }

    @Override
    public int getOldListSize() {
        return oldSolicitudModel.size();
    }

    @Override
    public int getNewListSize() {
        return newSolicitudModel.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        final SolicitudModel oldEmployee = oldSolicitudModel.get(oldItemPosition);
        final SolicitudModel newEmployee = newSolicitudModel.get(newItemPosition);

        return oldEmployee.getIIDSolicitud()==(newEmployee.getIIDSolicitud());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldSolicitudModel.get(oldItemPosition).equals(newSolicitudModel.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
