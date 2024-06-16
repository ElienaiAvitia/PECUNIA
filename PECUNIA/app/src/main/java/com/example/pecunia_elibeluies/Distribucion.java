package com.example.pecunia_elibeluies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import com.example.pecunia_elibeluies.db.dbPecunia;
import com.example.pecunia_elibeluies.db.dbHelper;


public class Distribucion extends AppCompatActivity {


    private ListView lvDistribucion;
    private dbHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribucion);

        String usuario = getIntent().getStringExtra("UserName");
        lvDistribucion = findViewById(R.id.lvDistribucion);
        databaseHelper = new dbHelper(this);

        ImageButton agregar = findViewById(R.id.ibAgregar);
        TextView agregarT = findViewById(R.id.txtAgregar);
        EditText folioE = findViewById(R.id.txtFolioE);
        ImageButton eliminar = findViewById(R.id.ibEliminar);
        TextView eliminarT = findViewById(R.id.txtEliminar);

        // Abrir la base de datos
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        // Obtener datos de la tabla de usuarios
        List<String> usuarios = obtenerEgresos(database);

        // Configurar el adaptador y mostrar en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, usuarios);
        lvDistribucion.setAdapter(adapter);

        agregar.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();

                Intent intentUser = new Intent(Distribucion.this, Fijos.class);
                intentUser.putExtra("UserName", usuario);
                Distribucion.this.startActivity(intentUser);
            }
        });
        agregarT.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();

                Intent intentUser = new Intent(Distribucion.this, Fijos.class);
                intentUser.putExtra("UserName", usuario);
                Distribucion.this.startActivity(intentUser);
            }
        });

        eliminar.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();
                dbPecunia DbPecunia = new dbPecunia(Distribucion.this);
                SQLiteDatabase db = DbPecunia.getWritableDatabase();

                String folito = folioE.getText().toString();
                Integer folioeg = Integer.parseInt(folito);

                if (db != null){
                    Long elimina = DbPecunia.eliminarEgresoPorFolio(folioeg);
                    if (elimina  > 0) {
                        Toast.makeText(Distribucion.this, "Usuario Eliminado", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Distribucion.this, "No fue posible eliminar el usuario", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        eliminarT.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();
                dbPecunia DbPecunia = new dbPecunia(Distribucion.this);
                SQLiteDatabase db = DbPecunia.getWritableDatabase();

                String folito = folioE.getText().toString();
                Integer folioeg = Integer.parseInt(folito);

                if (db != null){
                    Long elimina = DbPecunia.eliminarEgresoPorFolio(folioeg);
                    if (elimina  > 0) {
                        Toast.makeText(Distribucion.this, "Usuario Eliminado", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Distribucion.this, "No fue posible eliminar el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private List<String> obtenerEgresos(SQLiteDatabase database) {
        List<String> egresos = new ArrayList<>();

        // Consulta a la tabla de egresos
        String consulta = "SELECT FolioEgreso, Concepto, Periodo, Cantidad " +
                "FROM " + dbHelper.DB_TABLE_EGRESOS;

        Cursor cursor = database.rawQuery(consulta, null);

        // Obtener datos de las columnas y crear la cadena deseada
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int folioEgreso = cursor.getInt(cursor.getColumnIndex("FolioEgreso"));
                String concepto = cursor.getString(cursor.getColumnIndex("Concepto"));
                String periodo = cursor.getString(cursor.getColumnIndex("Periodo"));
                double cantidad = cursor.getDouble(cursor.getColumnIndex("Cantidad"));

                // Crear la cadena deseada y agregar a la lista
                String egresoConcatenado = "A Pagar \nFolio: " + folioEgreso +
                        "\nConcepto: " + concepto +
                        "\n" + periodo +
                        "\nCantidad: " + cantidad;

                egresos.add(egresoConcatenado);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return egresos;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la base de datos al salir de la actividad
        databaseHelper.close();
    }

}
