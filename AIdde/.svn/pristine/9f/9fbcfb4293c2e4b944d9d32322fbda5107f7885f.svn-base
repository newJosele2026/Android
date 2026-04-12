package com.slt.dependenciajudicial.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nelsy Acuña on 11/12/2017.
 */

public class PdfUtils {


    public static final String LOG_ACTIVITY = "PdfUtils";

    public static String ImgPdf(Activity activity, ArrayList<String> listPathImg, String folder, String pdfName) {

        String result;

        Image image;
        String path = FileUtils.createFolderSoportePdfApp(folder, activity);
        path = path + pdfName + ".pdf";

        Document document = new Document(PageSize.A4, 38, 38, 50, 38);

        Log.v(LOG_ACTIVITY, "Document Created");

        Rectangle documentRect = document.getPageSize();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));

            Log.v(LOG_ACTIVITY, "Pdf writer");

            document.open();


            Log.v(LOG_ACTIVITY, "Document opened");

            for (int i = 0; i < listPathImg.size(); i++) {


              /*  Bitmap bmp= MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                activity.getContentResolver(),
                                Uri.fromFile(new File(listPathImg.get(i))));


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 70, stream);


                image = Image.getInstance(listPathImg.get(i));*/
               // image.setRotation(-1.5f);

                BitmapFactory.Options opts = new BitmapFactory.Options ();
                opts.inSampleSize = 2;   // for 1/2 the image to be loaded
                Bitmap bmp = Bitmap.createScaledBitmap (BitmapFactory.decodeFile(listPathImg.get(i), opts), 480, 640, false);

                ExifInterface exifInterface = new ExifInterface(listPathImg.get(i));

               /* Bitmap bmp= MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                activity.getContentResolver(),
                                Uri.fromFile(new File(listPathImg.get(i))));*/

                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        bmp=FileUtils.rotateImage(bmp, 90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        bmp= FileUtils.rotateImage(bmp, 180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        bmp=FileUtils.rotateImage(bmp, 270);
                        break;
                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        break;
                }


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 10, stream);

                image = Image.getInstance(stream.toByteArray());


                if (bmp.getWidth() > documentRect.getWidth() || bmp.getHeight() > documentRect.getHeight()) {

                    //bitmap is larger than page,so set bitmap's size similar to the whole page
                    image.scaleAbsolute(documentRect.getWidth(), documentRect.getHeight());
                    //image.scaleAbsolute(100, 100);

                } else {
                    //bitmap is smaller than page, so add bitmap simply.
                    //[note: if you want to fill page by stretching image,
                    // you may set size similar to page as above]

                   image.scaleAbsolute(documentRect.getWidth(), documentRect.getHeight());
                    //image.scaleAbsolute(100, 100);
                }

                Log.v(LOG_ACTIVITY, "Image path adding");

                image.setAbsolutePosition(
                        (documentRect.getWidth() - image.getScaledWidth()) / 2,
                        (documentRect.getHeight() - image.getScaledHeight()) / 2);
                Log.v(LOG_ACTIVITY, "Image Alignments");

                image.setBorder(Image.BOX);

                image.setBorderWidth(15);

                document.add(image);

                document.newPage();
            }

            Log.v(LOG_ACTIVITY, "Image adding");

            result = path;


        } catch (Exception err) {

            Log.v(LOG_ACTIVITY, "Error ");
            err.printStackTrace();
            result = "";
        } finally {

             document.close();
             Log.v(LOG_ACTIVITY, "Document Closed" + path);





        }


        return result;

    }


    public static void openIntentPdf(Activity activity, String path) {
        File file = new File(path);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Abrir Archivo");
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "No hay aplicaciones disponibles", Toast.LENGTH_LONG).show();
        }
    }

}
