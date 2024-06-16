package com.example.pecunia_elibeluies;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pecunia_elibeluies.db.dbPecunia;

public class PerfilUsuario extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_usuario);
        String usuario = getIntent().getStringExtra("UserName");
        TextView nombreUser = findViewById(R.id.txtNusuario);
        nombreUser.setText(usuario);

        ImageButton ingreso = findViewById(R.id.ibIngreso);
        ImageButton egreso = findViewById(R.id.ibEgreso);
        ImageButton eliminarU = findViewById(R.id.ibEliminarUsuario);
        ImageButton modificarC = findViewById(R.id.ibModificarContrasena);
        Button cerrarSesion =  findViewById(R.id.btnLogOut);

        cerrarSesion.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(PerfilUsuario.this, "", Toast.LENGTH_SHORT).show();

                Intent intentFijo = new Intent(PerfilUsuario.this, MainActivity.class);

                PerfilUsuario.this.startActivity(intentFijo);
            }
        });

        //aqui manda al layout de fijos al hacer clic en ingreso
        ingreso.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(PerfilUsuario.this, "", Toast.LENGTH_SHORT).show();

                Intent intentFijo = new Intent(PerfilUsuario.this, Fijos.class);
                intentFijo.putExtra("UserName", usuario);
                PerfilUsuario.this.startActivity(intentFijo);
            }
        });

        //aqui manda a layout egreso
        egreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(PerfilUsuario.this, "", Toast.LENGTH_SHORT).show();

                Intent intentFijo = new Intent(PerfilUsuario.this, Fijos.class);
                intentFijo.putExtra("UserName", usuario);
                PerfilUsuario.this.startActivity(intentFijo);
            }
        });

        modificarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(PerfilUsuario.this, "", Toast.LENGTH_SHORT).show();

                Intent intentFijo = new Intent(PerfilUsuario.this, Ajustes.class);
                intentFijo.putExtra("UserName", usuario);
                PerfilUsuario.this.startActivity(intentFijo);
            }
        });

        eliminarU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(PerfilUsuario.this, "", Toast.LENGTH_SHORT).show();
                mostrarDialogoConfirmacion();

            }
        });
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String usuario = getIntent().getStringExtra("UserName");
        builder.setTitle("Confirmar");
        builder.setMessage("¿Estás seguro de que deseas realizar esta acción?");

        // Configurar el botón "Aceptar"
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dbPecunia DbPecunia = new dbPecunia(PerfilUsuario.this);
                SQLiteDatabase db = DbPecunia.getWritableDatabase();

                //pa obtener el folio
                int folioUsuario = DbPecunia.obtenerFolio(usuario);

               if (db != null){
                    Long elimina = DbPecunia.eliminarUsuario(folioUsuario);
                    if (elimina  > 0) {
                        Toast.makeText(PerfilUsuario.this, "Usuario Eliminado", Toast.LENGTH_SHORT).show();

                        Intent intentFijo = new Intent(PerfilUsuario.this, MainActivity.class);

                        PerfilUsuario.this.startActivity(intentFijo);
                    } else {
                        Toast.makeText(PerfilUsuario.this, "No fue posible eliminar el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Configurar el botón "Cancelar"
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Aquí puedes colocar el código que se ejecutará al hacer clic en "Cancelar"
                // Por ejemplo, puedes cerrar el diálogo.
                dialogInterface.dismiss();
            }
        });

        // Mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
