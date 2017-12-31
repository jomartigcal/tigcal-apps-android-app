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
        app.setName("Budget Pinoy");
        app.setPackageName("com.sweldongpinoy.budget");
        app.setUrl("https://play.google.com/store/apps/details?id=com.sweldongpinoy.budget");
        androidApps.add(app);

        app = new App();
        app.setAndroid(true);
        app.setName("Tigcal Utilities");
        app.setPackageName("com.tigcal.utils");
        app.setUrl("https://play.google.com/apps/testing/com.tigcal.utils");
        androidApps.add(app);

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
        app.setIcon(R.drawable.ic_app_gdg);
        assistantApps.add(app);

        return assistantApps;
    }

    public static List<App> getChromeApps() {
        List<App> chromeApps = new ArrayList<>();

        App app = new App();
        app.setName("Android Gradle Extension");
        app.setUrl("https://chrome.google.com/webstore/detail/android-gradle-extension/fhpkfgflphcjlacgolciajdkfodapmcn");
        app.setIcon(R.drawable.ic_app_age);
        chromeApps.add(app);

        app = new App();
        app.setName("Pinoy Jokes");
        app.setUrl("https://chrome.google.com/webstore/detail/pinoy-jokes-chrome-extens/olpaccmgjefjnmojjmhkobjgmofpogai");
        app.setIcon(R.drawable.ic_app_pinoy_jokes);
        chromeApps.add(app);

        app = new App();
        app.setName("GDG Philippines");
        app.setUrl("https://chrome.google.com/webstore/detail/gdg-philippines/pbpopomlbomjpbmpfigaogeodiemmjbm");
        app.setIcon(R.drawable.ic_app_gdg);
        chromeApps.add(app);

        return chromeApps;
    }
}
