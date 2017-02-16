package com.example.jesus.mislibros;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevo(view);
            }
        });

        //Se crea la instancia de la clase de la base de datos
        Libros database = new Libros(this);
        SQLiteDatabase db = database.getWritableDatabase();

        //Se indica la consulta en el cursor
        Cursor cursor = db.rawQuery("SELECT * FROM libro", null);

        //lista de la vista principal
        ListView lista = (ListView) findViewById(R.id.lista);

        //Se instancia el adaptador pasándole por parámetro el cursor con su respectiva consulta
        LibrosAdaptador adaptadorlista = new LibrosAdaptador(this,cursor);

        //Se llama al adaptador anteriormente creado
        lista.setAdapter(adaptadorlista);
        lista.setOnItemClickListener(this);
    }

    public void nuevo(View view){
        Intent i = new Intent(this, nuevoLibro.class);
        startActivity(i);
    }

    public void edicion(View view, long id){
        Intent i = new Intent(this, editaLibro.class);
        //Habrá que pasarle la id con putExtra
        i.putExtra("id_book", id);
        startActivity(i);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Se accede a edición cuando se pulsa un item de la lista
        edicion(view, id);
    }



}
