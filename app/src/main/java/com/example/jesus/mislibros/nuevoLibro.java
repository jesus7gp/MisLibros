package com.example.jesus.mislibros;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class nuevoLibro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_libro);

        Button boton = (Button) findViewById(R.id.button2);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarLibro();
            }
        });
    }



    public void insertarLibro() {
        Libros libro = new Libros(this);
        SQLiteDatabase database = libro.getWritableDatabase();

        ContentValues values = new ContentValues();
        EditText titulo = (EditText) findViewById(R.id.tx_titulo);
        EditText autor = (EditText) findViewById(R.id.tx_autor);
        EditText editorial = (EditText) findViewById(R.id.tx_editorial);
        EditText isbn = (EditText) findViewById(R.id.tx_isbn);
        EditText paginas = (EditText) findViewById(R.id.tx_paginas);
        EditText anio = (EditText) findViewById(R.id.tx_anio);
        CheckBox ebook = (CheckBox) findViewById(R.id.ch_ebook);
        CheckBox leido = (CheckBox) findViewById(R.id.ch_leido);
        RatingBar nota  = (RatingBar) findViewById(R.id.rb_nota);
        EditText resumen = (EditText) findViewById(R.id.tx_resumen);

        if(titulo.getText().toString().equals("") || autor.getText().toString().equals("")){
            Context context = getApplicationContext();
            CharSequence text = "¡El título y el autor son obligatorios!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {

            values.put("titulo", titulo.getText().toString());
            values.put("autor", autor.getText().toString());
            values.put("editorial", editorial.getText().toString());
            values.put("isbn", isbn.getText().toString());
            values.put("paginas", paginas.getText().toString());
            values.put("anio", anio.getText().toString());
            values.put("ebook", ebook.isChecked());
            values.put("leido", leido.isChecked());
            values.put("nota", nota.getRating());
            values.put("resumen", resumen.getText().toString());

            libro.insertar(database, values);

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}
