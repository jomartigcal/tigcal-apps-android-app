package com.tigcal.apps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

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
                        setTitle(getString(R.string.menu_android));
                        return true;
                    case R.id.action_assistant:
                        setTitle(getString(R.string.menu_assistant));
                        return true;
                    case R.id.action_chrome:
                        setTitle(getString(R.string.menu_chrome));
                        return true;
                }

                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_android);

        appsRecyclerView = findViewById(R.id.apps_recycler_view);
        appsRecyclerView.setHasFixedSize(true);
        appsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppAdapter appAdapter = new AppAdapter(this, AppUtils.getAndroidApps(), new AppAdapter.OnClickListener() {
            @Override
            public void onClick(App app) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getUrl())));
            }
        });
        appsRecyclerView.setAdapter(appAdapter);
    }
}
