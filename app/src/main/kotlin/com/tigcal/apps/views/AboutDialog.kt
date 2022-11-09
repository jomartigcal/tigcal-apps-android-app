package com.tigcal.apps.views

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.tigcal.apps.R

class AboutDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_about, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setTitle(context?.getString(R.string.about_header))

        val appVersion = "\n${context?.getString(R.string.app_name)} ${getAppVersion()}\n"
        val versionText: TextView = view.findViewById(R.id.version_text)
        versionText.text = appVersion

        val linkMovementMethod = LinkMovementMethod.getInstance()
        val developerText: TextView = view.findViewById(R.id.about_text)
        developerText.movementMethod = linkMovementMethod
        val contactText: TextView = view.findViewById(R.id.contact_info_text)
        contactText.movementMethod = linkMovementMethod
    }

    private fun getAppVersion(): String {
        return try {
            context?.let { context ->
                val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    context.packageManager.getPackageInfo(
                        context.packageName, PackageManager.PackageInfoFlags.of(0)
                    )
                } else {
                    context.packageManager.getPackageInfo(context.packageName, 0)
                }
                packageInfo?.versionName
            } ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(DIALOG_TAG, "package name not found")
            ""
        }
    }

    companion object {
        private val DIALOG_TAG = AboutDialog::class.java.simpleName

        fun newInstance(): AboutDialog {
            return AboutDialog()
        }
    }

}
