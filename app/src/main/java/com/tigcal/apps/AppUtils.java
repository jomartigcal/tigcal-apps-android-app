package com.tigcal.apps;

import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    private AppUtils() {

    }

    public static List<App> getAndroidApps() {
        List<App> androidApps = new ArrayList<>();

        App app = new App();
        app.setAndroid(true);
        app.setName("Sweldong Pinoy");
        app.setPackageName("com.tigcal.salarycalculator");
        app.setUrl("https://play.google.com/store/apps/details?id=com.tigcal.salarycalculator");
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Pinoy Jokes");
        app.setPackageName("com.tigcal.pinoyjokes");
        app.setUrl("https://play.google.com/store/apps/details?id=com.tigcal.pinoyjokes");
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("GDG Watch Face");
        app.setPackageName("org.gdgph.watchface");
        app.setUrl("https://play.google.com/store/apps/details?id=org.gdgph.watchface");
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Help Me");
        app.setPackageName("com.tigcal.helpme");
        app.setUrl("https://play.google.com/store/apps/details?id=com.tigcal.helpme");
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("GDG Philippines");
        app.setPackageName("org.gtugphilippines.android");
        app.setUrl("https://play.google.com/store/apps/details?id=org.gtugphilippines.android");
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Boto Ko");
        app.setPackageName("com.tigcal.botoko");
        app.setUrl("https://play.google.com/store/apps/details?id=com.tigcal.botoko");
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Thirteenth Month");
        app.setPackageName("com.sweldongpinoy.thirteenthmonth");
        app.setUrl("https://play.google.com/store/apps/details?id=com.sweldongpinoy.thirteenthmonth");
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("PHostpaid");
        app.setPackageName("com.tigcal.postpaid");
        app.setUrl("https://play.google.com/store/apps/details?id=com.tigcal.postpaid");
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Tigcal Utilities");
        app.setPackageName("com.tigcal.utils");
        app.setUrl("https://play.google.com/apps/testing/com.tigcal.utils");
        androidApps.add(app);

        return androidApps;
    }
}
