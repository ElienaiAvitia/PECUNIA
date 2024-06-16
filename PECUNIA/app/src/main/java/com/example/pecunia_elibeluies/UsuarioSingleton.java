package com.example.pecunia_elibeluies;

import androidx.appcompat.app.AppCompatActivity;

public class UsuarioSingleton extends AppCompatActivity {
    private static UsuarioSingleton instance;
    private int folioUsuario;

    //constructor
    private UsuarioSingleton() {

    }

    public static synchronized UsuarioSingleton getInstance() {
        if (instance == null) {
            instance = new UsuarioSingleton();
        }
        return instance;
    }

    public int getFolioUsuario() {
        return folioUsuario;
    }

    public void setFolioUsuario(int folioUsuario) {
        this.folioUsuario = folioUsuario;
    }
}
