package com.example.chessp2p;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ImageButton;
import android.widget.Spinner;

public class Setting extends Activity{

    Spinner mySpinner;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_view);

        mySpinner = (Spinner) findViewById(R.id.spinner);
        back = (ImageButton) findViewById(R.id.settingBack);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(Setting.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.theme));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
