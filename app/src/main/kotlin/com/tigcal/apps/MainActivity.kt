package com.tigcal.apps

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tigcal.apps.util.AppUtils
import com.tigcal.apps.views.AboutDialog

class MainActivity : AppCompatActivity() {
    private var appsRecyclerView: RecyclerView? = null
    private var isDisplayWide = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        appsRecyclerView = findViewById(R.id.apps_recycler_view)
        if (appsRecyclerView == null) {
            isDisplayWide = true
        } else {
            appsRecyclerView?.setHasFixedSize(true)
            appsRecyclerView?.layoutManager = LinearLayoutManager(this)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView?.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_android -> {
                    displayAndroidApps()
                    MenuItemCompat.setContentDescription(item, getString(R.string.apps_android))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_assistant -> {
                    displayAssistantApps()
                    MenuItemCompat.setContentDescription(item, getString(R.string.apps_assistant))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_chrome -> {
                    displayChromeApps()
                    MenuItemCompat.setContentDescription(item, getString(R.string.apps_chrome))
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        bottomNavigationView.selectedItemId = R.id.action_android

        if (isDisplayWide) {
            displayAndroidApps()
            displayAssistantApps()
            displayChromeApps()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_about -> {
                val aboutDialog = AboutDialog.newInstance()
                aboutDialog.show(supportFragmentManager, getString(R.string.about_header))
                true
            }
            R.id.menu_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_feedback -> {
                sendFeedback()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayAndroidApps() {
        val appAdapter = AppAdapter(this, AppUtils.getAndroidApps(this)) { app ->
            openAppLink(app)
        }
        appAdapter.openOrDownloadListener = { app -> openApp(app) }
        appAdapter.shareListener = { app -> shareApp(app) }

        val androidRecyclerView = findViewById<RecyclerView>(R.id.android_recycler_view)
        if (isDisplayWide) {
            androidRecyclerView.setHasFixedSize(true)
            androidRecyclerView.layoutManager = LinearLayoutManager(this)
            androidRecyclerView.adapter = appAdapter
        } else {
            title = getString(R.string.menu_android)
            appsRecyclerView?.adapter = appAdapter
        }
    }

    private fun displayAssistantApps() {
        val appAdapter = AppAdapter(this, AppUtils.getAssistantApps(this)) { app ->
            openAppLink(app)
        }
        appAdapter.shareListener = { app -> shareApp(app) }

        if (isDisplayWide) {
            val assistantRecyclerView = findViewById<RecyclerView>(R.id.assistant_recycler_view)
            assistantRecyclerView.setHasFixedSize(true)
            assistantRecyclerView.layoutManager = LinearLayoutManager(this)
            assistantRecyclerView.adapter = appAdapter
        } else {
            title = getString(R.string.menu_assistant)
            appsRecyclerView?.adapter = appAdapter
        }
    }

    private fun displayChromeApps() {
        val appAdapter = AppAdapter(this, AppUtils.getChromeApps(this)) { app ->
            openAppLink(app)
        }
        appAdapter.shareListener = { app -> shareApp(app) }

        if (isDisplayWide) {
            val chromeRecyclerView = findViewById<RecyclerView>(R.id.chrome_recycler_view)
            chromeRecyclerView.setHasFixedSize(true)
            chromeRecyclerView.layoutManager = LinearLayoutManager(this)
            chromeRecyclerView.adapter = appAdapter
        } else {
            title = getString(R.string.menu_chrome)
            appsRecyclerView?.adapter = appAdapter
        }
    }

    private fun openApp(app: App) {
        val intent = packageManager.getLaunchIntentForPackage(app.packageName)
        if (app.isInstalled && intent != null) {
            startActivity(intent)
        } else {
            openAppLink(app)
        }
    }

    private fun openAppLink(app: App) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(app.link)))
    }

    private fun shareApp(app: App) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, app.name + ": " + app.link)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun sendFeedback() {
        val deviceInfo = """

            --------------------
            Device Information:
            App Version: ${getPackageVersion()}
            OS Version: ${System.getProperty("os.version")} (${Build.VERSION.INCREMENTAL})
            OS API Level: ${Build.VERSION.SDK_INT}
            Manufacturer: ${Build.MANUFACTURER}
            Model (Product): ${Build.MODEL} (${Build.PRODUCT})
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:");
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("jomar@tigcal.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_feedback_subject, getString(R.string.app_name)))
        intent.putExtra(Intent.EXTRA_TEXT, deviceInfo)
        startActivity(Intent.createChooser(intent, getString(R.string.send_feedback_header)))
    }

    private fun getPackageVersion(): String {
        return try {
            packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "package name not found")
            ""
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}
