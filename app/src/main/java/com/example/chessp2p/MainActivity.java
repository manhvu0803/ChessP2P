package com.example.chessp2p;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    ImageButton profile;
    ImageButton rules;
    ImageButton achievement;

    Button playBtn;
    Button settingBtn;
    Button puzzleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn =(Button) findViewById(R.id.play);
        settingBtn =(Button) findViewById(R.id.setting);
        puzzleBtn =(Button) findViewById(R.id.puzzle);

        profile =(ImageButton) findViewById(R.id.profile);
        rules =(ImageButton) findViewById(R.id.history);
        achievement =(ImageButton) findViewById(R.id.achievement);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent Game = new Intent(MainActivity.this, com.example.chessp2p.MainGame.class);
                startActivity(Game);
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent Setting = new Intent(MainActivity.this, com.example.chessp2p.Setting.class);
                startActivity(Setting);
            }
        });

        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent Profile = new Intent(MainActivity.this, com.example.chessp2p.Profile.class);
                startActivity(Profile);
            }
        });

    }
}