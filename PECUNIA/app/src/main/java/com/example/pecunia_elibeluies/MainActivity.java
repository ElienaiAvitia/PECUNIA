package com.example.pecunia_elibeluies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.text.TextPaint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.MotionEvent;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import com.example.pecunia_elibeluies.db.dbPecunia;
import com.google.android.material.textfield.TextInputLayout;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
//import com.google.android.material.internal.ViewUtils;

public class MainActivity extends AppCompatActivity {
    public EditText txtUser;
    public EditText txtPassword;
    TextView regi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regi = findViewById(R.id.txtRegistrarme);
        Log.d("Registrar", "Se hizo clic en el botón Registrar.");
        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentReg = new Intent(MainActivity.this, Registrar.class);
                MainActivity.this.startActivity(intentReg);
                Log.d("Registrar", "Se hizo clic en el botón Registrar.");
            }
        });

        txtUser =  ((TextInputLayout) findViewById(R.id.txtUser)).getEditText();
        txtPassword =  ((TextInputLayout) findViewById(R.id.txtPasswordR)).getEditText();

        Button btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = txtUser.getText().toString();
                String contrasena = txtPassword.getText().toString();

                //Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                dbPecunia DbPecunia = new dbPecunia(MainActivity.this);
                SQLiteDatabase db = DbPecunia.getWritableDatabase();


               boolean id = DbPecunia.verificarCredenciales(usuario, contrasena);
               //esto es para folioUsuario



                if (id){
                    Intent intentLog = new Intent(MainActivity.this, Home.class);
                    intentLog.putExtra("UserName", usuario);
                    MainActivity.this.startActivity(intentLog);
                }
                else {
                    Toast.makeText(MainActivity.this, "Datos ingresados incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setTextColor(TextView txtColor, int ...color) {
        TextPaint textPaint = txtColor.getPaint();
        float width = textPaint.measureText(txtColor.getText().toString());
        Shader shader = new LinearGradient(0,0,width,txtColor.getTextSize(),color, null, Shader.TileMode.CLAMP);
        txtColor.getPaint().setShader(shader);
        txtColor.setTextColor(color[0]);
    }
}