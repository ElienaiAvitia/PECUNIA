package com.example.pecunia_elibeluies;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.DatePickerDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.DatePicker;
import com.example.pecunia_elibeluies.db.dbPecunia;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Calendar;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
public class Meta extends AppCompatActivity {

    private Button btnAceptar;
    private EditText txtConcepto, txtCantidadAlcanzar, txtFechaLimite, txtCantidadRecaudada;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta);

        String usuario = getIntent().getStringExtra("UserName");


        txtConcepto = findViewById(R.id.txtConcepto);
        txtCantidadAlcanzar = findViewById(R.id.txtCantidadAlcanzar);
        txtFechaLimite = findViewById(R.id.txtFechaLimite);
        txtCantidadRecaudada = findViewById(R.id.txtCantidadRecaudada);

        btnAceptar = findViewById(R.id.btnAceptar);

        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                txtFechaLimite.setText(sdf.format(myCalendar.getTime()));
            }
        };

        txtFechaLimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Meta.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String concepto = txtConcepto.getText().toString();
                String cantidadAlc = txtCantidadAlcanzar.getText().toString();
                String cantidadRec = txtCantidadRecaudada.getText().toString();
                String fechaLimite = txtFechaLimite.getText().toString();

                if (concepto.isEmpty() || cantidadAlc.isEmpty() || cantidadRec.isEmpty() || fechaLimite.isEmpty()) {
                    Toast.makeText(Meta.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double cantidadA, cantidadR;
                try {
                    cantidadA = Double.parseDouble(cantidadAlc);
                    cantidadR = Double.parseDouble(cantidadRec);
                } catch (NumberFormatException e) {
                    Toast.makeText(Meta.this, "Ingrese números válidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cantidadA <= 0 || cantidadR <= 0) {
                    Toast.makeText(Meta.this, "Las cantidades deben ser mayores que cero", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date fechaActual = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String fechaIni = dateFormat.format(fechaActual);

                dbPecunia DbPecunia = new dbPecunia(Meta.this);
                int folioUsuario = DbPecunia.obtenerFolio(usuario);

                long resultado = DbPecunia.insertarMeta(fechaIni, fechaLimite, cantidadR, cantidadA, concepto, folioUsuario);

                if (resultado > 0) {
                    Toast.makeText(Meta.this, "Guardado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Meta.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
