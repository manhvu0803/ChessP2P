package com.example.chessp2p;

import android.app.Application;

public class UserSetting extends Application {

    public static final String PREFERENCES = "preferences";

    public static final String CUSTOM_THEME = "customTheme";
    public static final String BACKGROUNDSTATUS = "backgroundStatus";
    public static final String EFFECTSTATUS = "effectStatus";

    public static final String THEME_1 = "Classic 1";
    public static final String THEME_2 = "Classic 2";
    public static final String THEME_3 = "Advance 1";
    public static final String THEME_4 = "Advance 2";


    private String customTheme;
    private String backgroundMusic;
    private String soundEffect;

    public String getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(String customTheme) {
        this.customTheme = customTheme;
    }

    public String getBackgroundMusic() {
        return backgroundMusic;
    }

    public void setBackgroundMusic(String backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    public String getSoundEffect() {
        return soundEffect;
    }

    public void setSoundEffect(String soundEffect) {
        this.soundEffect = soundEffect;
    }
}

