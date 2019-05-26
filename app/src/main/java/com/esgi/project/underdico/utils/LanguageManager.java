package com.esgi.project.underdico.utils;

import android.content.Context;

import java.util.Locale;

public class LanguageManager {

    public static final String FR = "fr";
    public static final String EN = "en";

    private Locale newLocale;
    private Context context;

    public LanguageManager(Context context) {
        this.context = context;
    }

    public boolean set(String code) {
        if(!code.isEmpty()) {
            if(!Locale.getDefault().getLanguage().equals(code)) {
                    newLocale = new Locale(code);
                    Locale.setDefault(newLocale);
                    android.content.res.Configuration config = new android.content.res.Configuration();
                    config.setLocale(newLocale);
                    context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                return true;
            }
        }
        return false;
    }

}
