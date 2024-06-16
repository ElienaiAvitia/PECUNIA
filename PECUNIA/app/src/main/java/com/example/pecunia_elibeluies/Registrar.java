package com.example.pecunia_elibeluies;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.annotation.SuppressLint;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pecunia_elibeluies.db.dbPecunia;
import com.google.android.material.textfield.TextInputLayout;

public class Registrar extends AppCompatActivity {

    Button btnRegistrar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarme);
        Log.d("Registrar", "La actividad Registrar se ha creado correctamente.");

        btnRegistrar = findViewById(R.id.btnRegistrar);

        EditText txtEmailR, txtNameR, txtPasswordR;

        txtEmailR = ((TextInputLayout) findViewById(R.id.txtEmailR)).getEditText();
        txtNameR = ((TextInputLayout) findViewById(R.id.txtNameR)).getEditText();
        txtPasswordR = ((TextInputLayout) findViewById(R.id.txtPasswordR)).getEditText();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d("Registrar", "Se hizo clic en el botón Registrar.");

                    // Validar el formato del email
                    if (!isValidEmail(txtEmailR.getText().toString())) {
                        Toast.makeText(Registrar.this, "Por favor, introduce un email válido", Toast.LENGTH_SHORT).show();
                        return; // Salir del método si el email no es válido
                    }

                    dbPecunia DbPecunia = new dbPecunia(Registrar.this);
                    SQLiteDatabase db = DbPecunia.getWritableDatabase();
                    if (db != null) {
                        Long id = DbPecunia.insertarUsuario(txtNameR.getText().toString(),
                                txtEmailR.getText().toString(),
                                txtPasswordR.getText().toString());
                        if (id > 0) {
                            Toast.makeText(Registrar.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Registrar.this, "Registro Fallido", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("Registrar", "Error al intentar registrar: " + e.getMessage(), e);
                    Toast.makeText(Registrar.this, "Error al intentar registrar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Maneja la excepción, mensaje error
                }
            }
        });
    }

    private class RegistrarUsuarioTask extends AsyncTask<String, Void, Long> {
        @Override
        protected Long doInBackground(String... parametro) {
            // Realizar operaciones de base de datos en segundo plano
            dbPecunia DbPecunia = new dbPecunia(Registrar.this);
            SQLiteDatabase db = DbPecunia.getWritableDatabase();
            if (db != null) {
                return DbPecunia.insertarUsuario(parametro[0], parametro[1], parametro[2]);
            }
            return -1L;
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);
            // Actualizar la interfaz de usuario con el resultado de la tarea
            if (result > 0) {
                Toast.makeText(Registrar.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Registrar.this, "Registro Fallido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para validar el formato del email con expresión regular
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Color del texto pecunia
    private void setTextColor(TextView txtColor, int... color) {
        TextPaint textPaint = txtColor.getPaint();
        float width = textPaint.measureText(txtColor.getText().toString());
        Shader shader = new LinearGradient(0, 0, width, txtColor.getTextSize(), color, null, Shader.TileMode.CLAMP);
        txtColor.getPaint().setShader(shader);
        txtColor.setTextColor(color[0]);
    }
}