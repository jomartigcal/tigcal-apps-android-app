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
import com.google.android.material.snackbar.Snackbar
import com.tigcal.apps.AppAdapter.OnButtonClickListener
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
        val androidApps = AppUtils.getAndroidApps(this)

        val appAdapter = AppAdapter(this, androidApps, object : AppAdapter.OnClickListener {
            override fun onClick(app: App) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(app.link)))
            }
        })

        val androidRecyclerView = findViewById<RecyclerView>(R.id.android_recycler_view)
        if (isDisplayWide) {
            androidRecyclerView.setHasFixedSize(true)
            androidRecyclerView.layoutManager = LinearLayoutManager(this)
            androidRecyclerView.adapter = appAdapter
        } else {
            title = getString(R.string.menu_android)
            appsRecyclerView?.adapter = appAdapter
        }

        appAdapter.setButtonClickListener(object : OnButtonClickListener {
            override fun onButtonClick(app: App) {
                if (!app.isInstalled) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(app.link)))
                    return
                }
                val intent = packageManager.getLaunchIntentForPackage(app.packageName)
                if (intent != null) {
                    startActivity(intent)
                } else {
                    Snackbar.make(if (isDisplayWide) androidRecyclerView else appsRecyclerView!!, getString(R.string.app_cannot_open), Snackbar.LENGTH_SHORT).show()
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(app.link)))
                }
            }
        })
    }

    private fun displayAssistantApps() {
        val appAdapter = AppAdapter(this, AppUtils.getAssistantApps(this), object : AppAdapter.OnClickListener {
            override fun onClick(app: App) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(app.link)))
            }
        })

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
        val appAdapter = AppAdapter(this, AppUtils.getChromeApps(this), object : AppAdapter.OnClickListener {
            override fun onClick(app: App) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(app.link)))
            }
        })

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

    private fun sendFeedback() {
        val deviceInfoBuilder = StringBuilder()
        deviceInfoBuilder.append("\n\n--------------------")
        deviceInfoBuilder.append("\nDevice Information:")
        try {
            deviceInfoBuilder.append("\n App Version: ")
            deviceInfoBuilder.append(packageManager.getPackageInfo(packageName, 0).versionName)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "package name not found")
        }
        deviceInfoBuilder.append("\n OS Version: ")
                .append(System.getProperty("os.version"))
                .append("(")
                .append(Build.VERSION.INCREMENTAL)
                .append(")")
        deviceInfoBuilder.append("\n OS API Level: ")
                .append(Build.VERSION.SDK_INT)
        deviceInfoBuilder.append("\n Manufacturer: ")
                .append(Build.MANUFACTURER)
        deviceInfoBuilder.append("\n Model (Product): ")
                .append(Build.MODEL)
                .append(" (")
                .append(Build.PRODUCT)
                .append(")")

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:");
        intent.putExtra(Intent.EXTRA_EMAIL,  arrayOf("jomar@tigcal.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_feedback_subject, getString(R.string.app_name)))
        intent.putExtra(Intent.EXTRA_TEXT, deviceInfoBuilder.toString())
        startActivity(Intent.createChooser(intent, getString(R.string.send_feedback_header)))
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}
