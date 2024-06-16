package com.example.pecunia_elibeluies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pecunia_elibeluies.db.dbPecunia;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        String usuario = getIntent().getStringExtra("UserName");
        TextView nombreUser = findViewById(R.id.txtNusuario);
        String saludo = "Hola, "+ usuario;
        nombreUser.setText(saludo);

        ImageView perfilUsuario = findViewById(R.id.imagenUsuario);
        ImageButton Distribucion = findViewById(R.id.btnDis);
        ImageButton Progreso = findViewById(R.id.btnProgreso);
        ImageButton EIVariable = findViewById(R.id.btnIEV);
        ImageButton Meta = findViewById(R.id.btnMeta);
        /*TextView textViewGreeting = findViewById(R.id.txtNusuario);
        String saludo = "Hola, "+ UserName();
        textViewGreeting.setText(saludo);*/
        dbPecunia DbPecunia = new dbPecunia(Home.this);
        SQLiteDatabase db = DbPecunia.getWritableDatabase();
        if (db!=null){
            TextView totel = findViewById(R.id.txtTotalTV);
            int folioUsuario = DbPecunia.obtenerFolio(usuario);
            Double totalito = DbPecunia.obtenerDiferenciaSumaColumnas(folioUsuario);
            String totaal = "$"+ totalito;
            totel.setText(totaal);
        }


        perfilUsuario.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();

                Intent intentUser = new Intent(Home.this, PerfilUsuario.class);
                intentUser.putExtra("UserName", usuario);
                Home.this.startActivity(intentUser);
            }
        });
        EIVariable.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();

                Intent intentVar = new Intent(Home.this, Variables.class);
                intentVar.putExtra("UserName", usuario);
                Home.this.startActivity(intentVar);
            }
        });
        Progreso.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();

                Intent intentProg = new Intent(Home.this, Progreso.class);
                intentProg.putExtra("UserName", usuario);
                Home.this.startActivity(intentProg);
            }
        });
        Distribucion.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();

                Intent intentDis = new Intent(Home.this, Distribucion.class);
                intentDis.putExtra("UserName", usuario);
                Home.this.startActivity(intentDis);
            }
        });
        Meta.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();

                Intent intentMeta = new Intent(Home.this,   Meta.class);
                intentMeta.putExtra("UserName", usuario);
                Home.this.startActivity(intentMeta);
            }
        });

    }
}
