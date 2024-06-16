package com.example.pecunia_elibeluies.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;//version de la bd
    public static final String DATABASE_NAME ="PECUNIA";//nombre bd
    public static final String DB_TABLE_USUARIO ="Usuario";//tabla usuarios
    public static final String DB_TABLE_META ="Meta";//tabla meta
    public static final String DB_TABLE_INGRESOS ="Ingresos";//tabla ingresos
    public static final String DB_TABLE_EGRESOS ="Egresos";//tabla egresos

    //hacer uso de la bd
    public dbHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //metodo para crear las tablas
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Crear tabla usuario
        sqLiteDatabase.execSQL(" CREATE TABLE " + DB_TABLE_USUARIO +
                "(" + "FolioUsuario INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "UserName VARCHAR (100) UNIQUE NOT NULL,"
                + "Email VARCHAR (100) NOT NULL,"
                + "Password VARCHAR (100) NOT NULL,"
                + "UNIQUE (Email));");

        //crear tabla meta
        sqLiteDatabase.execSQL(" CREATE TABLE " + DB_TABLE_META +
                "(" + "FolioMeta INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "FechaInicio DATE NOT NULL,"
                + "FechaLimite DATE NOT NULL,"
                + "CantidadRecaudada NUMERIC NOT NULL,"
                + "CantidadAlcanzar NUMERIC NOT NULL,"
                + "Concepto VARCHAR (150) NOT NULL,"
                + "FolioUsuario INTEGER,"
                + "FOREIGN KEY (FolioUsuario) REFERENCES " + DB_TABLE_USUARIO + "(FolioUsuario)"
                + ");");

        //crear tabla ingresos
        sqLiteDatabase.execSQL(" CREATE TABLE " + DB_TABLE_INGRESOS +
                "(" + "FolioIngreso INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Concepto VARCHAR (150) NOT NULL,"
                + "Tipo VARCHAR (100) NOT NULL,"
                + "FechaIngreso DATE NOT NULL,"
                + "Periodo TEXT,"
                + "Cantidad NUMERIC NOT NULL,"
                + "FolioUsuario INTEGER,"
                + "FOREIGN KEY (FolioUsuario) REFERENCES " + DB_TABLE_USUARIO + "(FolioUsuario)"
                + ");");

        //crear tabla egresos
        sqLiteDatabase.execSQL(" CREATE TABLE " + DB_TABLE_EGRESOS +
                "(" + "FolioEgreso INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Concepto VARCHAR (150) NOT NULL,"
                + "Tipo VARCHAR (100) NOT NULL,"
                + "Fecha DATE NOT NULL,"
                + "Periodo TEXT,"
                + "Cantidad NUMERIC NOT NULL,"
                + "FolioUsuario INTEGER,"
                + "FOREIGN KEY (FolioUsuario) REFERENCES " + DB_TABLE_USUARIO + "(FolioUsuario)"
                + ");");
    }

    //para cuando se actualice la bd (actualiza tablas)
    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int j){

        //tabla usuario
        sqLiteDatabase.execSQL((" DROP TABLE " + DB_TABLE_USUARIO));
        onCreate(sqLiteDatabase);

        //tabla meta
        sqLiteDatabase.execSQL((" DROP TABLE " + DB_TABLE_META));
        onCreate(sqLiteDatabase);

        //tabla ingresos
        sqLiteDatabase.execSQL((" DROP TABLE " + DB_TABLE_INGRESOS));
        onCreate(sqLiteDatabase);

        //tabla egresos
        sqLiteDatabase.execSQL((" DROP TABLE " + DB_TABLE_EGRESOS));
        onCreate(sqLiteDatabase);
    }

}