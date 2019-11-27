package com.tigcal.apps

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.DrawableRes
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

object AppUtils {
    private val TAG = AppUtils::class.java.simpleName

    private val appComparator = AppComparator()

    fun getAndroidApps(context: Context): List<App> {
        val androidApps = getApps(context, "android.json")

        for (app in androidApps) {
            app.isInstalled = isAndroidAppInstalled(context, app)
        }

        var app = App()
        app.isAndroid = true
        app.name = "Help Me"
        app.packageName = "com.tigcal.helpme"
        app.link = "https://play.google.com/store/apps/details?id=com.tigcal.helpme"
        app.icon = R.drawable.ic_app_help_me
        var isInstalled = isAndroidAppInstalled(context, app)
        app.isInstalled = isInstalled
        if (isInstalled) {
            androidApps.add(app)
        }

        app = App()
        app.isAndroid = true
        app.name = "Tigcal Utils"
        app.packageName = "com.tigcal.utils"
        app.link = "https://play.google.com/apps/testing/com.tigcal.utils"
        app.icon = R.drawable.ic_app_tigcal_utils
        isInstalled = isAndroidAppInstalled(context, app)
        app.isInstalled = isInstalled
        if (isInstalled) {
            androidApps.add(app)
        }

        app = App()
        app.isAndroid = true
        app.name = "You and Me"
        app.packageName = "com.tigcal.youandme"
        app.link = "https://play.google.com/store/apps/details?id=com.tigcal.youandme"
        app.icon = R.drawable.ic_app_yumi
        isInstalled = isAndroidAppInstalled(context, app)
        app.isInstalled = isInstalled
        if (isInstalled) {
            androidApps.add(app)
        }

        Collections.sort(androidApps, appComparator)

        return androidApps
    }

    fun getAssistantApps(context: Context): List<App> {
        val assistantApps = getApps(context, "assistant.json")

        Collections.sort(assistantApps, appComparator)

        return assistantApps
    }

    fun getChromeApps(context: Context): List<App> {
        val chromeApps = getApps(context, "chrome.json")

        Collections.sort(chromeApps, appComparator)

        return chromeApps
    }

    private fun getApps(context: Context, jsonFile: String): ArrayList<App> {
        var apps = ArrayList<App>()

        val appsJson = getAppsJson(context, jsonFile)
        try {
            val parentObject = JSONObject(appsJson)
            apps = parseApps(context, parentObject.optJSONArray("apps"))
        } catch (e: JSONException) {
            Log.e(TAG, "JSONException in getApps: " + e.localizedMessage)
        }

        return apps
    }

    private fun getAppsJson(context: Context, jsonFile: String): String {
        try {
            val inputStream = context.assets.open(jsonFile)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer,  Charsets.UTF_8)
        } catch (e: IOException) {
            Log.e(TAG, "IOException in getAppsJson: " + e.localizedMessage)
            return ""
        }

    }

    private fun parseApps(context: Context, appsJsonArray: JSONArray): ArrayList<App> {
        val appList = ArrayList<App>()
        for (i in 0 until appsJsonArray.length()) {
            val appObject = appsJsonArray.optJSONObject(i)
            if (appObject != null) {
                val app = App()
                app.name = appObject.optString("name")
                app.icon = getDrawableById(context, appObject.optString("icon"))
                app.link = appObject.optString("link")
                app.isAndroid = appObject.optBoolean("isAndroid")
                app.packageName = appObject.optString("packageName")
                appList.add(app)
            }
        }

        return appList
    }

    private fun isAndroidAppInstalled(context: Context, androidApp: App): Boolean {
        try {
            context.packageManager.getPackageInfo(androidApp.packageName, 0)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }

    }

    @DrawableRes
    private fun getDrawableById(context: Context, drawableId: String): Int {
        return context.resources.getIdentifier(drawableId, "drawable", context.packageName)
    }

    private class AppComparator : Comparator<App> {

        override fun compare(app1: App, app2: App): Int {
            return app1.name!!.compareTo(app2.name!!)
        }
    }
}
