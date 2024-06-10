package com.example.herupachi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Start extends AppCompatActivity implements View.OnClickListener {
    Button startGameButton;
    Button tutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tab_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startGameButton = (Button) findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(this);

        tutorial = (Button) findViewById(R.id.tutorial);
        tutorial.setOnClickListener(this);

        ImageView startGIF = findViewById(R.id.startGIF);
        //Glide.with(this).asGif().load(R.drawable.start_hintergrund).into(startGIF);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tutorial) {
            Intent intent = new Intent(this, Tutorial.class); //Intent(wir sind in dieser Klasse, wollen zu MainActivity.class)
            startActivity(intent); //startActivity ist Methode aus Activity -> intent sagt wohin es geht
            this.finish(); //Ende
        } else {
            Intent intent = new Intent(this, MainActivity.class); //Intent(wir sind in dieser Klasse, wollen zu MainActivity.class)
            startActivity(intent); //startActivity ist Methode aus Activity -> intent sagt wohin es geht
            this.finish(); //Ende


        }
    }
}