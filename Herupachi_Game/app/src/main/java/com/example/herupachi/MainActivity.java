package com.example.herupachi;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;
import android.content.SharedPreferences;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Button-Variablen:
    ImageView buttonFood;
    ImageView buttonWash;
    ImageView buttonShop;
    ImageView buttonSleep;

    //_________________________________________________________________________

    //Progress-Bar Variablen:
    ProgressBar hunger;
    ProgressBar wash;
    ProgressBar xp;
    TextView anzeige_XP;

    //_________________________________________________________________________

    //Zustände-Variablen:
    ImageView normal;
    ImageView happy;
    ImageView sad;
    ImageView sleepy;

    //_________________________________________________________________________

    //Hintergründe

    GifImageView sad_BG;

    //---------------------------------------------------------------------------

    //Sprechblasen für Zustände
    ImageView happyHungerBubble;
    ImageView sadHungerBubble;
    ImageView happyWashBubble;
    ImageView sadWashBubble;

    //------------------------------------------------------------------------------

    //Der Herupatchi-Dreck? xD
    ImageView dirt;

    //-------------------------------------------------------------------------------

    //Essens- und Waschgeschenke
    ImageView foodGift1;
    ImageView foodGirft2;
    ImageView foodGift3;

    ImageView washGift1;
    ImageView washGift2;
    ImageView washGift3;
    ImageView washGift4;
    ImageView washGift5;

    //---------------------------------------------------------------------------------
    int dX;
    int dY;



    //Das passiert beim Öffnen dieses Views:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tab_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        //Verknüpfen der Variablen mit ihren Activity-Elementen über ID:
        normal = (ImageView) findViewById(R.id.Herupachi_Normal);
        happy = (ImageView) findViewById(R.id.Herupachi_Happy);
        sad = (ImageView) findViewById(R.id.Herupachi_Sad);
        sleepy = (ImageView) findViewById(R.id.Herupachi_Sleep);

        happyHungerBubble = (ImageView) findViewById(R.id.happyBubbleHunger);
        happyWashBubble = (ImageView) findViewById(R.id.happyBubbleClean);
        sadHungerBubble = (ImageView) findViewById(R.id.sadBubbleHungry);
        sadWashBubble = (ImageView) findViewById(R.id.sadBubbleDirty);

        dirt = (ImageView) findViewById(R.id.Dirt);

        buttonFood = (ImageView) findViewById(R.id.buttonFood);
        buttonFood.setOnClickListener(this);

        buttonWash = (ImageView) findViewById(R.id.buttonWash);
        buttonWash.setOnClickListener(this);

        buttonSleep = (ImageView) findViewById(R.id.buttonSleep);
        buttonSleep.setOnClickListener(this);

        buttonShop = (ImageView) findViewById(R.id.XP_Icon);
        buttonShop.setOnClickListener(this);

        foodGift1 = (ImageView) findViewById(R.id.food_gift1);


        hunger = findViewById(R.id.progress_Hunger);
        wash = findViewById(R.id.progress_Hygiene);
        //countdownHunger(); -> Habs ausgeklammert weil ich der Countdown ja jetzt für beides gilt
        startFoodCountdown();
        startWashCountdown();

        //Hintergründe:
        sad_BG = (GifImageView) findViewById(R.id.sad_BG);

        //_______________________________XP-Leiste und Anzeige:_____________________________________
        int experience = 0; //Startwert der XP bei 0.
        anzeige_XP = findViewById(R.id.anzeige_XP);
        xp = findViewById(R.id.progress_XP);
        xpProgress(experience); //Aufruf der Methode mit experience als Parameter

        SharedPreferences sharedPreferences = getSharedPreferences("XP_PREFERENCES", MODE_PRIVATE);
        int xpValue = sharedPreferences.getInt("xp_value", 0); // 0 ist der Standardwert, falls kein Wert gefunden wird

        // Aktualisieren der XP-Anzeige
        xpProgress(xpValue); // Annahme, dass xpProgress die XP-Anzeige aktualisiert



        //_______________________________Food-Belohnungen:__________________________________________

        //Anzeige von Food-Belohnung nach 30 Minuten:
        int image1_food_Visibility = getIntent().getIntExtra("image1_food_visibility", View.GONE); // Erhalte den Status des Bildes aus den Intent-Extras

        ImageView image1_food = findViewById(R.id.food_gift1);
        image1_food.setVisibility(image1_food_Visibility);

        image1_food.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                image1_food.setVisibility(View.GONE);
                //addToHunger(15); //Wert wie viel Huger-Leiste aufgefüllt wird;
                addToProgress("HUNGER_PREFERENCES", "hunger_value", hunger, 15);
            }
        });

        //Anzeige von Food-Belohnung nach 60 Minuten:
        int image2_food_Visibility = getIntent().getIntExtra("image2_food_visibility", View.GONE); // Erhalte den Status des Bildes aus den Intent-Extras

        ImageView image2_food = findViewById(R.id.food_gift2);
        image2_food.setVisibility(image2_food_Visibility);

        image2_food.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                image2_food.setVisibility(View.GONE);
                //addToHunger(30); //Wert wie viel Huger-Leiste aufgefüllt wird
                addToProgress("HUNGER_PREFERENCES", "hunger_value", hunger, 30);
            }
        });

        //Anzeige von Food-Belohnung nach 120 Minuten:
        int image3_food_Visibility = getIntent().getIntExtra("image3_food_visibility", View.GONE); // Erhalte den Status des Bildes aus den Intent-Extras

        ImageView image3_food = findViewById(R.id.food_gift3);
        image3_food.setVisibility(image3_food_Visibility);

        image3_food.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               image3_food.setVisibility(View.GONE);
               //addToHunger(60); //Wert wie viel Huger-Leiste aufgefüllt wird
               addToProgress("HUNGER_PREFERENCES", "hunger_value", hunger, 60);
           }
        });



        //_______________________________Wash-Belohnungen:__________________________________________


        // Anzeige von Wash-Belohnung nach 15 Minuten:
        int image1_wash_Visibility = getIntent().getIntExtra("image1_wash_visibility", View.GONE);

        ImageView image1_wash = findViewById(R.id.wash_gift1);
        image3_food.setVisibility(image1_wash_Visibility);

        image1_wash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                image1_wash.setVisibility(View.GONE);
                addToProgress("WASH_PREFERENCES", "wash_value", wash, 15);
            }
        });



        //Anzeige von Wash-Belohnung  nach 30 Minuten:
        int image2_wash_Visibility = getIntent().getIntExtra("image2_wash_visibility", View.GONE);

        ImageView image2_wash = findViewById(R.id.wash_gift2);
        image2_food.setVisibility(image2_wash_Visibility);

        image2_wash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                image2_wash.setVisibility(View.GONE);
                addToProgress("WASH_PREFERENCES", "wash_value", wash, 30);
            }
        });


        //Anzeige von Wash-Belohnung  nach 60 Minuten:
        int image3_wash_Visibility = getIntent().getIntExtra("image3_wash_visibility", View.GONE);

        ImageView image3_wash = findViewById(R.id.wash_gift3);
        image3_food.setVisibility(image1_wash_Visibility);

        image3_wash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                image3_wash.setVisibility(View.GONE);
                addToProgress("WASH_PREFERENCES", "wash_value", wash, 60);
            }
        });


        //Anzeige von Wash-Belohnung  nach 120 Minuten:
        int image4_wash_Visibility = getIntent().getIntExtra("image4_wash_visibility", View.GONE);

        ImageView image4_wash = findViewById(R.id.wash_gift4);
        image4_wash.setVisibility(image4_wash_Visibility);

        image1_wash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                image4_wash.setVisibility(View.GONE);
                addToProgress("WASH_PREFERENCES", "wash_value", wash, 90);
            }
        });

        //Anzeige von Wash-Belohnung  nach 180 Minuten:
        int image5_wash_Visibility = getIntent().getIntExtra("image5_wash_visibility", View.GONE);

        ImageView image5_wash = findViewById(R.id.wash_gift5);
        image5_wash.setVisibility(image5_wash_Visibility);

        image5_wash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                image5_wash.setVisibility(View.GONE);
                addToProgress("WASH_PREFERENCES", "wash_value", wash, 120);
            }
        });

        /*foodGift1.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
            @Override
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.animate()
                                .x(event.getRawX() + dX)
                                .y()
                }

            }*/


        }

    /** Hunger-Leistenwert wird verringert.
     * Dies geschieht mithilfe eines Countdowns.
     * Pro Intervall wird die Hungerleiste um -1% verringert.
     * Je nach Wert der Leiste wird zudem der passende Herupachi-Zustand gezeigt.
     */
    /* public void countdownHunger(){
        SharedPreferences sharedPreferencesHunger = getSharedPreferences("HUNGER_PREFERENCES", MODE_PRIVATE);
        int currentHunger = sharedPreferencesHunger.getInt("hunger_value",100); //Standardwert: 100, falls kein Wert gefunden wird
        hunger.setProgress(currentHunger); // Zeigt aktuellen Wert auf Progressbar

        CountDownTimer countDownTimer = new CountDownTimer((60000 * 1440)*2,60000) { //48 Stunden als Startzeit, eine Minute als Intervall
            SharedPreferences.Editor editor = sharedPreferencesHunger.edit();

            @Override
            public void onTick(long millisUntilFinished) { //Bei Intervall:
                int currentProgress = hunger.getProgress(); //Progress wird ausgelesen
                int newProgress = Math.max(0, currentProgress - 1); //Neuer Progress soll um einen verringert werden aber nicht unter null gehen.
                hunger.setProgress(newProgress); //Neuer Progress wird auf der Progressbar Hunger gezeigt.

                updateMood(); //Aussehen von Herupachi wird angepasst.

                //Hunger-Wert in SharedPreferences aktualisieren:
                editor.putInt("hunger_value", hunger.getProgress()); // Den aktualisierten Hunger-Wert speichern
                editor.apply(); //und anwenden.
            }



            @Override
            public void onFinish() { //Wenn Countdown fertig / bei 0:
                if (hunger.getProgress() > 1) //Falls der Hunger über 1% ist:
                    countdownHunger(); //Countdown beginnt von vorne. Rekursiver Aufruf!
            }
        }.start();

    }*/


    public void countdown(String preferencesName, String key, ProgressBar progressBar, Runnable updateCallback) {
        SharedPreferences sharedPreferences = getSharedPreferences(preferencesName, MODE_PRIVATE);
        int currentValue = sharedPreferences.getInt(key, 100); //Standardwert: 100, falls kein Wert gefunden wirdprogressBar.setProgress(currentValue); // Zeigt aktuellen Wert auf Progressbar

        CountDownTimer countDownTimer = new CountDownTimer(6000, 1000) { // 48 Stunden als Startzeit, eine Minute als Intervall 60000 * 1440) * 2, 60000
        SharedPreferences.Editor editor = sharedPreferences.edit();

        @Override
        public void onTick(long millisUntilFinished) { // Bei Intervall:
            int currentProgress = progressBar.getProgress(); // Progress wird ausgelesen
            int newProgress = Math.max(0, currentProgress - 1); // Neuer Progress soll um einen verringert werden, aber nicht unter null gehen.
            progressBar.setProgress(newProgress); // Neuer Progress wird auf der Progressbar gezeigt.

            updateCallback.run(); // Callback-Methode für zusätzliche Aktionen (z.B. Aussehen anpassen)

            // Wert in SharedPreferences aktualisieren:
            editor.putInt(key, progressBar.getProgress()); // Den aktualisierten Wert speichern
            editor.apply(); // und anwenden.
        }

        @Override
        public void onFinish() { // Wenn Countdown fertig / bei 0:
            if (progressBar.getProgress() > 1) // Falls der Wert über 1% ist:
                countdown(preferencesName, key, progressBar, updateCallback); // Countdown beginnt von vorne. Rekursiver Aufruf!
        }
    }.start();
}

public void startFoodCountdown() {
    countdown("HUNGER_PREFERENCES", "hunger_value", hunger, this::updateMood);
}

public void startWashCountdown() {
    countdown("WASH_PREFERENCES", "wash_value", wash, this::updateAppearance);
}


    /** addToHunger
     * Rechnet einen Wert auf die Hunger-Leiste drauf.
     * @param food_value als Wert, welcher hinzugerechnet wird.
     */
    /*public void addToHunger(int food_value){
        SharedPreferences sharedPreferencesHunger = getSharedPreferences("HUNGER_PREFERENCES", MODE_PRIVATE);
        int currentHunger = sharedPreferencesHunger.getInt("hunger_value", 100); //Standardwert: 100, falls kein Wert gefunden wird

        // Neuer Hungerwert wird berechnet: food_value wird addiert, aber Wert geht nicht über 100
        int newHunger = Math.min(100, currentHunger + food_value); // Math.min() gibt den kleineren der beiden Werte zurück

        // Aktualisiere den Hungerwert in den SharedPreferences
        SharedPreferences.Editor editor = sharedPreferencesHunger.edit();
        editor.putInt("hunger_value", newHunger);
        editor.apply();

        hunger.setProgress(newHunger);
        updateMood();
    }*/

    /**
     * Rechnet einen Wert auf eine Progress-Leiste drauf.
     * @param preferencesName Der Name der SharedPreferences-Datei.
     * @param key Der Schlüssel, unter dem der aktuelle Wert gespeichert ist.
     * @param progressBar Die ProgressBar, die aktualisiert werden soll.
     * @param incrementValue Der Wert, welcher hinzugerechnet wird.
     */

    public void addToProgress(String preferencesName, String key, ProgressBar progressBar, int incrementValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(preferencesName, MODE_PRIVATE);
        int currentValue = sharedPreferences.getInt(key, 100); // Standardwert: 100, falls kein Wert gefunden wird

        // Neuer Wert wird berechnet: incrementValue wird addiert, aber Wert geht nicht über 100
        int newValue = Math.min(100, currentValue + incrementValue); // Math.min() gibt den kleineren der beiden Werte zurück

        // Aktualisiere den Wert in den SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, newValue);
        editor.apply();

        // Aktualisiere die ProgressBar
        progressBar.setProgress(newValue);
        updateMood();
        updateAppearance();
    }

    //Hier Countdown Hygiene c:
    //Die Zustände, welche je nach Progress aus- und eingeblendet werden sollen heißen: Clean und Dirt

    //Hier addToHygiene

    /** xpProgress
     * Setzt den Progress der XP-Leiste und der XP-Zahl.
     * @param newExperience als Wert für die Anzeige.
     */
    public void xpProgress(int newExperience){
        xp.setIndeterminate(false); //Anzeige soll nicht animiert sein.
        xp.setProgress(newExperience); //Progress wird auf Leiste gesetzt.
        String formatXP = String.format(Locale.getDefault(), "%02d",newExperience);
        anzeige_XP.setText(formatXP); //Progress wird als Zahl angezeigt.
    }

    /** updateMood
     * Die Hungerleiste wird auf ihren Wert geprüft. Je nach Wert, wird die passende Herupachi-Emotion
     * eingeblendet und die Anderen verschwinden.
     */
    public void updateMood(){
        int moodProgress = hunger.getProgress();

        if (moodProgress >= 75){  //Wenn die Hunger-Leiste mehr als 75 % gefüllt ist:
            happyHungerBubble.setVisibility(View.VISIBLE); //Sagt: "Yay ich bin Happy"
            sadHungerBubble.setVisibility(View.GONE);

            happy.setVisibility(View.VISIBLE); //Happy Zustand von Herupachi erscheint.
            normal.setVisibility(View.GONE); //Normaler Zustand von Herupachi wird ausgeblendet.
            sad.setVisibility(View.GONE); //Sad Zustand von Herupachi wird ausgeblendet.

            sad_BG.setVisibility(View.GONE); //Der traurige Hintergrund wird ausgeblendet.

        }else if(moodProgress <= 35){ //Wenn die Hunger-Leiste niedriger als 35 % gefüllt ist:
            sadHungerBubble.setVisibility(View.VISIBLE); //Sagt: "ICH HABE HUNGER"
            happyHungerBubble.setVisibility(View.GONE);

            sad.setVisibility(View.VISIBLE); //Sad Zustand von Herupachi erscheint.
            happy.setVisibility(View.GONE); //Normaler Zustand von Herupachi wird ausgeblendet.
            normal.setVisibility(View.GONE); //Happy Zustand von Herupachi wird ausgeblendet.

            sad_BG.setVisibility(View.VISIBLE); //Der traurige Hintergrund wird eingeblendet.

        }else{ //Wenn sich Leiste in einem Wert zwischen den anderen Optionen befindet:
            happyHungerBubble.setVisibility(View.GONE);
            sadHungerBubble.setVisibility(View.GONE);

            normal.setVisibility(View.VISIBLE); //Normaler Zustand von Herupachi wird eingeblendet.
            sad.setVisibility(View.GONE); //Sad Zustand von Herupachi wird ausgeblendet.
            happy.setVisibility(View.GONE); //Happy Zustand von Herupachi wird ausgeblendet.

            sad_BG.setVisibility(View.GONE); //Der traurige Hintergrund wird unsichtbar.

        }
    }

    //Dirt muss noch eingefügt werden
    public void updateAppearance(){
        int washProgress = wash.getProgress();
        if (washProgress >= 75) { //(wash.getProgress() >= 75)
            happyWashBubble.setVisibility(View.VISIBLE); //Sagt: Ich fühle mich so wohl!
            sadWashBubble.setVisibility(View.GONE);

            dirt.setVisibility(View.GONE);

            happy.setVisibility(View.VISIBLE); //Happy Zustand von Herupachi erscheint.
            normal.setVisibility(View.GONE); //Normaler Zustand von Herupachi wird ausgeblendet.
            sad.setVisibility(View.GONE); //Sad Zustand von Herupachi wird ausgeblendet.

            sad_BG.setVisibility(View.GONE); //Der traurige Hintergrund wird ausgeblendet.

        } else if (washProgress <= 35) {

            sadWashBubble.setVisibility(View.VISIBLE); //Sagt: "Ich stinkeeee hilfeeee"
            happyWashBubble.setVisibility(View.GONE);

            dirt.setVisibility(View.VISIBLE);

            sad.setVisibility(View.VISIBLE); //Sad Zustand von Herupachi erscheint.
            happy.setVisibility(View.GONE); //Normaler Zustand von Herupachi wird ausgeblendet.
            normal.setVisibility(View.GONE); //Happy Zustand von Herupachi wird ausgeblendet.

            sad_BG.setVisibility(View.VISIBLE); //Der traurige Hintergrund wird eingeblendet.

        }else{ //Wenn sich Leiste in einem Wert zwischen den anderen Optionen befindet:
            happyWashBubble.setVisibility(View.GONE);
            sadWashBubble.setVisibility(View.GONE);

            dirt.setVisibility(View.GONE);

            normal.setVisibility(View.VISIBLE); //Normaler Zustand von Herupachi wird eingeblendet.
            happy.setVisibility(View.GONE); //Happy Zustand von Herupachi wird ausgeblendet.
            sad.setVisibility(View.GONE); //Sad Zustand von Herupachi wird ausgeblendet.

            sad_BG.setVisibility(View.GONE); //Der traurige Hintergrund wird unsichtbar.

        }
    }

    /** onClick
     * Wenn auf Button geklickt wird:
     * buttonFood: Es wird zum Food_Timer View gewechselt.
     * buttonWash: Es wird zum Wash-Timer gewechselt.
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonFood) {
            Intent intent = new Intent(this, Food_Timer.class); //Intent(wir sind in dieser Klasse, wollen zu Spiel.class)
            startActivity(intent); //startActivity ist Methode aus Activity -> intent sagt wohin es geht
            this.finish(); //Ende
        } else if (view.getId() == R.id.buttonWash) {
            Intent intent = new Intent(this, Wash_Timer.class); //Intent(wir sind in dieser Klasse, wollen zu Spiel.class)
            startActivity(intent); //startActivity ist Methode aus Activity -> intent sagt wohin es geht
            this.finish(); //Ende
        } else if (view.getId() == R.id.buttonSleep) {
            Intent intent = new Intent(this, WinterSleep.class);
            startActivity(intent);
            this.finish();
        } else if (view.getId() == R.id.XP_Icon) {
            Intent intent = new Intent(this, Shop.class);
            startActivity(intent);
            this.finish();
        }
    }



}