package com.tigcal.apps;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.DrawableRes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppUtils {
    private static final String TAG = AppUtils.class.getSimpleName();

    private static Comparator<App> appComparator = new AppComparator();

    private AppUtils() {

    }

    public static List<App> getAndroidApps(Context context) {
        List<App> androidApps = getApps(context, "android.json");

        for (App app : androidApps) {
            app.setInstalled(isAndroidAppInstalled(context, app));
        }

        App app = new App();
        app.setAndroid(true);
        app.setName("Help Me");
        app.setPackageName("com.tigcal.helpme");
        app.setLink("https://play.google.com/store/apps/details?id=com.tigcal.helpme");
        app.setIcon(R.drawable.ic_app_help_me);
        boolean isInstalled = isAndroidAppInstalled(context, app);
        app.setInstalled(isInstalled);
        if (isInstalled) {
            androidApps.add(app);
        }

        app = new App();
        app.setAndroid(true);
        app.setName("Tigcal Utils");
        app.setPackageName("com.tigcal.utils");
        app.setLink("https://play.google.com/apps/testing/com.tigcal.utils");
        app.setIcon(R.drawable.ic_app_tigcal_utils);
        isInstalled = isAndroidAppInstalled(context, app);
        app.setInstalled(isInstalled);
        if (isInstalled) {
            androidApps.add(app);
        }

        app = new App();
        app.setAndroid(true);
        app.setName("You and Me");
        app.setPackageName("com.tigcal.youandme");
        app.setLink("https://play.google.com/store/apps/details?id=com.tigcal.youandme");
        app.setIcon(R.drawable.ic_app_yumi);
        isInstalled = isAndroidAppInstalled(context, app);
        app.setInstalled(isInstalled);
        if (isInstalled) {
            androidApps.add(app);
        }

        Collections.sort(androidApps, appComparator);

        return androidApps;
    }

    public static List<App> getAssistantApps(Context context) {
        List<App> assistantApps = getApps(context, "assistant.json");

        Collections.sort(assistantApps, appComparator);

        return assistantApps;
    }

    public static List<App> getChromeApps(Context context) {
        List<App> chromeApps = getApps(context, "chrome.json");

        Collections.sort(chromeApps, appComparator);

        return chromeApps;
    }

    private static List<App> getApps(Context context, String jsonFile) {
        List<App> apps = new ArrayList<>();

        String appsJson = getAppsJson(context, jsonFile);
        try {
            JSONObject parentObject = new JSONObject(appsJson);
            apps = parseApps(context, parentObject.optJSONArray("apps"));
        } catch (JSONException e) {
            Log.e(TAG, "JSONException in getApps: " + e.getLocalizedMessage());
        }

        return apps;
    }

    private static String getAppsJson(Context context, String jsonFile) {
        try {
            InputStream inputStream = context.getAssets().open(jsonFile);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.e(TAG, "IOException in getAppsJson: " + e.getLocalizedMessage());
            return "";
        }
    }

    private static List<App> parseApps(Context context, JSONArray appsJsonArray) {
        List<App> appList = new ArrayList<>();
        for (int i = 0; i < appsJsonArray.length(); i++) {
            JSONObject appObject = appsJsonArray.optJSONObject(i);
            if (appObject != null) {
                App app = new App();
                app.setName(appObject.optString("name"));
                app.setIcon(getDrawableById(context, appObject.optString("icon")));
                app.setLink(appObject.optString("link"));
                app.setAndroid(appObject.optBoolean("isAndroid"));
                app.setPackageName(appObject.optString("packageName"));
                appList.add(app);
            }
        }

        return appList;
    }

    private static boolean isAndroidAppInstalled(Context context, App androidApp) {
        try {
            context.getPackageManager().getPackageInfo(androidApp.getPackageName(), 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private @DrawableRes
    static int getDrawableById(Context context, String drawableId) {
        return context.getResources().getIdentifier(drawableId, "drawable", context.getPackageName());
    }

    private static class AppComparator implements Comparator<App> {

        @Override
        public int compare(App app1, App app2) {
            return app1.getName().compareTo(app2.getName());
        }
    }
}
