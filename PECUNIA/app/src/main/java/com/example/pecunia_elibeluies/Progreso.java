package com.example.pecunia_elibeluies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import com.example.pecunia_elibeluies.db.dbPecunia;
import com.example.pecunia_elibeluies.db.dbHelper;


public class Progreso extends AppCompatActivity {


    private ListView lvProgreso;
    private dbHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progreso);

        String usuario = getIntent().getStringExtra("UserName");

        lvProgreso = findViewById(R.id.lvProgreso);
        databaseHelper = new dbHelper(this);
        ImageButton agregar = findViewById(R.id.ibAgregarP);
        TextView agregarT = findViewById(R.id.txtAgregarP);

        // Abrir la base de datos
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        // Obtener datos de la tabla de usuarios
        List<String> usuarios = obtenerMetas(database);

        // Configurar el adaptador y mostrar en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, usuarios);
        lvProgreso.setAdapter(adapter);

        agregar.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();

                Intent intentUser = new Intent(Progreso.this, Meta.class);
                intentUser.putExtra("UserName", usuario);
                Progreso.this.startActivity(intentUser);
            }
        });
        agregarT.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();

                Intent intentUser = new Intent(Progreso.this, Meta.class);
                intentUser.putExtra("UserName", usuario);
                Progreso.this.startActivity(intentUser);
            }
        });
    }


    private List<String> obtenerMetas(SQLiteDatabase database) {
        List<String> metas = new ArrayList<>();

        // Consulta a la tabla de metas con el cálculo
        String consulta = "SELECT FolioMeta, Concepto, CantidadRecaudada, CantidadAlcanzar, " +
                "(CantidadRecaudada * 100 / CantidadAlcanzar) AS PorcentajeAlcanzado " +
                "FROM " + dbHelper.DB_TABLE_META;

        Cursor cursor = database.rawQuery(consulta, null);

        // Obtener datos de las columnas y calcular el porcentaje
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int folioMeta = cursor.getInt(cursor.getColumnIndex("FolioMeta"));
                @SuppressLint("Range") String concepto = cursor.getString(cursor.getColumnIndex("Concepto"));
                @SuppressLint("Range") double cantidadRecaudada = cursor.getDouble(cursor.getColumnIndex("CantidadRecaudada"));
                @SuppressLint("Range") double cantidadAlcanzar = cursor.getDouble(cursor.getColumnIndex("CantidadAlcanzar"));
                @SuppressLint("Range") double porcentajeAlcanzado = cursor.getDouble(cursor.getColumnIndex("PorcentajeAlcanzado"));

                // Crear la cadena deseada y agregar a la lista
                String metaConcatenada = "Meta: " + concepto +
                        "\nCantidadRecaudada: " + cantidadRecaudada +
                        "\nPorcentaje Alcanzado: " + porcentajeAlcanzado + "%";

                metas.add(metaConcatenada);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return metas;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la base de datos al salir de la actividad
        databaseHelper.close();
    }

}
