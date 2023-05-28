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
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    /*Definicones para enviar los datos a otras pantallas*/
    public static final String EXTRA_PALABRA = "com.example.myfirstapp.PALABRA";
    public static final String EXTRA_DEFINCION = "com.example.myfirstapp.DEFINICION";
    public static final String EXTRA_RESTRICCIONES = "com.example.myfirstapp.RESTRICCIONES";
    public static final String EXTRA_POSSIBLES_PARAULES = "com.example.myfirstapp.POSSIBLES_PARAULES";
    public String definicio;
    public String palabraSelecionada;
    public String posibles;
    public String restriccionesDatos;
    /*variables que me condicionan el juego*/
    public final int INTENTOS = 5;
    public final int LONGITUD = 5;
    public int intentos_actual = 0;
    public int longitud_palabra = 0;
    public boolean hasGanado;
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
    public TreeMap<Character, UnsortedLinkedListSet<Integer>> restricciones; //si

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
    /*se llama a para obtener la definicion aleatoria solo uno vez cuando se carga la pantalla pricipal
    * */
    @Override
    protected void onStart() {
        super.onStart();
        obtenerDefinicon();
        //resetValoresYDeStructurasConjuntos();
    }
    /* se puede separa la carga de datos y manterner los datos de soluciones, usar un copia de solucione en ves vaciar el mismo
    * tambien la limpieza de "datos" de las interfaz"*/
    @Deprecated
    private void resetValoresYDeStructurasConjuntos(){
        if(hasGanado)soluciones = new TreeSet<String>();
    }
    private void obtenerDefinicon(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        JSONObject jsonObject = new JSONObject(agafaHTML(palabraSelecionada));
                        definicio = jsonObject.getString("d");

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    private void cargarDatos() {
        this.hasGanado = false;
        diccionario = new HashMap<String, String>();
        soluciones = new TreeSet<String>();
        InputStream is = getResources().openRawResource(R.raw.paraules);
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        try {
            String linea;
            while ((linea = r.readLine()) != null) {
                if ((linea.length() - 1) / 2 == LONGITUD) {
                    String valor_palabra[] = linea.split(";");
                    diccionario.put(valor_palabra[1], valor_palabra[0]);
                    soluciones.add(valor_palabra[1]);
                }

            }
            Random random = new Random();
            this.palabraSelecionada = (String) diccionario.keySet().stream().skip(random.nextInt(diccionario.size())).findFirst().orElse(null);
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

        try {
            URL url = new URL("https://www.vilaweb.cat/paraulogic/?diec=" + palabra);

            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;

            StringBuffer contingut = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                contingut.append(line);
            }
            bufferedReader.close();
            return contingut.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("error");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return palabra;
    }


    /*implemnatcion de logica de las pantallas, donde se procesan para convertilos en String el map
     *de restricciones y el set posibles palabras
     * */
    public void logica(TreeMap<Character, UnsortedLinkedListSet<Integer>> restricciones, TreeSet<String> palabrasPosibles) {
        /* hay se cierra la pantalla*/
        if (hasGanado) {
            Intent intent = new Intent(this, PantallaGanador.class);
            intent.putExtra(EXTRA_PALABRA, palabraSelecionada);
            intent.putExtra(EXTRA_DEFINCION, definicio);
            startActivity(intent);

        } else {
            Intent intent = new Intent(this, PantallaPerdedor.class);
            intent.putExtra(EXTRA_PALABRA, palabraSelecionada);
            intent.putExtra(EXTRA_DEFINCION, definicio);
            intent.putExtra(EXTRA_RESTRICCIONES, "restricciones");
            intent.putExtra(EXTRA_POSSIBLES_PARAULES, "posibles palabras");
            startActivity(intent);
        }
    }

    public String obtenirParaula() {
        StringBuilder aux = new StringBuilder();
        for (int i = 0; i < longitud_palabra; i++) {
            TextView aux1 = (TextView) findViewById(Integer.parseInt(intentos_actual + "" + i));
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
                    TextView casilla = (TextView) b.getViewById(posicion - 1);
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
                    if (diccionario.containsKey(paraula)) {
                        //si existeix i per tant hem de omplir les restriccions
                        if (paraula.equals(palabraSelecionada)) {
                            //si s'ha paruala es la correcta donncs no actualitzam ses restriccions i solucions
                            //pasar a pantalla de ganador
                            hasGanado = true;
                        } else {
                            //hem d'actualitzar restriccions i solucions
                            char p[] = paraula.toUpperCase().toCharArray();
                            for (int i = 0; i < p.length; i++) {
                                UnsortedLinkedListSet<Integer> list = (UnsortedLinkedListSet<Integer>) registroPalabraActual.get(p[i]);
                                TextView aux1 = (TextView) findViewById(Integer.parseInt(intentos_actual + "" + i));
                                GradientDrawable gd = new GradientDrawable();
                                if (list.isEmpty()) {
                                    //la lletra no hi esta en la paraula
                                    restricciones.put(p[i], new UnsortedLinkedListSet<Integer>());
                                    gd.setColor(Color.RED);
                                } else {
                                    //la lletra si esta, de aqui tenim dos posibilitats, que hi este en sa posicio correcta o
                                    //que no hi esta en sa posicio correcta
                                    if (list.contains(i)) {
                                        //la lletra esta en sa posicio correcta
                                        UnsortedLinkedListSet<Integer> aux2 = restricciones.get(p[i]);
                                        if (restricciones.get(p[i]) != null) {
                                            //si hi esta la restriccio
                                            aux2.add(i);
                                            restricciones.put(p[i], aux2);
                                        } else {
                                            //no hi esta la restriccio
                                            UnsortedLinkedListSet<Integer> aux3 = new UnsortedLinkedListSet<>();
                                            aux3.add(i);
                                            restricciones.put(p[i], aux3);
                                        }
                                        gd.setColor(Color.GREEN);
                                    } else {
                                        //la lletra esta en sa posicio incorrecta
                                        UnsortedLinkedListSet<Integer> aux2 = restricciones.get(p[i]);
                                        if (restricciones.get(p[i]) != null) {
                                            //si hi esta la restriccio
                                            aux2.add(-1);
                                            restricciones.put(p[i], aux2);
                                        } else {
                                            //no hi esta la restriccio
                                            UnsortedLinkedListSet<Integer> aux3 = new UnsortedLinkedListSet<>();
                                            aux3.add(-1);
                                            restricciones.put(p[i], aux3);
                                        }
                                        gd.setColor(Color.YELLOW);
                                    }
                                }
                                aux1.setBackground(gd);
                            }
                        }
                        intentos_actual++;
                        longitud_palabra = 0;
                    }
                    String palabraReferencia = palabraSelecionada;


                    //startActivity(intentGanddor);
                }  else {
                    Context context = getApplicationContext();
                    CharSequence text = "Paraula incompleta!";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
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