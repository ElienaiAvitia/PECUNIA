package com.example.pecunia_elibeluies.db;
import androidx.annotation.Nullable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;

public class dbPecunia extends dbHelper{

    private Context context;

    public dbPecunia(@Nullable Context context) {
        super(context);
        this.context = context;

    }
    //para usuario------------------------------------------------------------------------------------
    public long insertarUsuario( String nameR,String emailR, String passwordr){
        //Creamos un objeto para realizar la escritura de los datos que usaremos
        //creamos un objeto de la clase dbhelper y vamos a escribir en nuestra bd
        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("UserName", nameR);
        values.put("Email", emailR);
        values.put("Password", passwordr);

        //ejecuta
        long id = db.insert(DB_TABLE_USUARIO, null, values);

        return id;
    }

    /*else {
        return -1; // Manejar el caso en el que no se pudo obtener la instancia de la base de datos
    }*/
    /* public long Ingresos()*/

    //metodo actualizar contraseña
    public long actualizarPassword(int folioUsuario, String password){

        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        //mandamos valor nuevo
        ContentValues valor = new ContentValues();
        valor.put("Password", password);

        //ejecuta
        long id = db.update(DB_TABLE_USUARIO, valor, "FolioUsuario = " + folioUsuario, null);

        return id;
    }

    //metodo actualizar nombre
    public long actualizarNombre(int folioUsuario, String userName){

        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        //mandamos valor nuevo
        ContentValues valor = new ContentValues();
        valor.put("UserName", userName);

        //ejecuta
        long id = db.update(DB_TABLE_USUARIO, valor, "FolioUsuario = " + folioUsuario, null);

        return id;
    }

    //para meta------------------------------------------------------
    //metodo insertar en meta
    public long insertarMeta(String fechaInicio, String fechaLimite, double cantidadRecaudada, double cantidadAlcanzar,
                             String concepto, int folioUsuario) {

        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("FechaInicio", fechaInicio);
        values.put("FechaLimite", fechaLimite);
        values.put("CantidadRecaudada", cantidadRecaudada);
        values.put("CantidadAlcanzar", cantidadAlcanzar);
        values.put("Concepto", concepto);
        values.put("FolioUsuario", folioUsuario);

        //ejecuta
        long id = db.insert(DB_TABLE_META, null, values);

        return id;
    }

    //para ingresos--------------------------------------------------
    public long insertarIngreso(String concepto, String tipo, String fechaIngreso, String periodo,
                                double cantidad, int folioUsuario){

        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Concepto", concepto);
        values.put("Tipo", tipo);
        values.put("FechaIngreso", fechaIngreso);
        values.put("Periodo", periodo);
        values.put("Cantidad", cantidad);
        values.put("FolioUsuario", folioUsuario);

        //ejecuta
        long id = db.insert(DB_TABLE_INGRESOS, null, values);

        return id;
    }

    //para egresos---------------------------------------------------
    public long insertarEgreso(String concepto, String tipo, String fechaEgreso, String periodo,
                               double cantidad, int folioUsuario){

        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Concepto", concepto);
        values.put("Tipo", tipo);
        values.put("Fecha", fechaEgreso);
        values.put("Periodo", periodo);
        values.put("Cantidad", cantidad);
        values.put("FolioUsuario", folioUsuario);

        //ejecuta
        long id = db.insert(DB_TABLE_EGRESOS, null, values);

        return id;
    }
    public long insertarIngresoV(String concepto, String tipo, String fechaIngreso,
                                double cantidad, int folioUsuario){

        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Concepto", concepto);
        values.put("Tipo", tipo);
        values.put("FechaIngreso", fechaIngreso);
        values.put("Cantidad", cantidad);
        values.put("FolioUsuario", folioUsuario);

        //ejecuta
        long id = db.insert(DB_TABLE_INGRESOS, null, values);

        return id;
    }

    //para egresos---------------------------------------------------
    public long insertarEgresoV(String concepto, String tipo, String fechaEgreso,
                               double cantidad, int folioUsuario){

        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Concepto", concepto);
        values.put("Tipo", tipo);
        values.put("Fecha", fechaEgreso);
        values.put("Cantidad", cantidad);
        values.put("FolioUsuario", folioUsuario);

        //ejecuta
        long id = db.insert(DB_TABLE_EGRESOS, null, values);

        return id;
    }
    public boolean verificarCredenciales(String nombreUsuario, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
       // int folioUsuario = 1;
        // Define la consulta SQL
        String consulta = "SELECT * FROM " + DB_TABLE_USUARIO + " WHERE UserName = ? AND Password = ?";

        // Ejecuta la consulta con dos parámetros en lugar de
        Cursor cursor = db.rawQuery(consulta, new String[]{nombreUsuario, password});

        //Obtiene el folio
        //UsuarioSingleton.getInstance().setFolioUsuario(folioUsuario);

        // Verifica si se encontraron resultados
        boolean credencialesCorrectas = cursor.moveToFirst();

        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();

        return credencialesCorrectas;
    }

    public int obtenerFolio(String nombre){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT FolioUsuario FROM Usuario WHERE UserName = ?", new String[] { nombre });
        int folioUsuario = -1;
        if (cursor.moveToFirst()) {
            folioUsuario = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return folioUsuario;
    }

    public long eliminarUsuario(int FolioUsuario){
        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        //elimina primero en tablas relacionadas
        long in = db.delete(DB_TABLE_INGRESOS, "FolioUsuario = "+ FolioUsuario, null);
        long eg = db.delete(DB_TABLE_EGRESOS, "FolioUsuario = "+ FolioUsuario, null);
        long me = db.delete(DB_TABLE_META, "FolioUsuario = "+ FolioUsuario, null);
        //elimina usuario
        long id = db.delete(DB_TABLE_USUARIO, "FolioUsuario = "+ FolioUsuario, null);

        return id;
    }

    public double obtenerDiferenciaSumaColumnas(int usuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        double sumaColumna1 = obtenerSumaColumna(db, DB_TABLE_INGRESOS, "Cantidad", usuario);
        double sumaColumna2 = obtenerSumaColumna(db, DB_TABLE_EGRESOS, "Cantidad", usuario);

        db.close();

        return sumaColumna1 - sumaColumna2;
    }

    // Método para obtener la suma de una columna específica de una tabla
    private double obtenerSumaColumna(SQLiteDatabase db, String nombreTabla, String nombreColumna, int User) {
        double suma = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + nombreColumna + ") FROM " + nombreTabla + " WHERE FolioUsuario = " + User, null);

        if (cursor.moveToFirst()) {
            suma = cursor.getDouble(0);
        }

        cursor.close();
        return suma;
    }

    public Long eliminarEgresoPorFolio(int folioEgreso) {
        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        //elimina usuario
        long id = db.delete(DB_TABLE_EGRESOS, "FolioEgreso = "+ folioEgreso, null);

        return id;
    }
    public Cursor obtenerEgresos() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "Concepto",
                "Tipo",
                "Fecha",
                "Cantidad"
        };

        Cursor cursor = db.query(
                DB_TABLE_EGRESOS,  // Nombre de la tabla
                projection,       // Columnas que deseas recuperar
                null,             // Cláusula WHERE
                null,             // Argumentos para la cláusula WHERE
                null,             // Agrupar por
                null,             // Filtrar por grupo
                null              // Ordenar por
        );

        return cursor;
    }

    /*public Cursor obtenerDatos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(DB_TABLE_EGRESOS, null, null, null, null, null, null);
    }

    //metodo para hacer la vista de los registros
    public Cursor obtenerEgresos() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( " SELECT * FROM " + DB_TABLE_EGRESOS, null );
        return res;
    }

    //metodo para actualizar los datos*/

}