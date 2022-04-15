package com.example.chessp2p;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;

public class Profile extends Activity {

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        back = (ImageButton) findViewById(R.id.profileBack);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
