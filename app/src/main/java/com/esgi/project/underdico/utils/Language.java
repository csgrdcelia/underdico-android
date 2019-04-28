package com.esgi.project.underdico.utils;

import android.content.Context;

import java.util.Locale;

public class Language {

    public static final String FR = "fr";
    public static final String EN = "en";

    private Locale newLocale;
    private Context context;

    public Language(Context context) {
        this.context = context;
    }

    public void set(String code) {
        if(!code.isEmpty()) {
            if(code == "fr") {
                reset();
            } else {
                newLocale = new Locale(code);
                Locale.setDefault(newLocale);
                android.content.res.Configuration config = new android.content.res.Configuration();
                config.setLocale(newLocale);
                context.getResources().updateConfiguration(config,
                        context.getResources().getDisplayMetrics());
            }
        }
    }

    public void reset() {
        Locale.setDefault(Locale.getDefault());
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(Locale.getDefault());
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

}
