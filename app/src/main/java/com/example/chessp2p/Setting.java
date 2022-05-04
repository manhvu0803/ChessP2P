package com.example.chessp2p;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


public class Setting extends Activity{

    Spinner mySpinner;
    ImageButton back;
    Switch backgroundSwitch, effectSwitch;
    TextView backgroundStatus, effectStatus;
    ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_view);

        mySpinner = (Spinner) findViewById(R.id.spinner);
        back = (ImageButton) findViewById(R.id.settingBack);


        backgroundStatus = (TextView) findViewById(R.id.music_status);
        effectStatus = (TextView) findViewById(R.id.music_status2);

        backgroundSwitch = (Switch) findViewById(R.id.music);
        effectSwitch = (Switch) findViewById(R.id.music2);

        if(MainActivity.setting.getBackgroundMusic().equals("on")){
            backgroundSwitch.setChecked(true);
            backgroundStatus.setText("On");
        }
        else{
            backgroundSwitch.setChecked(false);
            backgroundStatus.setText("Off");
        }

        if(MainActivity.setting.getSoundEffect().equals("on")){
            effectSwitch.setChecked(true);
            effectStatus.setText("On");
        }
        else{
            effectSwitch.setChecked(false);
            effectStatus.setText("Off");
        }

        backgroundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    MainActivity.setting.setBackgroundMusic("on");
                    backgroundSwitch.setChecked(true);
                    backgroundStatus.setText("On");
                }
                else{
                    MainActivity.setting.setBackgroundMusic("off");
                    backgroundSwitch.setChecked(false);
                    backgroundStatus.setText("Off");
                }
                SharedPreferences.Editor editor = getSharedPreferences(UserSetting.PREFERENCES,MODE_PRIVATE).edit();
                editor.putString(UserSetting.BACKGROUNDSTATUS,MainActivity.setting.getBackgroundMusic());
                editor.apply();
            }
        });

        effectSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    MainActivity.setting.setSoundEffect("on");
                    effectSwitch.setChecked(true);
                    effectStatus.setText("On");
                }
                else{
                    MainActivity.setting.setSoundEffect("off");
                    effectSwitch.setChecked(false);
                    effectStatus.setText("Off");

                }
                SharedPreferences.Editor editor = getSharedPreferences(UserSetting.PREFERENCES,MODE_PRIVATE).edit();
                editor.putString(UserSetting.EFFECTSTATUS,MainActivity.setting.getSoundEffect());
                editor.apply();
            }
        });

        myAdapter = new ArrayAdapter<>(Setting.this,
                R.layout.spinner_item,getResources().getStringArray(R.array.theme));
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
}
