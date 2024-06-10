package com.example.herupachi;

import android.content.Intent;
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
import android.os.Handler;
import android.content.SharedPreferences;

public class Wash_Timer extends AppCompatActivity  implements View.OnClickListener {


    //_______________________________Variablen für Countdown_____________________________________
    TextView textViewCountdown, text; //Variablen für Countdown und Motivationstext
    ImageView image1_wash; //Variable für Image (Belohnung nach Beenden des 15 Minuten Countdowns)
    ImageView image2_wash; //Variable für Image (Belohnung nach Beenden des 30 Minuten Countdowns)
    ImageView image3_wash; //Variable für Image (Belohnung nach Beenden des 30 Minuten Countdowns)

    CountDownTimer countDownTimer; //Timer-Objekt
    long countdownProgressMinutes = 15; //Progress, welcher angezeigt wird für Minuten
    long countdownProgressSeconds = 00; //Progress, welcher angezeigt wird für Sekunden

    //_______________________________Variablen für Buttons:_________________________________________
    Button startFuenfzehn;
    Button startDreissig;
    Button startSechzig;
    Button startStopWatch;

    ImageView returnButton;

    //_______________________________Variablen für Stopwatch:_______________________________________
    private long starttime = 0;
    private boolean isStopwatchRunning = false;


    //Variablen für Pop-up bei Exit:
    //Dialog popUp;


    //Das passiert beim Öffnen dieses Views:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wash_timer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tab_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewCountdown = findViewById(R.id.countdown); //Verknüpfen von Countdown
        text = findViewById(R.id.motivationText); //Verknüpfen von Motivationstext

        //Verknüpfen von Images / Belohnungen
        image1_wash = findViewById(R.id.gift1);
        image1_wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starte die MainActivity, wenn auf das Bild geklickt wird
                Intent intent = new Intent(Wash_Timer.this, MainActivity.class);
                // Füge den Status des Bildes als Extra hinzu
                intent.putExtra("image1_wash_visibility", image1_wash.getVisibility());
                startActivity(intent);
            }
        }); //image1_wash wird auf die MainActivity übertragen, nachdem es geklickt wurde.

        image2_wash = findViewById(R.id.gift2);
        image3_wash = findViewById(R.id.gift3);

        //Verknüpfen von Start-Buttons je Einstellung
        startFuenfzehn = (Button) findViewById(R.id.button30Min);
        startFuenfzehn.setOnClickListener(this);

        startDreissig = (Button) findViewById(R.id.button60Min);
        startDreissig.setOnClickListener(this);


        returnButton = (ImageView) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(this);

        //Stopwatch:
        startStopWatch = (Button) findViewById(R.id.startStopWatch);

        startStopWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStopwatchRunning) {
                    startStopwatch();
                } else {
                    stopStopwatch();
                }
            }
        });


        //Verknüpfungen für PopUp:

        //popUp = new Dialog(this);
    }


    /** Stopwatch Methode bei betätigen des Start-Buttons
     * Startet eine Stoppuhr und ändert den Start-Button in einen Stop-Button
     */
    public void startStopwatch(){
        isStopwatchRunning = true;
        startStopWatch.setText("Stop");
        starttime = System.currentTimeMillis();

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isStopwatchRunning) {
                    long elapsedTime = System.currentTimeMillis() - starttime;
                    String formattedTime = formatTime(elapsedTime);
                    textViewCountdown.setText(formattedTime);
                    handler.postDelayed(this, 1000); // Update every second
                }
            }
        });
    }

    /** stopStopwatch
     * Beendet die Stopuhr und speichert die erreichte Zeit in Shared Preferences.
     * Je nach erreichter Zeit wird eine passende Belohnung angezeigt.
     * Countdown und Text verschwinden.
     */
    private void stopStopwatch() {
        isStopwatchRunning = false;
        startStopWatch.setText("Start");

        SharedPreferences sharedPreferences = getSharedPreferences("XP_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        long elapsedTime = System.currentTimeMillis() - starttime;
        long elapsedMinutes = elapsedTime / (1000 * 60); // Umrechnung von Millisekunden in Minuten

        if (elapsedMinutes < 15) {
            textViewCountdown.setText(":("); // Keine Belohnung für weniger als 15 Minuten
            text.setText("You'll make it next time.");
        } else if (elapsedMinutes >= 15 && elapsedMinutes < 30) {
            image1_wash.setVisibility(View.VISIBLE); // Zahnbürste wird für 15 Minuten angezeigt
            textViewCountdown.setVisibility(View.GONE); //Countdown verschwindet
            text.setVisibility(View.GONE);
            //XP draufrechnen:
            int currentXP = sharedPreferences.getInt("xp_value", 0); // 0 ist der Standardwert, Momentane XP einlesen
            currentXP += 3; //3 XP dazurechnen.
            editor.putInt("xp_value", currentXP); // Den aktualisierten XP-Wert speichern
            editor.apply(); //und anwenden.

        } else if (elapsedMinutes >= 30 && elapsedMinutes < 60) {
            image2_wash.setVisibility(View.VISIBLE); // Bürste wird für 30 Minuten angezeigt
            image1_wash.setVisibility(View.GONE);
            textViewCountdown.setVisibility(View.GONE); //Countdown verschwindet
            text.setVisibility(View.GONE);

            int currentXP = sharedPreferences.getInt("xp_value", 0); // 0 ist der Standardwert, Momentane XP einlesen
            currentXP += 6; //6 XP dazurechnen.
            editor.putInt("xp_value", currentXP); // Den aktualisierten XP-Wert speichern
            editor.apply(); //und anwenden.

        } else if (elapsedMinutes >= 60 && elapsedMinutes < 120) {
            image3_wash.setVisibility(View.VISIBLE); // Dusche wird für 60 Minuten angezeigt
            image1_wash.setVisibility(View.GONE);
            image2_wash.setVisibility(View.GONE);
            textViewCountdown.setVisibility(View.GONE); //Countdown verschwindet
            text.setVisibility(View.GONE);

            int currentXP = sharedPreferences.getInt("xp_value", 0); // 0 ist der Standardwert, Momentane XP einlesen
            currentXP += 12; //12 XP dazurechnen.
            editor.putInt("xp_value", currentXP); // Den aktualisierten XP-Wert speichern
            editor.apply(); //und anwenden.
        } else if (elapsedMinutes >= 120 && elapsedMinutes <= 180) {
            // Schaumbad wird für 120 Minuten angezeigt

            int currentXP = sharedPreferences.getInt("xp_value", 0); // 0 ist der Standardwert, Momentane XP einlesen
            currentXP += 24; //24 XP dazurechnen.
            editor.putInt("xp_value", currentXP); // Den aktualisierten XP-Wert speichern
            editor.apply(); //und anwenden.
        } else {
            // Spa wird für mehr als 180 Minuten angezeigt

            int currentXP = sharedPreferences.getInt("xp_value", 0); // 0 ist der Standardwert, Momentane XP einlesen
            currentXP += 36; //36 XP dazurechnen.
            editor.putInt("xp_value", currentXP); // Den aktualisierten XP-Wert speichern
            editor.apply(); //und anwenden.
        }

    }

    /** formatTime
     * Gibt einen String heraus, welcher die Zeit in Minuten und Sekunden anzeigt.
     * @param milliseconds als Millisekunden, welche formatiert werden sollen.
     * @return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds); als fromatierter Zeit-String
     */
    private String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }


    /** Allgemeine Countdown Methode (VERALTET! Neue Variante: siehe startStopwatch und stopStopwatch)
     *
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
                text.setVisibility(View.GONE); //Motivationstext verschwindet
                textViewCountdown.setVisibility(View.GONE); //Countdown verschwindet
            }
        }.start();

    }


    /** Countdown 15 Minuten (VERALTET -> jetzt Stopwatch)
     * Countdown, welcher bei 15 Minuten startet und pro Sekunde runterzählt.
     * Die Zeit wird im Format "Minuten:Sekunden" angezeigt.
     * Wenn der Timer abläuft erscheint eine Belohnung.
     */
    private void countdown15() { //Countdown für 15 Minuten
        textViewCountdown.setText("15"); //Text des Countdown wird auf 15 gesetzt
        countdown(900000,1000, image1_wash); //Methode wird für einen Start von 15 Minuten und mit Intervall einer Sekunde aufgerufen.
    }

    /** Countdown 30 Minuten (VERALTET -> jetzt Stopwatch)
     * Countdown, welcher bei 15 Minuten startet und pro Sekunde runterzählt.
     * Die Zeit wird im Format "Minuten:Sekunden" angezeigt.
     * Wenn der Timer abläuft erscheint eine Belohnung.
     */
    private void countdown30() { //Countdown für 30 Minuten
        textViewCountdown.setText("30"); //Text des Countdown wird auf 30 gesetzt
        countdown(60000 * 30,1000, image2_wash); //Methode wird für einen Start von 30 Minuten und mit Intervall einer Sekunde aufgerufen.
    }

    /** Countdown 60 Minuten (VERALTET -> jetzt Stopwatch)
     * Countdown, welcher bei 15 Minuten startet und pro Sekunde runterzählt.
     * Die Zeit wird im Format "Minuten:Sekunden" angezeigt.
     * Wenn der Timer abläuft erscheint eine Belohnung.
     */
    private void countdown60() { //Countdown für 60 Minuten
        textViewCountdown.setText("60"); //Text des Countdown wird auf 60 gesetzt
        countdown(60000 * 60,1000, image3_wash); //Methode wird für einen Start von 15 Minuten und mit Intervall einer Sekunde aufgerufen.
    }

    /** onClick (VERALTET)
     * Countdowns werden auf Klick der zugehörigen Buttons gestartet.
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button30Min) {
            countdown15(); //Wenn 15 Min Button betätigt wird, startet der 15 Min Timer
            //Andere Countdown-Buttons werden deaktiviert.
            startDreissig.setEnabled(false);
            startSechzig.setEnabled(false);
        }else if (view.getId() == R.id.button60Min){
            countdown30();
            startFuenfzehn.setEnabled(false);
            startSechzig.setEnabled(false);
        } else if (view.getId() == R.id.button120Min){
            countdown60();
            startFuenfzehn.setEnabled(false);
            startDreissig.setEnabled(false);
        }else if (view.getId() == R.id.returnButton){
            Toast.makeText(Wash_Timer.this, "Der Timer wurde angehalten.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class); //Intent(wir sind in dieser Klasse, wollen zu MainActivity.class)
            startActivity(intent); //startActivity ist Methode aus Activity -> intent sagt wohin es geht
            this.finish(); //Ende
        }else{
            Toast.makeText(Wash_Timer.this, "Bitte Zeit eingeben.", Toast.LENGTH_SHORT).show();
        }
    }
}