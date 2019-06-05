package com.esgi.project.underdico.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionManager {

    Context context;
    Activity activity;

    public PermissionManager(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }
    public boolean isPermittedTo(String permission) {
        int result = ContextCompat.checkSelfPermission(context,
                permission);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void askPermissionTo(String permission, int requestCode) {
        ActivityCompat.requestPermissions(activity,
                new String[]{permission},
                requestCode);
    }
}
