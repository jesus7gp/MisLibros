package com.example.jesus.mislibros;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class LibrosAdaptador extends CursorAdapter {
    public LibrosAdaptador(Context contexto, Cursor cursor) {
        super(contexto, cursor);
    }

    @Override
    public View newView(Context contexto, Cursor cursor, ViewGroup viewgroup) {
        return LayoutInflater.from(contexto).inflate(R.layout.elemento_lista, viewgroup, false);
    }

    @Override
    public void bindView(View vista, Context contexto, Cursor cursor) {
        TextView titulo = (TextView) vista.findViewById(R.id.titulo);
        TextView autor = (TextView) vista.findViewById(R.id.autor);
        RatingBar nota = (RatingBar) vista.findViewById(R.id.ratingBar);
        ImageView img = (ImageView)  vista.findViewById(R.id.imageView);

        String titulo1 = cursor.getString(cursor.getColumnIndex("titulo"));
        String autor1 = cursor.getString(cursor.getColumnIndex("autor"));
        Float rating1 = cursor.getFloat(cursor.getColumnIndex("nota"));


        titulo.setText(titulo1);
        autor.setText(autor1);
        nota.setRating(rating1);

        //Este switch básicamente se encarga de poner una imagen aleatoria
        switch (generaNumeroAleatorio(0,2)) {
            case 0:
                img.setImageResource(R.drawable.libro1);
                break;

            case 1:
                img.setImageResource(R.drawable.libro2);
                break;

            case 2:
                img.setImageResource(R.drawable.libro3);
                break;

        }

    }

    //Función encargada de generar un número aleatorio
    public static int generaNumeroAleatorio(int minimo,int maximo){

        int num=(int)Math.floor(Math.random()*(maximo-minimo+1)+(minimo));
        return num;
    }
}
