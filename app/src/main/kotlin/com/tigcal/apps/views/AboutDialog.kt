package com.tigcal.apps.views

import android.content.pm.PackageManager
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

        val appVersionBuilder = StringBuilder()
        appVersionBuilder.append("\n")
        appVersionBuilder.append(context?.getString(R.string.app_name))
        try {
            appVersionBuilder.append(" ")
                    .append(context?.packageManager?.getPackageInfo(context?.packageName, 0)?.versionName)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(DIALOG_TAG, "package name not found")
        }

        appVersionBuilder.append("\n")

        val appVersion = view.findViewById<TextView>(R.id.version_text)
        appVersion.text = appVersionBuilder.toString()

        val linkMovementMethod = LinkMovementMethod.getInstance()
        val descriptionText = view.findViewById<TextView>(R.id.about_text)
        descriptionText.movementMethod = linkMovementMethod
        val contactTextView = view.findViewById<TextView>(R.id.contact_info_text)
        contactTextView.movementMethod = linkMovementMethod
    }

    companion object {
        private val DIALOG_TAG = AboutDialog::class.java.simpleName

        @JvmStatic
        fun newInstance(): AboutDialog {
            return AboutDialog()
        }
    }

}
