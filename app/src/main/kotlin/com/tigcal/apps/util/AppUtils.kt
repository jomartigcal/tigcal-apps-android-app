package com.tigcal.apps.util

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.DrawableRes
import com.tigcal.apps.App
import com.tigcal.apps.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object AppUtils {
    private val TAG = AppUtils::class.java.simpleName

    fun getAndroidApps(context: Context): List<App> {
        val androidApps = getApps(context, "android.json")

        var app = App(
                isAndroid = true,
                name = "Help Me",
                description = "",
                packageName = "com.tigcal.helpme",
                link = "https://play.google.com/store/apps/details?id=com.tigcal.helpme",
                icon = R.drawable.ic_app_help_me
        )

        var isInstalled = isAndroidAppInstalled(context, app)
        app.isInstalled = isInstalled
        if (isInstalled) {
            androidApps.add(app)
        }

        app = App(
                isAndroid = true,
                name = "Tigcal Utilities",
                description = "Tigcal Utilities is a collection of utility mini-apps, which includes includes Battery and Flashlight.",
                packageName = "com.tigcal.utils",
                link = "https://play.google.com/store/apps/details?id=com.tigcal.utils",
                icon = R.drawable.ic_app_tigcal_utils
        )
        isInstalled = isAndroidAppInstalled(context, app)
        app.isInstalled = isInstalled
        if (isInstalled) {
            androidApps.add(app)
        }

        app = App(
                isAndroid = true,
                name = "You and Me",
                description = "You and Me is the app for you and your special someone. You can use the app to check your photos with your special someone.",
                packageName = "com.tigcal.youandme",
                link = "https://play.google.com/store/apps/details?id=com.tigcal.youandme",
                icon = R.drawable.ic_app_yumi
        )

        isInstalled = isAndroidAppInstalled(context, app)
        app.isInstalled = isInstalled
        if (isInstalled) {
            androidApps.add(app)
        }

        return androidApps.map { it.copy(isInstalled = isAndroidAppInstalled(context, it)) }
                .sortedBy { it.name }
    }

    fun getAssistantApps(context: Context) = getApps(context, "assistant.json")
            .sortedBy { it.name }

    fun getWebApps(context: Context) = getApps(context, "web.json")
            .sortedBy { it.name }

    private fun getApps(context: Context, jsonFile: String): ArrayList<App> {
        return try {
            val parentObject = JSONObject(getAppsJson(context, jsonFile))
            parseApps(context, parentObject.optJSONArray("apps"))
        } catch (e: JSONException) {
            Log.e(TAG, "JSONException in getApps: " + e.localizedMessage)
            ArrayList()
        }
    }

    private fun getAppsJson(context: Context, jsonFile: String): String {
        return try {
            val inputStream = context.assets.open(jsonFile)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            Log.e(TAG, "IOException in getAppsJson: " + e.localizedMessage)
            ""
        }
    }

    private fun parseApps(context: Context, appsJsonArray: JSONArray?): ArrayList<App> {
        appsJsonArray ?: return arrayListOf()

        val appList = ArrayList<App>()
        for (i in 0 until appsJsonArray.length()) {
            val appObject = appsJsonArray.optJSONObject(i)
            if (appObject != null) {
                val app = App(
                        name = appObject.optString("name"),
                        icon = getDrawableById(context, appObject.optString("icon")),
                        link = appObject.optString("link"),
                        isAndroid = appObject.optBoolean("isAndroid"),
                        packageName = appObject.optString("packageName")
                )
                appList.add(app)
            }
        }

        return appList
    }

    private fun isAndroidAppInstalled(context: Context, androidApp: App): Boolean {
        return try {
            context.packageManager.getPackageInfo(androidApp.packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    @DrawableRes
    private fun getDrawableById(context: Context, drawableId: String): Int {
        return context.resources.getIdentifier(drawableId, "drawable", context.packageName)
    }
}
