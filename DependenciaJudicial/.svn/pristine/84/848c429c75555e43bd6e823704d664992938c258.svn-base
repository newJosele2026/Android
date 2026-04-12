package com.slt.dependenciajudicial.views.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.requests.models.FuenteExternaModel;

import java.util.List;

/**
 * Created by Nelsy Acuña on 07/03/2018.
 */

public class AdapterNombreFuenteExterna extends ArrayAdapter {

    Activity context;
    List<FuenteExternaModel> fuenteExternaModels;

    public AdapterNombreFuenteExterna(Activity context, int resource, List<FuenteExternaModel> fuenteExternaModels) {
        // TODO Auto-generated constructor stub
        super(context, resource, fuenteExternaModels);
        this.context = context;
        this.fuenteExternaModels = fuenteExternaModels;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent);

    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.spinner_content, null);
        TextView txtTipoImagen = (TextView) item.findViewById(R.id.spinner_content_txt);
        txtTipoImagen.setText(fuenteExternaModels.get(position).getTNombreFuente());
        return item;

    }
}