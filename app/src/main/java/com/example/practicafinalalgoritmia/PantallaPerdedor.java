package com.example.practicafinalalgoritmia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PantallaPerdedor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_perdedor);
        Intent intent = getIntent();
        String palabra = intent.getStringExtra(MainActivity.EXTRA_PALABRA);
        String defincio = intent.getStringExtra(MainActivity.EXTRA_DEFINCION);
        String restriccions = intent.getStringExtra(MainActivity.EXTRA_RESTRICCIONES);
        String paraulesPossibles = intent.getStringExtra(MainActivity.EXTRA_POSSIBLES_PARAULES);

        TextView palabraTextView = (TextView) findViewById(R.id.textViewParaula);
        TextView definicioTextView = (TextView) findViewById(R.id.textViewDefincion);
        TextView restriccionsTextView = (TextView) findViewById(R.id.textViewRestriccio);
        TextView paraulesPossiblesTextView = (TextView) findViewById(R.id.textViewParaulesPosibles);

        palabraTextView.setText(palabra);
        definicioTextView.setText(defincio);
        restriccionsTextView.setText(restriccions);
        paraulesPossiblesTextView.setText(paraulesPossibles);


    }
}