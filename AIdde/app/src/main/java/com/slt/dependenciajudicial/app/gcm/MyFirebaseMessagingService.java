package com.slt.dependenciajudicial.app.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.app.Preferences;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.views.activity.ActivityChat;
import com.slt.dependenciajudicial.views.activity.ActivityGestionarSolicitud;
import com.slt.dependenciajudicial.views.activity.ActivityHome;

import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_CODE_ACCION_FCM_CHAT;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_CODE_ACCION_FCM_NUEVO_SERVICIO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_CODE_ACCION_FCM_NUEVO_SERVICIO_MASIVO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_CODE_ACCION_FCM_RECORDATORIO_SERVICIO_DISPONIBLE;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_CODE_ACCION_FCM_RECORDATORIO_SERVICIO_DISPONIBLE_MASIVO;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_CODE_ACCION_FCM_SERVICIO_ASIGNACION;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_CHAT;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_GESTIONAR_SOLICITUD;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_SERVICIOS_ACEPTADOS;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_SERVICIOS_MASIVOS;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.CODE_CHAT;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_CURRENT_SCREEN;
import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.SP_IIDSOLICITUD_TEM;

/**
 * Created by Nelsy Acuña on 20/11/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String LOG_SERVICE = "FMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(LOG_SERVICE, remoteMessage.getNotification().getBody());


        try {

            String strToken = Preferences.getPreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_TOKEN);

            //Validar que el usuario este en sesion
            if (strToken != "") {

                Log.d(LOG_SERVICE, "Notificacion en sesion ");

                // Seleccionar el estado de la aplicacion , la pantalla actual y la accion de la notificacion
                String iAppAccion = remoteMessage.getData().get("iAccion");
                String strPantallaActual = Preferences.getPreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_CURRENT_SCREEN);


                //accciones de notificaion por tipo usuario
                int iIDTipoUsuario = Integer.parseInt(Preferences.getPreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_ID_TIPO_USUARIO));

                switch (iIDTipoUsuario) {

                    case 1:

                        //region Validacion de acciones para el tipo usaurio 1 // Dependiente Judicial


                        if (iAppAccion.equals(APP_CODE_ACCION_FCM_NUEVO_SERVICIO)) { // Validar si la notificacion es de solicitud notificada

                            onReceivedFcmNuevoServicio(remoteMessage, strPantallaActual);

                        } else if (iAppAccion.equals(APP_CODE_ACCION_FCM_RECORDATORIO_SERVICIO_DISPONIBLE)) { // validar si la notificacion es de recordatorio

                            onReceivedFcmRecordatoriServicioDisponible(remoteMessage, strPantallaActual);

                        } else if (iAppAccion.equals(APP_CODE_ACCION_FCM_SERVICIO_ASIGNACION)) {

                            onReceivedFcmServicioAsignacion(remoteMessage, strPantallaActual);

                        } else if (iAppAccion.equals(APP_CODE_ACCION_FCM_CHAT)) {

                            onReceivedFcmChat(remoteMessage, strPantallaActual);

                        } else if(iAppAccion.equals(APP_CODE_ACCION_FCM_RECORDATORIO_SERVICIO_DISPONIBLE_MASIVO)){

                            onReceivedFcmRecordatoriServicioDisponibleMasivo(remoteMessage, strPantallaActual);

                        } else if(iAppAccion.equals(APP_CODE_ACCION_FCM_NUEVO_SERVICIO_MASIVO)){

                            onReceivedFcmNuevoServicioMasivo(remoteMessage, strPantallaActual);

                        }

                        //endregion


                        break;
                    case 2:

                        //region Validacion de acciones para el tipo usaurio 2 // Solicitante
                        //endregion

                        break;
                }

                //sendNotification(remoteMessage.getNotification().getBody());
            } else {

                Log.e(LOG_SERVICE, "Notificacion sin sesion ");
            }

        } catch (Exception e) {

        }

    }

    //region APP_CODE_ACCION_FCM_CHAT

    private void onReceivedFcmChat(RemoteMessage remoteMessage, String strPantallaActual) {

        Log.d(LOG_SERVICE, "strPantallaActual : " + strPantallaActual);

        String iIDSolicitud = remoteMessage.getData().get("iIDDJSolicitud");

        //validar pantalla
        if (strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_CHAT)) {

            Log.d(LOG_SERVICE, "onReceivedFcmChat en " + APP_SCREEN_ACTIVITY_CHAT);
            Log.d(LOG_SERVICE, "iIDSolicitud :" + iIDSolicitud);
            //validar usuario

            if (!Preferences.getPreferencesContext(getBaseContext(), DependenciaJudicialUtils.SP_IIDSOLICITUD_TEM).equals("") && Preferences.getPreferencesContext(getBaseContext(), DependenciaJudicialUtils.SP_IIDSOLICITUD_TEM).equals(iIDSolicitud)) {

                Log.d(LOG_SERVICE, "onReceivedFcmChat : en pantalla de chat de la solicitud actual ");

                ejecutarOnResumentActivityChat();

            } else {

                Log.d(LOG_SERVICE, "onReceivedFcmChat : en pantalla de chat de otra solicitud");

                setMenuCountChat(iIDSolicitud);
                sendNotificationChat(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), iIDSolicitud);

            }

        } else {

            Log.d(LOG_SERVICE, "onReceivedFcmChat  en pantalla diferente a " + APP_SCREEN_ACTIVITY_CHAT);

            setMenuCountChat(iIDSolicitud);

            if (strPantallaActual.equals(APP_SCREEN_ACTIVITY_GESTIONAR_SOLICITUD)) {

                // Actualizar pantalla de gestion

                Log.d(LOG_SERVICE, "onReceivedFcmChat  en pantalla : " + APP_SCREEN_ACTIVITY_GESTIONAR_SOLICITUD);

                if (!Preferences.getPreferencesContext(getBaseContext(), DependenciaJudicialUtils.SP_IIDSOLICITUD_TEM).equals("") && Preferences.getPreferencesContext(getBaseContext(), DependenciaJudicialUtils.SP_IIDSOLICITUD_TEM).equals(iIDSolicitud)) {

                    Log.d(LOG_SERVICE, "onReceivedFcmChat  Mensaje de chat en la pantalla de la gestion de la misma solicitud");
                    ejecutarOnResumentActivityGestionarSolicitud();

                } else {

                    Log.d(LOG_SERVICE, "onReceivedFcmChat  Mensaje de chat en la pantalla de la gestion de otra solicitud");
                    sendNotificationChat(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), iIDSolicitud);
                }

            } else {

                sendNotificationChat(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), iIDSolicitud);

            }

        }

    }

    private void sendNotificationChat(String title, String body, String iIDDJSolicitudes) {

        Log.d(LOG_SERVICE, "sendNotificationServicioAsignado iIDDJSolicitudes:" + iIDDJSolicitudes);

        Preferences.savePreferencesContext(getBaseContext(), SP_IIDSOLICITUD_TEM, iIDDJSolicitudes);

        Intent intent = new Intent(this, ActivityChat.class);
        intent.putExtra(SP_CURRENT_SCREEN, APP_SCREEN_ACTIVITY_CHAT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, Integer.parseInt((CODE_CHAT + iIDDJSolicitudes))/* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //  String channelId = getString(R.string.default_notification_channel_id);
        // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "App")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(Integer.parseInt((CODE_CHAT + iIDDJSolicitudes)), notificationBuilder.build());

    }

    private void setMenuCountChat(String iIDDJSolicitudes) {

        String srtExtraAddBagAlertChat = Preferences.getPreferencesContext(getApplicationContext(), (DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_CHAT + "_" + iIDDJSolicitudes));

        if (!srtExtraAddBagAlertChat.equals("")) {
            srtExtraAddBagAlertChat = "" + (Integer.parseInt(srtExtraAddBagAlertChat) + 1);
            Preferences.savePreferencesContext(getApplicationContext(), (DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_CHAT + "_" + iIDDJSolicitudes), srtExtraAddBagAlertChat);
        } else {
            Preferences.savePreferencesContext(getApplicationContext(), (DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_CHAT + "_" + iIDDJSolicitudes), "1");
        }
    }


    //endregion

    //region APP_CODE_ACCION_FCM_SERVICIO_ASIGNACION


    //Metodo que se ejecuta cuando el app esta en pantalla
    // y el iAccion  de la notificacion es APP_CODE_ACCION_FCM_SERVICIO_ASIGNACION=3
    private void onReceivedFcmServicioAsignacion(RemoteMessage remoteMessage, String strPantallaActual) {

        Log.d(LOG_SERVICE, "strPantallaActual : " + strPantallaActual);

        if (strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_SERVICIOS_ACEPTADOS)) {

            Log.d(LOG_SERVICE, "onReceivedAccionPrueba en " + APP_SCREEN_FRAGMENT_SERVICIOS_ACEPTADOS);

            //Ejecutar el onResument del fragmen en pantalla
            ejecutarOnResumentActivityHome();

        } else if (strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_CHAT) ||
                strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_GESTIONAR_SOLICITUD)) {

            //Sumar al count del menu
            setMenuCountServiciosAceptados();

            //Crear notificacion
            sendNotificationServicioAsignado(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData().get("iIDDJSolicitud"));

        } else {

            Log.d(LOG_SERVICE, "onReceivedAccionPrueba  en pantalla diferente a " + APP_SCREEN_FRAGMENT_SERVICIOS_ACEPTADOS);

            //Sumar al count del menu
            setMenuCountServiciosAceptados();

            //Ejecutar el onResument del fragmen en pantalla
            ejecutarOnResumentActivityHome();

            //Crear notificacion
            sendNotificationServicioAsignado(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData().get("iIDDJSolicitud"));


        }


    }

    private void sendNotificationServicioAsignado(String title, String body, String iIDDJSolicitudes) {

        Log.d(LOG_SERVICE, "sendNotificationServicioAsignado iIDDJSolicitudes:" + iIDDJSolicitudes);

        Intent intent = new Intent(this, ActivityHome.class);
        intent.putExtra(SP_CURRENT_SCREEN, APP_SCREEN_FRAGMENT_SERVICIOS_ACEPTADOS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, Integer.parseInt(iIDDJSolicitudes)/* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //  String channelId = getString(R.string.default_notification_channel_id);
        // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "App")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(Integer.parseInt(iIDDJSolicitudes), notificationBuilder.build());

    }


    //endregion

    //region APP_CODE_ACCION_FCM_NUEVO_SERVICIO y APP_CODE_ACCION_FCM_RECORDATORIO_SERVICIO_DISPONIBLE

    //Metodo que se ejecuta cuando el app esta en pantalla
            // y el iAccion  de la notificacion es APP_CODE_ACCION_FCM_NUEVO_SERVICIO=1
            public void onReceivedFcmNuevoServicio(RemoteMessage remoteMessage, String strPantallaActual) {

                Log.d(LOG_SERVICE, "strPantallaActual : " + strPantallaActual);

                if (strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES)) {

                    Log.d(LOG_SERVICE, "onReceivedAccionPrueba en " + APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES);

                    //Ejecutar el onResument del fragmen en pantalla
                    ejecutarOnResumentActivityHome();

        } else if (strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_CHAT) ||
                strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_GESTIONAR_SOLICITUD)) {

            //Sumar al count del menu
            setMenuCountServiciosDisponibles();

            sendNotificationNuevoServicio(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());


        } else {

            Log.d(LOG_SERVICE, "onReceivedAccionPrueba  en pantalla diferente a " + APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES);

            //Sumar al count del menu
            setMenuCountServiciosDisponibles();

            //Ejecutar el onResument del fragmen en pantalla
            ejecutarOnResumentActivityHome();

            //Crear notificacion
            sendNotificationNuevoServicio(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        }

    }



    //region APP_CODE_ACCION_FCM_NUEVO_SERVICIO y APP_CODE_ACCION_FCM_RECORDATORIO_SERVICIO_DISPONIBLE

    //Metodo que se ejecuta cuando el app esta en pantalla
    // y el iAccion  de la notificacion es APP_CODE_ACCION_FCM_NUEVO_SERVICIO=1
    public void onReceivedFcmNuevoServicioMasivo(RemoteMessage remoteMessage, String strPantallaActual) {

        Log.d(LOG_SERVICE, "strPantallaActual : " + strPantallaActual);

        if (strPantallaActual.equals(DependenciaJudicialUtils.
                APP_SCREEN_FRAGMENT_SERVICIOS_MASIVOS)) {

            Log.d(LOG_SERVICE, "onReceivedAccionPrueba en " + APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES);

            //Ejecutar el onResument del fragmen en pantalla
            ejecutarOnResumentActivityHome();

        } else if (strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_CHAT) ||
                strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_GESTIONAR_SOLICITUD)) {

            //Sumar al count del menu
            setMenuCountServiciosDisponiblesMasivo();

            sendNotificationNuevoServicioMasivo(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());


        } else {

            Log.d(LOG_SERVICE, "onReceivedAccionPrueba  en pantalla diferente a " + APP_SCREEN_FRAGMENT_SERVICIOS_MASIVOS);

            //Sumar al count del menu
            setMenuCountServiciosDisponiblesMasivo();

            //Ejecutar el onResument del fragmen en pantalla
            ejecutarOnResumentActivityHome();

            //Crear notificacion
            sendNotificationNuevoServicioMasivo(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        }

    }

    //Metodo que muestra la notificacion cuando el app esta en pantalla para
    // los iAccion APP_CODE_ACCION_FCM_NUEVO_SERVICIO_MASIVO
    private void sendNotificationNuevoServicioMasivo(String title, String body) {
        Intent intent = new Intent(this, ActivityHome.class);
        intent.putExtra(SP_CURRENT_SCREEN, APP_SCREEN_FRAGMENT_SERVICIOS_MASIVOS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, Integer.parseInt(APP_CODE_ACCION_FCM_NUEVO_SERVICIO_MASIVO)/* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //  String channelId = getString(R.string.default_notification_channel_id);
        // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "App")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(Integer.parseInt(APP_CODE_ACCION_FCM_NUEVO_SERVICIO_MASIVO), notificationBuilder.build());
    }




    //Metodo que muestra la notificacion cuando el app esta en pantalla para
    // los iAccion APP_CODE_ACCION_FCM_NUEVO_SERVICIO y APP_CODE_ACCION_FCM_NUEVO_SERVICIO_HILO
    private void sendNotificationNuevoServicio(String title, String body) {
        Intent intent = new Intent(this, ActivityHome.class);
        intent.putExtra(SP_CURRENT_SCREEN, APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, Integer.parseInt(APP_CODE_ACCION_FCM_NUEVO_SERVICIO)/* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //  String channelId = getString(R.string.default_notification_channel_id);
        // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "App")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(Integer.parseInt(APP_CODE_ACCION_FCM_NUEVO_SERVICIO), notificationBuilder.build());
    }

    //Metodo que se ejecuta cuando el app esta en pantalla
    // y el iAccion  de la notificacion es APP_CODE_ACCION_FCM_RECORDATORIO_SERVICIO_DISPONIBLE=2
    public void onReceivedFcmRecordatoriServicioDisponible(RemoteMessage remoteMessage, String strPantallaActual) {

        Log.d(LOG_SERVICE, "strPantallaActual : " + strPantallaActual);

        if (strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES)) {

            Log.d(LOG_SERVICE, "onReceivedAccionPrueba en " + APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES);

            //Ejecutar el onResument del fragmen en pantalla
            ejecutarOnResumentActivityHome();

        } else if (strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_CHAT) ||
                strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_GESTIONAR_SOLICITUD)) {

            //Ejecutar el onResument del fragmen en pantalla
            //ejecutarOnResumentActivityHome();

            //Crear notificacion
            sendNotificationNuevoServicio(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        } else {

            Log.d(LOG_SERVICE, "onReceivedAccionPrueba  en pantalla diferente a " + APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES);

            //Ejecutar el onResument del fragmen en pantalla
            ejecutarOnResumentActivityHome();

            //Crear notificacion
            sendNotificationNuevoServicio(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());


        }

    }


    //Metodo que se ejecuta cuando el app esta en pantalla
    // y el iAccion  de la notificacion es APP_CODE_ACCION_FCM_RECORDATORIO_SERVICIO_DISPONIBLE=2
    public void onReceivedFcmRecordatoriServicioDisponibleMasivo(RemoteMessage remoteMessage, String strPantallaActual) {

        Log.d(LOG_SERVICE, "strPantallaActual : " + strPantallaActual);

        if (strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_FRAGMENT_SERVICIOS_MASIVOS)) {

            Log.d(LOG_SERVICE, "onReceivedAccionPrueba en " + APP_SCREEN_FRAGMENT_SERVICIOS_MASIVOS);

            //Ejecutar el onResument del fragmen en pantalla
            ejecutarOnResumentActivityHome();

        } else if (strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_CHAT) ||
                strPantallaActual.equals(DependenciaJudicialUtils.APP_SCREEN_ACTIVITY_GESTIONAR_SOLICITUD)) {

            //Ejecutar el onResument del fragmen en pantalla
            //ejecutarOnResumentActivityHome();

            //Crear notificacion
            sendNotificationNuevoServicio(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        } else {

            Log.d(LOG_SERVICE, "onReceivedAccionPrueba  en pantalla diferente a " + APP_SCREEN_FRAGMENT_SERVICIOS_MASIVOS);

            //Ejecutar el onResument del fragmen en pantalla
            ejecutarOnResumentActivityHome();

            //Crear notificacion
            sendNotificationNuevoServicio(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());


        }

    }





    //endregion

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, ActivityHome.class);
        intent.putExtra(SP_CURRENT_SCREEN, APP_SCREEN_FRAGMENT_SERVICIOS_DISPONIBLES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //  String channelId = getString(R.string.default_notification_channel_id);
        // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "App")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("FCM Message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)

                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    //Metodo que actualiza el contador del menu Servicios Disponibles del tipo usuario 1 (Dependiente Judicial)
    private void setMenuCountServiciosDisponibles() {

        String srtExtraAddBagAlertSolicitudServicio = Preferences.getPreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_SOLICITUD_SERVICIO);

        if (!srtExtraAddBagAlertSolicitudServicio.equals("")) {
            srtExtraAddBagAlertSolicitudServicio = "" + (Integer.parseInt(srtExtraAddBagAlertSolicitudServicio) + 1);
            Preferences.savePreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_SOLICITUD_SERVICIO, srtExtraAddBagAlertSolicitudServicio);
        } else {
            Preferences.savePreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_SOLICITUD_SERVICIO, "1");
        }
    }

    //Metodo que actualiza el contador del menu Servicios Disponibles del tipo usuario 1 (Dependiente Judicial)
    private void setMenuCountServiciosDisponiblesMasivo() {

        String srtExtraAddBagAlertSolicitudServicio = Preferences.getPreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_SOLICITUD_SERVICIO_MASIVO);

        if (!srtExtraAddBagAlertSolicitudServicio.equals("")) {
            srtExtraAddBagAlertSolicitudServicio = "" + (Integer.parseInt(srtExtraAddBagAlertSolicitudServicio) + 1);
            Preferences.savePreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_SOLICITUD_SERVICIO_MASIVO, srtExtraAddBagAlertSolicitudServicio);
        } else {
            Preferences.savePreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_SOLICITUD_SERVICIO_MASIVO, "1");
        }
    }



    //Metodo que actualiza el contador del menu Servicios Aceptados del tipo usuario 1 (Dependiente Judicial)
    private void setMenuCountServiciosAceptados() {

        String srtExtraAddBagAlertAceptacionServicio = Preferences.getPreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_ACEPTACION_SERVICIO);

        if (!srtExtraAddBagAlertAceptacionServicio.equals("")) {
            srtExtraAddBagAlertAceptacionServicio = "" + (Integer.parseInt(srtExtraAddBagAlertAceptacionServicio) + 1);
            Preferences.savePreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_ACEPTACION_SERVICIO, srtExtraAddBagAlertAceptacionServicio);
        } else {
            Preferences.savePreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_EXTRA_ADD_BAG_ALERT_ACEPTACION_SERVICIO, "1");
        }
    }

    private void ejecutarOnResumentActivityHome() {
        Intent intent = new Intent(this, ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getApplication().startActivity(intent);
    }




    private void ejecutarOnResumentActivityChat() {

        Intent intent = new Intent(this, ActivityChat.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getApplication().startActivity(intent);

    }

    private void ejecutarOnResumentActivityGestionarSolicitud() {

        Intent intent = new Intent(this, ActivityGestionarSolicitud.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getApplication().startActivity(intent);

    }

}
