package com.example.practicafinalalgoritmia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public int intentos = 5;
    public int longitud = 7;
    public int MaxHeightDisplay;
    public int MaxWidthDisplay;
    public int TextViewWidth;
    public int TextViewHeight;
    public int ButtonHeight;
    public int ButtonWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Object to store display information
        DisplayMetrics metrics = new DisplayMetrics();
        // Get display information
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        MaxHeightDisplay = metrics.heightPixels;
        MaxWidthDisplay = metrics.widthPixels;
        TextViewWidth = (MaxWidthDisplay/longitud) - 10;
        TextViewHeight = (MaxWidthDisplay/longitud) - 10;
        ButtonHeight = (MaxWidthDisplay/13)-10;
        ButtonWidth = (MaxWidthDisplay/13)-10;
        crearGraella();
    }
    public void crearGraella(){
        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        //inicializamos variables para las posiciones de las textview
        int X = 0;
        int Y = 0;
        for(int i = 0; i < intentos; i++){
            if(i == 0){
                Y+=30;
            }else{
                Y+=TextViewHeight;
            }
            for(int j = 0; j< longitud; j++){
                if(j == 0){
                    X+=30;
                }else{
                    X+=TextViewWidth;
                }
                //llamamos al funcion createTextView para que nos cree
                //un TextView y lo modifique como queramos
                TextView cuadrat = CreateTextView(i,j,X,Y);
                //añadimos el TextView al layout de la app
                constraintLayout.addView(cuadrat);
            }
            X = 0;
        }
    }
    public void crearTeclat{
        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        char [] character = "abcdefghijklmnopqrstuvwxyzç".toCharArray();
        Character alfabeto[]= new Character[26];
        int x = 30;
        int y = MaxHeightDisplay-2*ButtonHeight;
        boolean change = true;
        for(int i = 0; i<alfabeto.length;i++){
            alfabeto[i] = character[i];
            if(i >= 13 && change){
                x = 0;
                y=MaxHeightDisplay-ButtonHeight;
                change = false;
            }
            x+=ButtonWidth;
            Button button = CreateButton(x,y,character[i]);
            constraintLayout.addView(button);
        }
    }
    public Button CreateButton(int X,int Y,Character ch){
        Button aux = new Button(this);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(3, Color.GRAY);
        String text = ch.toString();
        aux.setText(text);
        aux.setBackground(gd);
        aux.setWidth(ButtonWidth-5);
        aux.setHeight(ButtonHeight-5);
        // Posicionar el TextView
        aux.setX(X);
        aux.setY(Y);
        return aux;
    }
    public TextView CreateTextView(int fila, int columna, int X, int Y){
        TextView aux = new TextView(this);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(3, Color.GRAY);
        String text = fila+""+columna;
        aux.setText(text);
        aux.setBackground(gd);
        aux.setId(Integer.parseInt(fila+""+columna));
        aux.setWidth(TextViewWidth-5);
        aux.setHeight(TextViewHeight-5);
        // Posicionar el TextView
        aux.setX(X);
        aux.setY(Y);
        //no se para que sirve el gravity
        //aux.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        //ponemos color
        aux.setTextColor(Color.RED);
        aux.setTextSize(30);
        return aux;
    }
}