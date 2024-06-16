package com.example.pecunia_elibeluies;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Button;
import com.example.pecunia_elibeluies.db.dbPecunia;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;

import androidx.appcompat.app.AppCompatActivity;

public class Fijos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fijos);

        String usuario = getIntent().getStringExtra("UserName");

        //variables a utilizar
        EditText txtConcepto, txtCantidad;
        Spinner spnPeriodo, spnTipo;
        Button btnGuardar;

        //les asignamos el elemento del layout
        txtConcepto = findViewById(R.id.txtConcepto);
        txtCantidad = findViewById(R.id.txtCantidad);
        spnPeriodo = findViewById(R.id.spinnerPeriodo);
        spnTipo = findViewById(R.id.spinnerTipo);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //convertir a string
                //String usuario = txtUser.getText().toString();
                String concepto = txtConcepto.getText().toString();
                String cantidad = txtCantidad.getText().toString();
                String periodo = spnPeriodo.getSelectedItem().toString();
                String tipo = spnTipo.getSelectedItem().toString();
                Double cantidadD = Double.parseDouble(cantidad);

                try {

                    //para verificar que la cantidda sea valida
                    if (!CantidadValida(cantidad)) {
                        Toast.makeText(Fijos.this, "La cantidad ingresada no es valida", Toast.LENGTH_SHORT).show();
                        return; // Salir del método si la cantidad no es válido
                    }

                    Date fechaActual = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String fecha = dateFormat.format(fechaActual);

                    dbPecunia DbPecunia = new dbPecunia(Fijos.this);
                    SQLiteDatabase db = DbPecunia.getWritableDatabase();

                    //pa obtener el folio
                    int folioUsuario = DbPecunia.obtenerFolio(usuario);

                    if (db != null) {
                        //Toast.makeText(Fijos.this, "bd si", Toast.LENGTH_SHORT).show();
                        switch (tipo) {
                            case "Ingresos":
                                String tipoIngreso = "Fijo";
                                //Long in = DbPecunia.insertarIngreso("pago", "Fijo", "2023-12-10", "semanal", 455.50, 1);
                                Long in = DbPecunia.insertarIngreso(concepto, tipoIngreso, fecha, periodo, cantidadD, folioUsuario);
                                if (in > 0) {
                                    Toast.makeText(Fijos.this, "Guardado exitosamente", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Fijos.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case "Egresos":
                                String tipoEgreso = "Fijo";
                                Long eg = DbPecunia.insertarEgreso(concepto, tipoEgreso, fecha, periodo, cantidadD, folioUsuario);
                                if (eg > 0) {
                                    Toast.makeText(Fijos.this, "Guardado exitosamente", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Fijos.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            default:
                                Toast.makeText(Fijos.this, "no valido", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
                catch (Exception e) {
                    Log.e("Fijos", "Error al guardar" + e.getMessage(), e);
                    Toast.makeText(Fijos.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Maneja la excepción, mensaje error
                }
            }
        });
    }
    //para validar la cantidad
    private boolean CantidadValida(String cantidad) {
        String cantidadVer = "^[0-9]+(\\.[0-9]+)?$";
        return cantidad.matches(cantidadVer);
    }
}
