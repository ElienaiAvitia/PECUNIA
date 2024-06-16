package com.example.pecunia_elibeluies;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import com.example.pecunia_elibeluies.db.dbPecunia;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;

import androidx.appcompat.app.AppCompatActivity;

public class Variables extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variables);

        String usuario = getIntent().getStringExtra("UserName");
//variables a utilizar
        EditText txtConcepto, txtCantidad;
        Spinner spnTipo;
        Button btnGuardar;


        //les asignamos el elemento del layout
        txtConcepto = findViewById(R.id.txtConcepto);
        txtCantidad = findViewById(R.id.txtCantidad);
        spnTipo = findViewById(R.id.spinner);
        btnGuardar = findViewById(R.id.btnGuardar);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String concepto = txtConcepto.getText().toString();
                String cantidad = txtCantidad.getText().toString();
                String tipo = spnTipo.getSelectedItem().toString();
                Double cantidadD = Double.parseDouble(cantidad);

                Date fechaActual = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String fecha = dateFormat.format(fechaActual);

                dbPecunia DbPecunia = new dbPecunia(Variables.this);
                SQLiteDatabase db = DbPecunia.getWritableDatabase();

                //pa obtener el folio
                int folioUsuario = DbPecunia.obtenerFolio(usuario);

                if (db != null) {
                    Toast.makeText(Variables.this, "bd si", Toast.LENGTH_SHORT).show();
                    switch (tipo) {
                        case "Ingresos":
                            String tipoIngreso = "Variables";
                            Long in = DbPecunia.insertarIngresoV(concepto, tipoIngreso, fecha, cantidadD, folioUsuario);
                            if (in > 0) {
                                Toast.makeText(Variables.this, "Guardado exitosamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Variables.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case "Egresos":
                            String tipoEgreso = "Variables";
                            Long eg = DbPecunia.insertarEgresoV(concepto, tipoEgreso, fecha, cantidadD, folioUsuario);
                            if (eg > 0) {
                                Toast.makeText(Variables.this, "Guardado exitosamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Variables.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        default:
                            Toast.makeText(Variables.this, "no valido", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
    }
}
