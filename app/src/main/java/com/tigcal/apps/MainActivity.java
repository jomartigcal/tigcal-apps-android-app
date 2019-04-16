package com.tigcal.apps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private RecyclerView appsRecyclerView;
    private boolean isDisplayWide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

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

            bottomNavigationView.setSelectedItemId(R.id.action_android);
        }

        if (isDisplayWide) {
            displayAndroidApps();
            displayAssistantApps();
            displayChromeApps();
        }
    }

    private void displayAndroidApps() {
        List<App> androidApps = AppUtils.getAndroidApps(this);

        AppAdapter appAdapter = new AppAdapter(this, androidApps, new AppAdapter.OnClickListener() {
            @Override
            public void onClick(App app) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getUrl())));
            }
        });
        appAdapter.setButtonClickListener(new AppAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(App app) {
                if (!app.isInstalled()) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getUrl())));
                    return;
                }

                Intent intent = getPackageManager().getLaunchIntentForPackage(app.getPackageName());
                if (intent != null) {
                    startActivity(intent);
                } else {
                    Snackbar.make(appsRecyclerView, getString(R.string.app_cannot_open), Snackbar.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getUrl())));
                }
            }
        });

        if (isDisplayWide) {
            RecyclerView androidRecyclerView = findViewById(R.id.android_recycler_view);
            androidRecyclerView.setHasFixedSize(true);
            androidRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            androidRecyclerView.setAdapter(appAdapter);
        } else {
            setTitle(getString(R.string.menu_android));

            appsRecyclerView.setAdapter(appAdapter);
        }

    }

    private void displayAssistantApps() {
        AppAdapter appAdapter = new AppAdapter(this, AppUtils.getAssistantApps(), new AppAdapter.OnClickListener() {
            @Override
            public void onClick(App app) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getUrl())));
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
        AppAdapter appAdapter = new AppAdapter(this, AppUtils.getChromeApps(), new AppAdapter.OnClickListener() {
            @Override
            public void onClick(App app) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getUrl())));
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
}
