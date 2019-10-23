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
        List<App> androidApps = new ArrayList<>();

        App app = new App();
        app.setAndroid(true);
        app.setName("Sweldong Pinoy");
        app.setPackageName("com.tigcal.salarycalculator");
        app.setUrl("https://play.google.com/store/apps/details?id=com.tigcal.salarycalculator");
        app.setIcon(R.drawable.ic_app_sweldong_pinoy);
        app.setInstalled(isAndroidAppInstalled(context, app));
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Pinoy Jokes");
        app.setPackageName("com.tigcal.pinoyjokes");
        app.setUrl("https://play.google.com/store/apps/details?id=com.tigcal.pinoyjokes");
        app.setIcon(R.drawable.ic_app_pinoy_jokes);
        app.setInstalled(isAndroidAppInstalled(context, app));
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("GDG Watch Face");
        app.setPackageName("org.gdgph.watchface");
        app.setUrl("https://play.google.com/store/apps/details?id=org.gdgph.watchface");
        app.setIcon(R.drawable.ic_app_gdg_old);
        app.setInstalled(isAndroidAppInstalled(context, app));
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Help Me");
        app.setPackageName("com.tigcal.helpme");
        app.setUrl("https://play.google.com/store/apps/details?id=com.tigcal.helpme");
        app.setIcon(R.drawable.ic_app_help_me);
        boolean isInstalled = isAndroidAppInstalled(context, app);
        app.setInstalled(isInstalled);
        if (isInstalled) {
            androidApps.add(app);
        }

        app = new App();
        app.setAndroid(true);
        app.setName("GDG Philippines");
        app.setPackageName("org.gtugphilippines.android");
        app.setUrl("https://play.google.com/store/apps/details?id=org.gtugphilippines.android");
        app.setIcon(R.drawable.ic_app_gdg);
        app.setInstalled(isAndroidAppInstalled(context, app));
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Boto Ko");
        app.setPackageName("com.tigcal.botoko");
        app.setUrl("https://play.google.com/store/apps/details?id=com.tigcal.botoko");
        app.setIcon(R.drawable.ic_app_boto_ko);
        app.setInstalled(isAndroidAppInstalled(context, app));
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Thirteenth Month");
        app.setPackageName("com.sweldongpinoy.thirteenthmonth");
        app.setUrl("https://play.google.com/store/apps/details?id=com.sweldongpinoy.thirteenthmonth");
        app.setIcon(R.drawable.ic_app_13th_month);
        app.setInstalled(isAndroidAppInstalled(context, app));
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("PHostpaid");
        app.setPackageName("com.tigcal.postpaid");
        app.setUrl("https://play.google.com/store/apps/details?id=com.tigcal.postpaid");
        app.setIcon(R.drawable.ic_app_phostpaid);
        app.setInstalled(isAndroidAppInstalled(context, app));
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Budget Pinoy");
        app.setPackageName("com.sweldongpinoy.budget");
        app.setUrl("https://play.google.com/store/apps/details?id=com.sweldongpinoy.budget");
        app.setIcon(R.drawable.ic_app_budget_pinoy);
        app.setInstalled(isAndroidAppInstalled(context, app));
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Tigcal Utils");
        app.setPackageName("com.tigcal.utils");
        app.setUrl("https://play.google.com/apps/testing/com.tigcal.utils");
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
        app.setUrl("https://play.google.com/store/apps/details?id=com.tigcal.youandme");
        app.setIcon(R.drawable.ic_app_yumi);
        isInstalled = isAndroidAppInstalled(context, app);
        app.setInstalled(isInstalled);
        if (isInstalled) {
            androidApps.add(app);
        }

        Collections.sort(androidApps, appComparator);

        return androidApps;
    }

    public static List<App> getAssistantApps() {
        List<App> assistantApps = new ArrayList<>();

        App app = new App();
        app.setName("Budget Penny");
        app.setUrl("https://assistant.google.com/services/a/id/386ffed76114e24f");
        app.setIcon(R.drawable.ic_app_budget_penny);
        assistantApps.add(app);

        app = new App();
        app.setName("GDG Philippines");
        app.setUrl("https://assistant.google.com/services/a/id/631a617c588e09c9/");
        app.setIcon(R.drawable.ic_app_gdg_old);
        assistantApps.add(app);

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
            apps = parseApps(parentObject.optJSONArray("apps"));
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

    private static List<App> parseApps(JSONArray appsJsonArray) {
        List<App> appList = new ArrayList<>();
        for (int i = 0; i < appsJsonArray.length(); i++) {
            JSONObject appObject = appsJsonArray.optJSONObject(i);
            if (appObject != null) {
                App app = new App();
                app.setName(appObject.optString("name"));
                //TODO get icon from resources with name from appObject.optString("icon")
                app.setIcon(R.mipmap.ic_launcher);
                app.setUrl(appObject.optString("link"));
                app.setAndroid(appObject.optBoolean("isAndroid"));
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

    private static class AppComparator implements Comparator<App> {

        @Override
        public int compare(App app1, App app2) {
            return app1.getName().compareTo(app2.getName());
        }
    }
}
