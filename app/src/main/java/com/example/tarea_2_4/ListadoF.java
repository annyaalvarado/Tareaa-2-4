package com.example.tarea_2_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import java.util.ArrayList;

import android.os.Bundle;
import android.widget.Button;

public class ListadoF extends AppCompatActivity {

        SQLiteConexion conexion;

        RecyclerView.Adapter adapter;
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        ArrayList<firmas> firmList;
        Button btnVolver;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listado_f);

            conexion = new SQLiteConexion(this, Transacciones.NAME_DATABASE,null,1);

            recyclerView = (RecyclerView) findViewById(R.id.recycler_firm);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            firmList = new ArrayList<>();
            getSignaturess();

            adapter = new Adaptador(firmList);
            recyclerView.setAdapter(adapter);


            btnVolver = (findViewById(R.id.btnVer));

            btnVolver.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            });

        }


        private void getSignaturess(){
            SQLiteDatabase db = conexion.getReadableDatabase();
            firmas firm = null;
            Cursor cursor = db.rawQuery("SELECT * FROM "+ Transacciones.TABLE_FIRMA,null);
            while (cursor.moveToNext()){
                firm = new firmas();
                firm.setId(cursor.getInt(0));
                firm.setDescripcion(cursor.getString(1));
                firm.setImagen(cursor.getString(2));

                firmList.add(firm);
            }
        }

    }

