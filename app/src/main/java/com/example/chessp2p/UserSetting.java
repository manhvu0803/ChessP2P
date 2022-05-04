package com.example.chessp2p;

import android.app.Application;

public class UserSetting extends Application {

    public static final String PREFERENCES = "preferences";

    public static final String CUSTOM_THEME = "customTheme";

    public static final String THEME_1 = "Classic 1";
    public static final String THEME_2 = "Classic 2";
    public static final String THEME_3 = "Advance 1";
    public static final String THEME_4 = "Advance 2";

    private String customTheme;

    public String getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(String customTheme) {
        this.customTheme = customTheme;
    }
}

