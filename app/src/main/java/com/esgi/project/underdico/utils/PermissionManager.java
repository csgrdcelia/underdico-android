package com.esgi.project.underdico.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
