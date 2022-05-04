package com.example.chessp2p;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;

public class Profile extends Activity {

    ImageButton back;
    ImageView board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        back = (ImageButton) findViewById(R.id.profileBack);
        board= (ImageView) findViewById(R.id.boardImage);

        board.setImageResource(getCurrentId());

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private int getCurrentId(){
        int id=0;
        switch (MainActivity.setting.getCustomTheme()){
            case "Classic 1":
                id = getResources().getIdentifier("classic_1" , "drawable", getPackageName());
                break;
            case "Classic 2":
                id= getResources().getIdentifier("classic_2" , "drawable", getPackageName());
                break;
            case "Advance 1":
                id= getResources().getIdentifier("advance_1" , "drawable", getPackageName());
                break;
            case "Advance 2":
                id= getResources().getIdentifier("advance_2" , "drawable", getPackageName());
                break;
        }
        return id;
    }
}
