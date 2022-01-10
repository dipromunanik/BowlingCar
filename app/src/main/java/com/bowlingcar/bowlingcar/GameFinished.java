package com.bowlingcar.bowlingcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameFinished extends AppCompatActivity {
    private Button playagain;
    private TextView display_score;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);

        score = getIntent().getExtras().get("Score").toString();

        playagain = findViewById(R.id.play_again);
        display_score = findViewById(R.id.display_score);

        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GameFinished.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        display_score.setText("Score: " +score);
    }
}