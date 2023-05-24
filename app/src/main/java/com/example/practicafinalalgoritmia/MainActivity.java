package com.example.practicafinalalgoritmia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practicafinalalgoritmia.EDyAII.UnsortedLinkedListSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    /*Definicones para enviar los datos a otras pantallas*/
    public static final String EXTRA_PALABRA = "com.example.myfirstapp.PALABRA";
    public static final String EXTRA_DEFINCION = "com.example.myfirstapp.DEFINICION";
    public static final String EXTRA_RESTRICCIONES = "com.example.myfirstapp.RESTRICCIONES";
    public static final String EXTRA_POSSIBLES_PARAULES = "com.example.myfirstapp.POSSIBLES_PARAULES";
    /*variables que me condicionan el juego*/
    public final int INTENTOS = 5;
    public final int LONGITUD = 5;
    public int intentos_actual = 0;
    public int longitud_palabra = 0;
    public String Paraula_Seleccionada;
    /*fin de variables de condicionamiento de juego*/
    /*medidas para poder implementar los textviews*/
    public int MaxHeightDisplay;
    public int MaxWidthDisplay;
    public int TextViewWidth;
    public int TextViewHeight;
    public int ButtonHeight;
    public int ButtonWidth;
    /*fin de medidas*/
    /*variables para saber en que momento estoy de mi juego y en que posicion de la etapa*/
    public int intentosActual = 0;
    public int pospalabra = 0;
    /*fin de variables de etapa y posicionamiento*/
    /*estructuras que se van a usar globalmente*/
    public UnsortedArrayMapping registroPalabraActual;
    public HashMap diccionario;
    public TreeSet<String> soluciones;
    //-1 esta, value list como el teclado
    public TreeMap<Character,UnsortedLinkedListSet<Integer>> restricciones; //si
    /*fin de estructuras*/
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
        TextViewWidth = (MaxWidthDisplay / LONGITUD) - 10;
        TextViewHeight = (MaxWidthDisplay / LONGITUD) - 10;
        ButtonHeight = (MaxWidthDisplay / 8) - 10;
        ButtonWidth = (MaxWidthDisplay / 8) - 10;
        cargarDatos();
        crearGraella();
        crearTeclat();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    private void cargarDatos() {
        diccionario = new HashMap<String,String>();
        soluciones = new TreeSet<String>();
        InputStream is = getResources().openRawResource(R.raw.paraules);
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        try {
            String linea;
            while ((linea = r.readLine()) != null) {
                if ((linea.length() - 1) / 2 == LONGITUD) {
                    String valor_palabra[] = linea.split(";");
                    diccionario.put(valor_palabra[1],valor_palabra[0]);
                    soluciones.add(valor_palabra[1]);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                r.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String agafaHTML(String palabra) {
        StringBuilder data = new StringBuilder();
        try {
            URL url = new URL(" https://www.vilaweb.cat/paraulogic/?diec=" + palabra);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = in.readLine();
            while (line!=null){
                data.append(line);
                line = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return data.toString();
    }

    /*implemnatcion de logica, entra el valor de la plabra introducido por el usuario
    * */
    public void logica(String palabraReferencia) {
       /* hay se cierra la pantalla

       String definicio;
        try {
            JSONObject jsonObject = new JSONObject(agafaHTML(palabraReferencia));
            definicio = jsonObject.getString("d");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }*/
        boolean hasGanado= true;
        if(hasGanado){
            Intent intent = new Intent(this, PantallaGanador.class);
            intent.putExtra(EXTRA_PALABRA,"palabra");
            intent.putExtra(EXTRA_DEFINCION,"denfinicon");
            startActivity(intent);

        }else{
            Intent intent = new Intent(this, PantallaPerdedor.class);
            intent.putExtra(EXTRA_PALABRA,"palabra");
            intent.putExtra(EXTRA_DEFINCION,"denfinicon");
            intent.putExtra(EXTRA_POSSIBLES_PARAULES,"posibles palabras");
            startActivity(intent);
        }



        /*Intent intent = new Intent(this, PantallaGanador.class);
        startActivity(intent);
        AtomicBoolean letrasIguales = new AtomicBoolean(true);
        soluciones.forEach((key, value) -> {

            for (int i = 0; i < palabraReferencia.length(); i++) {
                if ((key.charAt(i) != palabraReferencia.charAt(i))) {
                    letrasIguales.set(false);
                    break;
                }
            }
            if (letrasIguales.get()) {
                //soluciones.put(soluciones.);
            }

        });*/




    }
    public String obtenirParaula(){
        StringBuilder aux = new StringBuilder();
        for(int i = 0; i<longitud_palabra;i++){
            TextView aux1 = (TextView) findViewById(Integer.parseInt(intentos_actual+""+i));
            aux.append(aux1.getText());
        }
        return aux.toString();
    }

    public void crearGraella() {
        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        //inicializamos variables para las posiciones de las textview
        int X = 0;
        int Y = 0;
        for (int i = 0; i < INTENTOS; i++) {
            if (i == 0) {
                Y += 30;
            } else {
                Y += TextViewHeight;
            }
            for (int j = 0; j < LONGITUD; j++) {
                if (j == 0) {
                    X += 30;
                } else {
                    X += TextViewWidth;
                }
                //llamamos al funcion createTextView para que nos cree
                //un TextView y lo modifique como queramos
                TextView cuadrat = CreateTextView(i, j, X, Y);
                //añadimos el TextView al layout de la app
                constraintLayout.addView(cuadrat);
            }
            X = 0;
        }
    }

    public void crearTeclat() {
        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        int x = 0;
        int y = MaxHeightDisplay - 5 * ButtonHeight;
        CreateStructureMapping();
        Iterator it = registroPalabraActual.iterator();
        int j = 0;
        while (it.hasNext()) {
            UnsortedArrayMapping.Pair p = (UnsortedArrayMapping.Pair) it.next();
            if (j == 9) {
                x = 0;
                y = y + ButtonHeight;
                j = 0;
            }
            bottonBorrar(constraintLayout);
            bottonComprobar(constraintLayout);
            Button button = CreateButton(x, y, (Character) p.getKey());

            constraintLayout.addView(button);
            j++;
            x += ButtonWidth - 6;
        }
    }

    private void bottonBorrar(ConstraintLayout constraintLayout) {
        Button buttonEsborrar = new Button(this);
        buttonEsborrar.setText("Esborrar");

        int buttonWidth = 400;
        int buttonHeight = 200;
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.height = buttonHeight;
        params.width = buttonWidth;
        buttonEsborrar.setLayoutParams(params);
        buttonEsborrar.setY(MaxHeightDisplay - 7 * ButtonHeight);
        buttonEsborrar.setX((MaxWidthDisplay >> 1) - (float) (buttonWidth + (buttonWidth * 0.05)));
        // Afegir el botó al layout
        constraintLayout.addView(buttonEsborrar);
        // Afegir la funcionalitat al botó
        buttonEsborrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (longitud_palabra > 0 && longitud_palabra <= LONGITUD) {
                    ConstraintLayout b = findViewById(R.id.layout);
                    int posicion = (intentos_actual * 10) + longitud_palabra;
                    TextView casilla = (TextView) b.getViewById(posicion-1);
                    casilla.setText("");
                    if (longitud_palabra > 0) longitud_palabra--;
                }

            }
        });
    }

    public void bottonComprobar(ConstraintLayout constraintLayout) {
        Button bottonComprobar = new Button(this);
        bottonComprobar.setText("Comprobar");
        int buttonWidth = 400;
        int buttonHeight = 200;
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.height = buttonHeight;
        params.width = buttonWidth;
        bottonComprobar.setLayoutParams(params);
        bottonComprobar.setY(MaxHeightDisplay - 7 * ButtonHeight);
        bottonComprobar.setX(MaxWidthDisplay / 2 + (float) (buttonWidth * 0.05));
        // Afegir el botó al layout

        // Afegir la funcionalitat al botó
        bottonComprobar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String paraula;
                if (longitud_palabra == LONGITUD) {
                    //agafam la paraula dels text views
                    paraula = obtenirParaula().toLowerCase();
                    //comprobam si existeix la paraula
                    if(diccionario.containsKey(paraula)){
                        //si existeix i per tant hem de omplir les restriccions
                        if(paraula.equals())
                        //una vegada que hem fet ses reduccions i adiccions als conjunts
                        //aumentam intentos_actual i posam longitud_palabra a 0
                        intentos_actual++;
                        longitud_palabra = 0;
                        System.out.println("la paraula existeix");
                    }
                   //logica(" ");
                    //startActivity(intentGanddor);
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Paraula incompleta!";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();
                }

            }
        });
        constraintLayout.addView(bottonComprobar);

    }


    ;

    public Button CreateButton(int X, int Y, Character ch) {
        Button aux = new Button(this);
        aux.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (longitud_palabra < LONGITUD) {
                    ConstraintLayout b = findViewById(R.id.layout);
                    int posicion = (intentos_actual * 10) + longitud_palabra;

                    TextView casilla = (TextView) b.getViewById(posicion);
                    casilla.setTextColor(Color.GRAY);
                    casilla.setText(ch.toString());
                    casilla.setGravity(Gravity.CENTER);

                    longitud_palabra++;
                }

            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ButtonHeight, ButtonWidth);
        String text = ch.toString();
        aux.setText(text);
        aux.setLayoutParams(layoutParams);
        // Posicionar el TextView
        aux.setX(X);
        aux.setY(Y);
        return aux;
    }

    public TextView CreateTextView(int fila, int columna, int X, int Y) {
        TextView aux = new TextView(this);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(3, Color.GRAY);
        aux.setBackground(gd);
        aux.setId(Integer.parseInt(fila + "" + columna));
        aux.setWidth(TextViewWidth - 5);
        aux.setHeight(TextViewHeight - 5);
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

    public void CreateStructureMapping() {
        char[] character = "abcdefghijklmnopqrstuvwxyzç".toUpperCase(Locale.ROOT).toCharArray();
        registroPalabraActual = new UnsortedArrayMapping<Character, UnsortedLinkedListSet<Integer>>(character.length);
        UnsortedLinkedListSet<Integer> listaPosPal;
        boolean change = true;
        for (int i = 0; i < character.length; i++) {
            listaPosPal = new UnsortedLinkedListSet<Integer>();
            registroPalabraActual.put(character[i], listaPosPal);
        }
    }
}