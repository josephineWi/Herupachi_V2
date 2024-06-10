package com.example.herupachi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WinterSleep extends AppCompatActivity implements View.OnClickListener{

    //Button-Variablen:
    ImageView goBack;

    //Zustand-Variable:
    ImageView sleepy;

    //Hintergrund-Variable:
    ImageView sleep_BG;

    //Sprechblase-Variable:
    ImageView sleepBubble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_winter_sleep);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Verknüpfen der Variablen mit ihren Activity-Elementen über ID:

        sleepy = (ImageView) findViewById(R.id.Herupachi_Sleep);

        goBack = (ImageView) findViewById(R.id.returnButton);
        goBack.setOnClickListener(this);

        sleepBubble = (ImageView) findViewById(R.id.sleepBubble);

        sleep_BG = (ImageView) findViewById(R.id.wintersleep_BG);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.returnButton) {
            Intent intent = new Intent(this, MainActivity.class); //Intent(wir sind in dieser Klasse, wollen zu Spiel.class)
            startActivity(intent); //startActivity ist Methode aus Activity -> intent sagt wohin es geht
            this.finish(); //Ende
        }
    }

}