package com.slt.dependenciajudicial.views.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.app.database.tables.CiudadBD;


import java.util.List;

/**
 * Created by Sergio on 12/11/17.
 */

public class AdapterCiudad extends ArrayAdapter {

    Activity context;
    List<CiudadBD> ciudadBDS;

    public AdapterCiudad(Activity context, int resource, List<CiudadBD> ciudadBDS) {
        // TODO Auto-generated constructor stub
        super(context, resource, ciudadBDS);
        this.context = context;
        this.ciudadBDS = ciudadBDS;
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
        txtTipoImagen.setText(ciudadBDS.get(position).gettNombreCiudad());
        return item;

    }
}