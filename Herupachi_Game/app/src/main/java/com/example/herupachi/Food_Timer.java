package com.example.herupachi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

public class Food_Timer extends AppCompatActivity  implements View.OnClickListener {


    //_______________________________Variablen für Countdown:_______________________________________
    TextView textViewCountdown, text; //Variablen für Countdown und Motivationstext
    ImageView image1_food; //Variable für Image (Belohnung nach Beenden des 15 Minuten Countdowns)
    ImageView image2_food; //Variable für Image (Belohnung nach Beenden des 30 Minuten Countdowns)
    ImageView image3_food; //Variable für Image (Belohnung nach Beenden des 30 Minuten Countdowns)

    ImageView peach;
    ImageView croissant;
    ImageView cake;


    CountDownTimer countDownTimer; //Timer-Objekt
    long countdownProgressMinutes = 15; //Progress, welcher angezeigt wird für Minuten
    long countdownProgressSeconds = 00; //Progress, welcher angezeigt wird für Sekunden

    //_______________________________Variablen für Buttons:_________________________________________
    Button startDreissig;
    Button startSechzig;
    Button startHundertZwanzig;

    ImageView returnButton;

    MainActivity herupachi;

    //Variablen für Pop-up bei Exit:
    //Dialog popUp;

    //Das passiert beim Öffnen dieses Views:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_timer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tab_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewCountdown = findViewById(R.id.countdown); //Verknüpfen von Countdown
        text = findViewById(R.id.motivationText); //Verknüpfen von Motivationstext
        //Verknüpfen von Images / Belohnungen
        image1_food = findViewById(R.id.gift1);
        image1_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starte die MainActivity, wenn auf das Bild geklickt wird
                Intent intent = new Intent(Food_Timer.this, MainActivity.class);
                // Füge den Status des Bildes als Extra hinzu
                intent.putExtra("image1_food_visibility", image1_food.getVisibility());
                startActivity(intent);
            }
        }); //image1 wird auf die MainActivity übertragen, nachdem es geklickt wurde.

        image2_food = findViewById(R.id.gift2);
        image2_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starte die MainActivity, wenn auf das Bild geklickt wird
                Intent intent = new Intent(Food_Timer.this, MainActivity.class);
                // Füge den Status des Bildes als Extra hinzu
                intent.putExtra("image2_food_visibility", image2_food.getVisibility());
                startActivity(intent);
            }
        }); //image wird auf die MainActivity übertragen, nachdem es geklickt wurde.

        image3_food = findViewById(R.id.gift3);
        image3_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starte die MainActivity, wenn auf das Bild geklickt wird
                Intent intent = new Intent(Food_Timer.this, MainActivity.class);
                // Füge den Status des Bildes als Extra hinzu
                intent.putExtra("image3_food_visibility", image3_food.getVisibility());
                startActivity(intent);
            }
        }); //image3 wird auf die MainActivity übertragen, nachdem es geklickt wurde.

        peach = findViewById(R.id.peach);
        croissant = findViewById(R.id.croissant);
        cake = findViewById(R.id.cake);

        //Verknüpfen von Start-Buttons je Einstellung
        startDreissig = (Button) findViewById(R.id.button30Min);
        startDreissig.setOnClickListener(this);

        startSechzig = (Button) findViewById(R.id.button60Min);
        startSechzig.setOnClickListener(this);

        startHundertZwanzig = (Button) findViewById(R.id.button120Min);
        startHundertZwanzig.setOnClickListener(this);

        returnButton = (ImageView) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(this);

        //Verknüpfungen für PopUp:

        //popUp = new Dialog(this);
    }



    /** Allgemeine Countdown Methode
     * Startet einen Countdown.
     * Nach Ablauf des Coountdowns verschwindet die Zeit und der Motivationstext.
     * Stattdessen erscheint ein Bild. (Belohnung je nach Zeitaufwand)
     * @param start Startzeit des Countdowns in Millisekunden.
     * @param intervall in welchem Countdown heruntergezählt wird in Millisekunden.
     * @param image das ImageView, was die Belohnung darstellen soll.
     */
    public void countdown(long start, long intervall, ImageView image){
        countDownTimer = new CountDownTimer(start, intervall) { //start Minuten als Startzeit, intervall Minuten als Intervall
            @Override
            public void onTick(long millisUntilFinished) { //Bei Intervall:
                countdownProgressMinutes = (long) (millisUntilFinished/1000)/60; //Minuten-Countdown in Variable speichern
                countdownProgressSeconds = (long) (millisUntilFinished/1000) % 60; //Sekunden-Countdown in Variable speichern
                String zeit = String.format(Locale.getDefault(), "%02d:%02d",countdownProgressMinutes, countdownProgressSeconds); //Format in welchem der Countdown angezeigt wird.
                textViewCountdown.setText(zeit); //wird auf Countdown angezeigt
            }

            @Override
            public void onFinish() { //Wenn Countdown fertig / bei 0:
                image.setVisibility(View.VISIBLE);  //Bild von Belohnung wird angezeigt
                text.setText("Good job! Here is your reward:"); // Änderung des Motivationstextes
                textViewCountdown.setVisibility(View.GONE); //Countdown verschwindet
                //herupachi.addXP(plusXP); //App crashed, wieso???
                //Kriegt man die XP updated, wenn Belohnungen angewendet werden?
            }
        }.start();

    }


    /** Countdown 15 Minuten
     * Countdown, welcher bei 15 Minuten startet und pro Sekunde runterzählt.
     * Die Zeit wird im Format "Minuten:Sekunden" angezeigt.
     * Wenn der Timer abläuft erscheint eine Belohnung.
     */
    private void countdown30() { //Countdown für 30 Minuten
        textViewCountdown.setText("30"); //Text des Countdown wird auf 30 gesetzt
        countdown(60000*1,1000, image1_food); //Methode wird für einen Start von 30 Minuten und mit Intervall einer Sekunde aufgerufen.
    } //Ist für Testzwecke gerade auf eine Minute gesetzt!!!!

    /** Countdown 30 Minuten
     * Countdown, welcher bei 15 Minuten startet und pro Sekunde runterzählt.
     * Die Zeit wird im Format "Minuten:Sekunden" angezeigt.
     * Wenn der Timer abläuft erscheint eine Belohnung.
     */
    private void countdown60() { //Countdown für 60 Minuten
        textViewCountdown.setText("60"); //Text des Countdown wird auf 60 gesetzt
        countdown(60000 * 60,1000, image2_food); //Methode wird für einen Start von 60 Minuten und mit Intervall einer Sekunde aufgerufen.
    }

    /** Countdown 60 Minuten
     * Countdown, welcher bei 15 Minuten startet und pro Sekunde runterzählt.
     * Die Zeit wird im Format "Minuten:Sekunden" angezeigt.
     * Wenn der Timer abläuft erscheint eine Belohnung.
     */
    private void countdown120() { //Countdown für 120 Minuten
        textViewCountdown.setText("120"); //Text des Countdown wird auf 120 gesetzt
        countdown(60000 * 120,1000, image3_food); //Methode wird für einen Start von 120 Minuten und mit Intervall einer Sekunde aufgerufen.
    }

    /** onClick
     * Bei Klick auf button30Min:
     * andere Buttons werden ausgegraut und 6 XP werden hinzugefügt.
     * Bei Klick auf button60Min:
     * andere Buttons werden ausgegraut und 12 XP werden hinzugefügt.
     * Bei Klick auf button120Min:
     * andere Buttons werden ausgegraut und 24 XP werden hinzugefügt.
     * Bei Klick auf returnButton:
     * Ein Toast wird angezeigt und der Nutzer kehrt zur MainActivity zurück.
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("XP_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (view.getId() == R.id.button30Min) {
            countdown30(); //Wenn 30 Min Button betätigt wird, startet der 15 Min Timer
            //Andere Countdown-Buttons werden deaktiviert und ausgegraut.
            startDreissig.setEnabled(false);
            startSechzig.setEnabled(false);
            startSechzig.setBackgroundTintList(ColorStateList.valueOf(0xFFCCCCCC));
            startHundertZwanzig.setEnabled(false);
            startHundertZwanzig.setBackgroundTintList(ColorStateList.valueOf(0xFFCCCCCC));

            //Die zugehörigen Symbole werden ausgegraut:
            croissant.setColorFilter(0x99FFFFFF, PorterDuff.Mode.SRC_ATOP);
            cake.setColorFilter(0x99FFFFFF, PorterDuff.Mode.SRC_ATOP);


            //XP draufrechnen:
            int currentXP = sharedPreferences.getInt("xp_value", 0); // 0 ist der Standardwert, Momentane XP einlesen
            currentXP += 6; //3 XP dazurechnen.
            editor.putInt("xp_value", currentXP); // Den aktualisierten XP-Wert speichern
            editor.apply(); //und anwenden.

        }else if (view.getId() == R.id.button60Min){
            countdown60();
            //Andere Countdown-Buttons werden deaktiviert und ausgegraut.
            startSechzig.setEnabled(false);
            startDreissig.setEnabled(false);
            startDreissig.setBackgroundTintList(ColorStateList.valueOf(0xFFCCCCCC));
            startHundertZwanzig.setEnabled(false);
            startHundertZwanzig.setBackgroundTintList(ColorStateList.valueOf(0xFFCCCCCC));

            //Die zugehörigen Symbole werden ausgegraut:
            peach.setColorFilter(0x99FFFFFF, PorterDuff.Mode.SRC_ATOP);
            cake.setColorFilter(0x99FFFFFF, PorterDuff.Mode.SRC_ATOP);

            //XP draufrechnen:
            int currentXP = sharedPreferences.getInt("xp_value", 0); // 0 ist der Standardwert, Momentane XP einlesen
            currentXP += 12; //3 XP dazurechnen.
            editor.putInt("xp_value", currentXP); // Den aktualisierten XP-Wert speichern
            editor.apply(); //und anwenden.

        } else if (view.getId() == R.id.button120Min){
            countdown120();
            //Andere Countdown-Buttons werden deaktiviert und ausgegraut.
            startHundertZwanzig.setEnabled(false);
            startDreissig.setEnabled(false);
            startDreissig.setBackgroundTintList(ColorStateList.valueOf(0xFFCCCCCC));
            startSechzig.setEnabled(false);
            startSechzig.setBackgroundTintList(ColorStateList.valueOf(0xFFCCCCCC));

            //Die zugehörigen Symbole werden ausgegraut:
            peach.setColorFilter(0x99FFFFFF, PorterDuff.Mode.SRC_ATOP);
            croissant.setColorFilter(0x99FFFFFF, PorterDuff.Mode.SRC_ATOP);

            //XP draufrechnen:
            int currentXP = sharedPreferences.getInt("xp_value", 0); // 0 ist der Standardwert, Momentane XP einlesen
            currentXP += 24; //3 XP dazurechnen.
            editor.putInt("xp_value", currentXP); // Den aktualisierten XP-Wert speichern
            editor.apply(); //und anwenden.

        }else if (view.getId() == R.id.returnButton){
            Toast.makeText(Food_Timer.this, "Der Timer wurde angehalten.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class); //Intent(wir sind in dieser Klasse, wollen zu MainActivity.class)
            startActivity(intent); //startActivity ist Methode aus Activity -> intent sagt wohin es geht
            this.finish(); //Ende
        }else{
            Toast.makeText(Food_Timer.this, "Bitte Zeit eingeben.", Toast.LENGTH_SHORT).show();
        }

    }
}