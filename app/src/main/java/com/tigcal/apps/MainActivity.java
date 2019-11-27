package com.tigcal.apps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.tigcal.apps.views.AboutDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private RecyclerView appsRecyclerView;
    private boolean isDisplayWide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appsRecyclerView = findViewById(R.id.apps_recycler_view);
        if (appsRecyclerView == null) {
            isDisplayWide = true;
        } else {
            appsRecyclerView.setHasFixedSize(true);
            appsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_android:
                            displayAndroidApps();
                            MenuItemCompat.setContentDescription(item, getString(R.string.apps_android));
                            return true;
                        case R.id.action_assistant:
                            displayAssistantApps();
                            MenuItemCompat.setContentDescription(item, getString(R.string.apps_assistant));
                            return true;
                        case R.id.action_chrome:
                            displayChromeApps();
                            MenuItemCompat.setContentDescription(item, getString(R.string.apps_chrome));
                            return true;
                    }

                    return false;
                }
            });

            bottomNavigationView.setSelectedItemId(R.id.action_android);
        }

        if (isDisplayWide) {
            displayAndroidApps();
            displayAssistantApps();
            displayChromeApps();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                AboutDialog aboutDialog = AboutDialog.newInstance();
                aboutDialog.show(getSupportFragmentManager(),getString(R.string.about_header));
                return true;
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_feedback:
                sendFeedback();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayAndroidApps() {
        List<App> androidApps = AppUtils.INSTANCE.getAndroidApps(this);

        AppAdapter appAdapter = new AppAdapter(this, androidApps, new AppAdapter.OnClickListener() {
            @Override
            public void onClick(App app) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getLink())));
            }
        });

        final RecyclerView androidRecyclerView = findViewById(R.id.android_recycler_view);
        if (isDisplayWide) {
            androidRecyclerView.setHasFixedSize(true);
            androidRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            androidRecyclerView.setAdapter(appAdapter);
        } else {
            setTitle(getString(R.string.menu_android));

            appsRecyclerView.setAdapter(appAdapter);
        }

        appAdapter.setButtonClickListener(new AppAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(App app) {
                if (!app.isInstalled()) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getLink())));
                    return;
                }

                Intent intent = getPackageManager().getLaunchIntentForPackage(app.getPackageName());
                if (intent != null) {
                    startActivity(intent);
                } else {
                    Snackbar.make(isDisplayWide ? androidRecyclerView : appsRecyclerView, getString(R.string.app_cannot_open), Snackbar.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getLink())));
                }
            }
        });
    }

    private void displayAssistantApps() {
        AppAdapter appAdapter = new AppAdapter(this, AppUtils.INSTANCE.getAssistantApps(this), new AppAdapter.OnClickListener() {
            @Override
            public void onClick(App app) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getLink())));
            }
        });

        if (isDisplayWide) {
            RecyclerView assistantRecyclerView = findViewById(R.id.assistant_recycler_view);
            assistantRecyclerView.setHasFixedSize(true);
            assistantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            assistantRecyclerView.setAdapter(appAdapter);
        } else {
            setTitle(getString(R.string.menu_assistant));

            appsRecyclerView.setAdapter(appAdapter);
        }
    }

    private void displayChromeApps() {
        AppAdapter appAdapter = new AppAdapter(this, AppUtils.INSTANCE.getChromeApps(this), new AppAdapter.OnClickListener() {
            @Override
            public void onClick(App app) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getLink())));
            }
        });

        if (isDisplayWide) {
            RecyclerView chromeRecyclerView = findViewById(R.id.chrome_recycler_view);
            chromeRecyclerView.setHasFixedSize(true);
            chromeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            chromeRecyclerView.setAdapter(appAdapter);
        } else {
            setTitle(getString(R.string.menu_chrome));

            appsRecyclerView.setAdapter(appAdapter);
        }
    }

    private void sendFeedback() {
        StringBuilder deviceInfoBuilder = new StringBuilder();
        deviceInfoBuilder.append("\n\n--------------------");
        deviceInfoBuilder.append("\nDevice Information:");
        try {
            deviceInfoBuilder.append("\n App Version: ");
            deviceInfoBuilder.append(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "package name not found");
        }
        deviceInfoBuilder.append("\n OS Version: ")
                .append(System.getProperty("os.version"))
                .append("(")
                .append(android.os.Build.VERSION.INCREMENTAL)
                .append(")");
        deviceInfoBuilder.append("\n OS API Level: ")
                .append(android.os.Build.VERSION.SDK_INT);
        deviceInfoBuilder.append("\n Manufacturer: ")
                .append(Build.MANUFACTURER);
        deviceInfoBuilder.append("\n Model (Product): ")
                .append(android.os.Build.MODEL)
                .append(" (")
                .append(android.os.Build.PRODUCT)
                .append(")");

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:info@sweldongpinoy.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_feedback_subject, getString(R.string.app_name)));
        intent.putExtra(Intent.EXTRA_TEXT, deviceInfoBuilder.toString());
        startActivity(Intent.createChooser(intent, getString(R.string.send_feedback_header)));
    }
}
