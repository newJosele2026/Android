package com.slt.dependenciajudicial.views.asyn;

/**
 * Created by Nelsy Acuña on 11/12/2017.
 */

import android.app.Activity;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;
import com.slt.dependenciajudicial.utils.PdfUtils;
import com.slt.dependenciajudicial.utils.callback.CallbackCreatePdf;

import java.util.ArrayList;

/**
 * Created by Sergio on 10/12/17.
 */

public class AsynCreatePdf extends AsyncTask<Void, Void, String> {


    private Activity activity;
    private CallbackCreatePdf callbackCreatePdf;
    private MaterialDialog materialDialog;
    private ArrayList<String> listPathImg;
    private String folde;
    private String nameFile;


    public AsynCreatePdf(Activity activity,
                         CallbackCreatePdf callbackCreatePdf,
                         MaterialDialog materialDialog,
                         ArrayList<String> listPathImg,
                         String folde,
                         String nameFile) {

        this.activity = activity;
        this.callbackCreatePdf = callbackCreatePdf;
        this.materialDialog = materialDialog;
        this.listPathImg = listPathImg;
        this.folde = folde;
        this.nameFile = nameFile;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (!materialDialog.isShowing()) {
            materialDialog.setCancelable(false);
            materialDialog.show();
        }

    }

    @Override
    protected String doInBackground(Void... voids) {

        return PdfUtils.ImgPdf(activity,listPathImg,folde,nameFile);
    }


    @Override
    protected void onPostExecute(String tPath) {
        super.onPostExecute(tPath);

        if (materialDialog.isShowing())
            materialDialog.cancel();

        callbackCreatePdf.OnCallbackCreatePdf(tPath);

    }
}
