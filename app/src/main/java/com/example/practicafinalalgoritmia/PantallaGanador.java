package com.example.practicafinalalgoritmia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class PantallaGanador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_ganador);
        Intent intent= getIntent();
        String palabra = intent.getStringExtra(MainActivity.EXTRA_PALABRA);
        String defincio= intent.getStringExtra(MainActivity.EXTRA_DEFINCION);

        TextView palabraTextView = (TextView) findViewById(R.id.textViewParaulaGanador);
        TextView definicioTextView = (TextView) findViewById(R.id.textViewDefinicionGanador);

        palabraTextView.setText(palabra);
        definicioTextView.setText(Html.fromHtml(defincio,Html.FROM_HTML_MODE_LEGACY));

        }
}