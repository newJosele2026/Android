package com.slt.dependenciajudicial.views.fragments;


import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.slt.dependenciajudicial.BuildConfig;
import com.slt.dependenciajudicial.R;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.slt.dependenciajudicial.app.Preferences.savePreferences;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_ACERCA_DE;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_CURRENT_SCREEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAcercaDe extends Fragment {

    private static final String LOG_ACTIVITY = "FAcercaDe";
    private DrawerLayout drawer;

    //region Init view

    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    @BindView(R.id.fragment_acerca_de_version)
    TextView txtVersion;

    @BindView(R.id.fragment_acerca_de_ly_preguntas_frecuentes)
    LinearLayout ly_preguntas_frecuentes;

    @BindView(R.id.fragment_acerca_de_ly_contactanos)
    LinearLayout ly_contactanos;

    @BindView(R.id.fragment_acerca_de_ly_terminos)
    LinearLayout ly_terminos;

    @BindView(R.id.fragment_acerca_de_ly_licencias)
    LinearLayout ly_licencias;

    //endregion


    @BindString(R.string.fragment_acerca_de_str_title_bar)
    String strBarTitulo;


    public FragmentAcercaDe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_acerca_de, container, false);
        ButterKnife.bind(this, root);
        setupToolBar();
        setupDrawerLayout();

        setupFragment();
        return root;
    }

    private void setupFragment() {

        String versionCode;
        try {

            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);

            Log.d(LOG_ACTIVITY, "PackageInfo.versionName:" + packageInfo.versionName);
            Log.d(LOG_ACTIVITY, "BuildConfig.VERSION_NAME:" + BuildConfig.VERSION_NAME);

            versionCode = "Versión " + BuildConfig.VERSION_NAME;

        } catch (Exception e) {
            versionCode = "v 0.0.21";
        }
        txtVersion.setText(versionCode);


        ly_preguntas_frecuentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Preguntas Frecuentes",Toast.LENGTH_SHORT).show();
            }
        });


        ly_contactanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Contáctanos ",Toast.LENGTH_SHORT).show();
            }
        });

        ly_terminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Terminos y provacidad",Toast.LENGTH_SHORT).show();
            }
        });

        ly_licencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Licencia",Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void setupDrawerLayout() {
        drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupToolBar() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(strBarTitulo);
    }

    @Override
    public void onResume() {
        super.onResume();

        setupToolBar();
        setupDrawerLayout();

        savePreferences(getActivity(), SP_CURRENT_SCREEN, APP_SCREEN_FRAGMENT_ACERCA_DE);

        Log.d(LOG_ACTIVITY, "FragmentHistorialServicios onResume");
    }
}
