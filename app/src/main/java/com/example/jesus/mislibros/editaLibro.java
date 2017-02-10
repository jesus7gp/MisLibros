package com.example.jesus.mislibros;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class editaLibro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edi_libro);
        datosLibro();


    }

    public void editarLibro(){
        Libros libro = new Libros(this);
        SQLiteDatabase database = libro.getWritableDatabase();

        Intent e = getIntent();
        Long id = e.getLongExtra("id_book", 1L);

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
            mensaje("¡El título y el autor son obligatorios!");
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

            libro.editar(database, values, id);

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            mensaje("Se han guardado los cambios.");
        }
    }

    public void mensaje(String mensaje){
        Context context = getApplicationContext();
        CharSequence text = mensaje;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void eliminarLibro(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("No fastidies... ¿Lo vas a borrar?").setPositiveButton("Sí",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        confirmado();

                    }
                })
                .setNegativeButton("Pfff... mejor no", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.guardar).setVisible(false);
        menu.findItem(R.id.editar).setVisible(true);
        menu.findItem(R.id.eliminar).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editar:
                editarLibro();
                return true;
            case R.id.eliminar:
                eliminarLibro();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void confirmado(){
        Libros libro = new Libros(this);
        SQLiteDatabase database = libro.getWritableDatabase();
        Intent e = getIntent();
        Long id = e.getLongExtra("id_book", 1L);
        libro.eliminar(database,id);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        mensaje("Se ha eliminado un libro.");
    }

    public void datosLibro() {
        Libros libro = new Libros(this);
        SQLiteDatabase database = libro.getWritableDatabase();
        Intent e = getIntent();
        Long id = e.getLongExtra("id_book", 1L);

        String search_query = "SELECT * FROM libro WHERE _id="+id;
        Cursor cursor = database.rawQuery(search_query,null);

        TextView txtitulo = (TextView) findViewById(R.id.tx_titulo);
        TextView txautor = (TextView) findViewById(R.id.tx_autor);
        TextView txeditorial = (TextView) findViewById(R.id.tx_editorial);
        TextView txpaginas = (TextView) findViewById(R.id.tx_paginas);
        TextView txisbn = (TextView) findViewById(R.id.tx_isbn);
        TextView txanio = (TextView) findViewById(R.id.tx_anio);
        TextView txresumen = (TextView) findViewById(R.id.tx_resumen);
        RatingBar rbnota = (RatingBar) findViewById(R.id.rb_nota);
        CheckBox chebook= (CheckBox) findViewById(R.id.ch_ebook);
        CheckBox chleido= (CheckBox) findViewById(R.id.ch_leido);

        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){

                String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
                String autor = cursor.getString(cursor.getColumnIndex("autor"));
                String editorial = cursor.getString(cursor.getColumnIndex("editorial"));
                String isbn = cursor.getString(cursor.getColumnIndex("isbn"));
                String paginas = cursor.getString(cursor.getColumnIndex("paginas"));
                String anio = cursor.getString(cursor.getColumnIndex("anio"));
                String resumen = cursor.getString(cursor.getColumnIndex("resumen"));
                Integer ebook = cursor.getInt(cursor.getColumnIndex("ebook"));
                Integer leido = cursor.getInt(cursor.getColumnIndex("leido"));
                Float nota = cursor.getFloat(cursor.getColumnIndex("nota"));

                txtitulo.setText(titulo);
                txautor.setText(autor);
                txeditorial.setText(editorial);
                txisbn.setText(isbn);
                txpaginas.setText(paginas);
                txanio.setText(anio);
                txresumen.setText(resumen);
                rbnota.setRating(nota);

                if(ebook==1){
                    chebook.setChecked(true);
                }
                else{
                    chebook.setChecked(false);
                }

                if(leido==1){
                    chleido.setChecked(true);
                }
                else{
                    chleido.setChecked(false);
                }

                cursor.moveToNext();
            }
        }
        cursor.close();
    }
}
