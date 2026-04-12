package com.example.pickerfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.net.Uri;
import android.util.Log;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.Cursor;
import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.Comparator;
//import butterknife.OnClick;
public class MainActivity extends AppCompatActivity {


    private static final String TAG = "FileUtils";


    private static final String ACTION_OPEN = "open";
    private static final int PICK_FILE_REQUEST = 10;


    public static final String MIME = "mime";

   // CallbackContext callback;


    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button btn_mostrar;
    btn_mostrar=(Button)findViewById(R.id.button);
    btn_mostrar.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                //Pulsación corta
                  onClickBtnAdjuntarSoporte(v);
              }

            });
  }

  //@OnClick(R.id.button)
  public void onClickBtnAdjuntarSoporte(View v) {
    Toast mensaje =Toast.makeText(getApplicationContext(),"hola esto es un mensaje toast",Toast.LENGTH_LONG);
    mensaje.show();
      //String uri_filter = filter.has(MIME) ? filter.optString(MIME) : "*/*";
      String uri_filter="";
      // type and title should be configurable
      Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
      intent.addCategory(Intent.CATEGORY_DEFAULT);
      intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
      startActivityForResult(Intent.createChooser(intent, "Choose directory"), 9999);
  }

}
