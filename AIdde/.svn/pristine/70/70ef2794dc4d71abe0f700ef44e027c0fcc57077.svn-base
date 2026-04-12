package com.slt.dependenciajudicial.views.activity;

import android.app.Service;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.app.Preferences;
import com.slt.dependenciajudicial.requests.SOService;
import com.slt.dependenciajudicial.requests.models.ChatModel;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.views.adapters.RVAdapterChat;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_CHAT;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_CURRENT_SCREEN;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_IIDSOLICITUD_TEM;

/**
 * Created by Nelsy Acuña on 02/02/2018.
 */

public class ActivityChat extends AppCompatActivity {


    private static final String LOG_ACTIVITY = "ActivityChat";
    private RVAdapterChat adapter;
    private List<ChatModel> chatModelList;
    private SOService apiService;


    //region init view

    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    @BindView(R.id.activity_chat_rv_container)
    RecyclerView rvChat;

    @BindView(R.id.activity_chat_edt_message)
    EditText edtMensaje;

    @BindView(R.id.activity_chat_imgb_send)
    ImageButton imgbSend;

    @BindView(R.id.activity_chat_swipe_refresh_container)
    SwipeRefreshLayout swipeRefreshContainer;

    //endregion

    //region Init String
    @BindString(R.string.activity_chat_str_bar_title)
    String strBarTitle;

    @BindString(R.string.general_sin_conexion)
    String strGeneralSinConexion;

    @BindString(R.string.activity_chat_str_msm_no_valido)
    String strMensajeNoValido;



    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        chatModelList = new ArrayList<>();

        setupToolbar(strBarTitle, true);
        setupSwipeRefreshContainer();
        setupRVChat();
        setupKeyBoard();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getUpdateListChatModel();
        resetNotificationCountChat();
        Preferences.savePreferences(this, SP_CURRENT_SCREEN, APP_SCREEN_ACTIVITY_CHAT);

        Log.d(LOG_ACTIVITY, "FragmentServiciosDisponibles onResume");
    }

    private void setupKeyBoard(){


        Log.d(LOG_ACTIVITY,"setupKeyBoard()");

        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status

                        Log.d(LOG_ACTIVITY,"isOpen"+isOpen);

                        if(isOpen){

                            if (chatModelList.size() > 0)
                                rvChat.scrollToPosition(chatModelList.size() - 1);
                        }
                    }
                });

    }

    private void setupSwipeRefreshContainer() {

        swipeRefreshContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.d(LOG_ACTIVITY, "sertupSwipeRefreshContainer");
                getUpdateListChatModel();

            }
        });

    }

    private void setupRVChat() {

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        // rvServicios.setHasFixedSize(true);
        rvChat.setLayoutManager(llm);
        adapter = new RVAdapterChat(this, chatModelList);
        rvChat.setAdapter(adapter);
    }

    private void getUpdateListChatModel() {

        if (DependenciaJudicialUtils.isConnected(this)) {


            swipeRefreshContainer.setRefreshing(true);

            // llamar a servicio que consulta los msm

            chatModelList = new ArrayList<>();

            try {
                SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                ChatModel chatModel = new ChatModel(true, "msm adsasd asd asdasdas dasdasd asdas asd asd asd asdasd asd a dsa   asda sadas dasd asda sasdis me ", new Date());
                ChatModel chatModel2 = new ChatModel(false, "ms  asda sda dasd asm a asdasd asda sda sasadsdasd asdasdas das  not  me ", parseador.parse("07/02/2018 09:02:00"));
                ChatModel chatModel3 = new ChatModel(true, "ms  asda sda dasd asm a asdasd asda sda sasadsdasd asdasdas das  not  me ", parseador.parse("06/02/2018 09:02:00"));
                ChatModel chatModel4 = new ChatModel(false, "ms  asda sda dasd asm a asdasd asda sda sasadsdasd asdasdas das  not  me ", parseador.parse("05/02/2018 09:02:00"));
                ChatModel chatModel5 = new ChatModel(false, "ms  asda sda dasd asm a asdasd asda sda sasadsdasd asdasdas das  not  me ", parseador.parse("20/01/2018 09:02:00"));

                chatModelList.add(chatModel);
                chatModelList.add(chatModel2);
                chatModelList.add(chatModel3);
                chatModelList.add(chatModel4);
                chatModelList.add(chatModel5);

            }catch (Exception e){

            }

            adapter.updateRVAdapterChat(chatModelList);
            swipeRefreshContainer.setRefreshing(false);


        } else {


            swipeRefreshContainer.setRefreshing(true);

            // llamar a los msm internos
            // Consultar base de datos interna

            ChatModel chatModel = new ChatModel(true, "Chat sin conexion´me");
            ChatModel chatModel2 = new ChatModel(false, "Chat sin conexion");

            chatModelList.add(chatModel);
            chatModelList.add(chatModel2);

            swipeRefreshContainer.setRefreshing(false);

            Snackbar.make(rvChat, strGeneralSinConexion, Snackbar.LENGTH_SHORT).show();


        }


    }

    public void setupToolbar(String tittle, boolean upButton) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle +" Soli. #"+Preferences.getPreferences(this,SP_IIDSOLICITUD_TEM));
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }


    private void resetNotificationCountChat() {

        Log.d(LOG_ACTIVITY, "FragmentServiciosDisponibles resetNotificationCountChat");

        String iIDSolicitud = Preferences.getPreferences(this,DependenciaJudicialUtils.SP_IIDSOLICITUD_TEM);
        Preferences.savePreferences(this, DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_CHAT+"_"+iIDSolicitud, "");
    }

    @OnClick(R.id.activity_chat_imgb_send)
    public void btnSendOnClick(View view) {


        if (DependenciaJudicialUtils.isConnected(this)) {

            //validar texto

            if (edtMensaje.getText().toString().length() != 0) {

                ChatModel chatModel = new ChatModel();
                chatModel.setMe(true);
                chatModel.settMensaje(edtMensaje.getText().toString());
                chatModel.setDtFechaMensaje(new Date());


                chatModelList.add(chatModel);

                if (chatModelList.size() > 0)
                    rvChat.scrollToPosition(chatModelList.size() - 1);

                edtMensaje.setText("");


            } else {

                // no hay texto para enviar
                Snackbar.make(rvChat, strMensajeNoValido, Snackbar.LENGTH_SHORT).show();

            }

        } else {

            // No hay conexion a internet
            Snackbar.make(rvChat, strGeneralSinConexion, Snackbar.LENGTH_SHORT).show();

        }


    }

}
