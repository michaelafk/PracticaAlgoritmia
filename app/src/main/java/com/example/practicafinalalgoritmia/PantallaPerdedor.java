package com.example.practicafinalalgoritmia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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
    }
}