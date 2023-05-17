package com.example.practicafinalalgoritmia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class PantallaGanador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_ganador);
        Intent intent= getIntent();
        System.out.println(intent.getStringExtra("sad"));;
    }
}