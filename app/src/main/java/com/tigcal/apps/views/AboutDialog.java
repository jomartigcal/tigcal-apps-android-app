package com.tigcal.apps.views;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.tigcal.apps.R;

public class AboutDialog extends Dialog {
    private static final String TAG = AboutDialog.class.getSimpleName();

    public AboutDialog(Context context) {
        super(context);
        setTitle(context.getString(R.string.about_header));
        setContentView(R.layout.dialog_about);
        setCancelable(true);

        final StringBuilder appVersionBuilder = new StringBuilder();
        appVersionBuilder.append("\n");
        appVersionBuilder.append(context.getString(R.string.app_name));
        try {
            appVersionBuilder.append(" ")
                    .append(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "package name not found");
        }
        appVersionBuilder.append("\n");

        final TextView appVersion = findViewById(R.id.app_version);
        appVersion.setText(appVersionBuilder.toString());

        MovementMethod linkMovementMethod = LinkMovementMethod.getInstance();
        final TextView appDevelopers = findViewById(R.id.about_developer);
        appDevelopers.setMovementMethod(linkMovementMethod);
        final TextView contactTextView = findViewById(R.id.about_contact_info);
        contactTextView.setMovementMethod(linkMovementMethod);
    }

}
