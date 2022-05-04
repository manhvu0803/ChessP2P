package com.example.chessp2p;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;

public class Setting extends Activity{

    Spinner mySpinner;
    ImageButton back;
    ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_view);

        mySpinner = (Spinner) findViewById(R.id.spinner);
        back = (ImageButton) findViewById(R.id.settingBack);

        myAdapter = new ArrayAdapter<>(Setting.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.theme));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setSelection(myAdapter.getPosition(MainActivity.setting.getCustomTheme()));

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                MainActivity.setting.setCustomTheme(parent.getSelectedItem().toString());
                SharedPreferences.Editor editor = getSharedPreferences(UserSetting.PREFERENCES,MODE_PRIVATE).edit();
                editor.putString(UserSetting.CUSTOM_THEME,MainActivity.setting.getCustomTheme());
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });
    }

//    private void updateSpinner(){
//    }
}
