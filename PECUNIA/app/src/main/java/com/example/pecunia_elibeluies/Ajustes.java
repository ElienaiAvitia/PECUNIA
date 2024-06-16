package com.example.pecunia_elibeluies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pecunia_elibeluies.db.dbPecunia;
import com.google.android.material.textfield.TextInputLayout;

public class Ajustes extends AppCompatActivity {

    Button btnAceptarAjustes;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        String usuario = getIntent().getStringExtra("UserName");

        btnAceptarAjustes = findViewById(R.id.btnAceptarAjustes);

        EditText txtUsuarioA, txtContrasenaA, txtContrasenaC, txtContrasenaN;

        txtUsuarioA = ((TextInputLayout) findViewById(R.id.txtUsuarioAjustes)).getEditText();
        txtContrasenaA = ((TextInputLayout) findViewById(R.id.txtPasswordAjustes)).getEditText();
        txtContrasenaN = ((TextInputLayout) findViewById(R.id.txtPasswordAjustesNueva)).getEditText();
        txtContrasenaC = ((TextInputLayout) findViewById(R.id.txtPasswordAjustesConfirma)).getEditText();

        btnAceptarAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contrasenaA = txtContrasenaA.getText().toString();
                String contrasenaN = txtContrasenaN.getText().toString();
                String contrasenaC = txtContrasenaC.getText().toString();

                dbPecunia DbPecunia = new dbPecunia(Ajustes.this);
                SQLiteDatabase db = DbPecunia.getWritableDatabase();


                boolean id = DbPecunia.verificarCredenciales(usuario, contrasenaA);

                int folioUsuario = DbPecunia.obtenerFolio(usuario);

                if (id){
                    if (contrasenaN.length() > 0){
                        if(contrasenaN.equals(contrasenaC)){
                            Long cambio = DbPecunia.actualizarPassword(folioUsuario, contrasenaN);
                            if (cambio > 0) {
                                Toast.makeText(Ajustes.this, "La contraseña ha sido modificada", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Ajustes.this, "Error al modificar", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(Ajustes.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Ajustes.this, "La contraseña no puede estar vacía", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Ajustes.this, "La contraseña actual es incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
