package com.example.tarea_2_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.database.sqlite.SQLiteDatabase;
import android.content.DialogInterface;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Lienzo lie;
    Button ver;
    Button guardar;
    Button eliminar;
    EditText descripcion;
    SQLiteConexion conexion;
    boolean estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        estado=true;

        conexion = new SQLiteConexion(this, Transacciones.NAME_DATABASE,null,1);

        eliminar = (Button) findViewById(R.id.btnLimpiar);
        guardar = (Button) findViewById(R.id.btnguardar);
        ver = (Button)findViewById(R.id.btnVer);
        descripcion = (EditText) findViewById(R.id.txtnombre);


        lie = (Lienzo) findViewById(R.id.lienzo);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("¿Esta seguro de crear una nueva firma?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                lie.nuevoDibujo();
                                descripcion.setText("");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        });

        guardar .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSignaturess();
            }
        });

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListadoF.class);
                startActivity(intent);
            }
        });
    }

    private void saveSignaturess(){
        if(lie.borrador){
            Toast.makeText(getApplicationContext(), "Registra tu Firma Digital.", Toast.LENGTH_LONG).show();
            return;
        }else if(descripcion.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Completa el campo de texto.", Toast.LENGTH_LONG).show();
            return;
        }

        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Transacciones.descripcion,descripcion.getText().toString());
        ByteArrayOutputStream bay = new ByteArrayOutputStream(10480);

        Bitmap bitmap = Bitmap.createBitmap(lie.getWidth(), lie.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        lie.draw(canvas);

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , bay);
        byte[] bl = bay.toByteArray();
        String img= Base64.encodeToString(bl,Base64.DEFAULT);
        values.put(Transacciones.imagen, img);

        Long result = db.insert(Transacciones.TABLE_FIRMA, Transacciones.id, values);
        Toast.makeText(getApplicationContext(), "Firma guardada con Exito.", Toast.LENGTH_LONG).show();
        lie.nuevoDibujo();
        descripcion.setText("");

        db.close();
    }
}