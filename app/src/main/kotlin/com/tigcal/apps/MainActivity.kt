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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.tigcal.apps.util.AppUtils
import com.tigcal.apps.views.AboutDialog


class MainActivity : AppCompatActivity() {
    private var isDisplayWide = false

    private var appsRecyclerView: RecyclerView? = null
    private var androidRecyclerView: RecyclerView? = null
    private var webRecyclerView: RecyclerView? = null
    private var othersRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        androidRecyclerView = findViewById(R.id.android_recycler_view)
        appsRecyclerView = findViewById(R.id.apps_recycler_view)
        webRecyclerView = findViewById(R.id.web_recycler_view)
        othersRecyclerView = findViewById(R.id.others_recycler_view)

        isDisplayWide = appsRecyclerView == null

        appsRecyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        val bottomNavigationView: BottomNavigationView? = findViewById(R.id.bottom_navigation)
        bottomNavigationView?.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_android -> {
                    displayAndroidApps()
                    MenuItemCompat.setContentDescription(item, getString(R.string.apps_android))
                    return@OnItemSelectedListener true
                }
                R.id.action_web -> {
                    displayWebApps()
                    MenuItemCompat.setContentDescription(item, getString(R.string.apps_web))
                    return@OnItemSelectedListener true
                }
                R.id.action_others -> {
                    displayAssistantApps()
                    MenuItemCompat.setContentDescription(item, getString(R.string.apps_others))
                    return@OnItemSelectedListener true
                }
            }
            false
        })
        bottomNavigationView?.selectedItemId = R.id.action_android

        if (isDisplayWide) {
            displayAndroidApps()
            displayAssistantApps()
            displayWebApps()
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

        if (isDisplayWide) {
            androidRecyclerView?.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                adapter = appAdapter
            }
        } else {
            title = getString(R.string.menu_android)
            appsRecyclerView?.adapter = appAdapter
        }
    }

    private fun displayAssistantApps() {
        val appAdapter = AppAdapter(this, AppUtils.getOtherApps(this)) { app ->
            openAppLink(app)
        }
        appAdapter.shareListener = { app -> shareApp(app) }

        if (isDisplayWide) {
            othersRecyclerView?.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                adapter = appAdapter
            }
        } else {
            title = getString(R.string.menu_others)
            appsRecyclerView?.adapter = appAdapter
        }
    }

    private fun displayWebApps() {
        val appAdapter = AppAdapter(this, AppUtils.getWebApps(this)) { app ->
            openAppLink(app)
        }
        appAdapter.shareListener = { app -> shareApp(app) }

        if (isDisplayWide) {
            webRecyclerView?.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                adapter = appAdapter
            }
        } else {
            title = getString(R.string.menu_web)
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
        if (app.link.isNotEmpty()) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(app.link)))
        } else {
            MaterialAlertDialogBuilder(this)
                .setTitle(app.name)
                .setMessage(app.description)
                .setPositiveButton(android.R.string.ok, null)
                .show()
        }
    }

    private fun shareApp(app: App) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, app.name + ": " + app.link)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SHARE, null)
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
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("dev@tigcal.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_feedback_subject, getString(R.string.app_name)))
        intent.putExtra(Intent.EXTRA_TEXT, deviceInfo)
        startActivity(Intent.createChooser(intent, getString(R.string.send_feedback_header)))
    }

    private fun getPackageVersion(): String {
        return try {
            getPackageInfo().versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "package name not found")
            ""
        }
    }

    private fun getPackageInfo() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(
            packageName, PackageManager.PackageInfoFlags.of(0)
        )
    } else {
        packageManager.getPackageInfo(packageName, 0)
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}
