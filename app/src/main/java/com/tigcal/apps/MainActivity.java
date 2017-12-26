package com.tigcal.apps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private RecyclerView appsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_android:
                        displayAndroidApps();
                        return true;
                    case R.id.action_assistant:
                        displayAssistantApps();
                        return true;
                    case R.id.action_chrome:
                        displayChromeApps();
                        return true;
                }

                return false;
            }
        });

        appsRecyclerView = findViewById(R.id.apps_recycler_view);
        appsRecyclerView.setHasFixedSize(true);
        appsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView.setSelectedItemId(R.id.action_android);
    }

    private void displayAndroidApps() {
        setTitle(getString(R.string.menu_android));

        List<App> androidApps = AppUtils.getAndroidApps();
        for (App androidApp : androidApps) {
            androidApp.setInstalled(isAndroidAppInstalled(androidApp));
        }

        AppAdapter appAdapter = new AppAdapter(this, androidApps, new AppAdapter.OnClickListener() {
            @Override
            public void onClick(App app) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getUrl())));
            }
        });
        appAdapter.setButtonClickListener(new AppAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(App app) {
                startActivity(getPackageManager().getLaunchIntentForPackage(app.getPackageName()));
            }
        });
        appsRecyclerView.setAdapter(appAdapter);
    }

    private void displayAssistantApps() {
        setTitle(getString(R.string.menu_assistant));

        AppAdapter appAdapter = new AppAdapter(this, AppUtils.getAssistantApps(), new AppAdapter.OnClickListener() {
            @Override
            public void onClick(App app) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getUrl())));
            }
        });
        appsRecyclerView.setAdapter(appAdapter);
    }

    private void displayChromeApps() {
        setTitle(getString(R.string.menu_chrome));

        AppAdapter appAdapter = new AppAdapter(this, AppUtils.getChromeApps(), new AppAdapter.OnClickListener() {
            @Override
            public void onClick(App app) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getUrl())));
            }
        });
        appsRecyclerView.setAdapter(appAdapter);
    }

    private boolean isAndroidAppInstalled(App androidApp) {
        try {
            getPackageManager().getPackageInfo(androidApp.getPackageName(), 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
