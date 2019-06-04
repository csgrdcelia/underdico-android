package com.esgi.project.underdico.utils;

import android.util.Pair;

import com.esgi.project.underdico.R;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final int HTTP_CONFLICT = 409;
    public static final int HTTP_UNPROCESSABLE = 422;
    public static final int HTTP_UNAUTHORIZED = 401;

    public static final String NETWORK_ERROR = "network error";

    public static final List<Pair<String, Integer>> flags = new ArrayList<Pair<String, Integer>>() {
        {
            add(new Pair<>("fr", R.drawable.france_flag));
            add(new Pair<>("en", R.drawable.england_flag));
        }
    };


}
