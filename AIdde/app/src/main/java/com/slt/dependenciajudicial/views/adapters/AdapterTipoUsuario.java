package com.slt.dependenciajudicial.views.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.app.database.tables.CiudadBD;
import com.slt.dependenciajudicial.app.database.tables.TipoUsuarioBD;

import java.util.List;

/**
 * Created by Sergio on 12/11/17.
 */

public class AdapterTipoUsuario extends ArrayAdapter {

    Activity context;
    List<TipoUsuarioBD> tipoUsuarioBDS;

    public AdapterTipoUsuario(Activity context, int resource, List<TipoUsuarioBD> tipoUsuarioBDS) {
        // TODO Auto-generated constructor stub
        super(context, resource, tipoUsuarioBDS);
        this.context = context;
        this.tipoUsuarioBDS = tipoUsuarioBDS;
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
        txtTipoImagen.setText(tipoUsuarioBDS.get(position).gettNombreTipoUsuario());
        return item;

    }
}