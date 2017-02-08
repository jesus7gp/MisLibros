package com.example.jesus.mislibros;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JESUS on 31/01/2017.
 */

public class Libros extends SQLiteOpenHelper {

    public Libros(Context context) {
        super(context, "libros", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE libro (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "titulo TEXT,"+
                "autor TEXT,"+
                "editorial TEXT,"+
                "isbn TEXT,"+
                "anio TEXT,"+
                "paginas TEXT,"+
                "ebook INTEGER,"+
                "leido INTEGER,"+
                "nota FLOAT,"+
                "resumen TEXT)"
        );


        database.execSQL("INSERT INTO libro VALUES(" +
                "null,"+
                "'Libro1',"+
                "'autor1',"+
                "'editorial1',"+
                "'isbn1',"+
                "'1990',"+
                "'400',"+
                "0,"+
                "1,"+
                "5,"+
                "'Es muy bonito')"
        );

    }

    public void insertar(SQLiteDatabase database, ContentValues values) {
        database.insert("libro", null, values);
    }

    public void editar(SQLiteDatabase database, ContentValues values, long id) {
        String sentencia = "_id" + " = ?";
        String id_ = String.valueOf(id);
        String[] argid = { id_ };
        database.update("libro", values, sentencia, argid);
    }

    public void eliminar(SQLiteDatabase database, long id) {
        database.delete("libro", "_id" + "=" + id, null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
