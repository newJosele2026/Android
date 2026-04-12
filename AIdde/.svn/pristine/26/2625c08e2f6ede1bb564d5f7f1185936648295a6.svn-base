package com.slt.dependenciajudicial.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.slt.dependenciajudicial.app.database.tables.UsuarioDJCiudadesBD;
import com.slt.dependenciajudicial.utils.Security;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.slt.dependenciajudicial.utils.DependenciaJudicialUtils.NAME_COLLECTION_PREFERENCES;


/**
 * Created by Sergio on 26/10/2017.
 */

public class Preferences {

    public static int getTokenData(String token) {

        String secredt = Security.decodeString(token);

        return Integer.parseInt(secredt.split("\\|")[0]);
    }

    public static Date getTokenDataExpired(String token) {

        String secredt = Security.decodeString(token);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date dtExpired = formatter.parse(secredt.split("\\|")[1]);
            return dtExpired;
        } catch (Exception err) {

            return null;
        }

    }

    public static void savePreferences(Activity activity, String key, String value) {
        SharedPreferences prefs = activity.getSharedPreferences(NAME_COLLECTION_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (value.equals("")) {
            editor.putString(key, "");
        } else {
            editor.putString(key, Security.encodeString(value));
        }
        editor.apply();
    }

    public static void savePreferencesContext(Context activity, String key, String value) {
        SharedPreferences prefs = activity.getSharedPreferences(NAME_COLLECTION_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (value.equals("")) {
            editor.putString(key, "");
        } else {
            editor.putString(key, Security.encodeString(value));
        }

        editor.apply();
    }

    public static String getPreferences(Activity activity, String key) {

        SharedPreferences prefs = activity.getSharedPreferences(NAME_COLLECTION_PREFERENCES, Context.MODE_PRIVATE);
        String str = prefs.getString(key, "");

        if (str != "") {
            return Security.decodeString(str);
        } else {
            return "";
        }

    }

    public static String getPreferencesContext(Context activity, String key) {

        SharedPreferences prefs = activity.getSharedPreferences(NAME_COLLECTION_PREFERENCES, Context.MODE_PRIVATE);
        String str = prefs.getString(key, "");

        if (str != "") {
            return Security.decodeString(str);
        } else {
            return "";
        }

    }

    public static void clearPreferences(Activity activity) {

        //Limpiar todas las preferencias del app
        SharedPreferences prefs = activity.getSharedPreferences(NAME_COLLECTION_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();

        //Limpiar la bd
        Delete.table(UsuarioDJCiudadesBD.class);


    }

}
