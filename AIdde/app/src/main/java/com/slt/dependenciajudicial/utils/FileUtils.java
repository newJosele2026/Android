package com.slt.dependenciajudicial.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.slt.dependenciajudicial.app.Preferences;
import com.slt.dependenciajudicial.requests.models.SoportesModel;
import com.slt.dependenciajudicial.requests.settings.ApiUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nelsy Acuña on 11/12/2017.
 */

public class FileUtils {

    public static final String LOG_ACTIVITY = "FileUtils";

    public static final String FOLDER_NAME = "DependenciaJudicial";

    public static String folderApp() {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FOLDER_NAME + "/";

        Log.d(LOG_ACTIVITY, "path:" + path);


        File folder = new File(path);
        if (!folder.exists()) {
            Log.d(LOG_ACTIVITY, "folder.exists():" + folder.exists());
            folder.mkdir();
        }

        return path;

    }

    public static String createFolderSoportePdfApp(String newFolder, Activity activity) {

        folderApp();

        String usuario = Preferences.getPreferences(activity, DependenciaJudicialUtils.SP_IIDDJUSUARIO);

        String pathUsuario = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FOLDER_NAME + "/" + usuario + "/";

        Log.d(LOG_ACTIVITY, "pathUsuario:" + pathUsuario);

        File folderUser = new File(pathUsuario);
        if (!folderUser.exists()) {
            Log.d(LOG_ACTIVITY, "folderUser.exists():" + folderUser.exists());
            folderUser.mkdir();
        }

        String pathSolicitud = pathUsuario + newFolder + "/";

        Log.d(LOG_ACTIVITY, "pathSolicitud:" + pathSolicitud);

        File folderSolicitud = new File(pathSolicitud);
        if (!folderSolicitud.exists()) {
            Log.d(LOG_ACTIVITY, "folder.exists():" + folderSolicitud.exists());
            folderSolicitud.mkdir();
        }


        String pathTipoSoporte = pathUsuario + newFolder + "/pdf/";

        Log.d(LOG_ACTIVITY, "pathTipoSoporte:" + pathTipoSoporte);

        File folderTipoSoporte = new File(pathTipoSoporte);
        if (!folderTipoSoporte.exists()) {
            Log.d(LOG_ACTIVITY, "folder.exists():" + folderTipoSoporte.exists());
            folderTipoSoporte.mkdir();
        }

        return pathTipoSoporte;

    }

    public static Boolean checkExistFolder(String nameFolder) {

        File folder = new File(nameFolder);

        return folder.exists();
    }

    public static List<SoportesModel> getListFileFolder(String path, List<SoportesModel> listNameSoporte) {


        File f = new File(path);

        if (f.exists()) {

            File[] ficheros = f.listFiles();
            for (int x = 0; x < ficheros.length; x++) {

                if (ficheros[x].isDirectory()) {

                    getListFileFolder(path + ficheros[x].getName(), listNameSoporte);
                } else {

                    SoportesModel soportesModel = new SoportesModel();
                    soportesModel.settNombreArchivo(ficheros[x].getName());
                    soportesModel.settExtension(getFileExtension(ficheros[x].getName()).toLowerCase());
                    soportesModel.settPath(ficheros[x].getAbsolutePath());
                    Log.d(LOG_ACTIVITY, "getAbsolutePath:" + soportesModel.gettPath());

                    listNameSoporte.add(soportesModel);
                }

            }

        }


        return listNameSoporte;
    }

    public static void deleteFile(String tPath) {


        Log.d(LOG_ACTIVITY, "deleteFile:" + tPath);

        File file = new File(tPath);
        if (file.exists()) {
            file.delete();
        }

    }

    private static String getFileExtension(String fileName) {
        //String name = file.getName();
        try {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    private static void showPdfServer(Activity activity, SoportesModel soportesModel) {

        String urlPdf = ApiUtils.URL_FILES_SERVER;

        String tExtension = getFileExtension(soportesModel.gettNombreArchivoServer());

        urlPdf = (urlPdf + soportesModel.gettPath() + soportesModel.gettNombreArchivoServer()).replace("\\", "/");

        Log.d(LOG_ACTIVITY, "urlPdf:" + urlPdf);

        String googleDocsUrl = "http://docs.google.com/viewer?url=" + urlPdf;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(googleDocsUrl), "text/html");
        activity.startActivity(intent);
    }

    public static Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        // Read EXIF Data
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {

        Matrix mat = new Matrix();
        mat.postRotate(angle);

        Bitmap bitmap= Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), mat,false);

        return bitmap;
    }


}
