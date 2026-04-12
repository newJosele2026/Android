package com.slt.dependenciajudicial.app.gcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.slt.dependenciajudicial.app.Preferences;
import com.slt.dependenciajudicial.requests.SOService;
import com.slt.dependenciajudicial.requests.models.BasicRequestModel;
import com.slt.dependenciajudicial.requests.settings.ApiUtils;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;
import com.slt.dependenciajudicial.views.activity.ActivityHome;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String LOG_ACTIVITY = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(LOG_ACTIVITY, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

        try{

            SOService apiService = ApiUtils.getSOService();

            String strToken = Preferences.getPreferencesContext(getApplicationContext(), DependenciaJudicialUtils.SP_TOKEN);

            Log.d(LOG_ACTIVITY, "Token api " + strToken);

            if(strToken!=""){

                apiService.ActualizarTokenDispositivo(strToken, "", "", token).enqueue(new Callback<BasicRequestModel>() {
                    @Override
                    public void onResponse(Call<BasicRequestModel> call, Response<BasicRequestModel> response) {

                        if (response.isSuccessful()) {

                            if (response.code() != ApiUtils.CODE_SESION_EXPIRE) {

                                if (response.body().getResult() == 1) {
                                    Log.d(LOG_ACTIVITY, "Se actualziao el token con exito ");

                                } else {
                                    Log.d(LOG_ACTIVITY, "Error en actualziar el token ");
                                }

                            } else {
                                Log.d(LOG_ACTIVITY, "La sesion a terminado");
                            }
                        } else {

                            Log.d(LOG_ACTIVITY, "Error en el servicio");
                        }

                    }

                    @Override
                    public void onFailure(Call<BasicRequestModel> call, Throwable t) {
                        Log.d(LOG_ACTIVITY, "onFailure Error en el servicio");
                    }
                });
                
            }else{

                Log.d(LOG_ACTIVITY, "El usuario no esta en sesion" );
            }

           
            
            
        }catch (Exception e){
            
            
        }

       

    }
}
