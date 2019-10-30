package com.tigcal.apps.views;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tigcal.apps.R;

public class AboutDialog extends DialogFragment {
    private static final String DIALOG_TAG = AboutDialog.class.getSimpleName();

    public AboutDialog() {
        //Empty Constructor
    }

    public static AboutDialog newInstance() {
        return new AboutDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_about, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();
        getDialog().setTitle(context.getString(R.string.about_header));

        final StringBuilder appVersionBuilder = new StringBuilder();
        appVersionBuilder.append("\n");
        appVersionBuilder.append(context.getString(R.string.app_name));
        try {
            appVersionBuilder.append(" ")
                    .append(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(DIALOG_TAG, "package name not found");
        }
        appVersionBuilder.append("\n");

        final TextView appVersion = view.findViewById(R.id.version_text);
        appVersion.setText(appVersionBuilder.toString());

        MovementMethod linkMovementMethod = LinkMovementMethod.getInstance();
        final TextView descriptionText = view.findViewById(R.id.about_text);
        descriptionText.setMovementMethod(linkMovementMethod);
        final TextView contactTextView = view.findViewById(R.id.contact_info_text);
        contactTextView.setMovementMethod(linkMovementMethod);
    }

}
